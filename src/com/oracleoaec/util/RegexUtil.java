package com.oracleoaec.util;

import java.util.regex.Pattern;

public class RegexUtil {

	//正整数，最多两位小数
	public final static String numberRegex = "([1-9][0-9]*)|([0]\\.\\d{1,2})|([1-9][0-9]*\\.\\d{1,2})";
	//正整数
	public final static String positiveIntegerRegex = "([1-9][0-9]*)";
	//时间格式：1977-03-12 14:04:00
	public final static String timeRegex = "[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d";
	
	/**
	 * 判断字符串是否符合某个正则表达式
	 * @param regex	判断要匹配的正则表达式
	 * @param str	要判断的字符串
	 * @return		返回true或false
	 */
	public static boolean regexMatch(String regex,String str) {
		 Pattern pattern = Pattern.compile(regex);
		 return pattern.matcher(str).matches();
	}
	
	/*public static void main(String[] args) {
		System.out.println(RegexUtil.regexMatch(timeRegex, "1977-03-12 14:04:00"));
	}*/
}
