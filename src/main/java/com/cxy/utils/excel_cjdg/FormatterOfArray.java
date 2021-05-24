package com.cxy.utils.excel_cjdg;

/**
 * 数组类型格式化器
 *
 * @author wangpeng
 * @date 2018/6/29
 */
public interface FormatterOfArray {

    /**
     * 对值进行格式化输出
     *
     * @param value
     * @param objects
     * @return
     */
    String format(Object value, Object[] objects);
}
