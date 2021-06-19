package com.example.ganesh.deep_android.dsa.arrays;

/**
 * Created by Ganesh Padhayay on 19/06/21.
 */
public class ThirdMaxmimum {
    public static void main(String[] args) {
        System.out.println(new ThirdMaxmimum().thirdMax(new int[]{5, 2, 2}));
    }

    public int thirdMax(int[] nums) {
        Integer firstMaxima = null, secondMaxima = null, thirdMaxima = null;
        for (Integer num : nums) {
            if (num.equals(firstMaxima) || num.equals(secondMaxima) || num.equals(thirdMaxima))
                continue;

            if (firstMaxima == null || num > firstMaxima) {
                thirdMaxima = secondMaxima;
                secondMaxima = firstMaxima;
                firstMaxima = num;
            } else if (secondMaxima == null || num > secondMaxima) {
                thirdMaxima = secondMaxima;
                secondMaxima = num;
            } else if (thirdMaxima == null || num > thirdMaxima) {
                thirdMaxima = num;
            }
        }
        if (thirdMaxima == null)
            return firstMaxima;
        else return thirdMaxima;
    }
}
