package com.flx.model.annotation;

import org.springframework.stereotype.Service;

@Service
public class AnnotationService {
	
	@LogAnnotation
	public void test() {
		System.out.println("test..AnnotationService");
	}

}
