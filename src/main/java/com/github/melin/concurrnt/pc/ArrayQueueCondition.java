package com.github.melin.concurrnt.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayQueueCondition implements Queue {
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();
	
	private final String[] connections = new String[10];
	int putIndex, takeIndex, count;

	public void put(String e) throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			if(count == connections.length)
				notFull.await();
			
			connections[putIndex] = e;
			if(++putIndex == connections.length)
				putIndex = 0;
			
			count++;
	        notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public String take() throws InterruptedException {
		lock.lock(); 
		try {
			if(count == 0)
				notEmpty.await();
			
			String re = connections[takeIndex];
			if(++takeIndex == connections.length)
				takeIndex = 0;
			
			count--;
			notFull.signal();
			return re;
		} finally {
			lock.unlock();
		}
	}
	
	public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}