package com.github.melin.concurrnt.pc;

public class ArrayQueueObject implements Queue {
	private final Object notFull = new Object();
	private final Object notEmpty = new Object();

	private int length = 10;
	private final String[] connections = new String[length];
	int putIndex, takeIndex, count;

	public void put(String e) throws InterruptedException {
		synchronized (notEmpty) {
			if (count == 0) {
				notEmpty.notify();
			}
	
			synchronized (notFull) {
				if(count == length)
					notFull.wait();
				
				connections[putIndex] = e;
				if (++putIndex == length)
					putIndex = 0;
		
				count++;
			}
		}
	}

	public String take() throws InterruptedException {
		synchronized (notEmpty) {
			if (count == 0) {
				notEmpty.wait();
			}
			
			synchronized (notFull) {
				if(count == length)
					notFull.notify();
				
				String re = connections[takeIndex];
				if (++takeIndex == connections.length)
					takeIndex = 0;
		
				count--;
				return re;
			}
		}
	}

	public int size() {
		synchronized (notEmpty) {
			synchronized (notFull) {
				return count;
			}
		}
	}
}