package com.zc.cris;

// 模拟CAS 算法 
public class TestCAS {
    
    
    public static void main(String[] args) {
        final CompareAndSwap compareAndSwap = new CompareAndSwap();
        
        for (int i = 0; i < 10; i++) {
            
            new Thread(() ->{
                int num = compareAndSwap.getNum();
                System.out.println(compareAndSwap.compareAndSet(num, (int)(Math.random()*100)));
            }).start();
        }
    }

}

class CompareAndSwap{
    
    private int i = 0;
    
    public synchronized int getNum() {
        return this.i;
    }
    
    public synchronized int compareAndSwap(int exceptedValue, int newValue) {
        int oldValue = this.i;
        if(oldValue == exceptedValue) {
            this.i = newValue;
        }
        return oldValue;
    }
    
    public synchronized Boolean compareAndSet(int exceptedValue, int newValue) {
        return exceptedValue == compareAndSwap(exceptedValue, newValue);
        
    }
    
    
}
