package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;

public interface DirectorDao {
	
	 public String saveTask(Map<String, String> map) ;
	 
	 public List<TaskInfo> getTaskInfoList(Map<String, Object> map);
	 
	 public int getTaskInfoSize(Map<String, Object> map);
	 
	 public List<TaskInfo> getMyWorkbenchInfoList(Map<String, Object> map);
	 
	 public int getMyWorkbenchInfoSize(Map<String, Object> map);
	 
	 void updateTaskInfo(TaskInfo taskInfo);
	 
	 int getFinishCount();
	 
	 
	 
	 
	 
	 
	 

	public List<TaskInfo> getMyCreateTaskInfoList(Map<String, Object> qry);

	public Integer getMyCreateTaskInfoSize(Map<String, Object> qry);

	public void updateTask(Map<String, String> map);

	public User getUserInfoById(String userId);

	public List<TaskInfo> getMyResponsibleTaskInfoList(Map<String, Object> qry);

	public Integer getMyResponsibleTaskInfoSize(Map<String, Object> qry);

	public void submitTask(Map<String, String> map);

	public String getSeqNextval();

	public void downAllocatingTask(Map<String, String> map);

	public int checkFZR(Map<String, String> map);
	
	public TaskInfo getTaskInfoById(String id);


	public int getSubtaskCounts(String id);

	public int getSubtaskStatus(String id);

	public List<Map<String, String>> getAllSonList(String id);


	public List<Map<String, String>> getGrandsonTaskList(String id);

	public List<Map<String, String>> getSonTaskList(Map<String, Object> qry);

	public Integer getSonTaskListSize(Map<String, Object> qry);

	public List<TaskInfo> getSubtaskList(Map<String, Object> qry);

	public Integer getSubtaskListSize(Map<String, Object> qry);

	public int checkSonTask(String id);

	public void updateSonTask(String id);

	public void updateSonTask(Map<String, String> map);

	public int getGrandsonTask(String id);

	public void updateGrandsonTask(Map<String, String> map);

	public void deleteTask(Map<String, String> map);

	public void deleteSonTask(Map<String, String> map);

	public void deleteGrandsonTask(Map<String, String> map);

	public String getTaskStatus(String id);

	public int checkDeliver(Map<String, String> map);

	public void changeDeliverStatus(Map<String, String> map);

	
	 
	 
	 
	 
	 
	 
	 
}
