package com.ylb.util;

import java.util.Vector;

public class MultipleThreadManager3 {
	static final int SIZE = 100;
	// static final Object object = new Object();
	final static Vector<Integer> list = new Vector<Integer>();
	final static Vector<Integer> list1 = new Vector<Integer>();
	final static Vector<Integer> list2 = new Vector<Integer>();

	public static void main(String[] args) {

		for (int i = 1; i <= SIZE; ++i) {
			list.add(i);
		}
		list1.add(list.remove(0));

		Thread Printer1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// synchronized (object) {
				while (!list.isEmpty() || !list1.isEmpty()) {
					if (!list1.isEmpty()) {
						System.out.println(Thread.currentThread().getName() + "---" + list1.remove(0));
						if (!list.isEmpty()) {
							list2.add(list.remove(0));
						}
					} else {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}, "Printer1");
		Thread Printer2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!list.isEmpty() || !list2.isEmpty()) {
					if (!list2.isEmpty()) {
						System.out.println(Thread.currentThread().getName() + "---" + list2.remove(0));
						if (!list.isEmpty()) {
							list1.add(list.remove(0));
						}
					} else {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "Printer2");

		Printer1.start();
		Printer2.start();

//		try {
//			Thread.sleep(100000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}