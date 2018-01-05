package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Attendance;

public interface AttendanceDao {
	
	public List<Attendance> getPreAttendance(Map<String, Object> map) throws Exception;
	
	public void addPreAttendance(Map<String, Object> map) throws Exception;
	
	public void updatePreAttendance(Map<String, Object> map) throws Exception;
	
}
