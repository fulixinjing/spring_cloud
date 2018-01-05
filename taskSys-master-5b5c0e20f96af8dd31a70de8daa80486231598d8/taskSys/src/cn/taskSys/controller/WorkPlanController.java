package cn.taskSys.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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

import cn.taskSys.base.PageView;
import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.ProjectInfo;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.User;
import cn.taskSys.entity.WorkPlan;
import cn.taskSys.service.IProjectInfoService;
import cn.taskSys.service.IWorkPlanService;
import cn.taskSys.service.RoleService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;

@Controller
@RequestMapping(value = "/work_plan")
public class WorkPlanController extends BaseAction<Object>{
	
	@Autowired
	private IWorkPlanService workPlanService;

	@Autowired
	private UtilsService utilsService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IProjectInfoService projectInfoService;
	
	@RequestMapping(value = "/getWorkPlanList")
	public ModelAndView getWorkTimeList(@ModelAttribute("workPlan")WorkPlan workPlan, HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/workplan/workPlanList");
			
			/***********权限 start**************/
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
				workPlan.setDepartment(deparmentId);
			}else if (!StringUtil.isNUll(userRole) && userRole.equals("TDJL")) {//团队经理
				workPlan.setDepartment(deparmentId);
				workPlan.setTeam(teamId);
			}
			
			if (!StringUtil.isNUll(userRole) && (userRole.equals("staff"))) {//员工
				workPlan.setDepartment(deparmentId);
				workPlan.setTeam(teamId);
				workPlan.setUserId(userId);
			}
			/***********权限 end**************/
			
			PageView<WorkPlan> pageView  = new PageView<WorkPlan>();
			pageView = workPlanService.getWorkPlanListpageView(workPlan);	
			
