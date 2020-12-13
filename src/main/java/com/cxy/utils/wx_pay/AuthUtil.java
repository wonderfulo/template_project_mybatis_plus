package com.cxy.utils.wx_pay;

/**
 * @author: KOLO
 * @date:2018年12月19日 下午4:51:08
 * @version: 1.0
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthUtil {

    /*
    * WX_PAY_APPID: str = 'wx8e5c163cdb324a77'
      WX_PAY_MCHID: str = '1296277801'
      WX_PAY_APIKEY: str = '0979316bc7464fabcf09db2ba85bb5b2'
      * openid = "ohMwB5eHRCZ0qGIgfyyd6gvB7G88"
    *
    * */



//    public static final String APPID = "wx7dbb710e53c8d15e";
    //美鹿的appid
    public static final String APPID = "wx0399b4bed4bde860";
    public static final String MCHID = "1296277801";
    public static final String PATERNERKEY = "0979316bc7464fabcf09db2ba85bb5b2";
    public static final String CERTPATH = "wxpay/apiclient_cert.p12";


    public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException {
        JSONObject jsonObject = null;
        // 首先初始化HttpClient对象
        DefaultHttpClient client = new DefaultHttpClient();
        // 通过get方式进行提交
        HttpGet httpGet = new HttpGet(url);
        // 通过HTTPclient的execute方法进行发送请求
        HttpResponse response = client.execute(httpGet);
        // 从response里面拿自己想要的结果
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSON.parseObject(result);
        }
        // 把链接释放掉
        httpGet.releaseConnection();
        return jsonObject;
    }

    /**
     * @Title: getRequestIp
     * @Description: 获取用户的ip地址
     * @param:
     * @return:
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf(",") != -1) {
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
        return ip;
    }
}

