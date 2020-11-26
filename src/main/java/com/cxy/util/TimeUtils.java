package com.cxy.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Description 日期时间操作通用类
 * @Date 2020/5/19 11:38
 * @Author chen kang hua
 * @Version 1.0
 **/
public class TimeUtils {

    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_TIME_PATTERN = "yyyy-MM-dd";

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String ofDay = getStartOfDay(now);
        LocalDateTime appointTime = getAppointTime(-1L, ChronoUnit.DAYS);
        String dateTime = formatLocalDateTime(now, DEFAULT_TIME_PATTERN);
//        System.out.println(now);
//        System.out.println(ofDay);
        System.out.println(formatLocalDateTime(appointTime, DEFAULT_TIME_PATTERN));
//        System.out.println(dateTime);
    }

    // 获得某天最小时间 2020-05-19 00:00:00
    public static String getStartOfDay(LocalDateTime localDateTime) {
        LocalDateTime startTime = localDateTime.with(LocalTime.MIN);
        return formatLocalDateTime(startTime, DEFAULT_TIME_PATTERN);
    }

    // 获得某天最大时间 2020-05-19 23:59:59
    public static String getEndOfDay(LocalDateTime localDateTime) {
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return formatLocalDateTime(endOfDay, DEFAULT_TIME_PATTERN);
    }

    /**
     * 获取指定时间
     *
     * @param amountToAdd 时间跨度
     * @param chronoUnit  跨度时间单位
     * @return
     */
    public static LocalDateTime getAppointTime(Long amountToAdd, ChronoUnit chronoUnit) {
        return LocalDateTime.now()
                .plus(amountToAdd, chronoUnit);
    }

    /**
     * 格式化时间
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(pattern);
        return timeFormatter.format(localDateTime);
    }

    /**
     * localDate 转 Date
     *
     * @param localDate
     * @return
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * String 转 Date
     *
     * @param dateTime 需要转换的字符时间
     * @return
     * @throws ParseException
     */
    public static Date formatDate(String dateTime, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("时间格式化错误 错误参数:" + dateTime);
        }
    }

    /**
     * Date转String
     *
     * @param date 需要转换的时间
     * @return
     * @throws ParseException
     */
    public static String formatDate(Date date , String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("时间格式化错误 错误参数:" + date);
        }
    }

    /**
     * 把时间转换为：时分秒格式。如：11:04:05
     * @param second  传入单位 秒
     * @return
     */
    public static String getTimeString(Long second) {
        String secondString="0";
        String minutesString="0";
        String hoursString="0";
        //转换天数
        long days = second / 86400;
        //剩余秒数
        second = second % 86400;
        //转换小时数
        long hours = second / 3600;
        if(hours<10){
            hoursString=hoursString+hours;
        }else {
            hoursString= String.valueOf(hours);
        }
        //剩余秒数
        second = second % 3600;
        //转换分钟
        long minutes = second / 60;
        if(minutes<10){
            minutesString= minutesString+minutes;
        }else {
            minutesString= String.valueOf(minutes);
        }
        //剩余秒数
        second = second % 60;
        if(second<10){
            secondString= secondString+second;
        }else {
            secondString= String.valueOf(second);
        }
        if (0 < days){
            return days + "天"+hoursString+":"+minutesString+":"+secondString;
        }else {
            return hoursString+":"+minutesString+":"+secondString;
        }
    }
}
