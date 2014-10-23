package com.github.melin.concurrnt.pc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayQueueSemaphore implements Queue {
	private final Semaphore notFull = new Semaphore(10);
	private final Semaphore notEmpty = new Semaphore(0);
	private final ReentrantLock lock = new ReentrantLock();
	
	private final String[] connections = new String[10];
	int putIndex, takeIndex, count;
	
	public void put(String e) throws InterruptedException {
		notFull.acquire();
		lock.lockInterruptibly();
		try {
			connections[putIndex] = e;
			if(++putIndex == connections.length)
				putIndex = 0;
			
			count++;
		} finally {
			notEmpty.release();
			lock.unlock();
		}
	}
	
	public String take() throws InterruptedException {
		notEmpty.acquire();
		lock.lockInterruptibly(); 
		try {
			String re = connections[takeIndex];
			if(++takeIndex == connections.length)
				takeIndex = 0;
			
			count--;
			return re;
		} finally {
			notFull.release();
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