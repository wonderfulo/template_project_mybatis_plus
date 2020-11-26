package com.cxy.util.chinaUtil;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BZhou
 * @version 创建时间：2018年3月8日 下午1:57:28 
 */
public class FormVerify {

	public static boolean isContainChinese(String obj) {
		if(StringUtils.isEmpty(obj))
			return false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(obj);
		if(m.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(FormVerify.isContainChinese("aaaaa11111王五"));
	}
}
