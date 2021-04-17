package com.cxy.utils.hump;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName HumpUtil.java
 * @Description 驼峰工具类
 * @createTime 2021年04月17日 10:48:00
 */
public class HumpUtil {

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     */
    public static List<String> lineToHump(List headerList) {
        List<String> headerField = new ArrayList<>();
        for (int i = 0; i < headerList.size(); i++) {
            Object[] objects = (Object[]) headerList.get(i);
            String str = objects[1].toString().toLowerCase();
            Matcher matcher = linePattern.matcher(str);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            }
            matcher.appendTail(sb);
            headerField.add(sb.toString());
        }
        return headerField;
    }

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String string) {
            String str = string.toLowerCase();
            Matcher matcher = linePattern.matcher(str);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            }
            matcher.appendTail(sb);
        return sb.toString();
    }
}
