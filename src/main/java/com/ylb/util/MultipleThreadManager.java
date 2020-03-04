package com.ylb.util;

import java.util.ArrayList;
import java.util.List;

public class MultipleThreadManager {
	final List<Integer> list1 = new ArrayList<Integer>();
	final List<Integer> list2 = new ArrayList<Integer>();

	synchronized void addList1(Integer num) {
		list1.add(num);
	}

	synchronized void addList2(Integer num) {
		list2.add(num);
	}


	public static void main(String[] args) {

		MultipleThreadManager manager = new MultipleThreadManager();
		new Thread(manager.new Producer()).start();

		Consumer consumer1 = manager.new Consumer("Printer1", manager.list1);
		Consumer consumer2 = manager.new Consumer("Printer2", manager.list2);

		new Thread(consumer1).start();
		new Thread(consumer2).start();

	}

	class Producer implements Runnable {

		@Override
		public void run() {
			int i = 0;
			while (true && i < 100) {
				++i;
				if (0 == i % 2) {
					addList2(i);
				} else {
					addList1(i);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	class Consumer implements Runnable {

		String name = "";
		List<Integer> list;

		public Consumer(String name, List<Integer> list) {
			this.name = name;
			this.list = list;
		}

		@Override
		public void run() {
			while (true) {
				if (list.isEmpty()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println(name + "----"+list.remove(0));
				}
			}
		}

	}

}