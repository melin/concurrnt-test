package com.github.melin.concurrnt;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
	private AtomicInteger max = new AtomicInteger();
	
	public void set(int value) {
		for(;;) {
			int tmp = max.get();
			if(value>tmp) {
				if(max.compareAndSet(tmp, value)) {
					break;
				} else {
					continue;
				}
			} else {
				break;
			}
		}
	}
}
