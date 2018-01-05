package cn.taskSys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.taskSys.base.PageView;
import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Attence;
import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.entity.Attendance;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.PersonAttence;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.User;
import cn.taskSys.service.IAttenceDetailsService;
import cn.taskSys.service.IAttenceService;
import cn.taskSys.service.IAttendanceService;
import cn.taskSys.service.IPersonAttenceService;
import cn.taskSys.service.RoleService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.ExportExcelUtil;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;
import cn.taskSys.utils.UtilValidate;


@Controller
@RequestMapping(value = "/attence")
public class AttenceController extends BaseAction<Object>{
	
	@Autowired
	private IAttenceService attenceService;
	
	@Autowired
	private UtilsService utilsService;
	
	@Autowired
	private IPersonAttenceService personAttenceService;
	
	@Autowired
	private IAttendanceService attendanceService;
	
	
	@Autowired
	private IAttenceDetailsService attenceDetailsService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 考勤导入列表
	 * @param attence
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAttenceList")
	public ModelAndView getAttenceList(@ModelAttribute("attence")Attence attence, HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/attence/attenceList");
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			//String loginName = user.getLoginName();
			String deparmentId = user.getDepartment_id();
			/*DataSourceContextHolder.setDbType("hwDataSource");//调用考勤数据源
			List<String> list = new ArrayList<String>();
			list.add(loginName);
			List<Map<String, String>> sysUsers = attenceService.findSysUsers(list);//所有在职人员
			DataSourceContextHolder.setDbType("dataSourceTask");//调用任务系统数据源 
			for (Map<String, String> sysUser : sysUsers) {
				String empCodeT = sysUser.get("empCode");
				if(loginName.equals(empCodeT)){
					deparmentId = sysUser.get("department");
				}
			}*/
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL"))) {//部门经理
				attence.setDepartment(deparmentId);
			}
			mv.addObject("userRole", userRole);
			
			if(attence.getMonths()==null || attence.getMonths().equals("")){
				attence.setMonths(DateUtil.DateToString(new Date(), "yyyy-MM"));
			}
			PageView<Attence> pageView  = new PageView<Attence>();
			pageView = attenceService.getAttenceListpageView(attence);	
			
