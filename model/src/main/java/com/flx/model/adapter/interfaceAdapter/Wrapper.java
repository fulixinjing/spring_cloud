package com.flx.model.adapter.interfaceAdapter;

public abstract class Wrapper implements Sourceable{
	@Override
	public void method2() {
		System.out.println("实现方法。。。。2");
	}
	@Override
	public void method1() {
		System.out.println("实现方法。。。。1");
		
	}
}
