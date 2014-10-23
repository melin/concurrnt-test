package com.github.melin.concurrnt.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

public class TestCompletableFuture {
	
	/**
	 * supplyAsync 创建future, 并获取返回返回值，默认使用ForkJoinPool.commonPool() 线程池
	 * runAsync 创建future, 无返回值，默认使用ForkJoinPool.commonPool() 线程池
	 * 
	 * get() 获取执行返回值，如果没有执行完成，一直等待执行
	 * get(timeout, unit)  获取执行返回值，如果没有执行完成，阻塞等待，直至超时时间。
	 * getNow(default) 如果Future没有运行完成返回值，则返回默认值
	 * complete(default) 如果没有执行完成，设置get方法的返回值，中断get方法等待。
	 * completeExceptionally(throw) 如果没有执行完成，get方法抛出此异常
	 */
	//@Test
	public void test0() {
		Executor executor = Executors.newFixedThreadPool(2);
		final CompletableFuture<String> future = CompletableFuture.supplyAsync(
				new Supplier<String>() {
					@Override
					public String get() {
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return "42";
					}
				}, executor);
		/*future.complete("10");
		try {
			System.out.println(future.get(2, TimeUnit.SECONDS));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		boolean flag = future.completeExceptionally(new RuntimeException("ceshi"));
		System.out.println(flag);
		try {
			System.out.println(future.getNow("12"));
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * thenApply、thenApplyAsync : 转换叠加处理多个future
	 * thenAccept、thenAcceptAsync : future 执行完成以后调用
	 * thenRun、thenRunAsync : 与thenAccept类似，只是不能获取future的返回值。
	 * 
	 */
	//@Test
	public void test1() {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {

			@Override
			public String get() {
				System.out.println(Thread.currentThread().getName());
				return "42";
			}
			
		});
		CompletableFuture<Integer> future1 = future.thenApply(new Function<String, Integer>() {

			@Override
			public Integer apply(String t) {
				System.out.println(Thread.currentThread().getName());
				return Integer.valueOf(t);
			}
		});
		
		future1.thenAcceptAsync(new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
				System.out.println(t);
			}
		});
		System.out.println("finished......");
	}
	
	/**
	 * exceptionally : 当future执行出现异常时，被调用
	 * handle、handleAsync ： 不管执行成功还是失败，handle都会被执行，如果执行成功，Throwable值为null
	 */
	@Test
	public void test2() throws Exception {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {

			@Override
			public String get() {
				return "10";
				//throw new RuntimeException("test exception");
			}
			
		})/*.exceptionally(new Function<Throwable, String>() {

			@Override
			public String apply(Throwable t) {
				return "-1";
			}
		})*/.handleAsync(new BiFunction<String, Throwable, String>() {

			@Override
			public String apply(String t, Throwable e) {
				System.out.println(Thread.currentThread().getName());
				if(e == null)
					return "100";
				else {
					System.out.println(e.getMessage());
					return "-1";
				}
			}
		});
		System.out.println(future.get());
		System.out.println("finished......");
	}
}
