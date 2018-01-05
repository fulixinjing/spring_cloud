package cn.taskSys.common.cache;

import java.util.Date;

/**
 * 用户缓存对象,其中loginUUID为必填属性
 *            扩展对象
 */
public class UserCachedEntity<T extends Object> {
	private String loginUUID;// 79C9334A-F87C-4359-9BE0-360E46C254B7
	private String loginName = "unknown";// admin,unknown
	private String loginCaption = "Unknown";// Administrator,Unknown
	private String loginIP = "127.0.0.1";
	private Date loginDate = java.util.Calendar.getInstance().getTime();// 2014-02-25
	private Date loginHeartbeatDate = java.util.Calendar.getInstance()
			.getTime();// 间隔15分钟(loginHeartbeatDate>System.currentTimeMillis()在线)
	private int loginType = 0;// 1=Successful,0=fail
	private String loginDesc = "";// Administrator Login/Logout Successful/fail!
	private Integer loginCount = 0;// 3
	private T extend;// extend object
	private String source;
	private String empCode;
	
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLoginUUID() {
		return loginUUID;
	}

	public void setLoginUUID(String loginUUID) {
		this.loginUUID = loginUUID;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginCaption() {
		return loginCaption;
	}

	public void setLoginCaption(String loginCaption) {
		this.loginCaption = loginCaption;
	}

	public String getLoginIP() {
		
		return loginIP;
	}

	public void setLoginIP(String loingIP) {
		this.loginIP = loingIP;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLoginHeartbeatDate() {
		return loginHeartbeatDate;
	}

	public void setLoginHeartbeatDate(Date loginHeartbeatDate) {
		this.loginHeartbeatDate = loginHeartbeatDate;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getLoginDesc() {
		return loginDesc;
	}

	public void setLoginDesc(String loginDesc) {
		this.loginDesc = loginDesc;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public T getExtend() {
		return extend;
	}

	public void setExtend(T extend) {
		this.extend = extend;
	}
}
