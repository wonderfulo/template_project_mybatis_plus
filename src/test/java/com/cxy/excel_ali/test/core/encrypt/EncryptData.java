package com.cxy.excel_ali.test.core.encrypt;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class EncryptData {
    @ExcelProperty("姓名")
    private String name;
}
