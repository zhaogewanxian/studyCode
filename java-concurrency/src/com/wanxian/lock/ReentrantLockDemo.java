package com.wanxian.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        doAction(ReentrantLockDemo::action1);
    }

    public static void action1() {
        doAction(ReentrantLockDemo::action2);
    }

    public static void action2() {
        doAction(ReentrantLockDemo::action3);
    }

    public static void action3() {
        System.out.printf("end");
    }

    private static void doAction(Runnable runnable) {
        try {
            lock.lock();
            runnable.run();
        } finally {
            lock.unlock();
        }
    }
}
