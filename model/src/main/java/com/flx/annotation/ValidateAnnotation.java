package com.flx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateAnnotation {
	
	int max() default 0;//最大值
	int[] size() default {0,0};
	
	public String message() default "";//default默认值
	
}
