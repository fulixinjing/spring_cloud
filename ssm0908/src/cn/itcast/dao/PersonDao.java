package cn.itcast.dao;

import java.util.List;
import java.util.Map;

import cn.itcast.model.Person;

public interface PersonDao {
	
	/**
	 * 保存person
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	public void savePerson(Person person);
	
	/**
	 * 修改person
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	public void updatePerson(Person person);
	
	/**
	 * 批量删除person
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	public void deleteBatch(Map<String, Object> map);
	
	/**
	 * 根据id查询person
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	public Person selectPersonById(Integer personId);
	
	/**
	 * 动态条件组合查询
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	public List<Person> selectPersonByCondition(Map<String, Object> map);

}
