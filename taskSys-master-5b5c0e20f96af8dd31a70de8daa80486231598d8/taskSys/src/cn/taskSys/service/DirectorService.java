package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.pager.PagerModel;


public interface DirectorService {

	
	String saveTask(Map<String,String> map )throws Exception;
	
	PagerModel getTaskList(Map<String, Object> map)throws Exception;
	
	PagerModel getMyWorkbenchList(Map<String,Object> map);
	
	String updateTaskInfo(TaskInfo taskInfo, int flag, String senderUserId);
	
	

	PagerModel getMyCreateBenchList(Map<String, Object> qry);  //主管 我创建的任务

	void updateTask(Map<String, String> map);

	User getUserInfoById(String userId);

	PagerModel getMyResponsibleTaskList(Map<String, Object> qry); //主管  我负责的任务

	void submitTask(Map<String, String> map);  //提交任务

	String getSeqNextval();   //生成任务id

	void downAllocatingTask(Map<String, String> map);  //主管向下分配任务   也就是 主管新建任务

	int checkFZR(Map<String, String> map);     // 向下分配任务时 校验负责人是不是为同一人

	TaskInfo getTaskInfoById(String id);		//根据任务id查询任务详细信息

	PagerModel getSubtaskList(Map<String, Object> qry);

	int getSubtaskCounts(String id);//获取要提交的任务的子任务数量

	int getSubtaskStatus(String id);//获取要提交的任务的子任务状态为“已提交”的数量

	List<Map<String, String>> getAllSonList(String id);


	List<Map<String, String>> getGrandsonTaskList(String id);//根据任务id查出其孙子任务

	PagerModel getSonTaskList(Map<String, Object> qry);

	int checkSonTask(String id);

	void updateSonTask(Map<String, String> map);

	int getGrandsonTask(String id);

	void updateGrandsonTask(Map<String, String> map);

	void deleteTask(Map<String, String> map);

	void deleteSonTask(Map<String, String> map);

	void deleteGrandsonTask(Map<String, String> map);

	String getTaskStatus(String id);

	int checkDeliver(Map<String, String> map);

	void changeDeliverStatus(Map<String, String> map);


	
}
