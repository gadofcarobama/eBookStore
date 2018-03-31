package utils;

import java.sql.Connection;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class MyJDBCUtils {
	
	public static ComboPooledDataSource ds = new ComboPooledDataSource();
	//得到数据库的连接的方法
	public static Connection getConnection1() throws Exception {
		//得到连接
		Connection conn = ds.getConnection();
		return conn;
	}
	
	//返回DataSource
	public static DataSource getDataSource() {
		return ds;
	}
}



