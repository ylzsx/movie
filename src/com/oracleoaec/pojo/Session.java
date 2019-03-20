package com.oracleoaec.pojo;

public class Session {

	private Integer sessionid;
	private Integer sessionHid;
	private Integer sessionCid;
	private Integer sessionMid;
	private String sessionTime;
	private String sessionEndTime;
	private Double sessionPrice;
	private Integer sessionRemain;
	private String sessionStatus;
	
	public Session() {}

	public Session(Integer sessionid, Integer sessionHid, Integer sessionCid, Integer sessionMid, String sessionTime,
			String sessionEndTime, Double sessionPrice, Integer sessionRemain, String sessionStatus) {
		super();
		this.sessionid = sessionid;
		this.sessionHid = sessionHid;
		this.sessionCid = sessionCid;
		this.sessionMid = sessionMid;
		this.sessionTime = sessionTime;
		this.sessionEndTime = sessionEndTime;
		this.sessionPrice = sessionPrice;
		this.sessionRemain = sessionRemain;
		this.sessionStatus = sessionStatus;
	}

	public String getSessionEndTime() {
		return sessionEndTime;
	}

	public void setSessionEndTime(String sessionEndTime) {
		this.sessionEndTime = sessionEndTime;
	}

	public Integer getSessionid() {
		return sessionid;
	}

	public void setSessionid(Integer sessionid) {
		this.sessionid = sessionid;
	}

	public Integer getSessionHid() {
		return sessionHid;
	}

	public void setSessionHid(Integer sessionHid) {
		this.sessionHid = sessionHid;
	}

	public Integer getSessionCid() {
		return sessionCid;
	}

	public void setSessionCid(Integer sessionCid) {
		this.sessionCid = sessionCid;
	}

	public Integer getSessionMid() {
		return sessionMid;
	}

	public void setSessionMid(Integer sessionMid) {
		this.sessionMid = sessionMid;
	}

	public String getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}

	public Double getSessionPrice() {
		return sessionPrice;
	}

	public void setSessionPrice(Double sessionPrice) {
		this.sessionPrice = sessionPrice;
	}

	public Integer getSessionRemain() {
		return sessionRemain;
	}

	public void setSessionRemain(Integer sessionRemain) {
		this.sessionRemain = sessionRemain;
	}

	public String getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}
	
	
	
}
