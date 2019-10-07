package com.java8.concurrency.java5;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.printf("[Thread:%s],Hello,World", Thread.currentThread().getName());
            }
        });
        //关闭线程池？java 5 开始提供自动关闭 io/jdbc,为什么线程池没有提供自动关闭
        if (executor instanceof ExecutorService) {
            ExecutorService executorService = ExecutorService.class.cast(executor);
            executorService.shutdown();
        }
    }
}
