package com.flx.model.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;

/**
 * 单例模式 懒汉式
 * @author flx
 *
 */
public class Singleton2 implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Singleton2 singleton2 = null;
	
	private static boolean initialized = false;
	//限制产生多个对象
	private Singleton2(){
		synchronized (Singleton2.class) {
			if(initialized == false){
				initialized = !initialized;
			}else{
				throw new RuntimeException("单例模式遭到破坏");
			}
		}
	}
	/**
	 * v1版本 缺点：线程不安全
	 * @return
	 */
	public static Singleton2 getSingleton1(){
		//线程不安全
		//解决办法：在getSingleton方法前加synchronized关键字，也可以在getSingleton方法内增加synchronized来实现
		if(singleton2 == null){
			singleton2 = new Singleton2();
		}
		return singleton2;
	}
	/**
	 * v2版 双重校验锁
	 * 缺点：性能低
	 * @return
	 */
	public static Singleton2 getSingleton2(){
		if(singleton2 == null){
			 synchronized (Singleton2.class) {
				 if(singleton2 == null){
						singleton2 = new Singleton2();
					}
			}
		}
		return singleton2;
	}
	static class Singletion{
		private static final Singleton2 SINGLETON2=new Singleton2();
	}
	/**
	 * v3版  推荐使用
	 * 由于对象实例化是在内部类加载的时候构建的，因此该版是线程安全的
	 * 没有使用synchronized关键字，因此没有造成多余的性能损耗
	 * 缺点：在反射的作用下，单例结构是会被破坏的  ，测试方法test1()
	 * @return
	 */
	public static Singleton2 getSingleton3(){
		return Singletion.SINGLETON2;
	}
	/**
	 * v4版 
	 * 在v3版的基础上修改 在Singleton2的构造中添加判断，如果非第一次创建就报错
	 * 缺点:如果单例序列化，反序列化之后就变成了新对象 测试方法test2()
	 * @return
	 */
	public static Singleton2 getSingleton4(){
		return Singletion.SINGLETON2;
	}
	/**
	 * v5版 
	 * 在v4基础上修改 添加 readResolve()方法
	 * readResolve(）代替了从流中读取对象。这就确保了在序列化和反序列化的过程中没人可以创建新的实例
	 * @return
	 */
	public static Singleton2 getSingleton5(){
		return Singletion.SINGLETON2;
	}
	
	public static void main(String[] args) throws Exception {
		//test1();
		test2();
	}
	  private Object readResolve() {
	        return getSingleton4();
	    }
	private static void test2() {
		Singleton2 singleton4 = Singleton2.getSingleton4();
		Singleton2 singleton5 = null;
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:/file.ser"));
			out.writeObject(singleton4);
			out.close();
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:/file.ser"));
			singleton5 = (Singleton2) in.readObject();
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println(singleton4);
		System.out.println(singleton5);
	}
	private static void test1() throws Exception{
		Singleton2 singleton3 = Singleton2.getSingleton4();
		Singleton2 singleton4 =null;
		
			Constructor<Singleton2> declaredConstructor = Singleton2.class.getDeclaredConstructor();
			declaredConstructor.setAccessible(true);
			singleton4 = declaredConstructor.newInstance();
		
		System.out.println(singleton3.hashCode());
		System.out.println(singleton4.hashCode());
	}
	
	
	
}
