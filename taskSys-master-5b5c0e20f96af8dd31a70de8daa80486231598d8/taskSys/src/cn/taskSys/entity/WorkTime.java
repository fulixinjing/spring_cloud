package cn.taskSys.entity;

public class WorkTime {
	
	private String id;
	private String userid;
	private String allWorkTime;
	private String saturation;
	private String empCode;
	private String type;
	private String statisticsTime;
	private String department;
	private String team;
	private String addTime;
	private String attenceDay;
	
	private int page = 1;
	private int maxResult = 10;
	private String loginName;
	private String userName;
	private String departmentName;
	private String teamName;
	private String positionName;
	private String postName;
	private String orderByPro;
	private String orderByFlag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAllWorkTime() {
		return allWorkTime;
	}
	public void setAllWorkTime(String allWorkTime) {
		this.allWorkTime = allWorkTime;
	}
	public String getSaturation() {
		return saturation;
	}
	public void setSaturation(String saturation) {
		this.saturation = saturation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatisticsTime() {
		return statisticsTime;
	}
	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getOrderByPro() {
		return orderByPro;
	}
	public void setOrderByPro(String orderByPro) {
		this.orderByPro = orderByPro;
	}
	public String getOrderByFlag() {
		return orderByFlag;
	}
	public void setOrderByFlag(String orderByFlag) {
		this.orderByFlag = orderByFlag;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getAttenceDay() {
		return attenceDay;
	}
	public void setAttenceDay(String attenceDay) {
		this.attenceDay = attenceDay;
	}
	
	
}
