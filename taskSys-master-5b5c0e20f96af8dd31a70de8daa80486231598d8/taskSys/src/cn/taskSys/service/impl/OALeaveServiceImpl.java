package cn.taskSys.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.AttenceDao;
import cn.taskSys.dao.AttenceDetailsDao;
import cn.taskSys.dao.OALeaveDao;
import cn.taskSys.entity.Attence;
import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.entity.OALeave;
import cn.taskSys.entity.OAOnBusiness;
import cn.taskSys.entity.OAOut;
import cn.taskSys.service.IOALeaveService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.UtilValidate;

@Service("oALeaveService")
public class OALeaveServiceImpl implements IOALeaveService {
	
	@Autowired
	private OALeaveDao oALeaveDao;
	
	@Autowired
	private AttenceDetailsDao attenceDetailsDao;
	
	@Autowired
	private AttenceDao attenceDao;

	@Override
	public void saveOALeave(List<OALeave> oALeaves) throws Exception {
		if(UtilValidate.isNotEmpty(oALeaves)){
			oALeaveDao.saveOALeave(oALeaves);
			
		}
	}

	@Override
	public void saveOAOut(List<OAOut> oAOuts) throws Exception {
		if(UtilValidate.isNotEmpty(oAOuts)){
			oALeaveDao.saveOAOut(oAOuts);
		}

	}

	@Override
	public void saveOAOnBusiness(List<OAOnBusiness> oAOnBusinesss) throws Exception {
		if(UtilValidate.isNotEmpty(oAOnBusinesss)){
			oALeaveDao.saveOAOnBusiness(oAOnBusinesss);
		}
	}

	@Override
	public PageView<OALeave> getOALeaveListpageView(OALeave oALeave) throws Exception {
		PageView<OALeave> pageView = new PageView<OALeave>(oALeave.getMaxResult(), oALeave.getPage());// 需要设置当前页
		
		int count = 0;// 获取列表条数
		List<OALeave> oALeaveList = null;// 获取列表数据
		oALeaveList = oALeaveDao.getOALeaveList(oALeave);
		count = oALeaveDao.getOALeaveListCount(oALeave);
		
		QueryResult<OALeave> qr = new QueryResult<OALeave>();
		qr.setResultlist(oALeaveList);
		qr.setTotalrecord(count);
		pageView.setQueryResult(qr);
		
		return pageView;
	}

	@Override
	public PageView<OAOut> getOAOutListpageView(OAOut oAOut) throws Exception {
		PageView<OAOut> pageView = new PageView<OAOut>(oAOut.getMaxResult(), oAOut.getPage());// 需要设置当前页
		
		int count = 0;// 获取列表条数
		List<OAOut> oAOutList = null;// 获取列表数据
		oAOutList = oALeaveDao.getOAOutList(oAOut);
		count = oALeaveDao.getOAOutListCount(oAOut);
		
		QueryResult<OAOut> qr = new QueryResult<OAOut>();
		qr.setResultlist(oAOutList);
		qr.setTotalrecord(count);
		pageView.setQueryResult(qr);
		
		return pageView;
	}

	@Override
	public PageView<OAOnBusiness> getOAOnBusinessListpageView(OAOnBusiness oAOnBusiness) throws Exception {
		PageView<OAOnBusiness> pageView = new PageView<OAOnBusiness>(oAOnBusiness.getMaxResult(), oAOnBusiness.getPage());// 需要设置当前页
		
		int count = 0;// 获取列表条数
		List<OAOnBusiness> oAOnBusinessList = null;// 获取列表数据
		oAOnBusinessList = oALeaveDao.getOAOnBusinessList(oAOnBusiness);
		count = oALeaveDao.getOAOnBusinessListCount(oAOnBusiness);
		
		QueryResult<OAOnBusiness> qr = new QueryResult<OAOnBusiness>();
		qr.setResultlist(oAOnBusinessList);
		qr.setTotalrecord(count);
		pageView.setQueryResult(qr);
		
		return pageView;
	}
	

	@Override
	public List<OALeave> getUnUpdateLeaveList(OALeave oALeave) throws Exception {
		return oALeaveDao.getUnUpdateLeaveList(oALeave);
	}

	@Override
	public List<OAOut> getUnUpdateOutList(OAOut oAOut) throws Exception {
		return oALeaveDao.getUnUpdateOutList(oAOut);
	}

	@Override
	public List<OAOnBusiness> getUnUpdateOnBusinessList(OAOnBusiness oAOnBusiness) throws Exception {
		return oALeaveDao.getUnUpdateOnBusinessList(oAOnBusiness);
	}

	@Override
	public void updateOAInfo(List<AttenceDetails> attenceList, List<OALeave> leaveList, List<OAOut> outList,
			List<OAOnBusiness> onBusinessesList) throws Exception {
		
		try {
			if(UtilValidate.isNotEmpty(attenceList)){
				attenceDetailsDao.updateAttenceDetailsList(attenceList);
				
				Set<String> empCodeSet = new HashSet<String>();
				Set<String> attenceMonthSet = new HashSet<String>();
				for (AttenceDetails ad : attenceList) {
					if(ad.getAttenceDate()!=null && !ad.getAttenceDate().equals("")){
						empCodeSet.add(ad.getEmpCode());
						attenceMonthSet.add(ad.getAttenceDate().substring(0, 7));
					}
				}
				Iterator<String> it1 = attenceMonthSet.iterator();
				while (it1.hasNext()) {
					String monthStr=it1.next();
					Iterator<String> it2 = empCodeSet.iterator();
					AttenceDetails ad2 = null;
					while (it2.hasNext()) {
						String empCode = it2.next();
						if(monthStr==null || monthStr.equals("") || empCode==null || empCode.equals("")){
							continue;
						}
						ad2 = new AttenceDetails();
						ad2.setEmpCode(empCode);
						ad2.setAttenceDate(monthStr);
						ad2.setDateType("1");//工作日，判断工作日打卡是否正常
						List<AttenceDetails> detailsList = attenceDetailsDao.getAttenceDetailsList(ad2);//工作日打卡记录
						
						int count=0;
						if(detailsList!=null && detailsList.size()>0){
							for (AttenceDetails details : detailsList) {
								if(details.getMorningAttence()!=null && !"".equals(details.getMorningAttence()) && details.getAfternoonAttence()!=null && !"".equals(details.getAfternoonAttence())){
									String morningAttence = details.getMorningAttence();
									Date morning = DateUtil.StringToDate(morningAttence, "HH:mm:ss");
									String afternoonAttence = details.getAfternoonAttence();
									Date afternoon = DateUtil.StringToDate(afternoonAttence, "HH:mm:ss");
									Date date_12 = DateUtil.StringToDate("12:00:00", "HH:mm:ss");
									if(morning.before(date_12) && afternoon.after(date_12)){//判断标准：至少两次打卡记录并且上午打卡时间：12:00之前，下午打卡时间：12:00之后，系统认为正常状态。
										count++;//打卡正常count加1
									}
									
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(UtilValidate.isNotEmpty(leaveList)){
			oALeaveDao.updateLeaveById(leaveList);
		}
		if(UtilValidate.isNotEmpty(outList)){
			oALeaveDao.updateOutById(outList);
		}
		if(UtilValidate.isNotEmpty(onBusinessesList)){
			oALeaveDao.updateOnBusinessById(onBusinessesList);
		}
	}
	
}
