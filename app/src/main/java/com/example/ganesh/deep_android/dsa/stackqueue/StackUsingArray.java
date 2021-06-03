package com.example.ganesh.deep_android.dsa.stackqueue;

/**
 * Created by Ganesh Padhayay on 03/06/21.
 */
public class StackUsingArray {

    private static final int MAX_SIZE = 5;
    private static final int[] stackArray = new int[MAX_SIZE];
    private static int top = -1;

    public static void main(String[] args) {
        push(6);
        push(3);
        push(7);
        System.out.println(top());
        pop();
        System.out.println("Size of the stack is: " + size());
        push(5);
        push(10);
        push(12);
        System.out.println("Size of the stack is: " + size());
        push(7);
        pop();
        System.out.println("Size of the stack is: " + size());
        System.out.println("Is stack empty: " + isEmpty());
    }

    private static boolean isEmpty() {
        return top == -1;
    }

    private static int size() {
        if (top == -1)
            return 0;
        else
            return top + 1;
    }

    private static void pop() {
        if (isEmpty())
            System.out.println("Stack is empty, nothing to pop");
        else
            top = top - 1;
    }

    private static int top() {
        if (isEmpty()) {
            System.out.println("Stack is empty, nothing at top");
            return Integer.MIN_VALUE;
        } else
            return stackArray[top];
    }

    private static void push(int val) {
        if (top == MAX_SIZE - 1)
            System.out.println("Stack is full, can't add more elements");
        else {
            top = top + 1;
            stackArray[top] = val;
        }
    }
}
