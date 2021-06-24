package com.example.ganesh.deep_android.dsa.stackqueue;

import java.util.Stack;

/**
 * Created by Ganesh Padhayay on 24/06/21.
 */
public class MinStack {

    private final Stack<Integer> stack;
    private final Stack<Integer> minStack;

    public static void main(String[] args) {
        MinStack object = new MinStack();
        object.push(3);
        object.push(2);
        System.out.println(object.top());
        System.out.println(object.getMin());
        object.pop();
        object.push(8);
        System.out.println(object.top());
        System.out.println(object.getMin());
        object.pop();
        System.out.println(object.getMin());
    }

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty())
            minStack.push(val);
        else {
            if (minStack.peek() <= val)
                minStack.push(minStack.peek());
            else minStack.push(val);
        }
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
