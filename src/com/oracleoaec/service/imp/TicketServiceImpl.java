package com.oracleoaec.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.oracleoaec.dao.BaseDao;
import com.oracleoaec.dao.impl.BaseDaoImpl;
import com.oracleoaec.service.TicketService;
import com.oracleoaec.util.TimeUtil;

public class TicketServiceImpl implements TicketService {

	private BaseDao bd;
	
	@Override
	public int shoppingTicket(Integer userid, Integer sessionid, Double price, Set<String> ticketSet) {
		bd = new BaseDaoImpl();
		
		//判断余额是否足够买票
		double total = price*ticketSet.size();
		String sql = "select USER_BALANCE from USERS where USER_STATUS = '1' and USER_ID = "+userid;
		List<Map<String, Object>> balanceList = bd.queryForList(sql);
		Double balance = new Double(balanceList.get(0).get("USER_BALANCE").toString());
		if(balance >= total) {
			//买票
			List<String> sqlList = new ArrayList<String>();
			for (String ticketSeat : ticketSet) {
				String sql1 = "insert into TICKET(TICKET_ID,TICKET_UID,TICKET_SID,TICKET_SEAT,TICKET_STATUS)";
				sql1 += " values(SEQ_TICKET.nextval,"+userid;
				sql1 += " ,"+sessionid;
				sql1 += " ,"+ticketSeat;
				sql1 += " ,'1')";
				//System.out.println(sql1);
				sqlList.add(sql1);
			}
			
			//扣钱
			String sql2 = "update USERS set USER_BALANCE = "+(balance - total);
			sql2 += " where USER_ID = "+userid;
			sqlList.add(sql2);
			
			//减少余座数
			String sql3 = "update SESSIONS set SESSION_REMAIN = (SESSION_REMAIN-"+ticketSet.size()+")";
			sql3 += " where SESSION_ID = "+sessionid;
			//System.out.println(sql3);
			sqlList.add(sql3);
			int i = bd.executeUpdateTrans(sqlList);
			return i;
		}else {
			return -1;
		}
	}

	@Override
	public Set<String> findSetBySessionId(Integer sessionid) {
		bd = new BaseDaoImpl();
		
		String sql = "select TICKET_SEAT from TICKET";
		sql += " where TICKET_STATUS = '1' and TICKET_SID = "+sessionid;
		//System.out.println(sql);
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		
		
		Set<String> seatSet = new HashSet<String>();
		for (Map<String, Object> map : queryForList) {
			seatSet.add(map.get("TICKET_SEAT")+"");
		}
		return seatSet;
	}

	@Override
	public Vector<Vector<String>> findTicketByUserId(Integer userid) {
		bd = new BaseDaoImpl();
		
		String sql = "select t.TICKET_ID,CINEMA.CINEMA_NAME,HALL.HALL_NAME,MOVIE.MOVIE_NAME,s.SESSION_TIME,s.SESSION_PRICE,s.SESSION_REMAIN,t.TICKET_SEAT,s.SESSION_ID from TICKET t";
		sql += " inner join SESSIONS s on t.ticket_sid = s.SESSION_ID";
		sql += " inner join CINEMA on s.SESSION_CID = CINEMA.CINEMA_ID";
		sql += " inner join HALL on s.SESSION_HID = HALL.HALL_ID";
		sql += " inner join MOVIE on s.SESSION_MID = MOVIE.MOVIE_ID";
		sql += " where TICKET_STATUS = '1' and TICKET_UID = "+userid;
		sql += " and s.SESSION_TIME >= '"+TimeUtil.getCurrentTime()+"'";
		sql += " order by t.TICKET_ID desc";
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		for (Map<String, Object> map : queryForList) {
			Vector<String> row = new Vector<String>();
			row.add(map.get("TICKET_ID")+"");
			row.add(map.get("CINEMA_NAME")+"");
			row.add(map.get("HALL_NAME")+"");
			row.add(map.get("MOVIE_NAME")+"");
			row.add(map.get("SESSION_TIME")+"");
			row.add(map.get("SESSION_PRICE")+"");
			row.add(map.get("SESSION_REMAIN")+"");
			row.add(map.get("TICKET_SEAT")+"");
			row.add(map.get("SESSION_ID")+"");
			data.add(row);
		}
		return data;
	}

	@Override
	public int returnTicket(Integer userid, Integer sessionid, Integer ticketid, Double price) {
		bd = new BaseDaoImpl();
		//先根据sessionid去查询该电影是否失效
		String sql0 = "select SESSION_TIME from SESSIONS where SESSION_STATUS = '1' and SESSION_ID = "+sessionid;
		List<Map<String, Object>> queryForList = bd.queryForList(sql0);
		String startTime = queryForList.get(0).get("SESSION_TIME").toString();
		if(TimeUtil.compareTime(startTime, TimeUtil.getCurrentTime())) {
			return -1;
		}else {
			List<String> sqlList = new ArrayList<String>();
			//修改座位数
			String sql = "update SESSIONS set SESSION_REMAIN = (SESSION_REMAIN+1) where SESSION_ID = "+sessionid;
			sqlList.add(sql);
			//退钱
			String sql1 = "update USERS set USER_BALANCE = (USER_BALANCE+"+price+") where USER_ID = "+userid;
			sqlList.add(sql1);
			//取消ticket
			String sql2 = "update TICKET set TICKET_STATUS = '0' where TICKET_ID = "+ticketid;
			sqlList.add(sql2);
			int i = bd.executeUpdateTrans(sqlList);
			return i;
		}
	}

}
