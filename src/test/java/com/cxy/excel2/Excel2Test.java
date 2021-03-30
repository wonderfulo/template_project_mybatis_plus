package com.cxy.excel2;

import com.cxy.entity.User;
import com.cxy.utils.excel2.ExportExcelCellType;
import com.cxy.utils.excel2.ExportExcelCondition;
import com.cxy.utils.excel2.ExportExcelUtils;
import com.cxy.utils.excel2.FieldControlObject;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName excel2Test.java
 * @Description
 * @createTime 2021年03月30日 11:46:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class Excel2Test {

    @Test
    public void excel2Test() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUserName("userName" + i);
            user.setEmail("email" + i);
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
        condition.setFieldControlObjects(fieldControlObjects);
//        ExportExcelUtils.exportOneSheet("测试", condition, response);
    }
}
