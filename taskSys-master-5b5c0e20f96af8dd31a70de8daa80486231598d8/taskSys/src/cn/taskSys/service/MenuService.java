package cn.taskSys.service;

import java.util.List;

import cn.taskSys.entity.Menu;



public interface MenuService {
	 
	 public List<Menu> getMenuTree()throws Exception;
	 
	 public List<Menu> getMenuList(String menutype)throws Exception;
	 
	 public List<Menu> getSecMenuList(String pid)throws Exception;
	 
	 public List<Menu> getThiMenuList(String pid)throws Exception;
	 
	 public List<Menu> getMenuInfo(String menuid)throws Exception;
	 
	 public int updateMenu(Menu menu) throws Exception;
	 
	 public int updateLowMenu(String menuid) throws Exception;
	 
	 public int insertMenu(Menu menu) throws Exception;
	 
	 //////汇金端//////////////////////////////////////////////////////////////////////
	 
}
