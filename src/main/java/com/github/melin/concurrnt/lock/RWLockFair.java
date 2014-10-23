package com.github.melin.concurrnt.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockFair implements Counter {
	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	
	private Lock readLock = readWriteLock.readLock();
	private Lock writeLock = readWriteLock.writeLock();
	
	private long counter;
	
	@Override
	public long getCounter() {
		try {
			readLock.lock();
			return counter;
		} finally {
			readLock.unlock();
		}
	}
	@Override
	public void increment(long amount) {
		try {
			writeLock.lock();
			counter += amount;
		} finally {
			writeLock.unlock();
		}
	}
}
