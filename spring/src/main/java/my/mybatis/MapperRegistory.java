package my.mybatis;

import java.util.HashMap;
import java.util.Map;

import my.spring.bean.Test;

public class MapperRegistory {
	
	public static final Map<String,MapperData> METHOD_SQL = new HashMap<String,MapperData>();
	
	public MapperRegistory(){
		METHOD_SQL.put("my.mybatis.TestDao.selectByPrimaryKey", new MapperData("select * from test", Test.class));
	}
	
}
