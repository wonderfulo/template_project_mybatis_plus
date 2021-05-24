package com.cxy.excel_ali.test.core.skip;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class SkipData {

    @ExcelProperty("姓名")
    private String name;
}
