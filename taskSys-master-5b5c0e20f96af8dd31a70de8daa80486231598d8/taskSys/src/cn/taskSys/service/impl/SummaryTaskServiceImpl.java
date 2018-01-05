package cn.taskSys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.SummaryTaskDao;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Employee;
import cn.taskSys.entity.User;
import cn.taskSys.service.ISummaryTaskService;
@Service("summaryTaskService")
public class SummaryTaskServiceImpl implements ISummaryTaskService {
	@Autowired
	private SummaryTaskDao summaryTaskDao;

	public List<Dictionary> allKinds(Map<String, Object> map2) {
		return summaryTaskDao.allKinds(map2);
	}

	@Override
	public List<User> allEmployees() {
		return summaryTaskDao.allEmployees();
	}


	@Override
	public int totalTask(Map<String, String> map) {
		
		return summaryTaskDao.totalTask(map);
	}

	@Override
	public int aswcTask(Map<String, String> map) {
		return summaryTaskDao.aswcTask(map);
	}

	@Override
	public int tqwcTask(Map<String, String> map) {
		return summaryTaskDao.tqwcTask(map);
	}

	@Override
	public int ycwcTask(Map<String, String> map) {
		return summaryTaskDao.ycwcTask(map);
	}

}
