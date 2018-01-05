package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Employee;
import cn.taskSys.entity.User;

public interface ISummaryTaskService {
	 
	 public List<Dictionary> allKinds(Map<String, Object> map2);
	 public List<User> allEmployees();
	 //------------
	 public int totalTask(Map<String, String> map);
	 public int aswcTask(Map<String, String> map);
	 public int tqwcTask(Map<String, String> map);
	 public int ycwcTask(Map<String, String> map);
}
