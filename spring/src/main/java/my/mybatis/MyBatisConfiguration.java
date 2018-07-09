package my.mybatis;

import java.util.HashMap;
import java.util.Map;

public class MyBatisConfiguration {

	private Map<String,Map<String,String>> xml =new HashMap<>();
	
	public static final String nameSpace = "my.mybatis.TestDao";

    public static final Map<String, String> methodSqlMapping = new HashMap<String, String>();

    static {
        methodSqlMapping.put("selectByPrimaryKey", "select * from user");
    }
	
}
