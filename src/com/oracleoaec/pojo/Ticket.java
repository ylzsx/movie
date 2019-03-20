package com.oracleoaec.pojo;

public class Ticket {

	private Integer ticketid;
	private Integer ticketUid;
	private Integer ticketSid;
	private Integer ticketSeat;
	private String ticketStatus;
	
	public Ticket() {}

	public Ticket(Integer ticketid, Integer ticketUid, Integer ticketSid, Integer ticketSeat, String ticketStatus) {
		super();
		this.ticketid = ticketid;
		this.ticketUid = ticketUid;
		this.ticketSid = ticketSid;
		this.ticketSeat = ticketSeat;
		this.ticketStatus = ticketStatus;
	}

	public Integer getTicketid() {
		return ticketid;
	}

	public void setTicketid(Integer ticketid) {
		this.ticketid = ticketid;
	}

	public Integer getTicketUid() {
		return ticketUid;
	}

	public void setTicketUid(Integer ticketUid) {
		this.ticketUid = ticketUid;
	}

	public Integer getTicketSid() {
		return ticketSid;
	}

	public void setTicketSid(Integer ticketSid) {
		this.ticketSid = ticketSid;
	}

	public Integer getTicketSeat() {
		return ticketSeat;
	}

	public void setTicketSeat(Integer ticketSeat) {
		this.ticketSeat = ticketSeat;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	
	
	
}
