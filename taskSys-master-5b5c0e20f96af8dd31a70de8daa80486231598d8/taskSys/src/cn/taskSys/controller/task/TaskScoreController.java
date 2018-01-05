package cn.taskSys.controller.task;

import java.math.BigDecimal;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Grades;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.entity.UserScore;
import cn.taskSys.entity.WorkTime;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.IAdminService;
import cn.taskSys.service.IUserService;
import cn.taskSys.service.IWorkTimeService;
import cn.taskSys.service.RoleService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.ExportExcelUtil;
import cn.taskSys.utils.MathUtil;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;


@Controller
/**
 * 
 * @author sfl
 *	功能： 每天5时0分0秒计算本月的得分并保存
 */
public class TaskScoreController {
	private static Logger logger = Logger.getLogger(TaskScoreController.class);
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UtilsService utilsService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IWorkTimeService workTimeService;
	
	
	/**
	 * 功能： 每天5时0分0秒计算本月的得分并保存
	 * Time:2016-10-12
	 * @return
	 */
	@RequestMapping(value = "/executeScoreByDay")
	@ResponseBody
	public void executeScoreByDay() {
		Calendar instance = Calendar.getInstance();
		int day_of_month = instance.get(Calendar.DAY_OF_MONTH);
		String lastDate=getDate(0);//格式：yyyy-MM
		if(day_of_month==1){//如果是1号，获取上月
			lastDate=getDate(-1);
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
											gradesTemp.setSaturationScore(saturationScore+"");
											gradesTemp.setgScores(MathUtil.round(allScore, 1)+"");
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	
	/**
	 * 功能： 每天5时0分0秒计算本月的得分并保存
	 * @return
	 */
	@RequestMapping(value = "/executeScoreTaskEr")
	@ResponseBody
	public String taskScoreExecuteTaskEr() {
		Calendar instance = Calendar.getInstance();
		int day_of_month = instance.get(Calendar.DAY_OF_MONTH);
		String lastDate=getDate(0);//格式：yyyy-MM
		if(day_of_month==1){//如果是1号，获取上月
			lastDate=getDate(-1);
		}
		
		try {
			
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("lastDate", lastDate);
			List<Grades> gradesList = new ArrayList<Grades>();
			Grades gradesTemp = null;
			List<User> userList = userService.getAllUserList();//所有员工
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
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("subDate", lastDate);
						map.put("userId", user.getUserId());
						double departWorkScore = 0;
						if(taskCount>0){
							//double workDay = getDayOfMonth(lastDate);
							double a = countScore/taskCount/8 * 10;//任务得分率=【已完成任务的总得分/（已完成任务数量*8）】*10
							double a1 = Double.parseDouble(df.format(a));
							double b = (countStutas1*1.5+countStutas2*1+countStutas3*0.5)/taskCount*15;//任务完成状态分=（提前完成任务数*1.5+按时完成任务数*1+延期完成任务数*0.5）/完成任务总数量*15;
							double b1 = Double.parseDouble(df.format(b));
							//double c = forDate/workDay*10;
							double c1 = Double.parseDouble(df.format(10));//工作效率分
							double totleScore = Double.parseDouble(df.format(60 + a1 + b1 + c1));//总得分=基础分+任务得分率+任务完成状态分+工作效率分
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
								/**
								 * 任务得分与饱和度关联
								 */
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
								}
								
							}
						}else {
							if(userRole.equals("BMJL")&&userRole!=null){
								gradesTemp.setgScores("0");
								gradesTemp.setgRemark("没有已完成的任务");
								gradesTemp.setgState("2");
							}
	//						taskInfoList.removeAll(taskInfoListFul);
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
					}else {
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
		}
		System.out.println(lastDate);
		return "";
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
	
	public static int getDayOfMonth(String dateStr) throws Exception{  
		Date date = DateUtil.StringToDate(dateStr);
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		aCalendar.setTime(date);
		int maxDay=aCalendar.getActualMaximum(Calendar.DATE);
		String startDate = dateStr+"-01";
		String overDate = dateStr+"-"+maxDay;
		int day = getDates(startDate,overDate);
		return day;
	}
	
	//计算工作日
	private static int getDates(String startDate,String overDate) throws Exception{    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begDate = sdf.parse(startDate);
        Date endDate = sdf.parse(overDate);
        if (begDate.after(endDate))
            throw new Exception("日期范围非法");
        // 总天数
        int days = (int) ((endDate.getTime() - begDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;
        // 总周数，
        int weeks = days / 7;
        int rs = 0;
        // 整数周
        if (days % 7 == 0) {
            rs = days - 2 * weeks;
        }
        else {
            Calendar begCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            begCalendar.setTime(begDate);
            endCalendar.setTime(endDate);
            // 周日为1，周六为7
            int beg = begCalendar.get(Calendar.DAY_OF_WEEK);
            int end = endCalendar.get(Calendar.DAY_OF_WEEK);
            if (beg > end) {
                rs = days - 2 * (weeks + 1);
            } else if (beg < end) {
                if (end == 7) {
                    rs = days - 2 * weeks - 1;
                } else {
                    rs = days - 2 * weeks;
                }
            } else {
                if (beg == 1 || beg == 7) {
                    rs = days - 2 * weeks - 1;
                } else {
                    rs = days - 2 * weeks;
                }
            }
        } 
        return rs;
    }    
	/*private static Integer countDayTime(String endDate,String createDate) {
		Long endDateLong = DateUtil.StringToDate(endDate, "yyyy-MM-dd").getTime();
		Long createDateLong = DateUtil.StringToDate(createDate, "yyyy-MM-dd").getTime();
		Long forDate=(endDateLong-createDateLong)/(24*60*60*1000L);
		return forDate.intValue()+1;
	}*/
	
	@RequestMapping(value = "/admin/listScore")
	@LogAnnotation(eventCode="1023033",eventProcess="")
	public ModelAndView getListScore(@ModelAttribute("userScore")UserScore userScore, HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/admin/listScore");
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			String deparmentId = user.getDepartment_id();
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL"))) {//部门经理
				userScore.setDepartment(deparmentId);
			}
			mv.addObject("userRole", userRole);
			
			if(userScore.getgMonth()==null || "".equals(userScore.getgMonth())){
				userScore.setgMonth(getDate(0));
			}
			PageView<UserScore> pageView  = new PageView<UserScore>();
			pageView = adminService.getScoreListpageView(userScore);	
			
			mv.addObject("pageView", pageView);
			
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			
			mv.addObject("dictionaryLlist", dictionaryLlist);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/admin/exportScore")
	@LogAnnotation(eventCode="1023023",eventProcess="")
	public void exportScore(@ModelAttribute("userScore")UserScore userScore, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			List<UserScore> userScoreList = adminService.getScoreList2(userScore);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			int count = 0;
			if (userScoreList != null && userScoreList.size()>0) {
				count = userScoreList.size();
				//UserScore==>Map
				Map<String,Object> map = null;
				for (UserScore t : userScoreList) {
					map = new HashMap<String,Object>();
					map.put("login_name", StringUtil.nvlString(t.getLogin_name()));
					map.put("user_name", StringUtil.nvlString(t.getUser_name()));
					map.put("department_Name", StringUtil.nvlString(t.getDepartment_Name()));
					map.put("team_Name", StringUtil.nvlString(t.getTeam_Name()));
					map.put("position_Name", StringUtil.nvlString(t.getPosition_Name()));
					map.put("post_Name", StringUtil.nvlString(t.getPost_Name()));
					map.put("g_scores", StringUtil.nvlString(t.getG_scores()));
					map.put("g_task_score", StringUtil.nvlString(t.getG_task_score()));
					map.put("g_task_status_score", StringUtil.nvlString(t.getG_task_status_score()));
					map.put("g_saturation_score", StringUtil.nvlString(t.getG_saturation_score()));
					map.put("g_month", StringUtil.nvlString(t.getG_month()));
					map.put("g_remark", StringUtil.nvlString(t.getG_remark()));
					
					list.add(map);
				}
			}
			
			int n = 50000;		//定义每个sheet存放的数据条数
			String sheetName = "任务得分统计";
			@SuppressWarnings("rawtypes")
			List<List> lists = new ArrayList<List>();
			
			String[] header = {"员工号","姓名","所属部门","所属团队","职位","职位类别","得分","任务打分","任务状态分","饱和度得分","统计月份","备注"};
			String[][] headers = {header};
			String[] key = {"login_name","user_name","department_Name","team_Name","position_Name","post_Name","g_scores","g_task_score","g_task_status_score","g_saturation_score","g_month","g_remark"};
			String[][] keys = {key};
			
			String dateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
			String fileName = "绩效分数统计"+dateStr+".xlsx";
			
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
	
	public static void main(String[] args) throws Exception {
		String str = new TaskScoreController().getDate(-1);
		System.out.println(TaskScoreController.getDayOfMonth(str));
	}
	
}
