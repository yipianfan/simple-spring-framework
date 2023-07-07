package com.simple.common;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

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
        System.out.println();

        // 通过第三方架包,这种方式也不会调用构造函数
        Objenesis ob = new ObjenesisStd();
        ObjectInstantiator<Coffee> ins = ob.getInstantiatorOf(Coffee.class);
        Coffee coffee5 = ins.newInstance();
        System.out.println("name: " + coffee5.name);
        System.out.println("price: " + coffee5.price);
        System.out.println("capacity: " + coffee5.capacity);
        System.out.println("color: " + coffee5.color);

        System.out.println("density: " + coffee5.density);
        System.out.println("dateFormat: " + coffee5.dateFormat);
        System.out.println("date: " + coffee5.date);
        System.out.println("producingArea: " + coffee5.producingArea);
    }
}