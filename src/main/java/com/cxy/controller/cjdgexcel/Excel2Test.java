package com.cxy.controller.cjdgexcel;

import com.cxy.entity.User;
import com.cxy.utils.excel2.*;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName excel2Test.java
 * @Description
 * @createTime 2021年03月30日 11:46:00
 */
@Api("spring权限控制测试")
@RestController
@RequestMapping("/excel2Test")
public class Excel2Test {

    @RequestMapping("/excel2Test")
    public void excel2Test(HttpServletResponse response) {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUserName("userName" + i);
            user.setEmail("email" + i);
            users.add(user);
        }

        //创建导出条件
        ExportExcelCondition<User> condition = new ExportExcelCondition<>();
        //导出操作类型，根据javaBean格式导出
        condition.setType(ExportExcelCondition.BEAN);

        String[] headers = {"名称", "电子邮件"};
        condition.setHeaders(headers);

        condition.setData(users);

        String[] fieldNames = {"userName", "email"};

        ExportExcelCellType[] fieldTypes = null;
        fieldTypes = new ExportExcelCellType[]{
                ExportExcelCellType.RICH_TEXT_STRING,
                ExportExcelCellType.RICH_TEXT_STRING
        };
        FieldControlObject[] fieldControlObjects = new FieldControlObject[fieldNames.length];

        for (int i = 0; i < fieldNames.length; i++) {
            FieldControlObject fieldControlObject = new FieldControlObject(fieldNames[i], fieldTypes[i]);
            fieldControlObjects[i] = fieldControlObject;
        }

        condition.setFieldControlObjects(fieldControlObjects);
        try {
            ExportExcelUtils.exportOneSheet("测试", condition, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}