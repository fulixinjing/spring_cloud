package cn.taskSys.service;

import java.util.HashMap;

import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;

public interface AlreadyCreditorService {
	
	@LogAnnotation(eventCode="2001001",eventProcess="")
	public PagerModel getPageList(HashMap<String , Object> map);
	
	@LogAnnotation(eventCode="2001003",eventProcess="")
	public PagerModel getZQFSList(HashMap<String , Object> map);
	
	@LogAnnotation(eventCode="2001008",eventProcess="")
	public int getIsEmail(Long id);
	
	@LogAnnotation(eventCode="2001013",eventProcess="")
	public String getAlreadyCreditorListByStatusMoney(HashMap<String , Object> map);
	
	@LogAnnotation(eventCode="2001014",eventProcess="")
	public String getZQFSListMoney(HashMap<String , Object> map);
	
}
