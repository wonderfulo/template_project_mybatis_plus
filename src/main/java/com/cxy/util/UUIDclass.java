package com.cxy.util;
import java.util.UUID;

public class UUIDclass{
	 /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
	public String getUUID(){
		UUID uuid= UUID.randomUUID();
		String str = uuid.toString();
		return str.replace("-", "");
		
	}
	/** 
     * 获得指定数目的UUID 
     * @param number int 需要获得的UUID数量 
     * @return String[] UUID数组 
     */ 
	public String[] getUUID(int number){
		if(number < 1){
			return null;
		}
		String[] str = new String[number];
		for(int i=0;i<number;i++){
			str[i] = getUUID();
		}
		return str;
	}
	/** 
     * 获得oid,这只要24位
     * @return String
     */ 
	public String getOID(){
		return getUUID().substring(0,24);
	}
	public static void main(String[] args){
	System.out.println(UUID.randomUUID());
	}
}

