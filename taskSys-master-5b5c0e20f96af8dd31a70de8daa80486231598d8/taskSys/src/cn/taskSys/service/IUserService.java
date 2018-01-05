package cn.taskSys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.Menu;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.pager.PagerModel;

public interface IUserService {
	
	/**
	 * 查询所有用户
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<User> getAllUserList() throws Exception;

	/**
	 * 登录时查询用户角色
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<Role> getUserRole(String login) throws Exception;
	/**
	 * 获取登录用户信息
	 * @return
	 */
	User getSessionUser(Map map) throws Exception;
	
	/**
	 * 验证登录用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	User getIsLogin(User user) throws Exception;
	
	
	/**
	 * 获取ID集合
	 * @return
	 */
	List<User> getTDUser(Map map) throws Exception;
	
	/**
	 * 登录时查询用户权限
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<Menu> getUserPermission(String login) throws Exception;
	
	/**
	 * 通过用户ID获取用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getUserById(String userId) throws Exception;	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int updateUserPassword(User user) throws Exception;
	
	void updateUserHeadPortrait(User user) throws Exception;
	PagerModel getPageUser(HashMap<String, Object> qry) throws Exception;
	/**
	 * 用户查询
	 * @param user
	 * @return
	 */
	User getUserWithRoles(String userId) throws Exception;	
	/**
	 * 停用用户
	 * @param id
	 * @return
	 */
	int disableUser(String id) throws Exception;	
	/**
	 * 重置密码
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int resetUserPassword(String id) throws Exception;	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	int deleteUser(String id) throws Exception;	
	/**
	 * 用户保存（新增、修改）
	 * @param user
	 * @throws Exception
	 */
	User saveUser(User user) throws Exception;	
	/**
	 * 通过用户loginName获取用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getUserByLoginName(String loginName) throws Exception;	
	/**
	 * 启用用户
	 * @param id
	 * @return
	 */
	int enableUser(String id) throws Exception;	
	
	/**
	 * 查询角色与绩效端角色对应-绩效库
	 * @param id
	 * @return
	 */
	List<Role> getRolecode(String roleid)throws Exception;
	
	/**
	 * 登录时查询用户--角色权限-绩效库
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<Menu> getUserRolePermission(String roleCode) throws Exception;
	
//////////////////汇金端库////////////////////////////////////////////////////////
	
	/**
	 * 汇金端-验证登录用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	User getIsFinaceLogin(Map<String,String> map) throws Exception;
	
	/**
	 * 汇金端-获取登录用户信息
	 * @return
	 */
	User getFinaceSessionUser(Map map) throws Exception;
	
	/**
	 * 汇金端-获取ID集合
	 * @return
	 */
	List<User> getFinaceTDUser(Map map) throws Exception;
	
	/**
	 * 汇金端-登录时查询用户角色
	 * @param String
	 * @return
	 * @throws Exception
	 */
	List<Role> getFinaceUserRole(String login) throws Exception;
	
	/**
	 * 查询部门、团队、职位、岗位集合，用于新增下拉框选择
	 * @return
	 */
	List<Map<String, String>> getOrgInfo();
	/**
	 * 根据部门联动团队
	 * @param code
	 * @return
	 */
	List getTeamByCode(String code);
	/**
	 * 批量删除用户
	 * @param list
	 */
	void batchDeleteUser(List list);
	
	List<User> getUserList(User user);
	
	/**
	 * 用户新增时校验用户名是否重复
	 * @param loginName
	 * @return
	 */
	int checkLoginName(String loginName);
	
	/**
	 * 导入用户信息时  查出数据库已存在的用户名的List，用于校验用户名是否重复
	 * @return
	 */
	List<Map<String, String>> getLoginNameList();
	List<Map<String, String>> getBuMenList();
	List<Map<String, String>> getRoleList();
	void batchStopUser(List list);
	List<User> getDepartmentManagerById(String zjid);
	
	List<User> getAllGeneralUsers();//所有在部门经理以下的员工
	
	List<Map<String, Object>> getUserCountByDeparment();//所有在部门经理以下的各部门人数
	
	PageView<Map<String, Object>> getUserListpageView(Map<String,Object> map,User user)throws Exception;
	
}
