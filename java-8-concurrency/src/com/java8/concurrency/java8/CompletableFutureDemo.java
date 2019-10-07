package com.java8.concurrency.java8;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> completableFuture = new CompletableFuture<>();
//        //完成操作，可以由其他线程去完成
//        completableFuture.complete("Hello,World");
//        String value = completableFuture.get();
//        System.out.println(value);
//        //2.异步执行，阻塞操作
//        CompletableFuture asynccompletableFuture = CompletableFuture.runAsync(() -> {
//            System.out.println("Hello,World");
//        });
//        //仍然会阻塞
//        asynccompletableFuture.get();
//        System.out.println("start.....");
        //3.异步操作
//        CompletableFuture supplyAsynCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
//            @Override
//            public String get() {
//                return "Hello,World";
//            }
//        });
//        CompletableFuture<String> supplyAsynCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            return String.format("[Thread:%s],Hello,World", Thread.currentThread().getName());
//        });
//        String value = supplyAsynCompletableFuture.get();
//        System.out.println(value);
        //4.合并操作
        CompletableFuture combinedCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return String.format("[Thread:%s],Hello,World", Thread.currentThread().getName());
        }).thenApply(value -> {
            return value + " - 来自数据库";
        }).thenApply(value -> {
            return value + " at " + LocalDate.now();
        }).thenApply(value -> {
            System.out.println(value);
            return value;
        }).thenRun(() -> {
            //commit 操作
            System.out.println("操作完成");
        });
        while (!combinedCompletableFuture.isDone()) {

        }
        System.out.println("start.....");
    }
}
