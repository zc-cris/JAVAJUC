package com.zc.cris;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// 创建执行线程的第三种方式：实现Callable 接口，相较于实现Runnable 接口的方式，方法可以具有返回值，并且还要抛出异常
// 执行Callable 的call 方法，还需要FutureTask 类的支持，用于接收运算的结果，futureTask是Future 和Runnable 接口的实现类
public class TestCallable {

    public static void main(String[] args) {
        
        CallableDemo demo = new CallableDemo();
        FutureTask<Integer> task = new FutureTask<>(demo);
        
        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }
        
        try {
            System.out.println("-------------");
            System.out.println("结果是：" + task.get());        // FutureTask 可实现闭锁的效果
        } catch (InterruptedException | ExecutionException e) {
            
            e.printStackTrace();
        }
        
        
    }
}

class CallableDemo implements Callable<Integer>{

    Integer sum = 0;
    @Override
    public Integer call() throws Exception {
        for (int i = 0; i <= 100; i++) {
            sum += i;
            System.out.println(Thread.currentThread().getName()+ "------" + sum);
        }
        return sum;
    }
    
}
