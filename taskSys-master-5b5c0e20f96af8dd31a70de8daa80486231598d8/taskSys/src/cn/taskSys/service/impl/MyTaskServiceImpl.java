package cn.taskSys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.taskSys.dao.MyTaskDao;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.IMyTaskService;

@Service("IMyTaskService")
public class MyTaskServiceImpl implements IMyTaskService {
	@Autowired
	private MyTaskDao myTaskDao;
	
	@LogAnnotation(eventCode="1021006",eventProcess="")
	@Override
	public PagerModel empMyTaskList(Map<String, Object> qry) {
		// TODO Auto-generated method stub
		try {
			PagerModel pagemodel = new PagerModel();
			List<TaskInfo> taskInfoList = myTaskDao.empMyTaskList(qry);
			pagemodel.setDatas(taskInfoList);
			Integer totalCount = myTaskDao.empMyTaskSize(qry);
			pagemodel.setTotal(totalCount);
			return pagemodel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@LogAnnotation(eventCode="1021007",eventProcess="")
	@Override
	public PagerModel gaoGuanTaskList(Map<String, Object> qry) {
		// TODO Auto-generated method stub
		try {
			PagerModel pagemodel = new PagerModel();
			List<TaskInfo> taskInfoList = myTaskDao.gaoGuanTaskList(qry);
			pagemodel.setDatas(taskInfoList);
			Integer totalCount = myTaskDao.gaoGuanTaskSize(qry);
			pagemodel.setTotal(totalCount);
			return pagemodel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据修改任务是所选的执行人的id获取其详细信息，如所属部门、团队、岗位、职位
	 */
	@LogAnnotation(eventCode="1021008",eventProcess="")
	@Override
	public User getUserInfoById(String userId) {
		// TODO Auto-generated method stub
		return myTaskDao.getUserInfoById(userId);
	}
	@LogAnnotation(eventCode="1021009",eventProcess="")
	@Override
	public String roleCode(String userId) {
		// TODO Auto-generated method stub
		return myTaskDao.roleCode(userId);
	}

}
