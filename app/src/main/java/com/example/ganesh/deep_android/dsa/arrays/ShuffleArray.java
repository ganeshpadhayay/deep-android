package com.example.ganesh.deep_android.dsa.arrays;

import java.util.Arrays;

/**
 * Created by Ganesh Padhayay on 19/06/21.
 */
public class ShuffleArray {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new ShuffleArray().shuffle(new int[]{2, 5, 1, 3, 4, 7}, 3)));
    }

    public int[] shuffle(int[] nums, int n) {
        int[] ans = new int[nums.length];
        int k = 0;
        for (int i = 0; i < n; i++) {
            ans[k] = nums[i];
            ans[k + 1] = nums[n + i];
            k = k + 2;
        }
        return ans;
    }
}