package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.pager.PagerModel;

public interface UtilsService {
	/**
	 * 字典表查询
	 * 参数：map: type类型 00 公司|01 部门|02 团队|03 职位级别|04 岗位类型
	 * 			 parentId:从部里找出下面的所属团队
	 * @return list
	 * @throws Exception
	 */
	public List<Dictionary> getDictionaryList(Map<String,String> map) throws Exception;
	
	public List<Dictionary> getDictionaryList2(Map<String,String> map) throws Exception;
	
	/**
	 * 根据code_type,code查询字典表对象
	 * @param map
	 * @return Dictionary
	 */
	public Dictionary getDictionary(Map<String,String> map) throws Exception;
	
	
	/**
	 * 取得字典列表code最大值
	 * @return
	 */
	public int getDictionaryMaxCode();
	
	public void saveDictionary(Dictionary dictionary);
	
	public void delDictionaryByIds(List<String> ids);
	
	public void modifyDictionary(Dictionary dictionary);
	/**
	 * 获取任务进度列表
	 * 参数：map: status 状态	,createUser创建人,exeUser 执行人
	 * 返回 ：List
	 */
	public List<TaskInfo> getTaskInfoList(Map<String,String> map)throws Exception;
	
	/**
	 * 获取任务进度列表
	 * 参数：map: status 状态	,createUser创建人,exeUser 执行人
	 * 返回 ：List分页
	 */
	PagerModel getPageTaskInfoList(Map<String,String> map) throws Exception;
	
	/**
	 * 保存消息提醒功能
	 * 参数：map:Message
	 * 返回：空
	 */
	public void saveMessage(Map<String,String> mm)throws Exception;
	
	/**
	 * 获取任务列表当前延期数据
	 * 返回：任务列表
	 */
	public List<TaskInfo> getTaskInfoList()throws Exception;
	
	/**
	 * 获取任务列表即将（提前一天）延期数据
	 * 返回：任务列表
	 */
	public List<TaskInfo> getTaskInfoLaterList(String nowDate)throws Exception;
	
	/**
	 * 增加提醒表情况-list 
	 * @param map
	 * @return
	 */
	public void updateTaskInfo(List<Map<String,String>> list)throws Exception;
	
	/**
	 * 增加提醒表情况-list 
	 * @param map
	 * @return
	 */
	public void insertTxList(List<Map<String,String>> list)throws Exception;
}
