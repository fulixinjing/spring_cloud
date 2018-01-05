package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.TaskInfo;


public interface TaskQueryDao {

	/**
	 * 获取机构树查询列表（通过机构父Id）
	 * @param map
	 * @return
	 */	
	public List<TaskInfo> loadTaskOrgTree(Map<String,Object> map); 
	
	public List<TaskInfo> getOrgList(String orgId);
	
	public List<TaskInfo> getOrgListId(String orgId);
	
	/**
	 * 
	 * //TODO 根据任务id查询任务详情内容
	 * @param id
	 * @return
	 */
	public TaskInfo getTaskInfoById(String id);
	

}
