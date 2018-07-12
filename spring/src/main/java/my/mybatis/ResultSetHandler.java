package my.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 数据库结果映射 bean
 * @author flx
 *
 */
public class ResultSetHandler {

	public <E> E handle(PreparedStatement prepareStatement, MapperData mapperData) throws Exception {
		ResultSet rs = prepareStatement.getResultSet();
		Object newInstance = mapperData.getResultType().newInstance();
		 if(rs.next()){
			 Field[] fields = newInstance.getClass().getDeclaredFields();
			 for (Field field : fields) {
				 Method method = newInstance.getClass().getMethod("set"+upperCapital(field.getName()), field.getType());
				 try{
					 method.invoke(newInstance, rs.getObject(field.getName()));
				 }catch(SQLException e){
					 System.out.println(field.getName()+"不存在");
				 }
			 }
		 }
		
		return (E) newInstance;
	}

	private String upperCapital(String name) {
		String first = name.substring(0, 1);
        String tail = name.substring(1);
        return first.toUpperCase() + tail;
	}

}
