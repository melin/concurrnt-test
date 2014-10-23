package com.github.melin.concurrnt.lock;

public interface Counter {
	public long getCounter();
	public void increment(long amount);
}