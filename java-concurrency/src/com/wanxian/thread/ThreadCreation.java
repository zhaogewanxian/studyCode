package com.wanxian.thread;

public class ThreadCreation {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
        }, "main子线程-1"); //创建线程只有这种方式，运行线程方式1.implements Runnable或者2.extends Thread
    }

    /**
     * 不推荐自定义扩展Thread
     */
    private static class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }


}
