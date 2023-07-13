package com.simple.thread;

public class ThreadDemo {

    /**
     * 线程按启动顺序执行方案,join方法的原理本质上是调用wait方法.
     */
    void demo1() throws InterruptedException {
        Thread t1 = new Thread(() -> System.out.println("#1"));
        Thread t2 = new Thread(() -> System.out.println("#2"));
        Thread t3 = new Thread(() -> System.out.println("#3"));
        t1.start();
        t2.start();
        t3.start();

        // 判断所有子线程都执行完的方案,也可以用join方法
        while (t1.isAlive() || t2.isAlive() || t3.isAlive()) ;
        System.out.println();

        //确保线程启动顺序与执行顺序一样的方案.
        Thread t4 = new Thread(() -> System.out.println("#4"));
        Thread t5 = new Thread(() -> System.out.println("#5"));
        Thread t6 = new Thread(() -> System.out.println("#6"));
        t4.start();
        t4.join();
        t5.start();
        t5.join();
        t6.start();
    }

    /**
     * Thread.sleep只会让线程等待,不会让出锁,也不用在同步方法或同步块中调用.
     */
    synchronized void demo2() {
        System.out.println("demo2() called. Thread name is: " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程结束睡眠");
    }

    /**
     * wait、notify 方法一定要在同步方法或同步块中调用.
     * 调用wait方法期间,线程会释放持有的锁.
     */
    synchronized void demo3() {
        System.out.println("demo3() called. Thread name is: " + Thread.currentThread().getName());
        try {
            wait(2000);
            for (int i = 0; i < 5; i++) {
                System.out.println(i + " -> " + Thread.currentThread().getName());
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "线程结束");
    }
}