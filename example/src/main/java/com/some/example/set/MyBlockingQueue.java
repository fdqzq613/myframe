package com.some.example.set;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-10 11:21
 */
public class MyBlockingQueue<T> {
        final BlockingQueue<T> queue = new LinkedBlockingQueue();

        public Object lock = new Object();
        public void put(T t) throws InterruptedException {
            queue.put(t);
            synchronized(lock){
                lock.notify();
            }

        }
        public synchronized T take() throws InterruptedException {
            return queue.take();
        }
        public  T defTake(){
            while(true){
                T t = queue.poll();
                if(t==null){
                    synchronized(lock){
                        try {
                            lock.wait();
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return t;

            }

        }

        public static void main(String[] args){
            final  MyBlockingQueue<Integer> myQueue = new MyBlockingQueue();
            Thread producer = new Thread(new Runnable(){
                @Override
                public void run(){
                    try {
                        myQueue.put(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            producer.start();

            Thread concumer = new Thread(new Runnable(){
                @Override
                public void run(){
                    while(true){
                        //这里消费者获取生产者的值进行页面 处理
                        try {
                            Integer myConcumerValue = myQueue.take();
                            System.out.println("myConcumerValue1:"+myConcumerValue);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });
            concumer.start();

            Thread concumer2 = new Thread(new Runnable(){
                @Override
                public void run(){
                    while(true){
                        //这里消费者获取生产者的值进行页面 处理
                            Integer myConcumerValue = myQueue.defTake();
                            System.out.println("myConcumerValue2:"+myConcumerValue);

                    }

                }
            });
            concumer2.start();
            try {
                Thread.sleep(1000);
                for(int i=0;i<10;i++){
                    myQueue.put(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //也可以消费者等待锁 获取通知消费
        }

}
