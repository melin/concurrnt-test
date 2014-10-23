package com.github.melin.concurrnt.barrier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class TestPhaser {
	private static final int THREAD_NUM = 5;
	
	public static class WorkThread implements Runnable {
		private Phaser phaser;
		
		public WorkThread(Phaser phaser) {
			this.phaser = phaser;
		}

		@Override
		public void run() {
			phaser.arrive();
			System.out.println("开始执行...");
			try {
				phaser.awaitAdvance(phaser.getPhase());
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("....执行结束...");
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
		Phaser phaser = new Phaser(THREAD_NUM);
		for(int i=0; i<5; i++) {
			executorService.submit(new WorkThread(phaser));
		}
		System.out.println("------------");
		
		executorService.shutdown();
	}
}
