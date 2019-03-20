package com.oracleoaec.service;

import java.util.Map;
import java.util.Vector;

import com.oracleoaec.pojo.Users;

public interface UsersService {

	/**
	 * 登录
	 * @param accout
	 * @param pwd
	 * @param state
	 * @return 返回Map对象,其中包括一个结果值 key:result value:"登录成功","用户名或密码错误","用户不是管理员身份"
	 */
	public Map<String,Object> login(String account,String pwd,String state);
	
	/**
	 * 管理员添加用户
	 * @param user
	 * @return	返回添加用户的主键,失败返回0,若该账号已经存在返回-1
	 */
	public int registUser(Users user);
	
	/**
	 * 根据姓名和账号查询
	 * @param userNmae
	 * @param userAccount
	 * @return	返回用户信息Vector<Vector>
	 */
	public Vector<Vector> findUserInfo(String userName,String userAccount,String userLevel);
	
	/**
	 * 根据用户id查找用户信息
	 * @param userid
	 * @return 装有user信息的Map
	 */
	public Map<String, Object> findUserById(Integer userid);
	
	/**
	 * 根据id删除用户
	 * @param userid
	 * @return
	 */
	public int deleteUserById(Integer userid);
	
	/**
	 * 更新用户信息
	 * @param user	传入更新后的用户
	 * @return		返回执行成功的条数
	 */
	public int updateUser(Users user);
	
	/**
	 * 初始化密码
	 * @param userid
	 * @return	返回执行成功的条数
	 */
	public int initializePassword(Integer userid);
	
	/**
	 * 为用户充值
	 * @param userid
	 * @return	返回执行成功的条数
	 */
	public int addUserBalanceById(Integer userid,Double balance);
}
