package com.demo.parallel;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者
 * @author: c0l0121
 * Date: 2018/05/17 Time: 17:33
 */
public class ProducerAndConsumerUsingSemaphore {

	private static boolean isDebug = false;

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
	 * 信号量
	 */
	static class Semaphore {
		private String name;
		private AtomicInteger semaphore;

		public Semaphore(String name, int init) {
			this.name = name;
			this.semaphore = new AtomicInteger(init);
		}

		/**
		 * Passeren(荷兰语) 通过操作
		 */
		public void p() {
			synchronized (semaphore) {
				// s = s - 1, 若 s < 0, 暂停当前线程
				if (semaphore.decrementAndGet() < 0) {
					try {
						if (isDebug) {
							System.out.println(String.format("[%s], Blocking thread [%s] because [%s = %d] < 0.", Thread.currentThread().getName(), Thread.currentThread().getName(), name, semaphore.get()));
						}
						// 暂停当前线程
						semaphore.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					if (isDebug) {
						System.out.println(String.format("[%s], Thread [%s] pass [%s = %d] .", Thread.currentThread().getName(), Thread.currentThread().getName(), name, semaphore.get()));
					}
				}
			}
		}

		/**
		 * Vrijgeven (荷兰语) 释放操作
		 */
		public void v() {
			synchronized (semaphore) {
				// s = s + 1, 若 s <= 0, 唤醒一个等待此信号量的线程
				if (semaphore.incrementAndGet() <= 0) {
					if (isDebug) {
						System.out.println(String.format("[%s], Activate one thread who is waiting for [%s = %d] by thread [%s]", Thread.currentThread().getName(), name, semaphore.get(), Thread.currentThread().getName()));
					}
					// 唤醒线程
					semaphore.notify();
				}
			}
		}
	}

	/**
	 * 生产者
	 */
	static class Producer implements Runnable {
		private Product[] buffer;
		private Semaphore filledBoxes;
		private Semaphore emptyBoxes;
		private Semaphore handleBoxPermit;

		private static int idx = 0;
		private static int sequence = 0;

		public Producer(Product[] buffer, Semaphore filledBoxes, Semaphore emptyBoxes, Semaphore handleBoxPermit) {
			this.buffer = buffer;
			this.filledBoxes = filledBoxes;
			this.emptyBoxes = emptyBoxes;
			this.handleBoxPermit = handleBoxPermit;
		}

		@Override
		public void run() {
			while (true) {
				// 空箱 - 1， 小于 0 则等待
				emptyBoxes.p();
				// 生产产品并放入箱子
				put(produce());
				// 装箱 + 1， 小于 0 则唤醒一条线程
				filledBoxes.v();
			}
		}

		/**
		 * 把产品放入缓冲区
		 * @param p 产品
		 */
		private void put(Product p) {
			//获取操作缓冲区的许可，一次仅允许一条线程操作缓冲区
			handleBoxPermit.p();
			checkBoxStatus();
			if (isDebug) {
				System.out.println(String.format("[%s], Producer [%s] put product to slot [%d]", Thread.currentThread().getName(), Thread.currentThread().getName(), idx));
			}
			buffer[idx] = p;
			idx = ++idx % buffer.length;
			//释放操作缓冲区许可
			handleBoxPermit.v();
		}

		private void checkBoxStatus() {
			if (buffer[idx] != null) {
				throw new RuntimeException(String.format("[%s], Producer [%s] want to put product to not empty slot [%d].", Thread.currentThread().getName(), Thread.currentThread().getName(), idx));
			}
		}

		private Product produce() {
			Product p = new Product(String.format("%06d", sequence++));
			System.out.println(String.format("[%s], Produce: %s", Thread.currentThread().getName(), p.name));
			return p;
		}
	}

	static class Consumer implements Runnable {
		private Product[] buffer;
		private Semaphore filledBoxes;
		private Semaphore emptyBoxes;
		private Semaphore handleBoxPermit;

		private static int idx = 0;

		public Consumer(Product[] buffer, Semaphore filledBoxes, Semaphore emptyBoxes, Semaphore handleBoxPermit) {
			this.buffer = buffer;
			this.filledBoxes = filledBoxes;
			this.emptyBoxes = emptyBoxes;
			this.handleBoxPermit = handleBoxPermit;
		}

		@Override
		public void run() {
			while (true) {
				// 已经装箱数量 - 1， 数量 < 0， 则暂停
				filledBoxes.p();
				Product p = get();
				// 空箱 + 1， 数量 <= 0, 则唤醒一条等待的线程
				emptyBoxes.v();
				consume(p);
			}
		}

		private Product get() {
			// 获取操作缓冲区的许可
			handleBoxPermit.p();
			checkBoxStatus();
			if (isDebug) {
				System.out.println(String.format("[%s], Consumer [%s] get product from slot [%d].", Thread.currentThread().getName(), Thread.currentThread().getName(), idx));
			}
			Product p = buffer[idx];
			buffer[idx] = null;
			idx = ++idx % buffer.length;
			//释放操作缓冲区的许可
			handleBoxPermit.v();
			return p;
		}

		private void checkBoxStatus() {
			if (buffer[idx] == null) {
				throw new RuntimeException(String.format("[%s], Consumer [%s] want to get product from empty slot [%d].", Thread.currentThread().getName(), Thread.currentThread().getName(), idx));
			}
		}

		private void consume(Product p) {
			System.out.println(String.format("[%s], Consume: %s", Thread.currentThread().getName(), p.name));
		}
	}

	public static void main(String[] args) {
		Product[] buffer = new Product[10];
		Semaphore filledBoxes = new Semaphore("FilledBoxes", 0);
		Semaphore emptyBoxes = new Semaphore("EmptyBoxes", buffer.length);
		Semaphore producerHandleBoxPermit = new Semaphore("ProducerHandleBoxPermit", 1);
		Semaphore consumerHandleBoxPermit = new Semaphore("ConsumerHandleBoxPermit", 1);

		for (int i = 0; i < 2; i++) {
			Thread p = new Thread(new Producer(buffer, filledBoxes, emptyBoxes, producerHandleBoxPermit), String.format("P%d", i));
			p.start();
		}
		for (int i = 0; i < 2; i++) {
			Thread p = new Thread(new Consumer(buffer, filledBoxes, emptyBoxes, consumerHandleBoxPermit), String.format("C%d", i));
			p.start();
		}
	}

}
