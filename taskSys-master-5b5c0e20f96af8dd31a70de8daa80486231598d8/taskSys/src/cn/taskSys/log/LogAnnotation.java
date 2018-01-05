package cn.taskSys.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解接口
 * 
 * @author 
 * @since 2014-11-12
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
	/**
	 * 事件代码
	 * 
	 * @return
	 */
	String eventCode() default "";// USM101

	/**
	 * 事件信息
	 * 
	 * @return
	 */
	String eventProcess() default "";// UserService doSaveUser : User

	/**
	 * 操作者
	 * 
	 * @return
	 */
	String operator() default "";// UserName
	
	/**
	 * 模块名称
	 * @return
	 */
	String operateModule() default "";


	/**
	 * 实体
	 * 
	 * @return
	 */
	String entity() default "";// ClassT.getName()

	/**
	 * 实体状态
	 * 
	 * @return
	 */
	String entityStatus() default "";// CREATE\UPDATE\DELETE

	/**
	 * 注解类型 ，如controller\entity\other
	 * 
	 * @return
	 */
	String type() default "controller";// controller\entity\other
}
