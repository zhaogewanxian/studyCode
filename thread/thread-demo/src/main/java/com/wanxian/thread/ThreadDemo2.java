package com.wanxian.thread;

public class ThreadDemo2 implements Runnable {
    @Override
    public void run() {
        System.out.printf("线程启动");
    }

    public static void main(String[] args) {
        Thread thread1 = new ThreadDemo1();
        thread1.start();
    }
}
