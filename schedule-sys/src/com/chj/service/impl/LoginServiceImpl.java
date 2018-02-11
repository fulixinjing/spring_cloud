package com.chj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chj.dao.LoginDao;
import com.chj.model.Login;
import com.chj.service.LoginService;

/**
 * 登陆 service
 * @author flx
 *
 */
@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private LoginDao loginDao;
	
	@Override
	public Login getUser(Login login) {
		
		return loginDao.getUser(login);
	}
	
	@Override
	public void addUser(Login login) {
		loginDao.addUser(login);
	}


}
