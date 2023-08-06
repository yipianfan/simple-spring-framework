package com.simple.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    private static final Lock lock = new ReentrantLock(); // 默认是非公平锁

    public static void main(String[] args) throws InterruptedException {
        int poolSize = 4;
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        CountDownLatch countDownLatch = new CountDownLatch(poolSize);
        for (int i = 0; i < poolSize; i++) {
            executorService.execute(LockDemo::printNumber);
            countDownLatch.countDown();
        }

        // 等所有线程都提交后,关闭线程池
        countDownLatch.await();
        executorService.shutdown();// 调用此方法后,不再接收新的任务,已接收的任务不受影响
    }

    static void printNumber() {
        lock.lock();

        // 在try finally 语句中执行业务代码，保证在出现异常时也能解锁
        try {
            for (int i = 0; i < 10; i++)
                System.out.print(i + " ");
            System.out.println();
        } finally {
            lock.unlock();
        }
    }
}
