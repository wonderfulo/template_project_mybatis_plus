package com.cxy.random;

import com.cxy.utils.date.DateUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@SpringBootTest
public class RandomTest {

    @Test
    public void appId() throws ParseException {
        // code格式： 39097663-c00b-4ede-8286-09db2187bcc8
        String code = UUID.randomUUID().toString();
        long appraiseId = DateUtil.getNowUtilDate().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(appraiseId);
        System.out.println("");
    }
}