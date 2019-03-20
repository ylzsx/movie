package com.oracleoaec.service.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.oracleoaec.dao.BaseDao;
import com.oracleoaec.dao.impl.BaseDaoImpl;
import com.oracleoaec.pojo.Session;
import com.oracleoaec.service.SessionService;
import com.oracleoaec.util.TimeUtil;

public class SessionServiceImpl implements SessionService {

	private BaseDao bd;
	
	@Override
	public Vector<Vector<String>> findAnyMovieInfo() {
		bd = new BaseDaoImpl();
		
		String sql = "select S.SESSION_ID,CINEMA.CINEMA_NAME,HALL.HALL_NAME,MOVIE.MOVIE_NAME,S.SESSION_TIME,MOVIE.MOVIE_TYPE,S.SESSION_PRICE,S.SESSION_REMAIN from SESSIONS S";
		sql += " inner join HALL on S.SESSION_HID = HALL.HALL_ID";
		sql += " inner join CINEMA on S.SESSION_CID = CINEMA.CINEMA_ID";
		sql += " inner join MOVIE on S.SESSION_MID = MOVIE.MOVIE_ID";
		sql += " where S.SESSION_STATUS = '1' and S.SESSION_TIME >= '"+TimeUtil.getCurrentTime()+"'";
		sql += " order by SESSION_ID desc";
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		for (Map<String, Object> map : queryForList) {
			Vector<String> row = new Vector<String>();
			row.add(map.get("SESSION_ID")+"");
			row.add(map.get("CINEMA_NAME")+"");
			row.add(map.get("HALL_NAME")+"");
			row.add(map.get("MOVIE_NAME")+"");
			row.add(map.get("SESSION_TIME")+"");
			row.add(map.get("MOVIE_TYPE")+"");
			row.add(map.get("SESSION_PRICE")+"");
			row.add(map.get("SESSION_REMAIN")+"");
			data.add(row);
		}
		return data;
	}

	@Override
	public List<Map<String, Object>> findAnyMovieInfo(Integer sessionid) {
		bd = new BaseDaoImpl();
		
		String sql = "select CINEMA.CINEMA_NAME,HALL.HALL_NAME,MOVIE.MOVIE_NAME,S.SESSION_TIME,S.SESSION_PRICE,S.SESSION_REMAIN,HALL.HALL_CAPACITY from SESSIONS S";
		sql += " inner join HALL on S.SESSION_HID = HALL.HALL_ID";
		sql += " inner join CINEMA on S.SESSION_CID = CINEMA.CINEMA_ID";
		sql += " inner join MOVIE on S.SESSION_MID = MOVIE.MOVIE_ID";
		sql += " where S.SESSION_STATUS = '1' and S.SESSION_TIME >= '"+TimeUtil.getCurrentTime()+"'";
		sql += " and S.SESSION_ID = "+sessionid;
		//System.out.println(sql);
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		return queryForList;
	}
	
	@Override
	public int addSession(Session session) {
		bd = new BaseDaoImpl();
		
		String sql = "insert into SESSIONS(SESSION_ID,SESSION_HID,SESSION_CID,SESSION_MID,SESSION_TIME,SESSION_ENDTIME,SESSION_PRICE,SESSION_REMAIN,SESSION_STATUS)";
		sql += "values(SEQ_SESSIONS.NEXTVAL,"+session.getSessionHid();
		sql += " ,"+session.getSessionCid();
		sql += " ,"+session.getSessionMid();
		sql += " ,'"+session.getSessionTime()+"'";
		sql += " ,'"+session.getSessionEndTime()+"'";
		sql += " ,"+session.getSessionPrice();
		sql += " ,"+session.getSessionRemain();
		sql += " ,'"+session.getSessionStatus()+"')";
		//System.out.println(sql);
		String[] generatedColumns = {"SESSION_ID"};
		int pk = bd.executeInsert(sql, generatedColumns);
		return pk;
	}
	
	@Override
	public Set<Integer> whetherHasSession(Set<Integer> hallidSet,Integer cinemaid) {
		bd = new BaseDaoImpl();
		
		Set<Integer> hasSessionHallidSet = new HashSet<Integer>();
		for (Integer hallid : hallidSet) {
			String sql = "select SESSION_ID,SESSION_TIME,SESSION_ENDTIME from SESSIONS where SESSION_STATUS = '1' and SESSION_CID = "+cinemaid;
			sql += " and SESSION_HID = "+hallid;
			//sql += " and SESSION_TIME <= '"+TimeUtil.getCurrentTime()+"'";
			sql += " order by SESSION_ID desc";
			List<Map<String, Object>> queryForList = bd.queryForList(sql);
			for(Map<String, Object> map : queryForList) {
				if(TimeUtil.compareTime(TimeUtil.getCurrentTime(),map.get("SESSION_TIME").toString()) ||
					TimeUtil.compareTime(TimeUtil.getCurrentTime(),map.get("SESSION_ENDTIME").toString())) {
					hasSessionHallidSet.add(hallid);
					break;
				}
			}
			//if(queryForList.size() != 0) hasSessionHallidSet.add(hallid);
		}
		return hasSessionHallidSet;
	}

