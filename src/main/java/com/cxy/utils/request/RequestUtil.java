package com.cxy.utils.request;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName RequestUtil.java
 * @Description request工具类
 * @createTime 2020年12月14日 12:52:00
 */
public class RequestUtil {

    /**
     * 通过body获取实体集合
     *
     * @return
     * @aram clazz
     */
//    private static <T> List<T> getListByBody(Class<T> clazz) {
//        HttpServletRequest request = ActionContext.getActionContext().getHttpServletRequest();
//        // 获取body中的json参数
//        try {
//            BufferedReader bufferReader = new BufferedReader(request.getReader());
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = bufferReader.readLine()) != null) {
//                sb.append(line);
//            }
//            String s = sb.toString();
//            JSONArray jsonArray = JSONArray.fromObject(s);
//            T[] ts = (T[]) JSONArray.toArray(jsonArray, clazz);
//            return Arrays.asList(ts);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
