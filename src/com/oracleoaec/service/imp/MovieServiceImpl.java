package com.oracleoaec.service.imp;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.oracleoaec.dao.BaseDao;
import com.oracleoaec.dao.impl.BaseDaoImpl;
import com.oracleoaec.pojo.Movie;
import com.oracleoaec.service.MovieService;

public class MovieServiceImpl implements MovieService{

	private BaseDao bd;
	
	@Override
	public int addMovie(Movie movie) {
		bd = new BaseDaoImpl();
		
		String sql1 = "select * from MOVIE where MOVIE_STATUS = '1' and MOVIE_NAME = '"+movie.getMovieName()+"'";
		int i = bd.executeUpdate(sql1);
		if(i == 0) {
			String sql = "insert into MOVIE(MOVIE_ID,MOVIE_NAME,MOVIE_DETAIL,MOVIE_DURATION,MOVIE_TYPE,MOVIE_STATUS)";
			sql += " values(SEQ_MOVIE.NEXTVAL,'"+movie.getMovieName()+"'";
			sql += " ,'"+movie.getMovieDetail()+"'";
			sql += " ,'"+movie.getMovieDuration()+"'";
			sql += " ,'"+movie.getMovieType()+"'";
			sql += " ,'"+movie.getMovieStatus()+"')";
			String[] generatedColumns = {"MOVIE_ID"};
			int pk = bd.executeInsert(sql, generatedColumns);
			return pk;
		}else {
			return -1;
		}
		
	}

	@Override
	public Vector<Vector<String>> findMovieInfo(Movie movie) {
		bd = new BaseDaoImpl();
		
		String sql = "select * from MOVIE where MOVIE_STATUS = '1'";
		if(!"".equals(movie.getMovieName()) && null != movie.getMovieName())
			sql += " and MOVIE_NAME like '%"+movie.getMovieName()+"%'";
		if(!"".equals(movie.getMovieType()) && null != movie.getMovieType())
			sql += " and MOVIE_TYPE like '%"+movie.getMovieType()+"%'";
		sql += " order by MOVIE_ID desc";
		
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		for(Map<String,Object> map:queryForList) {
			Vector<String> row = new Vector<String>();
			row.add(map.get("MOVIE_ID")+"");
			row.add(map.get("MOVIE_NAME")+"");
			row.add(map.get("MOVIE_DURATION")+"");
			row.add(map.get("MOVIE_TYPE")+"");
			row.add(map.get("MOVIE_DETAIL")+"");
			data.add(row);
		}
		return data;
	}

	@Override
	public int updateMovieById(Movie movie) {
		bd = new BaseDaoImpl();
		
		String sql = "update MOVIE set MOVIE_NAME = '"+movie.getMovieName()+"'";
		sql += " ,MOVIE_DURATION = "+movie.getMovieDuration();
		sql += " ,MOVIE_DETAIL = '"+movie.getMovieDetail()+"'";
		sql += " ,MOVIE_TYPE = '"+movie.getMovieType()+"'";
		sql += " ,MOVIE_STATUS = '"+movie.getMovieStatus()+"'";
		sql += " where MOVIE_ID = "+movie.getMovieid();
		int i = bd.executeUpdate(sql);
		return i;
	}

	@Override
	public int deleteMovieById(Integer movieid) {
		bd = new BaseDaoImpl();
		
		String sql = "update MOVIE set MOVIE_STATUS = '0' where MOVIE_ID = "+movieid;
		int i = bd.executeUpdate(sql);
		return i;
	}

}