	@Override
	public boolean isPlatoo(Integer hid,String startTime) {
		bd = new BaseDaoImpl();
		
		String sql = "select SESSION_ENDTIME from SESSIONS where SESSION_STATUS = '1' and SESSION_HID = "+hid;
		sql += " order by SESSION_ID desc";
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		if(queryForList.size() == 0) {
			return true;
		}else {
			String originTime = queryForList.get(0).get("SESSION_ENDTIME").toString();
			if(TimeUtil.compareTime(originTime, startTime)) {
				return true;
			}else {
				return false;
			}
		}
		
		/*String sql = "select SESSION_ID from SESSIONS where SESSION_HID = "+hid+" and SESSION_TIME <= '"+startTime+"' and '"+startTime+"' <= SESSION_ENDTIME";
		//System.out.println(sql);
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		if(queryForList.size() == 0) {
			String sql1 = "select SESSION_ID from SESSIONS where SESSION_HID = "+hid+" and SESSION_TIME <= '"+endTime+"' and '"+endTime+"' <= SESSION_ENDTIME";
			//System.out.println(sql1);
			List<Map<String, Object>> queryForList2 = bd.queryForList(sql1);
			if(queryForList2.size() == 0) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}*/
	}

	
	@Override
	public Vector<Vector<String>> findSessionByHallId(Integer hallid) {
		bd = new BaseDaoImpl();
		
		String sql = "select s.SESSION_ID,s.SESSION_TIME,s.SESSION_PRICE,s.Session_REMAIN,m.MOVIE_NAME,m.MOVIE_DURATION from SESSIONS s";
		sql += " inner join MOVIE m on s.SESSION_MID = m.MOVIE_ID";
		sql += " where s.SESSION_STATUS = '1' and s.SESSION_HID = "+hallid;
		sql += " and SESSION_TIME >= '"+TimeUtil.getCurrentTime()+"'";
		sql += " order by SESSION_ID desc";
		//System.out.println(sql);
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		for (Map<String, Object> map : queryForList) {
			Vector<String> row = new Vector<String>();
			row.add(map.get("SESSION_ID")+"");
			row.add(map.get("MOVIE_NAME")+"");
			row.add(map.get("SESSION_TIME")+"");
			row.add(map.get("SESSION_PRICE")+"");
			row.add(map.get("SESSION_REMAIN")+"");
			row.add(map.get("MOVIE_DURATION")+"");
			data.add(row);
		}
		return data;
	}

	
	@Override
	public int deleteSessionById(Integer sessionid) {
		bd = new BaseDaoImpl();
		
		//查询是否卖出了票
		String sql0 = "select TICKET_ID from TICKET where TICKET_STATUS = '1' and TICKET_SID = "+sessionid;
		List<Map<String, Object>> queryForList = bd.queryForList(sql0);
		if(queryForList.size() == 0) {
			String sql = "update SESSIONS set SESSION_STATUS = '0' where SESSION_ID = "+sessionid;
			int i = bd.executeUpdate(sql);
			return i;
		}else {
			return -1;
		}
	}

	
	@Override
	public int updateSessionById(Session session) {
		bd = new BaseDaoImpl();
		
		String sql = "update SESSIONS set SESSION_TIME = '"+session.getSessionTime()+"'";
		sql += " ,SESSION_ENDTIME = '"+session.getSessionEndTime()+"'";
		sql += " ,SESSION_PRICE = "+session.getSessionPrice();
		sql += " where SESSION_ID = "+session.getSessionid();
		int i = bd.executeUpdate(sql);
		return i;
	}

	@Override
	public List<Map<String, Object>> findSessionByMovieId(Integer movieid) {
		bd = new BaseDaoImpl();
		
		String sql = "select SESSION_ID from SESSIONS where SESSION_STATUS = '1'";
		sql += " and SESSION_ENDTIME >= '"+TimeUtil.getCurrentTime()+"'";
		sql += " and SESSION_MID = "+movieid;
		//System.out.println(sql);
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		return queryForList;
	}

	

}
