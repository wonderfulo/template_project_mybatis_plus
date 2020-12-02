package com.cxy.utils.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ：陈翔宇
 * @date ：2020/12/2 15:03
 * @description：加密工具类
 * @modified By：
 * @version: $
 * 加密类型：MD5、SHA-1
 */
public class EncryptionUtil {

    /**
     * 做MD5加密
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(str.getBytes());
            byte[] digesta = alga.digest();
            String digesta_last = byte2hex2(digesta);
            return digesta_last.toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 做SHA-1加密
     * @param password
     * @return
     */
    public static String getSHA(String password) {
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(password.getBytes());
            byte[] digesta = alga.digest();
            String digesta_last = byte2hex2(digesta);
            return digesta_last;
        }
        catch (NoSuchAlgorithmException ex) {
        }
        return "";
    }
    /**
     * 二进制转化成16进制表示，每个数字之间加入":"作为分割
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) { //二行制转字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
            if (n < b.length - 1) hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    /**
     * 二进制转化成16进制表示
     * @param b
     * @return
     */
    private static String byte2hex2(byte[] b) { //二行制转字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
        }
        return hs;
    }

    public static void main(String[] args){
        System.out.println(EncryptionUtil.getMD5("vsvz_"));
    }
}
