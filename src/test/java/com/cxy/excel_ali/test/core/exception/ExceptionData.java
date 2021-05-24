package com.cxy.excel_ali.test.core.exception;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class ExceptionData {
    @ExcelProperty("姓名")
    private String name;
}
