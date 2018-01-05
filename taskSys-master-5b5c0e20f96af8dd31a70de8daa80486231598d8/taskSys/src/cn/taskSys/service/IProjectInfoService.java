package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.ProjectInfo;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;

public interface IProjectInfoService {
	
	PageView<ProjectInfo> getProjectInfoListpageView(ProjectInfo projectInfo)throws Exception;//分页列表
	
	void saveProjectInfo(List<ProjectInfo> projectInfoList, String flag) throws Exception;//保存
	
	List<ProjectInfo> getProjectName(Map<String, Object> map) throws Exception;//根据输入模糊查询
	
	public List<User> getPMOList(User user) throws Exception;
	
	public List<Map<String, String>> findProjectInfos(ProjectInfo projectInfo) throws Exception;
	
	public List<ProjectInfo> allProjectInfos(ProjectInfo projectInfo) throws Exception;
	
	public ProjectInfo findProjectInfoByPro(ProjectInfo projectInfo) throws Exception;
	
	public void modifyProjectInfoByPro(ProjectInfo projectInfo) throws Exception;
	
	public void delProjectInfoById(String id) throws Exception;
	
	public List<Map<String, String>> getXmManagerList(User user) throws Exception;
	
	public List<ProjectInfo> getProjectInfoListByPro(ProjectInfo projectInfo) throws Exception;
	
	public List<ProjectInfo> getProjectInfoListByDepartment(ProjectInfo projectInfo) throws Exception;//只查询部门为"科技公司-企业信息部"的项目
	
	public List<Map<String, Object>> getTaskInfoListByProCode(TaskInfo taskinfo,int page) throws Exception;//根据项目编号查询任务信息
}
