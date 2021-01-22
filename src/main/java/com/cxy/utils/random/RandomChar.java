package com.cxy.utils.random;

/**
 * 生成随机字符
 * @author 陈翔宇
 */
public class RandomChar{
	/**
	 * 随机生成字符：含大写，小写，数字
	 * @return String*/
	public String getRandomChar(){
		int index = (int)Math.round(Math.random()*2);
		String randChar = "";
		
		switch(index){
		case 0:
			//大写字母
			randChar = String.valueOf((char)Math.round(Math.random()*25+65));
			break;
		case 1:
			//小写字母
			randChar = String.valueOf((char)Math.round(Math.random()*25+97));
			break;
		default:
			//数字
			randChar = String.valueOf(Math.round(Math.random()*9));
			break;
		}
		return randChar;
	}
}