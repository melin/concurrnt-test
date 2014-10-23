package com.github.melin.concurrnt.pc;


public class Consumer implements Runnable {
	private Queue queue;
	
	public Consumer(Queue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			for(;;) {
				String connect = queue.take();
				System.out.println(connect);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
