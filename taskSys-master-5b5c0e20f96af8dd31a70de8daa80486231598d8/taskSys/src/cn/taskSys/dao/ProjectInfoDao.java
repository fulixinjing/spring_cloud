package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.ProjectInfo;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;


public interface ProjectInfoDao {

	public List<ProjectInfo> getProjectInfoList(ProjectInfo projectInfo) throws Exception;//分页列表
	
	public int getProjectInfoListCount(ProjectInfo projectInfo) throws Exception;
	
	void saveProjectInfo(List<ProjectInfo> projectInfoList) throws Exception;//保存
	
	public List<User> getPMOList(User user) throws Exception;
	
	public List<Map<String, String>> findProjectInfos(ProjectInfo projectInfo) throws Exception;
	
	public List<ProjectInfo> allProjectInfos(ProjectInfo projectInfo) throws Exception;
	
	public ProjectInfo findProjectInfoByPro(ProjectInfo projectInfo) throws Exception;
	
	public void modifyProjectInfoByPro(ProjectInfo projectInfo) throws Exception;
	
	public void delProjectInfoById(String id) throws Exception;

	List<ProjectInfo> getProjectName(Map<String, Object> map) throws Exception;//根据输入模糊查询
	
	public List<Map<String, String>> getXmManagerList(User user) throws Exception;
	
	public void updateProjectInfo() throws Exception;

	public List<ProjectInfo> getProjectInfoListByPro(ProjectInfo projectInfo) throws Exception;
	
	/**
	 * 
	 * //TODO 只查询部门为"科技公司-企业信息部"的项目
	 * @return
	 * @throws Exception
	 */
	public List<ProjectInfo> getProjectInfoListByDepartment(ProjectInfo projectInfo) throws Exception;
	
	
	/**
	 * 
	 * //TODO 根据项目编号查询任务信息
	 * @param projectInfo
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTaskInfoListByProCode(Map<String, Object> map) throws Exception;
	
}
