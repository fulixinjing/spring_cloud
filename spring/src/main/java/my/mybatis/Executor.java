package my.mybatis;

public interface Executor {

	<E> E query(String sql, Object args);

}
