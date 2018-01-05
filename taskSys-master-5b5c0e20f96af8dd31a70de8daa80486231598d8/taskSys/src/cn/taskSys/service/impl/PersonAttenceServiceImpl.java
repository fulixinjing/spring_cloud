package cn.taskSys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.PersonAttenceDao;
import cn.taskSys.entity.PersonAttence;
import cn.taskSys.service.IPersonAttenceService;

@Service("personAttenceService")
public class PersonAttenceServiceImpl implements  IPersonAttenceService{
	
	@Autowired
	private PersonAttenceDao personAttenceDao;

	@Override
	public PersonAttence getPersonAttence(Map<String, Object> map) {
		return personAttenceDao.getPersonAttence(map);
	}


	@Override
	public void savePersonAttence(Map<String, Object> map) {
		personAttenceDao.savePersonAttence(map);
	}


	public void updatePersonAttence(Map<String, Object> map) {
		personAttenceDao.updatePersonAttence(map);
	}


	@Override
	public void updatePersonAttenceRemark(Map<String, Object> map) {
		personAttenceDao.updatePersonAttenceRemark(map);
	}
	
	/**
	 * 个人中心列表查询
	 */
	@Override
	public PageView<PersonAttence> personAttenceListpageView(PersonAttence personAttence) throws Exception {
		PageView<PersonAttence> pageView = new PageView<PersonAttence>(personAttence.getMaxResult(),personAttence.getPage());// 需要设置当前页
		try {	
			int count = personAttenceDao.personAttenceCount(personAttence);// 获取列表条数
			List<PersonAttence> personAttenceList = personAttenceDao.personAttenceList(personAttence);// 获取列表数据
			
			QueryResult<PersonAttence> qr = new QueryResult<PersonAttence>();
			qr.setResultlist(personAttenceList);
			qr.setTotalrecord(count);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageView;
	}

	//导出列表实现方法
	@Override
	public List<PersonAttence> personAttenceList(PersonAttence personAttence) {
		return personAttenceDao.exportPersonAttenceList(personAttence);
	}
}
