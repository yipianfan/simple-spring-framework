package com.simple.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * HTTP服务,本质上还是一个TCP服务
 * <br/>
 * HTTP请求报文结构分为3部分: 报文首部(请求行+各种首部)、空行(CR + LF,即,回车符+换行符)、报文主体
 * <br/>
 * HTTP响应报文结构分为3部分: 报文首部(状态行+各种首部)、空行(CR + LF,即,回车符+换行符)、报文主体
 * <br/>
 * 行与行之间用 CR + LF 分隔
 */
public class GoodsTCPServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1203);
        System.out.println("Server is started...");
        while (true) {
            Socket socket = serverSocket.accept(); // 应用将会被阻塞,直到有请求进来
            System.out.println("Connected from " + socket.getRemoteSocketAddress());

            // 每个请求建立一个线程处理,这也就是传统BIO性能差的原因之一
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                    String requestLine = reader.readLine(); // 读取请求行
                    System.out.println("Request line is: " + requestLine);

                    // 读取所有请求报文中的首部
                    for (; ; ) {
                        String header = reader.readLine();
                        if (header.isEmpty()) break;
                        System.out.println(header);
                    }

                    String body = "<html><head><title>Welcome</title></head><body>Happy every day</body></html>";
                    writer.write("HTTP/1.1 200 OK"); // 写入状态行
                    writer.write("Content-Length: " + body.length() + "\r\n");
                    writer.write("Content-Type: text/html\r\n");
                    writer.write("\r\n"); // 空行,下面就是响应报文的主体
                    writer.write(body);
                    writer.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }).start();
        }
    }
}
