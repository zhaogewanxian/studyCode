package com.wanxian.thread;

import java.util.concurrent.*;

public class ThreadDemo3 implements Callable<String> {
    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName() + "call";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ThreadDemo3 t1 = new ThreadDemo3();
        ThreadDemo3 t2 = new ThreadDemo3();
        Future<String> future1 = executorService.submit(t1);
        Future<String> future2 = executorService.submit(t2);
        System.out.println(future1.get());
        System.out.println(future2.get());
        executorService.shutdown();

    }
}
