package com.oracleoaec.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.oracleoaec.pojo.Session;

public interface SessionService {

	/**
	 * 返回电影信息
	 * @return
	 */
	public Vector<Vector<String>> findAnyMovieInfo(); 
	
	/**
	 * 根据sessionid查找电影信息
	 * @param sessionid
	 * @return 返回电影信息
	 */
	public List<Map<String,Object>> findAnyMovieInfo(Integer sessionid);
	
	/**
	 * 根据movieid查找场次
	 * @param movieid
	 * @return 返回List<Map<String,Object>>里边放有sessionid
	 */
	public List<Map<String,Object>> findSessionByMovieId(Integer movieid);
	
 	/**
	 * 通过hallid查找Session信息
	 * @param hallid
	 * @return	
	 */
	public Vector<Vector<String>> findSessionByHallId(Integer hallid);
	
	/**
	 * 添加场次
	 * @param session
	 * @return	返回添加的主键
	 */
	public int addSession(Session session);
	
	/**
	 * 根据影厅id判断其中是否有正在上映或已经上映的场次
	 * @param hallidSet
	 * @param cinemaid
	 * @return	返回有场次的影厅id的Set集合
	 */
	public Set<Integer> whetherHasSession(Set<Integer> hallidSet,Integer cinemaid);
	
	/**
	 * 判断该时间段是否可以排片
	 * @param startTime	电影开始时间
	 * @return	返回是否可以排片的布尔值
	 */
	public boolean isPlatoo(Integer hid,String startTime);
	
	/**
	 * 根据id删除场次
	 * @param sessionid
	 * @return	返回执行成功的条数,若票已卖出则返回-1
	 */
	public int deleteSessionById(Integer sessionid);
	
	/**
	 * 根据id更新场次信息
	 * @param session
	 * @return	返回执行成功的条数
	 */
	public int updateSessionById(Session session);
}
