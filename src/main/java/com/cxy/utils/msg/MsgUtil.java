package com.cxy.utils.msg;

import com.alibaba.fastjson.JSONObject;
import com.cxy.utils.bean.AppUtil;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName MsgUtil.java
 * @Description 消息工具类
 * @createTime 2021年05月14日 09:45:00
 */
public class MsgUtil {

    public final static String EXCEPTION_MSG_PREFIX = "exception";

    public static String getExceptionMsgByCode(String code){
        if(!code.startsWith(EXCEPTION_MSG_PREFIX)){
            return getMessageByCode(EXCEPTION_MSG_PREFIX + "." + code);
        }
        return getMessageByCode(code);
    }

    public static String getMessageByCode(String code){
        MessageSource source;
        try {
            source = AppUtil.getCtx().getBean(MessageSource.class);
        } catch (Exception e) {
            return code;
        }
        String msg =  source.getMessage(code, null , Locale.ROOT);
        if(msg == null || "".equals(msg)) {
            msg = code;
        }
        return msg;
    }

    /**
     * 发送消息 text/html;charset=utf-8
     * @param response
     * @param str
     * @throws Exception
     */
    public static void sendMessage(HttpServletResponse response, String str) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(str);
        writer.close();
        response.flushBuffer();
    }

    /**
     * 将某个对象转换成json格式并发送到客户端
     * @param response
     * @param obj
     * @throws Exception
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj));
        writer.close();
        response.flushBuffer();
    }


}
