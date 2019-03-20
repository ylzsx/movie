package com.oracleoaec.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.oracleoaec.dao.BaseDao;
import com.oracleoaec.dao.impl.BaseDaoImpl;
import com.oracleoaec.pojo.Hall;
import com.oracleoaec.service.HallService;

public class HallServiceImpl implements HallService{

	private BaseDao bd;
	
	@Override
	public int addHallReturnPrimaryKey(Hall hall) {
		bd = new BaseDaoImpl();
		
		String sql1 = "select * from HALL where HALL_STATUS = '1' and HALL_NAME = '"+hall.getHallName()+"'";
		sql1 += " and HALL_CID = "+hall.getHallCid();
		int i = bd.executeUpdate(sql1);
		if(i == 0) {
			String sql = "insert into HALL(HALL_ID,HALL_NAME,HALL_CID,HALL_CAPACITY,HALL_STATUS)";
			sql += " values(SEQ_HALL.Nextval,'"+hall.getHallName()+"'";
			sql += " ,"+hall.getHallCid();
			sql += " ,'"+hall.getHallCapacity()+"'";
			sql += " ,'"+hall.getHallStatus()+"')";
			//System.out.println(sql);
			String[] generatedColumns = {"HALL_ID"};
			int pk = bd.executeInsert(sql, generatedColumns);
			return pk;
		}else {
			return -1;
		}
		
	}

	@Override
	public Vector<Vector<String>> findHallAndCinema() {
		bd = new BaseDaoImpl();
		
		String sql = "select CINEMA_ID,CINEMA_NAME,HALL_ID,HALL_NAME,HALL_CAPACITY from HALL";
		sql += " inner join CINEMA on HALL.HALL_CID = CINEMA.CINEMA_ID";
		sql += " where HALL_STATUS = '1'";
		sql += " order by CINEMA_ID desc";
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		for (Map<String, Object> map : queryForList) {
			Vector<String> row = new Vector<String>();
			row.add(map.get("CINEMA_ID")+"");
			row.add(map.get("CINEMA_NAME")+"");
			row.add(map.get("HALL_ID")+"");
			row.add(map.get("HALL_NAME")+"");
			row.add(map.get("HALL_CAPACITY")+"");
			data.add(row);
		}
		return data;
	}

	@Override
	public List<Map<String,Object>> findHallByCinemaId(Integer cinemaid) {
		bd = new BaseDaoImpl();
		
		String sql = "select * from HALL where HALL_STATUS = '1' and HALL_CID = "+cinemaid;
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		return queryForList;
	}

	@Override
	public int deleteHallById(Set<Integer> hallidSet) {
		bd = new BaseDaoImpl();
		
		List<String> sqlList = new ArrayList<String>();
		for(Integer hallid : hallidSet) {
			String sql = "update Hall set HALL_STATUS = '0' where HALL_ID = "+hallid;
			sqlList.add(sql);
		}
		int i = bd.executeUpdateTrans(sqlList);
		return i;
	}

	@Override
	public int updateHallById(Hall hall) {
		bd = new BaseDaoImpl();
		
		String sql = "update HALL set HALL_NAME = '"+hall.getHallName()+"'";
		sql += " ,HALL_CAPACITY = "+hall.getHallCapacity();
		sql += " where HALL_ID = "+hall.getHallid();
		int i = bd.executeUpdate(sql);
		return i;
	}

}
