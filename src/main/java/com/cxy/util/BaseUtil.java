package com.cxy.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 基础工具类
 * @Author: mason_ge
 * @Date: 10:42 2018/12/18
 */
public class BaseUtil {

	/**
	 * 对象转int
	 *
	 * @param obj
	 * @return
	 */
	public static int obj2int(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof String && "".equals(obj.toString())) {
			return 0;
		}
		return Integer.valueOf(obj.toString());
	}

	/**
	 * 转换字段的setter方法名，如pkId -> setPkId， pk_id -> setPkId
	 *
	 * @param fieldName 字段名
	 * @return
	 */
	public static String captureSetterFieldName(String fieldName) {
		String fragment, lowerFieldName;
		StringBuffer stringBuffer;
		StringTokenizer stringTokenizer;
		char[] chars;
		boolean init = true;

		lowerFieldName = fieldName.toLowerCase();
		stringTokenizer = new StringTokenizer(lowerFieldName, "_");

		stringBuffer = new StringBuffer("set");

		while (stringTokenizer.hasMoreTokens()) {
			fragment = stringTokenizer.nextToken();

			chars = fragment.toCharArray();
			if (init) {
				if (chars.length > 1) {
					chars[0] = Character.toUpperCase(chars[0]);
					// fragment = new String(chars);
				}
				init = false;
			} else {
				chars[0] = Character.toUpperCase(chars[0]);
				// fragment = new String(chars);
			}
			stringBuffer.append(chars);
		}

		return stringBuffer.toString();
	}

	/**
	 * 转换字段名，如pkId -> pkId， pk_id -> pkId
	 *
	 * @param fieldName 字段名
	 * @return
	 */
	public static String captureFieldName(String fieldName) {
		String fragment, lowerFieldName;
		StringBuffer stringBuffer;
		StringTokenizer stringTokenizer;
		char[] chars;
		boolean init = false;

		lowerFieldName = fieldName.toLowerCase();
		stringTokenizer = new StringTokenizer(lowerFieldName, "_");

		stringBuffer = new StringBuffer();

		while (stringTokenizer.hasMoreTokens()) {
			fragment = stringTokenizer.nextToken();

			if (init) {
				chars = fragment.toCharArray();
				chars[0] = Character.toUpperCase(chars[0]);
				// fragment = new String(chars);
				stringBuffer.append(chars);
			} else {
				stringBuffer.append(fragment);
				init = true;
			}
		}

		return stringBuffer.toString();
	}

	/**
	 * 获取实体类字段的属性和值
	 *
	 * @param model
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> getClzFieldProperties(Object model)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String name, type;
		Method m;
		Map<String, Object> map = new HashMap<>(0);
		// 获取实体类的所有属性，返回Field数组
		Field[] field = model.getClass().getDeclaredFields();
		// 遍历所有属性
		for (Field t : field) {
			// 获取属性的名字
			name = t.getName();
			// 将属性的首字符大写，方便构造get，set方法
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			m = model.getClass().getMethod("get" + name);
			map.put(t.getName(), m.invoke(model));
		}
		return map;
	}

	/**
	 * 字符串转化时间
	 *
	 * @param str
	 * @return
	 */
	public static Date formatStringToUtilDate(String str) {
		Date date = null;
		if (str == null || "".equals(str)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期格式化 yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 日期格式化 yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 根据提前天数和时间点计算提醒时间
	 *
	 * @param startTime  开始时间
	 * @param beforeDays 比开始时间提前几天
	 * @param beforeTime 提前天的时间点 例如 09:00
	 * @return
	 */
	public static Date getRemindTimeByBeforeDays(Date startTime, String beforeDays, String beforeTime) {
		Date result;
		try {
			int beforeDaysInt = NumberUtils.str2int(beforeDays);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startTime);
			calendar.add(Calendar.DATE, 0 - beforeDaysInt);
			String beforeDate = sdf.format(calendar.getTime());
			String resultTime = beforeDate + " " + beforeTime;
			result = sdf2.parse(resultTime);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取比当前日期相差的日期字符串
	 *
	 * @param diffDays -2为前两天2为后两天
	 * @return
	 */
	public static String getDateByDiffDays(int diffDays) {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, diffDays);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}
}
