package com.github.melin.concurrnt.lock.task;

import com.github.melin.concurrnt.lock.Counter;
import com.github.melin.concurrnt.lock.MainTest;

public class ReadTask implements Runnable {
	private Counter counter;
	
	public ReadTask(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void run() {
		while(!MainTest.flag) {
			//System.out.println(counter.getCounter());
			counter.getCounter();
		}
	}
}
