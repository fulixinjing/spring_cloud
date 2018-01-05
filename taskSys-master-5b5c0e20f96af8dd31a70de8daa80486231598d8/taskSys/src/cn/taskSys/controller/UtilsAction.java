package cn.taskSys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.LogService;
import cn.taskSys.service.UtilsService;
import cn.taskSys.utils.EncodeUtil;
import cn.taskSys.utils.PublicUtil;
import cn.taskSys.utils.StringUtil;

@Controller
@RequestMapping(value = "/utils")
public class UtilsAction  extends BaseAction<Dictionary>{
//	private static Logger logger = Logger.getLogger(UtilsAction.class);

	@Autowired
	private UtilsService utilsService;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/addDictionary")
	@LogAnnotation(eventCode="16000106",eventProcess="")
	public ModelAndView addDictionary(@ModelAttribute("dictionary")Dictionary dictionary, HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/utils/addDic");
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
	
	@RequestMapping(value = "/saveDictionary")
	@LogAnnotation(eventCode="16000116",eventProcess="")
	public String saveDictionary(@ModelAttribute("dictionary")Dictionary dictionary, HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
		try {
			String departmentName = StringUtil.nvlString(request.getParameter("departmentName"));
			String teamName = StringUtil.nvlString(request.getParameter("teamName"));
			String companyName = StringUtil.nvlString(request.getParameter("companyName"));
			String flag = StringUtil.nvlString(request.getParameter("flag"));
			if(flag.equals("modify")){
				if(!StringUtil.isNUll(departmentName) && dictionary.getType_code().equals("01")){
					dictionary.setName(departmentName);
				}
				if(!StringUtil.isNUll(teamName) && dictionary.getType_code().equals("02")){
					dictionary.setName(teamName);
				}
				if(!StringUtil.isNUll(companyName) && dictionary.getType_code().equals("00")){
					dictionary.setName(companyName);
				}
				
				utilsService.modifyDictionary(dictionary);
				addMessage(ra, "更新成功！");
				session.setAttribute("resultFlag", "1");
				//日志信息
				User currentUser = PublicUtil.getUserOnline(request);
				logService.saveLog(EncodeUtil.LOG_INFO, 
						((User) session.getAttribute("JX_USERINFO")).getUserName(),
						((User) session.getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【" + currentUser.getUserName() + "】更新字典表（部门配置）成功！", 
						((User) session.getAttribute("JX_USERINFO")).getLoginIp(),
								EncodeUtil.LOG_MODEL_UPDATE);
			}else if(flag.equals("add")){
				if(!StringUtil.isNUll(departmentName) && dictionary.getType_code().equals("01")){
					dictionary.setName(departmentName);
					dictionary.setType_name("部门");
					dictionary.setParent_id("9999999");
				}
				if(!StringUtil.isNUll(teamName) && dictionary.getType_code().equals("02")){
					dictionary.setName(teamName);
					dictionary.setType_name("团队");
				}
				if(!StringUtil.isNUll(companyName) && dictionary.getType_code().equals("00")){
					dictionary.setName(companyName);
					dictionary.setType_name("公司");
				}
				
				//取得字典列表code最大值
				int maxCode = utilsService.getDictionaryMaxCode();
				//插入前判断是否存在
				int newCode = maxCode+1;
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", dictionary.getType_code());
				map.put("code", newCode+"");
				Dictionary dictionary2 = utilsService.getDictionary(map);
				if(dictionary2==null){
					dictionary.setCode(newCode+"");
					utilsService.saveDictionary(dictionary);
					addMessage(ra, "添加成功！");
					session.setAttribute("resultFlag", "1");
					
					//日志
					User currentUser = PublicUtil.getUserOnline(request);
					logService.saveLog(EncodeUtil.LOG_INFO, 
							((User) session.getAttribute("JX_USERINFO")).getUserName(),
							((User) session.getAttribute("JX_USERINFO")).getLoginName(), 
							"用户【" + currentUser.getUserName() + "】添加字典表（部门配置）成功！", 
							((User) session.getAttribute("JX_USERINFO")).getLoginIp(),
									EncodeUtil.LOG_MODLE_SUBMIT);
				}else{
					session.setAttribute("resultFlag", "0");
					addMessage(ra, "添加失败！");
				}
			}
			
			return "redirect:/utils/listDictionary.do";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("resultFlag", "0");
			addMessage(ra, "操作失败！");
		}
		
		return null;
	}
	
	/***
	 * 字典表列表查询
	 * @param dictionary
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listDictionary")
	@LogAnnotation(eventCode="10600126",eventProcess="")
	public ModelAndView getListDictionary(@ModelAttribute("dictionary")Dictionary dictionary,Model m, HttpServletRequest request) {
		try {	
			
			ModelAndView mv = new ModelAndView("/utils/listDic");
			
			Map<String, String> map = new HashMap<String, String>();
			if(dictionary.getType_code()!=null && !dictionary.getType_code().equals("")){
				map.put("type", dictionary.getType_code());
			}
			if(dictionary.getName()!=null && !dictionary.getName().equals("")){
				map.put("name", dictionary.getName());
			}
			
			List<Dictionary> dictionaryList = utilsService.getDictionaryList2(map);
			
			mv.addObject("dictionaryList", dictionaryList);
			mv.addObject("dictionary", dictionary);
			
//			/***********初始化下拉菜单 start**************/
//			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(null);//加载字典表信息
//						
//			mv.addObject("dictionaryLlist", dictionaryLlist);
//			/************初始化下拉菜单 end*************/
			
			
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/delDictionary")
	@LogAnnotation(eventCode="1201011",eventProcess="")
	public String delDictionary(HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
		try{
			String ids = request.getParameter("ids");
			if (!StringUtil.isNUll(ids)) {
				String[] idsArr = ids.split(",");
				List<String> idList = Arrays.asList(idsArr);
				utilsService.delDictionaryByIds(idList);
				
				logService.saveLog(EncodeUtil.LOG_INFO, 
						((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(), 
						"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】字典表管理-删除成功！", 
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_TASK_DELETE);
				addMessage(ra, "删除成功！");
				session.setAttribute("resultFlag", "1");
			}else {
				addMessage(ra, "请选择要删除的行。");
				session.setAttribute("resultFlag", "0");
			}
		}catch(Exception e){
			e.printStackTrace();
			try {
				logService.saveLog(EncodeUtil.LOG_ERROR,
						((User) request.getSession().getAttribute("JX_USERINFO")).getUserName(),
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginName(),
						"用户【"+((User) request.getSession().getAttribute("JX_USERINFO")).getUserName()+"】字典表管理-删除失败！",
						((User) request.getSession().getAttribute("JX_USERINFO")).getLoginIp(), 
							EncodeUtil.LOG_TASK_DELETE);
				addMessage(ra, "删除失败！");
				session.setAttribute("resultFlag", "0");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return "redirect:/utils/listDictionary.do";
		
	}
	
	@RequestMapping(value = "/modifyDictionary")
	public ModelAndView modifyDictionary(HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView("/utils/modifyDic");
			String id = StringUtil.nvlString(request.getParameter("id"));
			String type_code = StringUtil.nvlString(request.getParameter("type_code"));
			Map<String, String> map_p1 = new HashMap<String, String>();
			Map<String, String> map_p2 = new HashMap<String, String>();
			String pid=null;
			if(!id.equals("")){
				map_p1.put("id", id);
				Dictionary dictionaryT = utilsService.getDictionary(map_p1);
				if(!type_code.endsWith("00")){
					map_p2.put("id", dictionaryT.getParent_id());
					Dictionary parentDictionary = utilsService.getDictionary(map_p2);
					mv.addObject("parentDictionary", parentDictionary);
				}
				pid = dictionaryT.getParent_id();
				mv.addObject("dictionaryT", dictionaryT);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "01");
			List<Dictionary> dictionaryLlist = utilsService.getDictionaryList(map);//加载字典表信息
			mv.addObject("dictionaryLlist", dictionaryLlist);
			
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("type", "02");
			map2.put("parentId", pid);
			List<Dictionary> teamlist = utilsService.getDictionaryList(map2);
			
			mv.addObject("teamlist", teamlist);
			mv.addObject("type_code", type_code);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//根据所选部门联动带出部门团队
	@RequestMapping("/change/team")
	@LogAnnotation(eventCode="1032112",eventProcess="")
	public void changeTeam(HttpServletRequest request, HttpServletResponse response) {
		try {
			String parentId = StringUtil.nvlString(request.getParameter("parentId"));
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "02");
			map.put("parentId", parentId);
			List<Dictionary> listT = utilsService.getDictionaryList(map);
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			Map<String, String> mapT = null;
			if(listT!=null && listT.size()>0){
				for (Dictionary dicT : listT) {
					mapT = new HashMap<String, String>();
					mapT.put("id", dicT.getId());
					mapT.put("type_code", dicT.getType_code());
					mapT.put("type_name", dicT.getType_name());
					mapT.put("code", dicT.getCode());
					mapT.put("name", dicT.getName());
					mapT.put("parent_id", dicT.getParent_id());
					
					list.add(mapT);
				}
				
			}
			
			JSONObject  json=new JSONObject();
			PrintWriter out = null;
			response.setContentType("text/plain");
			out = response.getWriter();
			json.put("list", list);
			
			out.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}

