package cn.taskSys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.taskSys.base.PageView;
import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.User;
import cn.taskSys.entity.WorkTime;
import cn.taskSys.service.IWorkTimeService;
import cn.taskSys.service.RoleService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.ExportExcelUtil;
import cn.taskSys.utils.MathUtil;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.StringUtil;


@Controller
@RequestMapping(value = "/work")
public class WorkTimeController extends BaseAction<Object>{
	
	@Autowired
	private IWorkTimeService workTimeService;
//	@Autowired
//	private IAdminService adminService;
//	
//	@Autowired
//	private IUserService userService;
	
	@Autowired
	private UtilsService utilsService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 分页列表
	 * @param workTime
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWorkTimeList")
	public ModelAndView getWorkTimeList(@ModelAttribute("workTime")WorkTime workTime, HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/work/workTimeList");
			
			if(workTime.getType()==null || workTime.getType().equals("")){
				workTime.setType("1");
			}
			if(workTime.getStatisticsTime()==null || "".equals(workTime.getStatisticsTime())){
				workTime.setStatisticsTime(getDate(0));
			}
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			String deparmentId = user.getDepartment_id();
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL"))) {//部门经理
				workTime.setDepartment(deparmentId);
			}
			mv.addObject("userRole", userRole);
			
			PageView<WorkTime> pageView  = new PageView<WorkTime>();
			pageView = workTimeService.getWorkTimeListpageView(workTime);	
			
			mv.addObject("pageView", pageView);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "01");
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(map);//加载字典表信息
			
			mv.addObject("dictionaryLlist", dictionaryLlist);
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出excel列表
	 * @param workTime
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportWorkTimeList")
	public void exportWorkTimeList(@ModelAttribute("workTime")WorkTime workTime, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(workTime.getType()==null || workTime.getType().equals("")){
				workTime.setType("1");
			}
			
			User user = PublicUtil.getUserOnline(request);
			String userId = user.getUserId();
			String deparmentId = user.getDepartment_id();
			List<Role> roles = roleService.getRoleByUserId(userId);
			String userRole = null;
			for (Role role : roles) {
				if (role.getRoleCode()!=null && !"".equals(role.getRoleCode())) {
					userRole = role.getRoleCode();
				}
			}
			if (!StringUtil.isNUll(userRole) && (userRole.equals("BMJL"))) {//部门经理
				workTime.setDepartment(deparmentId);
			}
			
			List<WorkTime> exportWorkTimeList = workTimeService.exportWorkTimeList(workTime);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			int count = 0;
			if (exportWorkTimeList != null && exportWorkTimeList.size()>0) {
				count = exportWorkTimeList.size();
				//WorkTime==>Map
				Map<String,Object> map = null;
				for (WorkTime wt : exportWorkTimeList) {
					map = new HashMap<String,Object>();
					if(StringUtil.nvlString(workTime.getType()).equals("1")){
						map.put("loginName", StringUtil.nvlString(wt.getLoginName()));
						map.put("userName", StringUtil.nvlString(wt.getUserName()));
						map.put("teamName", StringUtil.nvlString(wt.getTeamName()));
						map.put("positionName", StringUtil.nvlString(wt.getPositionName()));
						map.put("postName", StringUtil.nvlString(wt.getPostName()));
					}
					map.put("departmentName", StringUtil.nvlString(wt.getDepartmentName()));
					map.put("allWorkTime", StringUtil.nvlString(wt.getAllWorkTime()));
					if(!StringUtil.isNUll(wt.getSaturation())){
						if(wt.getSaturation().equals("N/A")){
							map.put("saturation", wt.getSaturation());
						}else {
							double saturation = MathUtil.round(Double.parseDouble(wt.getSaturation())*100, 2);
							map.put("saturation", saturation+"%");
						}
					}else{
						map.put("saturation", "0.0%");
					}
					map.put("statisticsTime", StringUtil.nvlString(wt.getStatisticsTime()));

					list.add(map);
				}
			}
			
			int n = 50000;		//定义每个sheet存放的数据条数
			String sheetName = "工作量统计";
			@SuppressWarnings("rawtypes")
			List<List> lists = new ArrayList<List>();
			String[] header = null;
			String[] key = null;
			if(StringUtil.nvlString(workTime.getType()).equals("1")){
				header = new String[]{"员工号","姓名","所属部门","所属团队","职位","职位类别","总工作量","饱和度","统计月份"};
				key =  new String[]{"loginName","userName","departmentName","teamName","positionName","postName","allWorkTime","saturation","statisticsTime"};
			}else{
				header = new String[]{"所属部门","总工作量","饱和度","统计月份"};
				key =  new String[]{"departmentName","allWorkTime","saturation","statisticsTime"};
			}
			
			String[][] headers = {header};
			String[][] keys = {key};
			
			String dateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
			String fileName = "工作量统计"+dateStr+".xlsx";
			
			if (count <= n) {
				String[] sheets = new String[1];
				sheets[0] = sheetName + "1";
				lists.add(list);
				ExportExcelUtil.exportExcel2(fileName, response, lists, headers, keys, sheets);
			}else{
				int fysheet = (int) Math.ceil((double)count / n) ;
				String[] sheets = new String[fysheet];
				for (int i = 1; i <= fysheet; i++) {
					sheets[i - 1] = sheetName + i;
				}
				lists = createList(list,n);
				ExportExcelUtil.exportExcel2(fileName, response, lists, headers, keys, sheets);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//拆分list数组
	/**
	 * 
	 * @param targe 查询的数据
	 * @param size 每个工作薄的容量
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<List>  createList(List targe,int size) {  
        List<List> listArr = new ArrayList<List>();  
        //获取被拆分的数组个数  
        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
        for(int i=0;i<arrSize;i++) {   
            List  sub = new ArrayList();  
            //把指定索引数据放入到list中  
            for(int j=i*size;j<=size*(i+1)-1;j++) {  
                if(j<=targe.size()-1) {  
                    sub.add(targe.get(j));  
                }  
            }  
            listArr.add(sub);  
        }  
        return listArr;  
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
	
	public static int getDayOfMonth(String dateStr) throws Exception{  
		Date date = DateUtil.StringToDate(dateStr);
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		aCalendar.setTime(date);
		int maxDay=aCalendar.getActualMaximum(Calendar.DATE);
		String startDate = dateStr+"-01";
		String overDate = dateStr+"-"+maxDay;
		int day = DateUtil.getDutyDays(startDate,overDate);
		return day;
	}
	
	
	private double getSaturation(double workTime){
		try {
			double temp1 = MathUtil.div(workTime, MathUtil.mul(8, getDayOfMonth(getDate(-1))), 4);
			double temp2 = MathUtil.mul(8, temp1);
			return MathUtil.div(temp2, 6, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		new WorkTimeController().getSaturation(12);
		
	}
}
