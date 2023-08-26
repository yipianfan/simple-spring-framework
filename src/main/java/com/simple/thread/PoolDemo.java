package com.simple.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池相关方法总结
 */
public class PoolDemo {
    private int i = 0;
    private int count = 1000;
    private ExecutorService pool = Executors.newFixedThreadPool(4);
    private CountDownLatch countDownLatch = new CountDownLatch(count);

    public void shutdownDemo() throws InterruptedException {
        for (int i = 0; i < count; i++) {
            // 用execute方法提交任务
            pool.execute(() -> {
                add();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown(); //提交任务完成后,减1
        }
        countDownLatch.await();
        System.out.println("所有任务已提交...");
        pool.shutdown(); // 关闭线程池,已提交的任务不受影响,不再接收新的任务
        System.out.println("已调用shutdown方法...");

        // isTerminated方法判断线程池中的任务是否已经执行完,执行完返回true,否则返回false
        // 当然要先调用shutdown方法或shutdownNow方法,否则isTerminated方法永远不会返回true
        while (!pool.isTerminated()) ;
        System.out.println(i);
    }

    public void shutdownNowDemo() throws InterruptedException {
        for (int i = 0; i < count; i++) {
            // 用execute方法提交任务
            pool.execute(() -> {
                add();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown(); //提交任务完成后,减1
        }
        countDownLatch.await();
        System.out.println("所有任务已提交...");
        pool.shutdownNow(); // 尝试终止正在执行任务,不再接受新的任务,终止等待中的任务
        System.out.println("已调用shutdownNow方法...");

        while (!pool.isTerminated()) ;
        System.out.println(i);
    }

    private synchronized int add() {
        return i++;
    }
}
