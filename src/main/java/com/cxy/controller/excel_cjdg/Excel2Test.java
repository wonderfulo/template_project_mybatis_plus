//package com.cxy.controller.excel_cjdg;
//
//import com.cxy.entity.User;
//import com.cxy.utils.excel_cjdg.*;
//import io.swagger.annotations.Api;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//
//
///**
// * @author 陈翔宇
// * @version 1.0.0
// * @ClassName excel2Test.java
// * @Description
// * @createTime 2021年03月30日 11:46:00
// */
//@Api("spring权限控制测试")
//@RestController
//@RequestMapping("/excel2Test")
//public class Excel2Test {
//
//    @RequestMapping("/excel2Test")
//    public void excel2Test(HttpServletResponse response) {
//        ArrayList<User> users = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            User user = new User();
//            user.setUserName("userName" + i);
//            user.setEmail("email" + i);
//            users.add(user);
//        }
//
//        //创建导出条件
//        ExportExcelCondition<User> condition = new ExportExcelCondition<>();
//        //导出操作类型，根据javaBean格式导出
//        condition.setType(ExportExcelCondition.BEAN);
//
//        String[] headers = {"名称", "电子邮件"};
//        condition.setHeaders(headers);
//
//        condition.setData(users);
//
//        String[] fieldNames = {"userName", "email"};
//
//        ExportExcelCellType[] fieldTypes = null;
//        fieldTypes = new ExportExcelCellType[]{
//                ExportExcelCellType.RICH_TEXT_STRING,
//                ExportExcelCellType.RICH_TEXT_STRING
//        };
//        FieldControlObject[] fieldControlObjects = new FieldControlObject[fieldNames.length];
//
////        for (int i = 0; i < length; i++) {
////            FieldControlObject fieldControlObject = new FieldControlObject(fieldNames[i], fieldTypes[i]);
////            if (i > 19 ) {
////                fieldControlObject.setIndex(i - 19);
////            }
////            if(i == 10 ){
////                fieldControlObject.setFormatterOfBean(new FormatterOfBean() {
////                    @Override
////                    public <T> String format(Object value, Field[] fields, T t) {
////                        Field score = ExportExcelUtils.getFieldByName(fields, "score");
////                        Field sourceScore = ExportExcelUtils.getFieldByName(fields, "sourceScore");
////                        String  r= "0.00%";
////                        try {
////                            if(score != null && sourceScore != null){
////                                String score1 = (String) score.get(t);
////                                String sourceScore2 = (String) sourceScore.get(t);
////                                DecimalFormat df = new DecimalFormat("0.00%");
////                                if (StringUtils.isNotEmpty(sourceScore2)){
////                                    r = df.format(Float.parseFloat(score1)/Float.parseFloat(sourceScore2));
////                                }
////                            }
////                        } catch (IllegalAccessException e) {
////                            throw new RatelRuntimeException("计算得分率失败");
////                        }
////                        return r;
////                    }
////                });
////
////            }
////            //处理接受者
////            if(i == 15 ){
////                fieldControlObject.setFormatterOfBean(new FormatterOfBean() {
////                    @Override
////                    public <T> String format(Object value, Field[] fields, T t) {
////                        return (String) groupUser.getOrDefault((String)value,(String)value);
////                    }
////                });
////
////            }
////            fieldControlObjects[i] = fieldControlObject;
////        }
//
//        for (int i = 0; i < fieldNames.length; i++) {
//            FieldControlObject fieldControlObject = new FieldControlObject(fieldNames[i], fieldTypes[i]);
//            fieldControlObjects[i] = fieldControlObject;
//        }
//
//        condition.setFieldControlObjects(fieldControlObjects);
//        try {
//            ExportExcelUtils.exportOneSheet("测试", condition, response);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//}