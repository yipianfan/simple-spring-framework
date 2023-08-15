package com.simple.algorithm;

public class Reverse {

    /**
     * 输入数字,将数字倒着输出
     */
    public static String reverseNum(int n) {
        StringBuilder builder = new StringBuilder();
        if (n > 0) {
            while (n != 0) {
                builder.append(n % 10);
                n /= 10;
            }
            return builder.toString();
        }
        if (n < 0) {
            while (n != 0) {
                builder.append((n % 10) * -1);
                n /= 10;
            }
            return builder.toString();
        }
        return "0";
    }
}
