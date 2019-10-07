package com.java8.concurrency.before5;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                action();
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            action();
        }, "thread2");
        //启动线程
        thread1.start();
        //等待线程执行完毕
        thread1.join();
        thread2.start();
        //等待线程执行完毕
        thread2.join();
        System.out.println("start.....");
    }

    private static void action() {
        System.out.printf("当前线程名称: %s", Thread.currentThread().getName());
    }

}