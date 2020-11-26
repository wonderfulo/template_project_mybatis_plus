package com.cxy.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenUtil {

	public static void addCookie(HttpServletResponse response,String name,String value){
		addCookie(response,name,value,7*24*60*60,"/");
	}
	
	public static void addCookie(HttpServletResponse response,String name,String value,int time){
		addCookie(response,name,value,time,"/");
	}
	
	public static void addCookie(HttpServletResponse response,String name,String value,int time,String path){
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(time);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
	
	public String getCookieValue(HttpServletRequest request,String cookieKey){
		Cookie[] cookies = request.getCookies();
		for(Cookie key:cookies){
			if(cookieKey.equals(key.getName())){
				return key.getValue();
			}
		}
		return null;
	}
	
	
}
