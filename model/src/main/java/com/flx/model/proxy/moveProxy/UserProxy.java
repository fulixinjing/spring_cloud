package com.flx.model.proxy.moveProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * jdk动态代理
 * @author flx
 *
 */
public class UserProxy implements InvocationHandler{

	private Object obj;
	public UserProxy(Object o) {
		this.obj = o;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("代理执行");
		Object object = null;
			 object = method.invoke(obj, args);
		return object;
	}
	/**
	 * 封装工具类 方便调用
	 * @return
	 */
	public Object getInstance(Object obj){
		Object o = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
		return o;
	}

}
