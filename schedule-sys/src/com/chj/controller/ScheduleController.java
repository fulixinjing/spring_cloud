package com.chj.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chj.model.Login;
import com.chj.model.Schedule;
import com.chj.pageUtil.Page;
import com.chj.service.ScheduleService;
import com.chj.util.CommonUtil;
import com.chj.util.StringUtil;

/**
 * 日程安排
 * @author flx
 *
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 今日安排列表
	 * @return
	 */
	@RequestMapping("/now")
	public String now(Schedule schedule,Model model){
		Page<Schedule> page = scheduleService.getfindList(schedule);
		model.addAttribute("page",page);
		if(StringUtil.notEmpty(schedule.getMonth())){
			return "schedule";
		}
		return "now_schedule";
	}
	/**
	 * 跳转 新增页面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAddSchedule(){
		
		return "addSchedule";
	}
	/**
	 * 跳转 修改页面
	 * @return
	 */
	@RequestMapping("/toUpate")
	public String toUpateSchedule(Schedule schedule,Model model){
		
		Schedule schedule2 = scheduleService.getScheduleById(schedule);
		String startDate = schedule2.getStartDate();
		if(StringUtil.notEmpty(startDate)){
			schedule2.setStartDate(startDate.substring(0, startDate.length()-2));
		}
		String endDate = schedule2.getEndDate();
		if(StringUtil.notEmpty(endDate)){
			schedule2.setEndDate(endDate.substring(0, endDate.length()-2));
		}
		model.addAttribute("schedule",schedule2);
		return "updateSchedule";
	}
	
	/**
	 * 保存任务
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addSchedule")
	public String addSchedule(Schedule schedule,HttpServletRequest request,HttpServletResponse response){
		if(StringUtil.isEmpty(schedule.getId())){//新增
			Login login = (Login) request.getSession().getAttribute(CommonUtil.LOGIN_TYPE);
			schedule.setUserId(login.getId());
			schedule.setCreateTime(new Date());
			scheduleService.addSchedule(schedule);
		}else{//修改
			scheduleService.updateSchedule(schedule);
		}
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/delSchedule")
	public String delSchedule(Schedule schedule){
		
		scheduleService.delSchedule(schedule);
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/toRemind")
	public Map<String,Object> toRemind(HttpServletRequest request){
		Login login = (Login) request.getSession().getAttribute(CommonUtil.LOGIN_TYPE);
		HashMap<String, Object> remind = scheduleService.remind(login.getId());
		return remind;
	}
}
