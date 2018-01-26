package com.chj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chj.model.Schedule;
import com.chj.pageUtil.Page;

public interface ScheduleService {

	void addSchedule(Schedule schedule);

	Page<Schedule> getfindList(Schedule schedule);

	void delSchedule(Schedule schedule);

	Schedule getScheduleById(Schedule schedule);

	void updateSchedule(Schedule schedule);

	List<Schedule> remind(String id);

	List<Map<String, Object>> getCount(Schedule schedule);



}
