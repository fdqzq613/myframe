package com.some.example.set;

import java.util.Arrays;
import java.util.Stack;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-09 11:18
 */
public class StackTest {
    private int maxSize;
    private long[] stackArray;
    private int top;
    public StackTest(int s) {
        maxSize = s;
        stackArray = new long[maxSize];
        top = -1;
    }
    public void push(long j) {
        stackArray[++top] = j;
    }
    public long pop() {
        return stackArray[top--];
    }
    public long peek() {
        return stackArray[top];
    }
    public boolean isEmpty() {
        return (top == -1);
    }
    public boolean isFull() {
        return (top == maxSize - 1);
    }
    public static void main(String[] args) {
        StackTest theStack = new StackTest(10);
        theStack.push(10);
        theStack.push(20);
        theStack.push(30);
        theStack.push(40);
        theStack.push(50);
        while (!theStack.isEmpty()) {
            long value = theStack.pop();
            System.out.print(value);
            System.out.print(" ");
        }
        System.out.println("");


        //jdk自带
        Stack stack=new Stack();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
        while(!stack.empty()){
            System.out.println(stack.pop());
        }
    }
}
