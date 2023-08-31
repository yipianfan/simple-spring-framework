package com.simple.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe类总结
 *
 * @see sun.misc.Unsafe
 */
public class UnsafeUtil {

    /**
     * 当调用Unsafe.getUnsafe方法的类为启动类加载器,才可以调用成功,否则会抛出异常
     * <p>
     * 所以,在项目中要用Unsafe有以下两种方式:
     * 1.通过-Xbootclasspath/a
     * 2.通过反射获取theUnsafe字段来获取Unsafe实例
     */
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            // nothing.
        }
        return null;
    }
}
