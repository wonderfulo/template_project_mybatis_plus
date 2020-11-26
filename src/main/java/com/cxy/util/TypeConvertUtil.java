package com.cxy.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @author BZhou
 * @version 创建时间：2018年4月8日 上午11:31:07 
 */
public class TypeConvertUtil {

	/**
	 * 负数和整数
	 */
	public static String REG_NUMBER01 = "^[-]?[0-9]+$";
	
	
	public static long bigint2long(Object obj) {
		if (obj == null)
			return 0;
		return ((BigInteger) obj).longValue();
	}
	
	public static int obj2int(Object obj) {
		if (obj == null)
			return 0;
		return ((Integer) obj).intValue();
	}
	
	public static int str2int(String str) {
		if (str == null || "".equals(str))
			return 0;
		return Integer.parseInt(str);
	}
	
	public static Integer str2Integer(String str) {
		if (str == null || "".equals(str))
			return null;
		return Integer.parseInt(str);
	}
	public static long str2long(String str) {
		if (str == null || "".equals(str))
			return 0L;
		return Long.parseLong(str);
	}
	public static Long str2Long(String str) {
		if (str == null || "".equals(str))
			return 0L;
		return Long.valueOf(str);
	}
	public static Float Obj2Float(Object obj) {
		if (obj == null)
			return 0f;
		return Float.valueOf(obj.toString());
	}
	public static String Obj2Str(Object obj) {
		if (obj == null)
			return null;
		return obj.toString();
	}
	public static String Obj2String(Object obj) {
		if(obj == null)
			return "";
		return obj.toString();
	}
	public static Long obj2Long(Object obj) {
		if (obj == null || obj.equals(""))
			return null;
		return Long.parseLong(obj.toString());
	}
	public static long obj2long(Object obj) {
		if (obj == null)
			return 0;
		return Long.parseLong(obj.toString());
	}
	public static Integer obj2Integer(Object obj) {
		if (obj == null)
			return null;
		return Integer.parseInt(obj.toString());
	}
	
	public static Double obj2Double(Object obj) {
		if (obj == null)
			return null;
		return Double.parseDouble(obj.toString());
	}
	public static float floatFormat(String num, int scale) {
		BigDecimal b = new BigDecimal(num);
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断字符串是否为数字/小数
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		return str.matches("-?[0-9]+.*[0-9]*");
	}
	
	/**
	 * 判断字符串是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isContainChina(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
	}
}
