package cn.taskSys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.StatCardDao;
import cn.taskSys.entity.StatCard;
import cn.taskSys.entity.SysBranch;
import cn.taskSys.entity.SysUser;
import cn.taskSys.entity.SysUserBranch;
import cn.taskSys.service.IStatCardService;

@Service("statCardService")
public class StatCardServiceImpl implements IStatCardService {
	
	@Autowired
	private StatCardDao statCardDao;

	@Override
	public void saveStatCard(List<StatCard> statCards, List<SysBranch> sysBranchs, List<SysUserBranch> sysUserBranchs, List<SysUser> sysUsers) throws Exception {
		if(statCards!=null && statCards.size()>0){
			statCardDao.saveStatCard(statCards);
		}
		if(sysBranchs!=null && sysBranchs.size()>0){
			statCardDao.deleteSysBranch();
			statCardDao.saveSysBranch(sysBranchs);
		}
		if(sysUserBranchs!=null && sysUserBranchs.size()>0){
			statCardDao.deleteSysUserBranch();
			statCardDao.saveSysUserBranch(sysUserBranchs);
		}
		if(sysUsers!=null && sysUsers.size()>0){
			statCardDao.deleteSysUser();
			statCardDao.saveSysUser(sysUsers);
		}
		
	}


	@Override
	public List<StatCard> findStatCardList(Map<String, Object> map) throws Exception {
		return statCardDao.findStatCardList(map);
	}


	@Override
	public List<SysBranch> findSysBranchList(Map<String, Object> map) throws Exception {
		return statCardDao.findSysBranchList(map);
	}


	@Override
	public List<SysUserBranch> findSysUserBranchList(Map<String, Object> map) throws Exception {
		return statCardDao.findSysUserBranchList(map);
	}


	@Override
	public List<SysUser> findSysUserList(Map<String, Object> map) throws Exception {
		return statCardDao.findSysUserList(map);
	}

}
