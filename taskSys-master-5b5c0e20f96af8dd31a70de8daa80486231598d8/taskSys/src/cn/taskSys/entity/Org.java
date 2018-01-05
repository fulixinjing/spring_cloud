package cn.taskSys.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import cn.taskSys.base.bean.BaseObject;

public class Org extends BaseObject {
	
	/**
	 * 组织ID
	 */
	private String organId;
	/**
	 * 组织名
	 */
	private String organName;	
	/**
	 * 组织类型
	 */
	private String organType;		
	/**
	 * 所属城市
	 */
	private String organCity;		
	/**
	 * 组织编码
	 */
	private String organCode;		
	/**
	 * 父组织id
	 */
	private String parentid;		
	/**
	 * 状态
	 */
	private String organStatus;
	/**
	 * 单位来源
	 */
	private String organSource;
	/**
	 * 层级
	 */
	private String organLevel;
	/**
	 * 单位排序号
	 */
	private String struOrder;
	/**
	 * 单位简称
	 */
	private String shortName;
	/**
	 * 办公地址
	 */
	private String address;	
	/**
	 * 办公电话
	 */
	private String officePhone;		
	/**
	 * 主要负责人
	 */
	private String cropDutyMan;
	/**
	 * 部门负责人
	 */
	private String depDutyMan;
	/**
	 * 联系人
	 */
	private String linkMan;	
	/**
	 * 联系电话
	 */
	private String linkPhone;	
	/**
	 * 传真
	 */
	private String fax;		
	/**
	 * 开始时间
	 */
	private String beginDate;			
	/**
	 * 结束时间
	 */
	private String endDate;			
	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganType() {
		return organType;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getOrganCity() {
		return organCity;
	}

	public void setOrganCity(String organCity) {
		this.organCity = organCity;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getOrganStatus() {
		return organStatus;
	}

	public void setOrganStatus(String organStatus) {
		this.organStatus = organStatus;
	}

	public String getOrganSource() {
		return organSource;
	}

	public void setOrganSource(String organSource) {
		this.organSource = organSource;
	}

	public String getOrganLevel() {
		return organLevel;
	}

	public void setOrganLevel(String organLevel) {
		this.organLevel = organLevel;
	}

	public String getStruOrder() {
		return struOrder;
	}

	public void setStruOrder(String struOrder) {
		this.struOrder = struOrder;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getCropDutyMan() {
		return cropDutyMan;
	}

	public void setCropDutyMan(String cropDutyMan) {
		this.cropDutyMan = cropDutyMan;
	}

	public String getDepDutyMan() {
		return depDutyMan;
	}

	public void setDepDutyMan(String depDutyMan) {
		this.depDutyMan = depDutyMan;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	} 
}
