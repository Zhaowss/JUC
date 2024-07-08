package org.example.test3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


@Slf4j
public class tt04 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        hello();
    }

    public static  void hello(){
        System.out.println("hello");
        hello2();
    }
    public static void hello2(){
        System.out.println("hello");
    }
}
