package com.wanxian.thread;

public class ThreadExecution {
    public static void main(String[] args) throws InterruptedException {
        //threadJoinOneByOne();
        //threadLoop();
        //threadSleep();
        threadWait();
    }

    private static void threadSleep() throws InterruptedException {
        Thread t1 = new Thread(ThreadExecution::action, "t1");
        Thread t2 = new Thread(ThreadExecution::action, "t2");
        Thread t3 = new Thread(ThreadExecution::action, "t3");
        //start()为启动线程
        t1.start();
        while (t1.isAlive()) {
            t1.sleep(0);
        }

        t2.start();
        while (t2.isAlive()) {
            t2.sleep(0);
        }

        t3.start();
        while (t3.isAlive()) {
            t3.sleep(0);
        }

    }


    private static void threadLoop() {
        Thread t1 = new Thread(ThreadExecution::action, "t1");
        Thread t2 = new Thread(ThreadExecution::action, "t2");
        Thread t3 = new Thread(ThreadExecution::action, "t3");
        //start()为启动线程
        t1.start();
        while (t1.isAlive()) {
            //自旋 spin
        }

        t2.start();
        while (t2.isAlive()) {

        }

        t3.start();
        while (t3.isAlive()) {

        }
    }

    private static void threadJoinOneByOne() throws InterruptedException {
        Thread t1 = new Thread(ThreadExecution::action, "t1");
        Thread t2 = new Thread(ThreadExecution::action, "t2");
        Thread t3 = new Thread(ThreadExecution::action, "t3");
        //start()为启动线程
        t1.start();
        //join()控制线程必须执行完成
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();


    }


    private static void threadWait() {
        Thread t1 = new Thread(ThreadExecution::action, "t1");
        Thread t2 = new Thread(ThreadExecution::action, "t2");
        Thread t3 = new Thread(ThreadExecution::action, "t3");
        threadStartAndWait(t1);
        threadStartAndWait(t2);
        threadStartAndWait(t3);
    }

    private static void threadStartAndWait(Thread thread) {
        if (Thread.State.NEW.equals(thread.getState())) { //如果未启动
            thread.start();
        }
        while (thread.isAlive()) {
            synchronized (thread) {
                try {
                    thread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行......\n", Thread.currentThread().getName());
    }
}
