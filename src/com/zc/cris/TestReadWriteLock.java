package com.zc.cris;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 读写锁：针对多个线程对于共享数据的读和写做出优化
// 写写/读写 需要采用写锁
// 读读 可以使用读锁来提高多线程效率
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteDemo demo = new ReadWriteDemo();
        
        new Thread(() ->{
            demo.write((int)(Math.random()*101));
        }, "写线程").start();
        
        for (int i = 0; i < 10; i++) {
            new Thread(() ->{
                demo.read();
            }, "读线程"+i).start();
        }
        
        
    }
    
}

class ReadWriteDemo{
    
    private int i = 0;
    // 读写锁
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    
    public void read() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读数据：" + i);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void write(int i) {
        lock.writeLock().lock();
        try {
            this.i = i;
            System.out.println(Thread.currentThread().getName() + "写数据为：" + this.i);
        } finally {
         lock.writeLock().unlock();   
        }
    }
    
}
