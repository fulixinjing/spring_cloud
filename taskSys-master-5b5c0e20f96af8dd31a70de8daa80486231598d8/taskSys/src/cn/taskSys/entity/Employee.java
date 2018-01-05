package cn.taskSys.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import cn.taskSys.base.bean.BaseObject;

public class Employee extends BaseObject {
	private static final long serialVersionUID = -416013539187886863L;

	/**
	 * 员工编码
	 */
	private String employeeCode;
	/**
	 * 员工名称
	 */
	private String employeeName;
	
	/**
	 * 机构ID
	 */
	private String orgId;

	/**
	 * 职务
	 */
	private String position;
	
	/**
	 * 职级
	 */
	private String jobLevel;	
	
	/**
	 * 员工组织机构编码
	 */
	private String employeeOrgCode;	
	
	/**
	 * 岗位id
	 */
	private String postId;		
	
	/**
	 * 用户来源
	 */
	private String employeeSource;		
	
	/**
	 * 状态
	 */
	private String status;		

	/**
	 * 性别
	 */
	private String sex;		
	
	/**
	 * 父id
	 */
	private String parentId;		
	
	/**
	 * 手机
	 */
	private String mobilePhone;		

	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 最后修改人
	 */
	private String remark;	
	/**
	 * 序号
	 */
	private String rowNum;
	private Post post;
	
	//add by zc
	/**
	 * 所属区域
	 */
	private String orgStore;
	/**
	 * 所属公司
	 */
	private String orgCompany;
	/**
	 * 评分
	 * @return
	 */
	
	public String getOrgStore() {
		return orgStore;
	}

	public void setOrgStore(String orgStore) {
		this.orgStore = orgStore;
	}

	public String getOrgCompany() {
		return orgCompany;
	}

	public void setOrgCompany(String orgCompany) {
		this.orgCompany = orgCompany;
	}
	



	//目前业绩和任务额获取逻辑有问题为实现
	/**
	 * 任务额
	 */
    private String taskmoney;
    
    /**
     * 业绩
     */
    private String achievement;

	public String getTaskmoney() {
		return taskmoney;
	}

	public void setTaskmoney(String taskmoney) {
		this.taskmoney = taskmoney;
	}

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}


	public Employee() {
		super();
	}

	public Employee(String employeeCode, String status) {
		super();
		this.employeeCode = employeeCode;
		this.status = status;
	}
	
	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		if(employeeCode != null){
			this.employeeCode = employeeCode.trim();
		}else{
			this.employeeCode = employeeCode;			
		}
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		if(employeeName != null){
			this.employeeName = employeeName.trim();
		}else{
			this.employeeName = employeeName;			
		}
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getEmployeeOrgCode() {
		return employeeOrgCode;
	}

	public void setEmployeeOrgCode(String employeeOrgCode) {
		this.employeeOrgCode = employeeOrgCode;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getEmployeeSource() {
		return employeeSource;
	}

	public void setEmployeeSource(String employeeSource) {
		this.employeeSource = employeeSource;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
