package com.simple.nio;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Buffer中的3个概念：
 * capacity：Buffer底层数组的容量大小，是固定不变的
 */
public class NioDemo {

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("D:/opt/apache-maven-3.8.6/conf/settings.xml");

        // 一个Channel相当于一个连接,可以用它来连接到IO设备,如磁盘、网络
        // FileChannel.open方法,默认文件打开的方式为可读,所以可以不用StandardOpenOption.READ
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            // buffer一开始为写模式
            ByteBuffer buf = ByteBuffer.allocate(10);

            StringBuilder config = new StringBuilder();
            while (channel.read(buf) > 0) { // channel从文件中读数据,相当于向buffer里写数据
                buf.flip(); // 将缓冲区切换为读模式
                while (buf.position() < buf.limit()) {
                    config.append((char) buf.get());
                }
                // 将缓冲区切换为写模式
                buf.clear();
            }
            System.out.println(config);
        }
    }
}
