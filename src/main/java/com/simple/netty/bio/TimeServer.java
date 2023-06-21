package com.simple.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO模型,即同步阻塞模型.
 * BIO的缺点: 每一个连接就要在服务端开启一个新的线程,在大型互联网公司,有成千上万个客户端连接时,服务器的资源将严重不足.
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
