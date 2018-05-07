package com.flx.annotation;

import org.springframework.stereotype.Service;

@Service
public class AnnotationService {
	
	public void test1() {
		System.out.println("11111");
		test();
	}
	
	@LogAnnotation
	public void test() {
		System.out.println("test..AnnotationService");
	}

}
