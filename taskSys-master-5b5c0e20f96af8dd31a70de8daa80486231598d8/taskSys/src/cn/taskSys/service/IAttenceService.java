package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.Attence;
import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.entity.AttenceTemp;

public interface IAttenceService {
	
	PageView<Attence> getAttenceListpageView(Attence attence)throws Exception;//分页列表
	
	List<AttenceTemp> punchCardRecordList(Map<String, Object> map) throws Exception;//考勤记录(mysql)
	
	List<Map<String, String>> findSysUsers(List<String> empCodes) throws Exception;//科技公司所有人员工号(mysql)
	
	List<AttenceTemp> punchCardRecordList2(Map<String, Object> map) throws Exception;//新入职员工后来同步到数据库的考勤记录(mysql)
	
	void saveAttenceDetails(List<AttenceDetails> attenceDetailsList, List<AttenceDetails> laterAttenceDetailsList)throws Exception;//保存考勤详情
	
	List<AttenceDetails> getAttenceDetailsList(AttenceDetails AttenceDetails) throws Exception;
	
	void modifyAttenceDetails(List<AttenceDetails> attenceDetailsList) throws Exception;
	
	public List<Map<String, String>> findUsers() throws Exception;
}
