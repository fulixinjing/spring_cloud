package cn.taskSys.service;

import java.util.List;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.entity.OALeave;
import cn.taskSys.entity.OAOnBusiness;
import cn.taskSys.entity.OAOut;


public interface IOALeaveService {
	
	//OALeave
	void saveOALeave(List<OALeave> oALeaves) throws Exception;
	
	PageView<OALeave> getOALeaveListpageView(OALeave oALeave)throws Exception;//分页列表
	 
	 public List<OALeave> getUnUpdateLeaveList(OALeave oALeave) throws Exception;
	 
	//OAOut
	void saveOAOut(List<OAOut> oAOuts) throws Exception;
	
	PageView<OAOut> getOAOutListpageView(OAOut oAOut)throws Exception;//分页列表
	 
	 public List<OAOut> getUnUpdateOutList(OAOut oAOut) throws Exception;
	 
	//OAOnBusiness
	void saveOAOnBusiness(List<OAOnBusiness> oAOnBusinesss) throws Exception;
	
	PageView<OAOnBusiness> getOAOnBusinessListpageView(OAOnBusiness oAOnBusiness)throws Exception;//分页列表
	 
	public List<OAOnBusiness> getUnUpdateOnBusinessList(OAOnBusiness oAOnBusiness) throws Exception;
	
	public void updateOAInfo(List<AttenceDetails> attenceList, List<OALeave> leaveList, List<OAOut> outList, List<OAOnBusiness> onBusinessesList ) throws Exception;
}
