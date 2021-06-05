package com.example.ganesh.deep_android.dsa.arrays;

/**
 * Created by Ganesh Padhayay on 05/06/21.
 */
public class Sort0s1s {
    public static void main(String[] args) {
        int[] arr = new int[]{0, 1, 0, 1, 0, 0, 0, 1, 1};
//        arr = sortCounting(arr);
        arr = sortEfficiently(arr);
        for (int value : arr) {
            System.out.println(value);
        }
    }

    //TC: O(N), SC: O(1)
    private static int[] sortEfficiently(int[] arr) {
        int low = 0, high = arr.length - 1;
        while (low < high) {
            if (arr[low] == 0 && arr[high] == 1) {
                low++;
                high--;
            } else if (arr[low] == 0 && arr[high] == 0) {
                low++;
            } else if (arr[low] == 1 && arr[high] == 0) {
                int temp = arr[low];
                arr[low] = arr[high];
                arr[high] = temp;
                low++;
                high--;
            } else {
                high--;
            }
        }
        return arr;
    }

    //TC: O(2N), SC: O(1)
    private static int[] sortCounting(int[] arr) {
        int count0 = 0;
        for (int value : arr) {
            if (value == 0)
                count0++;
        }
        int j = 0;
        while (count0-- > 0) {
            arr[j] = 0;
            j++;
        }
        while (j < arr.length) {
            arr[j] = 1;
            j++;
        }
        return arr;
    }
}
