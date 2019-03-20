package com.oracleoaec.service;

import java.util.Vector;

import com.oracleoaec.pojo.Cinema;

public interface CinemaService {

	/**
	 * 添加影院
	 * @param cinema
	 * @return	返回添加的影院的主键
	 */
	public int addCinemaReturnPrimaryKey(Cinema cinema);
	
	/**
	 * 查询影院
	 * @param ciname
	 * @return
	 */
	public Vector<Vector<String>> findCinema(Cinema cinema);
	
	/**
	 * 根据id删除影院
	 * @param cinemaid
	 * @return  返回删除执行成功的条数
	 */
	public int deleteCinemaById(Integer cinemaid);
	
	/**
	 * 更新影院
	 * @param cinema	传入要更新的影院信息
	 * @return	返回执行成功的条数
	 */
	public int updateCinemaById(Cinema cinema);
}
