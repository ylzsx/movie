package com.oracleoaec.pojo;

public class Movie {

	private int movieid;
	private String movieName;
	private String movieDetail;
	private String movieDuration;
	private String movieType;
	private String movieStatus;
	
	public Movie() {}
	public Movie(int movieid, String movieName, String movieDetail, String movieDuration, String movieType,
			String movieStatus) {
		super();
		this.movieid = movieid;
		this.movieName = movieName;
		this.movieDetail = movieDetail;
		this.movieDuration = movieDuration;
		this.movieType = movieType;
		this.movieStatus = movieStatus;
	}
	
	public int getMovieid() {
		return movieid;
	}
	public void setMovieid(int movieid) {
		this.movieid = movieid;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieDetail() {
		return movieDetail;
	}
	public void setMovieDetail(String movieDetail) {
		this.movieDetail = movieDetail;
	}
	public String getMovieDuration() {
		return movieDuration;
	}
	public void setMovieDuration(String movieDuration) {
		this.movieDuration = movieDuration;
	}
	public String getMovieType() {
		return movieType;
	}
	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}
	public String getMovieStatus() {
		return movieStatus;
	}
	public void setMovieStatus(String movieStatus) {
		this.movieStatus = movieStatus;
	}
	
	
}
