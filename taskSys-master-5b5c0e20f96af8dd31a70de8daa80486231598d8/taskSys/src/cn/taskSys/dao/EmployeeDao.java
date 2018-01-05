package cn.taskSys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Employee;

public interface EmployeeDao {

	Employee getEmployee(String employeeCode);

	List<Employee> getEmployees(Object param);

	int insertEmployee(Employee employee);

	int updateEmployee(Employee employee);

	int updateEmployeeStatus(Employee employee);

	int getCountByEmployeeCode(String employeeCode);

	/**
	 * 通过查询条件进行分页检索
	 * @param qry
	 * @return
	 */
	List<Employee> getPageEmployeeData(Map<String, Object> qry);

	/**
	 * 通过查询条件进行分页计算
	 * @param qry
	 * @return
	 */
	Integer getPageEmployeeSize(Map<String, Object> qry);

	/**
	 * 通过sourceRolecode查询汇金表对应角色的人员
	 * @param sourceRolecode
	 * @return
	 */
	List<Employee> getEmployeeList(HashMap<String, Object> map);

	int getEmployeeSize(HashMap<String, Object> map);
	
	public Employee getHjEmployee(Map<String, Object> map);
	
	public Employee getPrivilege(Map<String, Object> map);

	List<Employee> getEmpCodes(Map<String,Object> map);

}
