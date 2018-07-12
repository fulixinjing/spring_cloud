package my.mybatis;

public interface Executor {

	<E> E query(MapperData mapperData, Object args);

}
