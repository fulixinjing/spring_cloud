package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.TaskOrg;


public interface TestOrgDao {

	/**
	 * 获取机构树查询列表（通过机构父Id）
	 * @param map
	 * @return
	 */	
	public List<TaskOrg> loadTaskOrgTree(Map<String,Object> map); 
	
	public List<TaskOrg> getOrgList(String orgId);
	
	public List<TaskOrg> getOrgListId(String orgId);
	

}
