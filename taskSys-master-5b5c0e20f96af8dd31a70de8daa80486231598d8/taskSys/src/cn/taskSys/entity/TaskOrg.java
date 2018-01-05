package cn.taskSys.entity;


public class TaskOrg {
	/**
	 * ID
	 */
	private String id;		
	/**
	 * 组织机构名称
	 */
	private String orgName;			
	/**
	 * 组织机构编码
	 */
	private String orgCode;			
	/**
	 * 父级ID
	 */
	private String parentId;
	
	//判断是否有下级
	private String ids;
	
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}	
}
