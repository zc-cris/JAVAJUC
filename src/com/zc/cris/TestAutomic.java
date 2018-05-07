package com.zc.cris;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

/*
 * 一：i++ 的原子性操作：实际上是分为三步骤：1.读取i的值到中间变量；2.修改i的值；3.将中间变量的值赋值给i或者其他变量
 * 二：原子变量：jdk1.5之后，java.util.concurrent.automic 包下提供了常用的原子变量(如：AtomicInteger...)：
 *              1. 使用volatile 关键字保证内存可见性
 *              2. CAS（compare-and-swap）算法：保证数据的原子性
 *                  CAS算法是底层硬件对于并发操作共享数据的支持
 *                  CAS算法包含了三个操作数：
 *                  内存值V：线程第一次从主存中读取的共享数据值
 *                  预估值：线程修改共享数据的时候还会从主存中再读取一次当前值
 *                  更新值：线程修改共享数据后的值
 *                  当且仅当V == A 的时候，V = B,否则将不进行任何操作
 */
public class TestAutomic {

    public static void main(String[] args) {
       
        ThreadAutomic threadAutomic = new ThreadAutomic();
        
        for (int i = 0; i < 10; i++) {
            new Thread(threadAutomic).start();
        }
        
        
    }
    
    @Test
    public void test2() {
        int i = 10;
        i = i++;

        /*
         * int temp = i; i = i+1; i = temp;
         */

        System.out.println(i);  //10
        i = ++i;
        System.out.println(i);  //11
    }

    
    
    
    private int i = 1;
    @Test
    public void test1() {
        System.out.println(getNum());
        System.out.println("i = " + i);
    }

    public int getNum() {
//        return ++i; 
        return i++;
    }

}
class ThreadAutomic implements Runnable{

    private AtomicInteger integer = new AtomicInteger();
    
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
        System.out.println(getNum());
    }
    public int getNum() {
        return integer.getAndIncrement();
    }
    
}
