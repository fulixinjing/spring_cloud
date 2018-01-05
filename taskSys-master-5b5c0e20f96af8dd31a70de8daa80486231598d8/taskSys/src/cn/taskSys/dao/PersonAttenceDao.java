package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.PersonAttence;


public interface PersonAttenceDao {
	
	public PersonAttence getPersonAttence(Map<String, Object> map);
	
	public void savePersonAttence(Map<String, Object> map);
	
	public void updatePersonAttence(Map<String, Object> map);
	
	public void updatePersonAttenceRemark(Map<String, Object> map);
	//个人中心条数
	public int personAttenceCount(PersonAttence personAttence);
	//个人中心列表
	public List<PersonAttence> personAttenceList(PersonAttence personAttence);
	//个人中心列表导出
	public List<PersonAttence> exportPersonAttenceList(PersonAttence personAttence);
		
}
