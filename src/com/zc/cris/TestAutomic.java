package com.zc.cris;

import java.nio.channels.Pipe.SourceChannel;

import org.junit.jupiter.api.Test;

/*
 * 一：i++ 的原子性操作：实际上是分为三步骤：1.读取i的值到中间变量；2.修改i的值；3.将中间变量的值赋值给i或者其他变量
 * 二：
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

    private int i = 0;
    
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
        return ++i;
    }
    
}
