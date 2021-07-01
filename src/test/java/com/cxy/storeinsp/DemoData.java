//package com.cxy.storeinsp;
//
//import com.alibaba.excel.annotation.ExcelProperty;
//import lombok.Builder;
//import lombok.Data;
//
//@Data
//@Builder
//public class DemoData {
//
//    @ExcelProperty("报告名称")
//    private String code;
//    @ExcelProperty("检查表")
//    private String templateName;
//    @ExcelProperty("巡店总结")
//    private String summary;
//    @ExcelProperty("店铺编码")
//    private String shopCode;
//    @ExcelProperty("店铺名称")
//    private String shopName;
//    @ExcelProperty("发起人")
//    private String inspectorName;
//    @ExcelProperty("接收人")
//    private String receiverName;
//    @ExcelProperty("创建时间")
//    private String createTime;
//    @ExcelProperty("得分")
//    private String score;
//    @ExcelProperty("满分")
//    private String sourceScore;
//    @ExcelProperty("得分率")
//    private String scoreRate;
//    @ExcelProperty("分类")
//    private String groupName;
//    @ExcelProperty("检查项")
//    private String inspElementName;
//    @ExcelProperty("检查项标准描述")
//    private String inspElementDesc;
//    @ExcelProperty("问题描述")
//    private String comments;
//    @ExcelProperty("问题接收人")
//    private String jobReceiver;
//    @ExcelProperty("是否适用")
//    private String isAdaptation;
//    @ExcelProperty("满分（检查项）")
//    private String eachSourceScore;
//    @ExcelProperty("得分（检查项）")
//    private String eachScore;
//    @ExcelProperty(value = "图片1", index = 19)
//    private String img1;
//    @ExcelProperty(value = "图片2", index = 20)
//    private String img2;
//    @ExcelProperty(value = "图片3", index = 21)
//    private String img3;
//    @ExcelProperty(value = "图片4", index = 22)
//    private String img4;
//    @ExcelProperty(value = "图片5", index = 23)
//    private String img5;
//    @ExcelProperty(value = "图片6", index = 24)
//    private String img6;
//    @ExcelProperty(value = "图片7", index = 25)
//    private String img7;
//    @ExcelProperty(value = "图片8", index = 26)
//    private String img8;
//    @ExcelProperty(value = "图片9", index = 27)
//    private String img9;
//    /**
//     * 如果string类型 必须指定转换器，string默认转换成string
//     */
//    @ExcelProperty(value = "视频", index = 28)
//    private String video;
//    @ExcelProperty("一票否决")
//    private String isOneVoteVeto;
////
////    // 字符串的头背景设置成粉红 IndexedColors.PINK.getIndex()
////    @HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 14)
////    // 字符串的头字体设置成20
////    @HeadFontStyle(fontHeightInPoints = 30)
////    // 字符串的内容的背景设置成天蓝 IndexedColors.SKY_BLUE.getIndex()
////    @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 40)
////    // 字符串的内容字体设置成20
////    @ContentFontStyle(fontHeightInPoints = 30,underline=1)
////    @ExcelProperty("日期标题")
////    private Date date;
////    @ExcelProperty("数字标题")
////    private Double doubleData;
////    /**
////     * 忽略这个字段
////     */
////    @ExcelIgnore
////    private String ignore;
//}