package com.cxy.util;

public class HashUtil {

    /**
     * 计算hash值
     * @param val
     * @return
     */
    public static int toHash(String val) {
        // 数组大小一般取质数
        int arraySize = 10000000;
        int hashCode = 0;
        for (int i = 0; i < val.length(); i++) {
            // 将获取到的字符串转换成数字，比如a的码值是97，则97-96=1
            int letterValue = val.charAt(i) - 96;
            // 防止编码溢出，对每步结果都进行取模运算
            hashCode = ((hashCode << 5) + letterValue) % arraySize;
        }
        return hashCode;
    }

}