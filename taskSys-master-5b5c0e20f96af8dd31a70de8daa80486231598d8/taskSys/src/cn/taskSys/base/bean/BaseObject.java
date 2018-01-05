package cn.taskSys.base.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public abstract class BaseObject implements Serializable {

	private static final long serialVersionUID = -1031601349446401649L;

	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后修改人
	 */
	private Long lastModifyBy;
	/**
	 * 最后修改时间  保存时间
	 */
	private String lastModifyTime;

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getLastModifyBy() {
		return lastModifyBy;
	}

	public void setLastModifyBy(Long lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}
