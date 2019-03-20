package com.oracleoaec.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.oracleoaec.pojo.Hall;

public interface HallService {

	/**
	 * 添加影厅
	 * @param hall
	 * @return  返回添加影院的主键
	 */
	public int addHallReturnPrimaryKey(Hall hall);
	
	/**
	 * 查找影厅信息
	 * @return
	 */
	public Vector<Vector<String>> findHallAndCinema();
	
	/**
	 * 根据影院id查询该影院下所有影厅
	 * @param cinemaid
	 * @return	返回放了Map的List数组
	 */
	public List<Map<String,Object>> findHallByCinemaId(Integer cinemaid);
	
	/**
	 * 根据影厅id去删除影厅
	 * @param hallidSet
	 * @return	返回执行成功的条数
	 */
	public int deleteHallById(Set<Integer> hallidSet);
	
	/**
	 * 修改影厅信息
	 * @param hall
	 * @return	返回执行成功的条数
	 */
	public int updateHallById(Hall hall);
}
