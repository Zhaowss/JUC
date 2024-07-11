package org.example.JUCmian;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassNAME test2
 * @Description TODO
 * @Author zhaoweishan
 * @Date 2024/7/11 08:41
 * @Version 1.0
 */

@Slf4j
public class test4 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ArrayList<Callable<String>> collections=new ArrayList<Callable<String>>();
        for (int i = 0; i < 10; i++) {
            int j=i;
           Callable<String> callable=new Callable<String>() {
               @Override
               public String call() throws Exception {
                   log.info("第{}个任务"+j);
                   return String.valueOf(j);
               }
           };
           collections.add(callable);

        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String s = executorService.invokeAny(collections);
        System.out.println(s);
    }
    }

