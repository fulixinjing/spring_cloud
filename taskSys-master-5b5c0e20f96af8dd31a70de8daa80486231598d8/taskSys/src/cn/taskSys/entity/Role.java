package cn.taskSys.entity;

import java.util.Date;

public class Role {
	/**
	 * 主键角色ID
	 */
	private String id;
	/**
	 * 角色来源（1.汇金 2.财富 3.汇诚）
	 */
	private String jxSource;
	/**
	 * 角色编码
	 */
	private String roleCode;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 状态（0在用，1停用）
	 */
	private String status;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后修改人
	 */
	private String lastModifyBy;
	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	
	/**
	 * 上一级角色编码
	 */
	private String parentRoleCode;	
	
	/**
	 * 上一级角色名称
	 */
	private String parentRoleName;		
	
	/**
	 * 用于角色弹出多选框是否被选中判断
	 */
	private boolean isChecked;
	
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJxSource() {
		return jxSource;
	}
	public void setJxSource(String jxSource) {
		this.jxSource = jxSource;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	public String getParentRoleCode() {
		return parentRoleCode;
	}
	public void setParentRoleCode(String parentRoleCode) {
		this.parentRoleCode = parentRoleCode;
	}
	public String getParentRoleName() {
		return parentRoleName;
	}
	public void setParentRoleName(String parentRoleName) {
		this.parentRoleName = parentRoleName;
	}	

}
