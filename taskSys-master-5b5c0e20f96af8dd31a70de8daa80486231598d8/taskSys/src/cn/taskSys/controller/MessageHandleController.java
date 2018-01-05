package cn.taskSys.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.taskSys.entity.Message;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.IAdminService;
import cn.taskSys.service.IMessageService;
import cn.taskSys.service.RoleService;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.StringUtil;

@Controller
@RequestMapping(value="/message")
public class MessageHandleController {

	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private RoleService roleService;
	/**
	 * 点击消息调到对应任务
	 * @param request
	 * @param response
	 * @param ra
	 */
	@RequestMapping(value="/messageHandle")
	@LogAnnotation(eventCode="1117010", eventProcess="")
	public void getMessage(HttpServletRequest request, HttpServletResponse response, RedirectAttributes ra){
		try {
			String result = null;
			String userId = PublicUtil.getUserOnline(request).getUserId();
			String id = StringUtil.nvlString(request.getParameter("id"));
			Message message = messageService.getMessageById(id);
			if (message.getIsread()!=null && message.getIsread().equals("0")) {
				messageService.updateMessage(message.getId());
				
				String taskInfoId = message.getTaskInfoId();
				TaskInfo taskInfo = adminService.getTaskInfoById(taskInfoId);
				
				String taskStatus = null;
				String flag = null;
				String focusMenu=null;
				if (taskInfo!=null) {
					List<Role> roles = roleService.getRoleByUserId(userId);
					String userRole = null;
					for (Role role : roles) {
						if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
							userRole = role.getRoleCode();
							break;
						}
					}
					taskStatus = taskInfo.getTaskstatus();
					//高管
					if (userRole.equals("boss")) {
						if (taskStatus.equals("1")) {
							//拒绝接收任务==>我的工作台-待分配...
							flag = "1";
							result = "/admin/myWorkbenchList.do";
							focusMenu="31";
						}else if (taskStatus.equals("2") || taskStatus.equals("3") || taskStatus.equals("4")) {
							//我的任务==>进行中...
							flag = "5";
							result = "/director/myTaskBoss.do";
							focusMenu="32";
						}else if (taskStatus.equals("5") || taskStatus.equals("6") || taskStatus.equals("7")) {
							//我的工作台==>待确认...
							flag = "3";
							ra.addAttribute("id", taskInfoId);
							result = "/admin/myWorkbenchList.do";
							focusMenu="31";
						}else if (taskStatus.equals("8")) {
							//我的任务==>已完成...
							flag = "6";
							result = "/director/myTaskBoss.do";
							focusMenu="32";
						}
					}else if (userRole.equals("director") || userRole.equals("BMJL") || userRole.equals("XMJL") || userRole.equals("TDJL") || userRole.equals("PMO001") || userRole.equals("PMUPDATE")) {//主管
						if (taskStatus.equals("1")) {
							if (taskInfo.getAllocationUser().equals(userId)) {
								//拒绝接收任务==>我的工作台-待分配...
								flag = "1";
								result = "/admin/myWorkbenchList.do";
								focusMenu="21";
							}else {
								flag = "2";
								result = "/admin/myWorkbenchList.do";
								focusMenu="21";
							}
						}else if (taskStatus.equals("2")) {
							//......(工作台==>待接收)
							flag = "2";
							result = "/admin/myWorkbenchList.do";
							focusMenu="21";
						}else if (taskStatus.equals("3")) {
							if (taskInfo.getAllocationUser().equals(userId)) {
								//......(我创建的任务==>进行中)
								flag = "1";
								result = "/director/mySetTask.do";
								focusMenu="22";
							}else if (taskInfo.getExecutedevtasksys().equals(userId)) {
								//......(我负责的任务==>进行中)
								flag = "2";
								result = "/director/myTask.do";
								focusMenu="23";
							}
						}else if (taskStatus.equals("4")) {
							if (taskInfo.getAllocationUser().equals(userId)) {
								//......(我创建的任务==>进行中)
								flag = "1";
								result = "/director/mySetTask.do";
								focusMenu="22";
							}else if (taskInfo.getExecutedevtasksys().equals(userId)) {
								//......(我负责的任务==>进行中)
								flag = "2";
								result = "/director/myTask.do";
								focusMenu="23";
							}
						}else if (taskStatus.equals("5") || taskStatus.equals("6") || taskStatus.equals("7")) {
							if (taskInfo.getAllocationUser().equals(userId)) {
								//我的工作台==>待确认...
								flag = "3";
								ra.addAttribute("id", taskInfoId);
								result = "/admin/myWorkbenchList.do";
								focusMenu="21";
							}else if (taskInfo.getExecutedevtasksys().equals(userId)) {
								//......(我负责的任务==>已提交)
								flag = "3";
								result = "/director/myTask.do";
								focusMenu="23";
							}
						}else if (taskStatus.equals("8")) {
							if (taskInfo.getAllocationUser().equals(userId)) {
								//......(我创建的任务==>已完成)
								flag = "2";
								result = "/director/mySetTask.do";
								focusMenu="22";
							}else if (taskInfo.getExecutedevtasksys().equals(userId)) {
								//我负责的任务==>已完成...
								flag = "4";
								result = "/director/myTask.do";
								focusMenu="23";
							}
						}
					}else if (userRole.equals("staff")) {//员工
						if (taskStatus.equals("1") || taskStatus.equals("2")) {
							//......(工作台==>待接收)
							flag = "2";
							result = "/admin/myWorkbenchList.do";
							focusMenu="11";
						}else if (taskStatus.equals("3") || taskStatus.equals("4")) {
							//我的任务==>进行中...
							flag = "2";
							result = "/director/myTaskBoss.do";
							focusMenu="12";
						}else if (taskStatus.equals("5") || taskStatus.equals("6") || taskStatus.equals("7")) {
							//我的任务==>已提交...
							flag = "3";
							result = "/director/myTaskBoss.do";
							focusMenu="12";
						}else if (taskStatus.equals("8")) {
							//我的任务==>已完成...
							flag = "4";
							result = "/director/myTaskBoss.do";
							focusMenu="12";
						}
					}
					
				}
				
				List<Message> messages = messageService.getMessage(userId);
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				Map<String, Object> map = null;
				if (messages!=null && messages.size()>0) {
					for (Message message2 : messages) {
						map = new HashMap<String, Object>();
						map.put("id", message2.getId());
						map.put("title", message2.getTitle());
						list.add(map);
					}
				}
				JSONObject  json=new JSONObject();
				json.put("list", list);
				json.put("result", result);
				json.put("taskStatus", taskStatus);
				json.put("taskInfoId", taskInfoId);
				json.put("flag", flag);
				json.put("focusMenu", focusMenu);
				
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				out.write(json.toString());
			}
			
//			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return null;
		
	}
	/**
	 * 点击消息将消息变为已读，并将相关过时消息标为已读
	 * @param request
	 * @param response
	 * @param ra
	 */
	@RequestMapping(value="/messageIgnore")
	@LogAnnotation(eventCode="1117011", eventProcess="")
	public void messageIgnore(HttpServletRequest request, HttpServletResponse response, RedirectAttributes ra){
		String userId = PublicUtil.getUserOnline(request).getUserId();
		String taskInfoId = StringUtil.nvlString(request.getParameter("taskInfoId"));
		String messageStatus = StringUtil.nvlString(request.getParameter("messageStatus"));
		try{
			Map<String,String> map = new HashMap<String, String>();
			map.put("messageStatus", messageStatus);
			map.put("taskInfoId", taskInfoId);
			map.put("userId", userId);
			List<Message> list = messageService.getMessageForList(map);
			if(list.size()>0){
				for(Message message:list){
					String taskInfoId2 = message.getTaskInfoId();
					messageService.updateMessageForTaskId(taskInfoId2);
				}
			}
			
			List<Message> messages = messageService.getMessage(userId);
			List<Map<String,Object>> mesgList = new ArrayList<Map<String,Object>>();
			Map<String, Object> map1 = null;
			if (messages!=null && messages.size()>0) {
				for (Message message2 : messages) {
					map1 = new HashMap<String, Object>();
					map1.put("id", message2.getId());
					map1.put("title", message2.getTitle());
					mesgList.add(map1);
				}
			}
			
			JSONObject  json=new JSONObject();
			json.put("list", list);
			json.put("mesgList", mesgList);
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
		}catch (Exception e) {
			// TODO: handle exception
		}
 	}
}
