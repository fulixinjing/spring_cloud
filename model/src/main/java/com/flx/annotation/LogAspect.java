package com.flx.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面
 * @author flx
 *
 */
@Component
@Aspect //作用是把当前类标识为一个切面供容器读取
public class LogAspect {

	@Pointcut(value ="@annotation(com.flx.annotation.LogAnnotation)") //切入点
	public void aspect(){}
	@Before("aspect()")
	public void doBefore(JoinPoint jp){
		// Logger	log  =LoggerFactory.getLogger(jp.getTarget().getClass());
		 System.out.println("前置通知");
	}
	@After("aspect()")
	public void doAfter(JoinPoint jp){
		System.out.println("后置通知");
	}
	@AfterThrowing(pointcut="aspect()", throwing="ex")
	public void doThrowing(JoinPoint jp, Throwable ex) {
		System.out.println("异常通知");
		ex.printStackTrace();
	}
}
