package org.example.test3;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class tt02 {
    public static void main(String[] args) {


        myThread myThread = new myThread();
        myThread.setName("线程0");
        myThread.start();

        log.info("main thread"+Thread.currentThread().getId());

    }

    public static class  myThread extends Thread{
        @Override
        public void run() {
            log.info("hello"+this.getId()+this.getName());
        }
    }
}
