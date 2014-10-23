package com.github.melin.concurrnt.lock.task;

import java.util.concurrent.CountDownLatch;

import com.github.melin.concurrnt.lock.Counter;

public class ReadTask implements Runnable {
	private long count;
	private Counter counter;
	
	private CountDownLatch latch;
	
	public ReadTask(CountDownLatch latch, long count, Counter counter) {
		this.latch = latch;
		this.count = count;
		this.counter = counter;
	}

	@Override
	public void run() {
		for(int i=0; i<count; i++) {
			counter.increment(1);
		}
		
		latch.countDown();
	}
}
