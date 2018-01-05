package cn.taskSys.entity;

import java.io.Serializable;



public class TaskInfo  implements Serializable{
	
	private String id;					//任务id	
	private String allocationUser;		//任务分配人id
	private String taskname;				//任务名字
	private String taskContent;				//任务内容
	private String createTime;		    //任务开始时间
	private String expectEndTime;			//计划结束时间
	private String actualEndTime;			//实际结束时间
	private String taskstatus;				//任务状态，1待分配，2已分配，3进行中，4延期未提交，5延期提交，6按时提交，7提前提交，8已完成	
	private String executedevtasksys;        //任务执行者id
	private String score;				//得分
	private String remark;				//任务备注
	private String isdel ;              //是否删除
	private String create_name;			//任务分配者名称
	private String exec_name; 			//任务执行者名称
	private String direct_supervisor;	//直接主管
	private String team;				//所属团队
	private String department;			//所属部门
	private String jobId;			//所属岗位
	private String yfpDate;			//已分配时间
	private String jxzDate;			//进行中时间（已接收时间）
	private String subDate;			//提交时间
	private String endDate;			//完成时间
	private String falred;    //任务延期标识符    0 延期     1 未延期
	private String taskpid;   //上级任务id   主管向下分配任务时，相当于主管新建任务，需加上原任务id
	private String tjtype;	  //任务提交状态
	private int page = 1;
	private int maxResult = 10;
	private String conQuarter;	//季度
	private String conYear;		//年度
	private String tLevel;		//任务级别
	private String taskWorkTime;//任务工作量
	private String projectCode;//项目编号
	private String projectName;//项目名称
	private String projectStage;//项目阶段
	
