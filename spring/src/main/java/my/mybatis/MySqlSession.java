package my.mybatis;

import java.lang.reflect.Proxy;

public class MySqlSession {
	
	private Executor executor = new SimpleExecutor();

	public <T> T getMapper(Class<T> cls) {
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[] {cls},new MapperProxy<>(this, cls));
	}

	public <T> T selectOne(MapperData mapperData, Object args) {
		return executor.query(mapperData,args);
	}

}
