package com.chj.model;

/**
 * 个人统计
 * @author flx
 *
 */
public class PersonSys {

	//月份
	private String month;
	
	//正常完成
	private int normalFinish = 0;
	
	//进行中
	private int haveInHand = 0;
	
	//尚未开始
	private int beforeStart = 0;
	
	private String userId;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getNormalFinish() {
		return normalFinish;
	}

	public void setNormalFinish(int normalFinish) {
		this.normalFinish = normalFinish;
	}

	public int getHaveInHand() {
		return haveInHand;
	}

	public void setHaveInHand(int haveInHand) {
		this.haveInHand = haveInHand;
	}

	public int getBeforeStart() {
		return beforeStart;
	}

	public void setBeforeStart(int beforeStart) {
		this.beforeStart = beforeStart;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
