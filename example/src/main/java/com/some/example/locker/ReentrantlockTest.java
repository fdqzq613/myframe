package com.some.example.locker;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-08 16:30
 */
@Slf4j
public class ReentrantlockTest {
    public static void main(String[] args) {

        //公平锁
        ReentrantLock reentrantlock = new ReentrantLock(false);
        reentrantlock.lock();
        for(int i=0;i<10;i++){
            final int c = i;
            new Thread(new Runnable(){

                @Override
                public void run() {
                    reentrantlock.lock();
                    try {
                        log.info("执行{}", c);
                    }finally {

                        reentrantlock.unlock();
                    }

                }
            }).start();
        }
        try {
            Thread.sleep(100);
            reentrantlock.unlock();

            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
