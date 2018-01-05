package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.entity.AttenceTemp;


public interface AttenceDetailsDao {
	
	public List<AttenceTemp>punchCardRecordList(Map<String, Object> map) throws Exception;//考勤记录
	
	void saveAttenceDetails(List<AttenceDetails> attenceDetailsList)throws Exception;//保存考勤详情
	
	List<AttenceDetails> getAttenceDetailsList(AttenceDetails attenceDetails) throws Exception;
	
	public List<AttenceDetails> getAllAttence(String nowDate) throws Exception;
	
	void modifyAttenceDetails(AttenceDetails attenceDetails) throws Exception;
	
	public List<AttenceDetails> getAttenceByEmpCode(Map<String, Object>map) throws Exception;
	
	void deleteAttenceDetailsByPro(AttenceDetails attenceDetails) throws Exception;
	
	void modifyRemarkFalg (String id) throws Exception;//处理后补打卡信息方法---修改
	
	public AttenceDetails getAttdetailsById(String attdetailsId) throws Exception;
//	AttenceDetails getAttenceDetails(AttenceDetails AttenceDetails) throws Exception;
	
	public List<String> findLeaveEmpcodeList() throws Exception;//离职员工的员工号
	
	public List<AttenceDetails> getLastList() throws Exception;//处理后补打卡信息方法---查询
	
	public int getAttenceDay(Map<String, String> map) throws Exception; //应出勤天数
	
	public List<AttenceDetails> findAttenceDetailsList(AttenceDetails attenceDetails) throws Exception;//分页列表
	
	public int findAttenceDetailsListCount(AttenceDetails attenceDetails) throws Exception;
	
	void updateAttenceDetailsList (List<AttenceDetails> attenceList) throws Exception;//批量修改
//	void updateAttenceDetailsList (AttenceDetails attenceList) throws Exception;//批量修改
}
