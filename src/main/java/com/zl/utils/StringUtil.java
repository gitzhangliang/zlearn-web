package com.zl.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tzxx
 */
public class StringUtil {

	public static List<String> strToList(String str, String split){
		if(str == null || "".equals(str)){
			return new ArrayList<>();
		}
		String[] strArr = str.split(split);
		return Arrays.asList(strArr);
	}
	/**统计某个字符出现的次数 
	 * @param shortStr
	 * @param longStr
	 * @return
	 */
	public static int countSubStr(String  shortStr,String longStr) {
		int num = 0;
		String regEx = shortStr;
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(longStr);
		while (m.find()) {
			num++;
		}
		return num;
	}
	
	/**字符串匹配正则 
	 * @param regEx
	 * @param longStr
	 * @return
	 */
	public static boolean match(String regEx,String longStr) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(longStr);
		while (m.find()) {
			return true;
		}
		return false;
	}
	
	/**截取最后一位字符
	 * @param str
	 * @return
	 */
	public static String subStrLastBit(String str) {
		return str.substring(0,str.length()-1);
	}


}
