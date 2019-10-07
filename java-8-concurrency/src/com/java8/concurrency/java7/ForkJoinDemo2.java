package com.java8.concurrency.java7;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAdder;

/**
 * ForkJoin示例
 */
public class ForkJoinDemo2 {
    public static void main(String[] args) {
        //并行:多核心参与
        //并发:一同参与
        //ForkJoinPool 线程池
        System.out.println("当前 ForkJoin 线程池 并行数量:" + ForkJoinPool.commonPool().getParallelism());
        System.out.println("当前cpu 核心数:" + Runtime.getRuntime().availableProcessors());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        LongAdder longAdder = new LongAdder();
        AddTask addTask = new AddTask(list, longAdder);
        forkJoinPool.invoke(addTask);
        System.out.println(list + "累加结果:" + longAdder);
    }

    private static class AddTask extends RecursiveAction {
        private final List<Integer> list;
        private final LongAdder longAdder;

        private AddTask(List<Integer> list, LongAdder longAdder) {
            this.list = list;
            this.longAdder = longAdder;
        }

        @Override
        protected void compute() {
            int size = list.size();
            if (size > 1) {
                //二分
                int parts = size / 2;
                //上部分
                List<Integer> leftList = list.subList(0, parts);
                AddTask leftTask = new AddTask(leftList, longAdder);

                //下部分
                List<Integer> rightList = list.subList(parts, size);
                AddTask rightTask = new AddTask(rightList, longAdder);
                invokeAll(leftTask, rightTask);

            } else {
                if (size == 0) {
                    return;
                }
                Integer value = list.get(0);
                //累加
                longAdder.add(value.longValue());
            }


        }
    }

}
