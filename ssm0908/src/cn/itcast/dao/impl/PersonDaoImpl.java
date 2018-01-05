package cn.itcast.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.PersonDao;
import cn.itcast.model.Person;
@Repository
public class PersonDaoImpl extends SqlSessionDaoSupport implements PersonDao {
	//SqlSessionDaoSupport是mybatis对dao的支持
	String ns = "cn.itcast.model.Person.";
	@Override
	public void savePerson(Person person) {
		this.getSqlSession().insert(ns+"insert", person);
	}

	@Override
	public void updatePerson(Person person) {
		this.getSqlSession().update(ns+"dynamicUpdate", person);
	}

	@Override
	public void deleteBatch(Map<String, Object> map) {
		this.getSqlSession().delete(ns+"deleteBatch", map);
	}

	@Override
	public Person selectPersonById(Integer personId) {
		return (Person) this.getSqlSession().selectOne(ns+"selectPersonById1",personId);
	}

	@Override
	public List<Person> selectPersonByCondition(Map<String, Object> map) {
		return this.getSqlSession().selectList(ns+"selectPersonByCondition", map);
	}

}
