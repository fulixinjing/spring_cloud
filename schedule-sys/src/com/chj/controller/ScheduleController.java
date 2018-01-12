package com.chj.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chj.model.Login;
import com.chj.model.Schedule;
import com.chj.service.ScheduleService;
import com.chj.util.CommonUtil;

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
	 * 保存任务
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addSchedule")
	public String addSchedule(Schedule schedule,HttpServletRequest request,HttpServletResponse response){
		Login login = (Login) request.getSession().getAttribute(CommonUtil.LOGIN_TYPE);
		schedule.setUserId(login.getId());
		schedule.setCreateTime(new Date());
		scheduleService.addSchedule(schedule);
		return "true";
	}
}
