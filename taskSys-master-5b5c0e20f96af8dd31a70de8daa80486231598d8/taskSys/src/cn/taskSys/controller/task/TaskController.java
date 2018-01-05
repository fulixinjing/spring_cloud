package cn.taskSys.controller.task;

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

import cn.taskSys.entity.TaskInfo;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.SimpleMailSend;
import cn.taskSys.utils.StringUtil;


@Controller
/**
 * 
 * @author sfl
 *	定时判断任务提交延期发送提醒及邮件
 */
public class TaskController {
	private static Logger logger = Logger.getLogger(TaskController.class);
	@Autowired
	private UtilsService utilsService;
	
	/**
	 * extension remind 延期提醒
	 * 每日16点定时判断任务提交延期发送提醒及邮件
	 * @return
	 */
	@RequestMapping(value = "/executeTaskEr")
	@ResponseBody
	public String taskExecuteTaskLater() {
		String nowDate=getDate(0);
		Date nowDate1 = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(nowDate1);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, +1);  //设置为前一天
		String nowDate2 = DateUtil.DateToString(calendar.getTime(), "yyyy-MM-dd");//得到后一天的时间
		try {
				String ad="内网 访问地址：http://10.150.80.218:8080/taskSys";
				String ad1="其它办公区  访问地址：http://210.51.169.181/taskSys";
			//获取延期所有符合的时间
			List<TaskInfo> taskInfoList=utilsService.getTaskInfoLaterList(nowDate2);
			System.out.println(taskInfoList);
			//组装listMap添加提醒及修改加标记
			List<Map<String,String>> mapTxList=new ArrayList<Map<String,String>>();
			
			List<Map<String,String>> mapMailList=new ArrayList<Map<String,String>>();
			if(taskInfoList!=null&&taskInfoList.size()>0){
				for(int i=0;i<taskInfoList.size();i++){
					TaskInfo ti=taskInfoList.get(i);
					String endDate="";
					if(!"".equals(ti.getExpectEndTime()) && ti.getExpectEndTime()!=null){
						 endDate=ti.getExpectEndTime().substring(0, 10);
					}
					String content=ti.getTaskContent();
					
					//要循环发送两条信息一个是创建者，一是执行者
					//for(int j=0;j<=1;j++){
						Map<String,String> mapTx=new HashMap<String,String>();
						Map<String,String> mapMail=new HashMap<String,String>();
						
						
						
						mapTx.put("taskInfoId", StringUtil.nvlString(ti.getId()));//任务ID 
						//if(j==0){
							mapTx.put("receiverUserId", StringUtil.nvlString(ti.getExecutedevtasksys()));//执行者
							mapTx.put("title", "任务管理系统-您负责的一项任务即将（一天后）延期");
							
							mapMail.put("to_email", ti.getExeEmail());								//接收人邮件地址
							mapMail.put("title", "任务管理系统-您负责的一项任务即将（一天后）延期");		//邮件标题
							mapMail.put("context", "您好：\n    您负责的一项任务："+content+"，创建人为："+ti.getCreate_name()+"，计划完成时间为："+endDate+"，该任务即将（一天后）延期，请及时处理。\n			链接地址："+ad+"\n     "+ad1);		//邮件内容
							//mapMail.put("recipients", value);	//抄送人邮件地址
						mapTx.put("senderUserId", "888999");
						mapTx.put("sendTime", nowDate);
						mapTx.put("isread","0");
						mapTx.put("isdel","0");
						mapTx.put("content","");
						mapTxList.add(mapTx);
						
						
						mapMailList.add(mapMail);
				}
			}
			if(mapTxList!=null&&mapTxList.size()>0){
				//新增提醒方法
				insertTccs(mapTxList);
			}
			//邮件发送
			if(mapMailList!=null&&mapMailList.size()>0){
				for(int i=0;i<mapMailList.size();i++){
					String to_email=mapMailList.get(i).get("to_email");		//接收人邮件地址
					String title=mapMailList.get(i).get("title");			//邮件标题
					String context=mapMailList.get(i).get("context");		//邮件内容
					String recipients="";//mapMailList.get(i).get("recipients");	//抄送人邮件地址
					
					SimpleMailSend.sendMail(to_email, title, context, recipients);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			System.out.println("定时(延期)出现--------------问题");
		}
		System.out.println("定时(延期)--------------成功");
		return "";
	}
	
	public String taskExecuteTaskEr() {
		String nowDate=getDate(0);
		try {
				String ad="内网 访问地址：http://10.150.80.218:8080/taskSys";
				String ad1="其它办公区  访问地址：http://210.51.169.181/taskSys";
			//获取延期所有符合的时间
			List<TaskInfo> taskInfoList=utilsService.getTaskInfoList();
			//组装listMap添加提醒及修改加标记
			List<Map<String,String>> mapList=new ArrayList<Map<String,String>>();
			List<Map<String,String>> mapTxList=new ArrayList<Map<String,String>>();
			
			List<Map<String,String>> mapMailList=new ArrayList<Map<String,String>>();
			if(taskInfoList!=null&&taskInfoList.size()>0){
				for(int i=0;i<taskInfoList.size();i++){
					TaskInfo ti=taskInfoList.get(i);
					Map<String,String> mapTi=new HashMap<String,String>();
					mapTi.put("rwid", StringUtil.nvlString(ti.getId()));//任务ID taskInfoId
					mapTi.put("falRed", "0");		//0是延期，1是未延期
					mapTi.put("taskstatus", "4");	//0是延期，1是未延期
					mapList.add(mapTi);
					
					String endDate="";
					if(!"".equals(ti.getExpectEndTime()) && ti.getExpectEndTime()!=null){
						 endDate=ti.getExpectEndTime().substring(0, 10);
					}
					String content=ti.getTaskContent();
					
					//要循环发送两条信息一个是创建者，一是执行者
					for(int j=0;j<=1;j++){
						Map<String,String> mapTx=new HashMap<String,String>();
						Map<String,String> mapMail=new HashMap<String,String>();
						
						
						
						mapTx.put("taskInfoId", StringUtil.nvlString(ti.getId()));//任务ID 
						if(j==0){
							mapTx.put("receiverUserId", StringUtil.nvlString(ti.getExecutedevtasksys()));//执行者
							mapTx.put("title", "任务管理系统-您负责的一项任务已延期");
							
							mapMail.put("to_email", ti.getExeEmail());								//接收人邮件地址
							mapMail.put("title", "任务管理系统-您负责的一项任务已延期");		//邮件标题
							mapMail.put("context", "您好：\n    您负责的一项任务："+content+"，创建人为："+ti.getCreate_name()+"，计划完成时间为："+endDate+"，目前该任务已经延期，请及时处理。\n			链接地址："+ad+"\n     "+ad1);		//邮件内容
							//mapMail.put("recipients", value);	//抄送人邮件地址
						}else{
							mapTx.put("receiverUserId", StringUtil.nvlString(ti.getAllocationUser()));//创建人
							mapTx.put("title", "您分配的一项任务已延期");
							mapMail.put("to_email", ti.getCreateEmail());								//接收人邮件地址
							mapMail.put("title", "任务管理系统-您分配的一项任务已延期");		//邮件标题
							mapMail.put("context", "您好：\n    您创建的一项任务："+content+"，\n     负责人为："+ti.getExec_name()+"，计划完成时间为："+endDate+"，目前该任务已经延期，请及时跟踪。\n     链接地址："+ad+"\n     "+ad1);		//邮件内容
							//mapMail.put("recipients", value);	//抄送人邮件地址
						}
						
						mapTx.put("senderUserId", "888999");
						mapTx.put("sendTime", nowDate);
						mapTx.put("isread","0");
						mapTx.put("isdel","0");
						mapTx.put("content","");
						mapTxList.add(mapTx);
						
						
						mapMailList.add(mapMail);
					}
				}
			}
			if(mapList!=null&&mapList.size()>0){
				//修改任务表-加red
				updateTccsList(mapList);
			}
			if(mapTxList!=null&&mapTxList.size()>0){
				//新增提醒方法
				insertTccs(mapTxList);
			}
			//邮件发送
			if(mapMailList!=null&&mapMailList.size()>0){
				for(int i=0;i<mapMailList.size();i++){
					String to_email=mapMailList.get(i).get("to_email");		//接收人邮件地址
					String title=mapMailList.get(i).get("title");			//邮件标题
					String context=mapMailList.get(i).get("context");		//邮件内容
					String recipients="";//mapMailList.get(i).get("recipients");	//抄送人邮件地址
					
					SimpleMailSend.sendMail(to_email, title, context, recipients);
				}
				
			}
		} catch (Exception e) {
			logger.debug(e);
			System.out.println("定时(延期)出现--------------问题");
		}
		System.out.println("定时(延期)--------------成功");
		return "";
	}
	
	/**
	 * 修改任务表-加red
	 * @param map
	 * @return
	 */
	
	public void updateTccsList(List<Map<String,String>> updateList){
		try{
			//修改方法
			if(updateList!=null&&updateList.size()>0){

				int init = 1000;// 每隔1000条循环一次  
				int total = updateList.size();  
				int cycelTotal = total / init;  
				if (total % init != 0) {  
					cycelTotal += 1;  
					if (total < init) {  
						init = updateList.size();  
					}  
				}
				List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();  

				for (int i = 0; i < cycelTotal; i++) {  
					for (int j = 0; j < init; j++) {  
						if(j<updateList.size()){
							if (updateList.get(j) == null) {  
								continue;  
							}  
							list2.add(updateList.get(j));  
						}
					}  
					//每次循环保存的数据输出 
					utilsService.updateTaskInfo(list2);
					//接下来写保存数据库方法  
					updateList.removeAll(list2);//移出已经保存过的数据  
					list2.clear();//移出当前保存的数据  
				}
				
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 增加提醒表情况-list 
	 * @param map
	 * @return
	 */
	public void insertTccs(List<Map<String,String>>  mapList){
		try{
			//新增方法
			if(mapList!=null&&mapList.size()>0){
				
				int init = 1000;// 每隔1000条循环一次  
				int total = mapList.size();  
				int cycelTotal = total / init;  
				if (total % init != 0) {  
					cycelTotal += 1;  
					if (total < init) {  
						init = mapList.size();  
					}  
				}
				List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();  

				for (int i = 0; i < cycelTotal; i++) {  
					for (int j = 0; j < init; j++) {  
						if(j<mapList.size()){
							if (mapList.get(j) == null) {  
								continue;  
							}  
							list2.add(mapList.get(j));  
						}
					}  
					//每次循环保存的数据输出 
					utilsService.insertTxList(list2);
					//接下来写保存数据库方法  
					mapList.removeAll(list2);//移出已经保存过的数据  
					list2.clear();//移出当前保存的数据  
				}
				
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//获取当前时间
	public String getDate(int i){
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();  
		Date nowDate = new Date();
		calendar.setTime(nowDate);  
		calendar.add(Calendar.MONTH, i); //1下一月  0当前月    -1上一月
		String lastMont = monthFormat.format(calendar.getTime());  

		return lastMont;
	}
}
