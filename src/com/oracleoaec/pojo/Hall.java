package com.oracleoaec.pojo;

public class Hall {

	private Integer hallid;
	private String hallName;
	private Integer hallCid;
	private Integer hallCapacity;
	private String hallStatus;
	
	public Hall() {}

	public Hall(Integer hallid, String hallName, Integer hallCid, Integer hallCapacity, String hallStatus) {
		super();
		this.hallid = hallid;
		this.hallName = hallName;
		this.hallCid = hallCid;
		this.hallCapacity = hallCapacity;
		this.hallStatus = hallStatus;
	}

	public Integer getHallid() {
		return hallid;
	}

	public void setHallid(Integer hallid) {
		this.hallid = hallid;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public Integer getHallCid() {
		return hallCid;
	}

	public void setHallCid(Integer hallCid) {
		this.hallCid = hallCid;
	}

	public Integer getHallCapacity() {
		return hallCapacity;
	}

	public void setHallCapacity(Integer hallCapacity) {
		this.hallCapacity = hallCapacity;
	}

	public String getHallStatus() {
		return hallStatus;
	}

	public void setHallStatus(String hallStatus) {
		this.hallStatus = hallStatus;
	}
	
}
