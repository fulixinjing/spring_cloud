package cn.taskSys.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;



/**
 * 邮件发送
 * to_email 接收人邮箱；title 邮件标题；context 邮件内容；recipients 抄送人邮件地址；
 * @author 
 */
public class SendEmailUtil {

	public static String sendTaskEmail(Map<String,String> map){
		String ad="内网访问地址：http://10.150.80.218:8080/taskSys/";
		String ad1="其它办公区  访问地址：http://210.51.169.181/taskSys";
		String to_email=map.get("to_email");		//接收人邮件地址
		 String title="任务管理系统—您有一条新任务";			//邮件标题
		 String context= "您好：\n   "+map.get("create_name")+" 给您分配了一项新任务："+map.get("taskContent")+"。 \n   计划完成时间为："+map.get("expectEndTime")+"，请及时处理。\n			链接地址："+ad+"\n     "+ad1;		//邮件内容
		String recipients="";//mapMailList.get(i).get("recipients");	//抄送人邮件地址
		
		SimpleMailSend.sendMail(to_email, title, context, recipients);
		return null;
	}
	//转交任务时向转交负责人的部门经理发送待通过的邮件提醒
	public static String deliverTaskEmail(Map<String,String> map){
		String ad="内网访问地址：http://10.150.80.218:8080/taskSys/";
		String ad1="其它办公区  访问地址：http://210.51.169.181/taskSys";
		String to_email=map.get("to_email");		//接收人邮件地址
		 String title="任务管理系统—您有一条新消息";			//邮件标题
		 String context= "您好：\n   "+map.get("create_name")+" 给您转交了一项待通过的新任务："+map.get("taskContent")+"。请及时处理。\n			链接地址："+ad+"\n     "+ad1;		//邮件内容
		String recipients="";//mapMailList.get(i).get("recipients");	//抄送人邮件地址
		
		SimpleMailSend.sendMail(to_email, title, context, recipients);
		return null;
	}
	
	//新建成员时发送邮件提醒
		public static String newPeopleEmail(Map<String,String> map){
			String ad="内网访问地址：http://10.150.80.218:8080/taskSys/";
			String ad1="其它办公区  访问地址：http://210.51.169.181/taskSys";
			String to_email=map.get("to_email");		//接收人邮件地址
			String title="任务管理系统—您有一条新消息";			//邮件标题
			String context= "您好：\n   "+"您的任务管理系统账号： "+map.get("loginName")+" 初始化成功，密码为123456，为保证正常使用，请您务必修改密码。"+" \n     链接地址："+ad+"\n       "+ad1;		//邮件内容
			//String context= "您好：\n   "+map.get("create_name")+" 给您转交了一项待通过的新任务："+map.get("taskContent")+"。请及时处理。\n			链接地址："+ad+"\n     "+ad1;		//邮件内容
			context+="\n\n\n"+context();
			String recipients="";//mapMailList.get(i).get("recipients");	//抄送人邮件地址
			
			SimpleMailSend.sendMail(to_email, title, context, recipients);
			return null;
		}
	
	//获取当前时间
	public static String getDate(int i){
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();  
		Date nowDate = new Date();
		calendar.setTime(nowDate);  
		calendar.add(Calendar.MONTH, i); //1下一月  0当前月    -1上一月
		String lastMont = monthFormat.format(calendar.getTime());  

		return lastMont;
	}

	public static String context(){
		String context=null;
		context= "                                      任务管理系统简介说明   \n"

		 
		+"     任务管理系统是科技公司开发的一套任务管理和考核的工具，主要应用于将员工开展的各类任务进行统一管理，并由直接主管针对任务\n完成情况进行打分，最终系统自动计算得到员工的每月考核分数。 \n"
		+"	         公司要求每位员工都要应用任务管理系统来管理自己的日常工作任务，否则可能会影响员工的月度考核分数。 \n"
		+"     如果您是基层员工，请及时联系您的直接主管在系统里给您分配任务。如果您是主管，可以由您的上级给您分配任务，或者由您给自己分\n配任务，以便您的工作任务能及时完整地录入到系统。 \n"
		+"     该系统不同于项目管理系统，侧重点不同，使用特点为“线上+线下”。即首先在线下做好充分沟通，接下来在系统线上进行记录、跟踪和评价打分。 \n"
		 
		+"   任务管理系统访问地址为：http://10.150.80.218:8080/taskSys  \n" 
		+"   其它办公区  访问地址：http://210.51.169.181/taskSys \n" 
		+"      【注：google浏览器、火狐浏览器、IE浏览器支持较好，360浏览器不支持】 \n"
		+"l  登录用户名：各自的员工编号 \n"
		+"l  登录密码：默认123456（进去后修改） \n"
		+"l  系统使用咨询和支持，请联系董文艺：wenyidong@creditharmony.cn \n"
		+"l  部门用户账号信息添加、删除、修改、权限变更等系统管理问题，请联系李小彤：xiaotongli@creditharmony.cn \n"
		+"l  系统bug反馈，请联系孙凡磊：fanleisun@creditharmony.cn \n"
		+"l  系统需求优化建议，请联系邱士宗、马平波：shizongqiu@creditharmony.cn、  pingboma@creditharmony.cn \n";
		
		return context;
	}
}
