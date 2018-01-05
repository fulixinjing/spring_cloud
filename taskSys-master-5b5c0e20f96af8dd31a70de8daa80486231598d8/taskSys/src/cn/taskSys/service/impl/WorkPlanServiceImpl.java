package cn.taskSys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.WorkPlanDao;
import cn.taskSys.entity.WorkPlan;
import cn.taskSys.service.IWorkPlanService;

@Service("workPlanService")
public class WorkPlanServiceImpl implements IWorkPlanService {

	@Autowired
	private WorkPlanDao workPlanDao;
	
//	@Autowired
//	private UtilsDao utilsDao;
	
	@Override
	public void saveWorkPlan(WorkPlan workPlan) throws Exception {
		workPlanDao.saveWorkPlan(workPlan);
	}

	@Override
	public WorkPlan getWorkPlanById(String id) throws Exception {
		return workPlanDao.getWorkPlanById(id);
	}

	@Override
	public PageView<WorkPlan> getWorkPlanListpageView(WorkPlan workPlan)
			throws Exception {
		PageView<WorkPlan> pageView = new PageView<WorkPlan>(
				workPlan.getMaxResult(),
				workPlan.getPage());// 需要设置当前页
		try {	
			
			int count = 0;// 获取列表条数
			List<WorkPlan> workPlanList = null;// 获取列表数据
			
			count = workPlanDao.getWorkPlanListCount(workPlan);
			workPlanList = workPlanDao.getWorkPlanList(workPlan);
			
			
			QueryResult<WorkPlan> qr = new QueryResult<WorkPlan>();
			qr.setResultlist(workPlanList);
			qr.setTotalrecord(count);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageView;
	}

	@Override
	public List<WorkPlan> exportWorkPlanList(WorkPlan workPlan)
			throws Exception {
		return workPlanDao.exportWorkPlanList(workPlan);
	}

    @Override
    public void updateWorkPlan(WorkPlan workPlan)throws Exception{
        workPlanDao.updateWorkPlan(workPlan);
    }

	@Override
	public void deleteWorkPlan(WorkPlan workPlan) throws Exception {
		workPlanDao.deleteWorkPlan(workPlan);
	}

}
