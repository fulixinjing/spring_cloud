package com.flx.thread;

import java.util.concurrent.Callable;

public class MyThread3 implements Callable<String>{

	@Override
	public String call() throws Exception {
		
		return "ddd";
	}

}
