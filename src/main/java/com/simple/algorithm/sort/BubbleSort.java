package com.simple.algorithm.sort;

/**
 * 冒泡排序
 * 冒泡排序是稳定的算法,它满足稳定算法的定义。 算法稳定性: 假设在数列中存在a[i]=a[j],若在排序之前a[i]在a[j]前面,并且排序之后,a[i]仍然在a[j]前面
 */
public class BubbleSort {

    public static int[] sort(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length - i - 1; j++) {
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