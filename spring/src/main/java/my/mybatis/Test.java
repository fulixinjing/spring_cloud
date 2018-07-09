package my.mybatis;


public class Test {
	public static void main(String[] args) {
		MySqlSession sqlSession = new MySqlSession();
		TestDao testMapper = sqlSession.getMapper(TestDao.class);
        my.spring.bean.Test test = testMapper.selectByPrimaryKey(1);
		System.out.println(test);
	}
}
