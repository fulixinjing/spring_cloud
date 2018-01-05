package cn.taskSys.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
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
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.ProjectInfo;
import cn.taskSys.entity.User;
import cn.taskSys.service.IProjectInfoService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.DateUtil;
import cn.taskSys.utils.ExportExcelUtil;
import cn.taskSys.utils.ImportExcelUtil;
import cn.taskSys.utils.StringUtil;
import cn.taskSys.utils.UUIDHexGenerator;
import cn.taskSys.utils.UtilValidate;


@Controller
@RequestMapping(value = "/project")
public class ProjectInfoController extends BaseAction<Object>{

	@Autowired
	private IProjectInfoService projectInfoService;
	
	@Autowired
	private UtilsService utilsService;
//	
//	@Autowired
//	private RoleService roleService;
	
	
	@RequestMapping(value = "/getProjectInfoList")
	public ModelAndView getAttenceList(@ModelAttribute("projectInfo")ProjectInfo projectInfo, HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/project/projectInfoList");
			PageView<ProjectInfo> pageView  = new PageView<ProjectInfo>();
			pageView = projectInfoService.getProjectInfoListpageView(projectInfo);	
			
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			List<User> pmolist = projectInfoService.getPMOList(null);
			mv.addObject("pageView", pageView);
			mv.addObject("pmolist", pmolist);
			mv.addObject("dictionaryLlist", dictionaryLlist);
			mv.addObject("projectInfo", projectInfo);
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/searchUtils")
	public void searchUtils(HttpServletRequest request, HttpServletResponse response){
		 try {
			String param = StringUtil.nvlString(request.getParameter("param"));
			 ProjectInfo projectInfo = new ProjectInfo();
			 projectInfo.setProName(param);
			 List<Map<String, String>> list = projectInfoService.findProjectInfos(projectInfo);
			 if(UtilValidate.isNotEmpty(list)){
				 JSONObject json = new JSONObject();
				 json.put("list", list);
				
				 PrintWriter out = null;
				 response.setContentType("text/plain");
				 out = response.getWriter();
				 out.write(json.toString());
					
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	/**
	 * 导入excel
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/upfile")
	public String upScoreParam(Model m,HttpSession session ) { 
		return "project/excelUpProjectInfo";
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
	
	@SuppressWarnings("deprecation")
	@ResponseBody  
	@RequestMapping("/getExcelInfoList")
	public Map<String, Object> getExcelInfoList(@ModelAttribute("fileName")String fileName,HttpServletRequest request,RedirectAttributes ra,Model m){
		
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer message = new StringBuffer();
		
		try {
			fileName = URLDecoder.decode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  
		String uploadDir = request.getRealPath("/")+"upload";
		String sep = System.getProperty("file.separator");
	    String path = uploadDir + sep + fileName;
		List<String> list = null;
        try {
//        	List<ProjectInfo> allProjectInfos = projectInfoService.allProjectInfos(null);
            list =ImportExcelUtil.exportListFromExcelCustom(new File(path), 0, 1);
            ProjectInfo projectInfoTemp = null;
            List<ProjectInfo> listTempInfos = new ArrayList<ProjectInfo>();
           for(int i=0;i<list.size();i++){
            	String strOld = list.get(i);
            	String[] arrStr = strOld.split("\\|",-1);
            	String proCode = StringUtil.nvlString(arrStr[1]).trim();   //项目编号
            	String proName = StringUtil.nvlString(arrStr[2]).trim();	  //项目名称
            	String proTypeZH = StringUtil.nvlString(arrStr[3]).trim();	//项目类型
            	String categoryZH = StringUtil.nvlString(arrStr[4]).trim();	//项目类别
            	String departmentName = StringUtil.nvlString(arrStr[5]).trim();		//实施部门名称
            	String xmManagerName = StringUtil.nvlString(arrStr[6]).trim();		//项目经理
            	String pmoName = StringUtil.nvlString(arrStr[7]).trim();		//PMO
            	String stage = StringUtil.nvlString(arrStr[8]).trim();		//当前阶段
            	String approvalTime = StringUtil.nvlString(arrStr[9]).trim();		//项目立项时间(yyyy-MM-dd)
            	String expectOnlineTime = StringUtil.nvlString(arrStr[10]).trim();		//计划上线时间(yyyy-MM-dd)
            	String schedule = StringUtil.nvlString(arrStr[11]).trim();		//时间进度情况
            	String developType = StringUtil.nvlString(arrStr[12]).trim();		//项目研发类型
            	String requirementControl = StringUtil.nvlString(arrStr[13]).trim();		//需求控制
            	String riskCondition = StringUtil.nvlString(arrStr[14]).trim();		//风险情况
            	String remark = StringUtil.nvlString(arrStr[15]).trim();		//备注
            	
            	if(UtilValidate.isEmpty(proCode)){
            		message.append("第"+(i+1)+"条中【项目编号】不能为空" );
            		message.append("\n");
            		map.put("message", message.toString());
            		return map;
            	}
            	if(UtilValidate.isEmpty(proName)){
            		message.append("第"+(i+1)+"条中【项目名称】不能为空" );
            		message.append("\n");
            		map.put("message", message.toString());
            		return map;
            	}
            	
            	/*if(UtilValidate.isNotEmpty(allProjectInfos)){
            		for (ProjectInfo projectInfo : allProjectInfos) {
						if(projectInfo.getCode().equals(proCode)){
							message.append("第"+(i+1)+"条中【项目编号】数据库中已存在,不需要再导入，请修改后再导入" );
		            		message.append("\n");
		            		break;
						}
					}
            		for (ProjectInfo projectInfo : allProjectInfos) {
						if(projectInfo.getProName().equals(proName)){
							message.append("第"+(i+1)+"条中【项目名】数据库中已存在,不需要再导入，请修改后再导入" );
		            		message.append("\n");
		            		break;
						}
					}
            	}*/
            	
           	 if(UtilValidate.isEmpty(message.toString()) || "suc".equals(message.toString())){
           		projectInfoTemp = new ProjectInfo();
           		projectInfoTemp.setId(UUIDHexGenerator.generater());
           		projectInfoTemp.setCode(proCode);
           		projectInfoTemp.setProName(proName);
           		
           		if (!"".equals(proTypeZH)) {
           			projectInfoTemp.setProType(proTypeZH);
           		}
           		if(!"".equals(categoryZH)){
           			projectInfoTemp.setCategory(categoryZH);
           		}
           		if(!"".equals(departmentName)){
           			projectInfoTemp.setDepartment(departmentName);
           		}
           		if (!"".equals(xmManagerName)) {//项目经理
           			projectInfoTemp.setXmManager(xmManagerName);
           		}
           		if (!"".equals(pmoName)) {
           			projectInfoTemp.setPmo(pmoName);
           		}
           		if (!"".equals(stage)) {
           			projectInfoTemp.setStage(stage);
           		}
           		if (!"".equals(approvalTime)) {//项目立项时间(yyyy-MM-dd)
           			projectInfoTemp.setApprovalTime(approvalTime);
           		}
           		if (!"".equals(expectOnlineTime)) {//计划上线时间(yyyy-MM-dd)
           			projectInfoTemp.setExpectOnlineTime(expectOnlineTime);
           		}
           		if (!"".equals(schedule)) {//时间进度情况
           			projectInfoTemp.setSchedule(schedule);
           		}
           		if (!"".equals(developType)) {
           			projectInfoTemp.setDevelopType(developType);
           		}
           		
           		/*List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
           		List<User> pmolist = projectInfoService.getPMOList(null);
           		
           		if (!"".equals(proTypeZH)) {//项目类型
           			for (Dictionary dic : dictionaryLlist) {
           				if(dic.getType_code().equals("13") && dic.getName().equals(proTypeZH)){
           					projectInfoTemp.setProType(dic.getCode());
           					break;
           				}
           			}
				}
           		if (!"".equals(categoryZH)) {//项目类别
           			for (Dictionary dic : dictionaryLlist) {
           				if(dic.getType_code().equals("14") && dic.getName().equals(categoryZH)){
           					projectInfoTemp.setCategory(dic.getCode());
           					break;
           				}
           			}
				}
           		if (!"".equals(departmentName)) {//实施部门
           			for (Dictionary dic : dictionaryLlist) {
           				if(dic.getType_code().equals("01") && dic.getName().equals(departmentName)){
           					projectInfoTemp.setDepartment(dic.getCode());
           					break;
           				}
           			}
				}
           		if (!"".equals(xmManagerName)) {//项目经理
           			projectInfoTemp.setXmManager(xmManagerName);
           		}
           		if (!"".equals(pmoName)) {//pmo
           			for (User user : pmolist) {
           				if(user.getUserName().equals(pmoName)){
           					projectInfoTemp.setPmo(user.getLoginName());
           					break;
           				}
           			}
           		}
           		if (!"".equals(stage)) {//当前阶段
           			for (Dictionary dic : dictionaryLlist) {
           				if(dic.getType_code().equals("12") && dic.getName().equals(stage)){
           					projectInfoTemp.setStage(dic.getCode());
           					break;
           				}
           			}
           		}
           		if (!"".equals(approvalTime)) {//项目立项时间(yyyy-MM-dd)
           			projectInfoTemp.setApprovalTime(approvalTime);
           		}
           		if (!"".equals(expectOnlineTime)) {//计划上线时间(yyyy-MM-dd)
           			projectInfoTemp.setExpectOnlineTime(expectOnlineTime);
           		}
           		if (!"".equals(schedule)) {//时间进度情况
           			projectInfoTemp.setSchedule(schedule);
           		}
           		if (!"".equals(developType)) {//研发类型
           			for (Dictionary dic : dictionaryLlist) {
           				if(dic.getType_code().equals("15") && dic.getName().equals(developType)){
           					projectInfoTemp.setDevelopType(dic.getCode());
           					break;
           				}
           			}
           		}*/
           		if (!"".equals(requirementControl)) {//需求控制
           			projectInfoTemp.setRequirementControl(requirementControl);
           		}
           		if (!"".equals(riskCondition)) {//风险情况
           			projectInfoTemp.setRiskCondition(riskCondition);
           		}
           		if (!"".equals(remark)) {//备注
           			projectInfoTemp.setRemark(remark);
           		}
           		projectInfoTemp.setIsDelete("0");
           		projectInfoTemp.setAddTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
           		projectInfoTemp.setIsDistribution("1");//1=可分配，0=不可分配
           		
           		listTempInfos.add(projectInfoTemp);
           	 }
           }
           if(UtilValidate.isNotEmpty(listTempInfos)){
        	   projectInfoService.saveProjectInfo(listTempInfos, "import");
        	   message.append("suc" );  
           }else{
        	   message.append("没有可导入数据！" );  
           }
        } catch (Exception e) { 
        	e.printStackTrace();
        }  
        map.put("message", message.toString());
		return map;
	}
	
	@RequestMapping(value="/addProject")
	public ModelAndView addProject() { 
		try {
			ModelAndView mv = new ModelAndView("project/addProjectInfo");
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
			List<User> pmolist = projectInfoService.getPMOList(null);
			mv.addObject("dictionaryLlist", dictionaryLlist);
			mv.addObject("pmolist", pmolist);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/saveProjectInfo")
	public void saveProjectInfo(@ModelAttribute("projectInfo")ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes ra) { 
		String res="";
		try {
			List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();
			//flag=modify更新项目信息
			String flag = StringUtil.nvlString(request.getParameter("flag"));
			String id = StringUtil.nvlString(request.getParameter("id"));
			if(flag.equals("modify")){//修改
				projectInfo.setId(id);
				projectInfoService.modifyProjectInfoByPro(projectInfo);
				res="ok";
				addMessage(ra, "信息修改成功！");
				session.setAttribute("resultFlag", "1");
			}else{//新增
				projectInfo.setIsDelete("0");
				projectInfo.setAddTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				if(projectInfo.getIsDistribution()==null){
					projectInfo.setIsDistribution("1");//1=可分配，0=不可分配
				}
				
				ProjectInfo pInfo = new ProjectInfo();
				pInfo.setCode(projectInfo.getCode());
				List<Map<String,String>> projectInfos = projectInfoService.findProjectInfos(pInfo);
				if(projectInfos!=null && !projectInfos.isEmpty()){
					for (Map<String, String> map : projectInfos) {
						String code = map.get("code");
						if(code.equals(projectInfo.getCode())){
							res="double";
						}else {
							projectInfoList.add(projectInfo);
							projectInfo.setId(UUIDHexGenerator.generater());
							projectInfoService.saveProjectInfo(projectInfoList,null);
							res="ok";
							addMessage(ra, "保存成功！");
							session.setAttribute("resultFlag", "1");
						}
					}
				}else {
					projectInfoList.add(projectInfo);
					projectInfo.setId(UUIDHexGenerator.generater());
					projectInfoService.saveProjectInfo(projectInfoList,null);
					res="ok";
					addMessage(ra, "保存成功！");
					session.setAttribute("resultFlag", "1");
				}
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(ra, "保存失败！");
			session.setAttribute("resultFlag", "0");
		}
		
		JSONObject json = new JSONObject();
		try {
			json.put("res", res);
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//return "redirect:/project/getProjectInfoList.do";
	}
	
	@RequestMapping(value="/findProjectInfoDetails")
	public ModelAndView findProjectInfoDetails(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("project/projectInfoDetails");
		ProjectInfo projectInfo = new ProjectInfo();
		try {
			String id = StringUtil.nvlString(request.getParameter("id"));
			if(!id.equals("")){
				projectInfo.setId(id);
				projectInfo = projectInfoService.findProjectInfoByPro(projectInfo);
				mv.addObject("projectInfo", projectInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value="/modifyProject")
	public ModelAndView modifyProject(HttpServletRequest request, HttpSession session, RedirectAttributes ra){
		ModelAndView mv = new ModelAndView("project/modifyProjectInfo");
		ProjectInfo projectInfo = new ProjectInfo();
		try {
			String id = StringUtil.nvlString(request.getParameter("id"));
			if(!id.equals("")){
				projectInfo.setId(id);
				projectInfo = projectInfoService.findProjectInfoByPro(projectInfo);
				mv.addObject("projectInfo", projectInfo);
				
				List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
				List<User> pmolist = projectInfoService.getPMOList(null);
				mv.addObject("dictionaryLlist", dictionaryLlist);
				mv.addObject("pmolist", pmolist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value="/delProject")
	public String delProject(HttpServletRequest request, HttpSession session, RedirectAttributes ra){
		ProjectInfo projectInfo = new ProjectInfo();
		try {
			String id = StringUtil.nvlString(request.getParameter("id"));
			if(!id.equals("")){
				projectInfo.setId(id);
				projectInfoService.delProjectInfoById(id);
				
				addMessage(ra, "删除成功！");
				session.setAttribute("resultFlag", "1");
			}else {
				addMessage(ra, "请选择要删除的任务。");
				session.setAttribute("resultFlag", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(ra, "删除失败！");
			session.setAttribute("resultFlag", "0");
		}
		return "redirect:/project/getProjectInfoList.do";
	}
	
	@RequestMapping(value = "/xmManager")
	public void xmManager(HttpServletRequest request, HttpServletResponse response){
		 try {
			String department = StringUtil.nvlString(request.getParameter("department"));
			User user = new User();
			user.setDepartment_id(department);
			List<Map<String, String>> list = projectInfoService.getXmManagerList(user);
			if(UtilValidate.isNotEmpty(list)){
				JSONObject json = new JSONObject();
				json.put("list", list);
				
				PrintWriter out = null;
				response.setContentType("text/plain");
				out = response.getWriter();
				out.write(json.toString());
					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	@RequestMapping(value = "/exportProject")
	public void exportProject(@ModelAttribute("projectInfo")ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<ProjectInfo> projectInfoList = projectInfoService.getProjectInfoListByPro(projectInfo);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			int count = 0;
			if (projectInfoList != null && projectInfoList.size()>0) {
				count = projectInfoList.size();
				//ProjectInfo==>Map
				Map<String,Object> map = null;
				for (ProjectInfo pInfo : projectInfoList) {
					if(!pInfo.getCode().equals("100001")){
						map = new HashMap<String,Object>();
						map.put("code", StringUtil.nvlString(pInfo.getCode()));
						map.put("proName", StringUtil.nvlString(pInfo.getProName()));
						map.put("proType", StringUtil.nvlString(pInfo.getProType()));
						map.put("category", StringUtil.nvlString(pInfo.getCategory()));
						map.put("department", StringUtil.nvlString(pInfo.getDepartment()));
						map.put("xmManager", StringUtil.nvlString(pInfo.getXmManager()));
						map.put("pmo", StringUtil.nvlString(pInfo.getPmo()));
						map.put("stage", StringUtil.nvlString(pInfo.getStage()));
						map.put("approvalTime", StringUtil.nvlString(pInfo.getApprovalTime()));
						map.put("expectOnlineTime", StringUtil.nvlString(pInfo.getExpectOnlineTime()));
						map.put("schedule", StringUtil.nvlString(pInfo.getSchedule()));
						map.put("developType", StringUtil.nvlString(pInfo.getDevelopType()));
						map.put("requirementControl", StringUtil.nvlString(pInfo.getRequirementControl()));
						map.put("riskCondition", StringUtil.nvlString(pInfo.getRiskCondition()));
						if(pInfo.getIsDistribution()!=null && pInfo.getIsDistribution().equals("1")){
							map.put("isDistribution", "是");
						}else {
							map.put("isDistribution", "否");
						}
						map.put("addTime", StringUtil.nvlString(pInfo.getAddTime()));
						map.put("remark", StringUtil.nvlString(pInfo.getRemark()));
						
						list.add(map);
					}
				}
			}
			
			int n = 50000;		//定义每个sheet存放的数据条数
			String sheetName = "项目列表";
			@SuppressWarnings("rawtypes")
			List<List> lists = new ArrayList<List>();
			
			String[] header = {"项目编号","项目名称","项目类型","项目类别","实施部门","项目经理","PMO负责人","当前所处阶段","项目立项时间","计划上线时间","时间进度情况","项目研发类型","需求控制","风险情况","是否可分配","导入时间","备注"};
			String[][] headers = {header};
			String[] key = {"code","proName","proType","category","department","xmManager","pmo","stage","approvalTime","expectOnlineTime","schedule","developType","requirementControl","riskCondition","isDistribution","addTime","remark"};
			String[][] keys = {key};
			
			String dateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
			String fileName = "项目列表"+dateStr+".xlsx";
			
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
}
