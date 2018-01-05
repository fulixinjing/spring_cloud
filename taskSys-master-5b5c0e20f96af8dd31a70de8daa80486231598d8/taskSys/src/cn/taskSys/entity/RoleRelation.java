package cn.taskSys.entity;

public class RoleRelation {
	/**
	 * 角色编码
	 */
	private String roleCode;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 来源（0.本身1.汇金 2.财富 3.汇诚）
	 */
	private String jxSource;		
	
	/**
	 * 来源角色ID
	 */
	private String sourceRoleCode;			

	/**
	 * 来源角色名称
	 */
	private String sourceRoleName;

	
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getJxSource() {
		return jxSource;
	}

	public void setJxSource(String jxSource) {
		this.jxSource = jxSource;
	}

	public String getSourceRoleCode() {
		return sourceRoleCode;
	}

	public void setSourceRoleCode(String sourceRoleCode) {
		this.sourceRoleCode = sourceRoleCode;
	}

	public String getSourceRoleName() {
		return sourceRoleName;
	}

	public void setSourceRoleName(String sourceRoleName) {
		this.sourceRoleName = sourceRoleName;
	}		
	
}
