package com.simple.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * 记录面试过的算法题
 */
public class History {

    /**
     * 判断一个数是否是快乐数
     */
    public static boolean isHappy(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n必须为正整数");
        Set<Integer> set = new HashSet<>();
        while (n > 1) {
            if (!set.add(n))
                return false;
            int sum = 0;
            while (n > 0) {
                int tail = n % 10;
                sum += tail * tail;
                n /= 10;
            }
            n = sum;
        }
        return true;
    }
}
