package com.simple.netty.bio;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * TimeServerHandler的v2版本,当读取客户端流时,用null判断,手动捕获异常.
 */
public class TimeServerHandlerV2 implements Runnable {
    private final Socket socket;

    public TimeServerHandlerV2(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter pw = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String line = in.readLine();
            while (line != null) { // 用这种方式判断,会抛出socket异常
                String curTime = "QUERY TIME ORDER".equals(line) ? sdf.format(System.currentTimeMillis()) : "BAD ORDER";
                pw.println(curTime);
                line = in.readLine();
            }
        } catch (Exception e) { // 为什么不用finally块来关闭流,因为上面的while语句一定会抛出socket异常
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
    }
}
