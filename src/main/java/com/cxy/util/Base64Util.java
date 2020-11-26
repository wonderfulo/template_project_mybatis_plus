package com.cxy.util;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Base64编解码工具类
 * @author zhangchunxing
 *
 */
public class Base64Util {
    
   /**
    * URL安全的Base64编码。
    * 
    * 注：URL安全的Base64编码适用于以URL方式传递Base64编码结果的场景。
    * 该编码方式的基本过程是先将内容以Base64格式编码为字符串，
    * 然后检查该结果字符串，将字符串中的加号+换成中划线-，并且将斜杠/换成下划线_。
    *               ----七牛
    * 
    * @param str 字符串
    * @param charset 字符集
    * @return
    */
    public static String encodeToStringForQiNiu(String str, Charset charset) {
       return Base64.getEncoder().encodeToString(str.getBytes(charset)).replace("+", "-").replace("/", "_");
    }

}
