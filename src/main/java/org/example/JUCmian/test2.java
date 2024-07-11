package org.example.JUCmian;

import java.util.concurrent.*;

/**
 * @ClassNAME test2
 * @Description TODO
 * @Author zhaoweishan
 * @Date 2024/7/11 08:41
 * @Version 1.0
 */
public class test2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> submit = executorService.submit(new Callable<String>(

        ) {
            @Override
            public String call() throws Exception {
                Thread.sleep(1);
                return "hello";
            }
        });
        String s = submit.get();
        System.out.println("主线程拿到数据"+s);

    }
}
