package com.zc.cris;

import static org.hamcrest.CoreMatchers.nullValue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

//  CountDownLatch：闭锁，完成某些运算，其他线程的运算全部完成后，
// 当前线程的运算才执行（应用示例：京东多线程计算不同类别商品的库存等，最后由主线程计算所有商品的库存等）
public class TestCountDownLatch {

    public static void main(String[] args) {
        
        LocalDateTime localDateTime = LocalDateTime.now();
        
        CountDownLatch latch = new CountDownLatch(5);
        CountDownLatchDemo demo = new CountDownLatchDemo(latch);
        for (int i = 0; i < 5; i++) {
            new Thread(demo).start();
        }
        
        // 如果不使用闭锁，5个子线程和main线程同时进行，我们无法计算5个子线程执行完任务的总共时间
        // 使用了闭锁，main线程将会等待子线程们完成任务后再执行自己的任务
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        LocalDateTime localDateTime2 = localDateTime.now();
        System.out.println("消耗的时间是：" + Duration.between(localDateTime, localDateTime2).toMillis());
    }
    
    
}

class CountDownLatchDemo implements Runnable{

    private CountDownLatch latch;
    public CountDownLatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }
    
    
    @Override
    public void run() {
        synchronized (this) {
            
            try {
                for (int i = 0; i < 10000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            } finally {
                latch.countDown();
            }
        }
    }
}
