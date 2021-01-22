package com.cxy.utils.number;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName NumberUtil.java
 * @Description
 * @createTime 2021年01月13日 17:26:00
 */
public class NumberUtil {

    /**
     * 负数和整数
     */
    public static String REG_NUMBER01 = "^[-]?[0-9]+$";

    private static Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");

    public static double str2double(String str) {
        if(str==null||"".equals(str)){
            return 0D;
        }
        return Double.valueOf(str);
    }

    public static long longValue(Long numObj) {
        if (numObj == null) {
            return 0;
        }
        return numObj.longValue();
    }

    public static long bigint2long(Object obj) {
        if (obj == null) {
            return 0;
        }
        return ((BigInteger) obj).longValue();
    }

    public static long bigint2Float(Object obj) {
        if (obj == null) {
            return 0;
        }
        return ((Float) obj).longValue();
    }

    public static int bigint2int(Object obj) {
        if (obj == null) {
            return 0;
        }
        return ((BigInteger) obj).intValue();
    }

    public static int obj2int(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof String && "".equals(obj.toString())) {
            return 0;
        }
        return Integer.valueOf(obj.toString()).intValue();
    }

    public static int byte2int(Object obj) {
        if (obj == null) {
            return 0;
        }
        return ((Byte) obj).intValue();
    }

    public static float floatValue(Float num) {
        if (num == null) {
            return 0;
        }
        return num.floatValue();
    }

    public static int str2int(String str) {
        if (str == null || "".equals(str)) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    public static Integer str2Integer(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        return Integer.parseInt(str);
    }

    public static long str2long(String str) {
        if (str == null || "".equals(str)) {
            return 0L;
        }
        return Long.parseLong(str);
    }

    public static Long str2Long(String str) {
        if (str == null || "".equals(str)) {
            return 0L;
        }
        return Long.valueOf(str);
    }

    public static Long strToLong(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        return Long.valueOf(str);
    }

    public static Float Obj2Float(Object obj) {
        if (obj == null) {
            return 0f;
        }
        return Float.valueOf(obj.toString());
    }

    public static String Obj2Str(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
    public static String Obj2String(Object obj) {
        if(obj == null) {
            return "";
        }
        return obj.toString();
    }
    public static Long obj2Long(Object obj) {
        if (obj == null || "".equals(obj)) {
            return null;
        }
        return Long.parseLong(obj.toString());
    }

    public static long obj2long(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        return Long.parseLong(obj.toString());
    }

    public static Integer obj2Integer(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String && "".equals(obj.toString())) {
            return null;
        }
        return Integer.parseInt(obj.toString());
    }

    public static Float obj2Float(Object obj) {
        if (obj == null) {
            return null;
        }
        return Float.parseFloat(obj.toString());
    }

    public static Double obj2Double(Object obj) {
        if (obj == null) {
            return null;
        }
        return Double.parseDouble(obj.toString());
    }

    public static float floatFormat(float num, int scale) {
        BigDecimal b = new BigDecimal(num);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为数字/小数
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        return str.matches("-?[0-9]+.*[0-9]*");
    }

    /**
     * 判断字符串是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChina(String str) {
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String trimLastChar(String str) {
        if(null == str){
            return "";
        }
        if(str.lastIndexOf(",")==str.length()-1){
            return str.substring(0,str.length()-1);
        }
        return str;
    }

    public static Boolean obj2Boolean(Object obj) {
        if (obj == null) {
            return false;
        }
        return Boolean.valueOf(obj.toString());
    }

    /**
     * 去除隐藏符号
     * @param val
     * @return
     */
    public static String removeHiddenSymbols(String val) {
        if(StringUtils.isNotEmpty(val)) {
            val = val.replaceAll("\\p{C}", "");
        }
        return val;
    }

    /**
     * 去除参数前后的空格
     * @param str
     * @return
     */
    public static String checkOrTrim(String str) {
        if(str == null){
            return null;
        }
        return str.trim();
    }


    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


}
