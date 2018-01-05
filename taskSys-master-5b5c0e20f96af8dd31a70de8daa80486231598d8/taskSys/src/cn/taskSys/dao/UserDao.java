package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Menu;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;


public interface UserDao {
	String getSeqNextval();
	
	/**
	 * 查询所有用户
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<User> getAllUserList();
	
	/**
	 * 登录时查询用户角色
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<Role> getUserRole(String login);
	
	/**
	 * 验证登录用户
	 * @param user
	 * @return
	 */
	User getIsLogin(User user);
	
	/**
	 * 获取登录用户信息
	 * @return
	 */
	User getSessionUser(Map map);
	

	/**
	 * 获取ID集合
	 * @return
	 */
	
	List<User> getTDUser(Map map);
	
	/**
	 * 登录时查询用户权限
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<Menu> getUserPermission(String login);

	/**
	 * 用户查询
	 * @param user
	 * @return
	 */
	User getUser(User user);
	
	/**
	 * 设置密码
	 * @param user
	 * @return
	 */
	int updateUserPassword(User user);	
	/**
	 * 通过查询条件进行分页检索
	 * @param qry
	 * @return
	 */
	List<User> getPageUserData(Map<String, Object> qry);

	/**
	 * 通过查询条件进行分页计算
	 * @param qry
	 * @return
	 */
	Integer getPageUserSize(Map<String, Object> qry);	
	/**
	 * 停用用户
	 * @param id
	 * @return
	 */
	int disableUser(String id);	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	int deleteUser(String id);
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	int updateUser(User user);
	/**
	 * 新增用户
	 * @param user
	 */
	int insertUser(User user);
	/**
	 * 清除用户已有的角色关系
	 * @param userId
	 * @return
	 */
	int deleteUserRoles(String userId);
	/**
	 * 保存用户和角色关系
	 * @param urs
	 * @return
	 */
	int saveUserRole(Map<String, Object> obj);	
	/**
	 * 启用用户
	 * @param id
	 * @return
	 */
	int enableUser(String id);
	/**
	 * 查询角色与绩效端角色对应
	 * @param id
	 * @return
	 */
	List<Role> getRolecode(String roleid);
	
	/**
	 * 登录时查询用户--角色权限
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<Menu> getUserRolePermission(String roleCode);
	
/////////////////汇金端//////////////////////////////////////////////////////////////////////////
	
	/**
	 * 汇金-验证登录用户
	 * @param user
	 * @return
	 */
	User getIsFinaceLogin(Map<String,String> map);
	
	/**
	 * 汇金-获取登录用户信息
	 * @return
	 */
	User getFinaceLoginSessionUser(Map map);
	
	/**
	 * 汇金端-获取ID集合
	 * @return
	 */
	List<User> getFinaceTDUser(Map map);
	
	/**
	 * 汇金端-登录时查询用户角色
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<Role> getFinaceUserRole(String login);
	
	/**
	 * 查询部门、团队、职位、岗位集合，用于新增下拉框选择
	 * @return
	 */
	List<Map<String, String>> getOrgInfo();

	List getTeamByCode(String code);
	
	/**
	 * 批量删除用户，及用户角色关系
	 * @param list
	 */
	void batchDeleteUser(List list);
	void batchDeleteUserRole(List list);
	
	List<User> getUserListByPro(User user);

	int checkLoginName(String loginName);			//校验用户名是否重复

	List<Map<String, String>> getLoginNameList();

	List<Map<String, String>> getBuMenList();

	List<Map<String, String>> getRoleList();

	void batchStopUser(List list);

	List<User> getDepartmentManagerById(String zjid);    
	
	List<User> getAllGeneralUsers();//所有在部门经理以下的员工
	
	List<Map<String, Object>> getUserCountByDeparment();//所有在部门经理以下的各部门人数
	
	public List<Map<String, Object>> getUserList1(Map<String,Object> map);
	public int getUserSize1(Map<String,Object> map);
	
	
}
