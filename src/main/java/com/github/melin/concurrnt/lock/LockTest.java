package com.github.melin.concurrnt.lock;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest implements Counter {
	private ReentrantLock lock = new ReentrantLock();
	
	private long counter;
	
	@Override
	public long getCounter() {
		try {
			lock.lock();
			return counter;
		} finally {
			lock.unlock();
		}
	}
	@Override
	public void increment(long amount) {
		try {
			lock.lock();
			counter += amount;
		} finally {
			lock.unlock();
		}
	}
}
