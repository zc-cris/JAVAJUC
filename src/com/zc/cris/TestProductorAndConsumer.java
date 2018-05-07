package com.zc.cris;

public class TestProductorAndConsumer {

    public static void main(String[] args) {

        Clerk clerk = new Clerk();
        Productor pro = new Productor(clerk);
        Consumer con = new Consumer(clerk);
        
        new Thread(pro, "生产线程A").start();
        new Thread(con, "消费线程B").start();
        
        new Thread(pro, "生产线程C").start();
        new Thread(con, "消费线程D").start();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              

    }

}

class Clerk {

    private int goods = 0;

    // 进货
    public synchronized void get() {
        while (this.goods >= 1) {
            System.out.println("货架已满，不能进货了");
            try {
                this.wait();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "进货:" + ++goods);
        notifyAll();

    }

    // 卖货
    public synchronized void sale() {
        while (this.goods <= 0) {       // 为了避免虚假唤醒问题，需要将wait（）和条件判断都放在while 循环中
            System.out.println("没有货物了，不能卖货了");
            try {
                this.wait();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "卖货：" + --goods);
        notifyAll();

    }

}

// 生产者
class Productor implements Runnable {

    private Clerk clerk;

    public Productor(Clerk clerk) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
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
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }

    }

}
