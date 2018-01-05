package cn.taskSys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.DirectorService;
import cn.taskSys.service.IAdminService;
import cn.taskSys.service.IMyTaskService;
import cn.taskSys.service.IUserService;
import cn.taskSys.service.LogService;
import cn.taskSys.service.RoleService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.EncodeUtil;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.SendEmailUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;

@Controller
@RequestMapping(value = "/director")
public class DirectorController extends BaseAction<Object>{
	@Autowired
	private DirectorService directorService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private LogService logservice;
	
	@Autowired
	private UtilsService utilsService;
	
	@Autowired
	private IMyTaskService myTaskService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IAdminService adminService;
	
	/**
	 * 我创建的任务
	 * @param 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/mySetTask")
	@LogAnnotation(eventCode="3042001",eventProcess="")
	public ModelAndView mySetTask(HttpSession session, HttpServletRequest request, Model m) {
		
		try{
			User user = (User)request.getSession().getAttribute("JX_USERINFO");
			String userId = user.getUserId();
			
			String flag = StringUtil.nvlString(request.getParameter("flag"));//标识符  1进行中模块   2已完成模块    3已转交
		
			ModelAndView mv = new ModelAndView("/director/mySetTask");
			Map<String, Object> qry = new HashMap<String, Object>();
			
			Map<String, String> map11 = new HashMap<String, String>();
			map11.put("type", "12");
			List<Dictionary> peojectState = utilsService.getDictionaryList(map11);
			mv.addObject("peojectState", peojectState);

			String depart_id = StringUtil.nvlString(PublicUtil.getUserOnline(request).getDepartment_id());
			if(depart_id.equals("10006")||depart_id.equals("10009")){
					m.addAttribute("preFalg","1");
				}else{
					m.addAttribute("preFalg","2");
				}
			
			String flagS =  StringUtil.nvlString(request.getParameter("flagS"));		
			//由消息提醒跳转工作台时，根据id查看消息对应任务
			String id =  StringUtil.nvlString(request.getParameter("taskInfoId"));
			if (!StringUtil.isNUll(id)) {
				qry.put("id", id);
			}
			
			int offset = 0;
			Integer pageSize = (Integer) request.getSession().getAttribute("ps");
			if (request.getParameter("pager.offset") != null) {
				offset = Integer.parseInt(request.getParameter("pager.offset"));
			}
			if(offset<0){
				offset = 0;
			}
			pageSize = 5;
			request.getSession().setAttribute("ps", 5);
			qry.put("begin", offset);//翻页开始记录
			qry.put("nowNum", 5);
			qry.put("end", offset + pageSize);//翻页结束记录
			qry.put("userId", userId);//查询条件
			qry.put("flag", flag);//标识符  1进行中模块   2已完成模块   3已转交
		
			PagerModel pager = directorService.getMyCreateBenchList(qry);
			List<User> userList = userService.getPageUser(null).getDatas();
			List<TaskInfo> datas = pager.getDatas();
			int size = datas.size();
			
			mv.addObject("userList", userList);
			mv.addObject("pclist", pager);
			mv.addObject("userId", userId);
			mv.addObject("flag", flag);
			mv.addObject("flagS", flagS);
			mv.addObject("index", size);
			mv.addObject("nowDate",DateUtil.DateToString(new Date(),"yyyy-MM-dd"));
				return 	mv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
		
	}
	
	/**
	 * 修改任务   
	 * @param taskInfo
	 * @param session
	 * @param ra
	 * @return
	 */
	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	@LogAnnotation(eventCode="3042002",eventProcess="")
	public String updateTask(@ModelAttribute("taskInfo") TaskInfo taskInfo,HttpSession session,RedirectAttributes ra) {
		String nowDate=getDate(0);
		String nowDate2=getDate2(0);
		List updateName = (List) session.getAttribute("list");
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = taskInfo.getExecutedevtasksys();
			if(userId == "" || userId == null){	//没有修改负责人，负责人不需要重新接收任务  也不需要消息提醒
				map.put("id", taskInfo.getId());
				map.put("executeUsers", userId);
				map.put("taskWorkTime", taskInfo.getTaskWorkTime());
				TaskInfo task= adminService.getTaskInfoById(taskInfo.getId());
				if(task!=null && task.getTaskstatus().equals("4")){
					map.put("taskstatus", "3");
				}else{
					map.put("taskstatus", taskInfo.getTaskstatus());
				}
				map.put("taskContent", taskInfo.getTaskContent());
				map.put("expectEndTime", taskInfo.getExpectEndTime()+nowDate2);
				map.put("projectName", taskInfo.getProjectName());
				map.put("projectCode", taskInfo.getProjectCode());
				map.put("projectStage",taskInfo.getProjectStage());
				
				
				//map.put("yfpDate", nowDate);
				Date sysTime = DateUtil.StringToDate(nowDate, "yyyy-MM-dd");
				Date expectEndTime = DateUtil.StringToDate(taskInfo.getExpectEndTime(), "yyyy-MM-dd");
				if(expectEndTime.before(sysTime)){
					map.put("falred", "0");
				}else{
					map.put("falred", "1");
				}
				map.put("tstatus", taskInfo.getTstatus());
				map.put("start_suspend_time", taskInfo.getStart_suspend_time());
				//map.put("createTime", nowDate);
				directorService.updateTask(map);  
				
				//任务启动时弹出修改任务页面，
				if(taskInfo.getTstatus() != null || taskInfo.getTstatus() != ""){
					//查询要暂停or启动的任务是否有子任务
					int n = directorService.checkSonTask(taskInfo.getId());
					if(n>0){
						directorService.updateSonTask(map);  //有的话一起启动或暂停
					}
					//查询要暂停or启动的任务是否有孙任务
					int n2 = directorService.getGrandsonTask(taskInfo.getId());
					if(n2>0){
						directorService.updateGrandsonTask(map);
					}
				}
				
			}else{	//修改了负责人  新负责人需要接收任务  原任务分解的子任务也要相应的修改创建人
				User user = userService.getUserById(userId);  //根据修改任务是所选的执行人的id获取其详细信息，如所属部门、团队、岗位、职位
				map.put("id", taskInfo.getId());
				map.put("executeUsers", userId);
				map.put("taskstatus", "2");
				map.put("taskContent", taskInfo.getTaskContent());
				map.put("expectEndTime", taskInfo.getExpectEndTime()+nowDate2);
				map.put("exec_name", user.getUserName());
				map.put("department", user.getDepartment_id());
				map.put("team", user.getTeam_id());
				map.put("jobId", user.getJobId());
				map.put("yfpDate", nowDate);
				Date sysTime = DateUtil.StringToDate(nowDate, "yyyy-MM-dd");
				Date expectEndTime = DateUtil.StringToDate(taskInfo.getExpectEndTime(), "yyyy-MM-dd");
				if(expectEndTime.before(sysTime)){
					map.put("falred", "0");
				}else{
					map.put("falred", "1");
				}
				map.put("createTime", nowDate);
				map.put("taskWorkTime", taskInfo.getTaskWorkTime());
				
				map.put("projectName", taskInfo.getProjectName());
				map.put("projectCode", taskInfo.getProjectCode());
				map.put("projectStage",taskInfo.getProjectStage());
				
				//查询修改的任务是否有子任务
				int n = directorService.checkSonTask(taskInfo.getId());
				if(n>0){
					directorService.updateSonTask(map);
				}
				
				directorService.updateTask(map); 
				
				// 修改后 发送提醒
				map.put("receiverUserId", userId);
				map.put("senderUserId", taskInfo.getAllocationUser());
				map.put("sendTime", nowDate);
				map.put("title", ((User)session.getAttribute("JX_USERINFO")).getUserName()+"给您分配了新任务！");
				map.put("isread", "0");
				map.put("taskInfoId", taskInfo.getId());
				map.put("messageStatus", "1");
				utilsService.saveMessage(map);
				
				//修改后发送邮件提醒
				map.put("to_email", user.getEmail());
				map.put("create_name", taskInfo.getCreate_name());
				map.put("expectEndTime", taskInfo.getExpectEndTime());
				SendEmailUtil.sendTaskEmail(map);
			}
			
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】修改操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_TASK_UPDATE);
			addMessage(ra, "操作成功！");
		} catch (Exception e) {
			try {
				logservice.saveLog(EncodeUtil.LOG_ERROR, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】修改操作失败！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作失败！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
		
		String updateUrl = "";
		for(int i =0;i<updateName.size();i++){
			String s =(String) updateName.get(i);
			if(s.equals("zhuGuan")){
				ra.addAttribute("flag", 1);
				updateUrl = "redirect:/director/mySetTask.do";
			}else if(s.equals("gaoGuan")){
				ra.addAttribute("flag", 5);
				updateUrl = "redirect:/director/myTaskBoss.do";
			}
		}
		
		return updateUrl;
	}	
	
	
	/**
	 * 我负责的任务
	 * @param 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/myTask")
	@LogAnnotation(eventCode="3042003",eventProcess="")
	public ModelAndView myTask(HttpSession session, HttpServletRequest request, Model m) {
		
		try{
			User user = (User)request.getSession().getAttribute("JX_USERINFO");
			String userId = user.getUserId();
			
			Map<String, String> map11 = new HashMap<String, String>();
			map11.put("type", "12");
			List<Dictionary> peojectState = utilsService.getDictionaryList(map11);

			String depart_id = StringUtil.nvlString(PublicUtil.getUserOnline(request).getDepartment_id());
			if(depart_id.equals("10006")||depart_id.equals("10009")){
					m.addAttribute("preFalg","1");
				}else{
					m.addAttribute("preFalg","2");
				}
			
			String flag = StringUtil.nvlString(request.getParameter("flag"));//标识符  1已分配模块   2进行中模块   3已提交模块   4已完成模块
		
			ModelAndView mv = new ModelAndView("/director/myTask");
			Map<String, Object> qry = new HashMap<String, Object>();
			
			//由消息提醒跳转工作台时，根据id查看消息对应任务
			String id =  StringUtil.nvlString(request.getParameter("taskInfoId"));
			if (!StringUtil.isNUll(id)) {
				qry.put("id", id);
			}
			
			int offset = 0;
			Integer pageSize = (Integer) request.getSession().getAttribute("ps");
			if (request.getParameter("pager.offset") != null) {
				offset = Integer.parseInt(request.getParameter("pager.offset"));
			}
			if(offset<0){
				offset = 0;
			}
			pageSize = 5;
			request.getSession().setAttribute("ps", 5);
			qry.put("begin", offset);//翻页开始记录
			qry.put("nowNum", 5);
			qry.put("end", offset + pageSize);//翻页结束记录
			qry.put("userId", userId);//查询条件
			qry.put("flag", flag);//标识符  1进行中模块   2已完成模块
		
			PagerModel pager = directorService.getMyResponsibleTaskList(qry);
			List<User> userList = userService.getPageUser(null).getDatas();
			List<TaskInfo> datas = pager.getDatas();
			int size = datas.size();
			mv.addObject("peojectState", peojectState);
			mv.addObject("userList", userList);
			mv.addObject("pclist", pager);
			mv.addObject("userId", userId);
			mv.addObject("flag", flag);
			mv.addObject("index", size);
			mv.addObject("nowDate",DateUtil.DateToString(new Date(),"yyyy-MM-dd"));
				return 	mv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
		
	}
	
	/**
	 * 提交任务
	 * @param request
	 * @param ra
	 * @return
	 */
	@RequestMapping(value="/submitTask")
	@LogAnnotation(eventCode="3042004",eventProcess="")
	public String submitTask(HttpServletRequest request,RedirectAttributes ra,HttpSession session){
			String nowDate=getDate(0); //系统当前时间
			String select = StringUtil.nvlString(request.getParameter("select"));  //提交任务时是否同步转交，1同步转交   2不同步转交
			String flag = StringUtil.nvlString(request.getParameter("flag"));      //tab标识符   1已分配   2进行中   3已提交   4已完成   
		try {
			
			String id = StringUtil.nvlString(request.getParameter("id"));			//任务id
			String allocationUser = StringUtil.nvlString(request.getParameter("allocationUser"));    //任务创建人id
			String executedevtasksys = StringUtil.nvlString(request.getParameter("executedevtasksys"));//任务执行者id
			String taskContent = StringUtil.nvlString(request.getParameter("taskContent"));//任务内容
			String taskstatus = StringUtil.nvlString(request.getParameter("taskstatus"));//任务状态，1待分配，2已分配，3进行中，4延期未提交，5延期提交，6按时提交，7提前提交，8已完成
			String remark = StringUtil.nvlString(request.getParameter("remark"));//提交备注
			String projectName =  StringUtil.nvlString(request.getParameter("projectName"));
			String projectCode =  StringUtil.nvlString(request.getParameter("projectCode"));
			String projectStage =  StringUtil.nvlString(request.getParameter("projectStage"));
			
			Map<String,String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("taskstatus", taskstatus);
			map.put("subDate", nowDate);
			map.put("remark", remark);
			directorService.submitTask(map);   //提交任务  改变状态
			
			// 提交后 发送提醒 	
			map.put("receiverUserId", allocationUser);
			map.put("senderUserId",executedevtasksys);
			map.put("sendTime", nowDate);
			map.put("title", ((User)session.getAttribute("JX_USERINFO")).getUserName()+"提交了您分配的任务");
			map.put("isread", "0");
			map.put("taskInfoId", id);
			map.put("messageStatus", "3");
			utilsService.saveMessage(map);
			
			//提交任务时同步转交  
			if(select.equals("1")){
				String zjid = StringUtil.nvlString(request.getParameter("zjid"));  //转交负责人id
				String uid = ((User) request.getSession().getAttribute("JX_USERINFO")).getUserId();
				User user = userService.getUserById(zjid);
				String uuid = UUIDHexGenerator.generater();
				map.put("id", uuid);
				map.put("taskContent", taskContent);
				map.put("executedevtasksys", zjid);
				map.put("taskstatus", "0");  //已分配状态createTime
				map.put("createTime",nowDate);  //创建时间
				//map.put("create_name", create_name);//任务创建人名字
				map.put("exec_name", user.getUserName());//任务执行者名字
				map.put("team", user.getTeam_id());//转交负责人所在团队编码
				map.put("jobId", user.getJobId());	//转交负责人所属岗位
				map.put("department", user.getDepartment_id());//转交负责人所在部门编码
				map.put("deliver_time", nowDate);//转交时间
				map.put("deliver_taskid", id);//转交（原）任务ID
				map.put("deliver_person", uid);//转交人ID
				map.put("is_pass", "2");//是否通过
				map.put("is_deliver", "1");//是否是转交任务
				map.put("tLevel", "0");//任务级别
				
				map.put("projectName", projectName);
				map.put("projectCode", projectCode);
				map.put("projectStage",projectStage);
		    	directorService.downAllocatingTask(map); 
		    	
		    	//转交后改变原任务是否转交状态   0否  1是
				map.put("delivered", "1");//原任务是否已经转交
				directorService.changeDeliverStatus(map);
		    	 /*	//转交后改变原任务是否转交状态   0否  1是
				map.put("delivered", "1");//原任务是否已经转交
				directorService.changeDeliverStatus(map); 
		    	
		   		// 转交后给转交负责人的部门经理 发送待通过提醒
		    	List<User> datas = userService.getDepartmentManagerById(zjid);    //根据转交负责人id查询出其部门经理的信息
		    	if(datas != null && datas.size()>0){
		    		User dUser = datas.get(0);  //防止一个部门有多个部门经理，根据排序取第一个
		    		map.put("receiverUserId", dUser.getUserId());     //接收人id
					map.put("senderUserId", uid);      //发送人id
					map.put("sendTime", nowDate);
					map.put("title", ((User)session.getAttribute("JX_USERINFO")).getUserName()+"给您转交了一项待通过的新任务！");    //标题
					map.put("isread", "0");
					map.put("taskInfoId", uuid);
					map.put("messageStatus", "7");
					utilsService.saveMessage(map);
					
					//转交后给转交负责人的部门经理 发送待通过邮件提醒
					map.put("to_email", dUser.getEmail());
					map.put("create_name", ((User)session.getAttribute("JX_USERINFO")).getUserName());
					SendEmailUtil.deliverTaskEmail(map);
		    	}*/
			}
			
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】提交操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_TASK_UPDATE);
			addMessage(ra, "操作成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				logservice.saveLog(EncodeUtil.LOG_ERROR, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】提交操作失败！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_SUBMIT);
				addMessage(ra, "操作失败！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		ra.addAttribute("flag", flag);
		
		return "redirect:/director/myTask.do";
	}
	
	/**
	 * 向下分配任务时 校验负责人是不是为同一人
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkFZR")
	@LogAnnotation(eventCode="3132013",eventProcess="")
	public void checkFZR(HttpServletRequest request, HttpServletResponse response) {
		try {
			String executedevtasksys = StringUtil.nvlString(request.getParameter("executedevtasksys"));
			String taskpid = StringUtil.nvlString(request.getParameter("taskpid"));
			Map<String,String> map = new HashMap<String, String>();
			map.put("executedevtasksys", executedevtasksys);
			map.put("taskpid", taskpid);
			int r = directorService.checkFZR(map);

			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.print(r);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 主管   向下分配任务   相当于主管新建任务
	 * @param request
	 * @param ra
	 * @return
	 */
	@RequestMapping(value="/downAllocatingTask")
	@LogAnnotation(eventCode="3042005",eventProcess="")
	public String downAllocatingTask(HttpServletRequest request,RedirectAttributes ra,HttpSession session){
			String nowDate=getDate(0); //系统当前时间
			String nowDate2=getDate2(0);
		try {
			
			String id = UUIDHexGenerator.generater();
			//String id = directorService.getSeqNextval();  //生成任务id
			String taskpid = StringUtil.nvlString(request.getParameter("taskpid"));  //原任务 id
			String taskContent = StringUtil.nvlString(request.getParameter("taskContent"));//任务内容
			String expectEndTime = StringUtil.nvlString(request.getParameter("expectEndTime"));//任务完成时间
			String allocationUser = StringUtil.nvlString(request.getParameter("allocationUser"));    //任务创建人id
			String create_name = StringUtil.nvlString(request.getParameter("create_name"));    //任务创建人名字
			String executedevtasksys = StringUtil.nvlString(request.getParameter("executedevtasksys"));//任务执行者id
			String tLevel = StringUtil.nvlString(request.getParameter("tLevel"));//任务级别
			String taskWorkTime = StringUtil.nvlString(request.getParameter("taskWorkTime"));//任务工作量
			String projectName =  StringUtil.nvlString(request.getParameter("projectName"));
			String projectCode =  StringUtil.nvlString(request.getParameter("projectCode"));
			String projectStage =  StringUtil.nvlString(request.getParameter("projectStage"));
			
			Integer tLevel1 = Integer.parseInt(tLevel);
			String tLevel2 = Integer.toString(tLevel1+1);
			String userId = executedevtasksys;
			
			User user = userService.getUserById(userId);
			
			
			Map<String,String> map = new HashMap<String, String>();
			map.put("id", id);   //任务id
			map.put("allocationUser", allocationUser);  //任务创建人id
			map.put("taskContent", taskContent);//任务内容
			map.put("createTime", nowDate);//任务创建时间
			map.put("expectEndTime", expectEndTime+nowDate2);//任务计划完成时间
			map.put("executedevtasksys", executedevtasksys);//任务执行者id
			map.put("taskstatus", "2");  //已分配状态
			map.put("create_name", create_name);//任务创建人名字
			map.put("exec_name", user.getUserName());//任务执行者名字
			map.put("team", user.getTeam_id());//任务负责人所在团队编码
			map.put("jobId", user.getJobId());	//任务负责人所属岗位
			map.put("department", user.getDepartment_id());//任务负责人所在部门编码
			map.put("yfpDate", nowDate);//分配任务的时间
			map.put("taskpid", taskpid);//原任务id
			map.put("tLevel", tLevel2);
			map.put("taskWorkTime", taskWorkTime);//工作量
			map.put("projectName", projectName);
			map.put("projectCode", projectCode);
			map.put("projectStage",projectStage);
			
			map.put("to_email", user.getEmail());//执行人邮箱
			
			directorService.downAllocatingTask(map);   //主管向下分配任务    --------主管新建任务
			
			// 向下分配后给负责人 发送提醒 		
			map.put("receiverUserId", executedevtasksys);
			map.put("senderUserId",allocationUser);
			map.put("sendTime", nowDate);
			map.put("title", ((User)session.getAttribute("JX_USERINFO")).getUserName()+"给您分配了新任务！");
			map.put("isread", "0");
			map.put("taskInfoId", id);
			map.put("messageStatus", "1");
			utilsService.saveMessage(map);
			
			map.put("expectEndTime", expectEndTime);//任务计划完成时间
			//向任务执行人发送邮件提醒新任务
			SendEmailUtil.sendTaskEmail(map);
			
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】向下分配操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_TASK_DOWN);
			addMessage(ra, "操作成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				logservice.saveLog(EncodeUtil.LOG_ERROR, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】向下分配操作失败！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_DOWN);
				addMessage(ra, "操作失败！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		ra.addAttribute("flag", 1);//跳转到 我创建的任务  进行中 标识符1
		ra.addAttribute("flagS", "switch");
		return "redirect:/director/mySetTask.do";//跳转标识
	}
	
	
	/**
	 * 我的任务
	 * @param 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/myTaskBoss")
	@LogAnnotation(eventCode="1021005",eventProcess="")
	public ModelAndView myTaskBoss(HttpSession session, HttpServletRequest request, Model m) {
		ModelAndView mv;
		try {
			mv = new ModelAndView("/admin/myTask");
			User user = (User)request.getSession().getAttribute("JX_USERINFO");
			String userId = user.getUserId();
			
			String depart_id = StringUtil.nvlString(PublicUtil.getUserOnline(request).getDepartment_id());
			if(depart_id.equals("10006")||depart_id.equals("10009")){
					m.addAttribute("preFalg","1");
				}else{
					m.addAttribute("preFalg","2");
				}
			
			Map<String, String> map11 = new HashMap<String, String>();
			map11.put("type", "12");
			List<Dictionary> peojectState = utilsService.getDictionaryList(map11);
			mv.addObject("peojectState", peojectState);
			
			String flag= StringUtil.nvlString(request.getParameter("flag"));//标识符  1已分配   2进行中   3已提交   4已完成    5高管进行中模块   6高管已完成模块
			String roleCode = myTaskService.roleCode(userId);
			//初始化时  显示标识
			if(roleCode.equals("boss")&&flag.equals("")){
				flag = "5";
			}else if(flag.equals("")){
				flag = "2";
			}
			
			Map<String, Object> qry = new HashMap<String, Object>();

			//由消息提醒跳转时，根据id查看消息对应任务
			String id =  StringUtil.nvlString(request.getParameter("taskInfoId"));
			if (!StringUtil.isNUll(id)) {
				qry.put("id", id);
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
			qry.put("flag", flag);//标识符   1已分配   2进行中   3已提交   4已完成    5进行中模块   6已完成模块
			
			PagerModel pager = myTaskService.empMyTaskList(qry);
			PagerModel pager2 = myTaskService.gaoGuanTaskList(qry);
			List<User> userList = userService.getPageUser(null).getDatas();
			List<TaskInfo> datas = pager2.getDatas();
			int size = datas.size();
			mv.addObject("userList", userList);
			mv.addObject("pclist", pager);
			mv.addObject("pclist2", pager2);
			mv.addObject("userId", userId);
			mv.addObject("flag", flag);
			mv.addObject("index", size);
			mv.addObject("nowDate",DateUtil.DateToString(new Date(),"yyyy-MM-dd"));
			return mv;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return 	null;
	}
		
		/**
		 * 提交任务
		 * @param request
		 * @param ra
		 * @return
		 */
		@RequestMapping(value="/submitTask2")
		@LogAnnotation(eventCode="3042004",eventProcess="")
		public String submitTask2(HttpServletRequest request,RedirectAttributes ra,HttpSession session){
				String nowDate=getDate(0); //系统当前时间
				
				String flag = StringUtil.nvlString(request.getParameter("flag"));      //tab标识符   1已分配   2进行中   3已提交   4已完成   
			try {
				
				String id = StringUtil.nvlString(request.getParameter("id"));			//任务id
				String allocationUser = StringUtil.nvlString(request.getParameter("allocationUser"));    //任务创建人id
				String executedevtasksys = StringUtil.nvlString(request.getParameter("executedevtasksys"));//任务执行者id
				//String taskContent = StringUtil.nvlString(request.getParameter("taskContent"));//任务内容
				String taskstatus = StringUtil.nvlString(request.getParameter("taskstatus"));//任务状态，1待分配，2已分配，3进行中，4延期未提交，5延期提交，6按时提交，7提前提交，8已完成
				String remark = StringUtil.nvlString(request.getParameter("remark"));//提交备注
				Map<String,String> map = new HashMap<String, String>();
				map.put("id", id);
				map.put("taskstatus", taskstatus);
				map.put("subDate", nowDate);
				map.put("remark", remark);
				
				directorService.submitTask(map);   //提交任务  改变状态
				
				// 提交后 发送提醒 	
				String username= userService.getUserById(executedevtasksys).getUserName();
				map.put("senderUserId", executedevtasksys);
				map.put("receiverUserId",allocationUser);
				map.put("sendTime", nowDate);
				map.put("title", username+"提交了您分配的任务");
				map.put("isread", "0");
				map.put("taskInfoId", id);
				map.put("messageStatus", "3");
			
				utilsService.saveMessage(map);
				
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】提交操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作成功！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】提交操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_TASK_SUBMIT);
					addMessage(ra, "操作失败！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			ra.addAttribute("flag", flag);
			
			return "redirect:/director/myTaskBoss.do";
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
		
		//获取当前时间 时分秒
				public String getDate2(int i){
					SimpleDateFormat monthFormat = new SimpleDateFormat(" HH:mm:ss");
					Calendar calendar = Calendar.getInstance();  
					Date nowDate = new Date();
					calendar.setTime(nowDate);  
					calendar.add(Calendar.MONTH, i); //1下一月  0当前月    -1上一月
					String lastMont = monthFormat.format(calendar.getTime());  

					return lastMont;
				}
		
		
		//
		@RequestMapping(value="/userList")
		@LogAnnotation(eventCode="1112011",eventProcess="")
		public ModelAndView getUsers(HttpServletRequest request){
			try {
				ModelAndView mv = new ModelAndView("/director/userList");
				
				String flag = StringUtil.nvlString(request.getParameter("flag"));
				String positionId = StringUtil.nvlString(request.getParameter("positionId"));
				
				String userName = StringUtil.nvlString(request.getParameter("username"));
				User userTemp = new User();
				if (!StringUtil.isNUll(userName)) {
					userTemp.setUserName(userName);
					mv.addObject("username", userName);
				}

				User userT = PublicUtil.getUserOnline(request);
				String userId = userT.getUserId();
				
				List<Role> roles = roleService.getRoleByUserId(userId);
				String userRole = null;
				for (Role role : roles) {
					//if (role.getRoleCode().equals("director") || role.getRoleCode().equals("boss") || role.getRoleCode().equals("SUPER") || role.getRoleCode().equals("admin11")) {
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
					userTemp.setTeam_id(userT.getTeam_id());
				}else if (userRole.equals("XMJL")) {//项目经理
					String department_id = userT.getDepartment_id();
					userTemp.setDepartment_id(department_id);
				}
				if (!"".equals(positionId)) {
					userTemp.setPosition_id(positionId);
				}
				List<User> userList = userService.getUserList(userTemp);
				List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
				if (userList!=null && userList.size()>0) {
					for (User user : userList) {
						for (Dictionary dic : dictionaryLlist) {
//							System.out.println("department_id="+user.getDepartment_id());
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
		
		
		/**
		 * 进行中 任务详情
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/taskParticulars")
		@LogAnnotation(eventCode="1112012",eventProcess="")
		public ModelAndView taskParticulars(HttpServletRequest request){
			try {
				ModelAndView mv = new ModelAndView("/director/taskParticulars");
				String id = StringUtil.nvlString(request.getParameter("id"));
				TaskInfo taskInfo = directorService.getTaskInfoById(id);       //根据任务id获取任务详细信息
				
				Map<String, Object> qry = new HashMap<String, Object>();
				int offset = 0;
				Integer pageSize = (Integer) request.getSession().getAttribute("ps");
				if (request.getParameter("pager.offset") != null) {
					offset = Integer.parseInt(request.getParameter("pager.offset"));
				}
				pageSize = 5;
				request.getSession().setAttribute("ps", 5);
				qry.put("begin", offset);//翻页开始记录
				qry.put("nowNum", 5);
				qry.put("end", offset + pageSize);//翻页结束记录
				qry.put("id", id);//查询条件
				
				PagerModel pager = directorService.getSubtaskList(qry);	//根据任务id获取此任务所有的子任务
				List<Map<String,String>> tList = directorService.getAllSonList(id);	//根据任务id获取此任务所有的子孙任务
				
				int size = pager.getTotal();
				int tsize = tList.size();
				mv.addObject("pclist", pager);
				mv.addObject("index", size);
				mv.addObject("taskInfo", taskInfo);
				mv.addObject("tList", tList);
				mv.addObject("tindex", tsize);
				mv.addObject("id", id);
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * 提交任务时 校验主任务所有的分任务是否全部提价
		 * @param request
		 * @param response
		 */
		@RequestMapping("/checkSubmit")
		@LogAnnotation(eventCode="1112013",eventProcess="")
		public void checkSubmit(HttpServletRequest request, HttpServletResponse response) {
			try {
				String id = StringUtil.nvlString(request.getParameter("id"));
				Map<String,String> map = new HashMap<String, String>();
				int r = directorService.getSubtaskCounts(id);   //获取要提交的任务的子任务数量
				int s = directorService.getSubtaskStatus(id);   //获取要提交的任务的子任务状态为“已提交”的数量
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				if(r>0 && r==s){
					out.print("ok");
				}else if(r==0 && s==0){
					out.print("ok");
				}else{
					out.print("no");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * 已完成 任务详情
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/taskParticularsForCompleted")
		@LogAnnotation(eventCode="1112014",eventProcess="")
		public ModelAndView taskParticularsForCompleted(HttpServletRequest request){
			try {
				ModelAndView mv = new ModelAndView("/director/taskParticularsForCompleted");    //已完成 详情页面
				ModelAndView mv2 = new ModelAndView("/director/taskParticularsForSubmit");    //已提交 详情页面
				String id = StringUtil.nvlString(request.getParameter("id"));
				String flag = StringUtil.nvlString(request.getParameter("flag"));
				TaskInfo taskInfo = directorService.getTaskInfoById(id);       //根据任务id获取主任务详细信息
				
				Map<String, Object> qry = new HashMap<String, Object>();
				int offset = 0;
				Integer pageSize = (Integer) request.getSession().getAttribute("ps");
				if (request.getParameter("pager.offset") != null) {
					offset = Integer.parseInt(request.getParameter("pager.offset"));
				}
				pageSize = 5;
				request.getSession().setAttribute("ps", 5);
				qry.put("begin", offset);//翻页开始记录
				qry.put("nowNum", 5);
				qry.put("end", offset + pageSize);//翻页结束记录
				qry.put("id", id);//查询条件
				
				PagerModel pager = directorService.getSonTaskList(qry);	//根据任务id获取此任务所有的子任务
				List<Map<String,String>> grandsonTaskList = directorService.getGrandsonTaskList(id);	//根据任务id获取此任务所有的孙任务
					int n = pager.getTotal();
					if(flag.equals("2")){
						mv2.addObject("taskInfo", taskInfo);
						mv2.addObject("grandsonTaskList", grandsonTaskList);
						mv2.addObject("pclist", pager);
						mv2.addObject("flag", flag);
						mv2.addObject("id", id);
						mv2.addObject("n", n);
						return mv2;
					}else{
						mv.addObject("taskInfo", taskInfo);
						mv.addObject("pclist", pager);
						mv.addObject("grandsonTaskList", grandsonTaskList);
						mv.addObject("flag", flag);
						mv.addObject("id", id);
						mv.addObject("n", n);
						return mv;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * 删除任务
		 * @param request
		 * @param ra
		 * @return
		 */
		@RequestMapping(value="/deleteTask")
		@LogAnnotation(eventCode="1112015",eventProcess="")
		public String terminatedTask(HttpServletRequest request,RedirectAttributes ra,HttpSession session){
				String nowDate=getDate(0); //系统当前时间
			try {
				String id = StringUtil.nvlString(request.getParameter("id"));			//任务id
				Map<String,String> map = new HashMap<String, String>();
				map.put("id", id);
				map.put("final_time", nowDate);
				directorService.deleteTask(map);   
				//查询删除的任务是否有子任务
				int n = directorService.checkSonTask(id);
				if(n>0){
					directorService.deleteSonTask(map);	//有的话也要删除
				}
				//查询要删除的任务是否有孙任务
				int n2 = directorService.getGrandsonTask(id);
				if(n2>0){
					directorService.deleteGrandsonTask(map);
				}
				
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】删除操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作成功！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】删除操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_TASK_SUBMIT);
					addMessage(ra, "操作失败！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			ra.addAttribute("flag", 1);
			return "redirect:/director/mySetTask.do";
		}
		
		/**
		 * 任务启动前查看该任务的主任务是否已提交，已提交则不能启动该任务
		 * @param request
		 * @param response
		 */
		@RequestMapping("/ckTask")
		@LogAnnotation(eventCode="1212013",eventProcess="")
		public void ckTask(HttpServletRequest request, HttpServletResponse response) {
			try {
				String id = StringUtil.nvlString(request.getParameter("id"));
				Map<String,String> map = new HashMap<String, String>();
				String status = directorService.getTaskStatus(id);   //获取要提交的任务的主任务的任务状态
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				if(status != null && status != ""){
					int s = Integer.parseInt(status);
					out.print(s);
				}else{
					out.print(0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * 暂停or启动任务
		 * @param request
		 * @param ra
		 * @return
		 */
		@RequestMapping(value="/startOrTerminatedTask")
		@LogAnnotation(eventCode="1112016",eventProcess="")
		public String startOrTerminatedTask(HttpServletRequest request,RedirectAttributes ra,HttpSession session){
			    Map<String,String> map = new HashMap<String, String>();
				String nowDate=getDate(0); //系统当前时间
				String tstatus = StringUtil.nvlString(request.getParameter("tstatus"));    //任务活动状态  0 启用   1 暂停   2 终止
				String st = "";
				if(tstatus.equals("0")){
					st = "启用";
				}else{
					st = "暂停";
				}
			try {
				String id = StringUtil.nvlString(request.getParameter("id"));			//任务id
				map.put("id", id);
				map.put("tstatus", tstatus);
				map.put("start_suspend_time", nowDate);
				directorService.updateTask(map);   
				//查询暂停或启动的任务是否有子任务
				int n = directorService.checkSonTask(id);
				if(n>0){
					directorService.updateSonTask(map);  //有的话一起启动或暂停
				}
				//查询要暂停or启动的任务是否有孙任务
				int n2 = directorService.getGrandsonTask(id);
				if(n2>0){
					directorService.updateGrandsonTask(map);
				}
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】"+st+"操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作成功！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】"+st+"操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_TASK_SUBMIT);
					addMessage(ra, "操作失败！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			ra.addAttribute("flag", 1);
			return "redirect:/director/mySetTask.do";
		}
		
		/**
		 * 高管删除任务
		 * @param request
		 * @param ra
		 * @return
		 */
		@RequestMapping(value="/deleteTaskBoss")
		@LogAnnotation(eventCode="1112015",eventProcess="")
		public String deleteTaskBoss(HttpServletRequest request,RedirectAttributes ra,HttpSession session){
				String nowDate=getDate(0); //系统当前时间
			try {
				String id = StringUtil.nvlString(request.getParameter("id"));			//任务id
				Map<String,String> map = new HashMap<String, String>();
				map.put("id", id);
				map.put("final_time", nowDate);
				directorService.deleteTask(map);   
				//查询删除的任务是否有子任务
				int n = directorService.checkSonTask(id);
				if(n>0){
					directorService.deleteSonTask(map);	//有的话也要删除
				}
				//查询要删除的任务是否有孙任务
				int n2 = directorService.getGrandsonTask(id);
				if(n2>0){
					directorService.deleteGrandsonTask(map);
				}
				
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】删除操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作成功！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】删除操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_TASK_SUBMIT);
					addMessage(ra, "操作失败！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			ra.addAttribute("flag", 5);
			return "redirect:/director/myTaskBoss.do";
		}
			
		/**
		 * 高管启动或暂停任务
		 * @param request
		 * @param ra
		 * @return
		 */
		@RequestMapping(value="/startOrTerminatedTaskBoss")
		@LogAnnotation(eventCode="1112019",eventProcess="")
		public String startOrTerminatedTaskBoss(HttpServletRequest request,RedirectAttributes ra,HttpSession session){
				String nowDate=getDate(0); //系统当前时间
				String tstatus = StringUtil.nvlString(request.getParameter("tstatus"));    //任务活动状态  0 启用   1 暂停   2 终止
				String st = "";
				if(tstatus.equals("0")){
					st = "启用";
				}else{
					st = "暂停";
				}
			try {
				String id = StringUtil.nvlString(request.getParameter("id"));			//任务id
				Map<String,String> map = new HashMap<String, String>();
				map.put("id", id);
				map.put("tstatus", tstatus);
				map.put("start_suspend_time", nowDate);
				directorService.updateTask(map);   
				//查询暂停或启动的任务是否有子任务
				int n = directorService.checkSonTask(id);
				if(n>0){
					directorService.updateSonTask(map);  //有的话一起启动或暂停
				}
				//查询要暂停or启动的任务是否有孙任务
				int n2 = directorService.getGrandsonTask(id);
				if(n2>0){
					directorService.updateGrandsonTask(map);//有的话一起启动或暂停
				}
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】"+st+"操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作成功！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】"+st+"操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_TASK_SUBMIT);
					addMessage(ra, "操作失败！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			ra.addAttribute("flag", 5);
			return "redirect:/director/myTaskBoss.do";
		}
		
		@RequestMapping(value="/ajaxUserList", method=RequestMethod.POST)
		@LogAnnotation(eventCode="1112020",eventProcess="")
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
//					if (role.getRoleCode().equals("director") || role.getRoleCode().equals("boss") || role.getRoleCode().equals("SUPER") || role.getRoleCode().equals("admin11")) {
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
							if (!user.getPosition_id().equals("10039") && !user.getPosition_id().equals("10034")) {
								userListT2.add(user);
							}
						}
						userListT=userListT2;
					}
					for (User user : userListT) {
						for (Dictionary dic : dictionaryLlist) {
//							System.out.println("department_id="+user.getDepartment_id());
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
		 *转交任务时 校验转交负责人是不是为同一人
		 * @param request
		 * @param response
		 */
		@RequestMapping("/checkDeliver")
		@LogAnnotation(eventCode="3042003",eventProcess="")
		public void checkDeliver(HttpServletRequest request, HttpServletResponse response) {
			try {
				String fzrID = StringUtil.nvlString(request.getParameter("fzrID"));
				String deliver_taskid = StringUtil.nvlString(request.getParameter("deliver_taskid"));
				Map<String,String> map = new HashMap<String, String>();
				map.put("fzrID", fzrID);
				map.put("deliver_taskid", deliver_taskid);
				int r = directorService.checkDeliver(map);

				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				out.print(r);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * 转交任务   
		 * @param taskInfo
		 * @param session
		 * @param ra
		 * @return
		 */
		@RequestMapping(value = "/deliverTask", method = RequestMethod.POST)
		@LogAnnotation(eventCode="3042002",eventProcess="")
		public String deliverTask(@ModelAttribute("taskInfo") TaskInfo taskInfo,HttpSession session,HttpServletRequest request, RedirectAttributes ra) {
			String nowDate=getDate(0);
			try {
				String uid = ((User) request.getSession().getAttribute("JX_USERINFO")).getUserId();
				User user = userService.getUserById(taskInfo.getExecutedevtasksys());
				Map<String, String> map = new HashMap<String, String>();
				String id = UUIDHexGenerator.generater();
				map.put("id", id);
				map.put("taskContent", taskInfo.getTaskContent());
				map.put("executedevtasksys", taskInfo.getExecutedevtasksys());
				map.put("taskstatus", "0");  //已分配状态createTime
				map.put("createTime",nowDate);  //创建时间
				//map.put("create_name", create_name);//任务创建人名字
				map.put("exec_name", user.getUserName());//任务执行者名字
				map.put("team", user.getTeam_id());//转交负责人所在团队编码
				map.put("jobId", user.getJobId());	//转交负责人所属岗位
				map.put("department", user.getDepartment_id());//转交负责人所在部门编码
				map.put("deliver_time", nowDate);//转交时间
				map.put("deliver_taskid", taskInfo.getDeliver_taskid());//转交（原）任务ID
				map.put("deliver_person", uid);//转交人ID
				map.put("is_pass", "0");//是否通过
				map.put("is_deliver", "1");//是否是转交任务
				map.put("tLevel", "0");//任务级别
				
				map.put("projectName", taskInfo.getProjectName());
				map.put("projectCode", taskInfo.getProjectCode());
				map.put("projectStage",taskInfo.getProjectStage());
				
				
				directorService.downAllocatingTask(map); 
				
				//转交后改变原任务是否转交状态   0否  1是
				map.put("delivered", "1");//原任务是否已经转交
				directorService.changeDeliverStatus(map);
				
				// 转交后给转交负责人的部门经理 发送待通过提醒
		    	List<User> datas = userService.getDepartmentManagerById(taskInfo.getExecutedevtasksys());    //根据转交负责人id查询出其部门经理的信息
		    	if(datas != null && datas.size()>0){
		    		User dUser = datas.get(0);  //防止一个部门有多个部门经理，根据排序取第一个
		    		map.put("receiverUserId", dUser.getUserId());     //接收人id
					map.put("senderUserId", uid);      //发送人id
					map.put("sendTime", nowDate);
					map.put("title", ((User)session.getAttribute("JX_USERINFO")).getUserName()+"给您转交了一项待通过的新任务！");    //标题
					map.put("isread", "0");
					map.put("taskInfoId", id);
					map.put("messageStatus", "7");
					utilsService.saveMessage(map);
					
					//转交后给转交负责人的部门经理 发送待通过邮件提醒
					map.put("to_email", dUser.getEmail());
					map.put("create_name", ((User)session.getAttribute("JX_USERINFO")).getUserName());
					SendEmailUtil.deliverTaskEmail(map);
		    	}
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】转交操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_UPDATE);
				addMessage(ra, "操作成功！");
			} catch (Exception e) {
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】转交操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_TASK_UPDATE);
					addMessage(ra, "操作失败！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
			ra.addAttribute("flag", 2);
			String updateUrl = "redirect:/director/mySetTask.do";
			return updateUrl;
		}	
		
}
