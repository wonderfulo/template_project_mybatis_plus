package com.cxy.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class NumberUtils {
	public static long longValue(Long numObj) {
		if (numObj == null)
			return 0;
		return numObj.longValue();
	}

	public static Double obj2Double(Object numObj) {
		if (numObj == null)
			return 0d;
		return Double.parseDouble(numObj.toString());
	}

	public static long bigint2long(Object obj) {
		if (obj == null)
			return 0;
		return ((BigInteger) obj).longValue();
	}

	public static long bigint2Float(Object obj) {
		if (obj == null)
			return 0;
		return ((Float) obj).longValue();
	}

	public static int bigint2int(Object obj) {
		if (obj == null)
			return 0;
		return ((BigInteger) obj).intValue();
	}

	public static int obj2int(Object obj) {
		if (obj == null || "".equals(obj))
			return 0;
		return ((Integer) obj).intValue();
	}

	public static int objparseInt(Object obj) {
		if (obj == null || "".equals(obj))
			return 0;
		return Integer.parseInt(obj.toString());
	}

	public static int byte2int(Object obj) {
		if (obj == null)
			return 0;
		return ((Byte) obj).intValue();
	}

	public static float floatValue(Float num) {
		if (num == null)
			return 0;
		return num.floatValue();
	}

	public static int str2int(String str) {
		if (str == null || "".equals(str))
			return 0;
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
		if (obj == null || "".equals(obj))
			return 0f;
		return Float.valueOf(obj.toString());
	}

	public static void main(String[] args) {
		System.out.println(NumberUtils.Obj2Float(""));
	}

	public static String Obj2Str(Object obj) {
		if (obj == null)
			return null;
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

	public static float floatFormat(float num, int scale) {
		BigDecimal b = new BigDecimal(num);
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	public static double floatFormat(double num, int scale) {
		BigDecimal b = new BigDecimal(num);
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String trimLastChar(String str) {
		if (null == str)
			return "";
		if (str.lastIndexOf(",") == str.length() - 1) {
			return str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 移除重复
	 * 
	 * @param arr
	 * @return
	 */
	public static String[] arrayRepetition(String arr[]) {
		if (null == arr || 0 == arr.length)
			return arr;
		Set<String> set = new HashSet<>();
		for (int i = 0; i < arr.length; i++) {
			set.add(arr[i]);
		}
		return (String[]) set.toArray(new String[set.size()]);
	}

	public static Map<String, String> list2Map(List<String> list) {
		Map<String, String> retMap = new HashMap<>();
		if (null == list || 0 == list.size()) {
			return retMap;
		}
		for (int i = 0; i < list.size(); i++) {
			try {
				retMap.put(NumberUtils.Obj2Str(list.get(i)), "0");
			} catch (Exception e) {
				e.printStackTrace();
			}
			continue;
		}
		return retMap;

	}

}
