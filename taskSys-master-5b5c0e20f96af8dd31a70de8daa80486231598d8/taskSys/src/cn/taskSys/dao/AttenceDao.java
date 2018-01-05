package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Attence;
import cn.taskSys.entity.AttenceTemp;


public interface AttenceDao {

	public List<Attence> getAttenceList(Attence attence) throws Exception;//分页列表
	
	public int getAttenceListCount1(Attence attence) throws Exception;
	
	public List<AttenceTemp> punchCardRecordList(Map<String, Object> map) throws Exception;//考勤记录(mysql)
	
	public List<AttenceTemp> punchCardRecordList2(Map<String, Object> map) throws Exception;//新入职员工后来同步到数据库的考勤记录(mysql)
	
	public List<Map<String, String>> findSysUsers(List<String> empCodes) throws Exception;//科技公司所有人员工号(mysql)
	
	void saveAttence(List<Attence> attenceList) throws Exception;//保存考勤
	
	void deleteAttenceByPro(Attence attence) throws Exception;
	
	void modifyAttence(Attence attence) throws Exception;
	
	public List<Map<String, String>> findUsers() throws Exception;
	
}
