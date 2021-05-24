package com.cxy.excel_ali.test.core.parameter;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class ParameterData {
    @ExcelProperty("姓名")
    private String name;
}
