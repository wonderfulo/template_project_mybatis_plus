package com.cxy.util;

import java.util.HashMap;
/**
 * 关键字过滤类
 * @author Administrator
 *
 */
public class KeyWordFliterUtil {
	private static HashMap keysMap = new HashMap();
	private static int matchType = 1; //1:最小长度匹配 2：最大长度匹配
	private  static  String[] keyWords = {"操蛋","草泥马","麻痹","逼","垃圾","毛主席","操"};
	/**
	 * 构造关键字树状DFA图
	 */
	public  static void addKeywords(String[] keywords){
		for(int i=0;i<keywords.length;i++){
			String key = keywords[i];
			HashMap nowhash = keysMap;
			for(int j=0;j<key.length();j++){
				char word = key.charAt(j);
				Object wordMap = nowhash.get(word);
				if(wordMap!=null){
					nowhash = (HashMap)wordMap;
				}else{
					HashMap newWordHash = new HashMap();
					newWordHash.put("isEnd", "0");
					nowhash.put(word,newWordHash);
					nowhash = newWordHash;
				}
				if(j==key.length()-1){
					nowhash.put("isEnd", "1");
				}
			}
		}
	}
	
	/**
	 * 重置关键词
	 */
	public static void clearKeywords(){
		keysMap = new HashMap();
	}
	
	/**
	 * 检查一个字符串从begin位置起开始是否有keyword符合，
	 * 如果有符合的keyword值，返回值为匹配keyword的长度，否则返回零
	 * flag 1:最小长度匹配 2：最大长度匹配
	 */

	public static int checkKeyWords(String txt,int begin,int flag){	
		HashMap nowhash = keysMap;		
		int maxMatchRes = 0;
		int res = 0;
		for(int i=begin;i<txt.length();i++){
			char word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if(wordMap!=null){
				res++;
				nowhash = (HashMap)wordMap;
				if(((String)nowhash.get("isEnd")).equals("1")){
					if(flag==1){
						return res;
					}else{
						maxMatchRes = res;
					}
				}
			}else{
				return maxMatchRes;
			}
		}
		return maxMatchRes;
	}
	
	/**
	 * 返回txt中关键字的列表
	 */
	public static HashMap getTxtKeyWords(String txt){		
		HashMap res =new HashMap();
		for(int i=0;i<txt.length();){
			int len = checkKeyWords(txt,i,matchType);
			if(len>0){
				Object obj = res.get(txt.substring(i, i+len));
				if(obj==null){
					res.put(txt.substring(i, i+len),new Integer(1));
				}else{
					Integer count = new Integer(((Integer)obj).intValue()+1);
					res.put(txt.substring(i, i+len),count);
				}
				i+=len;
			}else{
				i++;
			}
		}
		return res;
	}

	/**
	 * 仅判断txt中是否有关键字
	 */
	public static boolean isContentKeyWords(String txt){
		for(int i=0;i<txt.length();i++){
			int len = checkKeyWords(txt,i,1);
			if(len>0){
				return true;
			}
		}
		return false;
	}

	/**
	 * flag:
	 * 1:将txt中的关键字替换成指定字符串
	 * 2：将txt中的关键字每个字都替换成指定的字符串
	 */
	public static String getReplaceStrTxtKeyWords(String txt,String replacestr,int flag){
		StringBuffer res = new StringBuffer();
		for(int i=0;i<txt.length();){
			int len = checkKeyWords(txt,i,matchType);
			if(len>0){
				if(flag==2)
				for(int j=0;j<len;j++){
					res.append(replacestr);
				}
				if(flag==1)
				res.append(replacestr);
				i+=len;
			}else{
				res.append(txt.charAt(i));
				i++;
			}
		}
		return res.toString();
	}
	

	public HashMap getKeysMap() {
		return keysMap;
	}

	public void setKeysMap(HashMap keysMap) {
		this.keysMap = keysMap;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}
   
	
	public  static String handlerKeyWord(String contentText){
		addKeywords(keyWords);
		if(isContentKeyWords(contentText)){
			contentText=getReplaceStrTxtKeyWords(contentText, "*", 2);
		//	String contentText=getTxtKeyWords(contentText);
		}
		return contentText;
	}

}
