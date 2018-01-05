package cn.taskSys.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import cn.taskSys.base.bean.BaseObject;

public class Log extends BaseObject {
	
	private long logid;
	private String logtype;
	private String logusername;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date logdate;
	private String loginfo;
	private String loguserlogin;
	private String logip;
	private String logmodul;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date lastlogdate;
	private Date logsource;
	
	public Date getLastlogdate() {
		return lastlogdate;
	}


	public void setLastlogdate(Date lastlogdate) {
		this.lastlogdate = lastlogdate;
	}


	


	public long getLogid() {
		return logid;
	}


	public void setLogid(long logid) {
		this.logid = logid;
	}


	public Date getLogsource() {
		return logsource;
	}


	public void setLogsource(Date logsource) {
		this.logsource = logsource;
	}


	public String getLogtype() {
		return logtype;
	}


	public void setLogtype(String logtype) {
		this.logtype = logtype.trim();
	}


	public String getLogusername() {
		return logusername;
	}


	public void setLogusername(String logusername) {
		this.logusername = logusername.trim();
	}


	public Date getLogdate() {
		return logdate;
	}


	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}


	public String getLoginfo() {
		return loginfo;
	}


	public void setLoginfo(String loginfo) {
		this.loginfo = loginfo.trim();
	}


	public String getLoguserlogin() {
		return loguserlogin;
	}


	public void setLoguserlogin(String loguserlogin) {
		this.loguserlogin = loguserlogin.trim();
	}


	public String getLogip() {
		return logip;
	}


	public void setLogip(String logip) {
		this.logip = logip.trim();
	}


	public String getLogmodul() {
		return logmodul;
	}


	public void setLogmodul(String logmodul) {
		this.logmodul = logmodul.trim();
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	} 
}
