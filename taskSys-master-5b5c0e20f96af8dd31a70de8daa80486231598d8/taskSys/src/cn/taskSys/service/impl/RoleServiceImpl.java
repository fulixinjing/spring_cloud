package cn.taskSys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.RoleDao;
import cn.taskSys.entity.Menu;
import cn.taskSys.entity.Post;
import cn.taskSys.entity.Right;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.RoleRelation;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@LogAnnotation(eventCode="2029001",eventProcess="")
	public List<Role> getRoleByUserId(String userId) {
		return roleDao.getRoleByUserId(userId);
	}
	
	@LogAnnotation(eventCode="2029002",eventProcess="")
	public List<Role> getRoles() {

		return roleDao.getRoles();
	}
	@LogAnnotation(eventCode="2029003",eventProcess="")
	@Override
	public List<Right> getRights() {
		return roleDao.getRights();
	}
	@LogAnnotation(eventCode="2029004",eventProcess="")
	public List<Role> getAllRole(Role role) {
		return roleDao.getAllRole(role);
	}
	
	@LogAnnotation(eventCode="2029005",eventProcess="")
	public int add(Role role,String sourceCodes,String sourceName) {
		Date d = new Date();
		role.setCreateBy("admin");
		role.setCreateTime(d);
		role.setLastModifyBy("admin");
		role.setLastModifyTime(d);
		/**
		 * roleCode鍞竴
		 */
		int p = roleDao.getRole(role);
		if (p > 1) {
			System.out.println("error");
		}
		/**
		 * 娣诲姞瑙掕壊
		 */
		roleDao.addRole(role);
		/**
		 * 插入关联角色关系表
		 */
		if(!"".equals(sourceCodes) && sourceCodes != null && !"null".equals(sourceCodes)){
			String sourceCode = sourceCodes.substring(0,sourceCodes.length()-1);
			String[] sc = sourceCode.split(",");
			for (int i = 0; i < sc.length; i++) {
				String scode = sc[i];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("roleCode", role.getRoleCode());
				map.put("roleName", role.getRoleName());
				map.put("jxSource", role.getJxSource());
				map.put("sourceCode", scode);
				roleDao.addRoleRelation(map);
			}					
		}		
		return 0;
	}

	@LogAnnotation(eventCode="2029006",eventProcess="")
	public int addRole(Role role, String rightCode,String sourceCodes,String sourceName) {
		Date d = new Date();
		role.setCreateBy("admin");
		role.setCreateTime(d);
		role.setLastModifyBy("admin");
		role.setLastModifyTime(d);
		/**
		 * roleCode鍞竴
		 */
		int p = roleDao.getRole(role);
		if (p > 1) {
			System.out.println("error");
		}
		/**
		 * 娣诲姞瑙掕壊
		 */
		roleDao.addRole(role);
		if (StringUtils.isNotBlank(rightCode)
				&& StringUtils.isNotEmpty(rightCode)) {
			String[] r = getRightCode(rightCode);
			for (int i = 0; i < r.length; i++) {
				String rightcod = r[i];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("roleCode", role.getRoleCode());
				map.put("rightCode", rightcod);
				map.put("releSource", '0');
				roleDao.addRoleAndRight(map);
			}
		}
		/**
		 * 插入关联角色关系表
		 */
		if(!"".equals(sourceCodes) && sourceCodes != null && !"null".equals(sourceCodes)){
			String sourceCode = sourceCodes.substring(0,sourceCodes.length()-1);
			String strSourceName = sourceName.substring(0,sourceName.length()-2);
			String[] sc = sourceCode.split(",");
			String[] sn = strSourceName.split("\r\n");
			String scode ="";
			String sname = "";
			for (int i = 0; i < sc.length; i++) {
				scode = sc[i];
				sname = sn[i];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("roleCode", role.getRoleCode());
				map.put("roleName", role.getRoleName());
				map.put("jxSource", role.getJxSource());
				map.put("sourceCode", scode);
				map.put("sourceName", sname);
				roleDao.addRoleRelation(map);
			}					
		}
		return 0;
	}

	@LogAnnotation(eventCode="2029007",eventProcess="")
	public int updateRole(Role role) {
		roleDao.updateRole(role);
		return 0;
	}

	@LogAnnotation(eventCode="2029008",eventProcess="")
	public int delRole(Role role) {
		/**
		 * 解除角色权限的关系
		 */
		roleDao.delRoleAndRight(role);
		roleDao.delRole(role);
		roleDao.delRoleRelation(role);
		return 0;
	}
    @LogAnnotation(eventCode="2029009",eventProcess="")
	private String[] getRightCode(String rightCode) {
		String[] rightcodes = rightCode.split("\\,");
		for (int i = 0; i < rightcodes.length; i++) {
			System.out.println(rightcodes[i]);
		}
		return rightcodes;
	}

	@LogAnnotation(eventCode="2029010",eventProcess="")
	@Override
	public Role getRoleToUpdate(String rolecode) {

		return roleDao.getRoleToUpdate(rolecode);
	}

	@LogAnnotation(eventCode="2029011",eventProcess="")
	@Override
	public Role checkRoleCode(String roleCode) throws Exception {
		Role role = new Role();
		role.setRoleCode(roleCode);
		return roleDao.checkRoleCode(role);
	}

	@LogAnnotation(eventCode="20290012",eventProcess="")
	@Override
	public PagerModel getPageUser(HashMap<String, Object> qry) {
		PagerModel pager = new PagerModel();
		pager.setDatas(getPageRoleData(qry));
		pager.setTotal(getPageRoleSize(qry));
		return pager;
	}

	/**
	 * 閫氳繃鏌ヨ鏉′欢杩涜鍒嗛〉璁＄畻
	 * 
	 * @param qry
	 * @return
	 */
	@LogAnnotation(eventCode="20290013",eventProcess="")
	private int getPageRoleSize(HashMap<String, Object> qry) {
		return roleDao.getPageRoleSize(qry);
	}

	/**
	 * 閫氳繃鏌ヨ鏉′欢杩涜鍒嗛〉妫�储
	 * 
	 * @param qry
	 * @return
	 */
	@LogAnnotation(eventCode="20290014",eventProcess="")
	private List getPageRoleData(HashMap<String, Object> qry) {
		return roleDao.getPageRoleData(qry);
	}

	@LogAnnotation(eventCode="20290015",eventProcess="")
	@Override
	public int updateRole(Role role, String rightcode,String sourceCodes,String sourceName) {
		//这里没有做事务控制-肯定会出问题
		roleDao.updateRole(role);
		if(StringUtils.isNotBlank(role.getRoleCode())&&StringUtils.isNotEmpty(role.getRoleCode())){
			roleDao.delRoleAndRight(role);
			roleDao.delRoleRelation(role);
		}
		if (StringUtils.isNotBlank(rightcode)
				&& StringUtils.isNotEmpty(rightcode)) {
			String[] r = getRightCode(rightcode);
			for (int i = 0; i < r.length; i++) {
				String rightcod = r[i];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("roleCode", role.getRoleCode());
				map.put("rightCode", rightcod);
				map.put("releSource", '0');
				roleDao.addRoleAndRight(map);
			}
		}
		/**
		 * 插入关联角色关系表
		 */
		if(!"".equals(sourceCodes) && sourceCodes != null && !"null".equals(sourceCodes)){
			String sourceCode = sourceCodes.substring(0,sourceCodes.length()-1);
			String strSourceName = sourceName.substring(0,sourceName.length()-2);
			String[] sc = sourceCode.split(",");
			String[] sn = strSourceName.split("\r\n");
			String scode ="";
			String sname = "";
			for (int i = 0; i < sc.length; i++) {
				scode = sc[i];
				sname = sn[i];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("roleCode", role.getRoleCode());
				map.put("roleName", role.getRoleName());
				map.put("jxSource", role.getJxSource());
				map.put("sourceCode", scode);
				map.put("sourceName", sname);
				roleDao.addRoleRelation(map);
			}					
		}		
		return 0;
	}
	
	@LogAnnotation(eventCode="20290016",eventProcess="")
	public List<Menu> getRoleById(String rightcode){
		return roleDao.getRoleById(rightcode);
	}
	
	@LogAnnotation(eventCode="20290017",eventProcess="")
	public List<Menu> getMenuById(String rightcode){
		return roleDao.getMenuById(rightcode);
	}
	

	/**
	 * 根据角色编码获取角色对应的权限集合
	 * @param roleCode 角色编码
	 * @return 权限集合
	 */
	@LogAnnotation(eventCode="20290018",eventProcess="")
	public List<Right> getRightByRoleCode(String roleCode) {
		List<Right> rights = null;
		if(!"".equals(roleCode) && null != roleCode){
			rights = roleDao.getRightByRoleCode(roleCode);
		}
		return rights;
	}

	/**
	 * 根据角色来源获取角色对应的权限集合
	 * @param jxSource 来源
	 * @return 权限集合
	 */	
	@LogAnnotation(eventCode="20290019",eventProcess="")
	public List<Role> getAllParentRole(Map<String,Object> map) {
		List<Role> parnentRoleList = null;
		parnentRoleList = roleDao.getAllParentRole(map);
		return parnentRoleList;
	}
	
	/**
	 * 根据角色获取来源角色的权限集合
	 * @param rightcode 来源
	 * @return 集合
	 */			
	@LogAnnotation(eventCode="20290020",eventProcess="")
	public List<RoleRelation> getRoleRelationById(String roleCode){
		return roleDao.getRoleRelationById(roleCode);
	}
	
