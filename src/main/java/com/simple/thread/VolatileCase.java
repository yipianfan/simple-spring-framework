package com.simple.thread;

/**
 * volatile关键字有两个作用: 1.保证变量的可见性  2.禁止指令的重排性
 */
public class VolatileCase {
    // instance变量必须为volatile,确保不会发性指令重排
    private static VolatileCase instance;
    private static boolean isStop;

    private VolatileCase() {
    }

    /**
     * 禁止指令重排案例
     * instance = new VolatileCase() 语句大概包含了以下3条指令
     * 1.为对象分配内存空间
     * 2.初始化对象,即为对象填充数据
     * 3.将对象内存地址指向到instance变量
     */
    public static VolatileCase getInstance() {
        /*为什么instance变量要为volatile,原因就是在这里,如果指令发生重排,得到的instance将是一个没有初始化的对象,
          所以双重检测造成的问题,是第一个 "instance == null"语句造成的,而不是第二个"instance == null"语句造成的*/
        if (instance == null) {
            synchronized (VolatileCase.class) {
                if (instance == null) {
                    instance = new VolatileCase();
                }
            }
        }
        return instance;
    }

    /**
     * 可见性案例
     * Thread 1 将永远不会终止,因为isStop不是volatile
     */
    public static void test() throws InterruptedException {
        new Thread(() -> {
            while (!isStop) ;
            System.out.println("Thread 1 exit.");
        }).start();
        Thread.sleep(200);
        new Thread(() -> {
            System.out.println("Thread 2 start");
            isStop = true;
            System.out.println("Thread 2 exit.");
        }).start();
    }
}
