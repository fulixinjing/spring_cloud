package cn.taskSys.entity;

import java.util.Date;

public class SysUserBranch {
	private String ng_id;
	private String ng_user_id;
	private String ng_branch_id;
	private String ng_post_id;
	private String nt_empley_rcd;
	private Date dt_start_date;
	private String bt_is_primary;
	private String sz_post_type;
	private String nt_order;
	private String ng_creator;
	private Date ts_create;
	
	public String getNg_id() {
		return ng_id;
	}
	public void setNg_id(String ng_id) {
		this.ng_id = ng_id;
	}
	public String getNg_user_id() {
		return ng_user_id;
	}
	public void setNg_user_id(String ng_user_id) {
		this.ng_user_id = ng_user_id;
	}
	public String getNg_branch_id() {
		return ng_branch_id;
	}
	public void setNg_branch_id(String ng_branch_id) {
		this.ng_branch_id = ng_branch_id;
	}
	public String getNg_post_id() {
		return ng_post_id;
	}
	public void setNg_post_id(String ng_post_id) {
		this.ng_post_id = ng_post_id;
	}
	public String getNt_empley_rcd() {
		return nt_empley_rcd;
	}
	public void setNt_empley_rcd(String nt_empley_rcd) {
		this.nt_empley_rcd = nt_empley_rcd;
	}
	public Date getDt_start_date() {
		return dt_start_date;
	}
	public void setDt_start_date(Date dt_start_date) {
		this.dt_start_date = dt_start_date;
	}
	public String getBt_is_primary() {
		return bt_is_primary;
	}
	public void setBt_is_primary(String bt_is_primary) {
		this.bt_is_primary = bt_is_primary;
	}
	public String getSz_post_type() {
		return sz_post_type;
	}
	public void setSz_post_type(String sz_post_type) {
		this.sz_post_type = sz_post_type;
	}
	public String getNt_order() {
		return nt_order;
	}
	public void setNt_order(String nt_order) {
		this.nt_order = nt_order;
	}
	public String getNg_creator() {
		return ng_creator;
	}
	public void setNg_creator(String ng_creator) {
		this.ng_creator = ng_creator;
	}
	public Date getTs_create() {
		return ts_create;
	}
	public void setTs_create(Date ts_create) {
		this.ts_create = ts_create;
	}
	
	
}
