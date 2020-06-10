package com.some.example.thread;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-10 09:21
 */
public class PrintXyz {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Object lock3 = new Object();
        new Thread(new Runnable(){
            public void run(){
                for(int i=0;i<10;i++){
                    if(i>0){
                        synchronized(lock1){
                            try {
                                lock1.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    System.out.print("x");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized(lock2){
                        lock2.notify();
                    }
                }

            }
        }).start();
        new Thread(new Runnable(){
            public void run(){
                for(int i=0;i<10;i++){
                    synchronized(lock2){
                        try {
                            lock2.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("y");
                    synchronized(lock3){
                        lock3.notify();
                    }
                }

            }
        }).start();

        new Thread(new Runnable(){
            public void  run(){
                for(int i=0;i<10;i++){
                    synchronized(lock3){
                        try {
                            lock3.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("z"+"\t");
                    synchronized(lock1){
                        lock1.notify();
                    }
                }

            }
        }).start();
    }
}
