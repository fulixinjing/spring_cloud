package com.chj.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chj.dao.LoginDao;
import com.chj.dao.ScheduleDao;
import com.chj.dao.UserDao;
import com.chj.model.Login;
import com.chj.model.Schedule;

/**
 * 任务安排 dao
 * @author flx
 *
 */
@Repository
public class ScheduleDaoImpl implements ScheduleDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addSchedule(Schedule schedule) {
		
		sqlSession.insert("schedule.addSchedule",schedule);
	}

	
}
