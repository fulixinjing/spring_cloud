package cn.taskSys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.AdminDao;
import cn.taskSys.dao.GradesDao;
import cn.taskSys.dao.UserDao;
import cn.taskSys.dao.UtilsDao;
import cn.taskSys.entity.Grades;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.entity.UserScore;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.IAdminService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.SendEmailUtil;

@Service("adminService")
public class AdminServiceImpl implements IAdminService {
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private UtilsDao utilsDao;
	
	@Autowired
	private GradesDao gradesDao;
	
	@Autowired
	private UserDao userDao;
	
//	@Autowired
//	private MessageDao messageDao;
//	
//	public void setMessageDao(MessageDao messageDao) {
//		this.messageDao = messageDao;
//	}

	public void setUtilsDao(UtilsDao utilsDao) {
		this.utilsDao = utilsDao;
	}

	public void setGradesDao(GradesDao gradesDao) {
		this.gradesDao = gradesDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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
	@LogAnnotation(eventCode="1021004",eventProcess="")
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
	public String updateTaskInfo(TaskInfo taskInfo, int flag, String senderUserId, String execName) {
		try {
			if (flag==2) {//延迟提交拒绝，状态返回4
				TaskInfo taskInfoT = adminDao.getTaskInfoById(taskInfo.getId());
				if (taskInfoT.getTaskstatus().equals("5")) {
					taskInfo.setTaskstatus("4");
				}
			}
			adminDao.updateTaskInfo(taskInfo);
			
			TaskInfo ti = adminDao.getTaskInfoById(taskInfo.getId());
			
			/**
			 * 确认打分之后，如果该任务给其他部门转交了新任务，
			 * 修改转交的新任务is_pass=0，并推送已转交部门经理待通过列表
			 */
			if(taskInfo.getTaskstatus()!=null && taskInfo.getTaskstatus().equals("8")){
				TaskInfo tInfo = new TaskInfo();
				tInfo.setDeliver_taskid(ti.getId());
				List<TaskInfo> taskList = adminDao.getTaskList(tInfo);
				if(taskList!=null && taskList.size()>0){
					//tInfo.setId(taskList.get(0).getId());
					tInfo.setIs_pass("0");
					adminDao.updateTaskInfo2(tInfo);
					
					//转交后发送邮件提醒
					for (TaskInfo taskInfo2 : taskList) {
						//查询邮件转交人
						User user = new User();
						user.setUserId(taskInfo2.getDeliver_person());
						user = userDao.getUser(user);
						//查询部门经理
						List<User> users = userDao.getDepartmentManagerById(taskInfo2.getExecutedevtasksys());
						
						Map<String, String> map = new HashMap<String, String>();
						map.put("to_email", users.get(0).getEmail());//邮件接收人（部门经理）
						map.put("create_name", user.getUserName());//转交人
						map.put("taskContent", taskInfo2.getTaskContent());
						SendEmailUtil.deliverTaskEmail(map);
					}
				}
				
			}
			
//			//添加新消息之前，将之前消息标为已读（过时）
//			messageDao.updateMessageByTaskId(ti.getId());
			//添加新消息
			//flag 1拒绝接收，2提交拒绝,3分配，4接收，5确认
			Map<String, String> map = new HashMap<String, String>();
			
			switch (flag) {
			case 1:
				map.put("receiverUserId", ti.getAllocationUser());
				map.put("title", execName+"拒绝了您分配的任务");
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

	@LogAnnotation(eventCode="1105004",eventProcess="")
	@Override
	public Integer getFinishTaskCount(TaskInfo taskInfo) {
		return adminDao.getFinishCount(taskInfo);
	}


	@LogAnnotation(eventCode="1105005",eventProcess="")
	@Override
	public PageView<Map<String, Object>> getTaskListpageView( TaskInfo taskInfo){
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				taskInfo.getMaxResult(),
				taskInfo.getPage());// 需要设置当前页
		try {	
			// 获取列表条数
			int count = adminDao.getTaskInfoSize1(taskInfo);
			// 获取列表数据
			List<Map<String, Object>> lcmList = adminDao.getTaskInfoList1(taskInfo);
	
			QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
			qr.setResultlist(lcmList);
			qr.setTotalrecord(count);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageView;
	}
	@Override
	public List<TaskInfo> findTaskListByProperties(TaskInfo taskInfo) {
		return adminDao.findTaskList(taskInfo);
	}

	@Override
	public int delTask(String id) {
		return adminDao.delTask(id);
	}

	@Override
	public TaskInfo getTaskInfoById(String id) {
		return adminDao.getTaskInfoById(id);
	}

	@Override
	public List<TaskInfo> getTaskList(TaskInfo taskInfo) {
		return adminDao.getTaskList(taskInfo);
	}

	@Override
	public void delTaskByIds(List<String> ids) {
		adminDao.delTasksByIds(ids);
	}

	@Override
	public int getDfpTaskSize(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return adminDao.getDfpTaskSize(map);
	}

	@Override
	public int getDjsTaskSize(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return adminDao.getDjsTaskSize(map);
	}

	@Override
	public int getDqrTaskSize(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return adminDao.getDqrTaskSize(map);
	}
	
	@Override
	public int getDtgTaskSize(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return adminDao.getDtgTaskSize(map);
	}
	

	@Override
	public List<TaskInfo> getScoreTaskInfoList(Map<String, Object> maps) {
		// TODO Auto-generated method stub
		return adminDao.getScoreTaskInfoList(maps);
	}
	
	@Override
	public List<TaskInfo> getAllTaskInfoList(Map<String, Object> maps) {
		// TODO Auto-generated method stub
		return adminDao.getAllTaskInfoList(maps);
	}
	
	@Override
	public List<TaskInfo> getScoreTaskInfoList2(Map<String, Object> maps) {
		// TODO Auto-generated method stub
		return adminDao.getScoreTaskInfoList2(maps);
	}

	@Override
	public void saveScore(List<Grades> gradesList, String dateStr){
		try {
			if (gradesList!=null && gradesList.size()>0) {
				UserScore userScore = new UserScore();
				userScore.setG_month(dateStr);
				gradesDao.delScore(userScore);
				
				gradesDao.saveScore(gradesList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getAllExecutedevtasksysList(Map<String, Object> map) {
		return adminDao.getAllExecutedevtasksysList(map);
	}

	@Override
	public Grades getScoreByPro(Map<String, Object> map) throws Exception {
		List<Grades> scoreList = gradesDao.getScoreByPro(map);
		if(scoreList!=null && scoreList.size()>0){
			return scoreList.get(0);
		}
		return null;
	}

	@Override
	public List<TaskInfo> getTaskIdListByUserId(String userId) {
		return adminDao.getTaskIdListByUserId(userId);
	}

	@Override
	public void updateExecName(TaskInfo taskInfo) {
		adminDao.updateExecName(taskInfo);
	}

	@Override
	public void updatecreateName(TaskInfo taskInfo) {
		adminDao.updatecreateName(taskInfo);
	}

	@Override
	public void checkTaskInfo(TaskInfo taskInfo) {
		adminDao.checkTaskInfo(taskInfo);
	}

	@Override
	public List<UserScore> getScoreList2(UserScore userScore)
			throws Exception {
		return gradesDao.getScoreList2(userScore);
	}

	@Override
	public PageView<UserScore> getScoreListpageView(UserScore userScore)
			throws Exception {
		PageView<UserScore> pageView = new PageView<UserScore>(
				userScore.getMaxResult(),
				userScore.getPage());// 需要设置当前页
		try {	
			// 获取列表条数
			int count = gradesDao.getScoreListCount(userScore);
			// 获取列表数据
			List<UserScore> scoreList = gradesDao.getScoreList(userScore);
	
			QueryResult<UserScore> qr = new QueryResult<UserScore>();
			qr.setResultlist(scoreList);
			qr.setTotalrecord(count);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageView;
	}

	@Override
	public void delScore(UserScore userScore) throws Exception {
		gradesDao.delScore(userScore);
		
	}

	@Override
	public List<TaskInfo> getunFinishTaskInfoList(Map<String, Object> map) {
		return adminDao.getunFinishTaskInfoList(map);
	}

	@Override
	public List<TaskInfo> getAllTaskInfoList2(Map<String, Object> map) {
		return adminDao.getAllTaskInfoList2(map);
	}
	
	@Override
	public List<String> departmentTaskTotle(Map<String, Object> map) {
		return adminDao.departmentTaskTotle(map);
	}

	@Override
	public List<TaskInfo> doingTaskWork(String userId) {
		return adminDao.doingTaskWork(userId);
	}
	@Override
	public List<TaskInfo> endTaskWork(Map<String, Object> map) {
		return adminDao.endTaskWork(map);
	}
	
}
