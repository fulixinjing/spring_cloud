package com.flx.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadTest {

	public static void main(String[] args) throws Exception {
		Thread thread1 = new Thread(new MyThread1(),"th1");
		thread1.setPriority(10);
		thread1.start();
		Thread thread2 = new Thread(new MyThread2(),"th2");
		thread2.start();
		//thread1.join();
		for(int i = 0 ;i < 10; i++){
			System.out.println("main........."+i);
		}
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		Future<String> submit = pool.submit(new MyThread3());
		
		//submit.get()等待线程结束之后执行
		System.out.println(submit.get());
	}
}
