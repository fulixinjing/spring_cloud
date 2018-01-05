package cn.taskSys.controller;


import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Log;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.LogService;
import cn.taskSys.utils.DateUtil;


@Controller
@RequestMapping(value="/log")
public class LogAction extends BaseAction{
	
//	@Autowired
//	private  HttpServletRequest request; 
	@Autowired
	private LogService logService;
	
	/**
	 * 查询日志
	 */
	@RequestMapping(value = "/getLoglistg", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1020001",eventProcess="")
	public String getLoglistg(Model m, HttpSession session, HttpServletRequest request) {
		HashMap<String , Object> map=new HashMap<String , Object>();
		Log log = new Log();
		int offset = 0;
		Integer pagesize = (Integer) request.getSession().getAttribute("ps");
		if (request.getParameter("pager.offset") != null) {
			offset = Integer.parseInt(request.getParameter("pager.offset"));
			if(session.getAttribute("log") != null){
				log=(Log)session.getAttribute("log");
			}
		}else{
			session.removeAttribute("log");
		}	
		//页面加载时设定默认时间查询区间
		if(log.getLogdate()==null){
			log.setLogdate(DateUtil.getNowTime(-1));
		}
		if(log.getLastlogdate()==null){
			String nowDateString = DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
			log.setLastlogdate(DateUtil.StringToDate(nowDateString));
		}
		
		//翻页开始记录
		map.put("begin", offset);
		map.put("nowNum", 10);//当前显示几条
		//翻页结束记录
		map.put("end", offset + pagesize);
		map.put("log",log);
		PagerModel pclist = logService.getLogList(map);
		m.addAttribute("pclist", pclist);
		m.addAttribute("log", log);
		return "log/loglist";
	}
	
	/**
	 * 日志条件查询
	 */
	@RequestMapping(value = "/getLoglistp", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1020002",eventProcess="")
	public String getLoglistp(@ModelAttribute("log")Log log,Model m, HttpSession session, HttpServletRequest request) {
		HashMap<String , Object> map=new HashMap<String , Object>();
		int offset = 0;
		Integer pagesize = (Integer) request.getSession().getAttribute("ps");
		if (request.getParameter("pager.offset") != null) {
			offset = Integer.parseInt(request.getParameter("pager.offset"));
		}	
		
		/*if(session.getAttribute("s_cjlender") != null){
			lender=(Lender)session.getAttribute("s_cjlender");
		}*/
		if(log.getLastlogdate()!=null && !log.getLastlogdate().equals("")){
			String lastlogdateStr = DateUtil.DateToString(log.getLastlogdate(), "yyyy-MM-dd")+" 23:59:59";
			log.setLastlogdate(DateUtil.StringToDate(lastlogdateStr));
		}
		//翻页开始记录
		map.put("begin", offset);
		map.put("nowNum", 10);//当前显示几条
		//翻页结束记录
		map.put("end", offset + pagesize);
		map.put("log",log);
		session.setAttribute("log", log);
		PagerModel pclist = logService.getLogList(map);
		m.addAttribute("pclist", pclist);
		m.addAttribute("log", log);
		return "log/loglist";
	}
	
	
	
}
