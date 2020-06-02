package com.some.example.locker;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-29 15:51
 */
public class LockTest {
    /**
     * 写锁可以再获取读锁 --锁降级
     */
    static void writeGetRead(){
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        rtLock.writeLock().lock();
        System.out.println("writeLock");

        rtLock.readLock().lock();
        System.out.println("get read lock");
    }

    /**
     * 读锁不能再获取写锁
     */
    static void readGetNotWrite(){
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        rtLock.readLock().lock();
        System.out.println("get readLock.");
        rtLock.writeLock().lock();
        System.out.println("blocking");
    }
    public static void main(String[] args) {
        writeGetRead();
        readGetNotWrite();
    }
}
