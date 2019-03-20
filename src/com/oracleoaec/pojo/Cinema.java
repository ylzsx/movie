package com.oracleoaec.pojo;

public class Cinema {

	private Integer cinemaid;
	private String cinemaName;
	private String cinemaAddress;
	private String cinemaStatus;
	
	public Cinema() {}
	public Cinema(Integer cinemaid, String cinemaName, String cinemaAddress) {
		super();
		this.cinemaid = cinemaid;
		this.cinemaName = cinemaName;
		this.cinemaAddress = cinemaAddress;
		this.cinemaStatus = "1";
	}
	
	public Integer getCinemaid() {
		return cinemaid;
	}
	public void setCinemaid(Integer cinemaid) {
		this.cinemaid = cinemaid;
	}
	public String getCinemaName() {
		return cinemaName;
	}
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	public String getCinemaAddress() {
		return cinemaAddress;
	}
	public void setCinemaAddress(String cinemaAddress) {
		this.cinemaAddress = cinemaAddress;
	}
	public String getCinemaStatus() {
		return cinemaStatus;
	}
	public void setCinemaStatus(String cinemaStatus) {
		this.cinemaStatus = cinemaStatus;
	}
	
	
}
