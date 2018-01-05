package cn.taskSys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Menu;
import cn.taskSys.entity.Right;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.RoleRelation;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.mybatis.DataSourceContextHolder;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.LogService;
import cn.taskSys.service.RoleService;
import cn.taskSys.utils.EncodeUtil;

@Controller
@RequestMapping("/role")
public class RoleAction extends BaseAction {

	@Autowired
	private RoleService roleService;
	//@Autowired
	//private RightService rightService;
	@Autowired
	private LogService logservice;
	private final String UI_SELECT_SOURCE_ROLES = "/role/selectSourceRoles";	

	/**
	 * 查询所有的角色
	 */
	@RequestMapping("/allrole")
	@LogAnnotation(eventCode="1029001",eventProcess="")
	public String getAllRole(HttpServletRequest request, Role role, Model m) {
		String source = request.getParameter("source");
		String pqFlag = request.getParameter("pqFlag");
		if (StringUtils.equals(pqFlag, "true")) {
			role = (Role) request.getSession().getAttribute("roleQry");
		} else {
			request.getSession().setAttribute("roleQry", role);
		}

		try {
			HashMap<String, Object> qry = new HashMap<String, Object>();
			int offset = 0;
			Integer pageSize = (Integer) request.getSession().getAttribute("ps");
			if (request.getParameter("pager.offset") != null) {
				offset = Integer.parseInt(request.getParameter("pager.offset"));
			}
			//翻页开始记录
			qry.put("begin", offset);
			qry.put("nowNum", 10);//当前显示几条
			//翻页结束记录
			qry.put("end", offset + pageSize);
			qry.put("role", role);
			qry.put("source", source);
			PagerModel pager = roleService.getPageUser(qry);

			//List<Role> listRole = roleService.getAllRole(role);
			m.addAttribute("pclist", pager);
			m.addAttribute("role", role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "role/roles";
	}

	/**
	 * 添加角色
	 */
	@RequestMapping(value = "/add")
	@LogAnnotation(eventCode="1029002",eventProcess="")
	public ModelAndView add(@ModelAttribute("source") String source) {
		ModelAndView mv = new ModelAndView("role/add");
		mv.addObject("source", 0);
		return mv;
	}

	/**
	 * 获取所有权限
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/allright/{rolecode}")
	@LogAnnotation(eventCode="1029003",eventProcess="")
	public ModelAndView allRight(Model m, @PathVariable String rolecode,HttpSession session) {
		List<Right> listRight = null;
		StringBuilder str_tree = new StringBuilder();
		List<Menu> menuList = roleService.getMenuById(rolecode);
		str_tree.append("[");
		try {
			listRight = roleService.getRights();
			if (listRight != null && listRight.size() > 0) {
				for (int i = 0; i < listRight.size(); i++) {
					Right right = listRight.get(i);
					if (i == listRight.size() - 1) {
						str_tree.append("{ id:" + right.getId() + ", pId:" + right.getMenu_pid() + ", " + "name:'"+ right.getMenu_name() + "',open:true");
						for(Menu mu:menuList){
							if(mu.getId().equals(right.getId())){
								str_tree.append(",checked:true");
							}
						};
						str_tree.append("}]");
					} else {
						str_tree.append("{ id:" + right.getId() + ", pId:" + right.getMenu_pid() + ", " + "name:'"+ right.getMenu_name() + "',open:true");
						for(Menu mu:menuList){
							if(mu.getId().equals(right.getId())){
								str_tree.append(",checked:true");
							}
						};
						str_tree.append("},");
					}
				}
			} else {
				//提示信息
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		m.addAttribute("listright", str_tree.toString());
		m.addAttribute("menuList", menuList);
		ModelAndView mv = new ModelAndView("role/selectRight");
		return mv;
	}

	/**
	 * 添加角色的权限
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addrole", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1029004",eventProcess="")
	public String addRole(Role role, HttpServletRequest request,HttpSession session,RedirectAttributes ra) {
		String rightcodes = request.getParameter("rightCodes");
		String sourceRoleCodes = request.getParameter("sourceCodes");
		String sourceRoleName = request.getParameter("sourceRole");
		String jxSource = request.getParameter("source");
		role.setJxSource(jxSource);
		StringBuilder sb = new StringBuilder();
		if(rightcodes ==""){
			sb=null;
			int t = roleService.add(role,sourceRoleCodes,sourceRoleName);
		}else{
			String rightcode = rightcodes.substring(0,rightcodes.length()-1);
			List<Menu> menu = roleService.getRoleById(rightcode);
			for(int i =0;i<menu.size();i++){
				sb.append(menu.get(i).getPrivileges_code()+",");
			}
			int t = roleService.addRole(role, sb.toString(),sourceRoleCodes,sourceRoleName);
			addMessage(ra, "操作成功！");
		}
		try {
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"角色用户【"+role.getRoleName()+"】新增操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_MODUL_JSXZ);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/role/allrole.do?source="+jxSource;

	}

	/**
	 * 修改角色的权限
	 */
	@RequestMapping("/updaterol/{rolecode}")
	@LogAnnotation(eventCode="1029005",eventProcess="")
	public ModelAndView updaterol(Model m, @PathVariable String rolecode,@ModelAttribute("source") String source) {
		/**
		 * 查询这个角色的信息，用于回显
		 */
		Role role = roleService.getRoleToUpdate(rolecode);
		/**
		 * 查询这个角色的权限，
		 */
		StringBuffer sb = new StringBuffer();
		StringBuilder str_tree = new StringBuilder();
		str_tree.append("[");
		try {
			List<Menu> menuList = roleService.getMenuById(rolecode);
			if (menuList != null && menuList.size() > 0) {
				for (int i = 0; i < menuList.size(); i++) {
					Menu right = menuList.get(i);
					if (i == menuList.size() - 1) {
						str_tree.append("{ id:" +"'"+ right.getId() +"'"+ ", " + "name:"+ "'"+right.getMenu_name()+"'");
						str_tree.append("}]");
					} else {
						str_tree.append("{ id:" +"'"+ right.getId() +"'"+ ", " + "name:"+"'"+ right.getMenu_name()+"'");
						str_tree.append("},");
					}
				}
			} else {
				//提示信息
				str_tree.append("]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Right> rightlist =roleService.getRightByRoleCode(rolecode);
		for (int i = 0; i < rightlist.size(); i++) {

			Right right = rightlist.get(i);
			if (right != null) {
				if (StringUtils.isNotEmpty(right.getRightName()) && StringUtils.isNotBlank(right.getRightName()))
					sb.append(right.getRightCode()).append(",");

			}
		}
		String strsb = sb.toString();
		if (StringUtils.isNotEmpty(strsb) && StringUtils.isNotBlank(strsb)) {
			strsb = strsb.substring(0, strsb.length() - 1);
		}
		List<RoleRelation> roleRelationList = roleService.getRoleRelationById(rolecode);
		StringBuilder strRoleReation = new StringBuilder();
		strRoleReation.append("[");
		if (roleRelationList != null && roleRelationList.size() > 0) {
			for (int a = 0; a < roleRelationList.size(); a++) {
				RoleRelation roleRelation = roleRelationList.get(a);
				if (a == roleRelationList.size() - 1) {
					strRoleReation.append("{ id:" +"'"+ roleRelation.getSourceRoleCode() +"'"+ ", " + "name:"+ "'"+roleRelation.getSourceRoleName()+"'");
					strRoleReation.append("}]");
				} else {
					strRoleReation.append("{ id:" +"'"+ roleRelation.getSourceRoleCode() +"'"+ ", " + "name:"+"'"+ roleRelation.getSourceRoleName()+"'");
					strRoleReation.append("},");
				}
			}
		} else {
			//提示信息
			strRoleReation.append("]");
		}		
		m.addAttribute("rightlist", rightlist);
		m.addAttribute("role", role);
		m.addAttribute("rightlistcode", strsb);
		m.addAttribute("menuList", str_tree);
		m.addAttribute("roleRelationList",strRoleReation);
		ModelAndView mv = new ModelAndView("role/updateRole");
		mv.addObject("source", 0);
		return mv;
	}

	@RequestMapping(value="/updaterole",method = RequestMethod.POST)
	@LogAnnotation(eventCode="1029006",eventProcess="")
	public String updateRole(Role role,HttpServletRequest request,HttpSession session,RedirectAttributes ra){
		String source = request.getParameter("source");
		String rightcodes=request.getParameter("rightCodes");
		String sourceRoleCodes = request.getParameter("sourceCodes");
		String sourceRoleName = request.getParameter("sourceRole");
		role.setJxSource(source);
		if(rightcodes==null||"".equals(rightcodes)){
			rightcodes="";
		}
		String rightcode = rightcodes.substring(0,rightcodes.length()-1);
		StringBuilder sb = new StringBuilder();
		List<Menu> menu = roleService.getRoleById(rightcode);
		for(int i =0;i<menu.size();i++){
			sb.append(menu.get(i).getPrivileges_code()+",");
		}
		roleService.updateRole(role,sb.toString(),sourceRoleCodes,sourceRoleName);
		try {
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"角色用户【"+role.getRoleName()+"】修改操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_MODUL_JSXG);
			addMessage(ra, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			try{
				logservice.saveLog(EncodeUtil.LOG_ERROR, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"角色用户【"+role.getRoleName()+"】修改操作失败！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_JSXG);
				addMessage(ra, "操作失败！");
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return "redirect:/role/allrole.do?source="+source;


	}

	/**
	 * 删除角色
	 */
	@RequestMapping("/delrole/{rolecode}/{roleName}")
	@LogAnnotation(eventCode="1029007",eventProcess="")
	public String delRole(Role role, @PathVariable String rolecode, @PathVariable String roleName,HttpSession session,HttpServletRequest request,RedirectAttributes ra) {
		    String source = request.getParameter("source");
			role.setRoleCode(rolecode);
			int d = roleService.delRole(role);
			try {
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"角色用户【"+roleName+"】删除操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_JSSC);
				addMessage(ra, "操作成功！");
			} catch (Exception e) {
				e.printStackTrace();
				try{
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"角色用户【"+roleName+"】删除操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_MODUL_JSSC);
					addMessage(ra, "操作失败！");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		return "redirect:/role/allrole.do?source="+source;
	}

	// ajax请求 检查roleCode是否存在
	@RequestMapping("/check/roleCode")
	@LogAnnotation(eventCode="1029008",eventProcess="")
	public void checkOnlyLoginName(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleCode = request.getParameter("roleCode");
			Role role = roleService.checkRoleCode(roleCode);
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(role == null ? "true" : "false");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/getParentRole", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1029009",eventProcess="")
	@ResponseBody
	//在新增角色中，根据角色来源获取上一级角色下拉选择中的内容
	public Map<String, String> getParentRole(@ModelAttribute("jxSource") String jxSource,HttpSession session) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> jxMap = new HashMap<String, Object>();
		jxMap.put("jxSource",jxSource);
		StringBuffer result=new StringBuffer("");
		List<Role> parnentRoleList = roleService.getAllParentRole(jxMap);
		
		for(int i =0;i<parnentRoleList.size();i++){		
			result.append(parnentRoleList.get(i).getRoleCode());
			result.append("_");
			result.append(parnentRoleList.get(i).getRoleName());
			result.append(",");
		}
		String temp = result.toString();
		temp = temp.substring(0,temp.length()-1);
		map.put("success", "true");
		map.put("parentRoleCode",temp);
		return map;
	}
	/**
	 * 来源角色弹出多选对话框页
	 */
	@RequestMapping("/source")
	@LogAnnotation(eventCode="1029010",eventProcess="")
	public ModelAndView selectMutliRole(HttpServletRequest request) {
		String source = request.getParameter("source");
		ModelAndView mv = new ModelAndView(UI_SELECT_SOURCE_ROLES);
		if("1".equals(source)){
			List<Role> roles = roleService.getAllRoleHj(null);	
			mv.addObject("roles", roles);
		}
		return mv;
	}	
}
