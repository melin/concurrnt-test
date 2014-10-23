package com.github.melin.concurrnt.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.melin.concurrnt.lock.task.WriteTask;
import com.github.melin.concurrnt.lock.task.ReadTask;

public class MainTest {
	private static final int READ_THREAD_NUM = 16;
	private static final int WRITE_THREAD_NUM = 1;
	
	private static final long NUM = 100000000;
	
	public static volatile boolean flag = false;
	
	private static ExecutorService executorService = 
			Executors.newFixedThreadPool(READ_THREAD_NUM + WRITE_THREAD_NUM); 
	
	private static Map<String, Long> result = new HashMap<String, Long>();
	
	public static void main(String[] args) throws InterruptedException {
		Counter counter = new LockTest();
		createLock(counter, "Lock");
		
		counter = new SyncTest();
		createLock(counter, "Sync");
		
		counter = new RWLock();
		createLock(counter, "RWLock");
		
		/*counter = new RWLockFair();
		createLock(counter, "RWFairLock");*/
		
		counter = new StampedLockTest();
		createLock(counter, "Stamped");
		
		counter = new AtomicTest();
		createLock(counter, "Atomic");
		
		counter = new LongAdderTest();
		createLock(counter, "LongAdder");
		
		System.out.println(result.toString());
	}
	
	private static void createLock(Counter counter, String type) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(READ_THREAD_NUM);
		long start = System.currentTimeMillis();
		createWriteThread(latch, counter);
		createReadThread(counter);
		flag = true;
		latch.await();
		long time = System.currentTimeMillis() - start;
		result.put(type, time);
		TimeUnit.SECONDS.sleep(1);
		flag = false;
		System.out.println(type + " - " + time);
	}
	
	private static void createWriteThread(CountDownLatch latch, Counter counter) {
		for(int i=0; i< READ_THREAD_NUM; i++) {
			executorService.submit(new WriteTask(latch, NUM/READ_THREAD_NUM, counter));
		}
	}
	
	private static void createReadThread(Counter counter) {
		for(int i=0; i< READ_THREAD_NUM; i++) {
			executorService.submit(new ReadTask(counter));
		}
	}
}
