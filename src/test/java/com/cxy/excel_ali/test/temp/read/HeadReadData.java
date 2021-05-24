package com.cxy.excel_ali.test.temp.read;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
@Data
public class HeadReadData {
    @ExcelProperty({"主标题","数据1"})
    private String h1;
    @ExcelProperty({"主标题", "数据2"})
    private String h2;
}
