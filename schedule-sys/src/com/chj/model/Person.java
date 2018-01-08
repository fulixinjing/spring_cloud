package com.chj.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.management.relation.Role;

public class Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer personId;
	
	private String name;
	
	private Date birthday;
	
	private String personAddr;
	
	private String gender;

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPersonAddr() {
		return personAddr;
	}

	public void setPersonAddr(String personAddr) {
		this.personAddr = personAddr;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", name=" + name
				+ ", birthday=" + birthday + ", personAddr=" + personAddr
				+ ", gender=" + gender + "]";
	}
	
	

}
