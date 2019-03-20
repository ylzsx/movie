package com.oracleoaec.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {

	/**
	 * 数据库增删改
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String sql);
	
	/**
	 * 数据库查询
	 * @param sql
	 * @param columnName	查询的列名
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String sql,List<String> columnNames);
	
	/**
	 * 数据库查询，自动取值
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String sql);
	
	/**
	 * 批量执行增删改
	 * @param sqlList
	 * @return
	 */
	public int executeUpdateTrans(List<String> sqlList);
	
	/**
	 * 添加数据，并返回主键（oracle专用）
	 * @param sql
	 * @param generatedColumns	主键的列名放于数组
	 * @return	添加成功返回主键，添加失败返回0
	 */
	public int executeInsert(String sql,String[] generatedColumns);
	
	/**
	 * 添加     数据，并返回主键（mySql专用）
	 * @param sql
	 * @return	添加成功返回主键，添加失败返回0
	 */
	public int executeInsert(String sql);
}
