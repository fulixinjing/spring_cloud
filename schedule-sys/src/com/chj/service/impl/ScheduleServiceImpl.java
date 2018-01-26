package com.chj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chj.dao.LoginDao;
import com.chj.dao.ScheduleDao;
import com.chj.dao.UserDao;
import com.chj.model.Login;
import com.chj.model.Schedule;
import com.chj.pageUtil.Page;
import com.chj.service.LoginService;
import com.chj.service.ScheduleService;
import com.chj.service.UserService;

/**
 * 任务安排 service
 * @author flx
 *
 */
@Service
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
	private ScheduleDao scheduleDao;
	
	@Override
	public void addSchedule(Schedule schedule) {
		scheduleDao.addSchedule(schedule);
	}

	/**
	 * 今日安排分页
	 */
	@Override
	public Page<Schedule> getfindList(Schedule schedule) {
		Page<Schedule> page = schedule;
		//查询总条数
		int count = scheduleDao.getfindCount(schedule);
		page.setRecordTotal(count);//总条数
		//查询分页数据
	    List<Schedule> list = scheduleDao.getfindList(schedule);
	    page.setList(list);//分页数据
		return page;
	}
	/**
	 * 删除任务
	 */
	@Override
	public void delSchedule(Schedule schedule) {
		
		scheduleDao.delSchedule(schedule);
	}
	/**
	 * 根据id查询任务信息
	 */
	@Override
	public Schedule getScheduleById(Schedule schedule) {
		return scheduleDao.getScheduleById(schedule);
	}

	@Override
	public void updateSchedule(Schedule schedule) {
		scheduleDao.updateSchedule(schedule);
	}

	@Override
	public List<Schedule> remind(String id) {
		 List<Schedule> remind = scheduleDao.remind(id);
		
		 return remind;
	}
	/**
	 * 分日期统计每天任务数量
	 */
	@Override
	public List<Map<String, Object>> getCount(Schedule schedule) {
		return scheduleDao.getCount(schedule);
	}
	
	

}
