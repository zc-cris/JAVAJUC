package com.zc.cris;

/**
 * volatile 关键字：多个线程操作共享数据的时候，可以保证主存中的数据可见，可以理解为线程写数据直接在主存中完成；
 * 而线程读数据则是每次都从主存中读取最新的
 * 
 * 效率相对于同步锁要高；但是volatile 关键字相对于同步锁是一种轻量级的同步策略
 * 1. volatile 关键字不具有互斥性
 * 2. volatile 不能保证变量的原子性
 */
public class TestVolatile {

    public static void main(String[] args) {

        ThreadDemo threadDemo = new ThreadDemo();
        // 子线程
        new Thread(threadDemo).start();
        
        // main 线程
        while(true) {
            // 同步锁保证线程到主存中读取最新的数据
//            synchronized ("cris") {
//                if(threadDemo.getFlag()) {
//                    System.out.println("-----------");
//                    break;
//                }
//            }
            if(threadDemo.getFlag()) {
                System.out.println("~~~~~~~~~~~~~~~~");
                break;
            }
        }
        
    }

}

class ThreadDemo implements Runnable {

    private volatile Boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        flag = true;    
        System.out.println("flag is " + flag);

    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

}
