package cn.taskSys.controller.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.entity.OALeave;
import cn.taskSys.entity.OAOnBusiness;
import cn.taskSys.entity.OAOut;
import cn.taskSys.service.IOALeaveService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.HolidayUtils;
import cn.taskSys.utils.UtilValidate;


@Controller
/**
 * 
 * @author 
 *	功能：  每天3时0分0秒根据导入OA数据更新考勤详情备注
 *  
 */
public class TaskOAController {
	private static Logger logger = Logger.getLogger(TaskOAController.class);
	
	@Autowired
	private IOALeaveService oALeaveService;
	
	@RequestMapping(value = "/executeTaskOAByDay")
	@ResponseBody
	public String executeTaskByDay() {
		try {
			/**
			 * 暂存请假已结束的OALeave对象，最后将leaveListTemp中的
			 * 对象字段(is_update)更新为1
			 */
			List<OALeave> leaveListTemp = new ArrayList<OALeave>();
			/**
			 * 暂存请假已结束的OAOut对象，最后将outListTemp中的
			 * 对象字段(is_update)更新为1
			 */
			List<OAOut> outListTemp = new ArrayList<OAOut>();
			/**
			 * 暂存请假已结束的OAOnBusiness对象，最后将onBusinessListTemp中的
			 * 对象字段(is_update)更新为1
			 */
			List<OAOnBusiness> onBusinessListTemp = new ArrayList<OAOnBusiness>();
			
			//查询未更新的OA数据
			List<OALeave> leaveList = oALeaveService.getUnUpdateLeaveList(null);
			List<OAOut> outList = oALeaveService.getUnUpdateOutList(null);
			List<OAOnBusiness> onBusinessList = oALeaveService.getUnUpdateOnBusinessList(null);
			
			List<AttenceDetails> attenceDetailsList = new ArrayList<AttenceDetails>();
			AttenceDetails ad = null;
			String nowDateStr = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
			Date nowDate = DateUtil.StringToDate(nowDateStr);
			/**************请假OA数据******************/
			if(UtilValidate.isNotEmpty(leaveList)){
				/**
				 * 导入保存数据库后，更新考勤详情备注
				 * 请假计算规则：
				 * (要判断结束日期是否大于等于当前日期，如果是则直接更改考勤备注，否则数据库不存在考勤数据，不能更改考勤备注)
				 * 1.先判断天数（days）：
				 * 1）days=0.5
				 * 2)days=1，判断是否跨天
				 * 2)days>1，跨天
				 * 2.请假不跨天：开始日期==结束日期-->判断开始时间和结束时间-->请假一天还是半天
				 * 3.请假跨天：开始日期!=结束日期-->判断开始时间和结束时间-->请假天数
				 * 
				 * 请假类型：4=病假，5=事假，6=婚假，7=丧假，8=产假，9=年假
				 * all=全天，moring=上午请假，afternoon=下午请假
				 */
				
				for (OALeave oaLeave : leaveList) {
					double days = Double.parseDouble(oaLeave.getDays());
					Date endDate = DateUtil.StringToDate(oaLeave.getEnd_date(), "yyyy-MM-dd");
					Date startDate = DateUtil.StringToDate(oaLeave.getStart_date(), "yyyy-MM-dd");
					Date startTime = DateUtil.StringToDate(oaLeave.getStart_time(), "HH:mm");
					Date endTime = DateUtil.StringToDate(oaLeave.getEnd_time(), "HH:mm");
					if(endDate.before(nowDate)){
						if(days==0.5){
							ad = new AttenceDetails();
							ad.setEmpCode(oaLeave.getEmp_code());
							ad.setAttenceDate(oaLeave.getStart_date());
							if (endTime.compareTo(DateUtil.StringToDate("12:00", "HH:mm"))<=0) {//<=12:00,上午请假
								this.setRemark(oaLeave.getType(), ad, "moring");
							}else if (startTime.compareTo(DateUtil.StringToDate("13:00", "HH:mm"))>=0) {//>=13:00,下午请假
								this.setRemark(oaLeave.getType(), ad, "afternoon");
							}
							attenceDetailsList.add(ad);
						}else if (days==1) {
							if(startDate.compareTo(endDate)==0){//请假不跨天
								ad = new AttenceDetails();
								ad.setEmpCode(oaLeave.getEmp_code());
								ad.setAttenceDate(oaLeave.getStart_date());
								this.setRemark(oaLeave.getType(), ad, "all");
								
								attenceDetailsList.add(ad);
							}else {
								//跨天,两个半天
								int dutyDays = DateUtil.getAllDays(oaLeave.getStart_date(), oaLeave.getEnd_date());
								for (int i = 0; i < dutyDays; i++) {
									ad = new AttenceDetails();
									ad.setEmpCode(oaLeave.getEmp_code());
									
									HolidayUtils hu = new HolidayUtils();
									if(i==0 || i==dutyDays-1){//首天和末天可能出现半天请假
										if(i==0){//首天即为考勤日期
											hu.setDay(oaLeave.getStart_date());
											if(hu.isWorkFlag()){
												ad.setAttenceDate(oaLeave.getStart_date());
											}
											if (startTime.compareTo(DateUtil.StringToDate("12:00", "HH:mm"))>=0) {//>=12:00,下午请假
												this.setRemark(oaLeave.getType(), ad, "afternoon");
											}else{//全天请假
												this.setRemark(oaLeave.getType(), ad, "all");
											}
										}else if (i==dutyDays-1) {//末天即为考勤日期
											hu.setDay(oaLeave.getEnd_date());
											if(hu.isWorkFlag()){
												ad.setAttenceDate(oaLeave.getEnd_date());
											}
											if (endTime.compareTo(DateUtil.StringToDate("13:00", "HH:mm"))<=0) {//<=13:00,上午请假
												this.setRemark(oaLeave.getType(), ad, "moring");
											}else{//全天请假
												this.setRemark(oaLeave.getType(), ad, "all");
											}
										}
										
										attenceDetailsList.add(ad);
									}else{//全天请假   并计算考勤日期
										long milliseconds = startDate.getTime()+i*24*60*60*1000L;
										Date newDate = new Date(milliseconds);
										String newDateStr = DateUtil.DateToString(newDate, "yyyy-MM-dd");
										
										hu.setDay(newDateStr);
										if(hu.isWorkFlag()){
											ad.setAttenceDate(newDateStr);
											this.setRemark(oaLeave.getType(), ad, "all");
											
											attenceDetailsList.add(ad);
										}
									}
								}
							}
							
						}else if (days>1) {
							/**
							 * 跨天请假，先计算所跨天数，并根据天数遍历每天请假的情况（全天、上午、下午），
							 * 计算天数需考虑非工作日
							 */
							
							int dutyDays = DateUtil.getAllDays(oaLeave.getStart_date(), oaLeave.getEnd_date());
							for (int i = 0; i < dutyDays; i++) {
								ad = new AttenceDetails();
								ad.setEmpCode(oaLeave.getEmp_code());
								
								HolidayUtils hu = new HolidayUtils();
								if(i==0 || i==dutyDays-1){//首天和末天可能出现半天请假
									if(i==0){//首天即为考勤日期
										hu.setDay(oaLeave.getStart_date());
										if(hu.isWorkFlag()){
											ad.setAttenceDate(oaLeave.getStart_date());
										}
										if (startTime.compareTo(DateUtil.StringToDate("12:00", "HH:mm"))>=0) {//>=12:00,下午请假
											this.setRemark(oaLeave.getType(), ad, "afternoon");
										}else{//全天请假
											this.setRemark(oaLeave.getType(), ad, "all");
										}
									}else if (i==dutyDays-1) {//末天即为考勤日期
										hu.setDay(oaLeave.getEnd_date());
										if(hu.isWorkFlag()){
											ad.setAttenceDate(oaLeave.getEnd_date());
										}
										if (endTime.compareTo(DateUtil.StringToDate("13:00", "HH:mm"))<=0) {//<=13:00,上午请假
											this.setRemark(oaLeave.getType(), ad, "moring");
										}else{//全天请假
											this.setRemark(oaLeave.getType(), ad, "all");
										}
									}
									
									attenceDetailsList.add(ad);
								}else{//全天请假   并计算考勤日期
									long milliseconds = startDate.getTime()+i*24*60*60*1000L;
									Date newDate = new Date(milliseconds);
									String newDateStr = DateUtil.DateToString(newDate, "yyyy-MM-dd");
									
									hu.setDay(newDateStr);
									if(hu.isWorkFlag()){
										ad.setAttenceDate(newDateStr);
										this.setRemark(oaLeave.getType(), ad, "all");
										
										attenceDetailsList.add(ad);
									}
								}
							}
						}
						leaveListTemp.add(oaLeave);
					}else {//OA上提请假，结束日期在当前时间之后,只需计算days>1的情况即可
						if(startDate.compareTo(nowDate)<0){//说明请假已开始，还未结束
							//当前日期前一天
							int dutyDays = DateUtil.getAllDays(oaLeave.getStart_date(), oaLeave.getEnd_date());
							for (int i = 0; i < dutyDays; i++) {
								ad = new AttenceDetails();
								ad.setEmpCode(oaLeave.getEmp_code());
								
								HolidayUtils hu = new HolidayUtils();
								if(i==0 || i==dutyDays-1){//首天和末天可能出现半天请假
									if(i==0){//首天即为考勤日期
										hu.setDay(oaLeave.getStart_date());
										if(hu.isWorkFlag()){
											ad.setAttenceDate(oaLeave.getStart_date());
										}
										if (startTime.compareTo(DateUtil.StringToDate("12:00", "HH:mm"))>=0) {//>=12:00,下午请假
											this.setRemark(oaLeave.getType(), ad, "afternoon");
										}else{//全天请假
											this.setRemark(oaLeave.getType(), ad, "all");
										}
									}else if (i==dutyDays-1) {//末天即为考勤日期
										hu.setDay(oaLeave.getEnd_date());
										if(hu.isWorkFlag()){
											ad.setAttenceDate(oaLeave.getEnd_date());
										}
										if (endTime.compareTo(DateUtil.StringToDate("13:00", "HH:mm"))<=0) {//<=13:00,上午请假
											this.setRemark(oaLeave.getType(), ad, "moring");
										}else{//全天请假
											this.setRemark(oaLeave.getType(), ad, "all");
										}
									}

									attenceDetailsList.add(ad);
								}else{//全天请假   并计算考勤日期
									long milliseconds2 = startDate.getTime()+i*24*60*60*1000L;
									Date newDate2 = new Date(milliseconds2);
									String newDateStr2 = DateUtil.DateToString(newDate2, "yyyy-MM-dd");
									hu.setDay(newDateStr2);
									if(hu.isWorkFlag()){
										ad.setAttenceDate(newDateStr2);
										this.setRemark(oaLeave.getType(), ad, "all");
										
										attenceDetailsList.add(ad);
									}
								}
							}
							
						}
						
					}
					
				}
				
			}
			
			/**************外出OA数据******************/
			if(UtilValidate.isNotEmpty(outList)){
				/**
				 * 导入保存数据库后，更新考勤详情备注
				 * 外出计算规则：
				 * (要判断结束日期是否大于等于当前日期，如果是则直接更改考勤备注，否则数据库不存在考勤数据，不能更改考勤备注)
				 * 1.如果外出开始日期==外出结束日期-->判断开始时间和结束时间-->外出一天还是半天
				 * 2.如果外出开始日期!=外出结束日期-->判断开始时间和结束时间-->外出天数
				 * 
				 * 外出类型：1=外出-->all=全天，moring=上午请假，afternoon=下午请假
				 */
				for (OAOut oAOut : outList) {
					Date endDate = DateUtil.StringToDate(oAOut.getEnd_date(), "yyyy-MM-dd");
					Date startDate = DateUtil.StringToDate(oAOut.getStart_date(), "yyyy-MM-dd");
					Date startTime = DateUtil.StringToDate(oAOut.getStart_time(), "HH:mm");
					Date endTime = DateUtil.StringToDate(oAOut.getEnd_time(), "HH:mm");
					if(endDate.before(nowDate)){
						if(startDate.compareTo(endDate)==0){//外出不跨天
							ad = new AttenceDetails();
							ad.setEmpCode(oAOut.getEmp_code());
							ad.setAttenceDate(oAOut.getStart_date());
							if (endTime.compareTo(DateUtil.StringToDate("13:00", "HH:mm"))<=0) {//<=13:00,上午外出
								ad.setRemark("1moring");
								ad.setRemarkFlag("1");
								ad.setMorningAttence("09:30:00");
							}else if (startTime.compareTo(DateUtil.StringToDate("12:00", "HH:mm"))>=0) {//>=12:00,下午外出
								ad.setRemark("1afternoon");
								ad.setRemarkFlag("1");
								ad.setAfternoonAttence("18:30:00");
							}else {//全天外出
								ad.setRemark("1all");
								ad.setRemarkFlag("1");
								ad.setMorningAttence("09:30:00");
								ad.setAfternoonAttence("18:30:00");
							}
							attenceDetailsList.add(ad);
						}else if (startDate.compareTo(endDate)<0) {
							/**
							 * 跨天外出，先计算所跨天数，并根据天数遍历每天外出的情况（全天、上午、下午），
							 * 计算天数需考虑非工作日
							 */
							int dutyDays = DateUtil.getAllDays(oAOut.getStart_date(), oAOut.getEnd_date());
							
							for (int i = 0; i < dutyDays; i++) {
								ad = new AttenceDetails();
								ad.setEmpCode(oAOut.getEmp_code());
								
								HolidayUtils hu = new HolidayUtils();
								if(i==0 || i==dutyDays-1){//首天和末天可能出现半天外出
									if(i==0){//首天即为考勤日期
										hu.setDay(oAOut.getStart_date());
										if(hu.isWorkFlag()){
											ad.setAttenceDate(oAOut.getStart_date());
										}
										if (startTime.compareTo(DateUtil.StringToDate("12:00", "HH:mm"))>=0) {//>=12:00,下午外出
											ad.setRemark("1afternoon");
											ad.setRemarkFlag("1");
											ad.setAfternoonAttence("18:30:00");
										}else {
											ad.setRemark("1all");
											ad.setRemarkFlag("1");
											ad.setMorningAttence("09:30:00");
											ad.setAfternoonAttence("18:30:00");
										}
									}else if (i==dutyDays-1) {//末天即为考勤日期
										hu.setDay(oAOut.getEnd_date());
										if(hu.isWorkFlag()){
											ad.setAttenceDate(oAOut.getEnd_date());
										}
										if (endTime.compareTo(DateUtil.StringToDate("13:00", "HH:mm"))<=0) {//<=13:00,上午外出
											ad.setRemark("1moring");
											ad.setRemarkFlag("1");
											ad.setMorningAttence("09:30:00");
										}else {
											ad.setRemark("1all");
											ad.setRemarkFlag("1");
											ad.setMorningAttence("09:30:00");
											ad.setAfternoonAttence("18:30:00");
										}
									}

									attenceDetailsList.add(ad);
								}else{//全天请假 并计算考勤日期
									long milliseconds = startDate.getTime()+i*24*60*60*1000L;
									Date newDate = new Date(milliseconds);
									String newDateStr = DateUtil.DateToString(newDate, "yyyy-MM-dd");
									
									hu.setDay(newDateStr);
									if(hu.isWorkFlag()){
										ad.setAttenceDate(newDateStr);
										ad.setRemark("1all");
										ad.setRemarkFlag("1");
										ad.setMorningAttence("09:30:00");
										ad.setAfternoonAttence("18:30:00");

										attenceDetailsList.add(ad);
									}
									
								}
							}
						}
						outListTemp.add(oAOut);
					}else {//OA上提外出，结束日期在当前时间之后
						if(startDate.compareTo(nowDate)<0){//说明外出已开始，还未结束
							//当前日期前一天
							long milliseconds1 = nowDate.getTime()-24*60*60*1000L;
							Date newDate1 = new Date(milliseconds1);
							int dutyDays = DateUtil.getAllDays(oAOut.getStart_date(), DateUtil.DateToString(newDate1, "yyyy-MM-dd"));
							HolidayUtils hu = new HolidayUtils();
							for (int i = 0; i < dutyDays; i++) {
								ad = new AttenceDetails();
								ad.setEmpCode(oAOut.getEmp_code());
								
								if(i==0 || i==dutyDays-1){//首天和末天可能出现半天外出
									if(i==0){//首天即为考勤日期
										hu.setDay(oAOut.getStart_date());
										if(hu.isWorkFlag()){
											ad.setAttenceDate(oAOut.getStart_date());
										}
										if (startTime.compareTo(DateUtil.StringToDate("12:00", "HH:mm"))>=0) {//>=12:00,下午外出
											ad.setRemark("1afternoon");
											ad.setRemarkFlag("1");
											ad.setAfternoonAttence("18:30:00");
										}else {
											ad.setRemark("1all");
											ad.setRemarkFlag("1");
											ad.setMorningAttence("09:30:00");
											ad.setAfternoonAttence("18:30:00");
										}
									}else if (i==dutyDays-1) {//末天即为考勤日期
										hu.setDay(oAOut.getEnd_date());
										if(hu.isWorkFlag()){
											ad.setAttenceDate(oAOut.getEnd_date());
										}
										if (endTime.compareTo(DateUtil.StringToDate("13:00", "HH:mm"))<=0) {//<=13:00,上午外出
											ad.setRemark("1moring");
											ad.setRemarkFlag("1");
											ad.setMorningAttence("09:30:00");
										}else {
											ad.setRemark("1all");
											ad.setRemarkFlag("1");
											ad.setMorningAttence("09:30:00");
											ad.setAfternoonAttence("18:30:00");
										}
									}
									
									attenceDetailsList.add(ad);
								}else{//全天请假 并计算考勤日期
									long milliseconds2 = startDate.getTime()+i*24*60*60*1000L;
									Date newDate2 = new Date(milliseconds2);
									String newDateStr2 = DateUtil.DateToString(newDate2, "yyyy-MM-dd");
									
									hu.setDay(newDateStr2);
									if(hu.isWorkFlag()){
										ad.setAttenceDate(newDateStr2);
										ad.setRemark("1all");
										ad.setRemarkFlag("1");
										ad.setMorningAttence("09:30:00");
										ad.setAfternoonAttence("18:30:00");
										
										attenceDetailsList.add(ad);
									}
									
								}
							}
							
						}
						
					}
				}
			}
			
			/**************出差OA数据******************/
			if(UtilValidate.isNotEmpty(onBusinessList)){
				/**
				 * 导入保存数据库后，更新考勤详情备注
				 * 出差计算规则：
				 * (要判断结束日期是否大于等于当前日期，如果是则直接更改考勤备注，否则数据库不存在考勤数据，不能更改考勤备注)
				 * 1.返回日期<当前日期 更新出差考勤
				 * 2.返回日期>=当前日期，更新之前考勤的备注
				 * 
				 * 请假类型：3=出差
				 */
				for (OAOnBusiness oaOnBusiness : onBusinessList) {
					Date backDate = DateUtil.StringToDate(oaOnBusiness.getBack_date(), "yyyy-MM-dd");
					Date startDate = DateUtil.StringToDate(oaOnBusiness.getStart_date(), "yyyy-MM-dd");
					if(backDate.before(nowDate)){
						int dutyDays = DateUtil.getAllDays(oaOnBusiness.getStart_date(), oaOnBusiness.getBack_date());
						HolidayUtils hu = new HolidayUtils();
						for (int i = 0; i < dutyDays; i++) {
							ad = new AttenceDetails();
							ad.setEmpCode(oaOnBusiness.getEmp_code());
							ad.setRemark("3");
							ad.setRemarkFlag("1");
							if(i==0){
								hu.setDay(oaOnBusiness.getStart_date());
								if(hu.isWorkFlag()){
									ad.setAttenceDate(oaOnBusiness.getStart_date());
								}
								
								attenceDetailsList.add(ad);
							}else if (i==dutyDays-1) {
								hu.setDay(oaOnBusiness.getBack_date());
								if(hu.isWorkFlag()){
									ad.setAttenceDate(oaOnBusiness.getBack_date());
								}
								
								attenceDetailsList.add(ad);
							}else{
								long milliseconds = startDate.getTime()+i*24*60*60*1000L;
								Date newDate = new Date(milliseconds);
								String newDateStr = DateUtil.DateToString(newDate, "yyyy-MM-dd");
								
								hu.setDay(newDateStr);
								if(hu.isWorkFlag()){
									ad.setAttenceDate(newDateStr);
									attenceDetailsList.add(ad);
								}
							}
							
						}
						onBusinessListTemp.add(oaOnBusiness);
					}else {//OA上提出差，结束日期在当前时间之后
						if(startDate.compareTo(nowDate)<0){//说明出差已开始，还未结束
							//当前日期前一天
							long milliseconds1 = nowDate.getTime()-24*60*60*1000L;
							Date newDate1 = new Date(milliseconds1);
							int dutyDays = DateUtil.getAllDays(oaOnBusiness.getStart_date(), DateUtil.DateToString(newDate1, "yyyy-MM-dd"));
							HolidayUtils hu = new HolidayUtils();
							for (int i = 0; i < dutyDays; i++) {
								ad = new AttenceDetails();
								ad.setEmpCode(oaOnBusiness.getEmp_code());
								ad.setRemark("3");
								ad.setRemarkFlag("1");
								if(i==0){
									hu.setDay(oaOnBusiness.getStart_date());
									if(hu.isWorkFlag()){
										ad.setAttenceDate(oaOnBusiness.getStart_date());
									}
									
									attenceDetailsList.add(ad);
								}else if (i==dutyDays-1) {
									hu.setDay(oaOnBusiness.getBack_date());
									if(hu.isWorkFlag()){
										ad.setAttenceDate(oaOnBusiness.getBack_date());
									}
									
									attenceDetailsList.add(ad);
								}else{
									long milliseconds = startDate.getTime()+i*24*60*60*1000L;
									Date newDate = new Date(milliseconds);
									String newDateStr = DateUtil.DateToString(newDate, "yyyy-MM-dd");
									
									hu.setDay(newDateStr);
									if(hu.isWorkFlag()){
										ad.setAttenceDate(newDateStr);
										
										attenceDetailsList.add(ad);
									}
								}
								
							}
						}
					}
				}
			}
		
			oALeaveService.updateOAInfo(attenceDetailsList, leaveList, outList, onBusinessList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
		}
		
		return "";
	}
	
	
	/**
	 * 
	 * @param type 请假类型
	 * @param ad   考勤详情对象
	 * @param flag 请假时间all,moring,afternoon
	 */
	private void setRemark(String type, AttenceDetails ad, String flag){
		if(flag.equals("moring")){
			if(type.equals("病假")){
				ad.setRemark("4moring");
			}else if (type.equals("事假")) {
				ad.setRemark("5moring");
			}else if (type.equals("婚假")) {
				ad.setRemark("6moring");
			}else if (type.equals("丧假")) {
				ad.setRemark("7moring");
			}else if (type.equals("产假")) {
				ad.setRemark("8moring");
			}else if (type.equals("年假")) {
				ad.setRemark("9moring");
			}
		}else if (flag.equals("afternoon")) {
			if(type.equals("病假")){
				ad.setRemark("4afternoon");
			}else if (type.equals("事假")) {
				ad.setRemark("5afternoon");
			}else if (type.equals("婚假")) {
				ad.setRemark("6afternoon");
			}else if (type.equals("丧假")) {
				ad.setRemark("7afternoon");
			}else if (type.equals("产假")) {
				ad.setRemark("8afternoon");
			}else if (type.equals("年假")) {
				ad.setRemark("9afternoon");
			}
		}else if (flag.equals("all")) {
			if(type.equals("病假")){
				ad.setRemark("4all");
			}else if (type.equals("事假")) {
				ad.setRemark("5all");
			}else if (type.equals("婚假")) {
				ad.setRemark("6all");
			}else if (type.equals("丧假")) {
				ad.setRemark("7all");
			}else if (type.equals("产假")) {
				ad.setRemark("8all");
			}else if (type.equals("年假")) {
				ad.setRemark("9all");
			}
		}
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
		
		
}
