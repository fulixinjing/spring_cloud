package cn.taskSys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.LogDao;
import cn.taskSys.entity.Log;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.LogService;

@Service("LogService")
public class LogServiceImpl implements LogService {
	@Autowired
	private LogDao logDao;

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	@LogAnnotation(eventCode="2020001",eventProcess="")
	public int saveLog(String logtype,String logusername,String loguserlogin
			,String loginfo ,String logip ,String logmodul) {
		
		Map map=new HashMap();
		map.put("logtype", logtype);
		map.put("logusername", logusername);
		map.put("loguserlogin", loguserlogin);
		map.put("loginfo", loginfo);
		map.put("logip", logip);
		map.put("logmodul", logmodul);
		map.put("logSource", "0");//来源
		
		return logDao.saveLog(map); 
	}
	
	
	@LogAnnotation(eventCode="2020003",eventProcess="")
	public PagerModel getLogList(HashMap<String, Object> qry){
		PagerModel pagemodel = new PagerModel();
		List<Log> logList = logDao.getLogList(qry);
		pagemodel.setDatas(logList);
		Integer totalCount = logDao.getLogSize(qry);
		pagemodel.setTotal(totalCount);
		return pagemodel;
	}
}
