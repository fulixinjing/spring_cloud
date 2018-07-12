package my.spring.service;


import my.mybatis.TestDao;
import my.spring.annotation.Autowired;
import my.spring.annotation.Service;
import my.spring.bean.Test;

@Service
public class TestService {
	
	@Autowired
	private TestDao testDao;
	
	public String get() {
	
        Test test = testDao.selectByPrimaryKey(1);
		System.out.println(test);
		return "...............service";
	}
	
}
