package com.some.example.thread;

import java.util.concurrent.Semaphore;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-10 09:48
 */
public class PrintXyzBySemaphore {


    public static void main(String[] args) {
         Semaphore semaphore1 = new Semaphore(1);
         Semaphore semaphore2 = new Semaphore(1);
         Semaphore semaphore3 = new Semaphore(1);
        try {
            semaphore2.acquire();
            semaphore3.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable(){
            public void run(){
                for(int i=0;i<10;i++){
                    try {
                        semaphore1.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("x");
                    semaphore2.release();
                }

            }
        }).start();
        new Thread(new Runnable(){
            public void run(){
                for(int i=0;i<10;i++){
                    try {
                        semaphore2.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("y");
                    semaphore3.release();
                }

            }
        }).start();
        new Thread(new Runnable(){
            public void run(){
                for(int i=0;i<10;i++){
                    try {
                        semaphore3.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("z\t");
                    semaphore1.release();
                }

            }
        }).start();
    }
}
