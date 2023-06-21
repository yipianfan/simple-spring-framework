package com.simple.netty.bio;

import java.io.*;
import java.net.Socket;

public class TimeClient {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 8080);
             PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             LineNumberReader in = new LineNumberReader(new InputStreamReader(socket.getInputStream()))) {

            // 向服务端发送数据
            pw.println("QUERY TIME ORDER");
            pw.println("EOF");

            // 接收服务端数据
            String response = in.readLine();
            System.out.println("客户端接收到服务端数据: " + response);
        }
    }
}