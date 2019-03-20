package com.oracleoaec.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	private static final SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	/**
	 * 获取当前时间
	 * @return	字符串时间
	 */
	public static String getCurrentTime() {
		 Date date = new Date();
		 String time = form.format(date);
		return time;
	}
	
	/**
	 * 时间相加
	 * @param startTime	开始时间
	 * @param duration	要加的分钟数
	 * @return	返回相加后的时间字符串
	 */
	public static String addTime(String startTime,Integer duration) {
		String endTime = null;
		try {
			Date date = form.parse(startTime);
			date.setMinutes(date.getMinutes()+duration);
			endTime = form.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endTime;
	}
	
	/**
	 * 比较两个字符串时间的先后
	 * @param startTime
	 * @param endTime
	 * @return	若第一个时间在第二个时间之前返回true，否则返回false
	 */
	public static boolean compareTime(String startTime,String endTime) {
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = form.parse(startTime);
			endDate = form.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(startDate.before(endDate)) {
			return true;
		}else {
			return false;
		}
	}
	
	/*public static void main(String[] args) {
		System.out.println(getCurrentTime());
		System.out.println(addTime("2018-08-27 23:15:05", 120));
		System.out.println(compareTime(getCurrentTime(), "2018-08-27 16:15:05"));
	}*/
}
