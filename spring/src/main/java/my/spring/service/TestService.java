package my.spring.service;


import my.mybatis.MySqlSession;
import my.mybatis.TestDao;
import my.spring.annotation.Service;
import my.spring.bean.Test;

@Service
public class TestService {

	
	public String get() {
		MySqlSession sqlSession = new MySqlSession();
		TestDao testMapper = sqlSession.getMapper(TestDao.class);
        Test test = testMapper.selectByPrimaryKey(1);
		System.out.println(test);
		return "...............service";
	}
	
}
