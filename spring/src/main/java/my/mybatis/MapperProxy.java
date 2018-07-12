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
		System.out.println(method.getDeclaringClass().getName()+"."+method.getName());
		MapperRegistory mrRegistory=new MapperRegistory();
		MapperData mapperData = mrRegistory.METHOD_SQL.get(method.getDeclaringClass().getName()+"."+method.getName());
		if(mapperData !=null){
			System.out.println(String.format("SQL [%s],parameter [%s]", mapperData.getSql(),args[0]));
			Object selectOne = sqlSession.selectOne(mapperData, args);
			return selectOne;
		}
		
		return null;
	}

}
