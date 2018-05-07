package com.flx.model.proxy.staticProxy;


public class Test {
	
	public static void main(String[] args) {
		UserInfo userInfo = new UserImpl();
		UserInfo userProxy= new UserProxy(userInfo);
		userProxy.add();
		//静态代理 ： 静态代理也就是在程序运行前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了。
		//为其他对象提供一种代理以控制对这个对象的访问。在某些情况下，一个对象不适合或者不能直接引用另一个对象，而代理对象可以在客户端和目标对象之间起到中介的作用
	}
}
