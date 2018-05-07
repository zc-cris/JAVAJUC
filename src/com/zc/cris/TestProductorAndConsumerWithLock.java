package com.zc.cris;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumerWithLock {

    public static void main(String[] args) {

        Clerk1 clerk = new Clerk1();
        Productor1 pro = new Productor1(clerk);
        Consumer1 con = new Consumer1(clerk);
        
        new Thread(pro, "生产线程A").start();
        new Thread(con, "消费线程B").start();
        
        new Thread(pro, "生产线程C").start();
        new Thread(con, "消费线程D").start();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              

    }

}

class Clerk1 {

    private int goods = 0;
    
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    
    // 进货
    public void get() {
        lock.lock();
        
        try {
            while (this.goods >= 1) {
                System.out.println("货架已满，不能进货了");
                try {
                    condition.await();          // 使用Lock 锁的wait和唤醒机制
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "进货:" + ++goods);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // 卖货
    public void sale() {
        lock.lock();
        
        try {
            while (this.goods <= 0) {       // 为了避免虚假唤醒问题，需要将wait（）和条件判断都放在while 循环中
                System.out.println("没有货物了，不能卖货了");
                try {
                    condition.await();
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "卖货：" + --goods);
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

}

// 生产者
class Productor1 implements Runnable {

    private Clerk1 clerk;

    public Productor1(Clerk1 clerk) {
        
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }

}

// 消费者
class Consumer1 implements Runnable {

    private Clerk1 clerk;

    public Consumer1(Clerk1 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }

    }

}
