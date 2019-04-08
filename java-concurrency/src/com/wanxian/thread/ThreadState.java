package com.wanxian.thread;

public class ThreadState {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {//new Runnable(){public void run(){}}
            System.out.printf("线程[%s] 正在执行...\n", Thread.currentThread().getName());
        }, "t1");
        //启动线程
        thread.start();
        System.out.printf("线程[%s] 是否还存活:%s\n", thread.getName(), thread.isAlive());
        // 在 Java 中，执行线程 Java 是没有办法销毁它的，
        // 但是当 Thread.isAlive() 返回 false 时，实际底层的 Thread 已经被销毁了
    }
}
