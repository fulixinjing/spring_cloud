package com.flx.model.singleton;
/**
 * 单例模式 饿汉式
 * @author flx
 *
 */
public class Singleton1 {

	private static final Singleton1 SINGLETON1=new Singleton1();
	
	public static Singleton1 getSingleton(){
		return SINGLETON1;
	}
}
