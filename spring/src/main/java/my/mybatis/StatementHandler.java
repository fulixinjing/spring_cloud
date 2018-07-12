package my.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author flx
 * 数据库连接
 */
public class StatementHandler {

	public <E> E query(MapperData mapperData, Object args) {
		try{
			Connection connection=getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(mapperData.getSql());
			prepareStatement.execute();
			ResultSetHandler resultSetHandler = new ResultSetHandler();
			
			return (E) resultSetHandler.handle(prepareStatement,mapperData);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	private Connection getConnection() {
		String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true";
        String username = "root";
        String password = "123456";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
	}	
		

	
}
