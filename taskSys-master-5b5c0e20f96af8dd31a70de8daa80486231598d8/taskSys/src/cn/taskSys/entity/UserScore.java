package cn.taskSys.entity;

public class UserScore {
	
	private String login_name;
	private String user_name;
	private String department;
	private String department_Name;
	private String team;
	private String team_Name;
	private String position_Name;
	private String post_Name;
	private String g_scores;
	private String g_remark;
	private String g_month;
	private int page = 1;
	private int maxResult = 10;
	private String gMonth;
	private String add_time;
	private String orderByPro;
	private String orderByFlag;
	private String g_task_score;
	private String g_task_status_score;
	private String g_saturation_score;
	
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getDepartment_Name() {
		return department_Name;
	}
	public void setDepartment_Name(String department_Name) {
		this.department_Name = department_Name;
	}
	public String getTeam_Name() {
		return team_Name;
	}
	public void setTeam_Name(String team_Name) {
		this.team_Name = team_Name;
	}
	public String getPosition_Name() {
		return position_Name;
	}
	public void setPosition_Name(String position_Name) {
		this.position_Name = position_Name;
	}
	public String getPost_Name() {
		return post_Name;
	}
	public void setPost_Name(String post_Name) {
		this.post_Name = post_Name;
	}
	public String getG_scores() {
		return g_scores;
	}
	public void setG_scores(String g_scores) {
		this.g_scores = g_scores;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	public String getgMonth() {
		return gMonth;
	}
	public void setgMonth(String gMonth) {
		this.gMonth = gMonth;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getG_remark() {
		return g_remark;
	}
	public void setG_remark(String g_remark) {
		this.g_remark = g_remark;
	}
	public String getG_month() {
		return g_month;
	}
	public void setG_month(String g_month) {
		this.g_month = g_month;
	}
	public String getOrderByPro() {
		return orderByPro;
	}
	public void setOrderByPro(String orderByPro) {
		this.orderByPro = orderByPro;
	}
	public String getOrderByFlag() {
		return orderByFlag;
	}
	public void setOrderByFlag(String orderByFlag) {
		this.orderByFlag = orderByFlag;
	}
	public String getG_task_score() {
		return g_task_score;
	}
	public void setG_task_score(String g_task_score) {
		this.g_task_score = g_task_score;
	}
	public String getG_task_status_score() {
		return g_task_status_score;
	}
	public void setG_task_status_score(String g_task_status_score) {
		this.g_task_status_score = g_task_status_score;
	}
	public String getG_saturation_score() {
		return g_saturation_score;
	}
	public void setG_saturation_score(String g_saturation_score) {
		this.g_saturation_score = g_saturation_score;
	}
	
}
