package com.oracleoaec.conn;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactory {

	private static String DRIVER;
	private static String URL;
	private static String USER;
	private static String PWD;
	private static Connection conn;
	
	// 将属性文件加载进入
	static {
		Properties p = new Properties();
		//只能导入同包下的
		InputStream ism = ConnectionFactory.class.getResourceAsStream("jdbcinfo.properties");
		try {
			p.load(ism);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ism.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DRIVER = p.getProperty("driver");
		URL = p.getProperty("url");
		USER = p.getProperty("user");
		PWD = p.getProperty("pwd");
	}
	
	public static Connection getConn() {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PWD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
