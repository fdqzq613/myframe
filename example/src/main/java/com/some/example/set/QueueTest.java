package com.some.example.set;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-09 11:08
 */
public class QueueTest {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<String>();
        //添加元素
        queue.offer("a");
        queue.offer("b");
        System.out.println(queue.poll());
    }
}
