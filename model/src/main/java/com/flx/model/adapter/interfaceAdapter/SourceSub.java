package com.flx.model.adapter.interfaceAdapter;

public class SourceSub extends Wrapper{

	//抽象类Wrapper已经实现了接口中的所有方法，现在 重写我们需要的方法就行
	@Override
	public void method2() {
		System.out.println("需要方法。。。。。。2");
	}

}
