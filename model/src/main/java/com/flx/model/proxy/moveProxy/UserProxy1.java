package com.flx.model.proxy.moveProxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * cglib动态代理
 * @author flx
 *
 */
public class UserProxy1 implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		System.out.println("执行之前"+methodProxy.getSuperName());
		Object invokeSuper = methodProxy.invokeSuper(obj, args);
		return invokeSuper;
	}
	/**
	 * 封装工具类，方便调用
	 * @param obj
	 * @return
	 */
	public Object getInstance(Object obj){
		Enhancer enhancer =new Enhancer();
		enhancer.setSuperclass(obj.getClass());
		enhancer.setCallback(this);
		Object create = enhancer.create();
		return create;
	}

}
