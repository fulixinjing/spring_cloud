package cn.taskSys.entity;

public class ProjectInfo {
	
	private String id;  // 项目ID
	private String code;  // 项目编proName号
	private String proName;  // 项目名称
	private String proType;  // 项目类型
	private String category;  // 项目类别
	private String department;  // 实施部门
	private String bmManager;  // 实施部门经理
	private String xmManager;  // 项目经理
	private String pmo;  // PMO负责人
	private String stage;  // 当前所处阶段
	private String approvalTime;  // 项目立项时间
	private String expectOnlineTime;  // 计划上线时间
	private String schedule;  // 时间进度情况
	private String developType;  // 项目研发类型
	private String requirementControl; //需求控制
	private String riskCondition; //风险情况
	private String remark; //备注
	private String isDelete; //
	private String addTime; //导入时间
	private String isDistribution; //是否可分配
	
	private int page = 1;
	private int maxResult = 10;
	private String proTypeZH;  // 项目类型(中文)
	private String categoryZH;  // 项目类别(中文)
	private String departmentName;  // 实施部门(中文)
	private String bmManagerName;  // 实施部门经理名称
	private String xmManagerName;  // 项目经理名称
	private String pmoName;  // PMO负责人名称
	private String stageZH;  // 当前所处阶段(中文)
	private String scheduleZH;  // 时间进度情况(中文)
	private String developTypeZH;  // 项目研发类型(中文)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBmManager() {
		return bmManager;
	}
	public void setBmManager(String bmManager) {
		this.bmManager = bmManager;
	}
	public String getXmManager() {
		return xmManager;
	}
	public void setXmManager(String xmManager) {
		this.xmManager = xmManager;
	}
	public String getPmo() {
		return pmo;
	}
	public void setPmo(String pmo) {
		this.pmo = pmo;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}
	public String getExpectOnlineTime() {
		return expectOnlineTime;
	}
	public void setExpectOnlineTime(String expectOnlineTime) {
		this.expectOnlineTime = expectOnlineTime;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getDevelopType() {
		return developType;
	}
	public void setDevelopType(String developType) {
		this.developType = developType;
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
	public String getCategoryZH() {
		return categoryZH;
	}
	public void setCategoryZH(String categoryZH) {
		this.categoryZH = categoryZH;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getBmManagerName() {
		return bmManagerName;
	}
	public void setBmManagerName(String bmManagerName) {
		this.bmManagerName = bmManagerName;
	}
	public String getXmManagerName() {
		return xmManagerName;
	}
	public void setXmManagerName(String xmManagerName) {
		this.xmManagerName = xmManagerName;
	}
	public String getPmoName() {
		return pmoName;
	}
	public void setPmoName(String pmoName) {
		this.pmoName = pmoName;
	}
	public String getStageZH() {
		return stageZH;
	}
	public void setStageZH(String stageZH) {
		this.stageZH = stageZH;
	}
	public String getDevelopTypeZH() {
		return developTypeZH;
	}
	public void setDevelopTypeZH(String developTypeZH) {
		this.developTypeZH = developTypeZH;
	}
	public String getScheduleZH() {
		return scheduleZH;
	}
	public void setScheduleZH(String scheduleZH) {
		this.scheduleZH = scheduleZH;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProType() {
		return proType;
	}
	public void setProType(String proType) {
		this.proType = proType;
	}
	public String getProTypeZH() {
		return proTypeZH;
	}
	public void setProTypeZH(String proTypeZH) {
		this.proTypeZH = proTypeZH;
	}
	public String getRequirementControl() {
		return requirementControl;
	}
	public void setRequirementControl(String requirementControl) {
		this.requirementControl = requirementControl;
	}
	public String getRiskCondition() {
		return riskCondition;
	}
	public void setRiskCondition(String riskCondition) {
		this.riskCondition = riskCondition;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getIsDistribution() {
		return isDistribution;
	}
	public void setIsDistribution(String isDistribution) {
		this.isDistribution = isDistribution;
	}
	
}
