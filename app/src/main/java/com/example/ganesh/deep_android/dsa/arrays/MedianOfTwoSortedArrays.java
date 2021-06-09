package com.example.ganesh.deep_android.dsa.arrays;

/**
 * Created by Ganesh Padhayay on 09/06/21.
 */
public class MedianOfTwoSortedArrays {

    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 33, 4, 3, 2};
        int[] arr2 = new int[]{4, 5, 6, 7, 8, 9, 10, 2000};
        System.out.println(findMedianSortedArrays(arr1, arr2));
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums2.length < nums1.length) return findMedianSortedArrays(nums2, nums1);
        int n1 = nums1.length;
        int n2 = nums2.length;
        int low = 0, high = n1;

        while (low <= high) {
            int cut1 = (low + high) >> 1;
            int cut2 = (n1 + n2 + 1) / 2 - cut1;

            int left1 = cut1 == 0 ? Integer.MIN_VALUE : nums1[cut1 - 1];
            int left2 = cut2 == 0 ? Integer.MIN_VALUE : nums2[cut2 - 1];

            int right1 = cut1 == n1 ? Integer.MAX_VALUE : nums1[cut1];
            int right2 = cut2 == n2 ? Integer.MAX_VALUE : nums2[cut2];


            if (left1 <= right2 && left2 <= right1) {
                if ((n1 + n2) % 2 == 0)
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                else
                    return Math.max(left1, left2);
            } else if (left1 > right2) {
                high = cut1 - 1;
            } else {
                low = cut1 + 1;
            }
        }
        return 0.0;
    }
}
