package com.example.ganesh.deep_android.dsa.arrays;

/**
 * Created by Ganesh Padhayay on 19/06/21.
 */
public class RunningSumOf1DArray {
    public int[] runningSum(int[] nums) {
        int[] ans = new int[nums.length];
        ans[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            ans[i] = nums[i] + ans[i - 1];
        }
        return ans;
    }
}
