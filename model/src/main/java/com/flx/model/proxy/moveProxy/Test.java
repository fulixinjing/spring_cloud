package com.flx.model.proxy.moveProxy;

public class Test {
	
	public static void main(String[] args) {
		jdk();
		//cglib();
	}

	private static void cglib() {
		UserProxy1 proxy1 = new UserProxy1();
		UserImpl impl=new UserImpl();
		UserImpl i = (UserImpl) proxy1.getInstance(impl);
		i.add();
	}

	private static void jdk() {
	/*	//创建一个实例对象，这个对象是被代理的对象
		UserInfo info =new UserImpl();
		//创建一个与代理对象相关联的InvocationHandler
		UserProxy userProxy = new UserProxy(info);
		//创建一个代理对象stuProxy来代理info，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
		UserInfo stuProxy = (UserInfo)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),info.getClass().getInterfaces(), userProxy);*/
		UserInfo info=new UserImpl();
		UserProxy proxy =new UserProxy(info);
		UserInfo instance = (UserInfo) proxy.getInstance(info);
		instance.add();
	}
	
}
