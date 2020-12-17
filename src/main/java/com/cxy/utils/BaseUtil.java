package com.cxy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * 根据逗号的字符串分割拼接sql语句
	 *
	 * @param strs
	 * @param fieldName
	 * @return
	 */
	public static String appendLoop(String strs, String fieldName) {
		String str;
		StringBuilder sb = new StringBuilder();
		String[] strArray = strs.split(",");
		for (int i = 0; i < strArray.length; i++) {
			str = strArray[i];
			// 判断第一次循环
			if (i == 0) {
				sb.append("  and ( ");
			} else if (i <= strArray.length - 1) {
				// 中间
				sb.append("  or ");
			}
			sb.append("  (").append(fieldName).append(" = '").append(str).append("') ");
			if (i == strArray.length - 1) {
				// 最后一次循环
				sb.append("  ) ");
			}
		}
		return sb.toString();
	}

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
