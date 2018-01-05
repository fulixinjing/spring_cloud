package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.AttenceDetails;

public interface IAttenceDetailsService {
	
	public List<AttenceDetails> getAllAttence(String nowDate) throws Exception;
	
	public List<AttenceDetails> getAttenceByEmpCode(Map<String, Object>map) throws Exception;
	
	void modifyRemarkFalg (String id) throws Exception;//处理后补打卡信息接口---修改
	
	List<AttenceDetails> getLastList () throws Exception;//处理后补打卡信息接口---查询
	
	public AttenceDetails getAttdetailsById(String attdetailsId) throws Exception;
	
	public List<String> findLeaveEmpcodeList() throws Exception;//离职员工的员工号
	
	public int getAttenceDay(Map<String, String> map) throws Exception; //应出勤天数
	
	List<AttenceDetails> getAttenceDetailsList(AttenceDetails attenceDetails) throws Exception;
	
	PageView<AttenceDetails> getAttenceDetailsListpageView(AttenceDetails attenceDetails)throws Exception;//分页列表
}
