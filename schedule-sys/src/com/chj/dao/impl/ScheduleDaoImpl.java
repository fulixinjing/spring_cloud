package com.chj.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public int getfindCount(Schedule schedule) {
		
		return (Integer) sqlSession.selectOne("schedule.getfindCount",schedule);
	}

	@Override
	public List<Schedule> getfindList(Schedule schedule) {
		
		return sqlSession.selectList("schedule.getfindList",schedule);
	}

	@Override
	public void delSchedule(Schedule schedule) {
		sqlSession.delete("schedule.delSchedule",schedule);
	}

	@Override
	public Schedule getScheduleById(Schedule schedule) {
		
		return (Schedule) sqlSession.selectOne("schedule.getScheduleById",schedule);
	}

	@Override
	public void updateSchedule(Schedule schedule) {
		sqlSession.update("schedule.updateSchedule",schedule);
	}

	@Override
	public List<Schedule> remind(String id) {
		return sqlSession.selectList("schedule.remind",id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getCount(Schedule schedule) {
		List<Map<String, Object>> selectList = sqlSession.selectList("schedule.getCount",schedule);
		return selectList;
	}

	
}
