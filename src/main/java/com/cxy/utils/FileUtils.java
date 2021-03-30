package com.cxy.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件处理工具类
 *
 * @author wangpeng
 * @date 2018/6/20
 */
public class FileUtils {

    /**
     * 网络超时时间
     */
    private static final int NET_TIME_OUT = 5 * 1000;

    /**
     * 根据url获取网络文件字节数组
     *
     * @param strUrl 文件url
     * @return
     */
    public static byte[] getByteFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //超时时间
            conn.setConnectTimeout(NET_TIME_OUT);
            //通过输入流获取文件数据
            InputStream inStream = conn.getInputStream();
            //得到文件的二进制数据
            return readInputStream(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据路径获取本地文件字节数组
     *
     * @param strUrl
     * @return
     */
    public static byte[] getByteFromLocalByUrl(String strUrl) {
        try {
            File imageFile = new File(strUrl);
            InputStream inStream = new FileInputStream(imageFile);
            //得到文件的二进制数据
            return readInputStream(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
