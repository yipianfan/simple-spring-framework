package com.simple.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(1203));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 将Channel注册到Selector上,并监听OP_ACCEPT事件

        while (true) {
            selector.select(); // 监听所有Channel,并返回就绪的事件个数
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {  //处理连接事件
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("client:" + socketChannel.getLocalAddress() + " is connect");
                    socketChannel.register(selector, SelectionKey.OP_READ); //注册客户端读取事件到selector
                } else if (key.isReadable()) {  //处理读取事件
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.read(buffer);
                    System.out.println("client:" + socketChannel.getLocalAddress() + " send " + new String(buffer.array()));
                }
                iterator.remove(); // 事件处理完毕,要记得清除
            }
        }
    }
}