//package com.vsvz.utils.pinyin;
//
//import org.apache.commons.lang.StringUtils;
//
//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
//
//public class PinyinUtil {
//
//	private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//
//	public static enum Type {
//		UPPERCASE,              //全部大写
//		LOWERCASE,              //全部小写
//		FIRSTUPPER              //首字母大写
//	}
//
//	public PinyinUtil() {
//		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//	}
//
//	public String toPinYin(String str) {
//		try {
//			return toPinYin(str, "", null);
//		} catch (BadHanyuPinyinOutputFormatCombination e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public String[] toPinYinAndFirst(String str) {
//		try {
//			return toPinYinAndFirst(str, "", null);
//		} catch (BadHanyuPinyinOutputFormatCombination e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 拼音转换
//	 * @param str 转换的字符串
//	 * @param spera 字符之间的间隔符
//	 * @param type 转换的类型 com.vsvz.utils.pinyin.PinyinUtil.Type
//	 * @return
//	 * @throws BadHanyuPinyinOutputFormatCombination
//	 */
//	public String toPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
//		if(StringUtils.isEmpty(str)) {
//			return null;
//		}
//		if(type != null && type == Type.UPPERCASE) {
//			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//		} else {
//			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//		}
//
//		Object[] convertChar = new Object[str.length()];
//        for(int i=0; i<str.length(); i++){
//            char c = str.charAt(i);
//            if((int)c <= 128) {
//            	convertChar[i] = c;
//            } else {
//            	 String[] t = PinyinHelper.toHanyuPinyinStringArray(c, format);
//                if(t == null || t.length<1) {
//                	convertChar[i] = c;
//                } else {
//                	String temp = t[0];
//                    if(type == Type.FIRSTUPPER) {
//                    	temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
//                    }
//                    convertChar[i] = temp;
//                }
//            }
//        }
//        return StringUtils.join(convertChar, spera);
//	}
//
//	/**
//	 * 拼音转换
//	 * @param str 转换的字符串
//	 * @param spera 字符之间的间隔符
//	 * @param type 转换的类型 com.vsvz.utils.pinyin.PinyinUtil.Type
//	 * @return
//	 * @throws BadHanyuPinyinOutputFormatCombination
//	 */
//	public String[] toPinYinAndFirst(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
//		if(StringUtils.isEmpty(str)) {
//			return null;
//		}
//		if(type != null && type == Type.UPPERCASE) {
//			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//		} else {
//			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//		}
//
//		String[] ret = new String[2];
//		Object[] convertChar = new Object[str.length()];
//		Object[] firstChar = new Object[str.length()];
//        for(int i=0; i<str.length(); i++){
//            char c = str.charAt(i);
//            if((int)c <= 128) {
//            	convertChar[i] = c;
//            } else {
//            	 String[] t = PinyinHelper.toHanyuPinyinStringArray(c, format);
//                if(t == null || t.length<1) {
//                	convertChar[i] = c;
//                } else {
//                	String temp = t[0];
//                    if(type == Type.FIRSTUPPER) {
//                    	temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
//                    }
//                    convertChar[i] = temp;
//                }
//            }
//            if(convertChar[i] != null && convertChar[i].toString().length()>0) {
//            	firstChar[i] = convertChar[i].toString().substring(0, 1);
//            }
//        }
//        ret[0] = StringUtils.join(convertChar, spera);
//        ret[1] = StringUtils.join(firstChar, spera);
//        return ret;
//	}
//
//	public static void main(String[] args) {
//		PinyinUtil py = new PinyinUtil();
//		System.out.println(py.toPinYin("汉字转换拼音测试123+-/*!@#￥%%……&*()——+abcdefghijkmnopq"));
//	}
//}
