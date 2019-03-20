package com.oracleoaec.service.imp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.oracleoaec.dao.BaseDao;
import com.oracleoaec.dao.impl.BaseDaoImpl;
import com.oracleoaec.pojo.Users;
import com.oracleoaec.service.UsersService;

public class UsersServiceImp implements UsersService{

	private BaseDao bd;

	@Override
	public Map<String, Object> login(String account, String pwd, String state) {
		bd = new BaseDaoImpl();
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sql = "select * from USERS ";
		sql	+= " where USER_ACCOUNT = '"+account+"'";
		sql += " and  USER_PASSWORD = '"+pwd+"'"+" and USER_STATUS = '1'";
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		if(queryForList.size() > 0) {
			map = queryForList.get(0);
			if(map.get("USER_STATE").equals(state)) {
				map.put("result", "登录成功");
			}else {
				map.put("result", "用户身份错误");
			}
		}else {
			map.put("result", "用户名或密码错误");
		}
		
		return map;
	}

	@Override
	public int registUser(Users user) {
		bd = new BaseDaoImpl();
		
		//根据输入余额处理用户等级
		if(user.getUserBalance() < 100) {
			user.setUserLevel("6");
		}else if(user.getUserBalance() < 300) {
			user.setUserLevel("5");
		}else if(user.getUserBalance() < 500) {
			user.setUserLevel("4");
		}else if(user.getUserBalance() < 1000) {
			user.setUserLevel("3");
		}else if(user.getUserBalance() < 5000) {
			user.setUserLevel("2");
		}else {
			user.setUserLevel("1");
		}
		
		String sql1 = "select * from USERS where USER_STATUS = '1' and USER_ACCOUNT = '"+user.getUserAccount()+"'";
		int i = bd.executeUpdate(sql1);
		if(i == 0) {
			String sql = "insert into USERS(USER_ID,USER_NAME,USER_ACCOUNT,USER_PASSWORD,USER_LEVEL,USER_BALANCE,USER_STATE,USER_STATUS)";
			sql += " values (SEQ_USERS.nextval,'"+user.getUserName()+"'";
			sql += ",'"+user.getUserAccount()+"'";
			sql += ",'"+user.getUserPassword()+"'";
			sql += ",'"+user.getUserLevel()+"'";
			sql += ","+user.getUserBalance();
			sql += ",'"+user.getUserState()+"','1')";
			//System.out.println(sql);
			String[] generatedColumns = {"USER_ID"};
			int executeInsert = bd.executeInsert(sql, generatedColumns);
			
			return executeInsert;
		}else {
			return -1;
		}
		
	}
	
	@Override
	public Vector<Vector> findUserInfo(String userName, String userAccount,String userLevel) {
		bd = new BaseDaoImpl();
		Vector<Vector> data = new Vector<Vector>();
		
		String sql = "select * from users where USER_STATE = '1' and USER_STATUS = '1'";
		if(!"".equals(userName) && null != userName) sql += " and USER_NAME like '%"+userName+"%'";
		if(!"".equals(userAccount) && null != userAccount) sql += "and USER_ACCOUNT like '%"+userAccount+"%'";
		if(!"".equals(userLevel) && null != userLevel) sql += " and USER_LEVEL like '%"+userLevel+"%'";
		sql += "order by USER_ID desc";
		
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		for (Map<String, Object> map : queryForList) {
			Vector<String> row = new Vector<String>();
			
			row.add(map.get("USER_ID")+"");
			row.add(map.get("USER_NAME")+"");
			row.add(map.get("USER_ACCOUNT")+"");
			row.add(map.get("USER_LEVEL")+"");
			row.add(map.get("USER_BALANCE")+"");
			row.add(map.get("USER_PASSWORD")+"");
			row.add(map.get("USER_STATE")+"");
			data.add(row);
		}
		return data;
	}

	@Override
	public int deleteUserById(Integer userid) {
		bd = new BaseDaoImpl();
		
		String sql = "update USERS set USER_STATUS = '0' where USER_ID = "+userid;
		int i = bd.executeUpdate(sql);
		return i;
	}

	@Override
	public int updateUser(Users user) {
		bd = new BaseDaoImpl();
		 
		String sql = "update USERS set ";
		sql += " USER_NAME = '"+user.getUserName()+"'";
		sql += " ,USER_PASSWORD = '"+user.getUserPassword()+"'";
		sql += " ,USER_LEVEL = '"+user.getUserLevel()+"'";
		sql += " ,USER_BALANCE = "+user.getUserBalance();
		sql += " ,USER_ACCOUNT = '"+user.getUserAccount()+"'";
		sql += " ,USER_STATE = '"+user.getUserState()+"'";
		sql += " ,USER_STATUS = '"+user.getUserStatus()+"'";
		sql += " where USER_ID = "+user.getUserId();
		int i = bd.executeUpdate(sql);
		return i;
	}

	@Override
	public Map<String, Object> findUserById(Integer userid) {
		bd = new BaseDaoImpl();
		
		String sql = "select * from USERS where USER_STATUS = '1' and USER_ID = "+userid;
		List<Map<String, Object>> queryForList = bd.queryForList(sql);
		return queryForList.get(0);
	}

	@Override
	public int initializePassword(Integer userid) {
		bd = new BaseDaoImpl();
		
		String sql = "update USERS set USER_PASSWORD = '000000' where USER_ID = "+userid;
		int i = bd.executeUpdate(sql);
		return i;
	}

	@Override
	public int addUserBalanceById(Integer userid,Double balance) {
		bd = new BaseDaoImpl();
		
		String userLevel = null;
		//根据输入余额处理用户等级
		if(balance < 100) {
			userLevel = "6";
		}else if(balance < 300) {
			userLevel = "5";
		}else if(balance < 500) {
			userLevel = "4";
		}else if(balance < 1000) {
			userLevel = "3";
		}else if(balance < 5000) {
			userLevel = "2";
		}else {
			userLevel = "1";
		}
		
		String sql = "update USERS set USER_BALANCE = (USER_BALANCE+"+balance+")";
		sql += " ,USER_LEVEL = '"+userLevel+"' where USER_ID = "+userid;
		int i = bd.executeUpdate(sql);
		return i;
	}
	
}
