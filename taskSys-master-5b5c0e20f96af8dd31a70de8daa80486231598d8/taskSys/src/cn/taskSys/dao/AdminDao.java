package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.TaskInfo;

public interface AdminDao {
	
	 public String saveTask(Map<String, String> map) ;
	 
	 public List<TaskInfo> getTaskInfoList(Map<String, Object> map);
	 
	 public int getTaskInfoSize(Map<String, Object> map);
	 
	 public List<TaskInfo> getMyWorkbenchInfoList(Map<String, Object> map);
//	 
	 public int getTaskInfoSize1(TaskInfo taskInfo);
//	 
	 public List<Map<String, Object>> getTaskInfoList1(TaskInfo taskInfo);
	 
	 public int getMyWorkbenchInfoSize(Map<String, Object> map);
	 
	 public void updateTaskInfo(TaskInfo taskInfo);
	 
	 public Integer getFinishCount(TaskInfo taskInfo);
	 
	 public TaskInfo getTaskInfoById(String id);
	 
	 public List<TaskInfo> findTaskList(TaskInfo taskInfo);
	 
	 public int delTask(String id);
	 
	 public List<TaskInfo> getTaskList(TaskInfo taskInfo);
	 
	 public void delTasksByIds(List<String> ids);

	public int getDfpTaskSize(Map<String, Object> map);

	public int getDjsTaskSize(Map<String, Object> map);

	public int getDqrTaskSize(Map<String, Object> map);
	
	public int getDtgTaskSize(Map<String, Object> map);
	
	public List<TaskInfo> getAllTaskInfoList(Map<String, Object> map);//查询所有任务

	public List<TaskInfo> getAllTaskInfoList2(Map<String, Object> map);//查询所有任务
	
	public List<TaskInfo> getScoreTaskInfoList(Map<String, Object> map);//得分计算
	
	public List<TaskInfo> getScoreTaskInfoList2(Map<String, Object> map);//得分计算
	
	public List<TaskInfo> getunFinishTaskInfoList(Map<String, Object> map);//未完成任务

	public List<String> getAllExecutedevtasksysList(Map<String, Object> map);//获取所有任务执行人
	
	public List<TaskInfo> getTaskIdListByUserId(String userId);//根据用户id获取任务
	public List<TaskInfo> getTaskIdListByUserId2(String userId);//根据用户id获取任务
	
	public void updateExecName(TaskInfo taskInfo);
	
	public void updatecreateName(TaskInfo taskInfo);
	
	void checkTaskInfo(TaskInfo taskInfo);//审核任务
	
	public void updateTaskInfo2(TaskInfo taskInfo);
	
	public List<String> departmentTaskTotle(Map<String, Object> map);//部门提交的任务
	
	public List<TaskInfo> doingTaskWork(String userid);//未完成的任务--工作量计算
	
	public List<TaskInfo> endTaskWork(Map<String, Object> map);//已经完成的任务--工作量计算
	 
}
