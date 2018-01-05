package cn.taskSys.dao;

import java.util.List;

import cn.taskSys.entity.WorkPlan;

public interface WorkPlanDao {
	
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
	public List<WorkPlan> getWorkPlanList(WorkPlan workPlan) throws Exception;
	
	/**
	 * 分页总条数
	 * @param workPlan
	 * @return
	 * @throws Exception
	 */
	public int getWorkPlanListCount(WorkPlan workPlan) throws Exception;
	
	/**
	 * 根据属性返回列表
	 * @param workPlan
	 * @return
	 * @throws Exception
	 */
	public List<WorkPlan> exportWorkPlanList(WorkPlan workPlan) throws Exception;//导出列表
	
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
