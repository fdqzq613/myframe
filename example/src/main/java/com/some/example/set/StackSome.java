package com.some.example.set;

import java.util.LinkedList;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-10 09:02
 */
public class StackSome {

        LinkedList list = new LinkedList();


        public synchronized void push(Object x) {
            synchronized(list) {
                list.addLast( x );
                notify();
            }
        }

        public synchronized Object pop()
                throws Exception {
            synchronized(list) {
                if( list.size() <= 0 ) {
                    wait();
                }
                return list.removeLast();
            }
        }

    public static void main(String[] args) {
        StackSome stackSome = new StackSome();
        stackSome.push(1);
        try {
            System.out.println(stackSome.pop().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
