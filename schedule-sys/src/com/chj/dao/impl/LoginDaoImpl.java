package com.chj.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chj.dao.LoginDao;
import com.chj.model.Login;

/**
 * 登陆 dao
 * @author flx
 *
 */
@Repository
public class LoginDaoImpl implements LoginDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Login getUser(Login login) {
		
		return (Login)sqlSession.selectOne("login.getUser",login);
	}

	
}
