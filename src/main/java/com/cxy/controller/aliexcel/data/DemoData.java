//package com.cxy.controller.excel.data;
//
//import com.alibaba.excel.annotation.ExcelIgnore;
//import com.alibaba.excel.annotation.ExcelProperty;
//import lombok.Data;
//
//import java.util.Date;
//
////Data失效了，test类报错
////@Data
//public class DemoData {
//    @ExcelProperty("字符串标题")
//    private String string;
//    @ExcelProperty("日期标题")
//    private Date date;
//    @ExcelProperty("数字标题")
//    private Double doubleData;
//    /**
//     * 忽略这个字段
//     */
//    @ExcelIgnore
//    private String ignore;
//
//
//    public String getString() {
//        return string;
//    }
//
//    public void setString(String string) {
//        this.string = string;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Double getDoubleData() {
//        return doubleData;
//    }
//
//    public void setDoubleData(Double doubleData) {
//        this.doubleData = doubleData;
//    }
//
//    public String getIgnore() {
//        return ignore;
//    }
//
//    public void setIgnore(String ignore) {
//        this.ignore = ignore;
//    }
//}