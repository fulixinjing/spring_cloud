package cn.taskSys.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.taskSys.entity.Menu;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.LogService;
import cn.taskSys.service.MenuService;
import cn.taskSys.utils.EncodeUtil;
 
@Controller
@RequestMapping(value="/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuservice;
//	@Autowired  
//	private  HttpServletRequest request; 
	@Autowired
	private LogService logservice;
	
	List<Menu> parentlist=new ArrayList<Menu>();
	
	
	 /**
	  *  菜单数据
	  *  
	  */
	@RequestMapping(value="/menutree", method=RequestMethod.GET)
	@LogAnnotation(eventCode="1022001",eventProcess="")
	public String getMenuTree(HttpServletRequest request,Model m) { 
		List<Menu> menutreelist;
		StringBuilder str_tree=new StringBuilder();	
		str_tree.append("[");
		try{
			menutreelist=menuservice.getMenuTree();
			if(menutreelist != null && menutreelist.size()>0){
				for(int i=0;i<menutreelist.size();i++){
					Menu menu=menutreelist.get(i);
					if(i==menutreelist.size()-1){
						str_tree.append("{ id:"+menu.getId()+", pId:"+menu.getMenu_pid()+", " +
								   "name:'"+menu.getMenu_name()+"',open:true, url:'"+request.getContextPath()+"/menu/menuinfo.do?menuid="+menu.getId()+"', target:'ky'}]");
					}else{
						str_tree.append("{ id:"+menu.getId()+", pId:"+menu.getMenu_pid()+", " +
								   "name:'"+menu.getMenu_name()+"',open:true, url:'"+request.getContextPath()+"/menu/menuinfo.do?menuid="+menu.getId()+"', target:'ky'},");
					}
				}
			}else{
				//
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		m.addAttribute("pm", str_tree.toString());		
		return "menu/menutree";
	}
	
	
	 /**
	  *  鑾峰彇鑿滃崟璇︾粏淇℃伅
	  *  
	  */
	@RequestMapping(value="/menuinfo", method=RequestMethod.GET)
	@LogAnnotation(eventCode="1022002",eventProcess="")
	public String getMenuInfo(@RequestParam("menuid") String menuid,Model m) { 
		Menu menu=new Menu();
		String menu_name = "";
		if(menuid == null || "".equals(menuid)){
			
			//鎻愮ず淇℃伅
		}else{
			try{
				parentlist=menuservice.getMenuTree();
				List<Menu> menuinfolist=menuservice.getMenuInfo(menuid);
				if(menuinfolist != null && menuinfolist.size()>0){
					menu=menuinfolist.get(0);
					List<Menu> menupidlist=menuservice.getMenuInfo(menu.getMenu_pid());
					if(menupidlist != null && menupidlist.size()>0){
						menu_name = menupidlist.get(0).getMenu_name();
					}
				}else{
					//鎻愮ず淇℃伅
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		m.addAttribute("menuinfo", menu);
		m.addAttribute("parentlist", parentlist);
		m.addAttribute("menu_name", menu_name);
		return "menu/menuinfo";
	}
	
	 /**
	  *  鏇存柊鑿滃崟淇℃伅
	  *  
	  */
	@RequestMapping(value="/menuup", method=RequestMethod.POST)
	@LogAnnotation(eventCode="1022003",eventProcess="")
	@ResponseBody
	public Map<String, String> UpdateMenuInfo(@ModelAttribute("menu")Menu menu,HttpSession session) { 
		Map<String, String> map = new HashMap<String, String>(1);  
		if(menu == null ){
			
			//鎻愮ず淇℃伅
		}else{
			try{
				menuservice.updateMenu(menu); 
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"菜单【"+menu.getMenu_name()+"】修改操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_CDXG);
				if("1".equals(menu.getMenu_status())){
					menuservice.updateLowMenu(menu.getId());
				}
				map.put("success", "true");
			}catch(Exception e){
				e.printStackTrace();
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"菜单【"+menu.getMenu_name()+"】修改操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_MODUL_CDXG);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				map.put("error", "false");
			}
		}
		
		return map;
	}
	
	/**
	  *  鍒涘缓鑿滃崟淇℃伅
	  *  
	  */
	@RequestMapping(value="/newmenu", method=RequestMethod.GET)
	@LogAnnotation(eventCode="1022004",eventProcess="")
	public String newMenuInfo(@RequestParam("menuid") String menuid,Model m) { 
		m.addAttribute("menuid", menuid);
		m.addAttribute("parentlist", parentlist);
		return "menu/newmenu";
	}
	
	/**
	  *  淇濆瓨鑿滃崟淇℃伅
	  *  
	  */
	@RequestMapping(value="/menusave", method=RequestMethod.POST)
	@LogAnnotation(eventCode="1022005",eventProcess="")
	@ResponseBody
	public Map<String, String> insertMenuInfo(@ModelAttribute("menu")Menu menu,HttpSession session) { 
		Map<String, String> map = new HashMap<String, String>(1);  
		if(menu == null ){
			map.put("error", "false");
			//鎻愮ず淇℃伅
		}else{
			try{
				menuservice.insertMenu(menu);
				logservice.saveLog(EncodeUtil.LOG_INFO, 
						((User)session.getAttribute("JX_USERINFO")).getUserName(),
						((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
						"菜单【"+menu.getMenu_name()+"】新增操作成功！",
						((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
						EncodeUtil.LOG_MODUL_CDXZ);
				map.put("success", "true");	
			}catch(Exception e){
				e.printStackTrace();
				try {
					logservice.saveLog(EncodeUtil.LOG_ERROR, 
							((User)session.getAttribute("JX_USERINFO")).getUserName(),
							((User)session.getAttribute("JX_USERINFO")).getLoginName(), 
							"菜单【"+menu.getMenu_name()+"】新增操作失败！",
							((User)session.getAttribute("JX_USERINFO")).getLoginIp(),
							EncodeUtil.LOG_MODUL_CDXZ);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				map.put("error", "false");
			}
		}
		return map;
	}
	
	
	/**
	 * 获取所有菜单
	 */
	@RequestMapping(value="/allRight", method=RequestMethod.GET)
	@LogAnnotation(eventCode="1022006",eventProcess="")
	@ResponseBody
	public ModelAndView allRight(Model m,HttpSession session) {
		List<Menu> parentlist;
		StringBuilder str_tree = new StringBuilder();
		str_tree.append("[");
		try {
			parentlist=menuservice.getMenuTree();
			if (parentlist != null && parentlist.size() > 0) {
				for (int i = 0; i < parentlist.size(); i++) {
					Menu menu = parentlist.get(i);
					if (i == parentlist.size() - 1) {
						str_tree.append("{ id:" + menu.getId() + ", pId:" + menu.getMenu_pid() + ", " + "name:'"
								+ menu.getMenu_name() + "',open:true}]");
					} else {
						str_tree.append("{ id:" + menu.getId() + ", pId:" + menu.getMenu_pid() + ", " + "name:'"
								+ menu.getMenu_name() + "',open:true},");
					}
				}
			} else {
				//提示信息
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		m.addAttribute("pm", str_tree.toString());
		ModelAndView mv = new ModelAndView("/sltwin/selectSingleOrg");
		return mv;
	}
}
