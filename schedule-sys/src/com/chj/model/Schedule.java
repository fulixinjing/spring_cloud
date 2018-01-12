package com.chj.model;

import java.util.Date;

import com.chj.pageUtil.Page;


/**
 * 日程安排实体
 * @author flx
 *
 */
public class Schedule extends Page<Schedule>{

	private String id;
	//名称
	private String name; 
	//开始时间
	private String startDate;
	//结束时间
	private String endDate;
	//是否提醒 1提醒 2不提醒
	private String remind ="2";
	//优先级
	private String priority;
	//备注
	private String remarks;
	//创建用户
	private String userId;
	//创建时间
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRemind() {
		return remind;
	}
	public void setRemind(String remind) {
		this.remind = remind;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
