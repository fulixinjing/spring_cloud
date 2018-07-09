package my.mybatis;

import java.lang.reflect.Proxy;

public class MySqlSession {
	
	private Executor executor = new SimpleExecutor();

	public <T> T getMapper(Class<T> cls) {
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[] {cls},new MapperProxy<>(this, cls));
	}

	public <T> T selectOne(String sql, Object args) {
		return executor.query(sql,args);
	}

}
