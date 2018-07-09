package my.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import my.spring.bean.Test;

public class SimpleExecutor implements Executor {

	@Override
	public <E> E query(String sql, Object args) {
		
		Connection connection=getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			Test test =new Test();
			while(rs.next()) {
				test.setName(rs.getString(1));
				test.setSex(rs.getString(2));
			}
			return (E) test;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private Connection getConnection() {
		String driver = "com.mysql.jdbc.Driver";
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
