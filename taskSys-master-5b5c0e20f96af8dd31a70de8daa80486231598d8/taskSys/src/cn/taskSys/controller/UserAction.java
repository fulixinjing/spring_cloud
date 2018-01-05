package cn.taskSys.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.taskSys.base.PageView;
import cn.taskSys.base.action.BaseAction;
import cn.taskSys.common.cache.CacheMgr;
import cn.taskSys.common.cache.UserCachedEntity;
import cn.taskSys.entity.Attendance;
import cn.taskSys.entity.Org;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.EmployeeService;
import cn.taskSys.service.IAttendanceService;
import cn.taskSys.service.IUserService;
import cn.taskSys.service.LogService;
import cn.taskSys.service.OrgService;
import cn.taskSys.service.RoleService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.EncodeUtil;
import cn.taskSys.utils.ExportExcelUtil;
import cn.taskSys.utils.FileUtils;
import cn.taskSys.utils.ImportExcelUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UtilValidate;

@Controller
@RequestMapping(value = "/user")
public class UserAction  extends BaseAction{

	private final String UI_EPW = "/user/editUserPassword";
	private final String UI_LIST = "/admin/adminuList";
	private final String UI_ADD = "/user/addUser";
	private final String UI_EDIT = "/user/editUser";
	
	@Autowired
	private IAttendanceService attendanceService;
	
