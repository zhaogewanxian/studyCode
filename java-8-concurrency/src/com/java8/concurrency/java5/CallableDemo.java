package com.java8.concurrency.java5;

import java.util.concurrent.*;

/**
 * Callable 是有返回值的
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello,World by Callable";
            }
        });
        String value = future.get();
        System.out.println(value);
        //为什么会有新的api出来
        executorService.shutdown();
    }
}
