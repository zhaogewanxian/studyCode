package com.wanxian.thread;

public class ThreadDemo1 extends Thread {
    @Override
    public void run() {
        System.out.printf("%s线程启动 %n", Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new ThreadDemo1();
        thread1.setName("t1");
        Thread thread2 = new ThreadDemo1();
        thread2.setName("t2");
        //保证顺序执行第一种方式  start()->join()
        //runByJoin(thread1, thread2);
        //保证顺序执行第二种方式 判断线程是否存活，存活继续空转，否则下一个线程运行
        runByIsAlive(thread1, thread2);
    }

    public static void runByJoin(Thread thread1, Thread thread2) throws InterruptedException {
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();

    }

    public static void runByIsAlive(Thread thread1, Thread thread2) {
        thread1.start();
        thread2.start();
        if (thread1.isAlive()) {

        }
        if (thread2.isAlive()) {

        }
    }


}
