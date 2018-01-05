package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.StatCard;
import cn.taskSys.entity.SysBranch;
import cn.taskSys.entity.SysUser;
import cn.taskSys.entity.SysUserBranch;

public interface IStatCardService {
	
	List<StatCard> findStatCardList(Map<String, Object> map) throws Exception;
	
	List<SysBranch> findSysBranchList(Map<String, Object> map) throws Exception;
	
	List<SysUserBranch> findSysUserBranchList(Map<String, Object> map) throws Exception;
	
	List<SysUser> findSysUserList(Map<String, Object> map) throws Exception;
	
	void saveStatCard(List<StatCard> statCards, List<SysBranch> sysBranchs, List<SysUserBranch> sysUserBranchs, List<SysUser> sysUsers) throws Exception;//批量保存
}
