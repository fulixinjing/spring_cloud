package cn.taskSys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.ProjectInfoDao;
import cn.taskSys.entity.ProjectInfo;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.service.IProjectInfoService;

@Service("projectInfoService")
public class ProjectInfoServiceImpl implements IProjectInfoService {

	@Autowired
	private ProjectInfoDao projectInfoDao;
	
	public void setProjectInfoDao(ProjectInfoDao projectInfoDao) {
		this.projectInfoDao = projectInfoDao;
	}

	@Override
	public PageView<ProjectInfo> getProjectInfoListpageView(ProjectInfo projectInfo) throws Exception {
		PageView<ProjectInfo> pageView = new PageView<ProjectInfo>(
				projectInfo.getMaxResult(),
				projectInfo.getPage());// 需要设置当前页
		try {	
			int count = projectInfoDao.getProjectInfoListCount(projectInfo);// 获取列表条数
			List<ProjectInfo> projectInfoList = projectInfoDao.getProjectInfoList(projectInfo);// 获取列表数据
			
			QueryResult<ProjectInfo> qr = new QueryResult<ProjectInfo>();
			qr.setResultlist(projectInfoList);
			qr.setTotalrecord(count);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageView;
	}

	@Override
	public void saveProjectInfo(List<ProjectInfo> projectInfoList, String flag) throws Exception {
		if(projectInfoList!=null && projectInfoList.size()>0){
			if(flag!=null && flag.equals("import")){//批量导入
				projectInfoDao.updateProjectInfo();
			}
			projectInfoDao.saveProjectInfo(projectInfoList);
		}

	}

	@Override
	public List<ProjectInfo> getProjectName(Map<String, Object> map)
			throws Exception {
		return projectInfoDao.getProjectName(map);
	}

	@Override
	public List<User> getPMOList(User user) throws Exception {
		return projectInfoDao.getPMOList(user);
	}

	@Override
	public List<Map<String, String>> findProjectInfos(ProjectInfo projectInfo) throws Exception {
		return projectInfoDao.findProjectInfos(projectInfo);
	}

	@Override
	public List<ProjectInfo> allProjectInfos(ProjectInfo projectInfo) throws Exception {
		return projectInfoDao.allProjectInfos(projectInfo);
	}

	@Override
	public ProjectInfo findProjectInfoByPro(ProjectInfo projectInfo) throws Exception {
		return projectInfoDao.findProjectInfoByPro(projectInfo);
	}

	@Override
	public void modifyProjectInfoByPro(ProjectInfo projectInfo)	throws Exception {
		projectInfoDao.modifyProjectInfoByPro(projectInfo);
	}

	@Override
	public void delProjectInfoById(String id) throws Exception {
		projectInfoDao.delProjectInfoById(id);
	}

	@Override
	public List<Map<String, String>> getXmManagerList(User user) throws Exception {
		return projectInfoDao.getXmManagerList(user);
	}

	@Override
	public List<ProjectInfo> getProjectInfoListByPro(ProjectInfo projectInfo) throws Exception {
		return projectInfoDao.getProjectInfoListByPro(projectInfo);
	}

    @Override
    public List<ProjectInfo> getProjectInfoListByDepartment(ProjectInfo projectInfo) throws Exception{
        return projectInfoDao.getProjectInfoListByDepartment(projectInfo);
    }

    @Override
    public List<Map<String, Object>> getTaskInfoListByProCode(TaskInfo taskinfo,int page)throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("taskinfo", taskinfo);
        map.put("page", page);
        List<Map<String, Object>> list = projectInfoDao.getTaskInfoListByProCode(map);
        for (Map<String, Object> map2 : list) {
			 String  status = (String) map2.get("taskstatus");	
			 if("1".equals(status) || "2".equals(status) || "3".equals(status) ){
				 map2.put("color","#73d047");
				 map2.put("showstatus", "进行中");
			 }else if("4".equals(status) || "5".equals(status)){
				 map2.put("color","#de3535" );
				 map2.put("showstatus", "延期");
			 }else if("6".equals(status) || "7".equals(status) || "8".equals(status) ){
				 map2.put("color", "#7cbbdd");
				 map2.put("showstatus", "完成");
			 }
		}
        return list;
    }

}
