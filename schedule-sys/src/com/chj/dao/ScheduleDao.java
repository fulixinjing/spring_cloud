package com.chj.dao;

import java.util.HashMap;
import java.util.List;

import com.chj.model.Login;
import com.chj.model.Schedule;

public interface ScheduleDao {

	void addSchedule(Schedule schedule);

	int getfindCount(Schedule schedule);

	List<Schedule> getfindList(Schedule schedule);

	void delSchedule(Schedule schedule);

	Schedule getScheduleById(Schedule schedule);

	void updateSchedule(Schedule schedule);

	List<HashMap<String, Object>> remind(String id);


}
