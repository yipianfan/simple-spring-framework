package com.simple.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * java实例化对象的方式.
 */
public class CoffeeTest {

    public static void main(String[] args) throws Exception {
        Coffee coffee1 = new Coffee();
        System.out.println("coffee1: " + coffee1);
        System.out.println();

        Coffee coffee2 = Coffee.class.newInstance();
        System.out.println("coffee2: " + coffee2);
        System.out.println();

        // 这种方式可以绕过构造函数是private的情况
        Coffee coffee3 = Coffee.class.getDeclaredConstructor().newInstance();
        System.out.println("coffee3: " + coffee3);
        System.out.println();

        // 通过反序列化的方式构造实例,这种方式不会调用构造函数
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:/Coffee.data"));
        out.writeObject(coffee1);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:/Coffee.data"));
        Coffee coffee4 = (Coffee) in.readObject();
        System.out.println("coffee4: " + coffee4);
    }
}