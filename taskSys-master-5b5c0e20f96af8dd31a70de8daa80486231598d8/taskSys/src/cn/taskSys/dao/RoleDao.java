package cn.taskSys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Menu;
import cn.taskSys.entity.Post;
import cn.taskSys.entity.Right;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.RoleRelation;

public interface RoleDao {

	/**
	 * 通过用户ID获取此用户所拥有的角色集
	 * @return
	 */
	public List<Role> getRoleByUserId(String userId);

	public List<Role> getRoles();
	/**
	 * 获取角色
	 * @param role
	 * @return
	 */
	public List<Role> getAllRole(Role role);

	public void addRole(Role role);

	public void updateRole(Role role);

	public void delRole(Role role);
	/**
	 * 角色权限关系表
	 * @param map 
	 * @param dd 角色code
	 * @param rightcod 权限code
	 */
	public void addRoleAndRight(Map<String, Object> map);
	/**
	 * 角色权限回显
	 * @param rolecode
	 * @return
	 */
	public Role getRoleToUpdate(String rolecode);
	/**
	 * 删除角色权限关系表中的数据。
	 * @param role
	 */
	public void delRoleAndRight(Role role);
	/**
	 * 查询角色，判断唯一性
	 * @param role
	 * @return
	 */
	public int getRole(Role role);
	/**
	 * 检查roleCode是否存在
	 * @param role
	 * @return
	 */
	public Role checkRoleCode(Role role);
	/**
	 * 通过查询条件进行分页计算
	 * @param qry
	 * @return
	 */
	public int getPageRoleSize(HashMap<String, Object> qry);
	/**
	 * 通过查询条件进行分页检索
	 * @param qry
	 * @return
	 */
	public List getPageRoleData(HashMap<String, Object> qry);
	
	
	public List<Menu> getRoleById(String rightcode);
	
	public List<Menu> getMenuById(String rightcode);
	
	 
	/**
	 * 获取所有权限
	 * 
	 * @return
	 */
	public List<Right> getRights();
	
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
	public List<Role> getAllParentRole(Map<String, Object> map);
	/**
	 * 添加角色关系表
	 * @param map 
	 */
	public void addRoleRelation(Map<String, Object> map);	
	/**
	 * 删除角色关系表
	 * @param map 
	 */	
	public void delRoleRelation(Role role);	

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
	public List<RoleRelation> getRoleRelationList(Map<String, Object> map);	
	
///////////汇金端////////////////////////////begin///////////////////////////////////////////////////		
	/**
	 * 获取汇金端所有角色
	 * @param role
	 * @return
	 */
	public List<Role> getAllRoleHj(Role role);	
	/**
	 * 根据角色来源获取角色对应的岗位集合
	 * @param 
	 * @return 集合
	 */
	public List<Post> getAllPost();		
///////////汇金端////////////////////////////end///////////////////////////////////////////////////	

	public List<Role> getAllRole2();
}

