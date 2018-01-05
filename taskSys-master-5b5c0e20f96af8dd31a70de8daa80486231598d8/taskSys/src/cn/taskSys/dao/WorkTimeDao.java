package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.WorkTime;

public interface WorkTimeDao {
	
//	public void saveWorkTime(WorkTime workTime)throws Exception;
	
	public void saveWorkTime(List<WorkTime> workTimes)throws Exception;
	
	public List<Map<String, Object>> getWorkTimeSumByDepartment(String statisticsTime)throws Exception;
	
//	public List<WorkTime> getWorkTimeByPro(Map<String, Object> map) throws Exception;
	
	public List<WorkTime> getWorkTimeList1(WorkTime workTime) throws Exception;//分页列表
	
	public int getWorkTimeListCount1(WorkTime workTime) throws Exception;
	
	public List<WorkTime> getWorkTimeList2(WorkTime workTime) throws Exception;//分页列表
	
	public int getWorkTimeListCount2(WorkTime workTime) throws Exception;
	
	public List<WorkTime> exportWorkTimeList1(WorkTime workTime) throws Exception;//导出列表
	
	public List<WorkTime> exportWorkTimeList2(WorkTime workTime) throws Exception;//导出列表
	
	public void delWorkTime(WorkTime workTime) throws Exception;
	
	public List<WorkTime> getDepartWT(String statisticsTime);//查询部门工作量
	
	public List<Map<String, Object>> getAttenceDaySumByDepartment(String statisticsTime)throws Exception;//部门实际出勤总天数
	
	public List<Map<String, Object>> getWorkTimeSumByTeam(String statisticsTime)throws Exception;
	
	public List<Map<String, Object>> getAttenceDaySumByTeam(String statisticsTime)throws Exception;
}
