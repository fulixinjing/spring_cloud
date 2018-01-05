package cn.taskSys.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import cn.taskSys.base.bean.BaseObject;

public class Menu  extends BaseObject{
	
	
	private String id;
	private String menu_name;
	private String menu_pid;
	private String description;
	private String menu_source;
	private String menu_url;
	private String menu_status;
	private String menu_level;
	private String menu_icon;
	private String privileges_code;
	private int menu_sort;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_pid() {
		return menu_pid;
	}
	public void setMenu_pid(String menu_pid) {
		this.menu_pid = menu_pid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public String getMenu_status() {
		return menu_status;
	}
	public void setMenu_status(String menu_status) {
		this.menu_status = menu_status;
	}
	
	public String getMenu_source() {
		return menu_source;
	}
	public void setMenu_source(String menu_source) {
		this.menu_source = menu_source;
	}
	public String getMenu_level() {
		return menu_level;
	}
	public void setMenu_level(String menu_level) {
		this.menu_level = menu_level;
	}
	public String getMenu_icon() {
		return menu_icon;
	}
	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}
	public String getPrivileges_code() {
		return privileges_code;
	}
	public void setPrivileges_code(String privileges_code) {
		this.privileges_code = privileges_code;
	}
	public int getMenu_sort() {
		return menu_sort;
	}
	public void setMenu_sort(int menu_sort) {
		this.menu_sort = menu_sort;
	}
	

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	} 
	/**
	 * 菜单ID
	 *//*
	private Long id;				
	*//**
	 * 菜单名称
	 *//*
	private String menuName	;	
	*//**
	 *菜单类型（1.汇金 2.财富 3.汇诚）
	 *//*
	private String menuSource;		
	*//**
	 * 父id
	 *//*
	private String menuPid;		
	*//**
	 * 描述
	 *//*
	private String description;		
	*//**
	 * 菜单url地址
	 *//*
	private String menuUrl	;	
	*//**
	 * 状态：0-可用，1-不可用
	 *//*
	private String menuStatus;		
	*//**
	 * 菜单级别
	 *//*
	private String menuLevel;		
	*//**
	 * 图标
	 *//*
	private String menuIcon	;	
	*//**
	 * 权限
	 *//*
	private String privilegesCode;	
	*//**
	 * 排序
	 *//*
	private String menuSort;*/
		
	
}
