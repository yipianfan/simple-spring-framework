package com.simple.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 改进传统BIO模式,使用线程池,避免开辟过多的线程.
 */
public class TimeServer2 {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ExecutorService executorService = createThreadPool(100, 100);

        // 创建服务端socket
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("The Time Server is started in port " + port);

            // 监听客户端socket,每进一个客户端socket,开启一个新的线程
            while (true) {
                Socket socket = server.accept(); // 会造成阻塞,直到有客户端连接
                executorService.execute(new TimeServerHandler(socket));
            }
        }
    }

    static ExecutorService createThreadPool(int maxPoolSize, int queueSize) {
        int coreSize = Runtime.getRuntime().availableProcessors();
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueSize);
        ExecutorService executor = new ThreadPoolExecutor(coreSize, maxPoolSize, 120L, TimeUnit.SECONDS, queue);
        return executor;
    }
}
