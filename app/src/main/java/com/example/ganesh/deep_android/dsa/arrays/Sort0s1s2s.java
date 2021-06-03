package com.example.ganesh.deep_android.dsa.arrays;

/**
 * Created by Ganesh Padhayay on 03/06/21.
 */
public class Sort0s1s2s {
    public static void main(String[] args) {
        int[] arr = new int[]{0, 1, 0, 2, 1, 2, 0, 0, 0, 1, 2, 2, 2, 1};
        arr = sortCounting(arr);
        arr = sortEfficiently(arr);
        for (int value : arr) {
            System.out.println(value);
        }
    }

    //TC: O(N), SC: O(1)
    private static int[] sortEfficiently(int[] arr) {
        int low = 0, mid = 0, high = arr.length - 1;
        while (mid <= high) {
            switch (arr[mid]) {
                case 0: {
                    //swap with low
                    int temp = arr[mid];
                    arr[mid] = arr[low];
                    arr[low] = temp;
                    low++;
                    mid++;
                    break;
                }
                case 1: {
                    mid++;
                    break;
                }
                case 2: {
                    //swap with high
                    int temp = arr[high];
                    arr[high] = arr[mid];
                    arr[mid] = temp;
                    high--;
                    break;
                }
            }
        }
        return arr;
    }

    //TC: O(2N), SC: O(1)
    private static int[] sortCounting(int[] arr) {
        int count0 = 0, count1 = 0;
        for (int value : arr) {
            if (value == 0)
                count0++;
            else if (value == 1)
                count1++;
        }
        int j = 0;
        while (count0-- > 0) {
            arr[j] = 0;
            j++;
        }
        while (count1-- > 0) {
            arr[j] = 1;
            j++;
        }
        while (j < arr.length) {
            arr[j] = 2;
            j++;
        }
        return arr;
    }
}
