package cn.taskSys.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import cn.taskSys.base.bean.BaseObject;

public class User extends BaseObject {

	private static final long serialVersionUID = 167281642280391967L;
	/**
	 * userID
	 */
	private String userId;
	/**
 	 * 登录名
 	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户名
	 */
	
	private String userName;
	/**
 	 * 所属机构ID	
	 */
	private String managerID;
	/**
 	 * 用户来源
	 */
	private String userSource;
	/**
 	 * 岗位ID
	 */
	private String jobId;
	/**
 	 * 岗位Name
	 */
	private String postName;
	/**
	 * 状态（0在用，1停用）
	 */
	private String status;
	/**
 	 * 登录IP
	 */
	private String loginIp;
	/**
	 * 类型
	 */
	private String userType;
	
	/**
	 * 角色对象
	 */

	private List<Role> roles;
	
	private String khid;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	
	
	private String orgtype;
	
	private String ids;
	
	private String addr_province;
	
	private String addr_city;
	
	/**
	 * 所属机构ID
	 */
	private String orgId;
	/**
	 * 员工编码
	 */
	private String empCode;	
	
	/**
	 * 所在公司编号
	 */
	private String company_id;	
	
	/**
	 * 所在公司名称
	 */
	private String company_Name;	
	
	/**
	 * 所在部门编号
	 */
	private String department_id;	
	
	/**
	 * 所在部门名称
	 */
	private String department_Name;	
	
	/**
	 * 职位编码
	 */
	private String position_id;	
	
	/**
	 * 职位名称
	 */
	private String position_Name;	
	
	/**
	 * 所在团队编码
	 */
	private String team_id;	
	
	/**
	 * 所在团队名称
	 */
	private String team_Name;	
	
	/**
	 * 入职时间
	 */
	private String employed_date;	
	
	/**
	 * 创建时间
	 */
	private String creates_date;	
	private int page = 1;
    private int maxResult = 10;
    
    /**
     * 人物头像
     */
    private String head_portrait;
	
	public String getHead_portrait()
    {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait)
    {
        this.head_portrait = head_portrait;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getMaxResult()
    {
        return maxResult;
    }

    public void setMaxResult(int maxResult)
    {
        this.maxResult = maxResult;
    }

    /**
	 * 邮箱
	 */
	private String email;	
	private String roleName;
	private String speciality;//特长
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getAddr_province() {
		return addr_province;
	}

	public void setAddr_province(String addr_province) {
		this.addr_province = addr_province;
	}

	public String getAddr_city() {
		return addr_city;
	}

	public void setAddr_city(String addr_city) {
		this.addr_city = addr_city;
	}

	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public User() {

	}
	public User(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}


	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}	
	
	
	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public String getPosition_id() {
		return position_id;
	}

	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public String getEmployed_date() {
		return employed_date;
	}

	public void setEmployed_date(String employed_date) {
		this.employed_date = employed_date;
	}

	

	public String getCreates_date() {
		return creates_date;
	}

	public void setCreates_date(String creates_date) {
		this.creates_date = creates_date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public String getDepartment_Name() {
		return department_Name;
	}

	public void setDepartment_Name(String department_Name) {
		this.department_Name = department_Name;
	}

	public String getPosition_Name() {
		return position_Name;
	}

	public void setPosition_Name(String position_Name) {
		this.position_Name = position_Name;
	}

	public String getTeam_Name() {
		return team_Name;
	}

	public void setTeam_Name(String team_Name) {
		this.team_Name = team_Name;
	}
	
	
	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany_Name() {
		return company_Name;
	}

	public void setCompany_Name(String company_Name) {
		this.company_Name = company_Name;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}


}
