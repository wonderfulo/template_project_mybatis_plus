package com.cxy.excel_ali.test.core.simple;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class SimpleData {
    @ExcelProperty("姓名")
    private String name;
}