	private String startTime;	//查询开始时间
	private String endTime;		//查询结束时间
	private String expectEndTime1;	//查询计划开始时间
	private String expectEndTime2;	//查询计划结束时间
	private String lowScore;	//选择最低查询分数
	private String highScore;	//选择最高查询分数
	private String departmentZh;//部门(中文)
	private String teamZh;		//团队(中文)
	private String taskstatusZh;//状态(中文)
	private String orderByPro;
	private String exeEmail;		//执行人邮件
	private String createEmail;		//创建人邮件
	private String ids;				//查询任务时，判断是否存在子任务的数量
	private String tstatus;		//任务活动状态  0 启用  1 暂停   2 终止
	private String final_time;		//任务终止时间
	private String start_suspend_time;				//任务启用或暂停时间
	private String deliver_person;  //转交人ID
	private String deliverPersonZh;  //转交人Name
	private String deliver_time; //任务转交时间
	private String is_pass; //是否通过（部门领导审核）
	private String is_deliver; //是否为转交任务
	private String delivered; //原任务是否已经转交
	private String deliver_taskid; //转交（原）任务ID
	private String orderByFlag;
	private String refuseCause;//拒绝原因
	private String head_portrait;//用户头像
	
	
	public String getHead_portrait()
    {
        return head_portrait;
    }
    public void setHead_portrait(String head_portrait)
    {
        this.head_portrait = head_portrait;
    }
    public String getRefuseCause()
    {
        return refuseCause;
    }
    public void setRefuseCause(String refuseCause)
    {
        this.refuseCause = refuseCause;
    }
    public String getTstatus() {
		return tstatus;
	}
	public void setTstatus(String tstatus) {
		this.tstatus = tstatus;
	}
	public String getFinal_time() {
		return final_time;
	}
	public void setFinal_time(String final_time) {
		this.final_time = final_time;
	}
	public String getStart_suspend_time() {
		return start_suspend_time;
	}
	public void setStart_suspend_time(String start_suspend_time) {
		this.start_suspend_time = start_suspend_time;
	}
	public String getExeEmail() {
		return exeEmail;
	}
	public void setExeEmail(String exeEmail) {
		this.exeEmail = exeEmail;
	}
	public String getCreateEmail() {
		return createEmail;
	}
	public void setCreateEmail(String createEmail) {
		this.createEmail = createEmail;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
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
	
	public String getTaskpid() {
		return taskpid;
	}
	public void setTaskpid(String taskpid) {
		this.taskpid = taskpid;
	}
	public String getFalred() {
		return falred;
	}
	public void setFalred(String falred) {
		this.falred = falred;
	}
	public String getCreate_name() {
		return create_name;
	}
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}
	public String getExec_name() {
		return exec_name;
	}
	public void setExec_name(String exec_name) {
		this.exec_name = exec_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAllocationUser() {
		return allocationUser;
	}
	public void setAllocationUser(String allocationUser) {
		this.allocationUser = allocationUser;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getExpectEndTime() {
		return expectEndTime;
	}
	public void setExpectEndTime(String expectEndTime) {
		this.expectEndTime = expectEndTime;
	}
	public String getActualEndTime() {
		return actualEndTime;
	}
	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}
	public String getTaskstatus() {
		return taskstatus;
	}
	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getLowScore() {
		return lowScore;
	}
	public void setLowScore(String lowScore) {
		this.lowScore = lowScore;
	}
	public String getHighScore() {
		return highScore;
	}
	public void setHighScore(String highScore) {
		this.highScore = highScore;
	}
	public String getYfpDate() {
		return yfpDate;
	}
	public void setYfpDate(String yfpDate) {
		this.yfpDate = yfpDate;
	}
	public String getJxzDate() {
		return jxzDate;
	}
	public void setJxzDate(String jxzDate) {
		this.jxzDate = jxzDate;
	}
	public String getSubDate() {
		return subDate;
	}
	public void setSubDate(String subDate) {
		this.subDate = subDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getExecutedevtasksys() {
		return executedevtasksys;
	}
	public void setExecutedevtasksys(String executedevtasksys) {
		this.executedevtasksys = executedevtasksys;
	}
	public String getDirect_supervisor() {
		return direct_supervisor;
	}
	public void setDirect_supervisor(String direct_supervisor) {
		this.direct_supervisor = direct_supervisor;
	}
	public String getDepartmentZh() {
		return departmentZh;
	}
	public void setDepartmentZh(String departmentZh) {
		this.departmentZh = departmentZh;
	}
	public String getTeamZh() {
		return teamZh;
	}
	public void setTeamZh(String teamZh) {
		this.teamZh = teamZh;
	}
	public String getTaskstatusZh() {
		return taskstatusZh;
	}
	public void setTaskstatusZh(String taskstatusZh) {
		this.taskstatusZh = taskstatusZh;
	}
	public String getTjtype() {
		return tjtype;
	}
	public void setTjtype(String tjtype) {
		this.tjtype = tjtype;
	}
	public String getOrderByPro() {
		return orderByPro;
	}
	public void setOrderByPro(String orderByPro) {
		this.orderByPro = orderByPro;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getConQuarter() {
		return conQuarter;
	}
	public void setConQuarter(String conQuarter) {
		this.conQuarter = conQuarter;
	}
	public String getConYear() {
		return conYear;
	}
	public void setConYear(String conYear) {
		this.conYear = conYear;
	}
	public String gettLevel() {
		return tLevel;
	}
	public void settLevel(String tLevel) {
		this.tLevel = tLevel;
	}
	public String getDeliver_person() {
		return deliver_person;
	}
	public void setDeliver_person(String deliver_person) {
		this.deliver_person = deliver_person;
	}
	public String getDeliver_time() {
		return deliver_time;
	}
	public void setDeliver_time(String deliver_time) {
		this.deliver_time = deliver_time;
	}
	public String getIs_pass() {
		return is_pass;
	}
	public void setIs_pass(String is_pass) {
		this.is_pass = is_pass;
	}
	public String getIs_deliver() {
		return is_deliver;
	}
	public void setIs_deliver(String is_deliver) {
		this.is_deliver = is_deliver;
	}
	public String getDelivered() {
		return delivered;
	}
	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}
	public String getDeliver_taskid() {
		return deliver_taskid;
	}
	public void setDeliver_taskid(String deliver_taskid) {
		this.deliver_taskid = deliver_taskid;
	}
	public String getOrderByFlag() {
		return orderByFlag;
	}
	public void setOrderByFlag(String orderByFlag) {
		this.orderByFlag = orderByFlag;
	}
	public String getDeliverPersonZh() {
		return deliverPersonZh;
	}
	public void setDeliverPersonZh(String deliverPersonZh) {
		this.deliverPersonZh = deliverPersonZh;
	}

	public String getTaskWorkTime() {
		return taskWorkTime;
	}
	public void setTaskWorkTime(String taskWorkTime) {
		this.taskWorkTime = taskWorkTime;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectStage() {
		return projectStage;
	}
	public void setProjectStage(String projectStage) {
		this.projectStage = projectStage;
	}
	public String getExpectEndTime1() {
		return expectEndTime1;
	}
	public void setExpectEndTime1(String expectEndTime1) {
		this.expectEndTime1 = expectEndTime1;
	}
	public String getExpectEndTime2() {
		return expectEndTime2;
	}
	public void setExpectEndTime2(String expectEndTime2) {
		this.expectEndTime2 = expectEndTime2;
	}
	
	
}
