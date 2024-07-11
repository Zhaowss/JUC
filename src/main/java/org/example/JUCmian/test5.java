package org.example.JUCmian;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @ClassNAME test5
 * @Description AQS 实现同步器并实现对应的不可重入锁
 * @Author zhaoweishan
 * @Date 2024/7/11 09:59
 * @Version 1.0
 */
@Slf4j
public class test5 {

    public static void main(String[] args) {
        mylock mylock=new mylock();
        new Thread(()->{
           try {
               mylock.lock();
               log.info("加锁成功1");
               Thread.sleep(2000);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           } finally {
               log.info("unlock解锁中---");
               mylock.unlock();
           }
        },"t1").start();
        new Thread(()->{
            try {
                mylock.lock();
                log.info("加锁成功2");
            }finally {
                log.info("unlock解锁中---");
                mylock.unlock();
            }
        },"t2").start();

    }
}

class  mylock implements Lock{
    //    实现独占锁
    class  Mysync extends AbstractQueuedSynchronizer{

        @Override
        protected boolean tryAcquire(int arg) {
           if(compareAndSetState(0,1)){
//               加锁成功
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
           }
           return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }
    Mysync mx=new Mysync();

    @Override
    public void lock() {
        mx.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
     mx.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {

        return mx.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return mx.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
      mx.release(1);
    }

    @Override
    public Condition newCondition() {
        return mx.newCondition();
    }
}
