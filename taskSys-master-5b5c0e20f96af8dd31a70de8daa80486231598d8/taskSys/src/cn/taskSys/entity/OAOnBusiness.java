package cn.taskSys.entity;

public class OAOnBusiness {
	private String oa_id; //出差ID
	private String on_business_person; //出差人
	private String company; //出差人所在分部（公司）
	private String department; //出差人所在部门
	private String apply_time; //申请时间
	private String start_site; //出发地点
	private String on_business_site; //出差地点
	private String is_companion; //是否有同伴
	private String companion; //同行人
	private String companion_sex; //同行人性别
	private String start_date; //出发日期
	private String start_time; //出发时间
	private String traffic_tool; //交通工具
	private String schedule; //班次
	private String back_date; //返回日期
	private String back_time; //返回时间
	private String pre_days; //预计天数
	private String back_traffic_tool; //返回交通工具
	private String back_shcedule; //返回班次
	private String add_time; //添加时间
	private String emp_code;//员工编号
	private String in_type;//导入类型
	private String is_update;//更新考勤备注标志
	
	private int page = 1;
	private int maxResult = 10;
	private String apply_time_start;//申请开始时间
	private String apply_time_end;//申请结束时间
	
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
	
	public String getOa_id() {
		return oa_id;
	}
	public void setOa_id(String oa_id) {
		this.oa_id = oa_id;
	}
	public String getOn_business_person() {
		return on_business_person;
	}
	public void setOn_business_person(String on_business_person) {
		this.on_business_person = on_business_person;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getStart_site() {
		return start_site;
	}
	public void setStart_site(String start_site) {
		this.start_site = start_site;
	}
	public String getOn_business_site() {
		return on_business_site;
	}
	public void setOn_business_site(String on_business_site) {
		this.on_business_site = on_business_site;
	}
	public String getIs_companion() {
		return is_companion;
	}
	public void setIs_companion(String is_companion) {
		this.is_companion = is_companion;
	}
	public String getCompanion() {
		return companion;
	}
	public void setCompanion(String companion) {
		this.companion = companion;
	}
	public String getCompanion_sex() {
		return companion_sex;
	}
	public void setCompanion_sex(String companion_sex) {
		this.companion_sex = companion_sex;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getTraffic_tool() {
		return traffic_tool;
	}
	public void setTraffic_tool(String traffic_tool) {
		this.traffic_tool = traffic_tool;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getBack_date() {
		return back_date;
	}
	public void setBack_date(String back_date) {
		this.back_date = back_date;
	}
	public String getBack_time() {
		return back_time;
	}
	public void setBack_time(String back_time) {
		this.back_time = back_time;
	}
	public String getPre_days() {
		return pre_days;
	}
	public void setPre_days(String pre_days) {
		this.pre_days = pre_days;
	}
	public String getBack_traffic_tool() {
		return back_traffic_tool;
	}
	public void setBack_traffic_tool(String back_traffic_tool) {
		this.back_traffic_tool = back_traffic_tool;
	}
	public String getBack_shcedule() {
		return back_shcedule;
	}
	public void setBack_shcedule(String back_shcedule) {
		this.back_shcedule = back_shcedule;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getIn_type() {
		return in_type;
	}
	public void setIn_type(String in_type) {
		this.in_type = in_type;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getApply_time_start() {
		return apply_time_start;
	}
	public void setApply_time_start(String apply_time_start) {
		this.apply_time_start = apply_time_start;
	}
	public String getApply_time_end() {
		return apply_time_end;
	}
	public void setApply_time_end(String apply_time_end) {
		this.apply_time_end = apply_time_end;
	}
	public String getIs_update() {
		return is_update;
	}
	public void setIs_update(String is_update) {
		this.is_update = is_update;
	}
	
}
