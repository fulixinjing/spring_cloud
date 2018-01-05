package cn.itcast.service;

import java.util.List;
import java.util.Map;

import cn.itcast.model.Person;

public interface PersonService {


	/**
	 * ����person
	 * @author:����
	 * @email:renliang@itcast.com
	 * @company��cn.itcast
	 */
	public void savePerson(Person person);
	
	/**
	 * �޸�person
	 * @author:����
	 * @email:renliang@itcast.com
	 * @company��cn.itcast
	 */
	public void updatePerson(Person person);
	
	/**
	 * ����ɾ��person
	 * @author:����
	 * @email:renliang@itcast.com
	 * @company��cn.itcast
	 */
	public void deleteBatch(Map<String, Object> map);
	
	/**
	 * ����id��ѯperson
	 * @author:����
	 * @email:renliang@itcast.com
	 * @company��cn.itcast
	 */
	public Person selectPersonById(Integer personId);
	
	/**
	 * ��̬������ϲ�ѯ
	 * @author:����
	 * @email:renliang@itcast.com
	 * @company��cn.itcast
	 */
	public List<Person> selectPersonByCondition(Map<String, Object> map);
}
