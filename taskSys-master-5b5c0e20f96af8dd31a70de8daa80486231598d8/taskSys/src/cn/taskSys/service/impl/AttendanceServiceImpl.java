package cn.taskSys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.AttendanceDao;
import cn.taskSys.entity.Attendance;
import cn.taskSys.service.IAttendanceService;

@Service("attendanceService")
public class AttendanceServiceImpl implements IAttendanceService {
	
	@Autowired
	private AttendanceDao attendanceDao;
	
	@Override
	public List<Attendance> getPreAttendance(Map<String, Object> map)
			throws Exception {
		return attendanceDao.getPreAttendance(map);
	}
	@Override
	public void addPreAttendance(Map<String, Object> map) throws Exception {
		attendanceDao.addPreAttendance(map);
	}

	@Override
	public void updatePreAttendance(Map<String, Object> map) throws Exception {
		attendanceDao.updatePreAttendance(map);
	}
	
}
