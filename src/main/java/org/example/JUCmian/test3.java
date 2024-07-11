package org.example.JUCmian;

import java.util.concurrent.*;

/**
 * @ClassNAME test2
 * @Description TODO
 * @Author zhaoweishan
 * @Date 2024/7/11 08:41
 * @Version 1.0
 */
public class test3 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
           System.out.println("hello");
       });

    }
}
