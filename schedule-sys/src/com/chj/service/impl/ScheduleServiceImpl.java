package com.chj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chj.dao.LoginDao;
import com.chj.dao.ScheduleDao;
import com.chj.dao.UserDao;
import com.chj.model.Login;
import com.chj.model.Schedule;
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
	
	

}
