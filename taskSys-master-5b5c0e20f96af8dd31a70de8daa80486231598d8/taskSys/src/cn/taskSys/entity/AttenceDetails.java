package cn.taskSys.entity;

public class AttenceDetails {
	
	private String id;
	private String username;
	private String empCode;//员工号
	private String department;
	private String attenceDate;//考勤日期
	private String week; //星期
	private String dateType;//日期类型
	private String attenceType;//考勤制度
	private String morningAttence;//上午考勤
	private String afternoonAttence;//下午考勤
	private String remark;//备注
	private String remarkFlag;//备注变更
	
	private int page = 1;
	private int maxResult = 10;
	private String departmentName;
	private String weekName;//星期
	private String dateTypeName;//日期类型
	private String attenceTypeName;//考勤制度
	private String orderByPro;
	private String orderByFlag;
	private String isException;//考勤异常标红
	private String flag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getAttenceDate() {
		return attenceDate;
	}
	public void setAttenceDate(String attenceDate) {
		this.attenceDate = attenceDate;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getAttenceType() {
		return attenceType;
	}
	public void setAttenceType(String attenceType) {
		this.attenceType = attenceType;
	}
	public String getMorningAttence() {
		return morningAttence;
	}
	public void setMorningAttence(String morningAttence) {
		this.morningAttence = morningAttence;
	}
	public String getAfternoonAttence() {
		return afternoonAttence;
	}
	public void setAfternoonAttence(String afternoonAttence) {
		this.afternoonAttence = afternoonAttence;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	public String getWeekName() {
		return weekName;
	}
	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}
	public String getDateTypeName() {
		return dateTypeName;
	}
	public void setDateTypeName(String dateTypeName) {
		this.dateTypeName = dateTypeName;
	}
	public String getAttenceTypeName() {
		return attenceTypeName;
	}
	public void setAttenceTypeName(String attenceTypeName) {
		this.attenceTypeName = attenceTypeName;
	}
	public String getRemarkFlag() {
		return remarkFlag;
	}
	public void setRemarkFlag(String remarkFlag) {
		this.remarkFlag = remarkFlag;
	}
	public String getIsException() {
		return isException;
	}
	public void setIsException(String isException) {
		this.isException = isException;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
