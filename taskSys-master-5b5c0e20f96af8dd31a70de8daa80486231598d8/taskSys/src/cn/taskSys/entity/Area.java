package cn.taskSys.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import cn.taskSys.base.bean.BaseObject;

public class Area extends BaseObject {
	
	/**
	 * 机构ID
	 */
	private Long id;
	
	private String area_name;
	
	private String parent_id;
	
	private String area_type;
	
	private String area_code;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getArea_name() {
		return area_name;
	}


	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}


	public String getParent_id() {
		return parent_id;
	}


	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}


	public String getArea_type() {
		return area_type;
	}


	public void setArea_type(String area_type) {
		this.area_type = area_type;
	}


	public String getArea_code() {
		return area_code;
	}


	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	} 
}
