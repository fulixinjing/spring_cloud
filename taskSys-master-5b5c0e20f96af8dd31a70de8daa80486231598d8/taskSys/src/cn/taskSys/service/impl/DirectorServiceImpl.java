package cn.taskSys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.AdminDao;
import cn.taskSys.dao.DirectorDao;
import cn.taskSys.dao.UserDao;
import cn.taskSys.dao.UtilsDao;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.DirectorService;
import cn.taskSys.service.IAdminService;
import cn.taskSys.utils.DateUtil;

@Service("DirectorService")
public class DirectorServiceImpl implements DirectorService {
	
	@Autowired
	private DirectorDao directorDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private UtilsDao utilsDao;
	
	@Autowired
	private UserDao userDao;
	
	public void setUtilsDao(UtilsDao utilsDao) {
		this.utilsDao = utilsDao;
	}

	/**
	 * 登录时查询用户角色
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	/**
	 * 保存创建的任务
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@LogAnnotation(eventCode="1022004",eventProcess="")
	public String saveTask(Map<String,String> map) {		 
		 return adminDao.saveTask(map);
	}

	@LogAnnotation(eventCode="1105001",eventProcess="")
	@Override
	public PagerModel getTaskList(Map<String, Object> map) throws Exception {
		try {
			PagerModel pagemodel = new PagerModel();
			List<TaskInfo> taskInfoList = adminDao.getTaskInfoList(map);
			pagemodel.setDatas(taskInfoList);
			Integer totalCount = adminDao.getTaskInfoSize(map);
			pagemodel.setTotal(totalCount);
			return pagemodel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@LogAnnotation(eventCode="1105002",eventProcess="")
	@Override
	public PagerModel getMyWorkbenchList(Map<String, Object> map) {
		PagerModel pagemodel = new PagerModel();
		List<TaskInfo> taskInfoList = adminDao.getMyWorkbenchInfoList(map);
		pagemodel.setDatas(taskInfoList);
		Integer totalCount = adminDao.getMyWorkbenchInfoSize(map);
		pagemodel.setTotal(totalCount);
		return pagemodel;
	}

	@LogAnnotation(eventCode="1105003",eventProcess="")
	@Override
	public String updateTaskInfo(TaskInfo taskInfo, int flag, String senderUserId) {
		try {
			adminDao.updateTaskInfo(taskInfo);
			
			TaskInfo ti = adminDao.getTaskInfoById(taskInfo.getId());
			//添加消息
			//flag 1拒绝接收，2提交拒绝,3分配，4接收，5确认
			Map<String, String> map = new HashMap<String, String>();
			
			switch (flag) {
			case 1:
				map.put("receiverUserId", ti.getAllocationUser());
				map.put("title", ti.getExec_name()+"拒绝了您分配的任务");
				map.put("messageStatus", "5");
				break;
			case 2:
				map.put("receiverUserId", ti.getExecutedevtasksys());
				map.put("title", ti.getCreate_name()+"拒绝了您提交的任务");
				map.put("messageStatus", "5");
				break;
			case 3:
				map.put("receiverUserId", ti.getExecutedevtasksys());
				map.put("title", ti.getCreate_name()+"给您分配了新任务！");
				map.put("messageStatus", "1");
				break;
			case 4:
				map.put("receiverUserId", ti.getAllocationUser());
				map.put("title", ti.getExec_name()+"接收了您分配的任务");
				map.put("messageStatus", "2");
				break;
			case 5:
				map.put("receiverUserId", ti.getExecutedevtasksys());
				map.put("title", ti.getCreate_name()+"确认了您提交的任务 ");
				map.put("messageStatus", "4");
				break;

			default:
				break;
			}
			
			map.put("senderUserId", senderUserId);
			map.put("sendTime", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			map.put("isdel", "0");
			map.put("isread", "0");
			map.put("taskInfoId", ti.getId());
			
			utilsDao.saveMessage(map);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	
	
	
	
	
	/**
	 * 主管：我创建的任务
	 */
	@LogAnnotation(eventCode="3105001",eventProcess="")
	@Override
	public PagerModel getMyCreateBenchList(Map<String, Object> qry) {
		// TODO Auto-generated method stub
		try {
			PagerModel pagemodel = new PagerModel();
			List<TaskInfo> taskInfoList = directorDao.getMyCreateTaskInfoList(qry);
			pagemodel.setDatas(taskInfoList);
			Integer totalCount = directorDao.getMyCreateTaskInfoSize(qry);
			pagemodel.setTotal(totalCount);
			return pagemodel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改任务  重新分配  状态改变
	 */
	@LogAnnotation(eventCode="3105002",eventProcess="")
	@Override
	public void updateTask(Map<String, String> map) {
		// TODO Auto-generated method stub
		directorDao.updateTask(map);
	}
	
	/**
	 * 根据修改任务是所选的执行人的id获取其详细信息，如所属部门、团队、岗位、职位
	 */
	@LogAnnotation(eventCode="3105003",eventProcess="")
	@Override
	public User getUserInfoById(String userId) {
		// TODO Auto-generated method stub
		return directorDao.getUserInfoById(userId);
	}
	
	/**
	 * 主管  我负责的任务List
	 */
	@LogAnnotation(eventCode="3105004",eventProcess="")
	@Override
	public PagerModel getMyResponsibleTaskList(Map<String, Object> qry) {
		// TODO Auto-generated method stub
		try {
			PagerModel pagemodel = new PagerModel();
			List<TaskInfo> taskInfoList = directorDao.getMyResponsibleTaskInfoList(qry);
			pagemodel.setDatas(taskInfoList);
			Integer totalCount = directorDao.getMyResponsibleTaskInfoSize(qry);
			pagemodel.setTotal(totalCount);
			return pagemodel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 主管  提交任务
	 */
	@LogAnnotation(eventCode="3105005",eventProcess="")
	@Override
	public void submitTask(Map<String, String> map) {
		// TODO Auto-generated method stub
		directorDao.submitTask(map);
	}

	@Override
	public String getSeqNextval() {
		// TODO Auto-generated method stub
		return directorDao.getSeqNextval();
	}
	
	/**
	 * 主管  向下分配任务   ----------  新建任务
	 */
	@LogAnnotation(eventCode="3105006",eventProcess="")
	@Override
	public void downAllocatingTask(Map<String, String> map) {
		// TODO Auto-generated method stub
		String id = userDao.getSeqNextval();
		directorDao.downAllocatingTask(map);
	}

	@Override
	public int checkFZR(Map<String, String> map) {
		// TODO Auto-generated method stub
		return directorDao.checkFZR(map);
	}

	@Override
	public TaskInfo getTaskInfoById(String id) {
		// TODO Auto-generated method stub
		return directorDao.getTaskInfoById(id);
	}


	@Override
	public int getSubtaskCounts(String id) {
		// TODO Auto-generated method stub
		return directorDao.getSubtaskCounts(id);
	}

	@Override
	public int getSubtaskStatus(String id) {
		// TODO Auto-generated method stub
		return directorDao.getSubtaskStatus(id);
	}

	@Override
	public List<Map<String, String>> getAllSonList(String id) {
		// TODO Auto-generated method stub
		return directorDao.getAllSonList(id);
	}


	@Override
	public List<Map<String, String>> getGrandsonTaskList(String id) {
		// TODO Auto-generated method stub
		return directorDao.getGrandsonTaskList(id);
	}

	@Override
	public PagerModel getSonTaskList(Map<String, Object> qry) {
		// TODO Auto-generated method stub
		try {
			PagerModel pagemodel = new PagerModel();
			List<Map<String,String>> sonTaskList = directorDao.getSonTaskList(qry);
			pagemodel.setDatas(sonTaskList);
			Integer sonTaskListSize = directorDao.getSonTaskListSize(qry);
			pagemodel.setTotal(sonTaskListSize);
			return pagemodel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PagerModel getSubtaskList(Map<String, Object> qry) {
		// TODO Auto-generated method stub
		try {
			PagerModel pagemodel = new PagerModel();
			List<TaskInfo> subtaskList = directorDao.getSubtaskList(qry);
			pagemodel.setDatas(subtaskList);
			Integer subtaskListSize = directorDao.getSubtaskListSize(qry);
			pagemodel.setTotal(subtaskListSize);
			return pagemodel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int checkSonTask(String id) {
		// TODO Auto-generated method stub
		return directorDao.checkSonTask(id);
	}

	@Override
	public void updateSonTask(Map<String, String> map) {
		// TODO Auto-generated method stub
		directorDao.updateSonTask(map);
	}

	@Override
	public int getGrandsonTask(String id) {
		// TODO Auto-generated method stub
		return directorDao.getGrandsonTask(id);
	}

	@Override
	public void updateGrandsonTask(Map<String, String> map) {
		// TODO Auto-generated method stub
		directorDao.updateGrandsonTask(map);
	}

	@Override
	public void deleteTask(Map<String, String> map) {
		// TODO Auto-generated method stub
		directorDao.deleteTask(map);
	}

	@Override
	public void deleteSonTask(Map<String, String> map) {
		// TODO Auto-generated method stub
		directorDao.deleteSonTask(map);
	}

	@Override
	public void deleteGrandsonTask(Map<String, String> map) {
		// TODO Auto-generated method stub
		directorDao.deleteGrandsonTask(map);
	}

	@Override
	public String getTaskStatus(String id) {
		// TODO Auto-generated method stub
		return directorDao.getTaskStatus(id);
	}

	@Override
	public int checkDeliver(Map<String, String> map) {
		// TODO Auto-generated method stub
		return directorDao.checkDeliver(map);
	}

	@Override
	public void changeDeliverStatus(Map<String, String> map) {
		// TODO Auto-generated method stub
		directorDao.changeDeliverStatus(map);
	}

}
