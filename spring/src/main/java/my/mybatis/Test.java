package my.mybatis;

import java.lang.reflect.Method;


public class Test {
	public static void main(String[] args) {
		/*MySqlSession sqlSession = new MySqlSession();
		TestDao testMapper = sqlSession.getMapper(TestDao.class);
        my.spring.bean.Test test = testMapper.selectByPrimaryKey(1);
		System.out.println(test);*/
		
		Method[] methods = SimpleExecutor.class.getDeclaredMethods();
		System.out.println(methods[1].getDeclaringClass());
	}
}
