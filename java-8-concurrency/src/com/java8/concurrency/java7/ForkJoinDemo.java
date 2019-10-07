package com.java8.concurrency.java7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * ForkJoin示例
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        //并行:多核心参与
        //并发:一同参与
        //ForkJoinPool 线程池
        System.out.println("当前 ForkJoin 线程池 并行数量:" + ForkJoinPool.commonPool().getParallelism());
        System.out.println("当前cpu 核心数:" + Runtime.getRuntime().availableProcessors());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new RecursiveAction() {
            @Override
            protected void compute() {
                System.out.printf("[Thread:%s],Hello,World", Thread.currentThread().getName());
            }
        });
    }
}
