package com.cxy.utils.excel_cjdg;

import java.lang.reflect.Field;

/**
 * javabean类型格式化器
 *
 * @author wangpeng
 * @date 2018/6/15
 */
public interface FormatterOfBean {

    /**
     * 对值进行格式化输出
     *
     * @param value 当前值
     * @param fields 字段数组，当前值依赖其他值时使用
     * @param t 数据对象
     * @return
     */
    <T> String format(Object value, Field[] fields, T t);
}
