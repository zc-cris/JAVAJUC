package com.zc.cris;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


// CopyOnWriteArrayList/set:"写入并复制"集合
// 注意：添加操作多时效率低，因为每次添加都会进行原数据的复制，开销非常大，并发迭代时建议使用
public class TestCopyOnWriteArrayList {

    public static void main(String[] args) {
        ThreadDemo2 threadDemo2 = new ThreadDemo2();
        for (int i = 0; i < 10; i++) {
            new Thread(threadDemo2).start();
        }
    }
    
    
}

class ThreadDemo2 implements Runnable{
    
   // private static List<String> list = new ArrayList<>();
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
    static {
        list.add("aa");
        list.add("bb");
        list.add("cc");
    }
    
    @Override
    public void run() {
        
        Iterator<String> iterator = list.iterator();
        for (String string : list) {
            System.out.println(string);
            list.add("cris");
        }
        
    }
    
    
    
    
}
