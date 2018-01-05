package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.PersonAttence;

public interface IPersonAttenceService {
	
	PageView<PersonAttence> personAttenceListpageView(PersonAttence personAttence)throws Exception;//个人中心分页列表
	
	public PersonAttence getPersonAttence(Map<String, Object> map);
	
	public void updatePersonAttence(Map<String, Object> map);
	
	public void savePersonAttence(Map<String, Object> map);
	
	public void updatePersonAttenceRemark(Map<String, Object> map);
	
	public List<PersonAttence> personAttenceList (PersonAttence personAttence);//导出列表
	
}
