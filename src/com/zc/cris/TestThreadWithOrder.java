package com.zc.cris;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 编写一个程序，开启三个线程 A,B,C,
// A线程将自己id打印5次，然后B线程将自己id打印10次，C线程将自己id打印15次，共执行10轮
// 一个主类负责线程之间的通信和上锁，开锁，三个实现了Runnable接口的线程类执行控制类的方法即可，主要逻辑实现都在主类里面
public class TestThreadWithOrder {

    public static void main(String[] args) {
        
        ThreadOrder order = new ThreadOrder();
        
        // 这里使用java8的 lambda表达式来完成实现Runnable 接口的线程类
        new Thread(() -> {
            
            for (int i = 1; i <= 10; i++) {
                order.loopA(i);
            }
            }, "线程A").start();
        
        new Thread(() -> {
            
            for (int i = 1; i <= 10; i++) {
                order.loopB(i);
            }
        }, "线程B").start();
        
        new Thread(() -> {
            
            for (int i = 1; i <= 10; i++) {
                order.loopC(i);
                System.out.println("---------------------------------------------------");
            }
        }, "线程C").start();

    }

}

// 专门写一个类用于控制线程之间的顺序。依次按照规定等待，执行对应业务逻辑然后顺序唤醒其他线程
// 通过lock 锁实现线程执行任务的互斥性；通过condition 完成线程之间的按序通信（重点）
class ThreadOrder{

    private Lock lock = new ReentrantLock();

    private int num = 1;
    
    private Condition con1 = lock.newCondition();
    private Condition con2 = lock.newCondition();
    private Condition con3 = lock.newCondition();

    public void loopA(int loop) {

        lock.lock();

        try {
            
            if(num != 1) {
                con1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "----->第" + loop + "轮");
            }
            
            num = 2;
            con2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public void loopB(int loop) {

        lock.lock();

        try {
            
            if(num != 2) {
                con2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "----->第" + loop + "轮");
            }
            
            num = 3;
            con3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public void loopC(int loop) {

        lock.lock();

        try {
            
            if(num != 3) {
                con3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "----->第" + loop + "轮");
            }
            
            num = 1;
            con1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
