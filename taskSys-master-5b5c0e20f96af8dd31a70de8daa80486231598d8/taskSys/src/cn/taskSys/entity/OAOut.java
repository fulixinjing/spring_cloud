package cn.taskSys.entity;

public class OAOut {
	private String oa_id; //外出ID
	private String out_person; //外出人
	private String company; //外出人所属分部（公司）
	private String department; //所属部门
	private String apply_time; //申请时间
	private String start_date; //外出开始日期
	private String end_date; //外出结束日期
	private String start_time; //外出开始时间
	private String end_time; //外出结束时间
	private String un_punch_moring; //上午未打卡
	private String un_punch_afternoon; //下午未打卡
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
	public String getOut_person() {
		return out_person;
	}
	public void setOut_person(String out_person) {
		this.out_person = out_person;
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
	public String getUn_punch_moring() {
		return un_punch_moring;
	}
	public void setUn_punch_moring(String un_punch_moring) {
		this.un_punch_moring = un_punch_moring;
	}
	public String getUn_punch_afternoon() {
		return un_punch_afternoon;
	}
	public void setUn_punch_afternoon(String un_punch_afternoon) {
		this.un_punch_afternoon = un_punch_afternoon;
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
