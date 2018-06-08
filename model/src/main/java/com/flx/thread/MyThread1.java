package com.flx.thread;

public class MyThread1 implements Runnable{

	@Override
	public void run() {
		for(int i = 0 ;i < 10; i++){
			System.out.println("mythread1........."+i);
				
			try {
				if(i ==5){
					this.wait();
				//Thread.sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
