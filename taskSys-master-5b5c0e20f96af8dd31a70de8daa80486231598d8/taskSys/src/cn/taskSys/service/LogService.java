package cn.taskSys.service;

import java.util.HashMap;

import cn.taskSys.pager.PagerModel;





public interface LogService {
	 
	public int saveLog(String logtype,String logusername,String loguserlogin
			,String loginfo ,String logip ,String logmodul) throws Exception;
	
	
	public PagerModel getLogList(HashMap<String, Object> qry); 
	
}
