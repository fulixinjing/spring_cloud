package cn.taskSys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Log;


public interface LogDao {
	 
	 public int saveLog(Map map);
	 
	 public List<Log> getLogList(HashMap<String, Object> qry);
	 
	 public int getLogSize(HashMap<String, Object> qry);
	 
}
