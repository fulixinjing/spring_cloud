package com.flx.model.adapter.objectAdapter;

public class Test {
	
	public static void main(String[] args) {
		Source source =new Source(); 
		Targetable targetable = new Adapter(source);
		targetable.method1();
		targetable.method2();
		//输出与类适配一样，只是适配的方法不同而已。
	}
}
