package com.chj.service;

import java.util.HashMap;
import java.util.List;

import com.chj.model.Schedule;
import com.chj.pageUtil.Page;

public interface ScheduleService {

	void addSchedule(Schedule schedule);

	Page<Schedule> getfindList(Schedule schedule);

	void delSchedule(Schedule schedule);

	Schedule getScheduleById(Schedule schedule);

	void updateSchedule(Schedule schedule);

	HashMap<String, Object> remind(String id);



}
