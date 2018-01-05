package cn.taskSys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.UtilsDao;
import cn.taskSys.entity.Dictionary;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.UtilsService;

@Service("UtilsService")
public class UtilsServiceImpl  implements UtilsService{
	@Autowired
	private UtilsDao utilsDao;	
	
	/**
	 * 字典表查询
	 * 参数：map: type类型 00 公司|01 部门|02 团队|03 职位级别|04 岗位类型
	 * 			 parentId:从部里找出下面的所属团队
	 * @return list
	 * @throws Exception
	 */
	@LogAnnotation(eventCode="2034001",eventProcess="")
	public List<Dictionary> getDictionaryList(Map<String,String> map){
		return utilsDao.getDictionaryList(map);
	}
	
	@LogAnnotation(eventCode="2034061",eventProcess="")
	public List<Dictionary> getDictionaryList2(Map<String,String> map){
		return utilsDao.getDictionaryList2(map);
	}
	

	/**
	 * 根据code_type,code查询字典表对象
	 * @param map
	 * @return Dictionary
	 */
	@LogAnnotation(eventCode="1112010",eventProcess="")
	@Override
	public Dictionary getDictionary(Map<String, String> map) throws Exception {
		return utilsDao.getDictionary(map);
	}

	/**
	 * 获取任务进度列表
	 * 参数：map: status 状态	,createUser创建人,exeUser 执行人
	 * 返回 ：List
	 */
	@LogAnnotation(eventCode="2034002",eventProcess="")
	public List<TaskInfo> getTaskInfoList(Map<String,String> map)throws Exception{
		return utilsDao.getTaskInfoList(map);
	}
	
	/**
	 * 获取任务进度列表
	 * 参数：map: status 状态	,createUser创建人,exeUser 执行人
	 * 返回 ：List分页
	 */
	@LogAnnotation(eventCode="2034003",eventProcess="")
	public PagerModel getPageTaskInfoList(Map<String,String> map) throws Exception{
		PagerModel pager = new PagerModel();
		pager.setDatas(getPageTaskData(map));
		pager.setTotal(getPageTaskSize(map));
		return pager;

	}
	/**
	 * 通过查询条件进行分页检索
	 * @param qry
	 * @return
	 */
	private List<TaskInfo> getPageTaskData(Map<String,String> qry) throws Exception {
		return utilsDao.getPageTaskData(qry);
	}
	
	/**
	 * 通过查询条件进行分页计算
	 * @param qry
	 * @return
	 */
	private Integer getPageTaskSize(Map<String,String> qry) throws Exception {
		return utilsDao.getPageTaskSize(qry);
	}
	
	/**
	 * 保存消息提醒功能
	 * 参数：Message
	 * 返回：空
	 */
	public void saveMessage(Map<String,String> mm)throws Exception{
		 utilsDao.saveMessage(mm);
	}
	
	/**
	 * 获取任务列表当前延期数据
	 * 返回：任务列表
	 */
	public List<TaskInfo> getTaskInfoList()throws Exception{
		 return utilsDao.getTaskInfo();
	}
	
	/**
	 * 获取任务列表即将（提前一天）延期数据
	 * 返回：任务列表
	 */
	public List<TaskInfo> getTaskInfoLaterList(String nowDate)throws Exception{
		 return utilsDao.getTaskInfoLater(nowDate);
	}
	
	/**
	 * 增加提醒表情况-list 
	 * @param map
	 * @return
	 */
	public void updateTaskInfo(List<Map<String,String>> list)throws Exception{
		utilsDao.updateTaskInfo(list);
	}
	
	/**
	 * 增加提醒表情况-list 
	 * @param map
	 * @return
	 */
	public void insertTxList(List<Map<String,String>> list)throws Exception{
		utilsDao.insertTxList(list);
	}


	@Override
	public int getDictionaryMaxCode() {
		return utilsDao.getDictionaryMaxCode();
	}


	@Override
	public void saveDictionary(Dictionary dictionary) {
		utilsDao.saveDictionary(dictionary);
	}

	@Override
	public void delDictionaryByIds(List<String> ids) {
		utilsDao.delDictionaryByIds(ids);
	}

	@Override
	public void modifyDictionary(Dictionary dictionary) {
		utilsDao.modifyDictionary(dictionary);
	}

}
