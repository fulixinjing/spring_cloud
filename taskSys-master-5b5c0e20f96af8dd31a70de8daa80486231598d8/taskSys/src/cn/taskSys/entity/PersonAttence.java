package cn.taskSys.entity;

import java.util.List;


public class PersonAttence {
	
	/**
	 * 个人考勤主键id
	 */
	private String pId;
	/**
	 * 用户id
	 */
	private String pEmpCode;
	/**
	 * 用户部门Code
	 */
	private String pDepCode;
	/**
	 * 出勤天数
	 */
	private String pAttenceDay;
	/**
	 * 累计加班小时
	 */
	private String pAttenceTime;
	/**
	 * 加班报销
	 */
	private String pAttenceWithoutPay;
	/**
	 * 迟到早退次数
	 */
	private String pAttenceUnpunctualTime;
	/**
	 * 可倒休天数
	 */
	private String pAttenceChangeDay;
	/**
	 * 统计月份
	 */
	private String pAttenceMonth;
	/**
	 * 添加日期
	 */
	private String pAddDate;
	
	/**
	 * 用户名
	 */
	private String pUsername;
	
	/**
	 * 备注
	 */
	private String pRemark;
	/**
	 * 餐补
	 */
	private String pMealPay;
	/**
	 * 交通费
	 */
	private String pTrafficPay;
	
	private int page = 1;
	private int maxResult = 10;
	private String departmentName;
	private String orderByPro;
	private String orderByFlag;
	private List<String> empCodes;
	
	
	public String getpUsername() {
		return pUsername;
	}
	public void setpUsername(String pUsername) {
		this.pUsername = pUsername;
	}
	public String getpRemark() {
		return pRemark;
	}
	public void setpRemark(String pRemark) {
		this.pRemark = pRemark;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpAttenceDay() {
		return pAttenceDay;
	}
	public void setpAttenceDay(String pAttenceDay) {
		this.pAttenceDay = pAttenceDay;
	}
	public String getpAttenceTime() {
		return pAttenceTime;
	}
	public void setpAttenceTime(String pAttenceTime) {
		this.pAttenceTime = pAttenceTime;
	}
	public String getpAttenceWithoutPay() {
		return pAttenceWithoutPay;
	}
	public void setpAttenceWithoutPay(String pAttenceWithoutPay) {
		this.pAttenceWithoutPay = pAttenceWithoutPay;
	}
	public String getpAttenceUnpunctualTime() {
		return pAttenceUnpunctualTime;
	}
	public void setpAttenceUnpunctualTime(String pAttenceUnpunctualTime) {
		this.pAttenceUnpunctualTime = pAttenceUnpunctualTime;
	}
	public String getpAttenceChangeDay() {
		return pAttenceChangeDay;
	}
	public void setpAttenceChangeDay(String pAttenceChangeDay) {
		this.pAttenceChangeDay = pAttenceChangeDay;
	}
	public String getpAttenceMonth() {
		return pAttenceMonth;
	}
	public void setpAttenceMonth(String pAttenceMonth) {
		this.pAttenceMonth = pAttenceMonth;
	}
	public String getpAddDate() {
		return pAddDate;
	}
	public void setpAddDate(String pAddDate) {
		this.pAddDate = pAddDate;
	}
	public String getpEmpCode() {
		return pEmpCode;
	}
	public void setpEmpCode(String pEmpCode) {
		this.pEmpCode = pEmpCode;
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
	public List<String> getEmpCodes() {
		return empCodes;
	}
	public void setEmpCodes(List<String> empCodes) {
		this.empCodes = empCodes;
	}
	public String getpDepCode() {
		return pDepCode;
	}
	public void setpDepCode(String pDepCode) {
		this.pDepCode = pDepCode;
	}
	public String getpMealPay() {
		return pMealPay;
	}
	public void setpMealPay(String pMealPay) {
		this.pMealPay = pMealPay;
	}
	public String getpTrafficPay() {
		return pTrafficPay;
	}
	public void setpTrafficPay(String pTrafficPay) {
		this.pTrafficPay = pTrafficPay;
	}
}
