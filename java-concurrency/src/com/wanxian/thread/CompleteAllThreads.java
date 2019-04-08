package com.wanxian.thread;

public class CompleteAllThreads {
    public static void main(String[] args) {
        //main ->子线程
        Thread t1 = new Thread(CompleteAllThreads::action, "t1");
        Thread t2 = new Thread(CompleteAllThreads::action, "t2");
        Thread t3 = new Thread(CompleteAllThreads::action, "t3");
        //不确定是否都调用start()
        t1.start();
        t2.start();
        t3.start();
        Thread mainThread = Thread.currentThread();
        //获取main线程组
        ThreadGroup threadGroup = mainThread.getThreadGroup();
        //活跃的线程数
        int count = threadGroup.activeCount();//活跃数量
        Thread[] threads = new Thread[count];
        //把活跃的线程复制  threads 数组
        threadGroup.enumerate(threads, true);
        for (Thread thread :
                threads) {
            System.out.printf("当前活跃线程：%s \n", thread.getName());
        }
    }

    private static void action() {
        System.out.printf("线程[%s] 正在执行......\n", Thread.currentThread().getName());
    }
}
