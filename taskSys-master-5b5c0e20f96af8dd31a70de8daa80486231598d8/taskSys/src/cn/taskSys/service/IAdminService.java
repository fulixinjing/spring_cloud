package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.Grades;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.UserScore;
import cn.taskSys.pager.PagerModel;


public interface IAdminService {

	
	String saveTask(Map<String,String> map )throws Exception;
	
	PagerModel getTaskList(Map<String, Object> map)throws Exception;
	
	PageView<Map<String, Object>> getTaskListpageView( TaskInfo taskInfo)throws Exception;
	
	PagerModel getMyWorkbenchList(Map<String,Object> map);
	
	String updateTaskInfo(TaskInfo taskInfo, int flag, String senderUserId, String execName);
	
	Integer getFinishTaskCount(TaskInfo taskInfo);
	
	List<TaskInfo> findTaskListByProperties(TaskInfo taskInfo);
	
	int delTask(String id);
	
	TaskInfo getTaskInfoById(String id);
	
	List<TaskInfo> getTaskList(TaskInfo taskInfo);
	
	void delTaskByIds(List<String> ids);

	int getDfpTaskSize(Map<String, Object> map);		//工作台   待分配   任务数

	int getDjsTaskSize(Map<String, Object> map);		//工作台   待接收   任务数

	int getDqrTaskSize(Map<String, Object> map);	//工作台   待确认   任务数
	
	int getDtgTaskSize(Map<String, Object> map);	//工作台   待通过   任务数
	
	public List<TaskInfo> getAllTaskInfoList(Map<String, Object> map); //查询当前月所有任务

	public List<TaskInfo> getAllTaskInfoList2(Map<String, Object> map); //查询当前月所有任务
	
	public List<TaskInfo> getScoreTaskInfoList(Map<String, Object> maps); //得分计算

	public List<TaskInfo> getScoreTaskInfoList2(Map<String, Object> maps); //得分计算
	
	public List<TaskInfo> getunFinishTaskInfoList(Map<String, Object> map);//未完成任务
	
	void saveScore(List<Grades> gradesList, String dateStr)throws Exception;
	
	public List<String> getAllExecutedevtasksysList(Map<String, Object> map);//获取所有任务执行人
	
	Grades getScoreByPro(Map<String, Object> map) throws Exception;
	
	List<TaskInfo> getTaskIdListByUserId(String userId);//根据用户id获取任务ids
	
	void updateExecName(TaskInfo taskInfo);
	
	void updatecreateName(TaskInfo taskInfo);
	
	void checkTaskInfo(TaskInfo taskInfo);//审核任务
	
	PageView<UserScore> getScoreListpageView(UserScore userScore)throws Exception;//用户分数分页列表

	public List<UserScore> getScoreList2(UserScore userScore) throws Exception;//用户分数导出列表
	
	public void delScore(UserScore userScore) throws Exception;//根据条件删除用户分数列表
	
	public List<String> departmentTaskTotle(Map<String, Object> map);//获取部门提交的任务
	
	
	public List<TaskInfo> doingTaskWork(String userid);//未完成的任务--工作量计算
	
	public List<TaskInfo> endTaskWork(Map<String, Object> map);//已经完成的任务--工作量计算
		
}
