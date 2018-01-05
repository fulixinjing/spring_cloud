package cn.taskSys.dao;

import java.util.List;

import cn.taskSys.entity.OALeave;
import cn.taskSys.entity.OAOnBusiness;
import cn.taskSys.entity.OAOut;


public interface OALeaveDao {
	 //OALeave
	 void saveOALeave(List<OALeave> oALeaves) throws Exception;
	 
	 public List<OALeave> getOALeaveList(OALeave oALeave) throws Exception;//分页列表
		
	 public int getOALeaveListCount(OALeave oALeave) throws Exception;
	 
	 public List<OALeave> getUnUpdateLeaveList(OALeave oALeave) throws Exception;
	 
	 void updateLeaveById(List<OALeave> oALeaves) throws Exception;
	 
	 //OAOut
	 void saveOAOut(List<OAOut> oAOuts) throws Exception;
	 
	 public List<OAOut> getOAOutList(OAOut oAOut) throws Exception;//分页列表
		
	 public int getOAOutListCount(OAOut oAOut) throws Exception;
	 
	 public List<OAOut> getUnUpdateOutList(OAOut oAOut) throws Exception;
	 
	 void updateOutById(List<OAOut> oAOuts) throws Exception;
	 
	 
	 //OAOnBusiness
	 void saveOAOnBusiness(List<OAOnBusiness> oAOnBusinesss) throws Exception;
	 
	 public List<OAOnBusiness> getOAOnBusinessList(OAOnBusiness oAOnBusiness) throws Exception;//分页列表
		
	 public int getOAOnBusinessListCount(OAOnBusiness oAOnBusiness) throws Exception;
	 
	 public List<OAOnBusiness> getUnUpdateOnBusinessList(OAOnBusiness oAOnBusiness) throws Exception;
	 
	 void updateOnBusinessById(List<OAOnBusiness> oAOnBusinesss) throws Exception;
	 
	 
	
}
