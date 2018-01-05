package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;

public interface MyTaskDao {
	
	public List<TaskInfo> empMyTaskList(Map<String, Object> map);
	
	public int empMyTaskSize(Map<String, Object> map);
	
	public int gaoGuanTaskSize(Map<String, Object> map);
	
	public List<TaskInfo> gaoGuanTaskList(Map<String, Object> map);
	
	public User getUserInfoById(String userId);
	
	public String roleCode(String userId);
}
