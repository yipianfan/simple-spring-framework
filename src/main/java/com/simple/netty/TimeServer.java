package com.simple.netty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO,即同步阻塞模型.
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;

        // 创建服务端socket
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("The Time Server is started in port " + port);

            // 监听客户端socket,每进一个客户端socket,开启一个新的线程
            while (true) {
                Socket socket = server.accept(); // 会造成阻塞,直到有客户端连接
                new Thread(new TimeServerHandler(socket)).start();
            }
        }
    }
}
