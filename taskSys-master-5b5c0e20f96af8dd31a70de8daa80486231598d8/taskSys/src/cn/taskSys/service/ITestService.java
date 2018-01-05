package cn.taskSys.service;

import org.json.JSONArray;


public interface ITestService {

	//测试
	String getModuleTree(String noid)throws Exception;
	
	String getTimerTree(String organId)throws Exception;
	
	JSONArray getSubTimerTree(String organId)throws Exception;

}
