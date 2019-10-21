package com.winstar;


public class CalucatorTest {


    public static void main(String[] args) {
        int[] arrays = new int[]{1, 2, 3, 4, 5, 6, 9, 12, 18, 19, 19, 45};
        System.out.println(binarySearch(arrays, 19));
    }


    /**
     * 二分查找法
     */
    private static int binarySearch(int[] arrays, int targetNum) {
        int start = 0;
        int end = arrays.length - 1;
        while (start <= end) {
            int mid = start + end / 2;
            if (arrays[mid] == targetNum) {
                return mid;
            } else if (targetNum > arrays[mid]) {
                start = mid + 1;
            } else if (targetNum < arrays[mid]) {
                end = mid - 1;
            }
        }
        return -1;
    }


}
