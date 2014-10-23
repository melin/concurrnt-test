package com.github.melin.concurrnt.lock;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderTest implements Counter {
	
	private LongAdder counter = new LongAdder();;
	
	@Override
	public long getCounter() {
		return counter.longValue();
	}
	
	@Override
	public void increment(long amount) {
		counter.add(amount);
	}
}
