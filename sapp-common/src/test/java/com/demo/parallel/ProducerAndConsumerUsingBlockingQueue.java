package com.demo.parallel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者消费者
 *
 * @author: c0l0121
 * Date: 2018/05/17 Time: 17:33
 */
public class ProducerAndConsumerUsingBlockingQueue {

	/**
	 * 产品
	 */
	static class Product {
		public String name;

		public Product(String name) {
			this.name = name;
		}
	}


	/**
	 * 生产者
	 */
	static class Producer implements Runnable {
		private BlockingQueue<Product> buffer;
		private static int sequence = 0;

		public Producer(BlockingQueue buffer) {
			this.buffer = buffer;
		}

		@Override
		public void run() {
			while (true) {
				// 生产产品并放入箱子
				put(produce());
			}
		}

		/**
		 * 把产品放入缓冲区
		 *
		 * @param p 产品
		 */
		private void put(Product p) {
			try {
				buffer.put(p);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private Product produce() {
			Product p = new Product(String.format("%06d", sequence++));
			System.out.println(String.format("[%s], Produce: %s", Thread.currentThread().getName(), p.name));
			return p;
		}
	}

	static class Consumer implements Runnable {
		private BlockingQueue<Product> buffer;


		private static int idx = 0;

		public Consumer(BlockingQueue<Product> buffer) {
			this.buffer = buffer;
		}

		@Override
		public void run() {
			while (true) {
				Product p = get();
				if (p != null) {
					consume(p);
				}
			}
		}

		private Product get() {
			Product p = null;
			try {
				p = buffer.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return p;
		}

		private void consume(Product p) {
			System.out.println(String.format("[%s], Consume: %s", Thread.currentThread().getName(), p.name));
		}
	}

	public static void main(String[] args) {
		ArrayBlockingQueue<Product> buffer = new ArrayBlockingQueue(10);

		for (int i = 0; i < 2; i++) {
			Thread p = new Thread(new Producer(buffer), String.format("P%d", i));
			p.start();
		}
		for (int i = 0; i < 2; i++) {
			Thread p = new Thread(new Consumer(buffer), String.format("C%d", i));
			p.start();
		}
	}

}
