package my.mybatis;

public class MapperData{
	
	private String sql;
	private Class resultType;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Class getResultType() {
		return resultType;
	}
	public void setResultType(Class resultType) {
		this.resultType = resultType;
	}
	public MapperData(String sql, Class resultType) {
		super();
		this.sql = sql;
		this.resultType = resultType;
	}
	
	
}
