package com.simple.thread;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
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
}