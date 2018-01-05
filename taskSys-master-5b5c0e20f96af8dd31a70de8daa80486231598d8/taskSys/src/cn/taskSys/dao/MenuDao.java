package cn.taskSys.dao;

import java.util.List;

import cn.taskSys.entity.Menu;


public interface MenuDao {
	 /**
	  *  获取菜单树
	  *  @author KANGYU
	  *  @return 菜单list
	  */
	 public List<Menu> getMenuTree(); 
	 
	 public List<Menu> getMenuList(String menutype); 
	 
	 public List<Menu> getMenuInfo(String id); 
	 
	 public List<Menu> getSecMenuList(String id); 
	 
	 public List<Menu> getThiMenuList(String id); 
	 
	 public int updateMenu(Menu menu);
	 
	 public int updateLowMenu(String menuid) ;
	 
	 public int insertMenu(Menu menu) ;
	
}
