package com.example.ganesh.deep_android.dsa.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ganesh Padhayay on 19/06/21.
 */
public class BuddyStrings {

    public static void main(String[] args) {
        BuddyStrings buddyStrings = new BuddyStrings();
        System.out.println(buddyStrings.buddyStrings("aa", "aa"));
    }

    public boolean buddyStrings(String s, String goal) {
        if (s.length() == 0 || goal.length() == 0) return false;
        if (s.length() != goal.length()) return false;

        int mismatchCount = 0;
        char s1 = '1', s2 = '1', goal1 = '1', goal2 = '1';
        Set<Character> set = new HashSet<>();

        if (s.equals(goal)) {
            //need to check if any element comes more than once, can use a set
            for (int i = 0; i < s.length(); i++) {
                if (set.contains(s.charAt(i)))
                    return true;
                else set.add(s.charAt(i));
            }
            return false;
        } else {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != goal.charAt(i)) {
                    mismatchCount++;
                    if (mismatchCount == 1) {
                        s1 = s.charAt(i);
                        goal1 = goal.charAt(i);
                    } else if (mismatchCount == 2) {
                        s2 = s.charAt(i);
                        goal2 = goal.charAt(i);
                    } else {
                        return false;
                    }
                }
            }
        }
        return s1 == goal2 && s2 == goal1;
    }
}
