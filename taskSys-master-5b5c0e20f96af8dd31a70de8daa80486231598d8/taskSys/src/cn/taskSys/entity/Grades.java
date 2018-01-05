package cn.taskSys.entity;

import java.io.Serializable;

public class Grades implements Serializable {

	private static final long serialVersionUID = 1L;
	private String gId;
	private String gUserId;
	private String gScores;
	private String gMonth;
	private String gState;
	private String gRemark;
	private String gEmpCode;
	private String addTime;
	private String taskScore;
	private String taskStatusScore;
	private String saturationScore;

	private String departmentManager;	//部门经理
	private String team;				//所属团队
	private String teamZh;				//所属团队
	private String department;			//所属部门
	private String departmentZh;			//所属部门
	private String username;			
	
	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
	}
	public String getgUserId() {
		return gUserId;
	}
	public void setgUserId(String gUserId) {
		this.gUserId = gUserId;
	}
	public String getgScores() {
		return gScores;
	}
	public void setgScores(String gScores) {
		this.gScores = gScores;
	}
	public String getgMonth() {
		return gMonth;
	}
	public void setgMonth(String gMonth) {
		this.gMonth = gMonth;
	}
	public String getgState() {
		return gState;
	}
	public void setgState(String gState) {
		this.gState = gState;
	}
	public String getgRemark() {
		return gRemark;
	}
	public void setgRemark(String gRemark) {
		this.gRemark = gRemark;
	}
	public String getgEmpCode() {
		return gEmpCode;
	}
	public void setgEmpCode(String gEmpCode) {
		this.gEmpCode = gEmpCode;
	}
	public String getDepartmentManager() {
		return departmentManager;
	}
	public void setDepartmentManager(String departmentManager) {
		this.departmentManager = departmentManager;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getTeamZh() {
		return teamZh;
	}
	public void setTeamZh(String teamZh) {
		this.teamZh = teamZh;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartmentZh() {
		return departmentZh;
	}
	public void setDepartmentZh(String departmentZh) {
		this.departmentZh = departmentZh;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getTaskScore() {
		return taskScore;
	}
	public void setTaskScore(String taskScore) {
		this.taskScore = taskScore;
	}
	public String getTaskStatusScore() {
		return taskStatusScore;
	}
	public void setTaskStatusScore(String taskStatusScore) {
		this.taskStatusScore = taskStatusScore;
	}
	public String getSaturationScore() {
		return saturationScore;
	}
	public void setSaturationScore(String saturationScore) {
		this.saturationScore = saturationScore;
	}
	
}
