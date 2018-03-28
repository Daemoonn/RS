package com.example.rs.util;

import com.example.rs.config.ApplicationConfig;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {

	public static Connection getJDBCConnection() {

		Connection conn = null;
		try {
			conn = getMysqlDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static MysqlDataSource getMysqlDataSource() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		MysqlDataSource mysqlDataSource = (MysqlDataSource)context.getBean("dataSource");
		return mysqlDataSource;
	}

}