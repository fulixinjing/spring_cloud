package cn.taskSys.controller.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.taskSys.entity.StatCard;
import cn.taskSys.entity.SysBranch;
import cn.taskSys.entity.SysUser;
import cn.taskSys.entity.SysUserBranch;
import cn.taskSys.mybatis.DataSourceContextHolder;
import cn.taskSys.service.IAttenceService;
import cn.taskSys.service.IStatCardService;
import cn.taskSys.utils.DateUtil;

@Controller
public class TaskSysController {
	private static Logger logger = Logger.getLogger(TaskSysController.class);
	
	@Autowired
	private IAttenceService attenceService;
	
	@Autowired
	private IStatCardService statCardService;

	/**
	 * 功能：  每天1时0分0秒同步MySQL信息
	 * @return
	 */
	@RequestMapping(value = "/executeSysInfoByDay")
	@ResponseBody
	public String executeSysInfoByDay() {
		try {
			String lastDay=getSpecifiedDayBefore(DateUtil.DateToString(new Date(), "yyyy-MM-dd"));//格式：yyyy-MM-dd
			List<Map<String, String>> users = attenceService.findUsers();//所有在职人员
			List<String> empcodes = new ArrayList<String>();
			if(users!=null && users.size()>0){
				for (Map<String, String> user : users) {
					empcodes.add(user.get("empcode"));
				}
			}
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("currentDate", lastDay);
			maps.put("currentMonth", lastDay.substring(0, 7));
			maps.put("empcodes", empcodes);
			DataSourceContextHolder.setDbType("hwDataSource");//调用考勤数据源
			List<StatCard> statCards = statCardService.findStatCardList(maps);
			List<SysBranch> sysBranchs = statCardService.findSysBranchList(maps);
			List<SysUserBranch> sysUserBranchs = statCardService.findSysUserBranchList(maps);
			List<SysUser> sysUsers = statCardService.findSysUserList(maps);
			DataSourceContextHolder.setDbType("dataSourceTask");//调用任务系统数据源 
			
			statCardService.saveStatCard(statCards,sysBranchs,sysUserBranchs,sysUsers);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
		}
		
		return "";
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
	
}
