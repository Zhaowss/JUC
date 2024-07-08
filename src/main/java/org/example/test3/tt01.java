package org.example.test3;

import lombok.extern.slf4j.Slf4j;

import java.util.TreeMap;


@Slf4j
public class tt01 {
    public static void main(String[] args) {

        Thread thread=new Thread(){
            @Override
            public void run() {
                log.info("hello"+Thread.currentThread().getId());
            }
        };
        thread.start();

        log.info("main thread"+Thread.currentThread().getId());

    }
}
