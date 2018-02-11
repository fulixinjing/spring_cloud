package com.creditloanservice.example.feigninterface;

import org.springframework.stereotype.Component;

@Component
public class TestFeign implements ITestFeign {

	@Override
	public String sayHiFromClientOne(String name) {
		// TODO Auto-generated method stub
		return "sorry " + name;
	}

}
