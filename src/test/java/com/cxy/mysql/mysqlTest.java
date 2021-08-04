package com.cxy.mysql;

import com.alibaba.fastjson.JSON;
import com.cxy.util.NumberUtils;
import com.cxy.utils.hump.HumpUtil;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName mysqlTest.java
 * @Description mysqlTest
 * @createTime 2021年04月17日 10:25:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class mysqlTest {


    @Test
    public void createMysqlComment() {
        try {
			/*Class.forName("com.mysql.jdbc.Driver");

			String conString="jdbc:mysql://localhost:3306/student_db";
			Connection connection=(Connection) DriverManager.getConnection(conString,"root","");
			Statement statement=(Statement) connection.createStatement();
			boolean resultSet=statement.execute("insert into student\r\n" +
					"VALUES(\"201215123\",\"李五\",\"男\",18,\"cs\");");*/
			/*while (resultSet.next()) {
				System.out.println(resultSet.getString("Sname"));

			}*/
            /*connection.close();*/
            DriverManager.deregisterDriver(new com.mysql.jdbc.Driver());
            Connection connection=(Connection) DriverManager.getConnection("jdbc:mysql://211.159.215.254:61499/share_center","root","cjdg_CesH1-2OO!");
            PreparedStatement preparedStatement=(PreparedStatement) connection.prepareStatement("show full fields from material");
            ResultSet resultSet=preparedStatement.executeQuery();
            HashMap<Object, Object> map = new LinkedHashMap<>();
            while (resultSet.next()){
                String field = resultSet.getString("Field");
                String comment = resultSet.getString("Comment");
                String key = HumpUtil.lineToHump(field);
                String value = HumpUtil.lineToHump(comment);
                map.put(key,value);
            }
            String s = JSON.toJSONString(map);
            System.out.println(s);
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * sql写法，给表追加临时列
     * 	select * from om_pk_index i
     * 	JOIN ( SELECT 315287597 AS user_id FROM DUAL ) a
     */


}
