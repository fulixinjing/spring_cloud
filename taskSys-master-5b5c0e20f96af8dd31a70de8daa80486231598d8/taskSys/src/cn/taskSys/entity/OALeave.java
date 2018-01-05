package cn.taskSys.entity;

public class OALeave {
	private String oa_id; //请假ID
	private String leave_person; //请假人
	private String emp_code; //申请人员工号
	private String operator; //经办人
	private String company; //申请人所属分部（公司）
	private String department; //申请人所属部门
	private String post; //申请人岗位
	private String apply_time; //申请日期
	private String type; //请假类型
	private String taking_work_time; //入职日期
	private String start_date; //请假开始日期
	private String end_date; //请假结束日期
	private String start_time; //请假开始时间
	private String end_time; //请假结束时间
	private String days; //请假天数
	private String add_time; //添加时间
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
	public String getLeave_person() {
		return leave_person;
	}
	public void setLeave_person(String leave_person) {
		this.leave_person = leave_person;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTaking_work_time() {
		return taking_work_time;
	}
	public void setTaking_work_time(String taking_work_time) {
		this.taking_work_time = taking_work_time;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
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
