package com.wanxian.thread;

public class StopThread {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 开关判断
         */
        Action action = new Action();
        Thread thread = new Thread(action, "t1");
        thread.start();
        action.setStopped(true);
        thread.join();

        /**
         * 2.中断操作
         */
        Thread t2 = new Thread(() -> {
            if (!Thread.currentThread().isInterrupted()) { //如果线程不是阻塞
                action();
            }
        }, "t2");
        t2.start();//启动线程
        //中断操作，设置状态，并非终止线程
        t2.interrupt();
        t2.join();
    }

    /**
     * 加开关控制
     */
    private static class Action implements Runnable {
        ///确保可见性
        private volatile boolean stopped = false;

        @Override
        public void run() {
            if (!stopped) {
                action();

            }
        }

        public void setStopped(boolean stopped) {
            this.stopped = stopped;
        }
    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行......\n", Thread.currentThread().getName());

    }
}
