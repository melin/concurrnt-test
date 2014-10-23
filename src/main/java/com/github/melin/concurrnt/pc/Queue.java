package com.github.melin.concurrnt.pc;

public interface Queue {
	public void put(String e) throws InterruptedException;
	public String take() throws InterruptedException;
	public int size();
}
