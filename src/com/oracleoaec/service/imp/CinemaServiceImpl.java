package com.oracleoaec.service.imp;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.oracleoaec.dao.BaseDao;
import com.oracleoaec.dao.impl.BaseDaoImpl;
import com.oracleoaec.pojo.Cinema;
import com.oracleoaec.service.CinemaService;

public class CinemaServiceImpl implements CinemaService {

	private BaseDao bd;
	
	@Override
	public int addCinemaReturnPrimaryKey(Cinema cinema) {
		bd = new BaseDaoImpl();
		
		String sql1 = "select * from CINEMA where CINEMA_STATUS = '1' and CINEMA_NAME = '"+cinema.getCinemaName()+"'";
		int i = bd.executeUpdate(sql1);
		if(i == 0) {
			String sql = "insert into CINEMA(CINEMA_ID,CINEMA_NAME,CINEMA_ADDRESS,CINEMA_STATUS)";
			sql += "values(SEQ_CINEMA.nextval,'"+cinema.getCinemaName()+"'";
			sql += ",'"+cinema.getCinemaAddress()+"'";
			sql += ",'"+cinema.getCinemaStatus()+"')";
			String[] generatedColumns = {"CINEMA_ID"};
			
			int pk = bd.executeInsert(sql, generatedColumns);
			return pk;
		}else {
			return -1;
		}
		
	}
	
	@Override
	public Vector<Vector<String>> findCinema(Cinema cinema) {
		bd = new BaseDaoImpl();
		
		String sql = "select * from CINEMA where CINEMA_STATUS = '1'";
		if(!"".equals(cinema.getCinemaName()) && null != cinema.getCinemaName())
			sql+=" and CINEMA_NAME like '%"+cinema.getCinemaName()+"%'";
		if(!"".equals(cinema.getCinemaAddress()) && null != cinema.getCinemaAddress())
			sql += " and CINEMA_ADDRESS like '%"+cinema.getCinemaAddress()+"%'";
		sql += " order by CINEMA_ID desc";
		//System.out.println(sql);
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		for (Map<String, Object> map : queryForList) {
			Vector<String> row = new Vector<String>();
			row.add(map.get("CINEMA_ID")+"");
			row.add(map.get("CINEMA_NAME")+"");
			row.add(map.get("CINEMA_ADDRESS")+"");
			data.add(row);
		}
		return data;
	}

	@Override
	public int deleteCinemaById(Integer cinemaid) {
		bd = new BaseDaoImpl();
		int i = 0;
		
		String sql = "update CINEMA set CINEMA_STATUS = '0' where CINEMA_ID = "+cinemaid;
		i += bd.executeUpdate(sql);
		return i;
	}

	
	@Override
	public int updateCinemaById(Cinema cinema) {
		bd = new BaseDaoImpl();
		
		String sql = "update CINEMA set CINEMA_NAME = '"+cinema.getCinemaName()+"'";
		sql += " ,CINEMA_ADDRESS = '"+cinema.getCinemaAddress()+"'";
		sql += " ,CINEMA_STATUS = '"+cinema.getCinemaStatus()+"'";
		sql += " where CINEMA_ID = "+cinema.getCinemaid();
		int i = bd.executeUpdate(sql);
		return i;
	}

	
	

}
