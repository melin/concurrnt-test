package com.github.melin.concurrnt;

import java.util.concurrent.TimeUnit;

public class ThreadInterruptTest {
	final static Thread thread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			//while(!Thread.interrupted()) {
			for(;;) {
				System.out.println("....");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					break;
				}
			}
			System.out.println("finished");
		}
	});
	public static void main(String[] args) throws InterruptedException {
		thread.start();
		
		TimeUnit.SECONDS.sleep(3);
		thread.interrupt();
		TimeUnit.SECONDS.sleep(1);
	}
}
