package cn.taskSys.dao;

import java.util.HashMap;
import java.util.List;

import cn.taskSys.entity.Area;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Org;


public interface OrgDao {
	 /**
	  *  获取组织机构树
	  *  @author KANGYU
	  *  @return 组织机构list
	  */
	 public List<Org> getOrgTree(); 
	 
	 public List<Org> getOrgInfo(String id); 
	 
	 public List<Org> getParentList(); 
	 
	 public List<Area> getAreaList(String areatype); 
	 
	 public List<Area> getCityList(String areacode); 
	 
	 public int insertOrg(Org org); 
	 
	 public int insertOrgHis(String id); 
	 
	 public int updateOrg(Org org); 
	 
	 public int updateLowOrg(String id); 
	 
	 public int deleteOrg(String id); 
	 
	 public List<Org> getOrgHisList(HashMap<String , Object> map);
	 
	 public int getOrgHisSize(HashMap<String , Object> map);
	 
	 /**
	  * 根据orgId查询用户的useType
	  * */
	 public String getUserTypeByOrgId(Long orgId);
	 
	 public List<Dictionary> getDictionary();
}
