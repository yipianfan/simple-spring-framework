package com.simple.algorithm.sort;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static int[] sort(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i; j < numbers.length - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    int a = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = a;
                }
            }
        }
        return numbers;
    }
}