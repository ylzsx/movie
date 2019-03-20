package com.oracleoaec.pojo;

public class Users {

	private Integer userId;
	private String userName;
	private String userAccount;
	private String userPassword;
	private String userLevel;
	private Double userBalance;
	//"0"为管理员，"1"为用户
	private String userState;
	private String userStatus;
	
	public Users() {}


	public Users(Integer userId, String userName, String userAccount, String userPassword, String userLevel,
			Double userBalance, String userState) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userLevel = userLevel;
		this.userBalance = userBalance;
		this.userState = userState;
		this.userStatus = "1";
	}



	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public Double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(Double userBalance) {
		this.userBalance = userBalance;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}


	public String getUserStatus() {
		return userStatus;
	}


	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	
	
	
}
