package com.github.melin.concurrnt.lock;

public class SyncTest implements Counter {
	
	private Object lock = new Object();
	
	private long counter;
	
	@Override
	public long getCounter() {
		synchronized (lock) {
			return counter;
		}
	}
	@Override
	public void increment(long amount) {
		synchronized (lock) {
			counter += amount;
		}
	}
}
