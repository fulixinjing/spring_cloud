package cn.taskSys.controller.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.taskSys.controller.WorkTimeController;
import cn.taskSys.entity.PersonAttence;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.entity.WorkTime;
import cn.taskSys.service.IAdminService;
import cn.taskSys.service.IPersonAttenceService;
import cn.taskSys.service.IUserService;
import cn.taskSys.service.IWorkTimeService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.MathUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;


@Controller
/**
 * 
 * @author 
 *	功能：  每天4时30分0秒计算本月的工作量饱和度并保存，
 *  如果是1号则计算的是上月的饱和度
 */
public class TaskWorkTimeController {
	private static Logger logger = Logger.getLogger(TaskWorkTimeController.class);
	
	@Autowired
	private IWorkTimeService workTimeService;
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPersonAttenceService personAttenceService;
	
//	@Autowired
//	private UtilsService utilsService;
	
	
	/**
	 * 功能： 每天4时30分0秒计算本月的工作量饱和度并保存，
	 * 	          如果是1号则计算的是上月的饱和度
	 * @return
	 */
	@RequestMapping(value = "/executeTaskWorkTimeByDay")
	@ResponseBody
	public String executeTaskWorkTimeByDay() {
		try {
			Calendar instance = Calendar.getInstance();
			int day_of_month = instance.get(Calendar.DAY_OF_MONTH);
			String lastDate=getDate(0);//格式：yyyy-MM
			if(day_of_month==1){//如果是1号，获取上月
				lastDate=getDate(-1);
			}
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("lastDate", lastDate);
			List<TaskInfo> allTaskInfoList = adminService.getAllTaskInfoList2(maps);
			
			List<User> allGeneralUsers = userService.getAllGeneralUsers();//所有在部门经理以下的员工
			List<WorkTime> workTimeList = new ArrayList<WorkTime>();
			WorkTime workTimeTemp = null;
			/**********员工工作量及饱和度计算，暂存workTimeList中 start************/
			if(allGeneralUsers!=null && allGeneralUsers.size()>0){
				for (User userTemp : allGeneralUsers) {
					workTimeTemp = new WorkTime();
					workTimeTemp.setId(UUIDHexGenerator.generater());
					workTimeTemp.setUserid(userTemp.getUserId());
					workTimeTemp.setEmpCode(userTemp.getLoginName());
					workTimeTemp.setType("1");//1=员工，2=团队，3部门
					workTimeTemp.setStatisticsTime(lastDate);//yyyy-MM
					workTimeTemp.setAddTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					workTimeTemp.setDepartment(userTemp.getDepartment_id());
					workTimeTemp.setTeam(userTemp.getTeam_id());
					
					double totalWorkTime = 0;//总工作量
					double saturation = 0;//工作量饱和度
					/******统计已完成任务的工作量（finishedWorkTime）start*******/
					List<TaskInfo> Lists1 = new ArrayList<TaskInfo>();
					if(allTaskInfoList!=null && allTaskInfoList.size()>0){
						if(Lists1!=null && Lists1.size()>0){
							allTaskInfoList.removeAll(Lists1);
						}
						for (TaskInfo taskInfo : allTaskInfoList) {
							if(userTemp.getUserId().equals(taskInfo.getExecutedevtasksys())){
								String jxzDateStr = taskInfo.getJxzDate();
								String expectEndTime = taskInfo.getExpectEndTime();
								String monthString = getDate(0).substring(5, 7);
								if(day_of_month==1){//如果是1号，获取上月
									monthString = getDate(-1).substring(5, 7);
								}
								int effectiveDay = 0;//任务持续天数
								int allDay = 0;//任务所需总天数
								String taskWorkTime = taskInfo.getTaskWorkTime();
								if(taskWorkTime==null || taskWorkTime.equals("")){//防止空指针
									taskWorkTime="0";
								}
								String taskstatus = taskInfo.getTaskstatus();
								if(jxzDateStr.substring(5, 7).equals(monthString)){//不跨月
									/**
									 * 任务工作量（W）
									 * 不跨月   1完成任务    工作量=W
									 * 		2延期任务   工作量=W
									 * 		3进行中任务 工作量=W*【(当前时间-接收时间)/(期望时间-接收时间)】	
									 */
									if(taskstatus.equals("5") || taskstatus.equals("6") || taskstatus.equals("7") || taskstatus.equals("8")){
										totalWorkTime = totalWorkTime + Double.parseDouble(taskWorkTime);
									}else if (taskstatus.equals("4")) {
										totalWorkTime = totalWorkTime + Double.parseDouble(taskWorkTime);
									}else {
										effectiveDay = DateUtil.getDutyDays(jxzDateStr, getBeforeDay(new Date()));
										allDay = DateUtil.getDutyDays(jxzDateStr, expectEndTime);
										if(allDay==0){
											totalWorkTime = totalWorkTime + MathUtil.mul(Double.parseDouble(taskWorkTime), MathUtil.div(effectiveDay, allDay+0.1, 4));
										}else {
											totalWorkTime = totalWorkTime + MathUtil.mul(Double.parseDouble(taskWorkTime), MathUtil.div(effectiveDay, allDay, 4));
										}
									}
								}else{//跨月
									/**
									 * 任务工作量（W）
									 * 跨月    	1完成任务   未延期 工作量=W*【(提交时间-当月1号)/(提交时间-接收时间)】
									 * 			          延期   工作量=W*【(期望时间-当月1号)/(期望时间-接收时间)】，
									 * 			          如果延期完成需判断期望时间是否在当前月
									 * 		2延期任务   延期   工作量=W*【(期望时间-当月1号)/(期望时间-接收时间)】
									 * 		3进行中任务 工作量=W*【(当前时间-当月1号)/(期望时间-接收时间)】	
									 */
									String subdate = taskInfo.getSubDate();
									String tjtype = StringUtil.nvlString(taskInfo.getTjtype());
									if(taskstatus.equals("6") || taskstatus.equals("7") || tjtype.equals("6") || tjtype.equals("7")){//提前或按时提交
										effectiveDay = DateUtil.getDutyDays(lastDate+"-01",subdate);
										allDay = DateUtil.getDutyDays(jxzDateStr, subdate);
									}else if (taskstatus.equals("5") || tjtype.equals("5")) {//延期提交
										if(expectEndTime.substring(5, 7).equals(monthString)){//期望时间在当前月
											effectiveDay = DateUtil.getDutyDays(lastDate+"-01",expectEndTime);
										}else{//期望时间在上月，即期望时间与接收时间在同一个月
											effectiveDay = 0;
										}
										allDay = DateUtil.getDutyDays(jxzDateStr, expectEndTime);
									}else if (taskstatus.equals("4")) {
										effectiveDay = DateUtil.getDutyDays(lastDate+"-01",expectEndTime);
										allDay = DateUtil.getDutyDays(jxzDateStr, expectEndTime);
									}else {
										effectiveDay = DateUtil.getDutyDays(lastDate+"-01", getBeforeDay(new Date()));
										allDay = DateUtil.getDutyDays(jxzDateStr, expectEndTime);
									}
									if(allDay==0){
										totalWorkTime = totalWorkTime + MathUtil.mul(Double.parseDouble(taskWorkTime), MathUtil.div(effectiveDay, allDay+0.1, 4));
									}else {
										totalWorkTime = totalWorkTime + MathUtil.mul(Double.parseDouble(taskWorkTime), MathUtil.div(effectiveDay, allDay, 4));
									}
								}
								
								Lists1.add(taskInfo);
							}
							
						}
						
					}
					//查询实际出勤天数
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pEmpCode", userTemp.getLoginName());
					map.put("nowDate", lastDate);
					PersonAttence personAttence = personAttenceService.getPersonAttence(map);
					double days=0;
					if(personAttence!=null){
						String attenceDay = personAttence.getpAttenceDay();
						days = Double.parseDouble(attenceDay);//实际出勤天数
					}
					totalWorkTime = MathUtil.round(totalWorkTime, 2);
					if(days<=0){
						workTimeTemp.setSaturation("N/A");
					}else{
						saturation = MathUtil.round(getSaturation(totalWorkTime,days), 4);
						if(saturation>1.5){
							saturation=1.50;
						}
						workTimeTemp.setSaturation(saturation+"");
					}
					
					workTimeTemp.setAllWorkTime(totalWorkTime+"");
					
					workTimeList.add(workTimeTemp);
					
				}
			}
			/**********员工工作量及饱和度计算，暂存workTimeList中 end************/
			//保存到数据库中
			if(workTimeList!=null && workTimeList.size()>0){
				workTimeService.saveWorkTime(workTimeList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
		}
		
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
		int day = DateUtil.getDutyDays(startDate,overDate);
		return day;
	}
	
	
	private double getSaturation(double workTime, double days){
		try {
			double temp1 = MathUtil.div(workTime, MathUtil.mul(8, days), 4);
//			double temp2 = MathUtil.mul(8, temp1);
//			return MathUtil.div(temp2, 6, 4);
			return temp1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取指定日期前一天
	 * @param date
	 * @return
	 */
	public static String getBeforeDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return DateUtil.DateToString(date, "yyyy-MM-dd");
	}
	
	public static void main(String[] args) throws Exception {
		long temp1 = 8;
		
		double temp2 = temp1;
		System.out.println(temp2);
		System.out.println(new WorkTimeController().getDate(-1).substring(5, 7));
		System.out.println(getBeforeDay(new Date()));
	}
}
