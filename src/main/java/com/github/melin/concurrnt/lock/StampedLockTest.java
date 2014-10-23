package com.github.melin.concurrnt.lock;

import java.util.concurrent.locks.StampedLock;

public class StampedLockTest implements Counter {
	public StampedLockTest(int retries) {
		this.retries = retries;
	}

	public StampedLockTest() {
		this.retries = 1;
	}

	private StampedLock rwlock = new StampedLock();

	private long counter;
	private int retries;

	public long getCounter() {
		long result;
		for (int i = 0; i < retries; i++) {
			long stamp = rwlock.tryOptimisticRead();
			if (stamp != 0) {
				result = counter;

				if (rwlock.validate(stamp)) {
					return result;
				}
			}
		}

		long stamp = rwlock.readLock();
		try {
			result = counter;
		} finally {
			rwlock.unlockRead(stamp);
		}

		return result;

	}

	public void increment(long amount) {
		long stamp = rwlock.writeLock();

		try {
			counter += amount;
		} finally {
			rwlock.unlockWrite(stamp);
		}
	}
}
