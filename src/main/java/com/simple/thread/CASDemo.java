package com.simple.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    private volatile int i;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    private void add() {
        while (true) {
            if (atomicInteger.compareAndSet(0, 1)) {
                i++;
                break;
            }
        }
        atomicInteger.getAndDecrement();
    }

    /**
     * 用AtomicInteger实现一个线程安全的自增操作
     */
    public int multiThreadAdd(int count) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(count);
        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < count; i++) {
            pool.execute(this::add);
            latch.countDown();
        }
        latch.await();
        pool.shutdown();
        while (!pool.isTerminated()) ;
        return i;
    }
}
