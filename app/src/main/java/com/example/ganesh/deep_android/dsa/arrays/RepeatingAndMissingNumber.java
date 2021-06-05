package com.example.ganesh.deep_android.dsa.arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ganesh Padhayay on 05/06/21.
 */
public class RepeatingAndMissingNumber {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 4, 5, 6, 7, 8, 10};
//        findRepeatingAndMissingNumber(arr);
        findRepeatingAndMissingNumberEfficiently(arr);
    }

    //TC: O(N), SC: O(N)
    private static void findRepeatingAndMissingNumberEfficiently(int[] arr) {
        //our two values
        int x = 0, y = 0;
        boolean ifXIsRepeating = false;

        //iterator
        int i;

        //find xor of those two values
        int xor1 = arr[0];
        for (i = 1; i < arr.length; i++) {
            xor1 = xor1 ^ arr[i];
        }
        for (i = 1; i <= arr.length; i++) {
            xor1 = xor1 ^ i;
        }

        //find set bit number in this xor value
        int setBitNumber;
        setBitNumber = xor1 & -xor1;

        //divide the given array in two buckets and keep taking xor of these values
        for (i = 0; i < arr.length; i++) {
            if ((arr[i] & setBitNumber) != 0)
                /* arr[i] belongs to first set */
                x = x ^ arr[i];
            else
                /* arr[i] belongs to second set*/
                y = y ^ arr[i];
        }

        //finally divide the values from 1 to n in these buckets and keep taking xor of these values
        for (i = 1; i <= arr.length; i++) {
            if ((i & setBitNumber) != 0)
                /* i belongs to first set */
                x = x ^ i;
            else
                /* i belongs to second set*/
                y = y ^ i;
        }

        //now, we need to find which value is repeating and which one is missing so iterate the arr one final time
        for (i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                ifXIsRepeating = true;
                break;
            }
        }

        //print repeating and missing number
        if (ifXIsRepeating) {
            System.out.println("Repeating = " + x);
            System.out.println("Missing = " + y);
        } else {
            System.out.println("Repeating = " + y);
            System.out.println("Missing = " + x);
        }
    }

    //TC: O(N), SC: O(N)
    private static void findRepeatingAndMissingNumber(int[] arr) {
        Map<Integer, Boolean> numberMap = new HashMap<>();
        int max = arr.length;
        for (Integer i : arr) {
            if (numberMap.get(i) == null) {
                numberMap.put(i, true);
            } else {
                System.out.println("Repeating = " + i);
            }
        }
        for (int i = 1; i <= max; i++) {
            if (numberMap.get(i) == null) {
                System.out.println("Missing = " + i);
            }
        }
    }
}
