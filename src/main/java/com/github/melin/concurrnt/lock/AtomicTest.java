package com.github.melin.concurrnt.lock;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicTest implements Counter {
	
	private AtomicLong counter = new AtomicLong();;
	
	@Override
	public long getCounter() {
		return counter.get();
	}
	@Override
	public void increment(long amount) {
		counter.addAndGet(amount);
	}
}
