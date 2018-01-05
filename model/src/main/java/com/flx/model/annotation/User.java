package com.flx.model.annotation;

import org.springframework.stereotype.Component;

@Component
public class User {
	private String name;
	
	private String sex;
	
	@ValidateAnnotation(max = 40,message = "哈哈，太大了")
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
