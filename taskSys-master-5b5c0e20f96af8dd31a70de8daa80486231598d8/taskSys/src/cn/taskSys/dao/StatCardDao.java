package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.StatCard;
import cn.taskSys.entity.SysBranch;
import cn.taskSys.entity.SysUser;
import cn.taskSys.entity.SysUserBranch;

public interface StatCardDao {
	
	List<StatCard> findStatCardList(Map<String, Object> map) throws Exception;
	
	void saveStatCard(List<StatCard> statCardList) throws Exception;//批量保存
	
	List<SysBranch> findSysBranchList(Map<String, Object> map) throws Exception;
	
	void saveSysBranch(List<SysBranch> sysBranchList) throws Exception;//批量保存
	
	List<SysUserBranch> findSysUserBranchList(Map<String, Object> map) throws Exception;

	void saveSysUserBranch(List<SysUserBranch> sysUserBranchList) throws Exception;//批量保存
	
	List<SysUser> findSysUserList(Map<String, Object> map) throws Exception;

	void saveSysUser(List<SysUser> sysUserList) throws Exception;//批量保存
	
	void deleteSysBranch() throws Exception;
	
	void deleteSysUserBranch() throws Exception;
	
	void deleteSysUser() throws Exception;
}
