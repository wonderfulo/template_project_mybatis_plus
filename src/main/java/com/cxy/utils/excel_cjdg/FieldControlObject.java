//package com.cxy.utils.excel_cjdg;
//
///**
// * 字段控制对象
// *
// * @author wangpeng
// * @date 2018/6/25
// */
//public class FieldControlObject {
//
//    /**
//     * field名称
//     */
//    private String fieldName;
//    /**
//     * excel单元格导出类型
//     */
//    private ExportExcelCellType fieldType;
//    /**
//     * excel单元格导出格式化，日期格式或正则格式
//     */
//    private String pattern = "yyyy-MM-dd HH:mm:ss";
//    /**
//     * 字符串分隔符
//     */
//    private String separatorChars = ",";
//    /**
//     * 字符串包含多项内容时所处的位置，从0开始
//     */
//    private int index;
//    /**
//     * javaBean类型字段格式化器
//     */
//    private FormatterOfBean formatterOfBean;
//
//    public FieldControlObject(String fieldName, ExportExcelCellType fieldType) {
//        this.fieldName = fieldName;
//        this.fieldType = fieldType;
//    }
//
//    public String getFieldName() {
//        return fieldName;
//    }
//
//    public ExportExcelCellType getFieldType() {
//        return fieldType;
//    }
//
//    public String getPattern() {
//        return pattern;
//    }
//
//    public void setFieldName(String fieldName) {
//        this.fieldName = fieldName;
//    }
//
//    public void setFieldType(ExportExcelCellType fieldType) {
//        this.fieldType = fieldType;
//    }
//
//    public void setPattern(String pattern) {
//        this.pattern = pattern;
//    }
//
//    public String getSeparatorChars() {
//        return separatorChars;
//    }
//
//    public void setSeparatorChars(String separatorChars) {
//        this.separatorChars = separatorChars;
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }
//
//    public FormatterOfBean getFormatterOfBean() {
//        return formatterOfBean;
//    }
//
//    public void setFormatterOfBean(FormatterOfBean formatterOfBean) {
//        this.formatterOfBean = formatterOfBean;
//    }
//}
