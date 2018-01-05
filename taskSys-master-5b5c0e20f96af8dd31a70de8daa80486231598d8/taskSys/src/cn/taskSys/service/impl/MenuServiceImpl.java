package cn.taskSys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.MenuDao;
import cn.taskSys.entity.Menu;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuDao menuDao;

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	


	@LogAnnotation(eventCode="2022001",eventProcess="")
	public List<Menu> getMenuTree() {
		 
		return menuDao.getMenuTree();
	}
	
	@LogAnnotation(eventCode="2022002",eventProcess="")
	public List<Menu> getMenuList(String menutype) {
		List<Menu> li=menuDao.getMenuList(menutype);
		return li;
	}
	
	@LogAnnotation(eventCode="2022003",eventProcess="")
	public List<Menu> getSecMenuList(String menutype) {
		 
		return menuDao.getSecMenuList(menutype);
	}
	
	@LogAnnotation(eventCode="2022004",eventProcess="")
	public List<Menu> getThiMenuList(String menutype) {
		 
		return menuDao.getThiMenuList(menutype);
	}
	
	@LogAnnotation(eventCode="2022005",eventProcess="")
	public List<Menu> getMenuInfo(String menuid) {
		 
		return menuDao.getMenuInfo(menuid);
	}
	
	@LogAnnotation(eventCode="2022006",eventProcess="")
	public int updateMenu(Menu menu) {
		
		return menuDao.updateMenu(menu);
		
	}
	 
	@LogAnnotation(eventCode="2022007",eventProcess="")
	 public int updateLowMenu(String menuid) {
		 
		 return menuDao.updateLowMenu(menuid);
	 }
	
	@LogAnnotation(eventCode="2022008",eventProcess="")
	 public int insertMenu(Menu menu) {
		 
		 return menuDao.insertMenu(menu);
	 }
	
	 //////汇金端//////////////////////////////////////////////////////////////////////
	
}
