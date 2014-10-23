package com.github.melin.concurrnt.barrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestCountDownLatch {
	private static final int THREAD_NUM = 5;
	
	public static class WorkThread implements Runnable {
		private CountDownLatch latch;
		
		public WorkThread(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			System.out.println("开始执行...");
			try {
				latch.await();
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("....执行结束...");
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
		CountDownLatch latch = new CountDownLatch(1);
		for(int i=0; i<5; i++) {
			executorService.submit(new WorkThread(latch));
		}
		latch.countDown();
		System.out.println("------------");
		
		executorService.shutdown();
	}
}
