package cn.taskSys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.ProjectInfo;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.service.IProjectInfoService;
import cn.taskSys.service.ITaskQueryService;
import cn.taskSys.utils.StringUtil;


@Controller
@RequestMapping(value = "/trello")
public class TrelloController extends BaseAction<Object>{
	@Autowired
	private IProjectInfoService infoService;
	
	@Autowired
	private ITaskQueryService taskService;
	

	

	/**
	 * 保存任务
	 * 
	 * @param map
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "/trelloList")
	public String trelloList(HttpSession session, HttpServletRequest request,HttpServletResponse response,Model m) {
	    List<ProjectInfo> departmentTreelist = null;
        StringBuilder str_tree=new StringBuilder(); 
        List<Map<String, Object>> taskInfolist = null;
        str_tree.append("[");
        ProjectInfo info = null;
        try{
            departmentTreelist=infoService.getProjectInfoListByDepartment(null);
            if(departmentTreelist != null && departmentTreelist.size()>0){
                for(int i=0;i<departmentTreelist.size();i++){
                     info=departmentTreelist.get(i);
                    if(i==departmentTreelist.size()-1){
                        str_tree.append("{ id:'"+info.getId()+"', name:'"+info.getProName()+"',open:true, url:'"+request.getContextPath()+"/trello/projectstage.do?code="+info.getCode()+"', target:'ky'}]");
                    }else{
                        str_tree.append("{ id:'"+info.getId()+"', name:'"+info.getProName()+"',open:true, url:'"+request.getContextPath()+"/trello/projectstage.do?code="+info.getCode()+"', target:'ky'},");
                    }
                }
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setProjectStage("1");
                taskInfolist = infoService.getTaskInfoListByProCode(taskInfo,1);
                
            }else{
                //
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        m.addAttribute("pm", str_tree.toString());
        m.addAttribute("code", info.getCode());
        m.addAttribute("taskInfolist", taskInfolist);
        m.addAttribute("departmentList", departmentTreelist);
        return "trello/trelloList";
	}
	public  int getParam(HttpServletRequest request,String str){
		int page = 1;
		try {
			String parameter = request.getParameter(str);
			String pagestr = StringUtil.nvlString(parameter);
			if(StringUtils.isNotBlank(pagestr)){
				page = Integer.parseInt(pagestr);
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
		return page;
	}
	@RequestMapping(value="/departmentList")
    public String departmentList(HttpServletRequest request,Model m) {
		List<ProjectInfo> departmentList = null;
		try {
			departmentList=infoService.getProjectInfoListByDepartment(null);
		} catch (Exception e) {
			e.getMessage();
		}
		m.addAttribute("departmentList", departmentList);
		 return "trello/departmentList";
	}
	@RequestMapping(value="/projectstage")
    public String projectstage(HttpServletRequest request,Model m,HttpSession session) { 
        TaskInfo taskInfo = new TaskInfo();
        List<Map<String, Object>> taskInfolistqidong = null;
        List<Map<String, Object>> taskInfolistguihua = null;
        List<Map<String, Object>> taskInfolistshouwei = null;
        List<Map<String, Object>> taskInfolistqita = null;
        List<Map<String, Object>> taskInfolistfenxi = null;
        List<Map<String, Object>> taskInfolistsheji = null;
        List<Map<String, Object>> taskInfolistbianma = null;
        List<Map<String, Object>> taskInfolistceshi = null;
        List<Map<String, Object>> taskInfolistweihu = null;
        List<ProjectInfo> departmentList = null;
        String code="";
        String flag="";
        String fcode=request.getParameter("fcode");
        String proName=request.getParameter("proName");
        ProjectInfo projectInfo = new ProjectInfo();
        try{
        	code = request.getParameter("code");//编码
        	if(code!=null&&!"".equals(code)){
        	    code = new String(code.getBytes("iso-8859-1"),"gb2312");
            }
        	flag = StringUtil.nvlString(request.getParameter("flag"));//页面显示点击第几个框
        	if(StringUtils.isBlank(flag)){
        		flag = "1";
        	}
        	//判断是否点击下拉框
        	
        	    if(proName!=null&&!"".equals(proName)){
        	        proName = new String(proName.getBytes("iso-8859-1"),"gb2312");
        	    }
        	
        	    if ((proName!=null&&!"".equals(proName))&&fcode!=null&&!"".equals(fcode)){
        	        projectInfo.setProName(proName);
        	    }
        	    String dname = ((User) session.getAttribute("JX_USERINFO")).getDepartment_Name();
                if(StringUtils.isNotEmpty(dname)){
                    projectInfo.setDepartment(dname);
                }
        	    //projectInfo.setDepartment("科技公司-企业信息部");
        	    departmentList=infoService.getProjectInfoListByDepartment(projectInfo);
        	
        	int page1 = getParam(request,"page1");
        	int page2 = getParam(request,"page2");
        	int page3 = getParam(request,"page3");
        	int page4 = getParam(request,"page4");
        	int page5 = getParam(request,"page5");
        	int page6 = getParam(request,"page6");
        	int page7 = getParam(request,"page7");
        	int page8 = getParam(request,"page8");
        	int page9 = getParam(request,"page9");
            String morestage = request.getParameter("morestage"); 
            if("1".equals(morestage)){
            	page1 = page1+1;
    	    }else if("2".equals(morestage)){
            	page2 = page2+1;
    	    }else if("3".equals(morestage)){
            	page3 = page3+1;
    	    }
    	    else if("4".equals(morestage)){
            	page4 = page4+1;
    	    }
    	    else if("5".equals(morestage)){
            	page5 = page5+1;
    	    }
    	    else if("6".equals(morestage)){
            	page6 = page6+1;
    	    }
    	    else if("7".equals(morestage)){
            	page7 = page7+1;
    	    }
    	    else if("8".equals(morestage)){
            	page8 = page8+1;
    	    }
    	    else if("9".equals(morestage)){
            	page9 = page9+1;
    	    }
            if(StringUtils.isNotBlank(code)){
            	taskInfo.setProjectCode(code);
            }
            if("3".equals(flag)){
            	//项目实施阶段
            	//分析
            	taskInfo.setProjectStage("3"); 
            	taskInfolistfenxi=infoService.getTaskInfoListByProCode(taskInfo,page3);
            	//设计
            	taskInfo.setProjectStage("4"); 
            	taskInfolistsheji=infoService.getTaskInfoListByProCode(taskInfo,page4);
            	//编码
            	taskInfo.setProjectStage("5"); 
            	taskInfolistbianma=infoService.getTaskInfoListByProCode(taskInfo,page5);
            	//测试
            	taskInfo.setProjectStage("7"); 
            	taskInfolistceshi=infoService.getTaskInfoListByProCode(taskInfo,page7);
            	//维护
            	taskInfo.setProjectStage("9"); 
            	taskInfolistweihu=infoService.getTaskInfoListByProCode(taskInfo,page9);
            }else{
            	taskInfo.setProjectStage(flag); 
            	if("1".equals(flag)){
            		taskInfolistqidong=infoService.getTaskInfoListByProCode(taskInfo,page1);
            	}else if("2".equals(flag)){
            		taskInfolistguihua=infoService.getTaskInfoListByProCode(taskInfo,page2);
            	}else if("5".equals(flag)){
            		taskInfolistqita=infoService.getTaskInfoListByProCode(taskInfo,page6);
            	}else if("4".equals(flag)){
            		taskInfolistshouwei=infoService.getTaskInfoListByProCode(taskInfo,page8);
            	}
            
        }
            m.addAttribute("page1", page1);
            m.addAttribute("page2", page2);
            m.addAttribute("page3", page3);
            m.addAttribute("page4", page4);
            m.addAttribute("page5", page5);
            m.addAttribute("page6", page6);
            m.addAttribute("page7", page7);
            m.addAttribute("page8", page8);
            m.addAttribute("page9", page9);
            m.addAttribute("departmentList", departmentList);
        }catch(Exception e){
            e.getMessage(); 
        }
        
        m.addAttribute("taskInfolistqidong", taskInfolistqidong);
        m.addAttribute("taskInfolistguihua", taskInfolistguihua);
        m.addAttribute("taskInfolistshouwei", taskInfolistshouwei);
        m.addAttribute("taskInfolistqita", taskInfolistqita);
        
        m.addAttribute("taskInfolistfenxi", taskInfolistfenxi);
        m.addAttribute("taskInfolistsheji", taskInfolistsheji);
        m.addAttribute("taskInfolistbianma", taskInfolistbianma);
        m.addAttribute("taskInfolistceshi", taskInfolistceshi);
        m.addAttribute("taskInfolistweihu", taskInfolistweihu);
        
        m.addAttribute("code", code);
        m.addAttribute("flag", flag);
        return "trello/projectstageinfo";
    }
	
	
	@RequestMapping(value="/getTaskInfo")
    public ModelAndView getTaskInfo(HttpSession session, HttpServletRequest request, Model m) {
	    ModelAndView mv = new ModelAndView("trello/projectstageinfo");
        try{
            String id = StringUtil.nvlString(request.getParameter("id"));
            TaskInfo taskInfo = taskService.getTaskInfoById(id);
            
            m.addAttribute("taskInfo", taskInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mv;
        
    }
	
}
