package com.example.ganesh.deep_android.dsa.arrays;

import java.util.Arrays;

/**
 * Created by Ganesh Padhayay on 22/06/21.
 */
public class PointsInsideCircle {

    public static void main(String[] args) {
        int[] ans = new PointsInsideCircle().countPoints(new int[][]{{1,3},{3,3},{5,3},{2,2}}, new int[][]{{2,3,1},{4,3,1},{1,1,2}});
        System.out.println(Arrays.toString(ans));
    }

    public int[] countPoints(int[][] points, int[][] queries) {
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int centerX = queries[i][0];
            int centerY = queries[i][1];
            int radius = queries[i][2];
            for (int[] point : points) {
                if (isInside(point[0], point[1], centerX, centerY, radius))
                    ans[i]++;
            }
        }
        return ans;
    }

    private static boolean isInside(int pointX, int pointY, int centerX, int centerY, int radius) {
        return Math.sqrt(Math.pow(Math.abs(pointX - centerX), 2) + Math.pow(Math.abs(pointY - centerY), 2)) <= radius;
    }
}
