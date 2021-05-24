package com.cxy.excel_ali.test.core.handler;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class WriteHandlerData {
    @ExcelProperty("姓名")
    private String name;
}
