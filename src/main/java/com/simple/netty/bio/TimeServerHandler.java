package com.simple.netty.bio;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class TimeServerHandler implements Runnable {
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (LineNumberReader in = new LineNumberReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {// 这里必须为true,否则客户端收不到消息

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String line = in.readLine();
            while (!"EOF".equals(line)) {// 用 null != line 判断会有socket重置异常
                System.out.println("Receive order is: " + line);
                String curTime = "QUERY TIME ORDER".equals(line) ? sdf.format(System.currentTimeMillis()) : "BAD ORDER";
                pw.println(curTime); // 返回给客户端的数据
                line = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}