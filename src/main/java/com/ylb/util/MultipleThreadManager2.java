package com.ylb.util;

import java.util.Vector;

public class MultipleThreadManager2 {
    static final int SIZE = 100;
    static final Object object = new Object();
	final static Vector<Integer> list = new Vector<Integer>();
	
    
    public static void main(String[] args) {
    	
    	for(int i=1;i<=SIZE;++i) {
    		list.add(i);
    	}

        Thread Printer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                	  while(!list.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + "---" + list.remove(0));
                        object.notify();
                        try {
                            object.wait();;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "Printer1");
        Thread Printer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                   while(!list.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + "---" + list.remove(0));
                        object.notify();
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "Printer2");
        Printer1.start();
        Printer2.start();
    }
}