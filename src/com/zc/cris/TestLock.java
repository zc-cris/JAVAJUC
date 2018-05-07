package com.zc.cris;

import java.nio.channels.NonWritableChannelException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 通常用于解决线程安全问题的三种方式：
/*
 * synchronized:隐式锁
 * 
 * 1. 同步代码块 2. 同步方法 
 * 
 * jdk1.5之后
 * 
 * 3. 同步锁：Lock （可重入锁）
 * 这是一个显示锁，通过我们手动lock（）上锁，并且必须通过unlock（）方法释放锁
 */
public class TestLock {

    public static void main(String[] args) {
        LockDemo deom = new LockDemo();
        new Thread(deom, "窗口1").start();
        new Thread(deom, "窗口2").start();
        new Thread(deom, "窗口3").start();
    }
}

class LockDemo implements Runnable {

    private int ticket = 1000;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock(); // 手动上锁
            
            try {
                if (ticket > 0) {
                    try {
                        Thread.sleep(200);

                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":" + --ticket);
                }
            } finally {
                lock.unlock(); // 手动释放锁
            }
        }

    }

}
