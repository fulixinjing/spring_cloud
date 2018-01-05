package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.TaskInfo;

public interface UtilsDao {

	/**
	 * 字典表查询
	 * 参数：map: type类型 00 公司|01 部门|02 团队|03 职位级别|04 岗位类型
	 * 			 parentId:从部里找出下面的所属团队
	 * @return list
	 * @throws Exception
	 */
	public List<Dictionary> getDictionaryList(Map<String,String> map);
	
	public List<Dictionary> getDictionaryList2(Map<String,String> map);
	
	/**
	 * 根据code_type,code查询字典表对象
	 * @param map
	 * @return
	 */
	public Dictionary getDictionary(Map<String,String> map);
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
	public List<TaskInfo> getTaskInfoList(Map<String,String> map);
	
	/**
	 * 通过查询条件进行分页检索
	 * @param qry
	 * @return
	 */
	public List<TaskInfo> getPageTaskData(Map<String,String> qry);

	/**
	 * 通过查询条件进行分页计算
	 * @param qry
	 * @return
	 */
	public Integer getPageTaskSize(Map<String,String> qry);
	
	/**
	 * 保存消息提醒功能
	 * 参数：Message
	 * 返回：空
	 */
	void saveMessage(Map<String,String> mm);
	
	/**
	 * 获取任务列表当前延期数据
	 * 返回：任务列表
	 */
	public List<TaskInfo> getTaskInfo();
	
	/**
	 * 获取任务列表即将（提前一天）延期数据
	 * 返回：任务列表
	 */
	public List<TaskInfo> getTaskInfoLater(String nowDate);
	
	
	/**
	 * 增加提醒表情况-list 
	 * @param map
	 * @return
	 */
	public void updateTaskInfo(List<Map<String,String>> list);
	
	/**
	 * 增加提醒表情况-list 
	 * @param map
	 * @return
	 */
	public void insertTxList(List<Map<String,String>> list);
}
