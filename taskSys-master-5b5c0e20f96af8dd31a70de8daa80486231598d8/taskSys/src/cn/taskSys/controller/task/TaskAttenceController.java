package cn.taskSys.controller.task;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.entity.AttenceTemp;
import cn.taskSys.entity.Attendance;
import cn.taskSys.entity.PersonAttence;
import cn.taskSys.mybatis.DataSourceContextHolder;
import cn.taskSys.service.IAttenceDetailsService;
import cn.taskSys.service.IAttenceService;
import cn.taskSys.service.IAttendanceService;
import cn.taskSys.service.IPersonAttenceService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.HolidayUtils;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;

@Controller
public class TaskAttenceController {
	private static Logger logger = Logger.getLogger(TaskAttenceController.class);
	
	@Autowired
	private IAttenceDetailsService attenceDetailsService;
	
	@Autowired
	private IAttenceService attenceService;
	
	@Autowired
	private IPersonAttenceService personAttenceService;
	
	@Autowired
	private IAttendanceService attendanceService;

	/**
	 * 功能：  每天2时0分0秒计算前一天的打卡记录并保存
	 * @return
	 */
	@RequestMapping(value = "/executeAttenceByDay")
	@ResponseBody
	public String executeAttenceByDay() {
		try {
//			String lastDay=DateUtil.DateToString(new Date(), "yyyy-MM-dd");//格式：yyyy-MM-dd
			String lastDay=getSpecifiedDayBefore(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));//格式：yyyy-MM-dd
			//String lastDay="2016-04-05";
			List<Map<String, String>> users = attenceService.findUsers();//所有在职人员
			List<String> empCodes = new ArrayList<String>();
			if(users!=null && users.size()>0){
				for (Map<String, String> user : users) {
					empCodes.add(user.get("empcode"));
				}
			}
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("currentDate", lastDay);
			maps.put("empCodes", empCodes);
			DataSourceContextHolder.setDbType("hwDataSource");//调用考勤数据源
			List<Map<String, String>> sysUsers = attenceService.findSysUsers(empCodes);//科技公司所有人员
			List<AttenceTemp> punchCardRecordList = attenceService.punchCardRecordList(maps);//考勤记录
			maps.put("currentMonth", lastDay.substring(0, 7));
			List<AttenceTemp> laterPunchCardRecordList = attenceService.punchCardRecordList2(maps);//新入职员工后来同步到数据库的考勤记录
			DataSourceContextHolder.setDbType("dataSourceTask");//调用任务系统数据源 
			AttenceDetails attenceDetails = null;
			List<AttenceDetails> attenceDetailsList = new ArrayList<AttenceDetails>();
			
			HolidayUtils holiday = new HolidayUtils();
			holiday.setDay(lastDay);
			if(sysUsers!=null && sysUsers.size()>0){
				List<AttenceTemp> lists = null;
				for (Map<String, String> sysUser : sysUsers) {
					lists = new ArrayList<AttenceTemp>();//暂存该员工考勤记录
					String empCodeT = sysUser.get("empCode");
					attenceDetails = new AttenceDetails();
					attenceDetails.setId(UUIDHexGenerator.generater());
					attenceDetails.setUsername(sysUser.get("username"));
					attenceDetails.setEmpCode(empCodeT);
					attenceDetails.setDepartment(sysUser.get("department"));
					attenceDetails.setAttenceDate(lastDay);//考勤日期
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DateUtil.StringToDate(lastDay));
					int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
					if(day==0){
						day=7;
					}
					attenceDetails.setWeek(day+"");//星期 1=周一，2=周二，3=周三，4=周四，5=周五，6=周六，7=周日
					
					if(holiday.isWorkFlag()){
						attenceDetails.setDateType("1");//日期类型 1=工作日,2=公休日
					}else{
						attenceDetails.setDateType("2");
					}
					attenceDetails.setAttenceType("9:00--18:00");//考勤制度
					
					if(punchCardRecordList!=null && punchCardRecordList.size()>0){//计算考勤记录
						for (AttenceTemp attenceTemp : punchCardRecordList) {
							if(empCodeT.equals(attenceTemp.getEmpCode())){
								lists.add(attenceTemp);
							}
						}
						if(lists!=null && lists.size()>0){//从考勤记录中获取早、晚考勤
							Date dateT = DateUtil.StringToDate(lastDay+" 12:00:00", "yyyy-MM-dd HH:mm:ss");
							if(lists.size()==1){
								String punchCardTimeStr = lists.get(0).getPunchCardTime();
								Date date1 = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								if(date1.after(dateT)){
									attenceDetails.setAfternoonAttence(punchCardTimeStr.substring(11, 19));
								}else{
									attenceDetails.setMorningAttence(punchCardTimeStr.substring(11, 19));
								}
							}else{
								String punchCardTimeStr = lists.get(0).getPunchCardTime();
								Date minDate = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								Date maxDate = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								for (int i = 0; i < lists.size(); i++) {
									String punchCardTime = lists.get(i).getPunchCardTime();
									Date date1 = DateUtil.StringToDate(punchCardTime, "yyyy-MM-dd HH:mm:ss");
									if(date1.before(minDate)){minDate = date1;}
									if(date1.after(maxDate)){maxDate = date1;}
								}
								
								attenceDetails.setMorningAttence(DateUtil.DateToString(minDate, "yyyy-MM-dd HH:mm:ss").substring(11, 19));
								
								attenceDetails.setAfternoonAttence(DateUtil.DateToString(maxDate, "yyyy-MM-dd HH:mm:ss").substring(11, 19));
								
							}
						}
					}
					
					attenceDetailsList.add(attenceDetails);
					
				}
			}
			
