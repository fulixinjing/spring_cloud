package cn.taskSys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.AttenceDao;
import cn.taskSys.dao.AttenceDetailsDao;
import cn.taskSys.entity.Attence;
import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.entity.AttenceTemp;
import cn.taskSys.service.IAttenceService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;

@Service("attenceService")
public class AttenceServiceImpl implements IAttenceService {
	
	@Autowired
	private AttenceDao attenceDao;
	
	@Autowired
	private AttenceDetailsDao attenceDetailsDao;

	public void setAttenceDao(AttenceDao attenceDao) {
		this.attenceDao = attenceDao;
	}

	public void setAttenceDetailsDao(AttenceDetailsDao attenceDetailsDao) {
		this.attenceDetailsDao = attenceDetailsDao;
	}
	
	@Override
	public PageView<Attence> getAttenceListpageView(Attence attence) throws Exception {
		PageView<Attence> pageView = new PageView<Attence>(
				attence.getMaxResult(),
				attence.getPage());// 需要设置当前页
		try {	
			int count = attenceDao.getAttenceListCount1(attence);// 获取列表条数
			List<Attence> attenceList = attenceDao.getAttenceList(attence);// 获取列表数据
			
			QueryResult<Attence> qr = new QueryResult<Attence>();
			qr.setResultlist(attenceList);
			qr.setTotalrecord(count);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageView;
	}

	@Override
	public List<AttenceTemp> punchCardRecordList(Map<String, Object> map) throws Exception {
		return attenceDao.punchCardRecordList(map);
	}

	@Override
	public List<Map<String, String>> findSysUsers(List<String> empCodes) throws Exception {
		return attenceDao.findSysUsers(empCodes);
	}

	@Override
	public void saveAttenceDetails(List<AttenceDetails> attenceDetailsList, List<AttenceDetails> laterAttenceDetailsList)
			throws Exception {
		try {
			if(attenceDetailsList!=null && attenceDetailsList.size()>0){
				List<AttenceDetails> allAttenceDetailsList = new ArrayList<AttenceDetails>();
				allAttenceDetailsList.addAll(attenceDetailsList);
				/*******插入前处理当天的打卡记录 start*******/
				AttenceDetails attenceDetails = new AttenceDetails();
				String attenceDate = attenceDetailsList.get(0).getAttenceDate();
				if(attenceDate==null || attenceDate.equals("")){
					return;
				}
				attenceDetails.setAttenceDate(attenceDate);
				attenceDetailsDao.deleteAttenceDetailsByPro(attenceDetails);
				/*******插入前处理当天的打卡记录 end*******/
				
				/*******插入前处理新入职员工后来的的打卡记录 start*******/
				if(laterAttenceDetailsList!=null && laterAttenceDetailsList.size()>0){
					for (AttenceDetails laterAttenceDetails : laterAttenceDetailsList) {
						attenceDetailsDao.deleteAttenceDetailsByPro(laterAttenceDetails);
					}
					allAttenceDetailsList.addAll(laterAttenceDetailsList);
				}
				/*******插入前处理新入职员工后来的的打卡记录 end*******/
				
				attenceDetailsDao.saveAttenceDetails(allAttenceDetailsList);
				
				//保存员工考勤Attence(jx_attence)
				String monthStr = attenceDetailsList.get(0).getAttenceDate().substring(0, 7);
				AttenceDetails ad = new AttenceDetails();
				ad.setAttenceDate(monthStr);
				ad.setDateType("1");//工作日，判断工作日打卡是否正常
				List<AttenceDetails> attenceDetailsList2 = attenceDetailsDao.getAttenceDetailsList(ad);
				Attence attenceTemp = null;
				List<AttenceDetails> attenceDetailsTempList1 = null;
				List<AttenceDetails> attenceDetailsTempList2 = null;
				List<Attence> attenceList = new ArrayList<Attence>();
				List<String> empCodes = new ArrayList<String>();
				for (AttenceDetails attenceDetails1 : attenceDetailsList) {
					attenceTemp = new Attence();
					attenceTemp.setId(UUIDHexGenerator.generater());
					attenceTemp.setUsername(attenceDetails1.getUsername());
					attenceTemp.setEmpCode(attenceDetails1.getEmpCode());
					attenceTemp.setDepartment(attenceDetails1.getDepartment());
					attenceTemp.setMonths(monthStr);
					attenceTemp.setImportTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					
					attenceDetailsTempList1 = new ArrayList<AttenceDetails>();//存放该员工所有打卡记录
					attenceDetailsTempList2 = new ArrayList<AttenceDetails>();//存放该员工正常打卡记录
					if(attenceDetailsList2!=null && attenceDetailsList2.size()>0){//判断该天打卡记录是否正常
						for (AttenceDetails attenceDetails2 : attenceDetailsList2) {
							if(attenceDetails1.getEmpCode().equals(attenceDetails2.getEmpCode())){
								attenceDetailsTempList1.add(attenceDetails2);
								if(attenceDetails2.getMorningAttence()!=null && !"".equals(attenceDetails2.getMorningAttence()) && attenceDetails2.getAfternoonAttence()!=null && !"".equals(attenceDetails2.getAfternoonAttence())){
									
									String morningAttence = attenceDetails2.getMorningAttence();
									Date morning = DateUtil.StringToDate(morningAttence, "HH:mm:ss");
									String afternoonAttence = attenceDetails2.getAfternoonAttence();
									Date afternoon = DateUtil.StringToDate(afternoonAttence, "HH:mm:ss");
									Date date_12 = DateUtil.StringToDate("12:00:00", "HH:mm:ss");
									if(morning.before(date_12) && afternoon.after(date_12)){//判断标准：至少两次打卡记录并且上午打卡时间：12:00之前，下午打卡时间：12:00之后，系统认为正常状态。
										attenceDetailsTempList2.add(attenceDetails2);
									}
//									String morningAttence = attenceDetails2.getMorningAttence().substring(0, 2);
//									int moring = Integer.parseInt(morningAttence);
//									String afternoonAttence = attenceDetails2.getAfternoonAttence().substring(0, 2);
//									int after = Integer.parseInt(afternoonAttence);
//									if(moring<=12 && after>12){//判断标准：至少两次打卡记录并且上午打卡时间：12:00之前，下午打卡时间：12:00之后，系统认为正常状态。
//										attenceDetailsTempList2.add(attenceDetails2);
//									}
									
								}
								
							}
						}
					}
					if(attenceDetailsTempList1.size()==attenceDetailsTempList2.size()){
						attenceTemp.setState("1");//状态 1=正常，0=异常
					}else {
						attenceTemp.setState("0");//状态 1=正常，0=异常
					}
					
					attenceList.add(attenceTemp);
					empCodes.add(attenceTemp.getEmpCode());
				}
				
				if(attenceList!=null && attenceList.size()>0){
					Attence att = new Attence();
					att.setMonths(monthStr);
					att.setEmpCodes(empCodes);
					attenceDao.deleteAttenceByPro(att);
					
					attenceDao.saveAttence(attenceList);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List<AttenceDetails> getAttenceDetailsList(
			AttenceDetails AttenceDetails) throws Exception {
		return attenceDetailsDao.getAttenceDetailsList(AttenceDetails);
	}

	@Override
	public void modifyAttenceDetails(List<AttenceDetails> attenceDetailsList)	throws Exception {
		if(attenceDetailsList.size()>0){
			boolean flag=false;
			for (AttenceDetails attenceDetails : attenceDetailsList) {
				attenceDetailsDao.modifyAttenceDetails(attenceDetails);
				if(!"".equals(StringUtil.nvlString(attenceDetails.getRemark()))){
					//判断是否修改考勤备注，如果修改flag置为true,以便重新统计该月打卡是否正常
					int rem = Integer.parseInt(attenceDetails.getRemark().substring(0, 1));
					if(rem>=1){
						flag=true;
					}
				}

			}
			//flag=true,说明有外出或调休、请假、加班、其他、哺乳假，将更改绩效表状态
			if(flag){
				AttenceDetails ad = new AttenceDetails();
				ad.setId(attenceDetailsList.get(0).getId());
				List<AttenceDetails> list = attenceDetailsDao.getAttenceDetailsList(ad);
				if(list.size()>0){
					ad.setEmpCode(list.get(0).getEmpCode());
					ad.setAttenceDate(list.get(0).getAttenceDate().substring(0, 7));
					ad.setId(null);
					ad.setDateType("1");//工作日，判断工作日打卡是否正常
					List<AttenceDetails> detailsList = attenceDetailsDao.getAttenceDetailsList(ad);//工作日打卡记录
					
					int count=0;
					if(detailsList!=null && detailsList.size()>0){
						for (AttenceDetails details : detailsList) {
							/*if(details.getMorningAttence()==null || "".equals(details.getMorningAttence()) || details.getAfternoonAttence()==null || "".equals(details.getAfternoonAttence())){
								if(details.getRemark()==null || "".equals(details.getRemark()) || StringUtil.nvlString(details.getRemark()).equals("0")){
									count++;
								}
							}*/
							
							if(details.getMorningAttence()!=null && !"".equals(details.getMorningAttence()) && details.getAfternoonAttence()!=null && !"".equals(details.getAfternoonAttence())){
								String morningAttence = details.getMorningAttence();
								Date morning = DateUtil.StringToDate(morningAttence, "HH:mm:ss");
								String afternoonAttence = details.getAfternoonAttence();
								Date afternoon = DateUtil.StringToDate(afternoonAttence, "HH:mm:ss");
								Date date_12 = DateUtil.StringToDate("12:00:00", "HH:mm:ss");
								if(morning.before(date_12) && afternoon.after(date_12)){//判断标准：至少两次打卡记录并且上午打卡时间：12:00之前，下午打卡时间：12:00之后，系统认为正常状态。
									count++;//打卡正常count加1
								}
								
								/*String morningAttence = details.getMorningAttence().substring(0, 2);
								int moring = Integer.parseInt(morningAttence);
								String afternoonAttence = details.getAfternoonAttence().substring(0, 2);
								int after = Integer.parseInt(afternoonAttence);
								if(moring<=12 && after>12){//判断标准：至少两次打卡记录并且上午打卡时间：12:00之前，下午打卡时间：12:00之后，系统认为正常状态。
									count++;//打卡正常count加1
								}*/
							}else {
								if(details.getRemark()!=null && (details.getRemark().startsWith("2") || details.getRemark().startsWith("3"))){//如果请假或出差无打卡记录排除异常情况之外
									count++;
								}
								
							}
							
						}
						
						if(count==detailsList.size()){//如果正常打卡次数==detailsList.size()，说明该月打卡正常
							Attence attenceTemp = new Attence();
							attenceTemp.setEmpCode(detailsList.get(0).getEmpCode());
							attenceTemp.setMonths(detailsList.get(0).getAttenceDate().substring(0, 7));
							attenceTemp.setState("1");//状态 1=正常，0=异常
							
							attenceDao.modifyAttence(attenceTemp);
						}
					}
				}
			}
		}
		
	}

	@Override
	public List<Map<String, String>> findUsers() throws Exception {
		return attenceDao.findUsers();
	}

	@Override
	public List<AttenceTemp> punchCardRecordList2(Map<String, Object> map) throws Exception {
		return attenceDao.punchCardRecordList2(map);
	}
	
}
