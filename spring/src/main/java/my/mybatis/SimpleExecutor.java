package my.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import my.spring.bean.Test;

public class SimpleExecutor implements Executor {

	@Override
	public <E> E query(MapperData mapperData, Object args) {
		
		StatementHandler statementHandler = new StatementHandler();
		return statementHandler.query(mapperData,args);
	}

}
