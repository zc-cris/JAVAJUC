package com.zc.cris;


/*
 * 1. 一个主类，两个普通同步方法，两个线程分别执行，打印？1 2
 * 2. 一个主类，getOne方法新增sleep（）方法，打印？1 2
 * 3. 一个主类，新增一个普通方法getThree（），打印？3 1 2
 * 4. 两个主类，两个普通同步方法，打印? 2 1
 * 5. 一个主类，getOne方法为静态同步方法，打印？2 1
 * 6. 一个主类，两个方法均为静态同步方法，打印？1 2
 * 7. 两个主类，一个为静态同步方法，一个为普通同步方法，打印？2 1
 * 8. 两个主类，两个方法均为静态同步方法，打印？1 2
 * 
 * 线程八锁：
 * ① 非静态同步方法的锁为this，静态同步方法的锁为对应的Class对象
 * ② 某一个时刻内，只能有一个线程持有锁（例如this）,其他的无论多少个方法都无法拿到这把this锁
 */
public class TestThread8Lock {

    public static void main(String[] args) {
        
        Thread8LockDemo demo = new Thread8LockDemo();
        Thread8LockDemo demo2 = new Thread8LockDemo();
        
        new Thread(() ->{
            demo.getOne();
        }).start();
        
        new Thread(() ->{
//            demo.getTwo();
            demo2.getTwo();
        }).start();
        
      /*  new Thread(()->{
            demo.getThree();
        }).start();*/

    }
}

class Thread8LockDemo {

    public static synchronized void getOne() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
        System.out.println(1);
    }

    public static synchronized void getTwo() {
        System.out.println(2);
    }
    
    public void getThree() {
        System.out.println(3);
    }

}
