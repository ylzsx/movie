package com.oracleoaec.service;

import java.util.Vector;

import com.oracleoaec.pojo.Movie;

public interface MovieService {

	/**
	 * 添加电影
	 * @param movie
	 * @return  返回添加成功的主键
	 */
	public int addMovie(Movie movie);
	
	/**
	 * 查找电影
	 * @param movie
	 * @return	返回一个Vector数组，顺序如下：电影编号、电影名称、电影时长、电影类型、电影详情
	 */
	public Vector<Vector<String>> findMovieInfo(Movie movie);
	
	/**
	 * 更新movie信息
	 * @param movie
	 * @return	返回执行成功的条数
	 */
	public int updateMovieById(Movie movie);
	
	/**
	 * 根据id删除电影
	 * @param movieid
	 * @return 返回执行成功的条数
	 */
	public int deleteMovieById(Integer movieid);
}
