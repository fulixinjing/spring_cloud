package cn.taskSys.service;

import java.util.List;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.WorkTime;

public interface IWorkTimeService {
	
	public void saveWorkTime(List<WorkTime> workTimes)throws Exception;
	
//	public List<Map<String, String>> getWorkTimeSumByDepartment(String statisticsTime)throws Exception;
	PageView<WorkTime> getWorkTimeListpageView(WorkTime workTime)throws Exception;//分页列表
	
	public List<WorkTime> exportWorkTimeList(WorkTime workTime) throws Exception;//导出列表
	
	
	public List<WorkTime> getDepartWT(String statisticsTime);//查询部门工作量
}
