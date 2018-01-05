package cn.taskSys.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("Right")
public class Right implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3234709347560035554L;

	/**
	 * ID,惟一标识
	 */
	private String id;

	/**
	 * 权限编码
	 */
	private String rightCode;

	/**
	 * 权限名称
	 */
	private String rightName;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 创建人
	 */
	private Date createTime;

	/**
	 * 权限与资源一对一关系
	 */
	// RightResource
	private Resource resource;
	
	/**
	 * 角色的权限是否被选中
	 */
	private boolean isChecked;
	
	private String menu_name;
	private String privilegescode;
	private String menu_pid;
	
	
	public String getMenu_pid() {
		return menu_pid;
	}

	public void setMenu_pid(String menu_pid) {
		this.menu_pid = menu_pid;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getPrivilegescode() {
		return privilegescode;
	}

	public void setPrivilegescode(String privilegescode) {
		this.privilegescode = privilegescode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
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

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// ///////////////////// 非持久化属性 /////////////////////
	/**
	 * 权限编码（更新前）
	 */
	private String rightCode_old;
	/**
	 * 权限资源ID
	 */
	private String prID;
	public String getRightCode_old() {
		return rightCode_old;
	}

	public void setRightCode_old(String rightCode_old) {
		this.rightCode_old = rightCode_old;
	}

	public String getPrID() {
		return prID;
	}

	public void setPrID(String prID) {
		this.prID = prID;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	

}
