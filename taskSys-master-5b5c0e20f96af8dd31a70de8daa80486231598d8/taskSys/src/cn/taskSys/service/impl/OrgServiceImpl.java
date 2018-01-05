package cn.taskSys.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.OrgDao;
import cn.taskSys.entity.Area;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Org;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.OrgService;

@Service("orgService")
public class OrgServiceImpl implements OrgService {
	@Autowired
	private OrgDao orgDao;

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	@LogAnnotation(eventCode="2024001",eventProcess="")
	public List<Org> getOrgTree() {
		 
		return orgDao.getOrgTree();
	}
	
	@LogAnnotation(eventCode="2024002",eventProcess="")
	public List<Org> getOrgInfo(String orgid) {
		 
		return orgDao.getOrgInfo(orgid);
	}
	
	@LogAnnotation(eventCode="2024003",eventProcess="")
	public List<Org> getParentList() {
		 
		return orgDao.getParentList();
	}
	
	@LogAnnotation(eventCode="2024004",eventProcess="")
	public List<Area> getAreaList(String areatype) {
		 
		return orgDao.getAreaList(areatype);
	}
	
	@LogAnnotation(eventCode="2024005",eventProcess="")
	public List<Area> getCityList(String areacode) {
		 
		return orgDao.getCityList(areacode);
	}
	
	@LogAnnotation(eventCode="2024006",eventProcess="")
	public int updateOrg(Org org){
		return orgDao.updateOrg(org);
	}
	
	@LogAnnotation(eventCode="2024007",eventProcess="")
	public int updateLowOrg(String orgid){
		return orgDao.updateLowOrg(orgid);
	}
	
	@LogAnnotation(eventCode="2024008",eventProcess="")
	public int insertOrg(Org org){
		return orgDao.insertOrg(org);
	}
	
	@LogAnnotation(eventCode="2024009",eventProcess="")
	public int insertOrgHis(String orgid){
		return orgDao.insertOrgHis(orgid);
	}
	
	@LogAnnotation(eventCode="20240010",eventProcess="")
	public int deleteOrg(String orgid){
		return orgDao.deleteOrg(orgid);
	}
	
	@LogAnnotation(eventCode="20240011",eventProcess="")
	public List<Org> getOrgHisList(HashMap<String , Object> map) {
		return orgDao.getOrgHisList(map);
	}
	
	@LogAnnotation(eventCode="20240012",eventProcess="")
	public int getOrgHisSize(HashMap<String , Object> map) {
		return orgDao.getOrgHisSize(map);
	}
	
	@LogAnnotation(eventCode="20240013",eventProcess="")
	public PagerModel getPageList(HashMap<String , Object> map) {
		PagerModel pagemodel = new PagerModel();
		List<Org> orglist = getOrgHisList(map);
		pagemodel.setDatas(orglist);
		Integer totalCount = getOrgHisSize(map);
		pagemodel.setTotal(totalCount);
		return pagemodel;
	}

	@LogAnnotation(eventCode="20240014",eventProcess="")
	@Override
	public String getOrgType(Long orgId) {
		String orgType = orgDao.getUserTypeByOrgId(orgId); 
		return orgType;
	}
	
	@LogAnnotation(eventCode="20240015",eventProcess="")
	public List<Dictionary> getDictionary(){
		return orgDao.getDictionary();
	}
	
	
}
