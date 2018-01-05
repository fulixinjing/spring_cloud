package cn.taskSys.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.taskSys.base.PageView;
import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.OALeave;
import cn.taskSys.entity.OAOnBusiness;
import cn.taskSys.entity.OAOut;
import cn.taskSys.service.IOALeaveService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.ImportExcelUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;
import cn.taskSys.utils.UtilValidate;

@Controller
@RequestMapping(value="/oa")
public class OALeaveController extends BaseAction<Object> {
	@Autowired
	private IOALeaveService oALeaveService;
	
	/**
	 * 分页列表
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="/getOAInfoList")
	public ModelAndView getOAInfoList(HttpServletRequest request){
		try {
			ModelAndView mv = new ModelAndView("/oa/oaInfoList");
			
			String in_type = StringUtil.nvlString(request.getParameter("in_type"));
			String userName = StringUtil.nvlString(request.getParameter("userName"));
			String emp_code = StringUtil.nvlString(request.getParameter("emp_code"));
			String apply_time_start = StringUtil.nvlString(request.getParameter("apply_time_start"));
			String apply_time_end = StringUtil.nvlString(request.getParameter("apply_time_end"));
			String page = StringUtil.nvlString(request.getParameter("page"));
			String maxResult = StringUtil.nvlString(request.getParameter("maxResult"));
			if(in_type==null || in_type.equals("")){
				in_type = "1";
			}
			if(in_type.equals("1")){//请假列表
				OALeave instance = new OALeave();
				instance.setIn_type("1");
				if(UtilValidate.isNotEmpty(userName)){
					instance.setLeave_person(userName);
					mv.addObject("userName", userName);
				}
				if(UtilValidate.isNotEmpty(emp_code)){
					instance.setEmp_code(emp_code);
					mv.addObject("emp_code", emp_code);
				}
				if(UtilValidate.isNotEmpty(apply_time_start)){
					instance.setApply_time_start(apply_time_start);
					mv.addObject("apply_time_start", apply_time_start);
				}
				if(UtilValidate.isNotEmpty(apply_time_end)){
					instance.setApply_time_end(apply_time_end + " 23:59:59");
					mv.addObject("apply_time_end", apply_time_end);
				}
				if(UtilValidate.isNotEmpty(page)){
					instance.setPage(Integer.parseInt(page));
					mv.addObject("page", page);
				}
				if(UtilValidate.isNotEmpty(maxResult)){
					instance.setMaxResult(Integer.parseInt(maxResult));
					mv.addObject("maxResult", maxResult);
				}
				PageView<OALeave> pageView  = new PageView<OALeave>();
				pageView = oALeaveService.getOALeaveListpageView(instance);	
				
				mv.addObject("pageView", pageView);
				
			}else if (in_type.equals("2")) {//外出列表
				OAOut instance = new OAOut();
				instance.setIn_type("2");
				if(UtilValidate.isNotEmpty(userName)){
					instance.setOut_person(userName);
					mv.addObject("userName", userName);
				}
				if(UtilValidate.isNotEmpty(emp_code)){
					instance.setEmp_code(emp_code);
					mv.addObject("emp_code", emp_code);
				}
				if(UtilValidate.isNotEmpty(apply_time_start)){
					instance.setApply_time_start(apply_time_start);
					mv.addObject("apply_time_start", apply_time_start);
				}
				if(UtilValidate.isNotEmpty(apply_time_end)){
					instance.setApply_time_end(apply_time_end + " 23:59:59");
					mv.addObject("apply_time_end", apply_time_end);
				}
				if(UtilValidate.isNotEmpty(page)){
					instance.setPage(Integer.parseInt(page));
					mv.addObject("page", page);
				}
				if(UtilValidate.isNotEmpty(maxResult)){
					instance.setMaxResult(Integer.parseInt(maxResult));
					mv.addObject("maxResult", maxResult);
				}
				PageView<OAOut> pageView  = new PageView<OAOut>();
				pageView = oALeaveService.getOAOutListpageView(instance);	
				
				mv.addObject("pageView", pageView);
			}else if (in_type.equals("3")) {//出差列表
				OAOnBusiness instance = new OAOnBusiness();
				instance.setIn_type("2");
				if(UtilValidate.isNotEmpty(userName)){
					instance.setOn_business_person(userName);
					mv.addObject("userName", userName);
				}
				if(UtilValidate.isNotEmpty(emp_code)){
					instance.setEmp_code(emp_code);
					mv.addObject("emp_code", emp_code);
				}
				if(UtilValidate.isNotEmpty(apply_time_start)){
					instance.setApply_time_start(apply_time_start);
					mv.addObject("apply_time_start", apply_time_start);
				}
				if(UtilValidate.isNotEmpty(apply_time_end)){
					instance.setApply_time_end(apply_time_end + " 23:59:59");
					mv.addObject("apply_time_end", apply_time_end);
				}
				if(UtilValidate.isNotEmpty(page)){
					instance.setPage(Integer.parseInt(page));
					mv.addObject("page", page);
				}
				if(UtilValidate.isNotEmpty(maxResult)){
					instance.setMaxResult(Integer.parseInt(maxResult));
					mv.addObject("maxResult", maxResult);
				}
				PageView<OAOnBusiness> pageView  = new PageView<OAOnBusiness>();
				pageView = oALeaveService.getOAOnBusinessListpageView(instance);	
				
				mv.addObject("pageView", pageView);
			}
			
			mv.addObject("in_type", in_type);
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入excel
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/upfile")
	public String upScoreParam(Model m,HttpSession session,HttpServletRequest request) { 
		return "oa/excelUpOAInfo";
	}
	
	/**
	 * 上传信息
	 * @param response
	 * @param request
	 * @param file
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)  
    public void uploadFile(HttpServletResponse response,HttpServletRequest request,
    		@RequestParam(value="file", required=false) MultipartFile file) throws IOException{
        byte[] bytes = file.getBytes();  
        String uploadDir = request.getRealPath("/")+"upload";  
        File dirPath = new File(uploadDir);  
        if (!dirPath.exists()) {
            dirPath.mkdirs();  
        }  
        String sep = System.getProperty("file.separator"); 
        File uploadedFile = new File(uploadDir + sep  
                + file.getOriginalFilename());  
        FileCopyUtils.copy(bytes, uploadedFile);  
        
        response.getWriter().write("true");  
    }
	
	/**
	 * 读取excel信息，转化list
	 * @param fileName
	 * @param request
	 * @param ra
	 * @param m
	 * @return
	 */
	@ResponseBody  
	@RequestMapping("/getExcelInfoList")
	public Map<String, Object> getExcelInfoList(@ModelAttribute("fileName")String fileName,HttpServletRequest request,RedirectAttributes ra,Model m){
		String in_type = request.getParameter("in_type");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer message = new StringBuffer();
		if(in_type==null || in_type.equals("")){
			message.append("请选择导入类型！");
			map.put("message", message.toString());
			return map;
		}
		try {
			fileName = URLDecoder.decode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  
		@SuppressWarnings("deprecation")
		String uploadDir = request.getRealPath("/")+"upload";
		String sep = System.getProperty("file.separator");
	    String path = uploadDir + sep + fileName;
		List<String> list = null;
        try {
            list =ImportExcelUtil.exportListFromExcelCustom(new File(path), 0, 1);
            List<OALeave> oALeaveInfos = new ArrayList<OALeave>();
            List<OAOut> oAOutInfos = new ArrayList<OAOut>();
            List<OAOnBusiness> oAOnBusinessesInfos = new ArrayList<OAOnBusiness>();
            if(in_type.equals("1")){//解析请假信息
            	OALeave oALeaveTemp = null;
            	for(int i=0;i<list.size();i++){
                	String strOld = list.get(i);
                	String[] arrStr = strOld.split("\\|",-1);
                	String applyTime = StringUtil.nvlString(arrStr[1]).trim();   //申请日期
                	String operator = StringUtil.nvlString(arrStr[2]).trim();   //经办人
                	String leavePerson = StringUtil.nvlString(arrStr[3]).trim();   //请假人
                	String empCode = StringUtil.nvlString(arrStr[4]).trim();   //员工号
                	String company = StringUtil.nvlString(arrStr[5]).trim();   //所属分部
                	String department = StringUtil.nvlString(arrStr[6]).trim();   //所属部门
                	String post = StringUtil.nvlString(arrStr[7]).trim();   //所属岗位
                	String type = StringUtil.nvlString(arrStr[8]).trim();   //请假类型
                	String takingWorkTime = StringUtil.nvlString(arrStr[9]).trim();   //入职日期
                	String startDate = StringUtil.nvlString(arrStr[10]).trim();   //请假开始日期
                	String endDate = StringUtil.nvlString(arrStr[11]).trim();   //请假结束日期
                	String startTime = StringUtil.nvlString(arrStr[12]).trim();   //请假开始时间
                	String endTime = StringUtil.nvlString(arrStr[13]).trim();   //请假结束时间
                	String days = StringUtil.nvlString(arrStr[14]).trim();   //请假天数
                	
                	if(UtilValidate.isEmpty(leavePerson)){
                		message.append("第"+(i+1)+"条中【请假人】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(empCode)){
                		message.append("第"+(i+1)+"条中【员工编号】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(type)){
                		message.append("第"+(i+1)+"条中【请假类型】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(startDate)){
                		message.append("第"+(i+1)+"条中【请假开始日期】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(endDate)){
                		message.append("第"+(i+1)+"条中【请假结束日期】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(startTime)){
                		message.append("第"+(i+1)+"条中【请假开始时间】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(endTime)){
                		message.append("第"+(i+1)+"条中【请假结束时间】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(days)){
                		message.append("第"+(i+1)+"条中【请假天数】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	
                	if(UtilValidate.isEmpty(message.toString()) || "suc".equals(message.toString())){
                		oALeaveTemp = new OALeave();
                		oALeaveTemp.setOa_id(UUIDHexGenerator.generater());
                		oALeaveTemp.setApply_time(applyTime);
                		oALeaveTemp.setOperator(operator);
                		oALeaveTemp.setLeave_person(leavePerson);
                		DecimalFormat df = new DecimalFormat();
                		oALeaveTemp.setEmp_code(df.parse(empCode).toString());
                		oALeaveTemp.setCompany(company);
                		oALeaveTemp.setDepartment(department);
                		oALeaveTemp.setPost(post);
                		oALeaveTemp.setType(type);
                		oALeaveTemp.setTaking_work_time(takingWorkTime);
                		oALeaveTemp.setStart_date(startDate);
                		oALeaveTemp.setEnd_date(endDate);
                		oALeaveTemp.setStart_time(startTime);
                		oALeaveTemp.setEnd_time(endTime);
                		oALeaveTemp.setDays(days);
                		oALeaveTemp.setAdd_time(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                		oALeaveTemp.setIn_type("1");
                		oALeaveTemp.setIs_update("0");

                		oALeaveInfos.add(oALeaveTemp);
                	}
            	}
            	
            	if(UtilValidate.isNotEmpty(oALeaveInfos)){
             	   oALeaveService.saveOALeave(oALeaveInfos);
             	   message.append("suc" );  
                }else{
             	   message.append("没有可导入数据！" );  
                }
            }else if (in_type.equals("2")) {//解析外出信息
            	OAOut oAOutTemp = null;
            	for(int i=0;i<list.size();i++){
                	String strOld = list.get(i);
                	String[] arrStr = strOld.split("\\|",-1);
                	String applyTime = StringUtil.nvlString(arrStr[1]).trim();   //申请日期
                	String outPerson = StringUtil.nvlString(arrStr[2]).trim();   //外出人
                	String company = StringUtil.nvlString(arrStr[3]).trim();   //所属分部
                	String empCode = StringUtil.nvlString(arrStr[4]).trim();   //员工编号
                	String department = StringUtil.nvlString(arrStr[5]).trim();   //所属部门
                	String startDate = StringUtil.nvlString(arrStr[6]).trim();   //外出开始日期
                	String endDate = StringUtil.nvlString(arrStr[7]).trim();   //外出结束日期
                	String startTime = StringUtil.nvlString(arrStr[8]).trim();   //外出开始时间
                	String endTime = StringUtil.nvlString(arrStr[9]).trim();   //外出结束时间
                	String unPunchMoring = StringUtil.nvlString(arrStr[10]).trim();   //上午未打卡
                	String unPunchAfternoon = StringUtil.nvlString(arrStr[11]).trim();   //下午未打卡
                	
                	if(UtilValidate.isEmpty(outPerson)){
                		message.append("第"+(i+1)+"条中【外出人】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(empCode)){
                		message.append("第"+(i+1)+"条中【员工编号】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(startDate)){
                		message.append("第"+(i+1)+"条中【外出开始日期】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(endDate)){
                		message.append("第"+(i+1)+"条中【外出结束日期】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(startTime)){
                		message.append("第"+(i+1)+"条中【外出开始时间】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(endTime)){
                		message.append("第"+(i+1)+"条中【外出结束时间】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	
                	if(UtilValidate.isEmpty(message.toString()) || "suc".equals(message.toString())){
                		oAOutTemp = new OAOut();
                		oAOutTemp.setOa_id(UUIDHexGenerator.generater());
                		oAOutTemp.setApply_time(applyTime);
                		oAOutTemp.setOut_person(outPerson);
                		oAOutTemp.setCompany(company);
                		DecimalFormat df = new DecimalFormat();
                		oAOutTemp.setEmp_code(df.parse(empCode).toString());
                		oAOutTemp.setDepartment(department);
                		oAOutTemp.setStart_date(startDate);
                		oAOutTemp.setEnd_date(endDate);
                		oAOutTemp.setStart_time(startTime);
                		oAOutTemp.setEnd_time(endTime);
                		oAOutTemp.setUn_punch_moring(unPunchMoring);
                		oAOutTemp.setUn_punch_afternoon(unPunchAfternoon);
                		oAOutTemp.setAdd_time(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                		oAOutTemp.setIn_type("2");
                		oAOutTemp.setIs_update("0");
                		
                		oAOutInfos.add(oAOutTemp);
                	}
            	}
            	if(UtilValidate.isNotEmpty(oAOutInfos)){
              	   oALeaveService.saveOAOut(oAOutInfos);
              	   message.append("suc" );  
                 }else{
              	   message.append("没有可导入数据！");  
                 }
			}else if (in_type.equals("3")) {//解析出差信息
				OAOnBusiness oAOnBusinessTemp = null;
				for(int i=0;i<list.size();i++){
                	String strOld = list.get(i);
                	String[] arrStr = strOld.split("\\|",-1);
                	String onBusinessPerson = StringUtil.nvlString(arrStr[1]).trim();   //出差人
                	String company = StringUtil.nvlString(arrStr[2]).trim();   //所属分部
                	String department = StringUtil.nvlString(arrStr[3]).trim();   //所属部门
                	String empCode = StringUtil.nvlString(arrStr[4]).trim();   //员工编号
                	String applyTime = StringUtil.nvlString(arrStr[5]).trim();   //申请日期
                	String startSite = StringUtil.nvlString(arrStr[6]).trim();   //出发地点
                	String onBusinessSite = StringUtil.nvlString(arrStr[7]).trim();   //出差地点
                	String isCompanion = StringUtil.nvlString(arrStr[8]).trim();   //是否有同行人
                	String companion = StringUtil.nvlString(arrStr[9]).trim();   //同行人
                	String companionSex = StringUtil.nvlString(arrStr[10]).trim();   //同行人性别
                	String startDate = StringUtil.nvlString(arrStr[11]).trim();   //出发日期
                	String startTime = StringUtil.nvlString(arrStr[12]).trim();   //出发时间
                	String trafficTool = StringUtil.nvlString(arrStr[13]).trim();   //交通工具
                	String schedule = StringUtil.nvlString(arrStr[14]).trim();   //班次
                	String backDate = StringUtil.nvlString(arrStr[15]).trim();   //返回日期
                	String backTime = StringUtil.nvlString(arrStr[16]).trim();   //返回时间
                	String preDays = StringUtil.nvlString(arrStr[17]).trim();   //预计天数
                	String backTrafficTool = StringUtil.nvlString(arrStr[18]).trim();   //返回交通工具
                	String backShcedule = StringUtil.nvlString(arrStr[19]).trim();   //返回班次
                	
                	if(UtilValidate.isEmpty(onBusinessPerson)){
                		message.append("第"+(i+1)+"条中【出差人】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(empCode)){
                		message.append("第"+(i+1)+"条中【员工编号】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(startDate)){
                		message.append("第"+(i+1)+"条中【出发日期】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	if(UtilValidate.isEmpty(backDate)){
                		message.append("第"+(i+1)+"条中【返回日期】不能为空" );
                		message.append("\n");
                		map.put("message", message.toString());
                		return map;
                	}
                	
                	if(UtilValidate.isEmpty(message.toString()) || "suc".equals(message.toString())){
                		oAOnBusinessTemp = new OAOnBusiness();
                		oAOnBusinessTemp.setOa_id(UUIDHexGenerator.generater());
                		oAOnBusinessTemp.setOn_business_person(onBusinessPerson);
                		oAOnBusinessTemp.setCompany(company);
                		oAOnBusinessTemp.setDepartment(department);
                		DecimalFormat df = new DecimalFormat();
                		oAOnBusinessTemp.setEmp_code(df.parse(empCode).toString());
                		oAOnBusinessTemp.setApply_time(applyTime);
                		oAOnBusinessTemp.setStart_site(startSite);
                		oAOnBusinessTemp.setOn_business_site(onBusinessSite);
                		oAOnBusinessTemp.setIs_companion(isCompanion);
                		oAOnBusinessTemp.setCompanion(companion);
                		oAOnBusinessTemp.setCompanion_sex(companionSex);
                		oAOnBusinessTemp.setStart_date(startDate);
                		oAOnBusinessTemp.setStart_time(startTime);
                		oAOnBusinessTemp.setTraffic_tool(trafficTool);
                		oAOnBusinessTemp.setSchedule(schedule);
                		oAOnBusinessTemp.setBack_date(backDate);
                		oAOnBusinessTemp.setBack_time(backTime);
                		oAOnBusinessTemp.setPre_days(preDays);
                		oAOnBusinessTemp.setBack_traffic_tool(backTrafficTool);
                		oAOnBusinessTemp.setBack_shcedule(backShcedule);
                		oAOnBusinessTemp.setAdd_time(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                		oAOnBusinessTemp.setIn_type("3");
                		oAOnBusinessTemp.setIs_update("0");
                		
                		oAOnBusinessesInfos.add(oAOnBusinessTemp);
                	}
                	
				}
				
				if(UtilValidate.isNotEmpty(oAOnBusinessesInfos)){
	              	   oALeaveService.saveOAOnBusiness(oAOnBusinessesInfos);
	              	   message.append("suc" );  
	                 }else{
	              	   message.append("没有可导入数据！");  
	                 }
				
			}
           
        } catch (Exception e) { 
        	e.printStackTrace();
        }  
        map.put("message", message.toString());
		return map;
	}
	
}
