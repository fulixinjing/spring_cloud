package cn.taskSys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.Constants;
import cn.taskSys.dao.EmployeeDao;
import cn.taskSys.entity.Employee;
import cn.taskSys.entity.Post;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.EmployeeService;
import cn.taskSys.utils.StringUtil;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{


	@Autowired
	private EmployeeDao employeeDao;


	@LogAnnotation(eventCode="2012001",eventProcess="")
	public Employee getEmployee(String employeeCode) throws Exception {
		return employeeDao.getEmployee(employeeCode);
	}

	@LogAnnotation(eventCode="2012002",eventProcess="")
	public int saveEmployee(Employee employee) throws Exception {
		int start = 0;
		if (hasEmployee(employee.getEmployeeCode())) {
			employeeDao.updateEmployee(employee);
			//employeeHisDao.insertEmployeeHis(employee);
			start=1;
		} else {
			employee.setCreateBy(employee.getLastModifyBy());
			employeeDao.insertEmployee(employee);
			start=2;
		}
		return start;
	}

	@LogAnnotation(eventCode="2012003",eventProcess="")
	@Override
	public List<Employee> getEmployees(Object param) throws Exception {
		return employeeDao.getEmployees(param);
	}

	@LogAnnotation(eventCode="2012004",eventProcess="")
	@Override
	public int deleteEmployee(String employeeCode) throws Exception {
		Employee employee = new Employee(employeeCode, Constants.EMP_STU_OFFJOB);
		return employeeDao.updateEmployeeStatus(employee);
	}

	@LogAnnotation(eventCode="2012005",eventProcess="")
	@Override
	public int disableEmployee(String employeeCode) throws Exception {
		Employee employee = new Employee(employeeCode, Constants.EMP_STU_OFFJOB);
		return employeeDao.updateEmployeeStatus(employee);
	}

	@LogAnnotation(eventCode="2012006",eventProcess="")
	@Override
	public int enableEmployee(String employeeCode) throws Exception {
		Employee employee = new Employee(employeeCode, Constants.EMP_STU_ONJOB);
		return employeeDao.updateEmployeeStatus(employee);
	}

	@LogAnnotation(eventCode="2012007",eventProcess="")
	@Override
	public PagerModel getPageEmployee(HashMap<String, Object> qry) throws Exception {
		PagerModel pager = new PagerModel();
		pager.setDatas(getPageEmployeeData(qry));
		pager.setTotal(getPageEmployeeSize(qry));
		return pager;
	}

	/************************ 私有方法  *******************/
	private boolean hasEmployee(String employeeCode) {
		return employeeDao.getCountByEmployeeCode(employeeCode) == 0 ? false : true;
	}

	/**
	 * 通过查询条件进行分页检索
	 * @param qry
	 * @return
	 */
	private List<Employee> getPageEmployeeData(Map<String, Object> qry) throws Exception {
		return employeeDao.getPageEmployeeData(qry);
	}

	/**
	 * 通过查询条件进行分页计算
	 * @param qry
	 * @return
	 */
	private Integer getPageEmployeeSize(Map<String, Object> qry) throws Exception {
		return employeeDao.getPageEmployeeSize(qry);
	}

	/**
	 * 通过sourceRolecode查询汇金表对应角色的人员
	 * @param sourceRolecode
	 * @return
	 */
	@LogAnnotation(eventCode="2012008",eventProcess="")
	@Override
	public PagerModel getEmployeeList(HashMap<String, Object> map) {
		PagerModel pager = new PagerModel();
		pager.setDatas(employeeDao.getEmployeeList(map));
		pager.setTotal(employeeDao.getEmployeeSize(map));
		return pager;
	}

	/*
	 * 封装Employee
	 */
	public Employee makeEmployee(Map<String, Object> map){
		Employee emp = new Employee();
		Post post = new Post();
		emp.setEmployeeCode(StringUtil.nvlString(map.get("EMP_CODE")));
		emp.setEmployeeName(StringUtil.nvlString(map.get("EMP_NAME")));
		emp.setPostId(StringUtil.nvlString(map.get("POSTID")));
		emp.setOrgStore(StringUtil.nvlString(map.get("ORG_STORE")));
		emp.setOrgCompany(StringUtil.nvlString(map.get("ORG_COMPANY")));
		post.setPostName(StringUtil.nvlString(map.get("POSTNAME")));
		emp.setPost(post);
		return emp;
	}
	@LogAnnotation(eventCode="2012009",eventProcess="")
	public Employee getHjEmployee(Map<String, Object> map){
		Employee employee = employeeDao.getHjEmployee(map);
		return employee;
	}
	@LogAnnotation(eventCode="2012010",eventProcess="")
	public Employee getPrivilege(Map<String, Object> map){
		Employee employee = employeeDao.getPrivilege(map);
		return employee;
	}

	//@Override
	public List<Employee> getEmpCodes(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return employeeDao.getEmpCodes(map);
	}	
}
