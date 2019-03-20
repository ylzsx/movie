package com.oracleoaec.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracleoaec.conn.ConnectionFactory;
import com.oracleoaec.dao.BaseDao;

public class BaseDaoImpl implements BaseDao{

	private Statement statement;
	private Connection conn;
	private ResultSet rs;
	
	private void closeAny() {
		try {
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int executeUpdate(String sql) {
		int i = 0;
		conn = ConnectionFactory.getConn();
		try {
			statement = conn.createStatement();
			
			i = statement.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAny();
		}
		return i;
	}

	//支持重命名的列名查询
	@Override
	public List<Map<String, Object>> queryForList(String sql, List<String> columnNames) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		conn = ConnectionFactory.getConn();
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			while(rs.next()) {
				
				Map<String,Object> rowMap = new HashMap<String,Object>();
				for (String key : columnNames) {
					Object value = rs.getObject(key);
					rowMap.put(key, value);
				}
				list.add(rowMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAny();
		}
		return list;
	}
	
	//不支持有重命名的列名查询
	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		conn = ConnectionFactory.getConn();
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			while(rs.next()) {
				Map<String,Object> rowMap = new HashMap<String,Object>();
				
				/**自己提取列名*/
				ResultSetMetaData metaData = rs.getMetaData();
				//列的个数
				int rowCount = metaData.getColumnCount();
				for(int i = 1;i <= rowCount;i++) {
					//获取列名
					String columnName = metaData.getColumnName(i);
					Object value = rs.getObject(columnName);
					rowMap.put(columnName, value);
				}
				
				list.add(rowMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAny();
		}
		return list;
	}

	@Override
	public int executeUpdateTrans(List<String> sqlList) {
		
		int i = 0;
		conn = ConnectionFactory.getConn();
		try {
			//取消自动commit
			conn.setAutoCommit(false);
			statement = conn.createStatement();
			for (String sql : sqlList) {
				i += statement.executeUpdate(sql);
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			closeAny();
		}
		return i;
	}

	
	@Override
	public int executeInsert(String sql,String[] generatedColumns) {
		int primaryKey = 0;
		conn = ConnectionFactory.getConn();

		try {
			statement = conn.createStatement();
			int i = statement.executeUpdate(sql, generatedColumns);
			//执行成功,即   i > 0   才会去获取主键
			if(i > 0) {
				rs = statement.getGeneratedKeys();
				if(rs.next()) {
					primaryKey = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeAny();
		}
		return primaryKey;
	}

	@Override
	public int executeInsert(String sql) {
		int primaryKey = 0;
		conn = ConnectionFactory.getConn();

		try {
			statement = conn.createStatement();
			int i = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			//执行成功,即   i > 0   才会去获取主键
			if(i > 0) {
				rs = statement.getGeneratedKeys();
				if(rs.next()) {
					primaryKey = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeAny();
		}
		return primaryKey;
	}
	
	

}
