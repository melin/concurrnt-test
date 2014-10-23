package com.github.melin.concurrnt.pc;


/**
 * 模拟生产这和消费者不同实现方式
 * @author Windows User
 *
 */
public class ProducerConsumerTest {
	public static void main(String[] args) {
		//ArrayQueueSemaphore queue = new ArrayQueueSemaphore();
		//ArrayQueueCondition queue = new ArrayQueueCondition();
		ArrayQueueObject queue = new ArrayQueueObject();
		Thread producer = new Thread(new Producer(queue), "producer-thread");
		Thread consumer = new Thread(new Consumer(queue), "consumer-thread");
		
		producer.start();
		consumer.start();
	}
}
