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

    /**
     * Unsafe类提供了以下3个方法用于CAS操作,CAS操作依赖于CPU的指令
     *
     * @param obj      修改field的所属对象
     * @param offset   field的偏移量
     * @param expected 期望值
     * @param update   更新值
     */
    public final native boolean compareAndSwapObject(Object obj, long offset, Object expected, Object update);

    public final native boolean compareAndSwapInt(Object obj, long offset, int expected, int update);

    public final native boolean compareAndSwapLong(Object obj, long offset, long expected, long update);


    // Unsafe类定义了的数组的相关操作方法

    /**
     * @param arrayObj 要使用的数组对象
     * @return 返回数组中index为0的内存偏移地址
     */
    public native int arrayBaseOffset(Class<?> arrayObj);

    /**
     * @param arrayClass 数组的class对象,如int[].class
     * @return 返回数组中一个元素所占用的空间大小
     */
    public native int arrayIndexScale(Class<?> arrayClass);
}