			mv.addObject("pageView", pageView);
			
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			
			mv.addObject("dictionaryLlist", dictionaryLlist);
			mv.addObject("workPlan", workPlan);
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/addWorkPlan")
    public ModelAndView addWorkPlan(@ModelAttribute("workPlan")WorkPlan workPlan, HttpServletRequest request) {
        try {
            ModelAndView mv = new ModelAndView("/workplan/addWorkPlan");
            
            List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
            
            mv.addObject("dictionaryLlist", dictionaryLlist);
            
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	@RequestMapping(value = "/saveWorkPlan", method = RequestMethod.POST)
    public String saveWorkPlan(@ModelAttribute("workPlan")WorkPlan workPlan, HttpServletRequest request,HttpSession session,RedirectAttributes ra) {
	    String department = ((User)session.getAttribute("JX_USERINFO")).getDepartment_id();//所属部门
	    String team = ((User)session.getAttribute("JX_USERINFO")).getTeam_id();//所属部门
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    String addTime = df.format(new Date());//添加时间
	    String userId = ((User)session.getAttribute("JX_USERINFO")).getUserId();//操作人ID
	    try{//principal
	        workPlan.setId(UUIDHexGenerator.generater());
	        workPlan.setDepartment(department);
	        workPlan.setTeam(team);
	        workPlan.setAddTime(addTime);
	        workPlan.setUserId(userId);
	        workPlan.setPrincipal(((User)session.getAttribute("JX_USERINFO")).getLoginName());
	        
            workPlanService.saveWorkPlan(workPlan);
        }catch (Exception e){
            e.printStackTrace();
        }
	    
	    return "redirect:/work_plan/getWorkPlanList.do";
	}
	
	@RequestMapping(value = "/findWorkPlanById")
	public void findWorkPlanById(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = StringUtil.nvlString(request.getParameter("id"));
			WorkPlan workPlan = workPlanService.getWorkPlanById(id);
			JSONObject  json=new JSONObject();
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			Map<String, String> map = new HashMap<String, String>();
			map.put("content1", workPlan.getContent1()==null ? "" : workPlan.getContent1());
			map.put("content2", workPlan.getContent2()==null ? "" : workPlan.getContent2());
			map.put("content3", workPlan.getContent3()==null ? "" : workPlan.getContent3());
			map.put("content4", workPlan.getContent4()==null ? "" : workPlan.getContent4());
			map.put("content5", workPlan.getContent5()==null ? "" : workPlan.getContent5());
			
			map.put("teamMember1", workPlan.getTeamMember1()==null ? "" : workPlan.getTeamMember1());
			map.put("teamMember2", workPlan.getTeamMember2()==null ? "" : workPlan.getTeamMember2());
			map.put("teamMember3", workPlan.getTeamMember3()==null ? "" : workPlan.getTeamMember3());
			map.put("teamMember4", workPlan.getTeamMember4()==null ? "" : workPlan.getTeamMember4());
			map.put("teamMember5", workPlan.getTeamMember5()==null ? "" : workPlan.getTeamMember5());
			
			map.put("completeStatus1", workPlan.getCompleteStatus1()==null ? "" : workPlan.getCompleteStatus1());
			map.put("completeStatus2", workPlan.getCompleteStatus2()==null ? "" : workPlan.getCompleteStatus2());
			map.put("completeStatus3", workPlan.getCompleteStatus3()==null ? "" : workPlan.getCompleteStatus3());
			map.put("completeStatus4", workPlan.getCompleteStatus4()==null ? "" : workPlan.getCompleteStatus4());
			map.put("completeStatus5", workPlan.getCompleteStatus5()==null ? "" : workPlan.getCompleteStatus5());
			
			map.put("title1", workPlan.getTitle1()==null ? "" : workPlan.getTitle1());
			map.put("title2", workPlan.getTitle2()==null ? "" : workPlan.getTitle2());
			map.put("title3", workPlan.getTitle3()==null ? "" : workPlan.getTitle3());
			map.put("title4", workPlan.getTitle4()==null ? "" : workPlan.getTitle4());
			map.put("title5", workPlan.getTitle5()==null ? "" : workPlan.getTitle5());
			
			map.put("departmentPlan", workPlan.getDepartmentPlan()==null ? "" : workPlan.getDepartmentPlan());
			map.put("risk", workPlan.getRisk()==null ? "" : workPlan.getRisk());
			map.put("measures", workPlan.getMeasures()==null ? "" : workPlan.getMeasures());
			
			json.put("map", map);
			
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/goUpdateWorkPlan")
    public ModelAndView goUpdateWorkPlan(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mv = new ModelAndView("/workplan/updateWorkPlan");
        try {
            String id = StringUtil.nvlString(request.getParameter("id"));
            WorkPlan workPlan = workPlanService.getWorkPlanById(id);
            
            List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
            
            mv.addObject("dictionaryLlist", dictionaryLlist);
            mv.addObject("workPlan", workPlan);
            return mv;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	@RequestMapping(value = "/updateWorkPlan")
	public String updateWorkPlan(@ModelAttribute("workPlan")WorkPlan workPlan, HttpServletRequest request){
	    try{
            workPlanService.updateWorkPlan(workPlan);
        }catch (Exception e){
            e.printStackTrace();
        }
	    return "redirect:/work_plan/getWorkPlanList.do";
	}
	
	@RequestMapping(value = "/exportWorkPlanList")
    public ModelAndView exportWorkPlanList(@ModelAttribute("workPlan")WorkPlan workPlan, HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/workplan/exportWorkPlan");
			
			/***********权限 start**************/
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
				workPlan.setDepartment(deparmentId);
			}else if (!StringUtil.isNUll(userRole) && userRole.equals("TDJL")) {//团队经理
				workPlan.setDepartment(deparmentId);
				workPlan.setTeam(teamId);
			}
			
			if (!StringUtil.isNUll(userRole) && (userRole.equals("staff"))) {//员工
				workPlan.setDepartment(deparmentId);
				workPlan.setTeam(teamId);
				workPlan.setUserId(userId);
			}
			/***********权限 end**************/
			
			workPlan.setType("1");
			List<WorkPlan> workPlanList1 = workPlanService.exportWorkPlanList(workPlan);
			workPlan.setType("2");
			List<WorkPlan> workPlanList2 = workPlanService.exportWorkPlanList(workPlan);
			mv.addObject("workPlanList1", workPlanList1);
			mv.addObject("workPlanList2", workPlanList2);
			mv.addObject("exportToExcel", "YES");
			mv.addObject("nowTime", DateUtil.DateToString(new Date(), "yyyyMMddHHmmss"));
			return mv;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/projectList")
	public ModelAndView getProject(HttpServletRequest request){
		try {
			ModelAndView mv = new ModelAndView("workplan/projectList");
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/ajaxProjectList", method=RequestMethod.POST)
	public void getUsers(HttpServletRequest request, Model model, HttpServletResponse response){
		try {
			String proName = StringUtil.nvlString(request.getParameter("proName"));
			String proCode = StringUtil.nvlString(request.getParameter("proCode"));
			JSONObject json = new JSONObject();
			
			ProjectInfo projectInfo = new ProjectInfo();
			if(!"".equals(proName)){
				projectInfo.setProName(proName);
				json.put("proName", proName);
			}
			if(!"".equals(proCode)){
				projectInfo.setCode(proCode);
				json.put("proCode", proCode);
			}
			List<Map<String, String>> list = projectInfoService.findProjectInfos(projectInfo);
			
			
			if(!list.isEmpty()){
				json.put("list", list);
			}
			
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/deleteWorkPlan")
	public String delPlan(HttpServletRequest request, HttpSession session, RedirectAttributes ra){
		try {
			String id = StringUtil.nvlString(request.getParameter("id"));
			if(!("").equals(id)){
				WorkPlan workPlan = new WorkPlan();
				workPlan.setId(id);
				workPlanService.deleteWorkPlan(workPlan);
				
				addMessage(ra, "删除成功！");
				session.setAttribute("resultFlag", "1");
			}else {
				addMessage(ra, "请选择要删除的任务。");
				session.setAttribute("resultFlag", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(ra, "删除失败！");
			session.setAttribute("resultFlag", "0");
		}
		return "redirect:/work_plan/getWorkPlanList.do";
	}
}