			if(attenceDetailsList!=null && attenceDetailsList.size()>0){
				List<AttenceDetails> laterAttenceDetailsList = null;
				if(laterPunchCardRecordList!=null && laterPunchCardRecordList.size()>0){
					laterAttenceDetailsList= getAttenceDetailsList(laterPunchCardRecordList, sysUsers);
				}
				attenceService.saveAttenceDetails(attenceDetailsList, laterAttenceDetailsList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
		}
		
		return "";
	}
	
	/**
	 * 手动执行导入考勤
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/attence/executeAttenceByDayTemp")
	@ResponseBody
	public void executeAttenceByDayTemp(HttpServletRequest request,HttpServletResponse response) {
		try {
			String lastDay = StringUtil.nvlString(request.getParameter("attenceDate"));
			if(lastDay==null || lastDay.equals("")){
				lastDay=DateUtil.DateToString(new Date(), "yyyy-MM-dd");//格式：yyyy-MM-dd
			}
			List<Map<String, String>> users = attenceService.findUsers();//所有在职人员
			List<String> empCodes = new ArrayList<String>();
			if(users!=null && users.size()>0){
				for (Map<String, String> user : users) {
					empCodes.add(user.get("empcode"));
				}
			}
			
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("currentDate", lastDay);
			maps.put("empCodes", empCodes);
			DataSourceContextHolder.setDbType("hwDataSource");//调用考勤数据源
			List<Map<String, String>> sysUsers = attenceService.findSysUsers(empCodes);//科技公司所有人员
			List<AttenceTemp> punchCardRecordList = attenceService.punchCardRecordList(maps);//考勤记录
			maps.put("currentMonth", lastDay.substring(0, 7));
			List<AttenceTemp> laterPunchCardRecordList = attenceService.punchCardRecordList2(maps);//新入职员工后来同步到数据库的考勤记录
			DataSourceContextHolder.setDbType("dataSourceTask");//调用任务系统数据源 
			AttenceDetails attenceDetails = null;
			List<AttenceDetails> attenceDetailsList = new ArrayList<AttenceDetails>();
			
			HolidayUtils holiday = new HolidayUtils();
			holiday.setDay(lastDay);
			if(sysUsers!=null && sysUsers.size()>0){
				List<AttenceTemp> lists = null;
				for (Map<String, String> sysUser : sysUsers) {
					lists = new ArrayList<AttenceTemp>();//暂存该员工考勤记录
					String empCodeT = sysUser.get("empCode");
					attenceDetails = new AttenceDetails();
					attenceDetails.setId(UUIDHexGenerator.generater());
					attenceDetails.setUsername(sysUser.get("username"));
					attenceDetails.setEmpCode(empCodeT);
					attenceDetails.setDepartment(sysUser.get("department"));
					attenceDetails.setAttenceDate(lastDay);//考勤日期
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DateUtil.StringToDate(lastDay));
					int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
					if(day==0){
						day=7;
					}
					attenceDetails.setWeek(day+"");//星期 1=周一，2=周二，3=周三，4=周四，5=周五，6=周六，7=周日
					
					if(holiday.isWorkFlag()){
						attenceDetails.setDateType("1");//日期类型 1=工作日,2=公休日
					}else{
						attenceDetails.setDateType("2");
					}
					attenceDetails.setAttenceType("9:00--18:00");//考勤制度
					
					if(punchCardRecordList!=null && punchCardRecordList.size()>0){//计算考勤记录
						for (AttenceTemp attenceTemp : punchCardRecordList) {
							if(empCodeT.equals(attenceTemp.getEmpCode())){
								lists.add(attenceTemp);
							}
						}
						if(lists!=null && lists.size()>0){//从考勤记录中获取早、晚考勤
							Date dateT = DateUtil.StringToDate(lastDay+" 12:00:00", "yyyy-MM-dd HH:mm:ss");
							if(lists.size()==1){
								String punchCardTimeStr = lists.get(0).getPunchCardTime();
								Date date1 = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								if(date1.after(dateT)){
									attenceDetails.setAfternoonAttence(punchCardTimeStr.substring(11, 19));
								}else{
									attenceDetails.setMorningAttence(punchCardTimeStr.substring(11, 19));
								}
							}else{
								String punchCardTimeStr = lists.get(0).getPunchCardTime();
								Date minDate = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								Date maxDate = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								for (int i = 0; i < lists.size(); i++) {
									String punchCardTime = lists.get(i).getPunchCardTime();
									Date date1 = DateUtil.StringToDate(punchCardTime, "yyyy-MM-dd HH:mm:ss");
									if(date1.before(minDate)){minDate = date1;}
									if(date1.after(maxDate)){maxDate = date1;}
								}
								
								attenceDetails.setMorningAttence(DateUtil.DateToString(minDate, "yyyy-MM-dd HH:mm:ss").substring(11, 19));
								
								attenceDetails.setAfternoonAttence(DateUtil.DateToString(maxDate, "yyyy-MM-dd HH:mm:ss").substring(11, 19));
								
							}
						}
					}
					
					attenceDetailsList.add(attenceDetails);
					
				}
			}
			
			JSONObject  json=new JSONObject();
			if(attenceDetailsList!=null && attenceDetailsList.size()>0){
				List<AttenceDetails> laterAttenceDetailsList = null;
				if(laterPunchCardRecordList!=null && laterPunchCardRecordList.size()>0){
					laterAttenceDetailsList= getAttenceDetailsList(laterPunchCardRecordList, sysUsers);
				}
				attenceService.saveAttenceDetails(attenceDetailsList, laterAttenceDetailsList);
				json.put("result", "ok");
			}
			
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
		}
		
	}
	/**
	 * 统计新入职员工后来同步到数据库的考勤记录
	 * @param laterPunchCardRecordList
	 * @param sysUsers
	 * @return
	 */
	private List<AttenceDetails> getAttenceDetailsList (List<AttenceTemp> laterPunchCardRecordList, List<Map<String, String>> sysUsers){
		try {
			Set<String> empcodeSet = new HashSet<String>();
			Set<String> attenceDateSet = new HashSet<String>();
			Map<String, Map<String, String>> userMap = new HashMap<String, Map<String, String>>();
			for (AttenceTemp at : laterPunchCardRecordList) {
				for (Map<String, String> sysUser : sysUsers) {
					String empCodeT = sysUser.get("empCode");
					if(empCodeT.equals(at.getEmpCode())){
						empcodeSet.add(at.getEmpCode());//员工号集合
						userMap.put(empCodeT, sysUser);//暂存用户信息
					}
				}
				attenceDateSet.add(at.getPunchCardTime().substring(0, 10));//考勤日期集合
			}
			
			AttenceDetails attenceDetails = null;
			List<AttenceDetails> attenceDetailsList = new ArrayList<AttenceDetails>();
			Iterator<String> it = attenceDateSet.iterator();
			while (it.hasNext()) {
				String attenceDate = it.next();
				
				List<AttenceTemp> lists = null;
				Iterator<String> it2 = empcodeSet.iterator();
				while (it2.hasNext()) {
					lists = new ArrayList<AttenceTemp>();//暂存该员工考勤记录
					String empCodeT = it2.next();
					attenceDetails = new AttenceDetails();
					attenceDetails.setId(UUIDHexGenerator.generater());
					attenceDetails.setUsername(userMap.get(empCodeT).get("username"));
					attenceDetails.setEmpCode(empCodeT);
					attenceDetails.setDepartment(userMap.get(empCodeT).get("department"));
					attenceDetails.setAttenceDate(attenceDate);//考勤日期
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DateUtil.StringToDate(attenceDate));
					int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
					if(day==0){
						day=7;
					}
					attenceDetails.setWeek(day+"");//星期 1=周一，2=周二，3=周三，4=周四，5=周五，6=周六，7=周日
					
					HolidayUtils holiday = new HolidayUtils();
					holiday.setDay(attenceDate);
					if(holiday.isWorkFlag()){
						attenceDetails.setDateType("1");//日期类型 1=工作日,2=公休日
					}else{
						attenceDetails.setDateType("2");
					}
					attenceDetails.setAttenceType("9:00--18:00");//考勤制度
					attenceDetails.setRemarkFlag("1");//新入职员工后来同步到数据库的考勤记录默认1，个人中心统计后改为0
					
					
					if(laterPunchCardRecordList!=null && laterPunchCardRecordList.size()>0){//计算考勤记录
						for (AttenceTemp attenceTemp : laterPunchCardRecordList) {
							//判断 是否是当前（遍历）员工 && 考勤日期是否是当前遍历的 日期
							//lists暂存该员工该天打卡次数
							if(empCodeT.equals(attenceTemp.getEmpCode()) && attenceTemp.getPunchCardTime().startsWith(attenceDate)){
								lists.add(attenceTemp);
							}
						}
						if(lists!=null && lists.size()>0){//从考勤记录中获取早、晚考勤
							Date dateT = DateUtil.StringToDate(attenceDate+" 12:00:00", "yyyy-MM-dd HH:mm:ss");
							if(lists.size()==1){//说明该天只打卡一次
								String punchCardTimeStr = lists.get(0).getPunchCardTime();
								Date date1 = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								if(date1.after(dateT)){//若该天只有一次打卡，判断是上午打卡还是下午打卡
									attenceDetails.setAfternoonAttence(punchCardTimeStr.substring(11, 19));
								}else{
									attenceDetails.setMorningAttence(punchCardTimeStr.substring(11, 19));
								}
							}else{//说明该天打卡次数>=2,从中获取最早和最晚的一次打卡时间分别作为上午打卡时间和下午打卡时间
								String punchCardTimeStr = lists.get(0).getPunchCardTime();
								Date minDate = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								Date maxDate = DateUtil.StringToDate(punchCardTimeStr, "yyyy-MM-dd HH:mm:ss");
								for (int i = 0; i < lists.size(); i++) {
									String punchCardTime = lists.get(i).getPunchCardTime();
									Date date1 = DateUtil.StringToDate(punchCardTime, "yyyy-MM-dd HH:mm:ss");
									if(date1.before(minDate)){minDate = date1;}
									if(date1.after(maxDate)){maxDate = date1;}
								}
								
								attenceDetails.setMorningAttence(DateUtil.DateToString(minDate, "yyyy-MM-dd HH:mm:ss").substring(11, 19));
								
								attenceDetails.setAfternoonAttence(DateUtil.DateToString(maxDate, "yyyy-MM-dd HH:mm:ss").substring(11, 19));
								
							}
						}
					}
					attenceDetailsList.add(attenceDetails);
					
				}
				
			}
			
			return attenceDetailsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 每天  6点  执行保存考勤状况记录
	 * @param  :
	 * @return :String
	 * @author :ruiy
	 * @throws Exception 
	 * @date   :2016-6-3上午9:59:16
	 *
	 */
	@RequestMapping(value = "/savePersonAttence")
	@ResponseBody
	public void savePersonAttence() throws Exception {
		String nowDate=getSpecifiedDayBefore(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));//格式：yyyy-MM-dd
		//String nowDate = DateUtil.DateToString(new Date(), getSpecifiedDayBefore(DateUtil.DateToString(new Date(), "yyyy-MM-dd")));
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd"); 
		Date dt1 = dfm.parse(nowDate);
		String nDate = nowDate.substring(0, 7);
		unAttence(nowDate);
		//查询所有用户
		//List<User> userList = userService.getAllUserList();
		//查询当前月所有打卡信息
		List<AttenceDetails> list = attenceDetailsService.getAllAttence(nowDate);
		Map<String, Object> map = null;
		Map<String, Object> maps = null;
		Map<String, Object> map1 = null;
		Map<String, Object> acgzFlag = null;
		if(list!=null&&list.size()>0){
			for(AttenceDetails attenceDetails:list){
				int remarkFalg=2;
				String acgzF = null;
				map = new HashMap<String, Object>();
				maps = new HashMap<String, Object>();
				map1 = new HashMap<String, Object>();
				acgzFlag = new HashMap<String, Object>();
				if(attenceDetails.getEmpCode()!=null){
					//查询personAttence数据根据id
					map.put("nowDate", nDate);
					map.put("pEmpCode",attenceDetails.getEmpCode());
					PersonAttence personAttence = personAttenceService.getPersonAttence(map);
					
					map1.put("acEmpCode", attenceDetails.getEmpCode());
					List<Attendance> list2 = attendanceService.getPreAttendance(map1);
					
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
										}else if(afternoon.getTime()>=gbnight3.getTime() && afternoon.getTime()<gbnight2.getTime()){//小于21点，大于20点
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
							maps.put("pDepCode", attenceDetails.getDepartment());
							maps.put("pAttenceMonth", nDate);//修改上次的考勤信息
							maps.put("pRemark", "");
							personAttenceService.updatePersonAttence(maps);
						}
					}
				}
			}
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
											//maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
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
	//获取指定时间上一天
	private static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数  
		Calendar c = Calendar.getInstance();  
		Date date = null;  
		try {  
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);  
			c.setTime(date);  
			int day = c.get(Calendar.DATE);  
			c.set(Calendar.DATE, day - 1);  
			
			String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  
			return dayBefore;  
		} catch (ParseException e) {  
			e.printStackTrace();  
		}  
		return null;  
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
	
	
	public static void main(String[] args) throws Exception {
		String nowDate=getSpecifiedDayBefore(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));//格式：yyyy-MM-dd
		//String nowDate = DateUtil.DateToString(new Date(), getSpecifiedDayBefore(DateUtil.DateToString(new Date(), "yyyy-MM-dd")));
		String nDate = nowDate.substring(0, 7);
		
		System.out.println(nowDate+nDate);
		
	}
}