	@Autowired
	private IUserService userService;
	@Autowired
	private LogService logservice;
	@Autowired
	private OrgService orgService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/epw4p", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032009",eventProcess="")
	public ModelAndView editUserPasswordForPerson(HttpServletRequest request,HttpSession session) {
		ModelAndView mv = new ModelAndView(UI_EPW);
		User principal = (User)session.getAttribute("JX_USERINFO");
	    mv.addObject("userId", principal.getUserId());
		return mv;
	}	
    //密码修改功能
	@RequestMapping(value = "/epw/{userId}", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032010",eventProcess="")
	public ModelAndView editUserPassword(@PathVariable("userId") Long userId) {
		ModelAndView mv = new ModelAndView(UI_EPW);
		mv.addObject("userId", userId);
		
		return mv;
	}
	
	@RequestMapping(value = "/upw")
	@LogAnnotation(eventCode="1032011",eventProcess="")
	public void updateUserPassword(@ModelAttribute("user") User user,HttpSession session,RedirectAttributes ra,HttpServletRequest request, HttpServletResponse response) {
		try {
		    
			userService.updateUserPassword(user);
			user = userService.getUserById(user.getUserId());
			//mv.addObject("msg", "修改成功！");
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【"+user.getUserName()+"】密码修改操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_MODUL_YHXG);
					PrintWriter out = null;
					response.setContentType("text/plain");
					out = response.getWriter();
					out.print(1);
		} catch (Exception e) {
			try {
				logservice.saveLog(EncodeUtil.LOG_ERROR, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+user.getUserName()+"】密码修改操作失败！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_YHXG);
						PrintWriter out = null;
						response.setContentType("text/plain");
						out = response.getWriter();
						out.print(2);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//mv.addObject("msg", "修改失败！");
			e.printStackTrace();
		}
		//ra.addAttribute("menu", "10000000000001");
		//return "redirect:/index.do";
	}
	
	@RequestMapping(value = "/upheadportrait")
	@ResponseBody
    public Map<String, Object> upheadportrait(@ModelAttribute("user") User user,HttpSession session, MultipartHttpServletRequest file,HttpServletRequest request) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    try {
	        MultipartFile file2 = file.getFile("file");
            byte[] bytes = file2.getBytes();  

              //获取上传文件的名称 
                String fileName = file2.getOriginalFilename(); 
                
              //获取文件得保存路径 
                File dirPath = new File(request.getSession().getServletContext().getRealPath("/opt/upload/headportrait"));  
                if (!dirPath.exists()) {
                    dirPath.mkdirs();  
                }
                String sep = System.getProperty("file.separator"); 
                File uploadedFile = new File(dirPath + sep + fileName);  

                user.setHead_portrait("/opt/upload/headportrait/" + file2.getOriginalFilename());
                
                FileCopyUtils.copy(bytes, uploadedFile);  

                userService.updateUserHeadPortrait(user);
                map.put("msg", "修改成功");
            
                user = userService.getUserById(user.getUserId());
                logservice.saveLog(EncodeUtil.LOG_INFO, 
                        ((User)session.getAttribute("JX_USERINFO")).getUserName(),
                        ((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
                        "用户【"+user.getUserName()+"】头像上传成功！",
                        ((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
                        EncodeUtil.LOG_MODUL_YHXG);
            

                } catch (Exception e) {
                    try {
                        logservice.saveLog(EncodeUtil.LOG_ERROR, 
                                ((User)session.getAttribute("JX_USERINFO")).getUserName(),
                                ((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
                                "用户【"+user.getUserName()+"】头像上传失败！",
                                ((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
                                EncodeUtil.LOG_MODUL_YHXG);
        
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    map.put("msg", "修改失败");
                    e.printStackTrace();
                }
        return map;
    }
	
	// ajax请求
	@RequestMapping("/check/password")
	@LogAnnotation(eventCode="1032013",eventProcess="")
	public void checkCorrectPassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("userId");
			User user = userService.getUserById(new String(id));
			String oldPassword = request.getParameter("oldPassword");
			oldPassword = EncodeUtil.encode(oldPassword.getBytes());

			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(oldPassword.equals(user.getPassword()) ? "true" : "false");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/list")
	@LogAnnotation(eventCode="1032001",eventProcess="")
	public ModelAndView listPage(@ModelAttribute("user") User user, HttpServletRequest request,Model m,RedirectAttributes ra) throws Exception {
	    Map<String,Object> map = new HashMap<String, Object>();
		String pqFlag = request.getParameter("pqFlag");
		
		
		if (StringUtils.equals(pqFlag, "true")) {
			user = (User) request.getSession().getAttribute("userQry");
		} else {
			request.getSession().setAttribute("userQry", user);
		}

		ModelAndView mv = new ModelAndView(UI_LIST);
		String flag = request.getParameter("param5");
		
		if("1".equals(flag)){ 
			user.setLoginName(request.getParameter("param1"));
			user.setUserName(request.getParameter("param2"));
			user.setDepartment_id(request.getParameter("param3"));
			user.setCompany_id(request.getParameter("param4"));
		}
		map.put("user", user);
		mv.addObject("user", user);
		mv.addObject("orgId", user.getOrgId());
		mv.addObject("nowDate",DateUtil.DateToString(new Date(),"yyyy-MM-dd"));
		m.addAttribute(mv);
		
		// 用于查询条件回显
		if (StringUtils.isNotBlank(user.getEmpCode())) {
			mv.addObject("employee", employeeService.getEmployee(user.getEmpCode()));
		}

		// 用于查询条件回显
		if (StringUtils.isNotBlank(user.getOrgId())) {
			List<Org> orgs = orgService.getOrgInfo(user.getOrgId());
			mv.addObject("org", orgs != null && orgs.size() > 0 ? orgs.get(0) : new Org());
		}

		try {
			
			
			User u = (User) request.getSession().getAttribute("JX_USERINFO");
			String userId = u.getUserId();
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
//				if (role.getRoleCode().equals("staff") || role.getRoleCode().equals("director") || role.getRoleCode().equals("boss") || role.getRoleCode().equals("SUPER") || role.getRoleCode().equals("admin11")) {
				if(!"".equals(role.getRoleCode()) && role.getRoleCode()!=null){
					userRole = role.getRoleCode();
					break;
				}
			}
			
			HashMap<String, Object> qry = new HashMap<String, Object>();
			if (!StringUtil.isNUll(userRole) && userRole.equals("admin11")) {     //普通管理员登录时  用户管理模块  不能查到超级管理员
				map.put("lname", "superadmin");
			}
			String maxResult = request.getParameter("maxResult");
			String page = request.getParameter("page");
			map.put("maxResult", maxResult);
			map.put("page", page);
			PageView<Map<String,Object>> pageView  = new PageView<Map<String,Object>>();
			String flag1 = StringUtil.nvlString(request.getParameter("flag1"));
            if (!flag1.equals("1")) {//flag1==1默认不加载任务列表
                pageView = userService.getUserListpageView(map,user);
            }
			
			
			
			//PagerModel pager = userService.getPageUser(qry);
			
			List<Map<String,String>> list = userService.getOrgInfo();
			
			//新增用户下拉菜单
			List<Map<String,String>> mlist = userService.getOrgInfo();
			
			
			
			m.addAttribute("pageView", pageView);
			//mv.addObject("pclist", pager);
			mv.addObject("user", user);
			mv.addObject("list", list);
			mv.addObject("mlist", mlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/check/listSys")
	public void sysTime(HttpServletRequest request,HttpServletResponse response){
		String loginName = request.getParameter("loginName");
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("acEmpCode",loginName);
		JSONObject json=new JSONObject();
		try {
			List<Attendance> list = attendanceService.getPreAttendance(maps);
			for(Attendance attendance:list){
				json.put("acgz", attendance.getAcgz());
				json.put("starttime", attendance.getAcStartTime());
				json.put("endtime", attendance.getAcEndTime());
			}
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	//用户维护中的新增人员
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032002",eventProcess="")
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView(UI_ADD);
		List<Map<String,String>> list = userService.getOrgInfo();
		mv.addObject("list",list);
		return mv;
	}
	
	//用户维护中的编辑操作
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1032003",eventProcess="")
	public void edit(String userId,HttpSession session,HttpServletResponse response) throws Exception {
		//ModelAndView mv = new ModelAndView(UI_EDIT);
		JSONObject  json=new JSONObject();
		System.out.println(userId);
		User user = userService.getUserWithRoles(userId);
		List<Map<String,String>> list = userService.getOrgInfo();
		json.put("userId", user.getUserId());
		json.put("userName", user.getUserName());
		json.put("loginName", user.getLoginName());
		json.put("department_id", user.getDepartment_id());
		json.put("company_id", user.getCompany_id());
		json.put("team_id", user.getTeam_id());
		json.put("position_id", user.getPosition_id());
		json.put("jobId", user.getJobId());
		json.put("employed_date", user.getEmployed_date());
		json.put("email", user.getEmail());
		json.put("speciality", user.getSpeciality());
		List<Role> roles = user.getRoles();
		List<Map> maplist=new ArrayList<Map>();
		for(int i=0;i<roles.size();i++){
			Role ro=roles.get(i);
			Map map=new HashMap();
			map.put("roleName", ro.getRoleName());
			map.put("id", ro.getId());
			maplist.add(map);
		}
		json.put("map", maplist);
		json.put("list", list);
		response.getWriter().write(json.toString());
		/*mv.addObject("user", user);
		mv.addObject("list",list);
		int orgType=Integer.parseInt(orgService.getOrgType(StringUtil.StringToLong(user.getOrgId())));
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
		case 5:
			codeType = EncodeUtil.D_AREA_USERTYPE;
			break;	
		case 0:
			codeType = EncodeUtil.D_ZONGBU_USERTYPE;
			break;		

		default:
			break;
		}
		
		if (StringUtils.isNotBlank(user.getEmpCode())) {
			//mv.addObject("employee", employeeService.getEmployee(user.getEmpCode()));
		}

		if (StringUtils.isNotBlank(user.getOrgId())) {
			//List<Org> orgs = orgService.getOrgInfo(user.getOrgId());
			//mv.addObject("org", orgs != null && orgs.size() > 0 ? orgs.get(0) : new Org());
		}
		return mv;*/
	}
	//用户维护中的停用功能
	@RequestMapping(value = "/disable/{userId}", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032006",eventProcess="")
	public String disableUser(@PathVariable("userId") String userId) {
		try {
			userService.disableUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/list.do";
	}
	//用户维护中的重置密码功能
	@RequestMapping(value = "/rpw/{userId}/{userName}", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032008",eventProcess="")
	public String resetUserPassword(@PathVariable("userId") String userId,@PathVariable("userName") String userName,HttpServletRequest request,HttpSession session,RedirectAttributes ra) {
		String param1 = StringUtil.nvlString(request.getParameter("param1"));
		String param2 = StringUtil.nvlString(request.getParameter("param2"));
		String param3 = StringUtil.nvlString(request.getParameter("param3"));
		String param4 = StringUtil.nvlString(request.getParameter("param4"));
		try {
			userService.resetUserPassword(userId);
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【"+userName+"】密码重置操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_MODUL_YHCZ);
			addMessage(ra, "密码成功重置为默认密码！");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				logservice.saveLog(EncodeUtil.LOG_ERROR, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+new String(userName.getBytes("ISO8859_1"),"UTF-8")+"】密码重置操作失败！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_YHCZ);
				addMessage(ra, "密码重置失败！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return "redirect:/user/list.do?param1="+param1+"&param2="+param2+"&param3="+param3+"&param4="+param4+"&param5="+1;
  
	}
	//用户维护中的删除功能
	@RequestMapping(value = "/delete/{userId}/{userName}", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032005",eventProcess="")
	public String deleteUser(@PathVariable("userId") String userId,@PathVariable("userName") String userName,HttpSession session) {
		try {
			userService.deleteUser(userId);
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【"+userName+"】删除操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_MODUL_YHSC);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				logservice.saveLog(EncodeUtil.LOG_ERROR, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+userName+"】删除操作失败！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_YHSC);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return "redirect:/user/list.do";
	}
	//用户维护中新增人员的保存功能
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1032004",eventProcess="")
	public String save(@ModelAttribute("user") User user, HttpServletRequest request,HttpSession session,RedirectAttributes ra) {
		String department_id = StringUtil.nvlString(request.getParameter("did"));
		
		String param1 = StringUtil.nvlString(request.getParameter("param1"));
		String param2 = StringUtil.nvlString(request.getParameter("param2"));
		String param3 = StringUtil.nvlString(request.getParameter("param3"));
		String param4 = StringUtil.nvlString(request.getParameter("param4"));
		String sta = "修改";
		if(user.getUserId()==null){
			sta="新增";
		}
		try {
			String nowDate=getDate(0); //系统当前时间
			user.setCreates_date(nowDate);
			user.setLastModifyTime(nowDate);
			User principal = (User) request.getSession().getAttribute("loginuser");
			//user.setLastModifyBy(principal.getId());
			user.setLastModifyBy((long) 1);
			userService.saveUser(user);
			logservice.saveLog(EncodeUtil.LOG_INFO, 
					((User)session.getAttribute("JX_USERINFO")).getUserName(),
					((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
					"用户【"+user.getUserName()+"】"+sta+"操作成功！",
					((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
					EncodeUtil.LOG_MODUL_YHXZ);
			addMessage(ra, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				logservice.saveLog(EncodeUtil.LOG_ERROR, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+user.getUserName()+"】"+sta+"操作失败！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_YHXZ);
				addMessage(ra, "操作失败！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		ra.addAttribute("department_id", department_id);
		ra.addAttribute("param1", param1);
		ra.addAttribute("param2", param2);
		ra.addAttribute("param3", param3);
		ra.addAttribute("param4", param4);
		ra.addAttribute("param5", 1);
		return "redirect:/user/list.do";
	}
	// ajax请求
	@RequestMapping("/check/loginName")
	@LogAnnotation(eventCode="1032012",eventProcess="")
	public void checkOnlyLoginName(HttpServletRequest request, HttpServletResponse response) {
		try {
			String loginName = request.getParameter("loginName");
			User user = userService.getUserByLoginName(loginName);
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(user == null ? "true" : "false");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	@RequestMapping(value = "/enable/{userId}", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032007",eventProcess="")
	public String enableUser(@PathVariable("userId") String userId) {
		try {
			userService.enableUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/list.do";
	}	
	
	/**
     * 添加在线用户
     *
     * @param userLogin
     * @param host
     * @since 2015.03.10
     */
	@RequestMapping(value = "/getUserOnline", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032008",eventProcess="")
    private String getUserOnline(HttpSession session,HttpServletRequest req, Model m) {
		String dbSource=session.getAttribute("dbSource").toString();
		List<UserCachedEntity<?>> user= CacheMgr.getUserListOnLine();
		List<UserCachedEntity<Object>> returnList=new ArrayList<UserCachedEntity<Object>>();

		for(int i=0;i<user.size();i++){
			UserCachedEntity<Object> u=new UserCachedEntity<Object>();
			if(!"0".equals(dbSource)&&dbSource.equals(user.get(i).getSource())){
				u=(UserCachedEntity<Object>) user.get(i);
			}
			if("0".equals(dbSource)){
				u=(UserCachedEntity<Object>) user.get(i);
			}
			returnList.add(u);
		}
		req.setAttribute("returnList", returnList);
		req.setAttribute("onLineNum", CacheMgr.getUserListSizeOnLine());
		return "/user/onLineUser";
    }
	
	//用户维护中的删除功能
	@RequestMapping(value = "/clearUser/{userId}/{userName}", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1032009",eventProcess="")
	public String clearUser(@PathVariable("userId") String userId,@PathVariable("userName") String userName,HttpServletRequest req,RedirectAttributes ra) {
		try {
//			CacheMgr.removeUserOnline(userId);//去除在线用户
//			List onlineUserList = (List) req.getSession().getAttribute("JX_USERINFO");
//			onlineUserList.remove(userId);
			//Subject currentUser = SecurityUtils.getSubject();
			
			//Session session = currentUser.getSession();
//			Map<Object,User> mapSession=new HashMap<Object, User>();
//			mapSession=(Map<Object,User>)session.getAttribute("JX_USERINFO");
//			
//			User u=mapSession.get(userId);
//			
			//currentUser.logout();//只能退出当前登录用户-把xml的时间改了
			
			
			String str = "abcdefghijklmnopqrs";
	        String str1 = "";
	        for (int i = 0; i < 106; i++) {
	            str1 = str1 + str;
	        }
	        System.out.println(str1.length());
	        
			addMessage(ra, "操作成功！有问题");
		} catch (Exception e1) {
			
			addMessage(ra, "操作失败！");
			e1.printStackTrace();
			
		}
		return "redirect:/user/getUserOnline.do";
	}
	
	// ajax请求
	//新增用户页面，根据所选部门联动带出部门团队
		@RequestMapping("/change/team")
		@LogAnnotation(eventCode="1032112",eventProcess="")
		public void changeTeam(HttpServletRequest request, HttpServletResponse response) {
			try {
				String code = StringUtil.nvlString(request.getParameter("code"));
				List list = userService.getTeamByCode(code);
				JSONObject  json=new JSONObject();
				//String json2 = JSONArray.fromObject(list).toString();
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				json.put("list", list);
				
				out.write(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		
		//用户维护中的批量删除功能
		@RequestMapping(value = "/delBatchUser/{ids}")
		@LogAnnotation(eventCode="1032113",eventProcess="")
		public String delBatchUser(@PathVariable("ids") String ids,HttpServletRequest request,HttpSession session,RedirectAttributes ra) {
			//String ids = request.getParameter("ids");
			String param1 = StringUtil.nvlString(request.getParameter("param1"));
			String param2 = StringUtil.nvlString(request.getParameter("param2"));
			String param3 = StringUtil.nvlString(request.getParameter("param3"));
			String param4 = StringUtil.nvlString(request.getParameter("param4"));
			String[] split = ids.split(",");
			List list = Arrays.asList(split);
			try {
				userService.batchDeleteUser(list);
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】删除操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_YHSC);
				addMessage(ra, "操作成功！");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】删除操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_MODUL_YHSC);
					addMessage(ra, "操作失败！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return "redirect:/user/list.do?param1="+param1+"&param2="+param2+"&param3="+param3+"&param4="+param4+"&param5="+1;
		}
		
		//用户维护中的批量停用功能
				@RequestMapping(value = "/stopBatchUser/{ids}")
				@LogAnnotation(eventCode="1032114",eventProcess="")
				public String stopBatchUser(@PathVariable("ids") String ids,HttpServletRequest request,HttpSession session,RedirectAttributes ra) {
					//String ids = request.getParameter("ids");
					
					String param1 = StringUtil.nvlString(request.getParameter("param1"));
					String param2 = StringUtil.nvlString(request.getParameter("param2"));
					String param3 = StringUtil.nvlString(request.getParameter("param3"));
					String param4 = StringUtil.nvlString(request.getParameter("param4"));
					String[] split = ids.split(",");
					List list = Arrays.asList(split);
					try {
						userService.batchStopUser(list);
						logservice.saveLog(EncodeUtil.LOG_INFO, 
								((User)session.getAttribute("JX_USERINFO")).getUserName(),
								((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
								"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】停用操作成功！",
								((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
								EncodeUtil.LOG_MODUL_YHSC);
						addMessage(ra, "操作成功！");
					} catch (Exception e) {
						e.printStackTrace();
						try {
							logservice.saveLog(EncodeUtil.LOG_ERROR, 
									((User)session.getAttribute("JX_USERINFO")).getUserName(),
									((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
									"用户【"+((User)session.getAttribute("JX_USERINFO")).getUserName()+"】停用操作失败！",
									((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
									EncodeUtil.LOG_MODUL_YHSC);
							addMessage(ra, "操作失败！");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					return "redirect:/user/list.do?param1="+param1+"&param2="+param2+"&param3="+param3+"&param4="+param4+"&param5="+1;
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
				
				
				// 新增时，校验用户名是否重复
				@RequestMapping("/checkLoginName")
				@LogAnnotation(eventCode="4032013",eventProcess="")
				public void checkLoginName(HttpServletRequest request, HttpServletResponse response) {
					try {
						String loginName = request.getParameter("loginName");
						int r = userService.checkLoginName(loginName);

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
				 * 用户信息导入
				 * @author 
				 * @param m
				 * @param session
				 * @return
				 */
				@RequestMapping(value="/upfile")
				@LogAnnotation(eventCode="5011022",eventProcess="")
				public String upScoreParam(Model m,HttpSession session ) { 
					return "user/excelUpUsersInfo";
				}
				
				/**
				 * 用户信息上传
				 * @param response
				 * @param request
				 * @param file
				 * @throws IOException
				 */
				@RequestMapping(value="/uploadFile",method=RequestMethod.POST)  
				@LogAnnotation(eventCode="5011023",eventProcess="")
			    public void uploadFile(HttpServletResponse response,HttpServletRequest request,
			    		@RequestParam(value="file", required=false) MultipartFile file) throws IOException{
			        byte[] bytes = file.getBytes();  
			        String uploadDir = request.getRealPath("/")+"upload";  
			        File dirPath = new File(uploadDir);  
			        if (!dirPath.exists()) {
			            dirPath.mkdirs();  
			        }  
			        String sep = System.getProperty("file.separator"); 
			        File uploadedFile = new File(uploadDir + sep  
			                + file.getOriginalFilename());  
			        FileCopyUtils.copy(bytes, uploadedFile);  
			        
			        response.getWriter().write("true");  
			    }
				
				
				/**
				 * 导入用户信息
				 * @param request
				 * @param m
				 * @return
				 */
				@ResponseBody  
				@RequestMapping("/getExcelInfoList")
				@LogAnnotation(eventCode="5011008",eventProcess="")
				public Map<String, Object> getExcelInfoList(@ModelAttribute("fileName")String fileName,HttpServletRequest request,RedirectAttributes ra,Model m){
					List<Map<String,String>> bmList = userService.getBuMenList();		//查出所有部门、团队、职位、岗位集合 
					List<Map<String,String>> roleList = userService.getRoleList();		//查出角色集合 
					//用户名不能重复
			    	List<Map<String,String>> loginNameList  = userService.getLoginNameList();
					Map<String, Object> map = new HashMap<String, Object>();
					StringBuffer message = new StringBuffer();
					User user = null;
					Role role = null;
					try {
						fileName = URLDecoder.decode(fileName, "UTF-8");
						
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					String uploadDir = request.getRealPath("/")+"upload";
					String sep = System.getProperty("file.separator");
				    String path = uploadDir + sep + fileName;
					List<String> list = null;
			        try {
			            list =ImportExcelUtil.exportListFromExcelCustom(new File(path), 0, 2);
			           for(int i=0;i<list.size();i++){
			        	   String nowDate=getDate(0); //系统当前时间
			        	   user=new User();
			        	   role = new Role();
			        	   List<Role> roles = new ArrayList<Role>();
			            	String strOld = list.get(i);
			            	String[] arrStr = strOld.split("\\|",-1);
			            	String loginName = arrStr[1];     //用户名（工号）
			            	String userName = arrStr[2];			//姓名
			            	String email = arrStr[3];		//邮箱
			            	String department_Name = StringUtil.nvlString(arrStr[4]);		//所属部门名称
			            	String team_Name = StringUtil.nvlString(arrStr[5]);		//所属团队名称
			            	String position_Name = StringUtil.nvlString(arrStr[6]);			//职位名称
			            	String postName = StringUtil.nvlString(arrStr[7]);		//岗位名称
			            	String employed_date = arrStr[8];		//入职时间
			            	String roleName = arrStr[9];		//角色名称
			            	String company_Name = StringUtil.nvlString(arrStr[10]);		//所属公司名称
			            	
			            	for (int j = 0; j < loginNameList.size(); j++) {
								if(loginNameList.get(j).get("login_name").equals(loginName)){
									message.append("第"+(i+1)+"条中【用户名】数据库中已存在,不需要再导入，请修改后再导入" );
				            		message.append("\n");
				            		break;
								}
							}
			            	
			            /*	if(!checkEmail(email)){
			            		message.append("第"+(i+1)+"条中【用户名(邮箱)】格式不正确，请修改后再导入" );
			            		message.append("\n");
			            		break;
			            	}*/
			            	
			            	if(UtilValidate.isEmpty(loginName)){
			            		message.append("第"+(i+1)+"条中【用户名】不能为空" );
			            		message.append("\n");
			            	}
			            	if(UtilValidate.isEmpty(userName)){
			           			message.append("第"+(i+1)+"条中【姓名】不能为空" );
			           			message.append("\n");
			           		}
			           		if(UtilValidate.isEmpty(email)){
			           			message.append("第"+(i+1)+"条中【个人邮箱】不能为空" );
			           			message.append("\n");
			           		}
			           		if(UtilValidate.isEmpty(employed_date)){
			           			message.append("第"+(i+1)+"条中【入职时间】不能为空" );
			           			message.append("\n");
			           		}
			           		if(UtilValidate.isEmpty(roleName)){
			           			message.append("第"+(i+1)+"条中【角色】不能为空" );
			           			message.append("\n");
			           		}
			           		
			           	 if(UtilValidate.isEmpty(message.toString()) || "suc".equals(message.toString())){
			           		user.setLoginName(loginName.trim());
			           		user.setUserName(userName.trim());
			           		user.setEmail(email.trim());
			           		user.setEmployed_date(employed_date.trim());
			           		user.setLastModifyTime(nowDate);
			           		user.setCreates_date(nowDate);
			           
			           		//通过 公司名称  查出 公司编码
			           		for (int b = 0; b < bmList.size(); b++) {
			           			if(!UtilValidate.isEmpty(company_Name)){
			           				if(bmList.get(b).get("type_code").equals("00")){
					        			if(bmList.get(b).get("name").equals(company_Name.trim())){
					        				user.setCompany_id(bmList.get(b).get("code"));
					        				break;
					        			}
			           				}
			           			}else{
			           				break;
			           			}
			        		}
			           		
			           		//通过 部门名称  查出 部门编码
			           		for (int b = 0; b < bmList.size(); b++) {
			           			if(!UtilValidate.isEmpty(department_Name)){
			           				if(bmList.get(b).get("type_code").equals("01")){
					        			if(bmList.get(b).get("name").equals(department_Name.trim())){
					        				user.setDepartment_id(bmList.get(b).get("code"));
					        				break;
					        			}
			           				}
			           			}else{
			           				break;
			           			}
			        		}
			           	//通过 团队名称  查出 团队编码
			           		for (int b1 = 0; b1 < bmList.size(); b1++) {
			           			if(!UtilValidate.isEmpty(team_Name)){
			           				if(bmList.get(b1).get("type_code").equals("02")){
					        			if(bmList.get(b1).get("name").equals(team_Name.trim())){
					        				user.setTeam_id(bmList.get(b1).get("code"));
					        				break;
					        			}
			           				}
			           			}else{
			           				break;
			           			}
			        		}
			           	//通过 职位名称  查出 职位编码
			           		for (int b2 = 0; b2< bmList.size(); b2++) {
			           			if(!UtilValidate.isEmpty(position_Name)){
			           				if(bmList.get(b2).get("type_code").equals("03")){
					        			if(bmList.get(b2).get("name").equals(position_Name.trim())){
					        				user.setPosition_id(bmList.get(b2).get("code"));
					        				break;
					        			}
			           				}
			           			}else{
			           				break;
			           			}
			        		}
			           	//通过 岗位名称  查出 岗位编码
			           		for (int b3 = 0; b3 < bmList.size(); b3++) {
			           			if(!UtilValidate.isEmpty(postName)){
			           				if(bmList.get(b3).get("type_code").equals("04")){
					        			if(bmList.get(b3).get("name").equals(postName.trim())){
					        				user.setJobId(bmList.get(b3).get("code"));
					        				break;
					        			}
			           				}
			           			}else{
			           				break;
			           			}
			        		}
			           		
			           	//通过 角色名称  查出 角色id
			           		for (int r = 0; r < roleList.size(); r++) {
			        			if(roleList.get(r).get("role_name").equals(roleName.trim())){
			        				role.setId(roleList.get(r).get("id"));
			        				roles.add(role);
			        				user.setRoles(roles);
			        				break;
			        			}
			        		}
			               
			           		userService.saveUser(user);
			                	if(i == list.size()-1){
			                		message.append("suc" );                				
			                	}
			                	}
			           }
			        } catch (Exception e) { 
			        	e.printStackTrace();
			        }  
			        map.put("message", message.toString());
					return map;
				}
				
				private String trim(String loginName) {
					// TODO Auto-generated method stub
					return null;
				}
				/**
			     * 验证邮箱
			     * @param email
			     * @return
			     */
			    public static boolean checkEmail(String email){
			        boolean flag = false;
			        try{
			                String check = "^[a-z0-9]([a-z0-9]*[-_\\.]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$";
			                Pattern regex = Pattern.compile(check);
			                Matcher matcher = regex.matcher(email);
			                flag = matcher.matches();
			            }catch(Exception e){
			                flag = false;
			            }
			        return flag;
			    }
			    
			    @SuppressWarnings("unchecked")
			    @RequestMapping(value="/findOnLineUser")
				public ModelAndView findOnLineUser(HttpSession session){
			    	List<User> onLineUserList = new ArrayList<User>();
			    	ServletContext servletContext = session.getServletContext();
			    	Set<HttpSession> sessions = (Set<HttpSession>) servletContext.getAttribute("sessions");
			    	Iterator<HttpSession> it = sessions.iterator();
			    	while(it.hasNext()){
			    		HttpSession httpSession = it.next();
			    		User user = (User) httpSession.getAttribute("JX_USERINFO");
			    		if (user!=null) {
			    			onLineUserList.add(user);
						}
			    	}
			    	ModelAndView mv = new ModelAndView("/user/onLineUserList");
			    	mv.addObject("onLineUserList", onLineUserList);
					return mv;
			    	
			    }
			    
			    /**
			     * 
			     * @param request
			     * @param response
			     */
				@RequestMapping("/exportUser")
				@LogAnnotation(eventCode="5011009",eventProcess="")
				public void exportUser(HttpServletRequest request, HttpServletResponse response) {
					try {
						String loginName = StringUtil.nvlString(request.getParameter("request"));
						String userName = StringUtil.nvlString(request.getParameter("userName"));
						String department_id = StringUtil.nvlString(request.getParameter("department_id"));
						String company_id = StringUtil.nvlString(request.getParameter("company_id"));
						String speciality = StringUtil.nvlString(request.getParameter("speciality"));
						String status = StringUtil.nvlString(request.getParameter("status"));
						HashMap<String, Object> map = new HashMap<String, Object>();
						List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
						User u = new User();
						u.setLoginName(loginName);
						u.setUserName(userName);
						u.setDepartment_id(department_id);
						u.setCompany_id(company_id);
						u.setSpeciality(speciality);
						u.setStatus(status);
						map.put("user", u);
						PagerModel pageUser = userService.getPageUser(map);
						List<User> datas = pageUser.getDatas();
						int count = 0;
						if (datas != null && datas.size()>0) {
							count = datas.size();
							Map<String,Object> map2 = null;
							for (User user : datas) {
								map2 = new HashMap<String,Object>();
								if (!StringUtil.isNUll(user.getLoginName())) {   //用户名
									map2.put("loginName", user.getLoginName());
								}else {
									map2.put("loginName", "");
								}
								
								if (!StringUtil.isNUll(user.getUserName())) {   //姓名
									map2.put("userName", user.getUserName());
								}else {
									map2.put("userName", "");
								}
								if (!StringUtil.isNUll(user.getDepartment_Name())) {   //所属公司
									map2.put("company_Name", user.getCompany_Name());
								}else {
									map2.put("company_Name", "");
								}
								
								if (!StringUtil.isNUll(user.getDepartment_Name())) {   //所属部门
									map2.put("department_Name", user.getDepartment_Name());
								}else {
									map2.put("department_Name", "");
								}
								
								if (!StringUtil.isNUll(user.getTeam_Name())) {  //所属团队
									map2.put("team_Name", user.getTeam_Name());
								}else {
									map2.put("team_Name", "");
								}
								
								if (!StringUtil.isNUll(user.getPosition_Name())) {   //职位级别
									map2.put("position_Name", user.getPosition_Name());
								}else {
									map2.put("position_Name", "");
								}
								
								if (!StringUtil.isNUll(user.getPostName())) {   //所属岗位
									map2.put("postName", user.getPostName());
								}else {
									map2.put("postName", "");
								}
								
								if (!StringUtil.isNUll(user.getRoleName())) {   //角色
									map2.put("roleName", user.getRoleName());
								}else {
									map2.put("roleName", "");
								}
								
								if (!StringUtil.isNUll(user.getEmployed_date())) {   //入职日期
									map2.put("employed_date", user.getEmployed_date());
								}else {
									map2.put("employed_date", "");
								}
								
								if (!StringUtil.isNUll(user.getEmail())) {   //邮箱
									map2.put("email", user.getEmail());
								}else {
									map2.put("email", "");
								}
								
								if (!StringUtil.isNUll(user.getStatus())) {   //状态
									if(user.getStatus().equals("0")){
										map2.put("status", "在职");
									}else if(user.getStatus().equals("1")){
										map2.put("status", "离职");
									}
								}else {
									map2.put("status", "");
								}
								if (!StringUtil.isNUll(user.getSpeciality())) {   //特长
									map2.put("speciality", user.getSpeciality());
								}else {
									map2.put("speciality", "");
								}
								list.add(map2);
							}
						}
						int n = 50000;		//定义每个sheet存放的数据条数
						String sheetName = "用户统计";
						List<List> lists = new ArrayList<List>();
						
						String[] header = {"用户名(工号)","姓名","所属公司","所属部门","所属团队","职位级别","所属岗位","角色","入职时间","邮箱","状态","特长"};
						String[][] headers = {header};
						String[] key = {"loginName","userName","company_Name","department_Name","team_Name","position_Name","postName","roleName","employed_date","email","status","speciality"};
						String[][] keys = {key};
						
						String dateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
						String fileName = "用户统计"+dateStr+".xlsx";
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
						
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
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
	
}
