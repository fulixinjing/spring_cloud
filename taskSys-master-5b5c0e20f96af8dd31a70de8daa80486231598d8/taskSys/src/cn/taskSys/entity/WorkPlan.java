package cn.taskSys.entity;

public class WorkPlan {
	private String id;	// id
	
	private String projectCode; // 项目编号
	
	private String projectName; // 项目名称
	
	private String department; 	 // 所属部门(code)
	
	private String departmentPlan; // 本月部门工作计划/目标
	
	private String importance; 	// 重要程度(code)
	
	private String principal;   // 负责人（工号）
	
	private String verifier; 	// 外部确认人
	
	private String type; 		// 类型(code)
	
	private String addTime; 		// 添加时间
	
	private String content1; 		// 工作内容（第一周）
	
	private String teamMember1; 	// 团队成员（第一周）
	
	private String completeStatus1; // 完成情况（第一周）
	
	private String content2; 		// 工作内容（第二周）
	
	private String teamMember2; 	// 团队成员（第二周）
	
	private String completeStatus2; // 完成情况（第二周）
	
	private String content3; 		// 工作内容（第三周）
	
	private String teamMember3; 	// 团队成员（第三周）
	
	private String completeStatus3; // 完成情况（第三周）
	
	private String content4;		// 工作任务（第四周）
	
	private String teamMember4; 	// 团队成员（第四周）
	
	private String completeStatus4; // 完成情况（第四周）
	
	private String content5; 		// 工作内容（第五周）
	
	private String teamMember5; 	// 团队成员（第五周）
	
	private String completeStatus5; // 完成情况（第五周）
	
	private String userId;//操作人ID
	
	private String title1;//工作标题（第一周）
	private String title2;//工作标题（第二周）
	private String title3;//工作标题（第三周）
	private String title4;//工作标题（第四周）
	private String title5;//工作标题（第五周）
	
	private String risk;//遇到风险
	
	private String measures;//采取措施
	
	private String month;//月份
	
	private String team;//所属团队

	private int page = 1;
	private int maxResult = 10;
	private String typeName; 		// 类型(中文)
	private String importanceName; 	// 重要性(中文)
	private String departmentName; 	 // 所属部门(中文)
	private String principalName;   // 负责人名称
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentPlan() {
		return departmentPlan;
	}

	public void setDepartmentPlan(String departmentPlan) {
		this.departmentPlan = departmentPlan;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public String getTeamMember1() {
		return teamMember1;
	}

	public void setTeamMember1(String teamMember1) {
		this.teamMember1 = teamMember1;
	}

	public String getCompleteStatus1() {
		return completeStatus1;
	}

	public void setCompleteStatus1(String completeStatus1) {
		this.completeStatus1 = completeStatus1;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public String getTeamMember2() {
		return teamMember2;
	}

	public void setTeamMember2(String teamMember2) {
		this.teamMember2 = teamMember2;
	}

	public String getCompleteStatus2() {
		return completeStatus2;
	}

	public void setCompleteStatus2(String completeStatus2) {
		this.completeStatus2 = completeStatus2;
	}

	public String getContent3() {
		return content3;
	}

	public void setContent3(String content3) {
		this.content3 = content3;
	}

	public String getTeamMember3() {
		return teamMember3;
	}

	public void setTeamMember3(String teamMember3) {
		this.teamMember3 = teamMember3;
	}

	public String getCompleteStatus3() {
		return completeStatus3;
	}

	public void setCompleteStatus3(String completeStatus3) {
		this.completeStatus3 = completeStatus3;
	}

	public String getContent4() {
		return content4;
	}

	public void setContent4(String content4) {
		this.content4 = content4;
	}

	public String getTeamMember4() {
		return teamMember4;
	}

	public void setTeamMember4(String teamMember4) {
		this.teamMember4 = teamMember4;
	}

	public String getCompleteStatus4() {
		return completeStatus4;
	}

	public void setCompleteStatus4(String completeStatus4) {
		this.completeStatus4 = completeStatus4;
	}

	public String getContent5() {
		return content5;
	}

	public void setContent5(String content5) {
		this.content5 = content5;
	}

	public String getTeamMember5() {
		return teamMember5;
	}

	public void setTeamMember5(String teamMember5) {
		this.teamMember5 = teamMember5;
	}

	public String getCompleteStatus5() {
		return completeStatus5;
	}

	public void setCompleteStatus5(String completeStatus5) {
		this.completeStatus5 = completeStatus5;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getImportanceName() {
		return importanceName;
	}

	public void setImportanceName(String importanceName) {
		this.importanceName = importanceName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getMeasures() {
		return measures;
	}

	public void setMeasures(String measures) {
		this.measures = measures;
	}
	
	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getTitle3() {
		return title3;
	}

	public void setTitle3(String title3) {
		this.title3 = title3;
	}

	public String getTitle4() {
		return title4;
	}

	public void setTitle4(String title4) {
		this.title4 = title4;
	}

	public String getTitle5() {
		return title5;
	}

	public void setTitle5(String title5) {
		this.title5 = title5;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
}
