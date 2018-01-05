package cn.taskSys.controller;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Message;
import cn.taskSys.entity.ProjectInfo;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.IMessageService;
import cn.taskSys.service.IMyTaskService;
import cn.taskSys.service.IProjectInfoService;
import cn.taskSys.service.MenuService;
import cn.taskSys.service.RoleService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.StringUtil;
 
@Controller
public class indexController {
	
	@Autowired
	private IMyTaskService myTaskService;
	@Autowired
	private MenuService menuservice;
	@Autowired
	private RoleService roleService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IProjectInfoService projectInfoService;
	@Autowired
	private UtilsService utilsService;
	/**
	 * 
	 * @param menu
	 * @param menuindex
	 * @param request
	 * @param m 查询框架菜单
	 * @return
	 */

	
	
	@RequestMapping("index")
	public String index(Model m){
		m.addAttribute("remind", "0");
		m.addAttribute("sysdate", new Date());
		return "index";
		}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	@LogAnnotation(eventCode="1017001",eventProcess="")
	public String getIndex(@ModelAttribute("menu")String menu,@ModelAttribute("menuindex")String menuindex,
			HttpServletRequest request,Model m) throws Exception { 
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "12");
		List<Dictionary> peojectState = utilsService.getDictionaryList(map);
		m.addAttribute("peojectState",peojectState);
		String depart_id = StringUtil.nvlString(PublicUtil.getUserOnline(request).getDepartment_id());
		if(depart_id.equals("10006")||depart_id.equals("10009")){
				m.addAttribute("preFalg","1");
			}else{
				m.addAttribute("preFalg","2");
			}
			
		try{
				String upPass=request.getParameter("upPass");
				String oldPassword=request.getParameter("paword");
				m.addAttribute("upPass",upPass);
				m.addAttribute("oldPassword",oldPassword);
				m.addAttribute("fmenulist", menuservice.getMenuList("1"));	
				m.addAttribute("smenulist", menuservice.getSecMenuList(menu));
				m.addAttribute("tmenulist", menuservice.getThiMenuList(menu));	
		}catch(Exception e){
			e.printStackTrace();
		}
		//主页消息提醒
		User user = PublicUtil.getUserOnline(request);
		List<Message> message = (List<Message>)messageService.getMessage(user.getUserId());
		//String roleCode = myTaskService.roleCode(user.getUserId());
		//初始化时  显示标识
		/*if(roleCode.equals("SUPER")||roleCode.equals("admin11")){
			message=null;
		}else{}*/
			//将 获取到的message 放入request中
			m.addAttribute("mlist", message);
			m.addAttribute("msgCount",message.size());
			/*//根据ID修改状态
			for(Message ms:message){
				String id = ms.getId();
				messageService.updateMessage(id);
			}*/
		
		m.addAttribute("fmenuxz", menuindex);
		m.addAttribute("menuid", menu);
		String falg="0";
		m.addAttribute("remind", falg);
		m.addAttribute("sysdate", new Date());
		m.addAttribute("nowDate",DateUtil.DateToString(new Date(),"yyyy-MM-dd"));	
		return "index3";
	}
	//ajax 消息状态改变成已读
	@RequestMapping(value="/upMessage")
	@LogAnnotation(eventCode="1017001",eventProcess="")
	public void upMessage(HttpServletRequest request,HttpServletResponse response,Model m) {
		String userId = StringUtil.nvlString(PublicUtil.getUserOnline(request)
				.getUserId());
		List<Message> message = (List<Message>)messageService.getMessage(userId);
		//根据ID修改状态
		try {
			for(Message ms:message){
				String id = ms.getId();
				messageService.updateMessage(id);
				
				JSONObject  json=new JSONObject();
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				out.write(json.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/top1", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1017003",eventProcess="")
	public String top1(@RequestParam("menu") String menuid, HttpServletResponse resp,Model m) {
		try {
			m.addAttribute("fmenulist", menuservice.getMenuList("1"));
				
		} catch (Exception e) {
			
		}	
		m.addAttribute("remind", "0");
		m.addAttribute("menuid", menuid);
		m.addAttribute("sysdate", new Date());
		return "main/top1";
	}
	
	@RequestMapping(value = "/left1", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1017004",eventProcess="")
	public String left1(@RequestParam("menu") String menuid, HttpServletResponse resp,Model m) {
		try {
			m.addAttribute("smenulist", menuservice.getSecMenuList(menuid));
			m.addAttribute("tmenulist", menuservice.getThiMenuList(menuid));
		} catch (Exception e) {
			e.printStackTrace();
		}	
		m.addAttribute("menuid", menuid);
		m.addAttribute("remind", "0");
		m.addAttribute("sysdate", new Date());
		return "main/left1";
	}
	
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	@LogAnnotation(eventCode="1017002",eventProcess="")
	public String getWelcome(HttpServletRequest request,HttpServletResponse response,Model m) { 
		String userId = StringUtil.nvlString(PublicUtil.getUserOnline(request)
				.getUserId());
		String returnStr=null;
		List<Role> roleList=roleService.getRoleByUserId(userId);
		if(roleList==null||roleList.size()<0){
			returnStr= "/welcome";
		}
		if("admin11".equals(roleList.get(0).getRoleCode())){
			returnStr= "redirect:admin/summary.do";
		}else if("SUPER".equals(roleList.get(0).getRoleCode())){
			//returnStr= "/welcome";
			returnStr= "redirect:admin/myWorkbenchList.do";	
		}else{
			returnStr= "redirect:admin/myWorkbenchList.do";	
		}
		
		return returnStr;
		
	}
	
	@RequestMapping(value="/bott", method=RequestMethod.GET)
	@LogAnnotation(eventCode="1017005",eventProcess="")
	public String bott() { 
		return "main/bott1";
	}
	
	
	//ajax 消息状态改变成已读
	@RequestMapping(value="/index/selName")
	@LogAnnotation(eventCode="",eventProcess="")
	public void selName(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String projectName=request.getParameter("projectName");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = null;
		map.put("proName", projectName);
		List<ProjectInfo> list = projectInfoService.getProjectName(map);
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		for(ProjectInfo projectinfo:list){
			map1 =  new HashMap<String, Object>();
			map1.put("proName", projectinfo.getProName());
			map1.put("code", projectinfo.getCode());
			list1.add(map1);
		}
		//根据ID修改状态
		JSONObject json=null;
		try {
			
			PrintWriter out = null;
			json = new JSONObject();
			json.put("list", list1);
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
