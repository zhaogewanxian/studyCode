package com.java8.concurrency.before5;

/**
 * 可完成的 @see Runnable
 */
public class CompletableRunnableDemo {
    public static void main(String[] args) throws InterruptedException {
        CompletableRunnable completableRunnable = new CompletableRunnable();

        Thread thread = new Thread(completableRunnable, "thread");
        thread.start();
        //等待线程执行完毕
        thread.join();
        System.out.println("start.....");
        System.out.println("completableRunnable completed:" + completableRunnable.isCompleted());

    }

    public static class CompletableRunnable implements Runnable {
        private volatile boolean completed = false;

        @Override
        public void run() {
            System.out.printf("当前线程名称: %s\n", Thread.currentThread().getName());
            completed = true;
        }

        public boolean isCompleted() {
            return completed;
        }
    }
}
