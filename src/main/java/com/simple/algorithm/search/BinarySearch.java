package com.simple.algorithm.search;

public class BinarySearch {

    /**
     * 二分查找,输入的数组必须是一个已经排序的数组.
     *
     * @return 目标数字的索引, 找不到则返回-1
     */
    public static int search(int[] numbers, int target) {
        int start = 0, end = numbers.length - 1;

        while (start <= end) {
            int middle = (start + end) >> 1;
            if (target == numbers[middle])
                return middle;
            else if (target > numbers[middle])
                start = middle + 1;
            else
                end = middle - 1;
        }
        return -1;
    }
}