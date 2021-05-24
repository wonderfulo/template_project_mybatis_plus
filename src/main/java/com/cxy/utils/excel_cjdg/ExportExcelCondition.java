//package com.cxy.utils.excel_cjdg;
//
//import java.util.Collection;
//
///**
// * 导出excel约束条件封装
// *
// * @author wangpeng
// * @date 2018/6/14
// */
//public class ExportExcelCondition<T> {
//
//    /**
//     * javaBean类型
//     */
//    public static final ExportExcelDataType BEAN = ExportExcelDataType.BEAN;
//
//    /**
//     * 字符串数组类型
//     */
//    public static final ExportExcelDataType ARRAY = ExportExcelDataType.ARRAY;
//
//    private static int IMG_COLUMN_WIDTH = 20 * 256;
//
//    private static short IMG_ROW_HEIGHT = 100 * 20;
//
//    private static int IMG_WIDTH = 120;
//
//    private static int IMG_HEIGHT = 120;
//
//    /**
//     * excel导出版本，默认为2007
//     */
//    private ExcelVersion version = ExcelVersion.VERSION_2007;
//
//    /**
//     * 按什么数据格式导出
//     */
//    private ExportExcelDataType type;
//
//    /**
//     * 表头
//     */
//    private String[] headers;
//
//    /**
//     * 数据
//     */
//    private Collection<T> data;
//
//    /**
//     * 字段控制对象
//     */
//    private FieldControlObject[] fieldControlObjects;
//
//    /**
//     * 导出excel操作类型
//     *
//     * @author wangpeng
//     * @date 2018/6/14
//     */
//    private enum ExportExcelDataType {
//
//        /**
//         * javaBean类型
//         */
//        BEAN,
//        /**
//         * 对象数组类型
//         */
//        ARRAY
//    }
//
//    /**
//     * 设置图片列宽度
//     *
//     * @param width 宽度
//     */
//    public static void setImgColumnWidth(int width) {
//        IMG_COLUMN_WIDTH = width;
//    }
//
//    /**
//     * 获取图片列宽度
//     *
//     * @return
//     */
//    public static int getImgColumnWidth() {
//        return IMG_COLUMN_WIDTH;
//    }
//
//    /**
//     * 设置图片行高度
//     *
//     * @param height 高度
//     */
//    public static void setImgRowHeight(short height) {
//        IMG_ROW_HEIGHT = height;
//    }
//
//    /**
//     * 获取图片行高度
//     *
//     * @return
//     */
//    public static short getImgRowHeight() {
//        return IMG_ROW_HEIGHT;
//    }
//
//    /**
//     * 获取图片宽度
//     *
//     * @return
//     */
//    public static int getImgWidth() {
//        return IMG_WIDTH;
//    }
//
//    /**
//     * 获取图片高度
//     *
//     * @return
//     */
//    public static int getImgHeight() {
//        return IMG_HEIGHT;
//    }
//
//    /**
//     * 使用2003版本导出
//     */
//    public void useVersion2003() {
//        this.version = ExcelVersion.VERSION_2003;
//    }
//
//    public ExcelVersion getVersion() {
//        return version;
//    }
//
//    public ExportExcelDataType getType() {
//        return type;
//    }
//
//    public void setType(ExportExcelDataType type) {
//        this.type = type;
//    }
//
//    public String[] getHeaders() {
//        return headers;
//    }
//
//    public void setHeaders(String[] headers) {
//        this.headers = headers;
//    }
//
//    public Collection<T> getData() {
//        return data;
//    }
//
//    public void setData(Collection<T> data) {
//        this.data = data;
//    }
//
//    public FieldControlObject[] getFieldControlObjects() {
//        return fieldControlObjects;
//    }
//
//    public void setFieldControlObjects(FieldControlObject[] fieldControlObjects) {
//        this.fieldControlObjects = fieldControlObjects;
//    }
//}
