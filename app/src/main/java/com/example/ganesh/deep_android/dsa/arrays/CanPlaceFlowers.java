package com.example.ganesh.deep_android.dsa.arrays;

/**
 * Created by Ganesh Padhayay on 19/06/21.
 */
public class CanPlaceFlowers {
    public static void main(String[] args) {
        System.out.println(new CanPlaceFlowers().canPlaceFlowers(new int[]{0, 0, 1, 0, 0, 1, 0, 0}, 2));
    }

    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int flowerCount = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0) {
                if (i - 1 == -1) {
                    //first case
                    if (i + 1 == flowerbed.length) {
                        if (flowerbed[i] == 0) {
                            return n == 1 || n == 0;
                        } else {
                            return n == 0;
                        }
                    }
                    if (flowerbed[i + 1] == 0) {
                        flowerCount++;
                        flowerbed[i] = 1;
                        if (flowerCount == n)
                            return true;
                    }
                } else if (i + 1 == flowerbed.length) {
                    //last case
                    if (flowerbed[i - 1] == 0) {
                        flowerCount++;
                        flowerbed[i] = 1;
                        if (flowerCount == n)
                            return true;
                    }

                } else if (flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                    flowerCount++;
                    flowerbed[i] = 1;
                    if (flowerCount == n)
                        return true;
                }
            }
        }
        return n == 0;
    }
}
