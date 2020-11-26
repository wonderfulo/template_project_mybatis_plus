//package com.cxy.util;
//
//import org.apache.commons.lang.StringUtils;
//
//import java.util.List;
//import java.util.Random;
//
//public class StringUtil {
//
//	/**
//	 * 获取字符串首字母
//	 * @param strChinese
//	 * @param bUpCase 是否转换为小写
//	 * @return
//	 */
//	public static String getPYIndexStr(String strChinese, boolean bUpCase){
//		try{
//			StringBuffer buffer = new StringBuffer();
//			byte b[] = strChinese.getBytes("GBK");//把中文转化成byte数组
//			for(int i = 0; i < b.length; i++){
//				if((b[i] & 255) > 128){
//					int char1 = b[i++] & 255;
//					char1 <<= 8;//左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方
//					int chart = char1 + (b[i] & 255);
//					buffer.append(getPYIndexChar((char)chart, bUpCase));
//					continue;
//				}
//				char c = (char)b[i];
//				if(!Character.isJavaIdentifierPart(c))//确定指定字符是否可以是 Java 标识符中首字符以外的部分。
//					c = 'A';
//				buffer.append(c);
//			}
//			return buffer.toString();
//		}catch(Exception e){
//			System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519").append(e.getMessage()).toString());
//		}
//		return null;
//	}
//
//	/**
//	 * 获取字符首字母
//	 * @param strChinese
//	 * @param bUpCase 是否转换为小写
//	 * @return
//	 */
//	private static char getPYIndexChar(char strChinese, boolean bUpCase){
//		int charGBK = strChinese;
//		char result;
//		if(charGBK >= 45217 && charGBK <= 45252)
//			result = 'A';
//		else if(charGBK >= 45253 && charGBK <= 45760)
//			result = 'B';
//		else if(charGBK >= 45761 && charGBK <= 46317)
//			result = 'C';
//		else if(charGBK >= 46318 && charGBK <= 46825)
//			result = 'D';
//		else if(charGBK >= 46826 && charGBK <= 47009)
//			result = 'E';
//		else if(charGBK >= 47010 && charGBK <= 47296)
//			result = 'F';
//		else if(charGBK >= 47297 && charGBK <= 47613)
//			result = 'G';
//		else if(charGBK >= 47614 && charGBK <= 48118)
//			result = 'H';
//		else if(charGBK >= 48119 && charGBK <= 49061)
//			result = 'J';
//		else if(charGBK >= 49062 && charGBK <= 49323)
//			result = 'K';
//		else if(charGBK >= 49324 && charGBK <= 49895)
//			result = 'L';
//		else if(charGBK >= 49896 && charGBK <= 50370)
//			result = 'M';
//		else if(charGBK >= 50371 && charGBK <= 50613)
//			result = 'N';
//		else if(charGBK >= 50614 && charGBK <= 50621)
//			result = 'O';
//		else if(charGBK >= 50622 && charGBK <= 50905)
//			result = 'P';
//		else if(charGBK >= 50906 && charGBK <= 51386)
//			result = 'Q';
//		else if(charGBK >= 51387 && charGBK <= 51445)
//			result = 'R';
//		else if(charGBK >= 51446 && charGBK <= 52217)
//			result = 'S';
//		else if(charGBK >= 52218 && charGBK <= 52697)
//			result = 'T';
//		else if(charGBK >= 52698 && charGBK <= 52979)
//			result = 'W';
//		else if(charGBK >= 52980 && charGBK <= 53688)
//			result = 'X';
//		else if(charGBK >= 53689 && charGBK <= 54480)
//			result = 'Y';
//		else if(charGBK >= 54481 && charGBK <= 55289)
//			result = 'Z';
//		else
//			result = (char)(65 + (new Random()).nextInt(25));
//		if(!bUpCase)
//			result = Character.toLowerCase(result);
//		return result;
//	}
//
//	/**
//	 * obj转String
//	 *
//	 * @param obj
//	 * @return
//	 */
//	public static String convertToString(Object obj) {
//		return (obj == null) ? "" : obj.toString();
//	}
//
//
//	public static boolean isEmpty(Object obj) {
//		return (obj==null||"".equals(obj.toString().trim()))?true:false;
//	}
//	/**
//	 * 如果表达式为空，则返回toStr
//	 * @param expr
//	 * @param toStr
//	 * @return
//	 */
//	public static String emptyReplace(Object expr,Object toStr) {
//		if(isEmpty(expr)) {
//			if(!isEmpty(toStr))
//				return toStr.toString();
//			return "";
//		}
//		return expr.toString();
//	}
//
//	/**
//	 * 去除html标签
//	 *
//	 * @param content
//	 * @return
//	 */
//	public static String stripHtml(String content) {
//		if (!StringUtils.isEmpty(content)) {
//			// <p>段落替换为换行
//			content = content.replaceAll("<p .*?>", "\r\n");
//			// <br><br/>替换为换行
//			content = content.replaceAll("<br\\s*/?>", "\r\n");
//			// 去掉其它的<>之间的东西
//			content = content.replaceAll("\\<.*?>", "");
//			// 去除tab、空格、空行
//			content = content.replaceAll("\\s*|\t|\r|\n", "");
//			// 去除 &nbsp
//			content = content.replaceAll("&nbsp", "");
//
//		}
//		return content;
//	}
//
//	/**
//	 * 根据逗号的字符串分割拼接sql语句
//	 *
//	 * @param strs
//	 * @param fieldName
//	 * @return
//	 */
//	public static String appendLoop(String strs, String fieldName) {
//		String str;
//		StringBuilder sb = new StringBuilder();
//		String[] strArray = strs.split(",");
//		for (int i = 0; i < strArray.length; i++) {
//			str = strArray[i];
//			// 判断第一次循环
//			if (i == 0) {
//				sb.append("  and ( ");
//			} else if (i <= strArray.length - 1) {
//				// 中间
//				sb.append("  or ");
//			}
//			sb.append("  (").append(fieldName).append(" = '").append(str).append("') ");
//			if (i == strArray.length - 1) {
//				// 最后一次循环
//				sb.append("  ) ");
//			}
//		}
//		return sb.toString();
//	}
//
//
//    /**
//     * 批量插入拼接sql
//     * */
//    public static String beans2db(List<Object[]> list) {
//
//        StringBuffer values=new StringBuffer();
//
//        for (int i = 0; i < list.size(); i++) {
//            Object[] objects = list.get(i);
//            StringBuffer value = new StringBuffer();
//            for (int j = 0; j < objects.length; j++) {
//                value.append(",'"+objects[j]+"'");
//            }
//            value = value.deleteCharAt(0);
//            values=values.append(",(").append(value).append(")");
//        }
//        values=values.deleteCharAt(0);
//        return values.toString();
//    }
//
//    /**
//     * 判断字符是不是没有表情符号
//     * @param codePoint
//     * @return
//     */
//	private static boolean isNotEmojiCharacter(char codePoint) {
//		return (codePoint == 0x0) ||
//				(codePoint == 0x9) ||
//				(codePoint == 0xA) ||
//				(codePoint == 0xD) ||
//				((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
//				((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
//				((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
//	}
//
//	/**
//	 * 将表情符号替换为"*"
//	 * @param source
//	 * @return
//	 */
//	public static String filterEmoji(String source) {
//		if(StringUtils.isEmpty(source)) {
//			return source;
//		}
//		int len = source.length();
//		StringBuilder buf = new StringBuilder(len);
//		for (int i = 0; i < len; i++) {
//			char codePoint = source.charAt(i);
//			if (isNotEmojiCharacter(codePoint)) {
//				buf.append(codePoint);
//			} else{
//				buf.append("*");
//			}
//		}
//		return buf.toString();
//	}
//
//	public static void main(String[] args) {
//		System.out.println(filterEmoji("dsdsds说明12343232!@@#$%^^^&&"));
//	}
//}
