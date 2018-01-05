package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.pager.PagerModel;

public interface IMyTaskService {
	
	PagerModel empMyTaskList(Map<String, Object> qry);
	
	PagerModel gaoGuanTaskList(Map<String, Object> qry);
	
	User getUserInfoById(String userId);
	
	public String roleCode(String userId);
}
