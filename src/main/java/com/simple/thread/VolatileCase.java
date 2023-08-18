package com.simple.thread;

public class VolatileCase {
    // instance变量必须为volatile,确保instance的可见性,即每次修改instance变量后,立刻更新到主存,每次读数据也是从主存中读
    private static VolatileCase instance;
    private static boolean isStop;

    private VolatileCase() {
    }

    public static VolatileCase getInstance() {
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
     * Thread 1 将永远不会终止,因为isStop不是volatile
     *
     * @throws InterruptedException
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
