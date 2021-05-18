package com.cxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxy.common.JsonResponse;
import com.cxy.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName cxyTest.java
 * @Description
 * @createTime 2021年03月04日 14:01:00
 */
public class cxyTest {

    public static void main(String[] args) throws ParseException {

        LocalDateTime today = LocalDateTime.now();
        System.out.println("今天⽇期：" + today);
        //获取年，⽉，⽇，周⼏
        System.out.println("现在是哪年:"+today.getYear());
        System.out.println("现在是哪⽉:"+today.getMonth());
        System.out.println("现在是哪⽉(数字):"+today.getMonthValue());
        System.out.println("现在是⼏号:"+today.getDayOfMonth());
        System.out.println("现在是周⼏:"+today.getDayOfWeek());
        circulationDay(today);
//        String strDate = "2021-05-18 00:00:00";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = simpleDateFormat.parse(strDate);
//        for (int i = 0; i < 10000; i++) {
//            date = getNextCycleWeek(date, null, 1);
//            System.out.println(simpleDateFormat.format(date));
//        }
    }

    private static void circulationDay(LocalDateTime today) {
        if (today.getYear() <= 2100){
            LocalDateTime localDateTime = today.plusDays(7);
            System.out.println(localDateTime.toString());
            circulationDay(localDateTime);
        }
    }

    //获取下个周期调度日期(周)
    public static Date getNextCycleWeek(Date startDate, Integer getTriggerDay, Integer everyNum) {
        Calendar now = Calendar.getInstance();
        now.setTime(startDate);
        Integer step = everyNum * 7;
        now.add(Calendar.DAY_OF_MONTH, step);
        Date m = now.getTime();
        return m;
    }


    static class A{
        String name ;
        Integer age;
        boolean IsDelete;

        A build(String name){
            this.name = name;
            return this;
        }
        A build(Integer age){
            this.age = age;
            return this;
        }
        A build(boolean IsDelete){
            this.IsDelete = IsDelete;
            return this;
        }

        public A() {
        }
    }
}
