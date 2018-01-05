package cn.taskSys.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.taskSys.base.PageView;
import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.IAdminService;
import cn.taskSys.service.ITestService;
import cn.taskSys.service.RoleService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.StringUtil;

@Controller
@RequestMapping(value = "/test")
public class testAction  extends BaseAction{
	private static Logger logger = Logger.getLogger(testAction.class);


	@Autowired
	private ITestService testService;
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UtilsService utilsService;
	
	//测试-用户树
	@RequestMapping(value = "/listquery", method = RequestMethod.GET)
	@LogAnnotation(eventCode="10000101",eventProcess="")
	public String listquery() {
			return "testTree/mainFrame";
	}	
	//测试-用户树
	@RequestMapping(value = "/treeJsp", method = RequestMethod.GET)
	@LogAnnotation(eventCode="10000102",eventProcess="")
	public String treeJsp() {
		return "testTree/treeTimeJsp";
	}
	//测试-用户树top
	@RequestMapping(value = "/topTask", method = RequestMethod.GET)
	@LogAnnotation(eventCode="10000108",eventProcess="")
	public String topTask() {
		return "testTree/topTask";
	}


	//测试- 查询业务组
	@RequestMapping(value = "/resTerr1", method = RequestMethod.GET)
	@LogAnnotation(eventCode="10000103",eventProcess="")
	public void resTerr(HttpServletRequest request, HttpServletResponse rep){
		PrintWriter out = null;
		String outs = null;
		try {
			out = rep.getWriter();
			String nodeId=StringUtil.nvlString(request.getParameter("nodeId"));
			User orgId =(User)request.getSession().getAttribute("JX_USERINFO");
			if("".equals(nodeId)){
				nodeId=orgId.getOrgId();
			}
			outs = testService.getModuleTree(nodeId);
			out.write(outs);
			out.flush();
		} catch (Exception e) {
			
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 按照用户id返回这个用户所具有的模块树,异步树的第一层
	 * 
	 * @return List
	 */
	//测试- 查询业务组
	@RequestMapping(value = "/resTerr", method = RequestMethod.GET)
	@LogAnnotation(eventCode="10000104",eventProcess="")
	public void timeTree(HttpServletRequest request, HttpServletResponse rep) {
		PrintWriter out = null;
		try {
			String nodeId=StringUtil.nvlString(request.getParameter("nodeId"));
			out = rep.getWriter();
			User orgId =(User)request.getSession().getAttribute("JX_USERINFO");
			if("".equals(nodeId)){
				nodeId=orgId.getDepartment_id();
			}
			String outs = testService.getTimerTree("2011803");
			out.write(outs);
			out.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 按照用户id返回这个用户所具有的模块树,异步树的第一层
	 * 
	 * @return List
	 */
	//测试- 查询业务组
	@RequestMapping(value = "/subTimeTree", method = RequestMethod.POST)
	@LogAnnotation(eventCode="10000105",eventProcess="")
	public void subTimeTree(HttpServletRequest req, HttpServletResponse rep) {
		PrintWriter out = null;
		try {
			out = rep.getWriter();
			String nodeId=StringUtil.nvlString(req.getParameter("nodeId"));
			JSONArray outs = testService.getSubTimerTree(nodeId);
			out.write(outs.toString());
			out.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	//test--列表
	
	/***
	 * 任务列表查询
	 * @param taskInfo
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listTask")
	@LogAnnotation(eventCode="10000106",eventProcess="")
	public ModelAndView getTasklist(@ModelAttribute("taskInfo")TaskInfo taskInfo,Model m, HttpServletRequest request) {
		try {	
			String pqFlag = request.getParameter("pqFlag");
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
				taskInfo.setExec_name(exec_name);
			}
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			String deparmentId = user.getDepartment_id();
			String teamId = user.getTeam_id();
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode().equals("staff") || role.getRoleCode().equals("director")  || role.getRoleCode().equals("boss") || role.getRoleCode().equals("SUPER")) {
					userRole = role.getRoleCode();
					break;
				}
			}
			if(!StringUtil.isNUll(userRole) && userRole.equals("director")){
				if (user.getPosition_id().equals("10035")) {//部门经理
					taskInfo.setDepartment(deparmentId);
				}else if (user.getPosition_id().equals("10036")) {//团队经理
					taskInfo.setDepartment(deparmentId);
					taskInfo.setTeam(teamId);
				}else{
					taskInfo.setDepartment(deparmentId);
				}
			}
			if (!StringUtil.isNUll(userRole) && userRole.equals("staff")) {
				taskInfo.setDepartment(deparmentId);
				taskInfo.setTeam(teamId);
				taskInfo.setExecutedevtasksys(userId);
			}
			if (!StringUtil.isNUll(taskInfo.getEndTime())) {
				taskInfo.setEndTime(taskInfo.getEndTime()+" 23:59:59");
			}
			
			ModelAndView mv = new ModelAndView("/testTree/listT");
			//查询条件带回
			mv.addObject("create_name", create_name);
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
			pageView = adminService.getTaskListpageView(taskInfo);	
			m.addAttribute("pageView", pageView);
			
			/***********初始化下拉菜单 start**************/
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
						
			mv.addObject("dictionaryLlist", dictionaryLlist);
			/************初始化下拉菜单 end*************/
			mv.addObject("num", "0");
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
	
}

