package com.github.melin.concurrnt.pc;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
	private Queue queue;
	
	private AtomicInteger counter = new AtomicInteger();
	
	public Producer(Queue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			Random random = new Random();
			for(;;) {
				queue.put("datasource-" + counter.getAndIncrement());
				TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