			mv.addObject("pageView", pageView);
			
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			
			mv.addObject("dictionaryLlist", dictionaryLlist);
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *     个人中心列表查询
	 * @param  :
	 * @return :ModelAndView
	 * @author :ruiy
	 * @date   :2016-7-27上午9:57:48
	 *
	 */
	@RequestMapping(value = "/personAttenceList")
	public ModelAndView personAttenceList(@ModelAttribute("personAttence")PersonAttence personAttence,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/attence/personAttenceList");
		
		try {
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			//String loginName = user.getLoginName();
			String deparmentId = user.getDepartment_id();
			
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL"))) {//部门经理
				personAttence.setpDepCode(deparmentId);
			}
			mv.addObject("userRole", userRole);
			
			if(personAttence.getpAttenceMonth()==null || personAttence.getpAttenceMonth().equals("")){
				personAttence.setpAttenceMonth(DateUtil.DateToString(new Date(), "yyyy-MM"));
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("attenceDate", personAttence.getpAttenceMonth());
			int countDay = attenceDetailsService.getAttenceDay(map);
			mv.addObject("countDay", countDay);
			PageView<PersonAttence> pageView  = new PageView<PersonAttence>();
			pageView = personAttenceService.personAttenceListpageView(personAttence);
			mv.addObject("pageView", pageView);
		
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			
			mv.addObject("dictionaryLlist", dictionaryLlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 		个人中心   导出excle表格
	 * @param  :
	 * @return :void
	 * @author :ruiy
	 * @date   :2016-7-27下午2:34:01
	 *
	 */
	@RequestMapping(value = "/exportpersonAttenceList")
	public void exportpersonAttenceList(@ModelAttribute("personAttence")PersonAttence personAttence, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			//String loginName = user.getLoginName();
			String deparmentId = user.getDepartment_id();
			/*DataSourceContextHolder.setDbType("hwDataSource");//调用考勤数据源
			List<String> list = new ArrayList<String>();
			list.add(loginName);
			List<Map<String, String>> sysUsers = attenceService.findSysUsers(list);//所有在职人员
			DataSourceContextHolder.setDbType("dataSourceTask");//调用任务系统数据源 
			for (Map<String, String> sysUser : sysUsers) {
				String empCodeT = sysUser.get("empCode");
				if(loginName.equals(empCodeT)){
					deparmentId = sysUser.get("department");
				}
			}*/
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL"))) {//部门经理
				personAttence.setpDepCode(deparmentId);
			}
			
			personAttence.setpAttenceMonth(StringUtil.nvlString(request
							.getParameter("pAttenceMonth")));
			personAttence.setpDepCode(StringUtil.nvlString(request
					.getParameter("department")));
			personAttence.setpUsername(StringUtil.nvlString(request
					.getParameter("pUsername")));
			personAttence.setpEmpCode(StringUtil.nvlString(request
					.getParameter("pEmpCode")));
					
			List<PersonAttence> personAttencesList = personAttenceService.personAttenceList(personAttence);
			List<Map<String,Object>> list1= new ArrayList<Map<String,Object>>();
			int count = 0;
			Map<String, String> mapT = new HashMap<String, String>();
			mapT.put("attenceDate", personAttence.getpAttenceMonth());
			int countDay = attenceDetailsService.getAttenceDay(mapT);
			if (personAttencesList != null && personAttencesList.size()>0) {
				count = personAttencesList.size();
				//PersonAttence==>Map
				Map<String,Object> map = null;
				for (PersonAttence pa : personAttencesList) {
					map = new HashMap<String,Object>();
					map.put("username", StringUtil.nvlString(pa.getpUsername()));
					map.put("empcode", StringUtil.nvlString(pa.getpEmpCode()));
					map.put("department", StringUtil.nvlString(pa.getDepartmentName()));
					map.put("countmonth", countDay);
					map.put("attenceday", StringUtil.nvlString(pa.getpAttenceDay()));
					map.put("attenceTime", StringUtil.nvlString(pa.getpAttenceTime()));
					map.put("unontime", StringUtil.nvlString(pa.getpAttenceUnpunctualTime()));
					map.put("changeday", StringUtil.nvlString(pa.getpAttenceChangeDay()));
					map.put("attenceWithoutPay", StringUtil.nvlString(pa.getpAttenceWithoutPay()));
					
					list1.add(map);
				}
			}
			
			int n = 50000;		//定义每个sheet存放的数据条数
			String sheetName = "出勤情况统计";
			@SuppressWarnings("rawtypes")
			List<List> lists = new ArrayList<List>();
			String[] header = null;
			String[] key = null;
			
			
			header = new String[]{"员工号","姓名","所属部门","应出勤天数","出勤天数","勤外工时","加班报销", "迟到早退","调休天数"};
			key =  new String[]{"empcode","username","department","countmonth","attenceday","attenceTime","attenceWithoutPay","unontime","changeday"};
			
			String[][] headers = {header};
			String[][] keys = {key};
			
			String dateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
			String fileName = "出勤情况统计"+dateStr+".xlsx";
			
			if (count <= n) {
				String[] sheets = new String[1];
				sheets[0] = sheetName + "1";
				lists.add(list1);
				ExportExcelUtil.exportExcel2(fileName, response, lists, headers, keys, sheets);
			}else{
				int fysheet = (int) Math.ceil((double)count / n) ;
				String[] sheets = new String[fysheet];
				for (int i = 1; i <= fysheet; i++) {
					sheets[i - 1] = sheetName + i;
				}
				lists = createList(list1,n);
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
	
	/**
	 *  个人中心  考勤
	 * @param  :
	 * @return :ModelAndView
	 * @author :ruiy
	 * @date   :2016-6-12下午1:12:17
	 *
	 */

	@RequestMapping(value = "/personSys")
	public ModelAndView personSys(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/attence/personSys");
		try {
			User user = (User) request.getSession().getAttribute("JX_USERINFO");
			String nowDate =  DateUtil.DateToString(new Date(), "yyyy-MM");
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> maps = new HashMap<String, Object>();
			//查询personAttence数据根据id
			map.put("nowDate", nowDate);
			map.put("pEmpCode",user.getLoginName());
			maps.put("acEmpCode",user.getLoginName());
			List<Attendance> list = attendanceService.getPreAttendance(maps);
			PersonAttence personAttence = personAttenceService.getPersonAttence(map);
			
			map.put("nowDate", getDate(-1));
			PersonAttence personAttence1 = personAttenceService.getPersonAttence(map);
			for(Attendance attendance:list){
				mv.addObject("acgz", attendance.getAcgz());
			}
				if(personAttence!=null){
					if(personAttence1==null){
						mv.addObject("pAttenceChangeDay", personAttence.getpAttenceChangeDay()==null?"":personAttence.getpAttenceChangeDay());
						mv.addObject("pAttenceDay", personAttence.getpAttenceDay()==null?"":personAttence.getpAttenceDay());
						mv.addObject("pAttenceUnpunctualTime",  personAttence.getpAttenceUnpunctualTime()==null?"":personAttence.getpAttenceUnpunctualTime());
						mv.addObject("pAttenceWithoutPay", personAttence.getpAttenceWithoutPay()==null?"":personAttence.getpAttenceWithoutPay());
						mv.addObject("pAttenceTime", personAttence.getpAttenceTime()==null?"":personAttence.getpAttenceTime());
						mv.addObject("pMealPay", personAttence.getpMealPay()==null?"":personAttence.getpMealPay());
						mv.addObject("pTrafficPay", personAttence.getpTrafficPay()==null?"":personAttence.getpTrafficPay());
					}else{
						mv.addObject("pAttenceChangeDay", Double.parseDouble(personAttence.getpAttenceChangeDay()==null?"":personAttence.getpAttenceChangeDay())+Double.parseDouble(personAttence1.getpAttenceChangeDay()==null?"":personAttence1.getpAttenceChangeDay()));
						mv.addObject("pAttenceDay", personAttence.getpAttenceDay()==null?"":personAttence.getpAttenceDay());
						mv.addObject("pAttenceUnpunctualTime", personAttence.getpAttenceUnpunctualTime()==null?"":personAttence.getpAttenceUnpunctualTime());
						mv.addObject("pAttenceWithoutPay", personAttence.getpAttenceWithoutPay()==null?"":personAttence.getpAttenceWithoutPay());
						mv.addObject("pAttenceTime", personAttence.getpAttenceTime()==null?"":personAttence.getpAttenceTime());
						mv.addObject("pMealPay", personAttence.getpMealPay()==null?"":personAttence.getpMealPay());
						mv.addObject("pTrafficPay", personAttence.getpTrafficPay()==null?"":personAttence.getpTrafficPay());
					}
				}else{
					if(personAttence1==null){
						mv.addObject("pAttenceChangeDay", "");
						mv.addObject("pAttenceDay", "");
						mv.addObject("pAttenceUnpunctualTime", "");
						mv.addObject("pAttenceWithoutPay", "");
						mv.addObject("pAttenceTime", "");
						mv.addObject("pMealPay", "");
						mv.addObject("pTrafficPay", "");
					}else{
						mv.addObject("pAttenceChangeDay", personAttence1.getpAttenceChangeDay()==null?"":personAttence1.getpAttenceChangeDay());
						mv.addObject("pAttenceDay", "");
						mv.addObject("pAttenceUnpunctualTime", "");
						mv.addObject("pAttenceWithoutPay", "");
						mv.addObject("pAttenceTime", "");
						mv.addObject("pMealPay", "");
						mv.addObject("pTrafficPay", "");
					}
			}
			mv.addObject("nowDate", nowDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * ajax  变更月份获取到不同的值
	 * @param  :
	 * @return :void
	 * @author :ruiy
	 * @date   :2016-6-12下午3:03:40
	 *
	 */
	@RequestMapping(value = "/personSysList")
	public void personSysList(HttpServletRequest request,HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("JX_USERINFO");
		String imTime =  StringUtil.nvlString(request.getParameter("imTime"));
		Map<String, Object> map = new HashMap<String, Object>();
		String year = imTime.substring(0, 4);
		String month = imTime.substring(5,7);
		int  a= Integer.parseInt(month);
		int  b= Integer.parseInt(year);
		String toDate =null;
		if(a>10){
			toDate = year + "-" + (a-1) + "";
		}
		if(a==10){
			toDate = year + "-09";
		}
		if(a<10&&a>1){
			toDate = year + "-0" + (a-1);
		}
		if(a==1){
			toDate = (b-1) + "12";
		}
		map.put("nowDate", imTime);
		map.put("pEmpCode",user.getLoginName());
		
		PersonAttence personAttence = personAttenceService.getPersonAttence(map);
		
		map.put("nowDate", toDate);
			JSONObject json=new JSONObject();
			try {
				PersonAttence personAttence1 = personAttenceService.getPersonAttence(map);
					if(personAttence!=null){
						if(personAttence1==null){
							json.put("pAttenceChangeDay", personAttence.getpAttenceChangeDay()==null?"":personAttence.getpAttenceChangeDay());
							json.put("pAttenceDay", personAttence.getpAttenceDay()==null?"":personAttence.getpAttenceDay());
							json.put("pAttenceUnpunctualTime",  personAttence.getpAttenceUnpunctualTime()==null?"":personAttence.getpAttenceUnpunctualTime());
							json.put("pAttenceWithoutPay", personAttence.getpAttenceWithoutPay()==null?"":personAttence.getpAttenceWithoutPay());
							json.put("pAttenceTime", personAttence.getpAttenceTime()==null?"":personAttence.getpAttenceTime());
							json.put("pMealPay", personAttence.getpMealPay()==null?"":personAttence.getpMealPay());
							json.put("pTrafficPay", personAttence.getpTrafficPay()==null?"":personAttence.getpTrafficPay());
						}else{
							json.put("pAttenceChangeDay", Double.parseDouble(personAttence.getpAttenceChangeDay()==null?"":personAttence.getpAttenceChangeDay())+Double.parseDouble(personAttence1.getpAttenceChangeDay()==null?"":personAttence1.getpAttenceChangeDay()));
							json.put("pAttenceDay", personAttence.getpAttenceDay()==null?"":personAttence.getpAttenceDay());
							json.put("pAttenceUnpunctualTime", personAttence.getpAttenceUnpunctualTime()==null?"":personAttence.getpAttenceUnpunctualTime());
							json.put("pAttenceWithoutPay", personAttence.getpAttenceWithoutPay()==null?"":personAttence.getpAttenceWithoutPay());
							json.put("pAttenceTime", personAttence.getpAttenceTime()==null?"":personAttence.getpAttenceTime());
							json.put("pMealPay", personAttence.getpMealPay()==null?"":personAttence.getpMealPay());
							json.put("pTrafficPay", personAttence.getpTrafficPay()==null?"":personAttence.getpTrafficPay());
						}
					}else{
						if(personAttence1==null){
							json.put("pAttenceChangeDay", "");
							json.put("pAttenceDay", "");
							json.put("pAttenceUnpunctualTime", "");
							json.put("pAttenceWithoutPay", "");
							json.put("pAttenceTime", "");
							json.put("pMealPay", "");
							json.put("pTrafficPay", "");
						}else{
							json.put("pAttenceChangeDay", personAttence1.getpAttenceChangeDay()==null?"":personAttence1.getpAttenceChangeDay());
							json.put("pAttenceDay", "");
							json.put("pAttenceUnpunctualTime", "");
							json.put("pAttenceWithoutPay", "");
							json.put("pAttenceTime", "");
							json.put("pMealPay", "");
							json.put("pTrafficPay", "");
						}
				}
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				out.write(json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 考勤规则设置 ajax
	 * @param  :
	 * @return :void
	 * @author :ruiy
	 * @throws Exception 
	 * @date   :2016-6-13上午11:56:36
	 *
	 */
	@RequestMapping(value = "/attenceSys")
	public void  attenceSys(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			String loginName =  StringUtil.nvlString(request.getParameter("loginName"));
			
			String acgz =  StringUtil.nvlString(request.getParameter("selTime"));
			String setTime =  StringUtil.nvlString(request.getParameter("setTime"));
			String endTime =  StringUtil.nvlString(request.getParameter("endTime"));
			map.put("acEmpCode", loginName);
			List<Attendance> attendance = attendanceService.getPreAttendance(map);
			
			if(attendance==null||attendance.size()<=0){
				if("4".equals(acgz)){
					map.put("acId", UUIDHexGenerator.generater());
					map.put("acgz", acgz);
					map.put("acgzFlag", 1);//哺乳期标识 0正常，1哺乳期 
					map.put("acStartTime", setTime);
					map.put("acEndTime", endTime);
					attendanceService.addPreAttendance(map);
				}else{
					map.put("acId", UUIDHexGenerator.generater());
					map.put("acgz", acgz);
					map.put("acgzFlag", 0);//哺乳期标识 0正常，1哺乳期 
					map.put("acStartTime", DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
					map.put("acEndTime", getDate(1)+DateUtil.DateToString(new Date(), "yyyy-MM-dd").substring(7, 10));
					attendanceService.addPreAttendance(map);
				}
			}else{
				if("4".equals(acgz)){
					//周期一年
					map.put("acgz", acgz);
					map.put("acgzFlag", 1);
					map.put("acStartTime", setTime);
					map.put("acEndTime", endTime);
					attendanceService.updatePreAttendance(map);
				}else{
					//按月修改 ---- 周期按月
					map.put("acgz", acgz);
					map.put("acgzFlag", 0);
					map.put("acStartTime", DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
					map.put("acEndTime", getDate(1)+DateUtil.DateToString(new Date(), "yyyy-MM-dd").substring(7, 10));
					attendanceService.updatePreAttendance(map);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/findAttenceDetails")
	public ModelAndView findAttenceDetails(HttpServletRequest request) {
		ModelAndView mv;
		try {
			mv = new ModelAndView("/attence/attenceDetails");
			User user = (User) request.getSession().getAttribute("JX_USERINFO");
			String loginName = user.getLoginName();
			String empCode = StringUtil.nvlString(request.getParameter("empCode"));
			String months = StringUtil.nvlString(request.getParameter("months"));
			String flag = StringUtil.nvlString(request.getParameter("flag"));
			AttenceDetails attenceDetails = new AttenceDetails();
			if(flag.equals("88888")){
				attenceDetails.setEmpCode(loginName);
				attenceDetails.setAttenceDate(months);
				mv.addObject("flag", "88888");
			}else{
				attenceDetails.setEmpCode(empCode);
				attenceDetails.setAttenceDate(months);
			}
			List<AttenceDetails> attenceDetailsList = attenceService.getAttenceDetailsList(attenceDetails);
			if(attenceDetailsList!=null && attenceDetailsList.size()>0){//考勤异常标红
				for (AttenceDetails attence : attenceDetailsList) {
					String remark = attence.getRemark();
					if(!StringUtil.isNUll(remark)){
						if(remark.endsWith("all") || remark.equals("1") || remark.equals("2") ||remark.equals("4") ||  remark.equals("5") ||remark.equals("6") ||  remark.equals("7") ||remark.equals("8") ||  remark.equals("9")){
							attence.setFlag("all");
						}else if (remark.endsWith("moring")) {
							attence.setFlag("moring");
						}else if (remark.endsWith("afternoon")) {
							attence.setFlag("afternoon");
						}
						
					}
					
					if(attence.getDateType().equals("1")){//工作日
						if(UtilValidate.isNotEmpty(attence.getMorningAttence()) && UtilValidate.isNotEmpty(attence.getAfternoonAttence())){
							String morningAttence = attence.getMorningAttence();
							Date morning = DateUtil.StringToDate(morningAttence, "HH:mm:ss");
							String afternoonAttence = attence.getAfternoonAttence();
							Date afternoon = DateUtil.StringToDate(afternoonAttence, "HH:mm:ss");
							Date date_12 = DateUtil.StringToDate("12:00:00", "HH:mm:ss");
							if(morning.after(date_12) || afternoon.before(date_12)){//判断标准：至少两次打卡记录并且上午打卡时间：12:00之前，下午打卡时间：12:00之后，系统认为正常状态。
								attence.setIsException("1");//异常
							}
						}else {
							attence.setIsException("1");//异常
						}
					}
					//<c:if test="${attenceDetails.dateType=='1' && (attenceDetails.morningAttence==null || attenceDetails.afternoonAttence==null)}">
				}
			}
			
			mv.addObject("attenceDetailsList", attenceDetailsList);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "11");
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(map);//加载字典表信息
			mv.addObject("dictionaryLlist", dictionaryLlist);
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@RequestMapping(value = "/modifyAttenceDetails")
	public void modifyAttenceDetails(HttpServletRequest request, HttpServletResponse response) {
		String result="";
		try {
			//String nowDate = DateUtil.DateToString(new Date(),"yyyy-MM");
			String paramStr = StringUtil.nvlString(request.getParameter("paramStr"));
			if(paramStr!=null && !paramStr.equals("")){
				String[] paramArr = paramStr.split(",");
				List<AttenceDetails> list = new ArrayList<AttenceDetails>();
				String  attdetailsId = null;
				double a = 0;
				if(paramArr.length>0){
					AttenceDetails ad = null;
					attdetailsId = paramArr[0].split("#")[0];
					
					for (int i = 0; i < paramArr.length; i++) {
						String id = paramArr[i].split("#")[0];
						String remark = paramArr[i].split("#")[1];
						ad = new AttenceDetails();
						ad.setId(id);
						ad.setRemark(remark);
						/**
						 * 1=外出，2=请假，1all=外出全天，2all=请假全天
						 * 1moring=上午外出，1afternoon=下午外出
						 * 2moring=上午请假，2afternoon=下午请假
						 * 3=出差
						 * 4=病假，5=事假，6=婚假，7=丧假，8=产假，9=年假,
						 * 都有上午、下午、全天
						 */
						if(remark.equals("1") || remark.equals("1all") ){//外出或调休
							ad.setMorningAttence("09:30:00");
							ad.setAfternoonAttence("18:30:00");
						}else if (remark.equals("1moring") ) {
							ad.setMorningAttence("09:30:00");
						}else if (remark.equals("1afternoon")) {
							ad.setAfternoonAttence("18:30:00");
						}
						
						if(remark.equals("1")|| remark.equals("1all")||remark.equals("3")){
							a = a + 1;
						}else if(remark.equals("1moring") ||remark.equals("1afternoon") ){
							/**
							 * 如果打卡只有一次考勤天数，外出半天，出勤天数+1；
							 * 因为定时任务在统计个人中心的数据时没有统计只有一次打卡记录的数据
							 */
							AttenceDetails attdetails = attenceDetailsService.getAttdetailsById(id);
							if(remark.equals("1moring") && attdetails.getMorningAttence()==null && attdetails.getAfternoonAttence()!=null){
								a = a + 1;
							}else if (remark.equals("1afternoon") && attdetails.getMorningAttence()!=null && attdetails.getAfternoonAttence()==null) {
								a = a + 1;
							}else{
								a +=0.5;
							}
						}
						ad.setRemarkFlag("0");
						list.add(ad);
					}
					
				}
				Map<String, Object> maps = new HashMap<String, Object>();
				Map<String, Object> mapup = new HashMap<String, Object>();
				AttenceDetails attencedetails = attenceDetailsService.getAttdetailsById(attdetailsId);
				String nowDate = attencedetails.getAttenceDate().substring(0, 7);
				String acEmpCode = attencedetails.getEmpCode();
				maps.put("nowDate", nowDate);
				maps.put("pEmpCode", acEmpCode);
				PersonAttence personAttence = personAttenceService.getPersonAttence(maps);
				//修改考勤信息
				mapup.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+a+"");//出勤天数更正
				mapup.put("pAddDate", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				mapup.put("pEmpCode", acEmpCode);
				mapup.put("pAttenceMonth", nowDate);//修改上次的考勤信息
				personAttenceService.updatePersonAttenceRemark(mapup);
				if(list.size()>0){
					attenceService.modifyAttenceDetails(list);
					result="ok";
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result="error";
		}finally{
			try {
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				out.write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 手动执行 个人考勤
	 * @param  :
	 * @return :void
	 * @author :ruiy
	 * @date   :2016-6-16下午3:18:42
	 *
	 */
	@RequestMapping(value = "/savePerAttence")
	public void savePerAttence(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String nowDate = StringUtil.nvlString(request.getParameter("attenceDate"));
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd"); 
		Date dt1 = dfm.parse(nowDate);
		String nDate = nowDate.substring(0, 7);
		unAttence(nowDate);
		//查询当前月所有打卡信息
		List<AttenceDetails> list = attenceDetailsService.getAllAttence(nowDate);
		Map<String, Object> map = null;
		Map<String, Object> map1 = null;
		Map<String, Object> maps = null;
		Map<String, Object> acgzFlag = null;
		JSONObject json=new JSONObject();
			try{
				if(list!=null&&list.size()>0){
					for(AttenceDetails attenceDetails:list){
						int remarkFalg = 2;
						String acgzF = null;
						map = new HashMap<String, Object>();
						map1 = new HashMap<String, Object>();
						maps = new HashMap<String, Object>();
						acgzFlag = new HashMap<String, Object>();
						if(attenceDetails.getEmpCode()!=null){
							//查询personAttence数据根据id
							map.put("nowDate", nDate);
							map.put("pEmpCode",attenceDetails.getEmpCode());
							
							acgzFlag.put("acEmpCode",attenceDetails.getEmpCode());//哺乳期标识
							List<Attendance> acgzList = attendanceService.getPreAttendance(acgzFlag);
							String stime =null;
							Date dt2 =null;
							String etime =null;
							Date dt3 =null;
							if(acgzList!=null&&acgzList.size()>0){
								for(Attendance attendance:acgzList){
									acgzF = attendance.getAcgzFlag();
									stime= attendance.getAcStartTime(); 
									dt2 = dfm.parse(stime);
									etime= attendance.getAcEndTime();
									dt3 = dfm.parse(etime);
								}
							}
							
							map1.put("acEmpCode", attenceDetails.getEmpCode());
							PersonAttence personAttence = personAttenceService.getPersonAttence(map);
							
							List<Attendance> list2 = attendanceService.getPreAttendance(map1);
							if(list2!=null&&list2.size()>0){
								for(Attendance attendance:list2){
									remarkFalg = Integer.parseInt(attendance.getAcgz());
								}
							}else{
								remarkFalg=2;
							}
							
							DecimalFormat df   = new DecimalFormat("#.0");
							SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
							Date gbnight2 = sdf.parse("21:00");
							Date gbnight3 = sdf.parse("20:00");
							//保存个人考勤信息---如果没有数据才保存（首次执行插入数据）
							if(personAttence==null){
							//上下班打卡时间都不为空的情况
							if(attenceDetails.getMorningAttence()!=null&&attenceDetails.getAfternoonAttence()!=null){
								Date morning = sdf.parse(attenceDetails.getMorningAttence());
								Date afternoon = sdf.parse(attenceDetails.getAfternoonAttence());
								double weekTime = afternoon.getTime()-morning.getTime();
								double weekTime1 = weekTime/1000/60/60;
								double weekTime2 = Double.parseDouble(df.format(weekTime1));
								String we = null;
								if(weekTime2>=10){
									String s = new String(weekTime2+"");
									String b = s.substring(0, 2);
									String c= s.substring(3, 4);
									int d = Integer.parseInt(c);
									if(d>=0&&d<5){
										 we = b+"."+"0";
									}else if(d>=5&&d<=9){
										 we = b+"."+"5";
									}
								}else{
									String s = new String(weekTime2+"");
									String b = s.substring(0, 1);
									String c= s.substring(2, 3);
									int d = Integer.parseInt(c);
									if(d>=0&&d<5){
										 we = b+"."+"0";
									}else if(d>=5&&d<=9){
										 we = b+"."+"5";
									}
								}
					//==============================start分两种情况===工作日、公休日==========================================
									//1.工作日
									if(attenceDetails.getDateType()!=null&&attenceDetails.getDateType().equals("1")){
										if (acgzF!=null&&acgzF.equals("1")&&dt1.getTime()>=dt2.getTime()&&dt1.getTime()<=dt3.getTime()) {
											maps = cn.taskSys.utils.AttenceMath.addAttence1(remarkFalg,morning,afternoon,personAttence);
										}else{
											maps = cn.taskSys.utils.AttenceMath.addAttence(remarkFalg,morning,afternoon,personAttence);
										}
									//2.公休日
									}else{
											//分两种情况
											//1.打卡时间差大于8小时算一天出勤 没有迟到早退
											maps.put("pAttenceUnpunctualTime",0);
											if(weekTime2>=8){
												maps.put("pAttenceDay", 1);
												maps.put("pAttenceChangeDay",1);
												if(afternoon.getTime()>=gbnight2.getTime()){//大于21点
													
													maps.put("pAttenceTime", Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", 75);
													maps.put("pMealPay", 50);
													maps.put("pTrafficPay", 25);
												}else if (afternoon.getTime()>=gbnight3.getTime() && afternoon.getTime()<gbnight2.getTime()) {//小于21点，大于20点
													maps.put("pAttenceTime", Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", 50);
													maps.put("pMealPay", 50);
													maps.put("pTrafficPay", 0);
												}else{//小于20点
													maps.put("pAttenceTime", Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", 25);
													maps.put("pMealPay", 25);
													maps.put("pTrafficPay", 0);
												}
											//2.打卡时间差4-8小时算半天出勤（没有迟到早退）
											}else if(weekTime2>=4&&weekTime2<8){
												maps.put("pAttenceDay", 0.5);
												maps.put("pAttenceChangeDay",0.5);
												if(afternoon.getTime()>=gbnight2.getTime()){//大于21点
													
													maps.put("pAttenceTime", Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", 50);
													maps.put("pMealPay", 25);
													maps.put("pTrafficPay", 25);
												}else if(afternoon.getTime()>=gbnight3.getTime() && afternoon.getTime()<gbnight2.getTime()){//小于21点,大于20点
													maps.put("pAttenceTime", Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", 25);
													maps.put("pMealPay", 25);
													maps.put("pTrafficPay", 0);
												}
											}else{
												maps.put("pAttenceDay", 0);
												maps.put("pAttenceChangeDay",0);
												maps.put("pAttenceTime", Double.parseDouble(we));
												maps.put("pAttenceWithoutPay", 0);
												maps.put("pMealPay", 0);
												maps.put("pTrafficPay", 0);
											}
									}
					//==============================end分两种情况===工作日、公休日==========================================
								//只有一次打卡或者没有打卡信息的
								}else{
									maps.put("pAttenceDay",0);
									maps.put("pAttenceUnpunctualTime", 0);
									maps.put("pAttenceTime", 0);
									maps.put("pAttenceWithoutPay", 0);
									maps.put("pMealPay", 0);
									maps.put("pTrafficPay", 0);
									maps.put("pAttenceChangeDay", 0);
								}
								//插入信息
								maps.put("pId", UUIDHexGenerator.generater());
								maps.put("pEmpCode", attenceDetails.getEmpCode());
								maps.put("pDepCode", attenceDetails.getDepartment());
								maps.put("pUsername", attenceDetails.getUsername());
								maps.put("pAttenceMonth", nDate);
								maps.put("pAddDate", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
								maps.put("pRemark", "");
								personAttenceService.savePersonAttence(maps);
		//**==================================说明考勤信息不为空，修改=========================*****************************************//*
							}else{
								//上下班打卡时间都不为空的情况
								if(attenceDetails.getMorningAttence()!=null&&attenceDetails.getAfternoonAttence()!=null){
									Date morning = sdf.parse(attenceDetails.getMorningAttence());
									Date afternoon = sdf.parse(attenceDetails.getAfternoonAttence());
									
									//公休日---加班时
									double weekTime = afternoon.getTime()-morning.getTime();
									double weekTime1 = weekTime/1000/60/60;
									double weekTime2 = Double.parseDouble(df.format(weekTime1));
									String we = null;
									if(weekTime2>=10){
										String s = new String(weekTime2+"");
										String b = s.substring(0, 2);
										String c= s.substring(3, 4);
										int d = Integer.parseInt(c);
										if(d>=0&&d<5){
											 we = b+"."+"0";
										}else if(d>=5&&d<=9){
											 we = b+"."+"5";
										}
									}else{
										String s = new String(weekTime2+"");
										String b = s.substring(0, 1);
										String c= s.substring(2, 3);
										int d = Integer.parseInt(c);
										if(d>=0&&d<5){
											 we = b+"."+"0";
										}else if(d>=5&&d<=9){
											 we = b+"."+"5";
										}
									}
									//1.工作日
									if(attenceDetails.getDateType()!=null&&attenceDetails.getDateType().equals("1")){
										if (acgzF!=null&&acgzF.equals("1")&&dt1.getTime()>=dt2.getTime()&&dt1.getTime()<=dt3.getTime()) {
											maps = cn.taskSys.utils.AttenceMath.modifyAttence1(remarkFalg, morning, afternoon, personAttence);
										}else{
											maps = cn.taskSys.utils.AttenceMath.modifyAttence(remarkFalg, morning, afternoon, personAttence);
										}
									//2.公休日
									}else{
											//分两种情况
											//1.打卡时间差大于8小时算一天出勤  按迟到计算 
											//计算出勤天数===========
											if(weekTime2>=8){
												maps.put("pAttenceChangeDay",Double.parseDouble(personAttence.getpAttenceChangeDay())+1);
												maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
												if(afternoon.getTime()>=gbnight2.getTime()){//大于21点
													maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+75);
													maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+50);
													maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
												}else if (afternoon.getTime()>=gbnight3.getTime() && afternoon.getTime()<gbnight2.getTime()) {//小于21点，大于20点
													maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
													maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+50);
													maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
												}else{//小于20点
													maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
													maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
												}
											}else if(weekTime2>=4&&weekTime2<8){
												maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
												maps.put("pAttenceChangeDay",Double.parseDouble(personAttence.getpAttenceChangeDay())+0.5);
												if(afternoon.getTime()>=gbnight2.getTime()){//大于21点
													maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
													maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
													maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
												}else if (afternoon.getTime()>=gbnight3.getTime() && afternoon.getTime()<gbnight2.getTime()){//小于21点，大于20点
													maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
													maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
													maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
													maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
												}
											}else{
												maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
											}
									}
									//修改考勤信息
									maps.put("pAddDate", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
									maps.put("pEmpCode", attenceDetails.getEmpCode());
									maps.put("pAttenceMonth", nDate);//修改上次的考勤信息
									maps.put("pRemark", "");
									personAttenceService.updatePersonAttence(maps);
								}
							}
						}
					}
				}			
				json.put("result", "ok");
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				out.write(json.toString());
			
				} catch (Exception e) {
					json.put("result", "error");
					e.printStackTrace();
				}
		}
	
	/**
	 *   计算后补考勤 ---- 新任职员工
	 * @param  :
	 * @return :void
	 * @author :ruiy
	 * @date   :2016-8-1下午2:09:06
	 *
	 */
	private void unAttence(String nowDate) throws Exception{
		//查询详情表----找到批量导进的考勤数据
		
		List<AttenceDetails> list = attenceDetailsService.getLastList();
		Map<String, Object> map = null;
		Map<String, Object> maps = null;
		if(list!=null&&list.size()>0){
			for (AttenceDetails attenceDetails:list) {//所有后补考勤
				map = new HashMap<String, Object>();
				maps = new HashMap<String, Object>();
				
				//查询 personAttence 个人考勤表人员
				map.put("nowDate", attenceDetails.getAttenceDate().substring(0, 7));
				map.put("pEmpCode",attenceDetails.getEmpCode());
				PersonAttence personAttence = personAttenceService.getPersonAttence(map);
				
				if(personAttence!=null){
					if(attenceDetails.getEmpCode().equals(personAttence.getpEmpCode())){//和个人中心考勤匹配、处理了天数，不需要遍历天数
						
						//调用计算考勤的方法----修改考勤信息
						DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd"); 
						Date dt1 = dfm.parse(nowDate);
						
						Map<String, Object> acgzFlag = new HashMap<String, Object>();
						acgzFlag.put("acEmpCode",attenceDetails.getEmpCode());//哺乳期标识
						List<Attendance> acgzList = attendanceService.getPreAttendance(acgzFlag);
						int remarkFalg = 0;
						String acgzF = null;
						String stime =null;
						Date dt2 =null;
						String etime =null;
						Date dt3 =null;
						if(acgzList!=null&&acgzList.size()>0){
							for(Attendance attendance:acgzList){
								acgzF = attendance.getAcgzFlag();
								stime= attendance.getAcStartTime(); 
								dt2 = dfm.parse(stime);
								etime= attendance.getAcEndTime();
								dt3 = dfm.parse(etime);
							}
						}
						DecimalFormat df   = new DecimalFormat("#.0");
						SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
						Date gbnight2 = sdf.parse("21:00");
						Date gbnight3 = sdf.parse("20:00");
						Date morning = null;
						Date afternoon = null;
						//上下班打卡时间都不为空的情况
						if(attenceDetails.getMorningAttence()!=null&&attenceDetails.getAfternoonAttence()!=null){
							morning = sdf.parse(attenceDetails.getMorningAttence());
							afternoon = sdf.parse(attenceDetails.getAfternoonAttence());
							
							//公休日---加班时
							double weekTime = afternoon.getTime()-morning.getTime();
							double weekTime1 = weekTime/1000/60/60;
							double weekTime2 = Double.parseDouble(df.format(weekTime1));
							String we = null;
							if(weekTime2>=10){
								String s = new String(weekTime2+"");
								String b = s.substring(0, 2);
								String c= s.substring(3, 4);
								int d = Integer.parseInt(c);
								if(d>=0&&d<5){
									 we = b+"."+"0";
								}else if(d>=5&&d<=9){
									 we = b+"."+"5";
								}
							}else{
								String s = new String(weekTime2+"");
								String b = s.substring(0, 1);
								String c= s.substring(2, 3);
								int d = Integer.parseInt(c);
								if(d>=0&&d<5){
									 we = b+"."+"0";
								}else if(d>=5&&d<=9){
									 we = b+"."+"5";
								}
							}
							//1.工作日
							if(attenceDetails.getDateType()!=null&&attenceDetails.getDateType().equals("1")){
								if (acgzF!=null&&acgzF.equals("1")&&dt1.getTime()>=dt2.getTime()&&dt1.getTime()<=dt3.getTime()) {
									maps = cn.taskSys.utils.AttenceMath.modifyAttence1(remarkFalg, morning, afternoon, personAttence);
								}else{
									maps = cn.taskSys.utils.AttenceMath.modifyAttence(remarkFalg, morning, afternoon, personAttence);
								}
							//2.公休日
							}else{
									//分两种情况
									//1.打卡时间差大于8小时算一天出勤  按迟到计算 
									//计算出勤天数===========
									if(weekTime2>=8){
										maps.put("pAttenceChangeDay",Double.parseDouble(personAttence.getpAttenceChangeDay())+1);
										maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
										if(afternoon.getTime()>=gbnight2.getTime()){//大于21点
											maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
											maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+75);
											maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+50);
											maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
											
										}else if (afternoon.getTime()>=gbnight3.getTime() && afternoon.getTime()<gbnight2.getTime()) {//小于21点，大于20点
											maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
											maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
											maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+50);
											maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
										}else{//小于20点
											maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
											maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
											maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
											maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
										}
									}else if(weekTime2>=4&&weekTime2<8){
										maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
										maps.put("pAttenceChangeDay",Double.parseDouble(personAttence.getpAttenceChangeDay())+0.5);
										if(afternoon.getTime()>=gbnight2.getTime()){//大于21点
											maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
											maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
											maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
											maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
										}else if (afternoon.getTime()>=gbnight3.getTime() && afternoon.getTime()<gbnight2.getTime()) {//小于21点，大于20点
											maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
											maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
											maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
										}else{//小于20点
											maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
										}
									}else{
										maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(we));
									}
							}
							//修改考勤信息
							maps.put("pAddDate", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
							maps.put("pEmpCode", attenceDetails.getEmpCode());
							maps.put("pAttenceMonth", attenceDetails.getAttenceDate().substring(0, 7));//修改上次的考勤信息
							maps.put("pRemark", "");
							personAttenceService.updatePersonAttence(maps);
							attenceDetailsService.modifyRemarkFalg(attenceDetails.getId());//修改考勤信息后，更改标识符
						}
					}
				}
			}
		}
	}
	
	/**
	 * 考勤详情列表
	 * @param attenceDetails
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/queryAttenceDetails")
	public ModelAndView queryAttenceDetails(@ModelAttribute("attenceDetails")AttenceDetails attenceDetails, HttpServletRequest request, HttpServletResponse response){
		try {
			ModelAndView mv = new ModelAndView("/attence/attenceDetailsList");
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			//String loginName = user.getLoginName();
			String deparmentId = user.getDepartment_id();
			/*DataSourceContextHolder.setDbType("hwDataSource");//调用考勤数据源
			List<String> list = new ArrayList<String>();
			list.add(loginName);
			List<Map<String, String>> sysUsers = attenceService.findSysUsers(list);//所有在职人员
			DataSourceContextHolder.setDbType("dataSourceTask");//调用任务系统数据源 
			for (Map<String, String> sysUser : sysUsers) {
				String empCodeT = sysUser.get("empCode");
				if(loginName.equals(empCodeT)){
					deparmentId = sysUser.get("department");
				}
			}*/
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL"))) {//部门经理
				attenceDetails.setDepartment(deparmentId);
			}
//			if(attenceDetails.getAttenceDate()==null || attenceDetails.getAttenceDate().equals("")){
//				attenceDetails.setAttenceDate(DateUtil.DateToString(new Date(), "yyyy-MM"));
//			}
			mv.addObject("userRole", userRole);
			
			PageView<AttenceDetails> pageView  = attenceDetailsService.getAttenceDetailsListpageView(attenceDetails);
			
			mv.addObject("pageView", pageView);
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			mv.addObject("dictionaryLlist", dictionaryLlist);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 考勤详情导出Excel
	 * @param attenceDetails
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/exportAttence")
	public void exportAttence(@ModelAttribute("attenceDetails")AttenceDetails attenceDetails, HttpServletRequest request, HttpServletResponse response){
		try {
			
			List<AttenceDetails> attenceDetailsList = attenceDetailsService.getAttenceDetailsList(attenceDetails);
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			int count=0;
			if(attenceDetailsList!=null && attenceDetailsList.size()>0){
				count = attenceDetailsList.size();
				Map<String,Object> map = null;
				for (AttenceDetails ad : attenceDetailsList) {
					map = new HashMap<String,Object>();
					map.put("username", ad.getUsername()!=null ? ad.getUsername() : "");
					map.put("empCode", ad.getEmpCode()!=null ? ad.getEmpCode() : "");
					map.put("departmentName", ad.getDepartmentName()!=null ? ad.getDepartmentName() : "");
					map.put("attenceDate", ad.getAttenceDate()!=null ? ad.getAttenceDate() : "");
					map.put("weekName", ad.getWeekName()!=null ? ad.getWeekName() : "");
					map.put("dateTypeName", ad.getDateTypeName()!=null ? ad.getDateTypeName() : "");
					map.put("morningAttence", ad.getMorningAttence()!=null ? ad.getMorningAttence() : "");
					map.put("afternoonAttence", ad.getAfternoonAttence()!=null ? ad.getAfternoonAttence() : "");
					String remark = ad.getRemark();
					if(remark!=null && !remark.equals("")){
						if(remark.equals("1") || remark.equals("1all")){
							map.put("remark", "外出一天");
						}else if (remark.equals("1moring")) {
							map.put("remark", "上午外出");
						}else if (remark.equals("1afternoon")) {
							map.put("remark", "下午外出");
						}else if (remark.equals("2") || remark.equals("2all")) {
							map.put("remark", "请假一天");
						}else if (remark.equals("2moring")) {
							map.put("remark", "上午请假");
						}else if (remark.equals("2afternoon")) {
							map.put("remark", "下午请假");
						}else if (remark.equals("3")) {
							map.put("remark", "出差");
						}else{
							map.put("remark", "--");
						}
					}else{
						map.put("remark", "--");
					}
					
					list.add(map);
				}
				
				int n = 50000;		//定义每个sheet存放的数据条数
				String sheetName = "考勤详情";
				@SuppressWarnings("rawtypes")
				List<List> lists = new ArrayList<List>();
				
				String[] header = {"姓名","员工号","部门","考勤日期","星期","日期类型","上午考勤","下午考勤","备注"};
				String[][] headers = {header};
				String[] key = {"username","empCode","departmentName","attenceDate","weekName","dateTypeName","morningAttence","afternoonAttence","remark"};
				String[][] keys = {key};
				
				String dateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
				String fileName = "考勤详情"+dateStr+".xlsx";
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
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getDayOfMonth(String dateStr) throws Exception{  
		Date date = DateUtil.StringToDate(dateStr);
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		aCalendar.setTime(date);
		int maxDay=aCalendar.getActualMaximum(Calendar.DATE);
		String startDate = dateStr+"-01";
		String overDate = dateStr+"-"+maxDay;
		int day = DateUtil.getDutyDays(startDate,overDate);
		return day;
	}
	
	//获取当前时间
		public String getDate(int i){
			SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
			Calendar calendar = Calendar.getInstance();  
			Date nowDate = new Date();
			calendar.setTime(nowDate);  
			calendar.add(Calendar.MONTH, i); //1下一月  0当前月    -1上一月
			String lastMont = monthFormat.format(calendar.getTime());  

			return lastMont;
		}
		
		public static void main(String[] args) {
			int nDate = Integer.parseInt("2016-04-08".substring(8,10));
			System.out.println(nDate);
			/*String imTime = "2016-09";
			String year = imTime.substring(0, 4);
			String month = imTime.substring(5,7);
			int  a= Integer.parseInt(month);
			int  b= Integer.parseInt(year);
			String toDate =null;
			if(a>10){
				toDate = year + "-" + (a-1) + "";
			}
			if(a==10){
				toDate = year + "-09";
			}
			if(a<10&&a>1){
				toDate = year + "-0" + (a-1);
			}
			if(a==1){
				toDate = (b-1) + "12";
			}
			System.out.println(toDate);*/
		}
		
}
