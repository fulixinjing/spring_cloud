package com.chj.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chj.dao.LoginDao;
import com.chj.dao.UserDao;
import com.chj.model.Login;

/**
 * 用户 dao
 * @author flx
 *
 */
@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void updateUser(Login user) {
		sqlSession.update("login.updateUser",user);
	}
	

	
}
