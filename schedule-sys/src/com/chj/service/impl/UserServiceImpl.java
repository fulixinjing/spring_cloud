package com.chj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chj.dao.LoginDao;
import com.chj.dao.UserDao;
import com.chj.model.Login;
import com.chj.service.LoginService;
import com.chj.service.UserService;

/**
 * 用户 service
 * @author flx
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	/**
	 * 修改密码
	 */
	@Override
	public void updateUser(Login user) {
		userDao.updateUser(user);
	}
	

}
