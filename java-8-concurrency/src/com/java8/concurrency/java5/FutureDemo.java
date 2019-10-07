package com.java8.concurrency.java5;

import java.util.concurrent.*;

/**
 * Callable 是有返回值的
 */
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello,World by Callable";
            }
        });
        //等待完成
        while (true) {
            if (future.isDone()) {
                break;
            }
        }
        //Future#get()会阻塞当前线程
        //抛出 J.U.C框架异常(InterruptedException等)就会阻塞
        String value = future.get();
        System.out.println(value);
        //为什么会有新的api出来
        executorService.shutdown();
    }
}
