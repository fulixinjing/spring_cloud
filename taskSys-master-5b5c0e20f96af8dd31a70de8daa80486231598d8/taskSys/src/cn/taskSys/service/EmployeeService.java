package cn.taskSys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Employee;
import cn.taskSys.pager.PagerModel;

public interface EmployeeService {

	Employee getEmployee(String employeeCode) throws Exception;

	List<Employee> getEmployees(Object param) throws Exception;

	int saveEmployee(Employee employee) throws Exception;

	int deleteEmployee(String employeeCode) throws Exception;

	int disableEmployee(String employeeCode) throws Exception;

	int enableEmployee(String employeeCode) throws Exception;

	PagerModel getPageEmployee(HashMap<String, Object> qry) throws Exception;

	PagerModel getEmployeeList(HashMap<String, Object> map);

	Employee makeEmployee(Map<String, Object> map);
	
	public Employee getHjEmployee(Map<String, Object> map);
	
	public Employee getPrivilege(Map<String, Object> map);

	List<Employee> getEmpCodes(Map<String,Object> map);

}
