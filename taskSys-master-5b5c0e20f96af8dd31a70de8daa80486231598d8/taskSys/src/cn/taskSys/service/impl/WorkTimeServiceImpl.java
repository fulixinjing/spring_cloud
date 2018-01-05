package cn.taskSys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.UtilsDao;
import cn.taskSys.dao.WorkTimeDao;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.WorkTime;
import cn.taskSys.service.IWorkTimeService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.MathUtil;
import cn.taskSys.utils.UUIDHexGenerator;

@Service("workTimeService")
public class WorkTimeServiceImpl implements IWorkTimeService {

	@Autowired
	private WorkTimeDao workTimeDao;
	
	/*@Autowired
	private UserDao userDao;*/
	
	@Autowired
	private UtilsDao utilsDao;
	
	public void setWorkTimeDao(WorkTimeDao workTimeDao) {
		this.workTimeDao = workTimeDao;
	}
	
	@Override
	public void saveWorkTime(List<WorkTime> workTimes) throws Exception {
		try {
			//保存员工的数据
			if(workTimes!=null && workTimes.size()>0){
				WorkTime wt = new WorkTime();
				wt.setType("1");
				wt.setStatisticsTime(workTimes.get(0).getStatisticsTime());
				workTimeDao.delWorkTime(wt);
				
				workTimeDao.saveWorkTime(workTimes);

			}
			
			/*//员工数据保存后，保存团队数据
			Map<String, String> teamMap = new HashMap<String, String>();
			teamMap.put("type", "02");
			List<Dictionary> teamList = utilsDao.getDictionaryList(teamMap);//已有团队
			List<Map<String, Object>> workTimeSumByTeamList = workTimeDao.getWorkTimeSumByTeam(workTimes.get(0).getStatisticsTime());//团队工作量
			List<Map<String, Object>> attenceDaysByTeam = workTimeDao.getAttenceDaySumByTeam(workTimes.get(0).getStatisticsTime());//团队实际出勤天数
			WorkTime workTimeTemp2 = null;
			List<WorkTime> teamWorkTimeList = new ArrayList<WorkTime>();
			for (Dictionary teamTemp : teamList) {
				workTimeTemp2 = new WorkTime();
				workTimeTemp2.setId(UUIDHexGenerator.generater());
				
				workTimeTemp2.setType("2");//1=员工，2=团队，3部门
				workTimeTemp2.setStatisticsTime(workTimes.get(0).getStatisticsTime());//yyyy-MM
				workTimeTemp2.setAddTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				workTimeTemp2.setTeam(teamTemp.getCode());
				
				double teamSum = 0;//团队总工作量
				double teamDays=0;//团队出勤总天数
				for (Map<String, Object> map : workTimeSumByTeamList) {
					if(map.get("team")!=null && map.get("team").equals(teamTemp.getCode())){
						teamSum=(Double) map.get("sum");
						workTimeTemp2.setDepartment((String)map.get("department"));
						break;
					}
					
				}
				for (Map<String, Object> map : attenceDaysByTeam) {
					if(map.get("team")!=null && map.get("team").equals(teamTemp.getCode())){
						teamDays=(Double) map.get("sum");
						break;
					}
					
				}
				
				double saturation = 0;
				if(teamDays>0){
					double temp1 = teamDays*8;
					saturation = MathUtil.div(teamSum, temp1, 4);
					if(saturation>1.5){
						saturation=1.50;
					}
					workTimeTemp2.setSaturation(saturation+"");
				}else {
					workTimeTemp2.setSaturation("N/A");
				}
				workTimeTemp2.setAllWorkTime(MathUtil.round(teamSum, 2)+"");
				
				teamWorkTimeList.add(workTimeTemp2);
				
			}
			if(teamWorkTimeList!=null && teamWorkTimeList.size()>0){
				WorkTime wt = new WorkTime();
				wt.setType("2");
				wt.setStatisticsTime(workTimes.get(0).getStatisticsTime());
				workTimeDao.delWorkTime(wt);
				
				workTimeDao.saveWorkTime(teamWorkTimeList);
			}*/
			
			
			//员工数据保存后，保存部门数据
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "01");
			List<Dictionary> departmentList = utilsDao.getDictionaryList(map);
//			List<Map<String, Object>> userCountList = userDao.getUserCountByDeparment();
			List<Map<String, Object>> workTimeSumList = workTimeDao.getWorkTimeSumByDepartment(workTimes.get(0).getStatisticsTime());
			List<Map<String, Object>> attenceDays = workTimeDao.getAttenceDaySumByDepartment(workTimes.get(0).getStatisticsTime());
			WorkTime workTimeTemp = null;
			List<WorkTime> workTimeList = new ArrayList<WorkTime>();
			for (Dictionary department : departmentList) {
				workTimeTemp = new WorkTime();
				workTimeTemp.setId(UUIDHexGenerator.generater());
				
				workTimeTemp.setType("3");//1=员工，2=团队，3部门
				workTimeTemp.setStatisticsTime(workTimes.get(0).getStatisticsTime());//yyyy-MM
				workTimeTemp.setAddTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				workTimeTemp.setDepartment(department.getCode());
				double sum = 0;
				//long count = 0;
				double attDays=0;//部门出勤总天数
				for (Map<String, Object> map2 : workTimeSumList) {
					if(map2.get("department")!=null && map2.get("department").equals(department.getCode())){
						sum=(Double) map2.get("sum");
						break;
					}
					
				}
				/*for (Map<String, Object> map3 : userCountList) {
					if(map3.get("department_id").equals(department.getCode())){
						count=(Long) map3.get("count");
						break;
					}
					
				}*/
				for (Map<String, Object> map4 : attenceDays) {
					if(map4.get("department")!=null && map4.get("department").equals(department.getCode())){
						attDays=(Double) map4.get("sum");
						break;
					}
					
				}
				
				double saturation = 0;
				if(attDays>0){
					double temp1 = attDays*8;
					saturation = MathUtil.div(sum, temp1, 4);
					if(saturation>1.5){
						saturation=1.50;
					}
					workTimeTemp.setSaturation(saturation+"");
				}else {
					workTimeTemp.setSaturation("N/A");
				}
				workTimeTemp.setAllWorkTime(MathUtil.round(sum, 2)+"");
				
				workTimeList.add(workTimeTemp);
			}
			
			if(workTimeList!=null && workTimeList.size()>0){
				WorkTime wt = new WorkTime();
				wt.setType("3");
				wt.setStatisticsTime(workTimes.get(0).getStatisticsTime());
				workTimeDao.delWorkTime(wt);
				
				workTimeDao.saveWorkTime(workTimeList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PageView<WorkTime> getWorkTimeListpageView(WorkTime workTime)throws Exception {
		PageView<WorkTime> pageView = new PageView<WorkTime>(
				workTime.getMaxResult(),
				workTime.getPage());// 需要设置当前页
		try {	
			
			int count = 0;// 获取列表条数
			List<WorkTime> workTimeList = null;// 获取列表数据
			if(workTime.getType().equals("1")){
				count = workTimeDao.getWorkTimeListCount1(workTime);
				workTimeList = workTimeDao.getWorkTimeList1(workTime);
			}else if (workTime.getType().equals("3")) {
				count = workTimeDao.getWorkTimeListCount2(workTime);
				workTimeList = workTimeDao.getWorkTimeList2(workTime);
			}
			
			QueryResult<WorkTime> qr = new QueryResult<WorkTime>();
			qr.setResultlist(workTimeList);
			qr.setTotalrecord(count);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageView;
	}

	@Override
	public List<WorkTime> exportWorkTimeList(WorkTime workTime)
			throws Exception {
		if(workTime.getType().equals("1")){
			return workTimeDao.exportWorkTimeList1(workTime);
		}else if (workTime.getType().equals("3")) {
			return workTimeDao.exportWorkTimeList2(workTime);
		}
		return null;
	}
	@Override
	public List<WorkTime> getDepartWT(String statisticsTime){
		return workTimeDao.getDepartWT(statisticsTime);
	}
	

}
