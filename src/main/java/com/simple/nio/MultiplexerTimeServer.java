package com.simple.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

public class MultiplexerTimeServer implements Runnable {
    private ServerSocketChannel servChannel;
    private Selector selector;
    private volatile boolean stop;

    public static void main(String[] args) throws IOException {
        MultiplexerTimeServer server = new MultiplexerTimeServer(1203);
        new Thread(server, "server-001").start();
    }

    public MultiplexerTimeServer(int port) throws IOException {
        servChannel = ServerSocketChannel.open();
        servChannel.configureBlocking(false); // 设置为非阻塞模式
        servChannel.bind(new InetSocketAddress(port)); //绑定端口号

        selector = Selector.open();
        // ServerSocketChannel:在Selector上只会注册一个,并且只关心OP_ACCEPT,即只关心客户端的连接请求
        // SocketChannel:通常会在Selector上注册多个,因为通常会接收多个client的请求,就有对应数量的SocketChannel
        // SocketChannel要关心的操作有:CONNECT、READ、WRITE
        servChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("The Time Server is start in port: " + port);
    }

    public void stop() {
        stop = true;
    }

    public void run() {
        while (!stop) {
            try {
                // 无参数的select方法会一直阻塞,直到某个注册的Channel事件就绪,有参的select方法则会设置超时时间,超时后,阻塞解除,返回就绪事件的个数
                selector.select();

                // 用selector.selectedKeys()轮询所有的就绪事件
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The Time Server receive order: " + body);
                    String curTime = "QUERY TIME ORDER".equals(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc, curTime);
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                } else {
                    // 读到0字节，忽略
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}