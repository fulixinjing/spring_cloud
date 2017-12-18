package com.flx.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * @author flx
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD}) //ElementType.TYPE)接口、类、枚举、注解 ElementType.METHOD) //方法
@Retention(RetentionPolicy.RUNTIME)// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Inherited //说明子类可以继承父类中的该注解
public @interface LogAnnotation {
	
	String Type() default "service"; //拦截类的类型
}
