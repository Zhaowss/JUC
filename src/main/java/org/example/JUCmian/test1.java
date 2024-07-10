package org.example.JUCmian;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassNAME test1
 * @Description 自定义线程池的操作1 阻塞队列
 * @Author zhaoweishan
 * @Date 2024/7/10 10:14
 * @Version 1.0
 */


@Slf4j
public class test1 {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS,10);
        for (int i = 0; i < 5; i++) {
            int i1=i;
            threadPool.execute(()->{
                log.info("方法{}在线程池中的打印",i1);
            });
        }

    }

}
@Slf4j
class ThreadPool{
    public ThreadPool(int coresize, long timeout, TimeUnit timeUnit,int capecty) {
        this.coresize = coresize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskqueue=new Blockqeue<>(capecty);
    }

    //    我们的任务队列
    private Blockqeue<Runnable>  taskqueue;
//    存放我们的核心线程
    private HashSet<Worker> workers=new HashSet<>();
//    核心线程
    private int coresize;
//    获取任务的超时时间
    private long timeout;
//    获取任务的超时时间单位
    private TimeUnit timeUnit;


    public void execute(Runnable task){
//为了保证此段代码的这个线程的安全性，我们使用synchronized关键词将其包围
        synchronized (workers){
            //        当任务数没有超过我们的核心数，直接交给我们的worker对象去执行
            if (workers.size()<coresize){

                Worker worker=new Worker(task);
                log.info("新增一些worker的{}",worker);
                workers.add(worker);
                worker.start();
            }
//        如果任务数超过我们的任务的core size的时候，我们将其放入我们的等待的队列
            else {
                taskqueue.put(task);
                log.info("新增任务到我们的任务队列中{}",task);
            }
        }

    }

    class Worker extends Thread{
        private  Runnable task1;
        public Worker(Runnable task) {
            this.task1=task;
        }

        @Override
        public void run() {
//            判断当前的task中是否为空
//            如果当前task为空，检查当前的队列中是否存在元素
            while (task1!=null || (task1=taskqueue.take())!=null){
                try {
                    log.info("正在执行的----{}",task1);
                 task1.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task1=null;
                }
            }

            synchronized (workers){
                workers.remove(this);
                log.info("worker 被移除掉{}",this);
            }

        }
    }

}



//阻塞队列的实现
class Blockqeue<T>{

    //任务队列
    private Deque<T> tDeque=new ArrayDeque<T>();
//    锁
   private ReentrantLock reentranlock= new ReentrantLock();
//   生产者消费变量

   private Condition fullwaitset=reentranlock.newCondition();
//    消费者条件变量

    private Condition nullwaitset=reentranlock.newCondition();

//    容量
    private  int capcity;

    public Blockqeue(int capcity) {
        this.capcity = capcity;
    }

    //带超时的获取
    public T poll(long timme, TimeUnit timeUnit){
        reentranlock.lock();
        try {
            long nanos = timeUnit.toNanos(timme);
            while (tDeque.isEmpty()){
                try {
                    if (nanos<0){
                        return null;
                    }
//                    返回的是剩余时间
                    nanos = nullwaitset.awaitNanos(nanos);


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T first = tDeque.getFirst();
            fullwaitset.signalAll();
            return first;
        }finally {
            reentranlock.unlock();

        }
    };
//    阻塞获取
    public T take(){
        reentranlock.lock();
        try {
           while (tDeque.isEmpty()){
               try {
                   nullwaitset.await();
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
            T first = tDeque.getFirst();
            fullwaitset.signalAll();
           return first;
        }finally {
            reentranlock.unlock();

        }
    };
//    阻塞添加
    public void put(T element){
        reentranlock.lock();
        try {
            while (tDeque.size()==capcity){
                try {
                    fullwaitset.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            tDeque.addLast(element);
            nullwaitset.signalAll();
        }finally {
            reentranlock.unlock();
        }


    };

//    获取大小
    public int size(){
        reentranlock.lock();
        try {
            int size = tDeque.size();
            return size;
        }finally {
            reentranlock.unlock();
        }
    };
}