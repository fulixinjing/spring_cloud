package cn.taskSys.service;

import java.util.HashMap;
import java.util.List;

import cn.taskSys.entity.Area;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Org;
import cn.taskSys.pager.PagerModel;


public interface OrgService {
	 
	 public List<Org> getOrgTree()throws Exception;
	 
	 public List<Org> getOrgInfo(String orgid)throws Exception;
	 
	 public List<Org> getParentList()throws Exception;
	 
	 public List<Area> getAreaList(String areatype)throws Exception;
	 
	 public List<Area> getCityList(String areacode)throws Exception;
	 
	 public int updateOrg(Org org) throws Exception;
	 
	 public int updateLowOrg(String orgid) throws Exception;
	 
	 public int insertOrg(Org org) throws Exception;
	 
	 public int insertOrgHis(String orgid) throws Exception;
	 
	 public int deleteOrg(String orgid) throws Exception;
	 
	 public PagerModel getPageList(HashMap<String , Object> map) throws Exception;
	 
	 public String getOrgType(Long orgId);
	 
	 public List<Dictionary> getDictionary();
}
