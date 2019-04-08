package com.wanxian.thread;

public class ThreadException {
    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> { //
            System.out.printf("线程[%s]发生异常,详细信息是:%s\n", thread.getName(), throwable.getMessage());
        });
        Thread thread = new Thread(() -> {
            throw new RuntimeException("数据达到阈值");
        }, "t1");
        thread.start();
        thread.join();
        // Java Thread 是一个包装，它由 GC 做垃圾回收
        // JVM Thread 可能是一个 OS Thread，JVM 管理，
        // 当线程执行完毕（正常或者异常）
        System.out.println(thread.isAlive());
    }
}
