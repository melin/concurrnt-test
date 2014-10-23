package com.github.melin.concurrnt.barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestCyclicBarrier {
	private static final int THREAD_NUM = 5;
	
	public static class WorkThread implements Runnable {
		private CyclicBarrier barrier;
		
		public WorkThread(CyclicBarrier barrier) {
			this.barrier = barrier;
		}

		@Override
		public void run() {
			System.out.println("开始执行...");
			try {
				barrier.await();
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("执行结束...");
		}
	}
	
	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(THREAD_NUM, new Runnable() {
			@Override
			public void run() {
				System.out.println("所有任务完成");
			}
		});
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
		for(int i=0; i<5; i++) {
			executorService.submit(new WorkThread(barrier));
		}
		System.out.println("----");
		executorService.shutdown();
	}
}
