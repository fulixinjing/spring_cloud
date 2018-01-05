package cn.taskSys.service;

import java.util.Map;

import org.json.JSONArray;

import cn.taskSys.entity.TaskInfo;


public interface ITaskQueryService {

	//测试
	String getModuleTree(String noid)throws Exception;
	
	String getTimerTree(Map<String, Object> map)throws Exception;
	
	JSONArray getSubTimerTree(String organId)throws Exception;
	
	public TaskInfo getTaskInfoById(String id);

}