///////////汇金端////////////////////////////begin///////////////////////////////////////////////////	
	/**
	 * 获取汇金端所有角色
	 * @param role
	 * @return 集合
	 */			
	@LogAnnotation(eventCode="20290021",eventProcess="")
	public List<Role> getAllRoleHj(Role role) {
		return roleDao.getAllRoleHj(role);
	}	
///////////汇金端////////////////////////////end///////////////////////////////////////////////////	

	/**
	 * 根据角色id, 角色code, type获取来源角色的权限集合
	 * @param rightcode 来源
	 * @return 集合
	 */		
	@LogAnnotation(eventCode="20290022",eventProcess="")
	public List<RoleRelation> getRoleRelationList(String roleid,
			String roleCode, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", roleid);
		map.put("roleCode", roleCode);
		map.put("type", type);
		return roleDao.getRoleRelationList(map);
	}
	/**
	 * 根据角色来源获取角色对应的岗位集合
	 * @param 
	 * @return 集合
	 */	
	@LogAnnotation(eventCode="20290019",eventProcess="")
	public List<Post> getAllPost() {
		List<Post> postList = null;
		postList = roleDao.getAllPost();
		return postList;
	}

	@Override
	public List<Role> getAllRole2(Role role) {
		// TODO Auto-generated method stub
		return roleDao.getAllRole2();
	}	
}
