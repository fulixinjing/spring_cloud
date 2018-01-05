package cn.taskSys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Employee;
import cn.taskSys.entity.Org;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.EmployeeService;
import cn.taskSys.service.OrgService;
import cn.taskSys.service.RoleService;
import cn.taskSys.utils.EncodeUtil;

@Controller
@RequestMapping("/sltwin")
public class SltwinAction extends BaseAction {

	private final String UI_SELECT_ROLE_MULTI = "/sltwin/selectMutliRole";
	private final String UI_SELECT_EMPLOYEE_SINGLE = "/sltwin/selectSingleEmployee";
	private final String UI_SELECT_ORG_SINGLE = "/sltwin/selectSingleOrg";

	@Autowired
	private RoleService roleService;

	
	@Autowired
	private OrgService orgService;

	@Autowired
	private EmployeeService employeeService;
	/**
	 * 角色弹出多选对话框页
	 */
	@RequestMapping(value = "/role/multi", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1030001",eventProcess="")
	public ModelAndView selectMutliRole(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(UI_SELECT_ROLE_MULTI);
		List<Role> roles = new ArrayList<Role>();
		
		User user = (User) request.getSession().getAttribute("JX_USERINFO");
		String userId2 = user.getUserId();
		List<Role> lr = roleService.getRoleByUserId(userId2);
		String roleCode = lr.get(0).getRoleCode();
		if(roleCode.equals("admin11")){
			roles = roleService.getAllRole2(null);
		}else{
			roles = roleService.getAllRole(null);
		}
		
		String flag = request.getParameter("flag");
		String userId = request.getParameter("userId");
		if (StringUtils.isNotBlank(userId)) {
			List<Role> userRoles = roleService.getRoleByUserId(new String(userId));
			for (Role role : roles) {
				for (Role r : userRoles) {
					if (role.getId().equals(r.getId())) {
						role.setChecked(true);
						break;
					}
				}
			}
		}
		mv.addObject("flag", flag);
		mv.addObject("roles", roles);
		return mv;
	}

	/**
	 * 机构弹出单选对话框页
	 */
	@RequestMapping(value = "/org/single", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1030004",eventProcess="")
	public String getOrgUserTree(HttpServletRequest request, Model m) {
		List<Org> orgtreelist; 
		StringBuilder str_tree = new StringBuilder();
		str_tree.append("[");
		try {
			orgtreelist = orgService.getOrgTree();
			if (orgtreelist != null && orgtreelist.size() > 0) {
				for (int i = 0; i < orgtreelist.size(); i++) {
					Org org = orgtreelist.get(i);
					if (i == orgtreelist.size() - 1) {
						str_tree.append("{ id:" + org.getOrganId() + ", pId:" + org.getParentid() + ", " + "name:'"
								+ org.getOrganName() + "',open:true}]");
					} else {
						str_tree.append("{ id:" + org.getOrganId() + ", pId:" + org.getParentid() + ", " + "name:'"
								+ org.getOrganName() + "',open:true},");
					}
				}
			} else {
				//提示信息
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		m.addAttribute("pm", str_tree.toString());
		return UI_SELECT_ORG_SINGLE;
	}
	
	
	@RequestMapping(value = "/getUserType", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1030005",eventProcess="")
	@ResponseBody
	public Map<String, String> creditorAuditingSub(@ModelAttribute("orgId") Long orgId,HttpSession session) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer result=new StringBuffer("");
		int orgType=Integer.parseInt(orgService.getOrgType(orgId));
		String codeType="";
		switch (orgType) {
		case 1:
			codeType = EncodeUtil.D_TEAM_USERTYPE;
			break;
		case 2:
			codeType = EncodeUtil.D_MENDIAN_USERTYPE;
			break;
		case 3:
			codeType = "";
			break;
		case 4:
			codeType = "";
			break;
		case 6:
			codeType = EncodeUtil.D_CFSYB_USERTYPE;
			break;	
		case 5:
			codeType = EncodeUtil.D_AREA_USERTYPE;
			break;	
		case 0:
			codeType = EncodeUtil.D_ZONGBU_USERTYPE;
			break;		

		default:
			break;
		}
		List<Dictionary> list=  EncodeUtil.getDictionaryInfo(codeType,"",(List<Dictionary>)session.getAttribute("Globe_Dictionary"));
		for(Dictionary d:list){
			
			result.append(d.getCode());
			result.append("_");
			result.append(d.getName());
			result.append(",");
		}
		String temp = result.toString();
		temp = temp.substring(0,temp.length()-1);
		map.put("success", "true");
		map.put("userType",temp);
		return map;
	}
	/**
	 * 员工弹出单选对话框页
	 */
	@RequestMapping(value = "/employee/single", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1030002",eventProcess="")
	public ModelAndView selectSingleEmployee(@ModelAttribute("employee") Employee employee) {
		ModelAndView mv = new ModelAndView(UI_SELECT_EMPLOYEE_SINGLE);
		try {
			mv.addObject("employees", employeeService.getEmployees(employee));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}	
	@RequestMapping(value = "/employee/single/search", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1030003",eventProcess="")
	public ModelAndView search(@ModelAttribute("employee") Employee employee) throws Exception {
		ModelAndView mv = new ModelAndView(UI_SELECT_EMPLOYEE_SINGLE);
		mv.addObject("employees", employeeService.getEmployees(employee));
		return mv;
	}	
}
