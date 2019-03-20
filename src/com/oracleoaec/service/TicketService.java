package com.oracleoaec.service;

import java.util.Set;
import java.util.Vector;

public interface TicketService {

	/**
	 * 购票
	 * @param userid
	 * @param sessionid
	 * @param ticketSet
	 * @param price 购票的单价
	 * @return	返回执行成功的条数,若余额不足返回-1
	 */
	public int shoppingTicket(Integer userid,Integer sessionid,Double price,Set<String> ticketSet);
	
	/**
	 * 退票
	 * @param userid
	 * @param sessionid
	 * @param ticketid
	 * @param price
	 * @return	返回执行成功的条数
	 */
	public int returnTicket(Integer userid,Integer sessionid,Integer ticketid,Double price);
	
	/**
	 * 根据场次id查询售出的座位
	 * @param sessionid
	 * @return
	 */
	public Set<String> findSetBySessionId(Integer sessionid);
	
	/**
	 * 通过userid来查找购票情况
	 * @param userid
	 * @return
	 */
	public Vector<Vector<String>> findTicketByUserId(Integer userid);
}
