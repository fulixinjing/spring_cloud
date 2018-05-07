package com.flx.model.adapter.objectAdapter;

public class Adapter implements Targetable{
	
	private Source source;
	
	public Adapter(Source source) {
		this.source = source;
	}

	@Override
	public void method1() {
		source.method1();
	}

	@Override
	public void method2() {
		System.out.println("新实现方法。。。。2");
	}

}
