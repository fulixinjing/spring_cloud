package cn.taskSys.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("Resource")
public class Resource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2931771979436790620L;

	/**
	 * ID锛屾儫涓�爣璇�
	 */
	private String id;

	/**
	 * 璧勬簮鍚嶇О
	 */
	private String resource_name;

	/**
	 * 璧勬簮缂栫爜
	 */
	private String resource_code;

	/**
	 * 璧勬簮璺緞
	 */
	private String url;

	/**
	 * 璧勬簮鎻忚堪
	 */
	private String description;

	/**
	 * 鏄惁鏈夋晥
	 */
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResource_name() {
		return resource_name;
	}

	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}

	public String getResource_code() {
		return resource_code;
	}

	public void setResource_code(String resource_code) {
		this.resource_code = resource_code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", resource_name=" + resource_name
				+ ", resource_code=" + resource_code + ", url=" + url
				+ ", description=" + description + ", status=" + status + "]";
	}
	
	/////////////////////////////// 非持久化属性 ///////////////////////////////////
	private String resource_name_old;

	public String getResource_name_old() {
		return resource_name_old;
	}

	public void setResource_name_old(String resource_name_old) {
		this.resource_name_old = resource_name_old;
	}
	
	
	
	
	
}
