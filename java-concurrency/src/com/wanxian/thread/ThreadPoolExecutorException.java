package com.wanxian.thread;

import java.util.concurrent.*;

public class ThreadPoolExecutorException {
    public static void main(String[] args) throws InterruptedException {
        //ExecutorService executorService = Executors.newFixedThreadPool(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.NANOSECONDS, new LinkedBlockingDeque<>()) {
            /**
             * 通过覆盖 {@link ThreadPoolExecutor#afterExecute(Runnable, Throwable)} 达到获取异常的信息
             * @param r
             * @param t
             */
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.printf("线程[%s]发生异常,详细信息是:%s\n", Thread.currentThread().getName(),
                        t.getMessage());
            }
        };
        executor.execute(() -> {
            throw new RuntimeException("数据达到阈值");
        });
        executor.awaitTermination(1, TimeUnit.SECONDS);
        executor.shutdown();//关闭线程池
    }
}
