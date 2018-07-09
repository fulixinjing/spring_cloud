package my.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy<T> implements InvocationHandler{
	
	private MySqlSession sqlSession;
	
	private Class<T> clazz;
	
	public MapperProxy(MySqlSession sqlSession, Class<T> clazz) {
		this.sqlSession = sqlSession;
		this.clazz = clazz;
	}



	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(clazz.getName().equals(MyBatisConfiguration.nameSpace)) {
			String sql = MyBatisConfiguration.methodSqlMapping.get(method.getName());
			System.out.println(sql);
			return sqlSession.selectOne(sql,args);
		}
		return null;
	}

}
