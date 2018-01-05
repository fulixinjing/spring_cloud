package cn.taskSys.service;

import java.util.List;

import cn.taskSys.base.PageView;
import cn.taskSys.entity.WorkPlan;

public interface IWorkPlanService {
	
	/**
	 * 保存工作计划
	 * @param workPlan
	 * @throws Exception
	 */
	public void saveWorkPlan(WorkPlan workPlan)throws Exception;
	
	/**
	 * 根据ID查询对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WorkPlan getWorkPlanById(String id) throws Exception;
	
	/**
	 * 工作计划分页列表
	 * @param workPlan
	 * @return
	 * @throws Exception
	 */
	public PageView<WorkPlan> getWorkPlanListpageView(WorkPlan workPlan)throws Exception;
	
	/**
	 * 根据属性返回列表
	 * @param workPlan
	 * @return
	 * @throws Exception
	 */
	public List<WorkPlan> exportWorkPlanList(WorkPlan workPlan) throws Exception;
	/**
     * 
     * //TODO 修改工作计划
     * @param workPlan
     * @throws Exception
     */
    public void updateWorkPlan(WorkPlan workPlan) throws Exception;
    /**
     * 
     * //TODO 删除工作计划
     * @param workPlan
     * @throws Exception
     */
    public void deleteWorkPlan(WorkPlan workPlan) throws Exception;
}
