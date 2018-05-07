package com.flx.model.singleton;
/**
 * 单例模式 懒汉式
 * @author flx
 *
 */
public class Singleton2 {
	
	private static Singleton2 singleton2 = null;
	//限制产生多个对象
	private Singleton2(){
		
	}
	public static Singleton2 getSingleton(){
		//线程不安全
		//解决办法：在getSingleton方法前加synchronized关键字，也可以在getSingleton方法内增加synchronized来实现
		if(singleton2 == null){
			singleton2 = new Singleton2();
		}
		return singleton2;
	}
}
