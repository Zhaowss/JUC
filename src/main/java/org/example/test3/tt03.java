package org.example.test3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


@Slf4j
public class tt03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Callable<Object> oneCallable = new Tickets<Object>();
        FutureTask<Object> oneTask = new FutureTask<Object>(oneCallable);

        Thread t = new Thread(oneTask);

        System.out.println(Thread.currentThread().getName());

        t.start();
        Object x=oneTask.get();

        System.out.println("结束");

    }

        static  class  Tickets<Object> implements Callable<Object>{

            //重写call方法
            @Override
            public Object call() throws Exception {
                // TODO Auto-generated method stub
                System.out.println(Thread.currentThread().getName()+"-->我是通过实现Callable接口通过FutureTask包装器来实现的线程");
                Thread.sleep(10000);
                return (Object) String.valueOf(1);
        }
    }
}
