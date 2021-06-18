package com.example.ganesh.deep_android.dsa.stackqueue;

import java.util.Stack;

/**
 * Created by Ganesh Padhayay on 19/06/21.
 */
public class ValidParentheses {

    public static void main(String[] args) {
        ValidParentheses validParentheses = new ValidParentheses();
        System.out.println(validParentheses.isValid("){"));
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (isLeftParentheses(s.charAt(i))) {
                //left, push in stack
                stack.push(s.charAt(i));
            } else {
                //right
                if (stack.isEmpty())
                    return false;
                if (isMatchingPair(s.charAt(i), stack.peek())) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static boolean isMatchingPair(char c1, char c2) {
        return ((c1 == '(' && c2 == ')')||(c1 == ')' && c2 == '(')) || ((c1 == '[' && c2 == ']') ||(c1 == ']' && c2 == '[') ) || ((c1 == '{' && c2 == '}') || (c1 == '}' && c2 == '{'));
    }

    public static boolean isLeftParentheses(char c) {
        return c == '[' || c == '(' || c == '{';
    }
}
