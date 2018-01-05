package cn.taskSys.controller;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.taskSys.base.PageView;
import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Grades;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.entity.WorkTime;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.IAdminService;
import cn.taskSys.service.ISummaryTaskService;
import cn.taskSys.service.IUserService;
import cn.taskSys.service.IWorkTimeService;
import cn.taskSys.service.LogService;
import cn.taskSys.service.RoleService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.EncodeUtil;
import cn.taskSys.utils.ExportExcelUtil;
import cn.taskSys.utils.MathUtil;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.SendEmailUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;
import cn.taskSys.utils.UtilValidate;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseAction<Object>{
	@Autowired
	private IWorkTimeService workTimeService;
	
	@Autowired
	private ISummaryTaskService summaryTaskService;
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private UtilsService utilsService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private LogService logService;
	/**
	 * 创建任务
	 * 
	 * @param user 
	 * 
	 * @return
	 */
	@RequestMapping(value="/createTask")
	@LogAnnotation(eventCode="1021001",eventProcess="")
	public String createTask(HttpSession session, HttpServletRequest request, Model m) {
		
		return "index3";
	}
	
/*
	
	@RequestMapping(value="/reLogin")
	@LogAnnotation(eventCode="1021001",eventProcess="")
	public String reLogin(HttpSession session, HttpServletRequest request, Model m) {
		
		return "reLogin";
	}*/
	
	/**
	 * 保存任务
	 * 
	 * @param map
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "/saveTask")
	@LogAnnotation(eventCode = "1021002", eventProcess = "")
	public void saveTask(HttpSession session, HttpServletRequest request,HttpServletResponse response,Model m) {
		
		String nowDate=getDate(0); //系统当前时间
		
		String id = UUIDHexGenerator.generater();
		
		String userId = StringUtil.nvlString(PublicUtil.getUserOnline(request)
				.getUserId());
		String userName = StringUtil.nvlString(PublicUtil
				.getUserOnline(request).getUserName());
		String taskname = StringUtil.nvlString(request.getParameter("taskname"));
		String taskContent = StringUtil.nvlString(request
				.getParameter("taskContent"));
		String expectEndTime = StringUtil.nvlString(request
				.getParameter("expectEndTime"));
		String taskWorkTime = StringUtil.nvlString(request
				.getParameter("taskWorkTime"));
		String projectName = StringUtil.nvlString(request
				.getParameter("projectName"));
		String projectCode = StringUtil.nvlString(request
				.getParameter("projectCode"));
		String projectStage = StringUtil.nvlString(request
				.getParameter("projectStage"));
		
		String str = nowDate.substring(10,19);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("taskname", taskname);
		map.put("taskContent", taskContent);
		map.put("taskWorkTime", taskWorkTime);
		map.put("userId", userId);
		map.put("userName", userName);
		map.put("projectName", projectName);
		map.put("projectCode", projectCode);
		map.put("projectStage", projectStage);
		
		if(expectEndTime==null || "".equals(expectEndTime)){
			str="";
		}
		map.put("expectEndTime", expectEndTime+str);
		map.put("nowDate", nowDate);
		try {
			adminService.saveTask(map);
			JSONObject  json=new JSONObject();
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
			logService.saveLog(EncodeUtil.LOG_INFO, 
					((User) session.getAttribute("JX_USERINFO")).getUserName(),
					((User) session.getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【" + userName+ "】新建任务成功！", 
					((User) session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_MODUL_YHXZ);
		} catch (Exception e) {
			try {
				logService.saveLog(EncodeUtil.LOG_ERROR,
						((User) session.getAttribute("JX_USERINFO")).getUserName(),
						((User) session.getAttribute("JX_USERINFO")).getLoginName(),
						"用户【" + userName + "】新建任务失败！",
						((User) session.getAttribute("JX_USERINFO")).getLoginIp(), 
							EncodeUtil.LOG_MODUL_YHXZ);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/***
	 * 任务列表查询
	 * @param taskInfo
	 * @param m
	 * @param request
	 * @return
	 * 员工任务查询调用
	 * 高管、主管、管理员任务查询调taskListQueryAction.getTasklist
	 */
	@RequestMapping(value = "/listTask")
	@LogAnnotation(eventCode="1023003",eventProcess="")
	public ModelAndView getTasklist(@ModelAttribute("taskInfo")TaskInfo taskInfo,Model m, HttpServletRequest request) {
		try {	
			String nodeId = request.getParameter("nodeId");
			String pqFlag = request.getParameter("pqFlag");
			String orderByFlag = StringUtil.nvlString(request.getParameter("orderByFlag"));
			if(!"".equals(orderByFlag)){
				taskInfo.setOrderByFlag(orderByFlag);
			}else {
				taskInfo.setOrderByFlag("desc");
			}
			if (StringUtils.equals(pqFlag, "true")) {
				taskInfo = (TaskInfo) request.getSession().getAttribute("taskInfo");
			} else {
				request.getSession().setAttribute("taskInfo", taskInfo);
			}
			String create_name =  request.getParameter("create_name");
			if(!StringUtil.isNUll(create_name)){
				taskInfo.setCreate_name(create_name);
			}
			String exec_name =  request.getParameter("exec_name");
			if(!StringUtil.isNUll(exec_name)){
			    exec_name = new String(exec_name.getBytes("iso-8859-1"),"gb2312");
				taskInfo.setExec_name(exec_name);
				
			}
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			String deparmentId = user.getDepartment_id();
			String teamId = user.getTeam_id();
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL") || userRole.equals("XMJL"))) {//部门经理、项目经理
				taskInfo.setDepartment(deparmentId);
			}else if (!StringUtil.isNUll(userRole) && userRole.equals("TDJL")) {//团队经理
				taskInfo.setDepartment(deparmentId);
				if(deparmentId.equals("10007")){
					if(taskInfo.getTeam()==null || taskInfo.getTeam().equals("")){
						taskInfo.setTeam(teamId);
					}
				}else{
					taskInfo.setTeam(teamId);
				}
			}else if (!StringUtil.isNUll(userRole) && userRole.equals("staff")) {//员工登录
				taskInfo.setDepartment(deparmentId);
				taskInfo.setTeam(teamId);
				taskInfo.setExecutedevtasksys(userId);
			}

			if (!StringUtil.isNUll(taskInfo.getEndTime())) {
				taskInfo.setEndTime(taskInfo.getEndTime()+" 23:59:59");
			}
			if (!StringUtil.isNUll(taskInfo.getExpectEndTime2())) {
				taskInfo.setExpectEndTime2(taskInfo.getExpectEndTime2()+" 23:59:59");
			}
			if (!StringUtil.isNUll(nodeId)) {
				taskInfo.setId(nodeId);
			}
			
			ModelAndView mv = new ModelAndView("/admin/listT");
			//查询条件带回
			mv.addObject("nodeId", nodeId);
			mv.addObject("create_name", create_name);
			if(!StringUtil.isNUll(userRole) && userRole.equals("staff")){
				exec_name = user.getUserName();
			}
			mv.addObject("exec_name", exec_name);
			mv.addObject("taskInfo", taskInfo);
			if(!StringUtil.isNUll(userRole)){
				mv.addObject("userRole", userRole);
				mv.addObject("positionId", user.getPosition_id());
			}
			mv.addObject("user", user);
			m.addAttribute(mv);
			
			PageView<Map<String,Object>> pageView  = new PageView<Map<String,Object>>();
			
//			int offset = 0;
//			Integer pageSize = (Integer) request.getSession().getAttribute("ps");
//			if (request.getParameter("pager.offset") != null) {
//				offset = Integer.parseInt(request.getParameter("pager.offset"));
//			}
//			String nowNum= StringUtil.nvlString(request.getParameter("maxResultSelect"));
//			if("".equals(nowNum)){
//				nowNum="10";
//			}
//			qry.put("begin", offset);//翻页开始记录
//			qry.put("nowNum", StringUtil.StringToInt(nowNum));//当前显示几条
//			qry.put("end", offset + pageSize);//翻页结束记录
//			qry.put("taskInfo", taskInfo);//查询条件
//			PagerModel pager = adminService.getTaskList(qry);

			//mv.addObject("pclist", pager);
			String flag1 = StringUtil.nvlString(request.getParameter("flag1"));
			if (!flag1.equals("1")) {//flag1==1默认不加载任务列表
				pageView = adminService.getTaskListpageView(taskInfo);	
			}
			//pageView = adminService.getTaskListpageView(taskInfo);	
			m.addAttribute("pageView", pageView);
			
			/***********初始化下拉菜单 start**************/
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
						
			mv.addObject("dictionaryLlist", dictionaryLlist);
			/************初始化下拉菜单 end*************/
			
			/**
			 * 查询条件选择部门，将其下的团队信息带回
			 */
			if (!StringUtil.isNUll(taskInfo.getDepartment())) {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("type", "01");
				map1.put("code", taskInfo.getDepartment());
				Dictionary dicTemp = utilsService.getDictionary(map1);
				
				List<Dictionary> childDictionaryList = null;
				if (dictionaryLlist!=null && dictionaryLlist.size()>0) {
					childDictionaryList = new ArrayList<Dictionary>();
					for (Dictionary dictionary : dictionaryLlist) {
						if(dictionary.getType_code().equals("02") && dictionary.getParent_id().equals(dicTemp.getId())){
							childDictionaryList.add(dictionary);
						}
					}
					if (childDictionaryList!=null && childDictionaryList.size()>0) {
						mv.addObject("childDictionaryList", childDictionaryList);
					}
				}
				
			}
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * 我的工作台信息查询
	 * taskstatus ==> 1:待分配，2：待接收，3待确认
	 * @param taskInfo
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myWorkbenchList")
	@LogAnnotation(eventCode="1029001",eventProcess="")
	public ModelAndView getMyWorkbenchInfoList(@ModelAttribute("taskInfo")TaskInfo taskInfo,Model m, HttpServletRequest request) {
		
		try {
			
			User user = (User) request.getSession().getAttribute("JX_USERINFO");
			String userId = user.getUserId();
			String username = user.getUserName();
			List<Role> roles = roleService.getRoleByUserId(userId);
			
			String depart_id = StringUtil.nvlString(PublicUtil.getUserOnline(request).getDepartment_id());
			if(depart_id.equals("10006")||depart_id.equals("10009")){
					m.addAttribute("preFalg","1");
				}else{
					m.addAttribute("preFalg","2");
				}
			
			String flag = request.getParameter("flag");
			//taskStatus ==> 1:待分配，2：待接收，3待确认(待确认=延期提交+按时提交+提取提交)
			String taskStatus =  StringUtil.nvlString(request.getParameter("taskStatus"));
			String pqFlag = request.getParameter("pqFlag");
			if (StringUtils.equals(pqFlag, "true") && (taskStatus==null || taskStatus.equals(""))) {
				taskStatus = taskInfo.getTaskstatus();
			}
			String userRole = null;
			TaskInfo taskInfoTemp = new TaskInfo();
			for (Role role : roles) {
//				if (role.getRoleCode().equals("staff") || role.getRoleCode().equals("director") || role.getRoleCode().equals("boss") || role.getRoleCode().equals("SUPER") || role.getRoleCode().equals("admin11")) {
				if(!"".equals(role.getRoleCode()) && role.getRoleCode()!=null){
					userRole = role.getRoleCode();
					break;
				}
			}
			if (!StringUtil.isNUll(userRole) && userRole.equals("admin11")) {
				ModelAndView mv = new ModelAndView("/welcome");
				return mv;
			}
			if (taskStatus==null || taskStatus.equals("")) {
				if (userRole!=null && userRole.equals("staff")){
					taskStatus = "2";
					taskInfoTemp.setTaskstatus("2");
					taskInfoTemp.setExecutedevtasksys(userId.toString());
				}else if(userRole!=null && (userRole.equals("director") || userRole.equals("boss") || userRole.equals("BMJL") || userRole.equals("TDJL") || userRole.equals("XMJL") || userRole.equals("PMO001") || userRole.equals("PMUPDATE"))){
					taskStatus = "1";
					taskInfoTemp.setTaskstatus("1");
					taskInfoTemp.setAllocationUser(userId.toString());
				}else if (userRole!=null && userRole.equals("SUPER")) {
					taskStatus = "1";
					taskInfoTemp.setTaskstatus("1");
				}
			}else{
				taskInfoTemp.setTaskstatus(taskStatus);
				if (userRole!=null && userRole.equals("SUPER")) {
					
				}else {
					if (taskStatus.equals("2")) {
						taskInfoTemp.setExecutedevtasksys(userId.toString());
					}else if (taskStatus.equals("1") || taskStatus.equals("3")) {
						taskInfoTemp.setAllocationUser(userId.toString());
					}
				}
				if(taskStatus.equals("0") && StringUtil.nvlString(flag).equals("4")){
					taskInfoTemp.setIs_pass("0");//状态为1，flag为4==>待通过任务列表
					taskInfoTemp.setDepartment(user.getDepartment_id());
				}
			}
			
			ModelAndView mv = new ModelAndView("/admin/myWorkbench");
			mv.addObject("taskInfo", taskInfo);
			m.addAttribute(mv);

			Map<String, Object> qry = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			
			//由消息提醒跳转工作台时，根据id查看消息对应任务
			String id =  StringUtil.nvlString(request.getParameter("taskInfoId"));
			if (!StringUtil.isNUll(id)) {
				qry.put("id", id);
				map.put("id", id);
			}
			
			int offset = 0;
			Integer pageSize = (Integer) request.getSession().getAttribute("ps");
			if (request.getParameter("pager.offset") != null) {
				offset = Integer.parseInt(request.getParameter("pager.offset"));
			}
			if(offset<0){
				offset=0;
			}
			pageSize = 5;
			request.getSession().setAttribute("ps", 5);
			qry.put("begin", offset);//翻页开始记录
			qry.put("nowNum", 5);
			qry.put("end", offset + pageSize);//翻页结束记录
			qry.put("userId", userId);//查询条件
			qry.put("userRole", userRole);//用户角色
			qry.put("taskInfo", taskInfoTemp);//用户角色
			
			map.put("allocationUser", userId);						//任务创建者id
			map.put("executedevtasksys", userId);				//任务执行者id
			int dfp = adminService.getDfpTaskSize(map);      //工作台   待分配   任务数
			int djs = adminService.getDjsTaskSize(map);      //工作台   待接收   任务数
			int dqr = adminService.getDqrTaskSize(map);      //工作台   待确认   任务数
			map.put("department", user.getDepartment_id());
			int dtg = adminService.getDtgTaskSize(map);      //工作台   待通过   任务数
			mv.addObject("dfp", dfp);
			mv.addObject("djs", djs);
			mv.addObject("dqr", dqr);
			mv.addObject("dtg", dtg);
			
			PagerModel pager = adminService.getMyWorkbenchList(qry);
			@SuppressWarnings("unchecked")
			List<TaskInfo> datas = pager.getDatas();
			if(datas!=null && datas.size()>0){
				for (TaskInfo taskInfo2 : datas) {
					String taskContent = StringUtil.nvlString(taskInfo2.getTaskContent());
					if (taskContent.contains("\n")) {
						String str = taskInfo2.getTaskContent().replaceAll("\n", "");
						taskInfo2.setTaskContent(str);
					}
					if(StringUtil.nvlString(flag).equals("4") && !StringUtil.nvlString(taskInfo2.getDeliver_person()).equals("")){
						User u = userService.getUserById(taskInfo2.getDeliver_person().trim());
						taskInfo2.setDeliverPersonZh(u.getUserName());
					}
				}
			}
			Map<String, String> map11 = new HashMap<String, String>();
			map11.put("type", "12");
			List<Dictionary> peojectState = utilsService.getDictionaryList(map11);
			mv.addObject("peojectState", peojectState);
			mv.addObject("pclist", pager);
			mv.addObject("taskInfo", taskInfo);
			mv.addObject("userRole", userRole);
			mv.addObject("username", username);
			
			if (!StringUtil.isNUll(flag)) {
				mv.addObject("flag", flag);
			}else{
				mv.addObject("flag", taskStatus);//分页跳转时flag为空
			}
			
			if (!StringUtil.isNUll(taskStatus) && taskStatus.equals("1")) {
				//加载下拉菜单
				@SuppressWarnings("unchecked")
				List<User> userList = userService.getPageUser(null).getDatas();
				mv.addObject("userList", userList);
			}
			
//			TaskInfo taskTemp = null;
			//最近一月完成任务数量
			int finishCount = 0;
			if(userRole!=null && (userRole.equals("director") || userRole.equals("staff") || userRole.equals("BMJL") || userRole.equals("TDJL") || userRole.equals("XMJL") || userRole.equals("PMO001") || userRole.equals("PMUPDATE"))){
				TaskInfo taskTemp = new TaskInfo();
				taskTemp.setExecutedevtasksys(userId);
				Date nowDate = new Date();
//				Long longTime = nowDate.getTime()-30*24*60*60*1000L;
//				Date oldDate = new Date(longTime);
				
				String startTime = DateUtil.DateToString(nowDate, "yyyy-MM")+"-01";
				String endTime = DateUtil.DateToString(nowDate, "yyyy-MM-dd HH:mm:ss");
				taskTemp.setStartTime(startTime+" 00:00:00");
				taskTemp.setEndTime(endTime);
				taskTemp.setTaskstatus("8");
				
				finishCount = adminService.getFinishTaskCount(taskTemp);
				
			}else if (userRole!=null && userRole.equals("SUPER")) {
				TaskInfo taskTemp = new TaskInfo();
				
				Date nowDate = new Date();
//				Long longTime = nowDate.getTime()-30*24*60*60*1000L;
//				Date oldDate = new Date(longTime);
				
				String startTime = DateUtil.DateToString(nowDate, "yyyy-MM")+"-01";
				String endTime = DateUtil.DateToString(nowDate, "yyyy-MM-dd HH:mm:ss");
				taskTemp.setStartTime(startTime+" 00:00:00");
				taskTemp.setEndTime(endTime);
				
				taskTemp.setTaskstatus("8");
				
				finishCount = adminService.getFinishTaskCount(taskTemp);
			}
			mv.addObject("finishCount", finishCount);
			
			int count1 = 0;//taskStatus==>2待接收
			int count2 = 0;//taskStatus==>员工：3，4进行中，高管：1-7进行中
			int count3 = 0;//taskStatus==>5延期提交
			int count4 = 0;//taskStatus==>6按时提交
			int count5 = 0;//taskStatus==>7提前提交
			int count6 = 0;//taskStatus==>5，6，7待确认
			if(userRole!=null && userRole.equals("staff")){//员工
				
				TaskInfo taskTemp = new TaskInfo();
				taskTemp.setExecutedevtasksys(userId);
				//taskInfoList1计算待接收、进行中数量
				List<TaskInfo> taskInfoList1 = adminService.findTaskListByProperties(taskTemp);
				for (TaskInfo taskInfo2 : taskInfoList1) {
					if(!StringUtil.isNUll(taskInfo2.getTaskstatus())){
						if (taskInfo2.getTaskstatus().equals("2")) {
							count1++;
							continue;
						}
						//进行中=进行中+延期未提交
						if (taskInfo2.getTaskstatus().equals("3") || taskInfo2.getTaskstatus().equals("4")) {
							count2++;
							continue;
						}
					}
				}
				//计算最近一个月 饼图
//				Date nowDate = new Date();
//				Long longTime = nowDate.getTime()-30*24*60*60*1000L;
//				Date oldDate = new Date(longTime);
//				String startTime = DateUtil.DateToString(oldDate, "yyyy-MM-dd");
//				String endTime = DateUtil.DateToString(nowDate, "yyyy-MM-dd");
//				taskTemp.setStartTime(startTime+" 00:00:00");
//				taskTemp.setEndTime(endTime+" 23:59:59");
				Date nowDate = new Date();
				String startTime = DateUtil.DateToString(nowDate, "yyyy-MM")+"-01";
				String endTime = DateUtil.DateToString(nowDate, "yyyy-MM-dd HH:mm:ss");
				taskTemp.setStartTime(startTime+" 00:00:00");
				taskTemp.setEndTime(endTime);
				
				List<TaskInfo> taskInfoList2 = adminService.findTaskListByProperties(taskTemp);
				for (TaskInfo taskInfo2 : taskInfoList2) {
					if(StringUtil.nvlString(taskInfo2.getTaskstatus()).equals("8") && !StringUtil.isNUll(taskInfo2.getTjtype())){
						if (taskInfo2.getTjtype().equals("5")) {
							count3++;
							continue;
						}
						if (taskInfo2.getTjtype().equals("6")) {
							count4++;
							continue;
						}
						if (taskInfo2.getTjtype().equals("7")) {
							count5++;
							continue;
						}
					}
				}
			}
			if(userRole!=null && (userRole.equals("director") || userRole.equals("BMJL") || userRole.equals("TDJL") || userRole.equals("XMJL") || userRole.equals("PMO001") || userRole.equals("PMUPDATE"))){//主管、部门经理、团队经理、项目经理、PMO
				
				TaskInfo TaskInfo2 = new TaskInfo();
				TaskInfo2.setAllocationUser(userId);
				//taskInfoList计算 所有  待确认数量
				List<TaskInfo> taskInfoList = adminService.findTaskListByProperties(TaskInfo2);
				for (TaskInfo taskInfo2 : taskInfoList) {
					if(!StringUtil.isNUll(taskInfo2.getTaskstatus())){
						if (taskInfo2.getTaskstatus().equals("5") || taskInfo2.getTaskstatus().equals("6") || taskInfo2.getTaskstatus().equals("7")) {
							count6++;
							continue;
						}
					}
				}
				
				TaskInfo taskTemp = new TaskInfo();
				taskTemp.setExecutedevtasksys(userId);
				//taskInfoList1计算 所有 待接收
				List<TaskInfo> taskInfoList1 = adminService.findTaskListByProperties(taskTemp);
				for (TaskInfo taskInfo2 : taskInfoList1) {
					if(!StringUtil.isNUll(taskInfo2.getTaskstatus())){
						if (taskInfo2.getTaskstatus().equals("2")) {
							count1++;
							continue;
						}
					}
				}
				//taskInfoList2计算 近一个月  饼图
				Date nowDate = new Date();
				String startTime = DateUtil.DateToString(nowDate, "yyyy-MM")+"-01";
				String endTime = DateUtil.DateToString(nowDate, "yyyy-MM-dd HH:mm:ss");
				taskTemp.setStartTime(startTime+" 00:00:00");
				taskTemp.setEndTime(endTime);
				
				List<TaskInfo> taskInfoList2 = adminService.findTaskListByProperties(taskTemp);
				for (TaskInfo taskInfo2 : taskInfoList2) {
					if(StringUtil.nvlString(taskInfo2.getTaskstatus()).equals("8") && !StringUtil.isNUll(taskInfo2.getTjtype())){
						if (taskInfo2.getTjtype().equals("5")) {
							count3++;
							continue;
						}
						if (taskInfo2.getTjtype().equals("6")) {
							count4++;
							continue;
						}
						if (taskInfo2.getTjtype().equals("7")) {
							count5++;
							continue;
						}
					}
				}
			}
			
			if(userRole!=null && userRole.equals("boss")){//高管
				
				TaskInfo taskTemp = new TaskInfo();
				taskTemp.setAllocationUser(userId);
				List<TaskInfo> taskInfoList = adminService.findTaskListByProperties(taskTemp);
				for (TaskInfo taskInfo2 : taskInfoList) {
					if(!StringUtil.isNUll(taskInfo2.getTaskstatus())){
//						if (taskInfo2.getTaskstatus().equals("3") || taskInfo2.getTaskstatus().equals("4")) {
//							count2++;
//						}
						int status = Integer.parseInt(taskInfo2.getTaskstatus());
						if(status>0 && status<8){//进行中=待分配+已分配+进行中+延期未提交+延期提交+按时提交+提取提交
							count2++;
						}
						if (taskInfo2.getTaskstatus().equals("5")) {
							//count3++;
							count6++;
							continue;
						}
						if (taskInfo2.getTaskstatus().equals("6")) {
							//count4++;
							count6++;
							continue;
						}
						if (taskInfo2.getTaskstatus().equals("7")) {
							//count5++;
							count6++;
							continue;
						}
					}
				}
			}
			
			if(userRole!=null && userRole.equals("SUPER")){//超级管理员
				
				TaskInfo taskTemp = new TaskInfo();
//				taskTemp.setExecutedevtasksys(userId);
				//计算待接收、进行中、待确认数量
				List<TaskInfo> taskInfoList1 = adminService.findTaskListByProperties(new TaskInfo());
				for (TaskInfo taskInfo2 : taskInfoList1) {
					if(!StringUtil.isNUll(taskInfo2.getTaskstatus())){
						if (taskInfo2.getTaskstatus().equals("2")) {
							count1++;
							continue;
						}
						if (taskInfo2.getTaskstatus().equals("3") || taskInfo2.getTaskstatus().equals("4")) {
							count2++;
							continue;
						}
						if (taskInfo2.getTaskstatus().equals("5") || taskInfo2.getTaskstatus().equals("6") || taskInfo2.getTaskstatus().equals("7")) {
							count6++;
							continue;
						}
					}
				}
				//饼图
				Date nowDate = new Date();
				String startTime = DateUtil.DateToString(nowDate, "yyyy-MM")+"-01";
				String endTime = DateUtil.DateToString(nowDate, "yyyy-MM-dd HH:mm:ss");
				taskTemp.setStartTime(startTime+" 00:00:00");
				taskTemp.setEndTime(endTime);
				
				List<TaskInfo> taskInfoList2 = adminService.findTaskListByProperties(taskTemp);
				for (TaskInfo taskInfo2 : taskInfoList2) {
					if(StringUtil.nvlString(taskInfo2.getTaskstatus()).equals("8") && !StringUtil.isNUll(taskInfo2.getTjtype())){
						if (taskInfo2.getTjtype().equals("5")) {
							count3++;
							continue;
						}
						if (taskInfo2.getTjtype().equals("6")) {
							count4++;
							continue;
						}
						if (taskInfo2.getTjtype().equals("7")) {
							count5++;
							continue;
						}
					}
				}
			}
			
			mv.addObject("count1", count1);
			mv.addObject("count2", count2);
			mv.addObject("count3", count3);
			mv.addObject("count4", count4);
			mv.addObject("count5", count5);
			mv.addObject("count6", count6);
			
			int creatCount = 0;
			if(userRole!=null && (userRole.equals("director") || userRole.equals("boss") || userRole.equals("BMJL") || userRole.equals("TDJL") || userRole.equals("XMJL") || userRole.equals("PMO001") || userRole.equals("PMUPDATE"))){
				//创建任务数
				TaskInfo taskTemp = new TaskInfo();
				taskTemp.setAllocationUser(userId);
				
				creatCount = adminService.getFinishTaskCount(taskTemp);
				
			}
			if(userRole!=null && userRole.equals("SUPER")){//超级管理员
				creatCount = adminService.getFinishTaskCount(new TaskInfo());
			}
			mv.addObject("creatCount", creatCount);
			mv.addObject("userRole", userRole);
			
			//得分数据计算
			String statisticsTime = getDate2(0);
			Map<String, Double> scoreMap = getScores(userId);
			double taskScore = scoreMap.get("taskScore");//任务打分
			double taskStatusScore = scoreMap.get("taskStatusScore");//任务状态分
			//关联饱和度
			WorkTime wt = new WorkTime();
			wt.setType("1");
			wt.setStatisticsTime(statisticsTime);
			wt.setEmpCode(user.getLoginName());
			List<WorkTime> workTimeList = workTimeService.exportWorkTimeList(wt);
			if (UtilValidate.isNotEmpty(workTimeList)) {
				String saturation = workTimeList.get(0).getSaturation();
				if(saturation!=null && !saturation.equals("N/A")){
					double saturationScore = MathUtil.round(MathUtil.mul(35, Double.parseDouble(saturation)), 1);//饱和度得分
					if(saturationScore>40){//饱和度得分范围25~40
						saturationScore=40;
					}else if(saturationScore<25){
						saturationScore=25;
					}
					double totalScore = taskScore+taskStatusScore+saturationScore;
					if(totalScore>110){//实时分数（总得分）范围75~110
						totalScore=110;
					}else if(totalScore<75){
						totalScore=75;
					}
					mv.addObject("saturationScore", MathUtil.round(saturationScore, 1));
					mv.addObject("totleScore", MathUtil.round(totalScore, 1));
				}else {
					mv.addObject("totleScore", "未获取到考勤信息");
					mv.addObject("saturationScore", "未获取到考勤信息");
				}
			}else {
				mv.addObject("totleScore", "未获取到考勤信息");
				mv.addObject("saturationScore", "未获取到考勤信息");
			}
			mv.addObject("taskScore", MathUtil.round(taskScore, 1));
			mv.addObject("taskStatusScore", MathUtil.round(taskStatusScore, 1));
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 得分计算ajax，工作台选择日期查询得分
	 * 更新时间：2016-10-13
	 * @param  :
	 * @return :void
	 * @author :ruiy
	 * @date   :2016-5-24下午1:36:32
	 *
	 */
		@RequestMapping(value = "/countScore")
		@LogAnnotation(eventCode="1029010",eventProcess="")
		public void countScore(Model m, HttpServletRequest request,HttpServletResponse response) {
			try{
					User user = (User) request.getSession().getAttribute("JX_USERINFO");
					String userId = user.getUserId();
					String imTime =  StringUtil.nvlString(request.getParameter("imTime")).substring(0, 7);
					
					Date nowDate = new Date();
					String imTime2 = DateUtil.DateToString(nowDate, "yyyy-MM");
					JSONObject json=new JSONObject();
					
					//得分数据计算
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("subDate", imTime);
					maps.put("userId", userId);
					if(imTime.equals(imTime2)){//是否当前月份
						Map<String, Double> scoreMap = getScores(userId);
						double taskScore = scoreMap.get("taskScore");//任务打分
						double taskStatusScore = scoreMap.get("taskStatusScore");//任务状态分
						
						WorkTime wt = new WorkTime();
						wt.setType("1");
						wt.setStatisticsTime(imTime);
						wt.setEmpCode(user.getLoginName());
						List<WorkTime> workTimeList = workTimeService.exportWorkTimeList(wt);
						if (UtilValidate.isNotEmpty(workTimeList)) {
							String saturation = workTimeList.get(0).getSaturation();
							if(saturation!=null && !saturation.equals("N/A")){
								double saturationScore = MathUtil.round(MathUtil.mul(35, Double.parseDouble(saturation)), 1);//饱和度得分
								if(saturationScore>40){//饱和度得分范围25~40
									saturationScore=40;
								}else if(saturationScore<25){
									saturationScore=25;
								}
								double totalScore = taskScore+taskStatusScore+saturationScore;
								if(totalScore>110){//实时分数范围75~110
									totalScore=110;
								}else if(totalScore<75){
									totalScore=75;
								}
								json.put("saturationScore", MathUtil.round(saturationScore, 1));
								json.put("totleScore", MathUtil.round(totalScore, 1));
							}else {
								json.put("totleScore", "未获取到考勤信息");
								json.put("saturationScore", "未获取到考勤信息");
							}
						}else {
							json.put("totleScore", "未获取到考勤信息");
							json.put("saturationScore", "未获取到考勤信息");
						}
						json.put("taskScore", MathUtil.round(taskScore, 1));
						json.put("taskStatusScore", MathUtil.round(taskStatusScore, 1));
						
					}else{
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("gUserId", userId);
						map.put("gMonth", imTime);
						Grades grades = adminService.getScoreByPro(map);
						if(grades==null || grades.getgState().equals("2") || grades.getgState().equals("3")){//有未完成任务或无任务
							json.put("totleScore", "75");
							json.put("taskScore", "0");
							json.put("taskStatusScore", "0");
							json.put("saturationScore", grades==null?"未获取到考勤信息":"0");
						}else{
							if(grades.getgScores()!=null && grades.getgScores().equals("N/A")){
								json.put("totleScore", "未获取到考勤信息");
								json.put("taskScore", grades.getTaskScore());
								json.put("taskStatusScore", grades.getTaskStatusScore());
								json.put("saturationScore", "未获取到考勤信息");
							}else {
								json.put("totleScore", grades.getgScores());
								json.put("taskScore", grades.getTaskScore());
								json.put("taskStatusScore", grades.getTaskStatusScore());
								json.put("saturationScore", grades.getSaturationScore());
							}
						}
						
					}
					
					PrintWriter out = null;
					response.setContentType("text/plain");
					out = response.getWriter();
					out.write(json.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	/**
	 * 更新我的工作台信息
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateMyWorkbench", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1102001",eventProcess="")
	public String updateMyWorkbenchInfo(Model m, HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
		try{
			String id =  StringUtil.nvlString(request.getParameter("id"));
			String status =  StringUtil.nvlString(request.getParameter("status"));
			String taskContent =  StringUtil.nvlString(request.getParameter("taskContent"));
			String expectEndTime =  StringUtil.nvlString(request.getParameter("expectEndTime"));
			String executedevtasksys =  StringUtil.nvlString(request.getParameter("executedevtasksys"));
			String score =  StringUtil.nvlString(request.getParameter("score"));
			String flagA =  StringUtil.nvlString(request.getParameter("flagA"));
			String taskWorkTime =  StringUtil.nvlString(request.getParameter("taskWorkTime"));
			String projectName =  StringUtil.nvlString(request.getParameter("projectName"));
			String projectCode =  StringUtil.nvlString(request.getParameter("projectCode"));
			String projectStage =  StringUtil.nvlString(request.getParameter("projectStage"));
			String refuseCause =  StringUtil.nvlString(request.getParameter("refuseCause"));

			TaskInfo taskInfo = new TaskInfo();
			if (!StringUtil.isNUll(id)) {
				taskInfo.setId(id);
			}
			TaskInfo task = adminService.getTaskInfoById(id);
			String execName = task.getExec_name();
			if (!StringUtil.isNUll(status)) {
				taskInfo.setTaskstatus(status);
				if(status.equals("1")){
					taskInfo.setExecutedevtasksys("");//拒绝接收任务，负责人为空
					taskInfo.setExec_name("");//拒绝接收任务，负责人名称为空
				}
			}
			if (!StringUtil.isNUll(taskContent)) {
				taskInfo.setTaskContent(taskContent);
			}
			if (!StringUtil.isNUll(expectEndTime)) {
				if(expectEndTime.length()==10){
					expectEndTime = expectEndTime + getDate(0).substring(10);
				}
				taskInfo.setExpectEndTime(expectEndTime);
			}
			
			
			if (!StringUtil.isNUll(taskWorkTime)) {
				taskInfo.setTaskWorkTime(taskWorkTime);
			}
			if (!StringUtil.isNUll(projectName)) {
				taskInfo.setProjectName(projectName);
			}
			if (!StringUtil.isNUll(projectCode)) {
				taskInfo.setProjectCode(projectCode);
			}
			if (!StringUtil.isNUll(projectStage)) {
				taskInfo.setProjectStage(projectStage);
			}
			if (!StringUtil.isNUll(refuseCause)) {
                taskInfo.setRefuseCause(refuseCause);
            }
			if (!StringUtil.isNUll(executedevtasksys)) {
				taskInfo.setExecutedevtasksys(executedevtasksys);
				User user = userService.getUserById(executedevtasksys);
				if (user!=null) {
					taskInfo.setExec_name(user.getUserName());
					taskInfo.setDepartment(user.getDepartment_id());
					taskInfo.setTeam(user.getTeam_id());
					taskInfo.setJobId(user.getJobId());
				}
			}
			if (!StringUtil.isNUll(score)) {
				
				taskInfo.setScore(score);
				String conQuarter = "";
				Calendar cal = Calendar.getInstance(); 
				int year = cal.get(Calendar.YEAR);//获取年份 
				String conYear = Integer.toString(year);
				int month=cal.get(Calendar.MONTH) + 1;//获取月份 
				if (month>=1 && month<=3) { 
					conQuarter = "1"; 
					}else if (month>=4 && month<=6) { 
					conQuarter = "2"; 
					}else if (month>=7 && month<=9) { 
					conQuarter = "3"; 
					}else if (month>=10 && month<=12) { 
					conQuarter = "4"; 
					} 
				taskInfo.setConQuarter(conQuarter);
				taskInfo.setConYear(conYear);
			}
			
			int currentPage = 1;
			String currentPageNumber = request.getParameter("currentPageNumber");
			if (!StringUtil.isNUll(currentPageNumber)) {
				currentPage = Integer.parseInt(currentPageNumber);
			}
			int offset = (currentPage-1)*5;
			if (offset<0) {
				offset = 0;
			}
			ra.addAttribute("pager.offset", offset);
			//1拒绝接收，2提交拒绝,3分配，4接收，5确认
			String logSuccessInfo = null;
			String username = ((User) request.getSession().getAttribute("JX_USERINFO")).getUserName();
			int res = Integer.parseInt(StringUtil.nvlString(flagA));
			switch (res) {
				case 1:
					ra.addAttribute("flag", "2");
					ra.addAttribute("taskStatus", "2");
					logSuccessInfo = "用户【"+username+"】拒绝了【"+task.getCreate_name()+"】分配的任务（任务："+task.getTaskContent()+"）！Success！";
					break;
				case 2:
					ra.addAttribute("flag", "3");
					ra.addAttribute("taskStatus", "3");
					logSuccessInfo = "用户【"+username+"】拒绝了【"+task.getExec_name()+"】提交的任务（任务："+task.getTaskContent()+"）！Success！";
					break;
				case 3:
					taskInfo.setYfpDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));//任务分配时间
					ra.addAttribute("flag", "1");
					ra.addAttribute("taskStatus", "1");
					logSuccessInfo = "用户【"+username+"】给【"+taskInfo.getExec_name()+"】分配了任务（任务："+task.getTaskContent()+"）！Success！";
					break;
				case 4:
					taskInfo.setJxzDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));//任务接收时间
					ra.addAttribute("flag", "2");
					ra.addAttribute("taskStatus", "2");
					logSuccessInfo = "用户【"+username+"】接收了【"+task.getCreate_name()+"】分配的任务（任务："+task.getTaskContent()+"）！Success！";
					break;
				case 5:
					taskInfo.setEndDate(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));//任务完成确认时间
					TaskInfo ti = adminService.getTaskInfoById(id);
					taskInfo.setTjtype(ti.getTaskstatus());
					ra.addAttribute("flag", "3");
					ra.addAttribute("taskStatus", "3");
					logSuccessInfo = "用户【"+username+"】确认了【"+task.getExec_name()+"】提交的任务（任务："+task.getTaskContent()+"）！Success！";
					break;
					
				default:
					break;
			}
			adminService.updateTaskInfo(taskInfo, res, PublicUtil.getUserOnline(request).getUserId(), execName);
			if(res==3){
				User userT = userService.getUserById(taskInfo.getExecutedevtasksys());
				//分配后发送邮件提醒
				Map<String, String> map = new HashMap<String, String>();
				map.put("to_email", userT.getEmail());
				map.put("create_name", task.getCreate_name());
				if(task.getExpectEndTime()==null||"".equals(task.getExpectEndTime())){
					map.put("expectEndTime", "");
				}else{
					if(expectEndTime!=null && !expectEndTime.equals("")){
						map.put("expectEndTime", expectEndTime.substring(0, 11));
					}else{
						map.put("expectEndTime", task.getExpectEndTime().substring(0, 11));
					}
				}
				map.put("taskContent", task.getTaskContent());
				SendEmailUtil.sendTaskEmail(map);
			}
			
			logService.saveLog(EncodeUtil.LOG_INFO, 
					((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
					((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(), 
					logSuccessInfo, 
					((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_TASK_UPDATE);
			addMessage(ra, "操作成功！");
			session.setAttribute("resultFlag", "1");
		}catch(Exception e){
			e.printStackTrace();
			try {
				logService.saveLog(EncodeUtil.LOG_ERROR,
						((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(),
						"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】执行操作失败！操作模块-->我的工作台",
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(), 
							EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作失败！");
				session.setAttribute("resultFlag", "0");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return "redirect:/admin/myWorkbenchList.do";
	}
	
	/**
	 * 审核我的工作台待通过任务
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkTask", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1112001",eventProcess="")
	public String checkTask(Model m, HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
		try{
			
			String id =  StringUtil.nvlString(request.getParameter("id"));
//			String flagA =  StringUtil.nvlString(request.getParameter("flagA"));
			
			User user = (User) request.getSession().getAttribute("JX_USERINFO");
			
			TaskInfo taskInfo = new TaskInfo();
			if (!StringUtil.isNUll(id)) {
				taskInfo.setId(id);
			}
			taskInfo.setTaskstatus("2");
			taskInfo.setIs_pass("1");
			taskInfo.setAllocationUser(user.getUserId());
			taskInfo.setCreate_name(user.getUserName());
			taskInfo.setYfpDate(getDate(0));

			adminService.checkTaskInfo(taskInfo);
			//通过后发送邮件提醒
			TaskInfo task = adminService.getTaskInfoById(id);
			User userT = userService.getUserById(task.getExecutedevtasksys());
			Map<String, String> map = new HashMap<String, String>();
			map.put("to_email", userT.getEmail());
			map.put("create_name", user.getUserName());
			if(task.getExpectEndTime()==null||"".equals(task.getExpectEndTime())){
				map.put("expectEndTime", "");
			}else{
				map.put("expectEndTime", task.getExpectEndTime().substring(0, 11));
			}
			map.put("taskContent", task.getTaskContent());
			SendEmailUtil.sendTaskEmail(map);
			
			//通过后发送消息提醒
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("receiverUserId", task.getExecutedevtasksys());
			map2.put("title", user.getUserName()+"给您分配了新任务！ ");
			map2.put("messageStatus", "1");
		
			map2.put("senderUserId", user.getUserId());
			map2.put("sendTime", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			map2.put("isdel", "0");
			map2.put("isread", "0");
			map2.put("taskInfoId", task.getId());
			utilsService.saveMessage(map2);

			int currentPage = 1;
			String currentPageNumber = request.getParameter("currentPageNumber");
			if (!StringUtil.isNUll(currentPageNumber)) {
				currentPage = Integer.parseInt(currentPageNumber);
			}
			int offset = (currentPage-1)*5;
			if (offset<0) {
				offset = 0;
			}
			ra.addAttribute("pager.offset", offset);
			ra.addAttribute("flag", "4");
			ra.addAttribute("taskStatus", "0");
			
			logService.saveLog(EncodeUtil.LOG_INFO, 
					((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
					((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】审核任务通过成功！", 
					((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_TASK_UPDATE);
			addMessage(ra, "操作成功！");
			session.setAttribute("resultFlag", "1");
		}catch(Exception e){
			e.printStackTrace();
			try {
				logService.saveLog(EncodeUtil.LOG_ERROR,
						((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(),
						"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】执行操作失败！操作模块-->我的工作台",
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(), 
							EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作失败！");
				session.setAttribute("resultFlag", "0");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return "redirect:/admin/myWorkbenchList.do";
	}
	
	/**
	 * 删除我的工作台信息
	 * @param ra
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delTask", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1102001",eventProcess="")
	public String deleteTask(HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
		try{
			String id =  request.getParameter("id");
			String flag =  StringUtil.nvlString(request.getParameter("flag"));
			
			int currentPage = 1;
			String currentPageNumber = request.getParameter("currentPageNumber");
			if (!StringUtil.isNUll(currentPageNumber)) {
				currentPage = Integer.parseInt(currentPageNumber);
			}
			long allTotal = 0L;
			String allTotalStr = request.getParameter("allTotal");
			if (!StringUtil.isNUll(allTotalStr)) {
				allTotal = Long.parseLong(allTotalStr);
				if (allTotal%5==1) {
					currentPage = currentPage - 1;
				}
			}
			int offset = (currentPage-1)*5;
			if (offset<0) {
				offset = 0;
			}
			ra.addAttribute("pager.offset", offset);
			
			if(!flag.equals("")){
				ra.addAttribute("flag", flag);
			}else{
				ra.addAttribute("flag", "1");
			}
			if("4".equals(flag)){
				ra.addAttribute("taskStatus", "0");
			}else {
				ra.addAttribute("taskStatus", "1");
			}
			
			int result = adminService.delTask(id);
			if (result==1) {
				logService.saveLog(EncodeUtil.LOG_INFO, 
						((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】执行我的工作台-任务删除-删除成功！", 
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_DELETE);
				addMessage(ra, "删除成功！");
				session.setAttribute("resultFlag", "1");
			}else {
				addMessage(ra, "删除失败！");
				session.setAttribute("resultFlag", "0");
			}
		}catch(Exception e){
			e.printStackTrace();
			try {
				logService.saveLog(EncodeUtil.LOG_ERROR,
						((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(),
						"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】执行我的工作台-任务删除-删除失败！",
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(), 
							EncodeUtil.LOG_TASK_DELETE);
				addMessage(ra, "我的工作台删除失败！");
				session.setAttribute("resultFlag", "0");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return "redirect:/admin/myWorkbenchList.do";
	}
	
	@RequestMapping(value="/userList")
	@LogAnnotation(eventCode="1112011",eventProcess="")
	public ModelAndView getUsers(HttpServletRequest request){
		try {
			ModelAndView mv = new ModelAndView("/admin/userList");
			
			String flag = StringUtil.nvlString(request.getParameter("flag"));
			String positionId = StringUtil.nvlString(request.getParameter("positionId"));
			
			String userName = request.getParameter("username");
			User userTemp = new User();
			if (!StringUtil.isNUll(userName)) {
				userTemp.setUserName(userName);
				mv.addObject("username", userName);
			}
			//判断请求选择负责人还是转交负责人，页面显示不同
			String isFenpei = StringUtil.nvlString(request.getParameter("isFenpei"));
			if(isFenpei==null || isFenpei.equals("")){
				isFenpei="1";
			}
			mv.addObject("isFenpei", isFenpei);

			User userT = PublicUtil.getUserOnline(request);
			String userId = userT.getUserId();
			
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
//				if (role.getRoleCode().equals("director") || role.getRoleCode().equals("boss") || role.getRoleCode().equals("SUPER") || role.getRoleCode().equals("admin11")) {
				if(!"".equals(role.getRoleCode()) && role.getRoleCode() != null){
					userRole = role.getRoleCode();
					break;
				}
			}
			if(userRole.equals("SUPER") || userId.equals("888999")){
				return mv;
			}else if (userRole.equals("BMJL")) {//部门经理
				String department_id = userT.getDepartment_id();
				userTemp.setDepartment_id(department_id);
			}else if (userRole.equals("TDJL")) {//团队经理
				String department_id = userT.getDepartment_id();
				userTemp.setDepartment_id(department_id);
				if(!department_id.equals("10007")){//需求分析与产品部可跨团队分配
					userTemp.setTeam_id(userT.getTeam_id());
				}
			}else if (userRole.equals("XMJL")) {//项目经理
				String department_id = userT.getDepartment_id();
				userTemp.setDepartment_id(department_id);
//				if (!StringUtil.isNUll(userT.getTeam_id())) {
//					userTemp.setTeam_id(userT.getTeam_id());
//				}
			}
			if (!"".equals(positionId)) {
				userTemp.setPosition_id(positionId);
			}
			List<User> userList = userService.getUserList(userTemp);
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			if (userList!=null && userList.size()>0) {
				for (User user : userList) {
					for (Dictionary dic : dictionaryLlist) {
//						System.out.println("department_id="+user.getDepartment_id());
						if (!StringUtil.isNUll(user.getDepartment_id()) && dic.getType_code().equals("01") && user.getDepartment_id().equals(dic.getCode())) {
							user.setDepartment_Name(dic.getName());
							break;
						}
					}
					for (Dictionary dic : dictionaryLlist) {
						if (!StringUtil.isNUll(user.getTeam_id()) && dic.getType_code().equals("02") && user.getTeam_id().equals(dic.getCode())) {
							user.setTeam_Name(dic.getName());
							break;
						}
					}
				}
			}
			
			/***********初始化下拉菜单 start**************/
//			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
						
			mv.addObject("dictionaryLlist", dictionaryLlist);
			
			
			/**
			 * 查询条件选择部门，将其下的团队信息带回
			 */
			if (!StringUtil.isNUll(userTemp.getDepartment_id())) {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("type", "01");
				map1.put("code", userTemp.getDepartment_id());
				Dictionary dicTemp = utilsService.getDictionary(map1);
				
				List<Dictionary> childDictionaryList = null;
				if (dictionaryLlist!=null && dictionaryLlist.size()>0) {
					childDictionaryList = new ArrayList<Dictionary>();
					for (Dictionary dictionary : dictionaryLlist) {
						if(dictionary.getType_code().equals("02") && dictionary.getParent_id().equals(dicTemp.getId())){
							childDictionaryList.add(dictionary);
						}
					}
					if (childDictionaryList!=null && childDictionaryList.size()>0) {
						mv.addObject("childDictionaryList", childDictionaryList);
					}
				}
				mv.addObject("department", userT.getDepartment_id());
				mv.addObject("team", userT.getTeam_id());
				mv.addObject("positionId", positionId);
			}
			
			/************初始化下拉菜单 end*************/
			
			mv.addObject("flag", flag);
			mv.addObject("userList", userList);
			mv.addObject("userId", userId);
			mv.addObject("userRole", userRole);
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/ajaxUserList", method=RequestMethod.POST)
	@LogAnnotation(eventCode="0314011",eventProcess="")
	public void getUsers(HttpServletRequest request, Model model, HttpServletResponse response){
		try {
			String flag = StringUtil.nvlString(request.getParameter("flag"));
			String userName = StringUtil.nvlString(request.getParameter("username"));
			String department = StringUtil.nvlString(request.getParameter("department"));
			String team = StringUtil.nvlString(request.getParameter("team"));
			String positionId = StringUtil.nvlString(request.getParameter("positionId"));
			User userTemp = new User();
			if (!StringUtil.isNUll(userName)) {
				userTemp.setUserName(userName);
				model.addAttribute("username", userName);
			}

			User userT = PublicUtil.getUserOnline(request);
			String userId = userT.getUserId();
			
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
//				if (role.getRoleCode().equals("director") || role.getRoleCode().equals("boss") || role.getRoleCode().equals("SUPER") || role.getRoleCode().equals("admin11")) {
				if(!"".equals(role.getRoleCode()) && role.getRoleCode() != null){
					userRole = role.getRoleCode();
					break;
				}
			}
			if(userRole.equals("SUPER") || userId.equals("888999")){
				return ;
			}
			if (!"".equals(department)) {
				userTemp.setDepartment_id(department);
			}
			if (!"".equals(team)) {
				userTemp.setTeam_id(team);
			}
			if (!"".equals(positionId)) {
				userTemp.setPosition_id(positionId);
			}
			
			List<User> userListT = userService.getUserList(userTemp);
			List<User> userListT2 = new ArrayList<User>();
			List<Map<String,String>> userList = new ArrayList<Map<String,String>>();
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			if (userListT!=null && userListT.size()>0) {
				if (!"".equals(department) && !department.equals(userT.getDepartment_id())) {//跨部门分配过滤掉高管和员工
					for (User user : userListT) {
//						if (!user.getPosition_id().equals("10039") && !user.getPosition_id().equals("10034")) {
//							userListT2.add(user);
//						}
						if (user.getPosition_id().equals("10035") || user.getPosition_id().equals("10036")) {//跨部门分配选择其他部门经理
							userListT2.add(user);
						}
					}
					userListT=userListT2;
				}
				for (User user : userListT) {
					for (Dictionary dic : dictionaryLlist) {
//						System.out.println("department_id="+user.getDepartment_id());
						if (!StringUtil.isNUll(user.getDepartment_id()) && dic.getType_code().equals("01") && user.getDepartment_id().equals(dic.getCode())) {
							user.setDepartment_Name(dic.getName());
							break;
						}
					}
					for (Dictionary dic : dictionaryLlist) {
						if (!StringUtil.isNUll(user.getTeam_id()) && dic.getType_code().equals("02") && user.getTeam_id().equals(dic.getCode())) {
							user.setTeam_Name(dic.getName());
							break;
						}
					}
					
				}
				Map<String, String> map = null;
				for (int i = 0; i < userListT.size(); i++) {
					User userTemp2 = userListT.get(i);
					map = new HashMap<String, String>();
					map.put("userId", userTemp2.getUserId());
					map.put("userName", userTemp2.getUserName());
					map.put("department_Name", userTemp2.getDepartment_Name());
					map.put("team_Name", userTemp2.getTeam_Name());
					userList.add(map);
				}
			}
			
			model.addAttribute("flag", flag);
			model.addAttribute("department", userTemp.getDepartment_id());
			model.addAttribute("team", userTemp.getTeam_id());
			model.addAttribute("positionId", userTemp.getPosition_id());
			
			JSONObject json = new JSONObject();
			json.put("userId", userId);
			json.put("userRole", userRole);
			json.put("userList", userList);
			
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 汇总统计主页
	 * @param 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/summary")
	@LogAnnotation(eventCode="1021005",eventProcess="")
	public ModelAndView doSummary(HttpSession session, HttpServletRequest request, Model m) {
		User userT = PublicUtil.getUserOnline(request);
		String userId = userT.getUserId();
		List<Role> roles = roleService.getRoleByUserId(userId);
		String userRole = null;
		for (Role role : roles) {
			if (role.getRoleCode().equals("BMJL") || role.getRoleCode().equals("XMJL") || role.getRoleCode().equals("TDJL") || role.getRoleCode().equals("PMO001") || role.getRoleCode().equals("PMUPDATE") || role.getRoleCode().equals("boss") || role.getRoleCode().equals("SUPER") || role.getRoleCode().equals("admin11")) {
				userRole = role.getRoleCode();
				break;
			}
		}
		
		ModelAndView mv = new ModelAndView("/admin/summary");
		List<User> userList= summaryTaskService.allEmployees();
		m.addAttribute("userList", userList);
		Map<String,String> map = new HashMap<String, String>();
		//tab1 初始数据及饼图      科技公司 默认选中 展示数据
		map.put("flag", "1");  //tab页标识符     1 按任务完成状态统计分析页面      2 按任务完成得分区间统计分析页面
		if(userRole.equals("BMJL") || userRole.equals("XMJL") || userRole.equals("TDJL")){
			map.put("department_id", userT.getDepartment_id());
		}
		int totalTask = 0;   //完成任务的总数
		int aswcTask= 0; //按时完成任务的条数
		int tqwcTask= 0;	//提前完成任务的条数
		int yqwcTask= 0; //延期完成任务的条数
		totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
		aswcTask= summaryTaskService.aswcTask(map); //按时完成任务的条数
		tqwcTask= summaryTaskService.tqwcTask(map);	//提前完成任务的条数
		yqwcTask= summaryTaskService.ycwcTask(map); //延期完成任务的条数
		request.setAttribute("totalTask", totalTask);
		request.setAttribute("aswcTask", aswcTask);
		request.setAttribute("tqwcTask", tqwcTask);
		request.setAttribute("yqwcTask", yqwcTask);
		
		//tab2 初始数据及饼图      科技公司 默认选中 展示数据
		int totalTask1 = 0;   //完成任务的总数
		int ztotTask= 0; 	//0~3分任务条数
		int ftosTask= 0;		//4~7分任务条数
		int etotTask= 0; 	//8~10分任务条数
	//----按科技公司统计----
		map.put("flag", "2");  //tab页标识符     1 按任务完成状态统计分析页面      2 按任务完成得分区间统计分析页面
			//任务数目
			totalTask1 = summaryTaskService.totalTask(map);   //完成任务的总数
			ztotTask= summaryTaskService.aswcTask(map); //0~3分任务条数
			ftosTask= summaryTaskService.tqwcTask(map);	//4~7分任务条数
			etotTask= summaryTaskService.ycwcTask(map); //8~10分任务条数
			request.setAttribute("totalTask1", totalTask1);
			request.setAttribute("ztotTask", ztotTask);
			request.setAttribute("ftosTask", ftosTask);
			request.setAttribute("etotTask", etotTask);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		for(int type_code=0;type_code<3;type_code++){
			if(type_code==0){
				map2.put("type_code", "01");
				map2.put("id", userT.getDepartment_id());
				List<Dictionary> list01= summaryTaskService.allKinds(map2);
				m.addAttribute("list01", list01);
			}
			if(type_code==1){
				map2.put("type_code", "02");
				List<Dictionary> list02= summaryTaskService.allKinds(map2);
				m.addAttribute("list02", list02);
				}
			if(type_code==2){
				map2.put("type_code", "04");
				List<Dictionary> list04= summaryTaskService.allKinds(map2);
					m.addAttribute("list04", list04);
				}
		}
		mv.addObject("userRole", userRole);
		return 	mv;
	}
	
	/**
	 * 汇总统计点击radiobutton ajax传值
	 * @param 
	 * @param m                            按任务状态统计任务完成情况
	 * @param request
	 * @return
	 */ 
	@RequestMapping(value="/radioChange")
	@LogAnnotation(eventCode="3021005",eventProcess="")
	public void radioChange(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model m) {
			String type_code = StringUtil.nvlString(request.getParameter("type_code"));   //单选框  checked
			String selected = StringUtil.nvlString(request.getParameter("selected"));		//下拉框  checked
			
			Map<String,String> map = new HashMap<String, String>();
			map.put("flag", "1");  //tab页标识符     1 按任务完成状态统计分析页面      2 按任务完成得分区间统计分析页面
			
				int totalTask = 0;   //完成任务的总数
				int aswcTask= 0; //按时完成任务的条数
				int tqwcTask= 0;	//提前完成任务的条数
				int yqwcTask= 0; //延期完成任务的条数
			//----按科技公司统计----
			if(type_code.equals("00")){
					//任务数目
					totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
					aswcTask= summaryTaskService.aswcTask(map); //按时完成任务的条数
					tqwcTask= summaryTaskService.tqwcTask(map);	//提前完成任务的条数
					yqwcTask= summaryTaskService.ycwcTask(map); //延期完成任务的条数
					try {
						JSONObject json=new JSONObject();
						json.put("alltask", totalTask);
						json.put("astask", aswcTask);
						json.put("tqtask", tqwcTask);
						json.put("yqtask", yqwcTask);
						PrintWriter out = null;
						response.setContentType("text/plain");
						out = response.getWriter();
						out.write(json.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				//----按人员统计-----
				}else if(type_code.equals("10")){
					if(selected != "" && selected != null){
						map.put("executedevtasksys", selected);
						//任务数目
						totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
						aswcTask= summaryTaskService.aswcTask(map); //按时完成任务的条数
						tqwcTask= summaryTaskService.tqwcTask(map);	//提前完成任务的条数
						yqwcTask= summaryTaskService.ycwcTask(map); //延期完成任务的条数
						
						try {
							JSONObject json=new JSONObject();
							json.put("alltask", totalTask);
							json.put("astask", aswcTask);
							json.put("tqtask", tqwcTask);
							json.put("yqtask", yqwcTask);
							PrintWriter out = null;
							response.setContentType("text/plain");
							out = response.getWriter();
							out.write(json.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				//--------按部门统计---------------
				}else if(type_code.equals("01")){				
					if(selected != "" && selected != null){
						map.put("department_id", selected);
						//任务数目
						totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
						aswcTask= summaryTaskService.aswcTask(map); //按时完成任务的条数
						tqwcTask= summaryTaskService.tqwcTask(map);	//提前完成任务的条数
						yqwcTask= summaryTaskService.ycwcTask(map); //延期完成任务的条数
						try {
							JSONObject json=new JSONObject();
							json.put("alltask", totalTask);
							json.put("astask", aswcTask);
							json.put("tqtask", tqwcTask);
							json.put("yqtask", yqwcTask);
							PrintWriter out = null;
							response.setContentType("text/plain");
							out = response.getWriter();
							out.write(json.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				//--------------按团队统计----------
				}else if(type_code.equals("02")){				
					if(selected != "" && selected != null){
						map.put("team_id", selected);
						//任务数目
						totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
						aswcTask= summaryTaskService.aswcTask(map); //按时完成任务的条数
						tqwcTask= summaryTaskService.tqwcTask(map);	//提前完成任务的条数
						yqwcTask= summaryTaskService.ycwcTask(map); //延期完成任务的条数
						try {
							JSONObject json=new JSONObject();
							json.put("alltask", totalTask);
							json.put("astask", aswcTask);
							json.put("tqtask", tqwcTask);
							json.put("yqtask", yqwcTask);
							PrintWriter out = null;
							response.setContentType("text/plain");
							out = response.getWriter();
							out.write(json.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				//--------------------按岗位统计----------------------------
				}else if(type_code.equals("04")){				
					if(selected != "" && selected != null){
						map.put("job_id", selected);
						//任务数目
						totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
						aswcTask= summaryTaskService.aswcTask(map); //按时完成任务的条数
						tqwcTask= summaryTaskService.tqwcTask(map);	//提前完成任务的条数
						yqwcTask= summaryTaskService.ycwcTask(map); //延期完成任务的条数
						try {
							JSONObject json=new JSONObject();
							json.put("alltask", totalTask);
							json.put("astask", aswcTask);
							json.put("tqtask", tqwcTask);
							json.put("yqtask", yqwcTask);
							PrintWriter out = null;
							response.setContentType("text/plain");
							out = response.getWriter();
							out.write(json.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}else{
					try {
						JSONObject json=new JSONObject();
						json.put("alltask", 0);
						json.put("astask", 0);
						json.put("tqtask", 0);
						json.put("yqtask", 0);
						PrintWriter out = null;
						response.setContentType("text/plain");
						out = response.getWriter();
						out.write(json.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				//得分计算
	}
	
	/**
	 * 汇总统计点击radiobutton ajax传值
	 * @param 
	 * @param m                            按任务得分区间统计任务完成情况
	 * @param request
	 * @return
	 */ 
	@RequestMapping(value="/radioChange2")
	@LogAnnotation(eventCode="3021006",eventProcess="")
	public void radioChange2(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model m) {
			String type_code = StringUtil.nvlString(request.getParameter("type_code"));   //单选框  checked 被选中的值
			String selected = StringUtil.nvlString(request.getParameter("selected"));		//下拉框  checked 被选中的值
			
			Map<String,String> map = new HashMap<String, String>();
			map.put("flag", "2");  //tab页标识符     1 按任务完成状态统计分析饼图页面      2 按任务完成得分区间统计分析饼图页面
			
				int totalTask = 0;   //完成任务的总数
				int ztotTask= 0; 	//0~3分任务条数
				int ftosTask= 0;		//4~7分任务条数
				int etotTask= 0; 	//8~10分任务条数
			//----按科技公司统计----
			if(type_code.equals("00")){
					//任务数目
					totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
					ztotTask= summaryTaskService.aswcTask(map); //0~3分任务条数
					ftosTask= summaryTaskService.tqwcTask(map);	//4~7分任务条数
					etotTask= summaryTaskService.ycwcTask(map); //8~10分任务条数
					try {
						JSONObject json=new JSONObject();
						json.put("allTask", totalTask);
						json.put("ztotTask", ztotTask);
						json.put("ftosTask", ftosTask);
						json.put("etotTask", etotTask);
						PrintWriter out = null;
						response.setContentType("text/plain");
						out = response.getWriter();
						out.write(json.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				//----按人员统计-----
				}else if(type_code.equals("10")){
					if(selected != "" && selected != null){
						map.put("executedevtasksys", selected);
						//任务数目
						totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
						ztotTask= summaryTaskService.aswcTask(map); //0~3分任务条数
						ftosTask= summaryTaskService.tqwcTask(map);	//4~7分任务条数
						etotTask= summaryTaskService.ycwcTask(map); //8~10分任务条数
						try {
							JSONObject json=new JSONObject();
							json.put("allTask", totalTask);
							json.put("ztotTask", ztotTask);
							json.put("ftosTask", ftosTask);
							json.put("etotTask", etotTask);
							PrintWriter out = null;
							response.setContentType("text/plain");
							out = response.getWriter();
							out.write(json.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				//--------按部门统计---------------
				}else if(type_code.equals("01")){				
					if(selected != "" && selected != null){
						map.put("department_id", selected);
						//任务数目
						totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
						ztotTask= summaryTaskService.aswcTask(map); //0~3分任务条数
						ftosTask= summaryTaskService.tqwcTask(map);	//4~7分任务条数
						etotTask= summaryTaskService.ycwcTask(map); //8~10分任务条数
						try {
							JSONObject json=new JSONObject();
							json.put("allTask", totalTask);
							json.put("ztotTask", ztotTask);
							json.put("ftosTask", ftosTask);
							json.put("etotTask", etotTask);
							PrintWriter out = null;
							response.setContentType("text/plain");
							out = response.getWriter();
							out.write(json.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				//--------------按团队统计----------
				}else if(type_code.equals("02")){				
					if(selected != "" && selected != null){
						map.put("team_id", selected);
						//任务数目
						totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
						ztotTask= summaryTaskService.aswcTask(map); //0~3分任务条数
						ftosTask= summaryTaskService.tqwcTask(map);	//4~7分任务条数
						etotTask= summaryTaskService.ycwcTask(map); //8~10分任务条数
						try {
							JSONObject json=new JSONObject();
							json.put("allTask", totalTask);
							json.put("ztotTask", ztotTask);
							json.put("ftosTask", ftosTask);
							json.put("etotTask", etotTask);
							PrintWriter out = null;
							response.setContentType("text/plain");
							out = response.getWriter();
							out.write(json.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				//--------------------按岗位统计----------------------------
				}else if(type_code.equals("04")){				
					if(selected != "" && selected != null){
						map.put("job_id", selected);
						//任务数目
						totalTask = summaryTaskService.totalTask(map);   //完成任务的总数
						ztotTask= summaryTaskService.aswcTask(map); //0~3分任务条数
						ftosTask= summaryTaskService.tqwcTask(map);	//4~7分任务条数
						etotTask= summaryTaskService.ycwcTask(map); //8~10分任务条数
						try {
							JSONObject json=new JSONObject();
							json.put("allTask", totalTask);
							json.put("ztotTask", ztotTask);
							json.put("ftosTask", ftosTask);
							json.put("etotTask", etotTask);
							PrintWriter out = null;
							response.setContentType("text/plain");
							out = response.getWriter();
							out.write(json.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}else{
					try {
						JSONObject json=new JSONObject();
						json.put("allTask", 0);
						json.put("ztotTask", 0);
						json.put("ftosTask", 0);
						json.put("etotTask", 0);
						PrintWriter out = null;
						response.setContentType("text/plain");
						out = response.getWriter();
						out.write(json.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				//得分计算
	}
	
	@RequestMapping(value="/PieChart")
	@LogAnnotation(eventCode="3021007",eventProcess="")
	public String PieChart(HttpSession session, HttpServletRequest request, Model m) {
		String astj=StringUtil.nvlString(request.getParameter("astj"));		//按时提交
		String tqtj=StringUtil.nvlString(request.getParameter("tqtj"));		//提前提交
		String yqtj=StringUtil.nvlString(request.getParameter("yqtj"));		//延期提交
		String type=StringUtil.nvlString(request.getParameter("type"));	//tab类型    1 按任务完成状态统计分析    2 按任务完成得分统计分析
		String ztot=StringUtil.nvlString(request.getParameter("ztot"));		//0~3分
		String ftos=StringUtil.nvlString(request.getParameter("ftos"));		//4~7分
		String etot=StringUtil.nvlString(request.getParameter("etot"));		//8~10分
		String message1="";
		String message2="";
		String message3="";
		int one =StringUtil.StringToInt(astj)+StringUtil.StringToInt(tqtj)+StringUtil.StringToInt(yqtj);
		int two =StringUtil.StringToInt(ztot)+StringUtil.StringToInt(ftos)+StringUtil.StringToInt(etot);
		if("1".equals(type)){
			message1="按时完成";
			 message2="提前完成";
			 message3="延期完成";
			 if("".equals(tqtj)&&"".equals(astj)&&"".equals(yqtj) || one == 0){
					astj="100";
					tqtj="100";
					yqtj="100";
				}
			 
			 	request.setAttribute("message1", message1);
				request.setAttribute("message2", message2);
				request.setAttribute("message3", message3);
				
				request.setAttribute("astj", astj);
				request.setAttribute("tqtj", tqtj);
				request.setAttribute("yqtj", yqtj);
			
		}else{
			 message1="1-3分";
			 message2="4-7分";
			 message3="8-10分";
			 if("".equals(ztot)&&"".equals(ftos)&&"".equals(etot) || two == 0){
				 ztot="100";
				 ftos="100";
				 etot="100";
				}
			 
			 	request.setAttribute("message1", message1);
				request.setAttribute("message2", message2);
				request.setAttribute("message3", message3);
				
				request.setAttribute("astj", ztot);
				request.setAttribute("tqtj", ftos);
				request.setAttribute("yqtj", etot);
		}
		
		return "admin/pieChart";
	}
	
	
	public static String subStr(double d){
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
		return df.format(d);
	} 
	
	/***
	 * 导出任务列表
	 * @param taskInfo
	 * @param m
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/exportTask")
	@LogAnnotation(eventCode="1118013",eventProcess="")
	public void exportTask(@ModelAttribute("taskInfo")TaskInfo taskInfo,Model m, HttpServletRequest request, HttpServletResponse response) {
		try {	
			String nodeId = request.getParameter("nodeId");
			String pqFlag = request.getParameter("pqFlag");
			if (StringUtils.equals(pqFlag, "true")) {
				taskInfo = (TaskInfo) request.getSession().getAttribute("taskInfo");
			} else {
				request.getSession().setAttribute("taskInfo", taskInfo);
			}
			String create_name =  request.getParameter("create_name");
			if(!StringUtil.isNUll(create_name)){
				//create_name =  new String(create_name.getBytes("ISO-8859-1"),"utf-8");
				taskInfo.setCreate_name(create_name);
			}
			String exec_name =  request.getParameter("exec_name");
			if(!StringUtil.isNUll(exec_name)){
				//exec_name =  new String(exec_name.getBytes("ISO-8859-1"),"utf-8");
				taskInfo.setExec_name(exec_name);
			}
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			String deparmentId = user.getDepartment_id();
			String teamId = user.getTeam_id();
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
					break;
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL") || userRole.equals("XMJL"))) {//部门经理、项目经理
				taskInfo.setDepartment(deparmentId);
			}else if (!StringUtil.isNUll(userRole) && userRole.equals("TDJL")) {//团队经理
				taskInfo.setDepartment(deparmentId);
				taskInfo.setTeam(teamId);
			}else if (!StringUtil.isNUll(userRole) && userRole.equals("staff")) {//员工登录
				taskInfo.setDepartment(deparmentId);
				taskInfo.setTeam(teamId);
				taskInfo.setExecutedevtasksys(userId);
			}
			
			if (!StringUtil.isNUll(taskInfo.getEndTime())) {
				taskInfo.setEndTime(taskInfo.getEndTime()+" 23:59:59");
			}
			if (!StringUtil.isNUll(taskInfo.getExpectEndTime2())) {
				taskInfo.setExpectEndTime2(taskInfo.getExpectEndTime2()+" 23:59:59");
			}
			if (!StringUtil.isNUll(nodeId)) {
				taskInfo.setId(nodeId);
			}
			
			List<TaskInfo> taskInfoList = adminService.getTaskList(taskInfo);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			int count = 0;
			if (taskInfoList != null && taskInfoList.size()>0) {
				count = taskInfoList.size();
				//taskInfo==>Map
				Map<String,Object> map = null;
				for (TaskInfo t : taskInfoList) {
					map = new HashMap<String,Object>();
					if (!StringUtil.isNUll(t.getTaskContent())) {
						map.put("taskContent", t.getTaskContent());
					}else {
						map.put("taskContent", "");
					}
					
					if (!StringUtil.isNUll(t.getCreate_name())) {
						map.put("createName", t.getCreate_name());
					}else {
						map.put("createName", "");
					}
					
					if (!StringUtil.isNUll(t.getCreateTime())) {
						if(t.getCreateTime().length() >= 10){
							map.put("createTime", t.getCreateTime().substring(0, 10));
						}else {
							map.put("createTime", t.getCreateTime());
						}
					}else {
						map.put("createTime", "");
					}
					if (!StringUtil.isNUll(t.getJxzDate())) {
						if(t.getJxzDate().length() >= 10){
							map.put("jxzTime", t.getJxzDate().substring(0, 10));
						}else {
							map.put("jxzTime", t.getJxzDate());
						}
					}else {
						map.put("jxzTime", "");
					}
					if (!StringUtil.isNUll(t.getExec_name())) {
						map.put("execName", t.getExec_name());
					}else {
						map.put("execName", "");
					}
					
					if (!StringUtil.isNUll(t.getExpectEndTime())) {
						if(t.getExpectEndTime().length() >= 10){
							map.put("expectEndTime", t.getExpectEndTime().substring(0, 10));
						}else {
							map.put("expectEndTime", t.getExpectEndTime());
						}
					}else {
						map.put("expectEndTime", "");
					}
					
					if (!StringUtil.isNUll(t.getDepartment())) {
						for (Dictionary dic : dictionaryLlist) {
							if (dic.getType_code().equals("01") && t.getDepartment().equals(dic.getCode())) {
								map.put("departmentZh",dic.getName());
								break;
							}
						}
					}else {
						map.put("departmentZh", "");
					}
					
					if (!StringUtil.isNUll(t.getTeam())) {
						for (Dictionary dic : dictionaryLlist) {
							if (dic.getType_code().equals("02") && t.getTeam().equals(dic.getCode())) {
								map.put("teamZh",dic.getName());
								break;
							}
						}
					}else {
						map.put("teamZh", "");
					}
					
					if (!StringUtil.isNUll(t.getSubDate())) {
						if(t.getSubDate().length() >= 16){
							map.put("subDate", t.getSubDate().substring(0, 16));
						}else {
							map.put("subDate", t.getSubDate());
						}
					}else {
						map.put("subDate", "");
					}
					
					if (!StringUtil.isNUll(t.getTaskstatus())) {
						for (Dictionary dic : dictionaryLlist) {
							if (dic.getType_code().equals("05") && t.getTaskstatus().equals(dic.getCode())) {
								map.put("taskstatusZh",dic.getName());
								break;
							}
						}
					}else {
						map.put("taskstatusZh", "");
					}
					if (!StringUtil.isNUll(t.getTjtype())) {
						if (t.getTjtype().equals("5")) {
							map.put("tjtypeZh","延期完成");
						}else if (t.getTjtype().equals("6")) {
							map.put("tjtypeZh","按时完成");
						}else if (t.getTjtype().equals("7")) {
							map.put("tjtypeZh","提前完成");
						}
					}else{
						map.put("tjtypeZh","未完成");
					}
					
					if (!StringUtil.isNUll(t.getEndDate())) {
						if(t.getEndDate().length() >= 16){
							map.put("endDate", t.getEndDate().substring(0, 16));
						}else {
							map.put("endDate", t.getEndDate());
						}
					}else {
						map.put("endDate", "");
					}
					
					if (!StringUtil.isNUll(t.getScore())) {
						map.put("score", t.getScore());
					}else {
						map.put("score", "0");
					}
					
					if (!StringUtil.isNUll(t.getRemark())) {
						map.put("remark", t.getRemark());
					}else {
						map.put("remark", "");
					}
					if(!StringUtil.isNUll(t.getTaskWorkTime())){
						map.put("taskWorkTime", t.getTaskWorkTime());
					}else{
						map.put("taskWorkTime", "0");
					}
					map.put("projectCode", StringUtil.nvlString(t.getProjectCode()));
					map.put("projectName", StringUtil.nvlString(t.getProjectName()));
					
					list.add(map);
					
				}
			}
			int n = 50000;		//定义每个sheet存放的数据条数
			String sheetName = "任务统计";
			List<List> lists = new ArrayList<List>();
			
			String[] header = {"任务内容","任务创建人","创建时间","接收时间（开始时间）","任务负责人","计划完成时间","所属部门","所属团队","提交时间","任务状态","任务完成状态","实际完成时间","任务打分","备注","工作量","项目编号","项目名称"};
			String[][] headers = {header};
			String[] key = {"taskContent","createName","createTime","jxzTime","execName","expectEndTime","departmentZh","teamZh","subDate","taskstatusZh","tjtypeZh","endDate","score","remark","taskWorkTime","projectCode","projectName"};
			String[][] keys = {key};
			
			String dateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
			String fileName = "任务统计"+dateStr+".xlsx";
			if (count <= n) {
				String[] sheets = new String[1];
				sheets[0] = sheetName + "1";
				lists.add(list);
				ExportExcelUtil.exportExcel2(fileName, response, lists, headers, keys, sheets);
			}else{
				int fysheet = (int) Math.ceil((double)count / n) ;
				String[] sheets = new String[fysheet];
				for (int i = 1; i <= fysheet; i++) {
					sheets[i - 1] = sheetName + i;
				}
				lists = createList(list,n);
				ExportExcelUtil.exportExcel2(fileName, response, lists, headers, keys, sheets);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//拆分list数组
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<List>  createList(List targe,int size) {  
        List<List> listArr = new ArrayList<List>();  
        //获取被拆分的数组个数  
        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;  
        for(int i=0;i<arrSize;i++) {  
            List  sub = new ArrayList();  
            //把指定索引数据放入到list中  
            for(int j=i*size;j<=size*(i+1)-1;j++) {  
                if(j<=targe.size()-1) {  
                    sub.add(targe.get(j));  
                }  
            }  
            listArr.add(sub);  
        }  
        return listArr;  
    }
	//获取当前时间1
	public String getDate1(int i){
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();  
		Date nowDate = new Date();
		calendar.setTime(nowDate);  
		calendar.add(Calendar.MONTH, i); //1下一月  0当前月    -1上一月
		String lastMont = monthFormat.format(calendar.getTime());  

		return lastMont;
	}

	//获取当前时间
	public String getDate(int i){
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();  
		Date nowDate = new Date();
		calendar.setTime(nowDate);  
		calendar.add(Calendar.MONTH, i); //1下一月  0当前月    -1上一月
		String lastMont = monthFormat.format(calendar.getTime());  

		return lastMont;
	}
	public String getDate2(int i){
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();  
		Date nowDate = new Date();
		calendar.setTime(nowDate);  
		calendar.add(Calendar.MONTH, i); //1下一月  0当前月    -1上一月
		String lastMont = monthFormat.format(calendar.getTime());  

		return lastMont;
	}
	/**
	 * 删除我的任务列表
	 * @param ra
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delTasks")
	@LogAnnotation(eventCode="1201011",eventProcess="")
	public String delTasks(HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
		try{
			String ids = request.getParameter("ids");
			if (!StringUtil.isNUll(ids)) {
				String[] idsArr = ids.split(",");
				List<String> idList = Arrays.asList(idsArr);
				adminService.delTaskByIds(idList);
				
				logService.saveLog(EncodeUtil.LOG_INFO, 
						((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】执行任务查询-任务删除-删除成功！", 
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_DELETE);
				addMessage(ra, "删除成功！");
				session.setAttribute("resultFlag", "1");
			}else {
				addMessage(ra, "请选择要删除的任务。");
				session.setAttribute("resultFlag", "0");
			}
		}catch(Exception e){
			e.printStackTrace();
			try {
				logService.saveLog(EncodeUtil.LOG_ERROR,
						((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(),
						"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】执行任务查询-任务删除-删除失败！",
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(), 
							EncodeUtil.LOG_TASK_DELETE);
				addMessage(ra, "任务删除失败！");
				session.setAttribute("resultFlag", "0");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		String flagfrom = StringUtil.nvlString(request.getParameter("flagfrom"));
		if(!"".equals(flagfrom) && flagfrom.equals("1")){
			return "redirect:/taskListQuery/listTask.do";
		}else{
			return "redirect:/admin/listTask.do";
		}
	}
	@SuppressWarnings("unused")
	private static Integer countDayTime(String endDate,String createDate) {
		Long endDateLong = DateUtil.StringToDate(endDate, "yyyy-MM-dd").getTime();
		Long createDateLong = DateUtil.StringToDate(createDate, "yyyy-MM-dd").getTime();
		Long forDate=(endDateLong-createDateLong)/(24*60*60*1000L);
		return forDate.intValue()+1;
	}

	/**
	 * 首页工作台-计算得分（任务打分、任务状态分）
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private Map<String, Double> getScores(String userId) throws Exception{
		Map<String, Double> map = new HashMap<String, Double>();
		Date nowDate = new Date();
		String imTime = DateUtil.DateToString(nowDate, "yyyy-MM");
		String startDay = imTime+"-01";
		String overDay = DateUtil.DateToString(nowDate, "yyyy-MM-dd");
		Map<String, Object> maps = new HashMap<String, Object>();
	     
		maps.put("userId", userId);
		maps.put("startDay", startDay);
		maps.put("overDay", overDay+" 23:59:59");
		double taskScore = 0;//任务打分
		double taskStatusScore = 0;//任务状态分
		double countStutas1=0;//提前完成的任务数 
		double countStutas2=0;//按时完成的任务数
		double countStutas3=0;//延迟完成的任务数
		double taskCount = 0;//完成任务总条数
		List<TaskInfo> taskList = adminService.getScoreTaskInfoList(maps);
		if(UtilValidate.isNotEmpty(taskList)){
			taskCount = taskList.size();//完成任务总条数
			for(TaskInfo ti:taskList){
				taskScore = Integer.parseInt(ti.getScore()) + taskScore;
				if(ti.getTjtype().equals("7")){
					countStutas1 ++;
				}
				if(ti.getTjtype().equals("6")){
					countStutas2 ++;
				}
				if(ti.getTjtype().equals("5")){
					countStutas3 ++;
				}
			}
			taskScore = MathUtil.div(taskScore, taskCount, 1);//任务打分
			taskStatusScore = MathUtil.div(40*countStutas3+45*countStutas2+50*countStutas1, taskCount, 1);//任务状态分
		}else {
			taskScore = 0;//任务打分
			taskStatusScore = 0;//任务状态分
		}
		map.put("taskScore", taskScore);
		map.put("taskStatusScore", taskStatusScore);
		
		return map;
	}
	
	/**
	* 计算得分
	* @param  :userId
	* @return :double
	* @author :ruiy
	* @date   :2016-5-24下午1:33:17
	*
	*/
	private double addScorre(String userId) throws Exception{
		Date nowDate = new Date();
		String imTime = DateUtil.DateToString(nowDate, "yyyy-MM");
		String startDay = imTime+"-01";
		String overDay = DateUtil.DateToString(nowDate, "yyyy-MM-dd");
		Map<String, Object> maps = new HashMap<String, Object>();
	     
		maps.put("userId", userId);
		maps.put("startDay", startDay);
		maps.put("overDay", overDay+" 23:59:59");
		double countScore = 0;//总得分
		//double forDate = 0;//总天数
		double countStutas1=0;//提前完成的任务数 
		double countStutas2=0;//按时完成的任务数
		double countStutas3=0;//延迟完成的任务数
		List<TaskInfo> scoreList = adminService.getScoreTaskInfoList(maps);
		double taskCount = scoreList.size();//完成任务总条数
		
		if(taskCount>0){
			
			for(TaskInfo taskScore:scoreList){
				countScore = Integer.parseInt(taskScore.getScore()) + countScore;
				//String endDateStr = taskScore.getSubDate();
				//String createDateStr = taskScore.getYfpDate();
				//forDate = countDayTime( endDateStr, createDateStr) + forDate;
				if(taskScore.getTjtype().equals("7")){
					countStutas1 ++;
				}
				if(taskScore.getTjtype().equals("6")){
					countStutas2 ++;
				}
				if(taskScore.getTjtype().equals("5")){
					countStutas3 ++;
				}
			}
			DecimalFormat df   = new DecimalFormat("#.00");
			//double workDay = getDates(startDay,overDay);
			double a = countScore/taskCount/8 * 10;
			double a1 = Double.parseDouble(df.format(a));
			double b = (countStutas1*1.5+countStutas2*1+countStutas3*0.5)/taskCount*15;
			double b1 = Double.parseDouble(df.format(b));
			//double c = forDate/workDay*10;
			double c1 = Double.parseDouble(df.format(10));
			double totleScore = Double.parseDouble(df.format(60 + a1 + b1 + c1));
			return totleScore;
			//double totleScore =55 + countScore/(taskCount*8)*10 + (countStutas1*1.5+countStutas2*1+countStutas3*0.5)/taskCount*15 + forDate/workDay*10;
			//json.put("totleScore",new BigDecimal(totleScore).setScale(0, BigDecimal.ROUND_HALF_UP));
		}else{
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if(userRole.equals("BMJL")&&userRole!=null){
				return 0;
			}
			return 0;
		}
	}
	
	/**
	 * 功能： 手动执行 当月的得分并保存
	 * @return
	 */
	@RequestMapping(value = "/executeScoreTaskEr")
	@ResponseBody
	public void taskScoreExecuteTaskEr(HttpServletResponse response) {

		Calendar instance = Calendar.getInstance();
		int day_of_month = instance.get(Calendar.DAY_OF_MONTH);
		String lastDate=getDate2(0);//格式：yyyy-MM
		if(day_of_month==1){//如果是1号，获取上月
			lastDate=getDate2(-1);
		}
		
		try {
			/**
			 * 计算规则：
			 * KPI值=任务打分+任务状态分+饱和度分;KPI取值范围（75≤KPI≤110）   系统默认得分75分
			 * 1、任务打分=任务打分总和/任务数
			 * 2、任务状态分=（40*延期完成任务数+45*按时完成任务数+50*提前完成任务数）/（延期完成任务数+按时完成任务数+提前完成任务数）
			 * 3、饱和度分={工作总工时/（实际出勤天数*8）}*35;饱和度分取值范围：25≤饱和度分≤40
			 */
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("lastDate", lastDate);
			List<Grades> gradesList = new ArrayList<Grades>();
			Grades gradesTemp = null;
			List<User> userList = userService.getAllUserList();//所有员工
			List<TaskInfo> unFinishTskInfoList = adminService.getunFinishTaskInfoList(maps);
			List<TaskInfo> taskInfoListFul = adminService.getScoreTaskInfoList2(maps);//已经完成的任务
			WorkTime wt = new WorkTime();
			wt.setType("1");
			wt.setStatisticsTime(lastDate);
			List<WorkTime> workTimeList = workTimeService.exportWorkTimeList(wt);
			
			if(userList!=null && userList.size()>0){
				for(User user:userList){
					double allScore = 0;//总得分
					double taskScore = 0;//任务打分
					double taskStatusScore = 0;//任务状态分
					double countStutas1=0;//提前完成的任务数 
					double countStutas2=0;//按时完成的任务数
					double countStutas3=0;//延迟完成的任务数
					double taskCount = 0;//完成任务总条数
					//保存得分
					gradesTemp = new Grades();
					gradesTemp.setgId(UUIDHexGenerator.generater());
					gradesTemp.setgUserId(user.getUserId());
					gradesTemp.setgMonth(lastDate);
					gradesTemp.setgEmpCode(user.getLoginName());
					gradesTemp.setAddTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					
					if(taskInfoListFul!=null && taskInfoListFul.size()>0){//说明本月有已完成任务
						for (TaskInfo ti : taskInfoListFul) {
							if(user.getUserId().equals(ti.getExecutedevtasksys())){
								taskScore = Integer.parseInt(ti.getScore()) + taskScore;
								if(ti.getTjtype().equals("7")){
									countStutas1 ++;
								}
								if(ti.getTjtype().equals("6")){
									countStutas2 ++;
								}
								if(ti.getTjtype().equals("5")){
									countStutas3 ++;
								}
							}
						}
						taskCount = countStutas1 + countStutas2 + countStutas3;
						if(taskCount>0){
							taskScore = MathUtil.div(taskScore, taskCount, 1);//任务打分
							taskStatusScore = MathUtil.div(40*countStutas3+45*countStutas2+50*countStutas1, taskCount, 1);//任务状态分
							gradesTemp.setTaskScore(taskScore+"");
							gradesTemp.setTaskStatusScore(taskStatusScore+"");
							/**
							 * 任务得分与饱和度关联
							 */
							if(workTimeList!=null && workTimeList.size()>0){	
								for(WorkTime wtm:workTimeList){
									if(wtm.getEmpCode().equals(user.getLoginName())){
										String saturation = wtm.getSaturation();
										if(saturation!=null && !saturation.equals("N/A")){
											double saturationScore = MathUtil.round(MathUtil.mul(35, Double.parseDouble(saturation)), 1);//饱和度分
											if(saturationScore>40){
												saturationScore=40;
											}else if (saturationScore<25) {
												saturationScore=25;
											}
											
											allScore = taskScore + taskStatusScore + saturationScore;
											if(allScore>110){
												allScore = 110;
											}else if (allScore<75) {
												allScore = 75;
											}
											gradesTemp.setSaturationScore(MathUtil.round(saturationScore,1)+"");
											gradesTemp.setgScores(MathUtil.round(allScore,1)+"");
											gradesTemp.setgRemark("正常情况");
											gradesTemp.setgState("1");
										}else {
											gradesTemp.setSaturationScore("未获取到考勤信息");
											gradesTemp.setgScores("N/A");
											gradesTemp.setgRemark("未获取到考勤信息");
											gradesTemp.setgState("4");
										}
										break;
									}else {
										gradesTemp.setSaturationScore("未获取到考勤信息");
										gradesTemp.setgScores("N/A");
										gradesTemp.setgRemark("未获取到考勤信息");
										gradesTemp.setgState("4");
									}
								}
							}
						}else {
							if(unFinishTskInfoList!=null && unFinishTskInfoList.size()>0){
								int countT=0;
								for (TaskInfo taskInfo : unFinishTskInfoList) {
									if(user.getUserId().equals(taskInfo.getExecutedevtasksys())){
										gradesTemp.setgRemark("没有已完成的任务");
										gradesTemp.setgState("2");
										break;
									}else{
										countT++;
									}
								}
								if(countT==unFinishTskInfoList.size()){
									gradesTemp.setgRemark("暂且没有任务");
									gradesTemp.setgState("3");
								}
							}else {//说明本月没有任务
								gradesTemp.setgRemark("暂且没有任务");
								gradesTemp.setgState("3");
							}
							gradesTemp.setTaskScore("0");
							gradesTemp.setTaskStatusScore("0");
							if(workTimeList!=null && workTimeList.size()>0){	
								for(WorkTime wtm:workTimeList){
									if(wtm.getEmpCode().equals(user.getLoginName())){
										String saturation = wtm.getSaturation();
										if(saturation!=null && !saturation.equals("N/A")){
											double saturationScore = MathUtil.round(MathUtil.mul(35, Double.parseDouble(saturation)), 1);//饱和度分
											if(saturationScore>40){
												saturationScore=40;
											}else if (saturationScore<25) {
												saturationScore=25;
											}
											
											gradesTemp.setSaturationScore(saturationScore+"");
											gradesTemp.setgScores("75");
										}else {
											gradesTemp.setSaturationScore("未获取到考勤信息");
											gradesTemp.setgScores("N/A");
										}
										break;
									}else {
										gradesTemp.setSaturationScore("未获取到考勤信息");
										gradesTemp.setgScores("N/A");
									}
								}
							}else{
								gradesTemp.setSaturationScore("未获取到考勤信息");
								gradesTemp.setgScores("N/A");
							}
						}
					}else{
						if(unFinishTskInfoList!=null && unFinishTskInfoList.size()>0){
							int countT=0;
							for (TaskInfo taskInfo : unFinishTskInfoList) {
								if(user.getUserId().equals(taskInfo.getExecutedevtasksys())){
									gradesTemp.setgRemark("没有已完成的任务");
									gradesTemp.setgState("2");
									break;
								}else{
									countT++;
								}
							}
							if(countT==unFinishTskInfoList.size()){
								gradesTemp.setgRemark("暂且没有任务");
								gradesTemp.setgState("3");
							}
						}else {//说明本月没有任务
							gradesTemp.setgRemark("暂且没有任务");
							gradesTemp.setgState("3");
						}
						gradesTemp.setTaskScore("0");
						gradesTemp.setTaskStatusScore("0");
						if(workTimeList!=null && workTimeList.size()>0){	
							for(WorkTime wtm:workTimeList){
								if(wtm.getEmpCode().equals(user.getLoginName())){
									String saturation = wtm.getSaturation();
									if(saturation!=null && !saturation.equals("N/A")){
										double saturationScore = MathUtil.round(MathUtil.mul(35, Double.parseDouble(saturation)), 1);//饱和度分
										if(saturationScore>40){
											saturationScore=40;
										}else if (saturationScore<25) {
											saturationScore=25;
										}
										
										gradesTemp.setSaturationScore(saturationScore+"");
										gradesTemp.setgScores("75");
									}else {
										gradesTemp.setSaturationScore("未获取到考勤信息");
										gradesTemp.setgScores("N/A");
									}
									break;
								}else {
									gradesTemp.setSaturationScore("未获取到考勤信息");
									gradesTemp.setgScores("N/A");
								}
							}
						}else{
							gradesTemp.setSaturationScore("未获取到考勤信息");
							gradesTemp.setgScores("N/A");
						}
					}	
					
					gradesList.add(gradesTemp);
				}
				
				if (gradesList!=null && gradesList.size()>0) {
					adminService.saveScore(gradesList,lastDate);
				}
				
				JSONObject json = new JSONObject();
				json.put("res", "ok");
				
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				out.write(json.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		/*Calendar instance = Calendar.getInstance();
		int day_of_month = instance.get(Calendar.DAY_OF_MONTH);
		String lastDate=getDate2(0);//格式：yyyy-MM
		if(day_of_month==1){//如果是1号，获取上月
			lastDate=getDate2(-1);
		}
		
		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("lastDate", lastDate);
			List<Grades> gradesList = new ArrayList<Grades>();
			Grades gradesTemp = null;
			List<User> userList = userService.getAllUserList();
//			List<TaskInfo> taskInfoList = adminService.getAllTaskInfoList(maps);
			List<TaskInfo> unFinishTskInfoList = adminService.getunFinishTaskInfoList(maps);
			List<TaskInfo> taskInfoListFul = adminService.getScoreTaskInfoList2(maps);//已经完成的任务
			WorkTime wt = new WorkTime();
			wt.setType("1");
			wt.setStatisticsTime(lastDate);
			List<WorkTime> workTimeList = workTimeService.exportWorkTimeList(wt);
			
			if(userList!=null && userList.size()>0){
				for(User user:userList){
						double countScore = 0;//总得分
						//double forDate = 0;//总天数
						double countStutas1=0;//提前完成的任务数 
						double countStutas2=0;//按时完成的任务数
						double countStutas3=0;//延迟完成的任务数
						double taskCount = 0;//完成任务总条数
						//保存得分
						gradesTemp = new Grades();
						gradesTemp.setgId(UUIDHexGenerator.generater());
						gradesTemp.setgUserId(user.getUserId());
						gradesTemp.setgMonth(lastDate);
						gradesTemp.setgEmpCode(user.getLoginName());
						gradesTemp.setAddTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
						
						List<Role> roles = roleService.getRoleByUserId(user.getUserId());
						String userRole = null;
						for (Role role : roles) {
							if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
								userRole = role.getRoleCode();
							}
						}
						DecimalFormat df   = new DecimalFormat("#.00");
						if(taskInfoListFul!=null && taskInfoListFul.size()>0){//说明本月有已完成任务
							BigDecimal db = null;
							for(TaskInfo taskInfo1Ful:taskInfoListFul){
								if(user.getUserId().equals(taskInfo1Ful.getExecutedevtasksys())){
									countScore = Integer.parseInt(taskInfo1Ful.getScore()) + countScore;
									//String endDateStr = taskScore.getSubDate();
									//String createDateStr = taskScore.getYfpDate();
									//forDate = countDayTime( endDateStr, createDateStr) + forDate;
									if(taskInfo1Ful.getTjtype().equals("7")){
										countStutas1 ++;
									}
									if(taskInfo1Ful.getTjtype().equals("6")){
										countStutas2 ++;
									}
									if(taskInfo1Ful.getTjtype().equals("5")){
										countStutas3 ++;
									}
								}
							}
							taskCount = countStutas1+countStutas2+countStutas3;
							//得分数据计算
							double departWorkScore = 0;
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("subDate", lastDate);
							map.put("userId", user.getUserId());
							if(taskCount>0){
								double a = countScore/taskCount/8 * 10;
								double a1 = Double.parseDouble(df.format(a));
								double b = (countStutas1*1.5+countStutas2*1+countStutas3*0.5)/taskCount*15;
								double b1 = Double.parseDouble(df.format(b));
								//double c = forDate/workDay*10;
								double c1 = Double.parseDouble(df.format(10));
								double totleScore = Double.parseDouble(df.format(60 + a1 + b1 + c1));
									if(userRole.equals("BMJL")&&userRole!=null){
										double score1 =  Double.parseDouble(df.format(totleScore*0.3));//个人整体打分*30%
										List<WorkTime> allDpartwt = workTimeService.getDepartWT(lastDate);
										if(allDpartwt!=null&&allDpartwt.size()>0){	
											for(WorkTime wtm:allDpartwt){
													if(wtm.getDepartment().equals(user.getDepartment_id())){
														if(wtm.getSaturation()!=null && !wtm.getSaturation().equals("N/A")){
															departWorkScore = Double.parseDouble(wtm.getSaturation());
														}
													}
												}
										}
										//departWorkScore = departWorkTime(user.getDepartment_id());
										double score2 =  Double.parseDouble(df.format(departWorkScore*0.3*100));//整个部门工作量饱和度得分*30%
										double departScore= subTaskTotle(map);
										double score3 =  Double.parseDouble(df.format(departScore*0.4*100));//部门提交任务得分*40%
										double score = Double.parseDouble(df.format(score1+score2+score3));
										db = new BigDecimal(score).setScale(0, BigDecimal.ROUND_HALF_UP);
										gradesTemp.setgScores(db.toString());
									}else{
										db = new BigDecimal(totleScore).setScale(0, BigDecimal.ROUND_HALF_UP);//员工任务总得分
										*//**
										 * 任务得分与饱和度关联
										 *//*
										if(workTimeList!=null && workTimeList.size()>0){	
											for(WorkTime wtm:workTimeList){
												if(wtm.getEmpCode().equals(user.getLoginName())){
													String saturation = wtm.getSaturation();
													if(saturation!=null && !saturation.equals("N/A")){
														double newScore = MathUtil.round(MathUtil.mul(db.doubleValue(), Double.parseDouble(saturation)), 2);
														if(newScore>105){
															newScore=105.0;
														}
														gradesTemp.setgScores(newScore+"");
														gradesTemp.setgRemark("正常情况");
														gradesTemp.setgState("1");
													}else {
														gradesTemp.setgScores("N/A");
														gradesTemp.setgRemark("考勤为N/A，饱和度为N/A，系统未调整分数为"+db.toString());
														gradesTemp.setgState("4");
														
													}
													break;
												}else {
													gradesTemp.setgScores("N/A");
													gradesTemp.setgRemark("考勤为N/A，饱和度为N/A，系统未调整分数为"+db.toString());
													gradesTemp.setgState("4");
												}
												
											}
										}else {
											gradesTemp.setgScores("N/A");
											gradesTemp.setgRemark("考勤为N/A，饱和度为N/A，系统未调整分数为"+db.toString());
											gradesTemp.setgState("4");
										}
									}
									
							}else if(unFinishTskInfoList!=null && unFinishTskInfoList.size()>0){
								int countT=0;
								for (TaskInfo taskInfo : unFinishTskInfoList) {
									if(user.getUserId().equals(taskInfo.getExecutedevtasksys())){
										gradesTemp.setgScores("0");
										gradesTemp.setgRemark("没有已完成的任务");
										gradesTemp.setgState("2");
										break;
									}else{
										countT++;
									}
								}
								if(countT==unFinishTskInfoList.size()){
									gradesTemp.setgScores("0");
									gradesTemp.setgRemark("暂且没有任务");
									gradesTemp.setgState("3");
								}
							
							}else {//说明本月没有任务
								gradesTemp.setgScores("0");
								gradesTemp.setgRemark("暂且没有任务");
								gradesTemp.setgState("3");
							}
						}else {
								if(userRole!=null && userRole.equals("BMJL")){
									gradesTemp.setgScores("0");
									gradesTemp.setgRemark("没有已完成的任务");
									gradesTemp.setgState("2");
								}
//								taskInfoList.removeAll(taskInfoListFul);
								if(unFinishTskInfoList!=null && unFinishTskInfoList.size()>0){
									int countT=0;
									for (TaskInfo taskInfo : unFinishTskInfoList) {
										if(user.getUserId().equals(taskInfo.getExecutedevtasksys())){
											gradesTemp.setgScores("0");
											gradesTemp.setgRemark("没有已完成的任务");
											gradesTemp.setgState("2");
											break;
										}else{
											countT++;
										}
									}
									if(countT==unFinishTskInfoList.size()){
										gradesTemp.setgScores("0");
										gradesTemp.setgRemark("暂且没有任务");
										gradesTemp.setgState("3");
									}
								
								}else {//说明本月没有任务
									gradesTemp.setgScores("0");
									gradesTemp.setgRemark("暂且没有任务");
									gradesTemp.setgState("3");
								}
						}
						gradesList.add(gradesTemp);
					}
				}
			
			if (gradesList!=null && gradesList.size()>0) {
				adminService.saveScore(gradesList,lastDate);
			}
			
			JSONObject json = new JSONObject();
			json.put("res", "ok");
			
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	/**
	 * 计算部门任务提交得分  还需要  * 40%
	 * @param  :userId
	 * @return :List<TaskInfo>
	 * @author :ruiy
	 * @date   :2016-5-24下午1:41:06
	 *
	 */
	public double subTaskTotle(Map<String, Object> map){
		double count5 = 0;//延迟提交
		double count6 = 0;//按时提交
		double count7 = 0;//提前提交
		double countT = 0;//提交总数
		List<String> list = adminService.departmentTaskTotle(map);
		if(list.size()>0){
			for(String str:list){
				if(str.equals("5")){
					count5++;
				}else if(str.equals("6")){
					count6++;
				}else{
					count7++;
				}
			}
			countT = list.size();
			DecimalFormat df   = new DecimalFormat("#.00");
			double sub5 = Double.parseDouble(df.format((count5/countT)));
			double sub6 = Double.parseDouble(df.format((count6/countT)));
			double sub7 = Double.parseDouble(df.format((count7/countT)));
			double subT = Double.parseDouble(df.format(sub5*0+sub6*1+sub7*1));
			return subT;
		}else{
			return 0;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new AdminController().getDate2(0));
	}
}
