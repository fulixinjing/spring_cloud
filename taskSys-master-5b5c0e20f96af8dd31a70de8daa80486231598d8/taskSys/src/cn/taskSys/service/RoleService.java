package cn.taskSys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Menu;
import cn.taskSys.entity.Post;
import cn.taskSys.entity.Right;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.RoleRelation;
import cn.taskSys.pager.PagerModel;

public interface RoleService {

	/**
	* 通过用户ID获取此用户所拥有的角色集
	* @return
	*/
	public List<Role> getRoleByUserId(String userId);

	 /**
	 * 获取所有权限
	 * @return
	 */
	public List<Right> getRights();
	
	public List<Role> getRoles();
	/**
	 * 获取所有的角色
	 * @param role 
	 * @return
	 */
	public List<Role> getAllRole(Role role);
	/**
	 * 添加角色权限
	 * @param role
	 * @param rightCode
	 * @return
	 */
	public int addRole(Role role,String rightCode,String sourceCodes,String sourceName);
	/**
	 * 添加角色
	 * @param role
	 * @param rightCode
	 * @return
	 */
	public int add(Role role,String sourceCodes,String sourceName);
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);
	/**
	 * 删除角色
	 * @param role
	 * @return
	 */
	public int delRole(Role role);

	/**
	 * 角色权限回显。
	 * @param rolecode
	 * @return
	 */
	public Role getRoleToUpdate(String rolecode);

	/**
	 * 检查roleCode是否存在
	 * @param roleCode
	 * @return
	 * @throws Exception 
	 */
	public Role checkRoleCode(String roleCode) throws Exception;
	
	/**
	 * 分页查询
	 * @param qry
	 * @return
	 */
	public PagerModel getPageUser(HashMap<String, Object> qry);

	/**
	 * 修改role
	 * @param role
	 * @param rightcode
	 * @return
	 */
	public int updateRole(Role role, String rightcode,String sourceRoleCodes,String sourceRoleName);
	
	public List<Menu> getRoleById(String rightcode);
	
	public List<Menu> getMenuById(String rightcode);
	
	
	
	/**
	 * 根据角色编码获取角色对应的权限集合
	 * @param roleCode 角色编码
	 * @return 权限集合
	 */
	public List<Right> getRightByRoleCode(String roleCode);
	
	/**
	 * 根据角色来源获取角色对应的权限集合
	 * @param jxSource 来源
	 * @return 权限集合
	 */	
	public List<Role> getAllParentRole(Map<String,Object> map);
	
	/**
	 * 根据角色获取来源角色的权限集合
	 * @param rightcode 来源
	 * @return 集合
	 */		
	public List<RoleRelation> getRoleRelationById(String roleCode);
	
	/**
	 * 根据角色id, 角色code, type获取来源角色的权限集合
	 * @param rightcode 来源
	 * @return 集合
	 */		
	public List<RoleRelation> getRoleRelationList(String roleid, String roleCode, String type);

///////////汇金端////////////////////////////begin///////////////////////////////////////////////////	
	/**
	 * 获取所有的角色
	 * @param role 
	 * @return
	 */
	public List<Role> getAllRoleHj(Role role);
	/**
	 * 根据角色来源获取对应的岗位集合
	 * @param 
	 * @return 集合
	 */	
	public List<Post> getAllPost();	
///////////汇金端////////////////////////////end///////////////////////////////////////////////////

	public List<Role> getAllRole2(Role role);
}
