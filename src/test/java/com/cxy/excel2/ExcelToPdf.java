package com.cxy.excel2;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ExcelToPdf.java
 * @Description
 * @createTime 2021年04月01日 17:11:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class ExcelToPdf {

    @Test
    public void test(){
        Workbook wo = new HSSFWorkbook();
        String[][][] strings = new String[10][10][10];

    }
}
