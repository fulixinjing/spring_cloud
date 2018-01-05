package cn.itcast.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.PersonDao;
import cn.itcast.model.Person;
import cn.itcast.service.PersonService;
@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;
	
	@Override
	public void savePerson(Person person) {
		personDao.savePerson(person);
	}

	@Override
	public void updatePerson(Person person) {
		personDao.updatePerson(person);
	}

	@Override
	public void deleteBatch(Map<String, Object> map) {
		personDao.deleteBatch(map);
	}

	@Override
	public Person selectPersonById(Integer personId) {
		return personDao.selectPersonById(personId);
	}

	@Override
	public List<Person> selectPersonByCondition(Map<String, Object> map) {
		return personDao.selectPersonByCondition(map);
	}

}
