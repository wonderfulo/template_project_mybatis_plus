package com.cxy.date;

import com.cxy.utils.date.DateUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@SpringBootTest
public class dateUtilTest {

    @Test
    public void appId() throws ParseException {
        // code格式： 39097663-c00b-4ede-8286-09db2187bcc8
        String code = UUID.randomUUID().toString();
        long appraiseId = DateUtil.getNowUtilDate().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(appraiseId);
        System.out.println(format);
    }


    @Test
    public void LocalDateTimeTest() throws ParseException {

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


    @Test
    public void timeTest(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar cld = Calendar.getInstance(Locale.CHINA);
//        cld.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
        cld.setTimeInMillis(System.currentTimeMillis());//当前时间

        cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//周一
        System.out.println(df.format(cld.getTime()));

        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//周日
        System.out.println(df.format(cld.getTime()));

        cld.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);//周二
        System.out.println(df.format(cld.getTime()));
    }


    private static final String week_unit = "week";
    private static final String month_unit = "month";
    @Test
    public void dispatchTime(){
        Date referenceTime = DateUtil.formatStringToDateWithCheck("2021-07-09 08:00:00");
        String  unit = week_unit;
        getNextDispatchTime(referenceTime, unit,4);
    }

    public Date getNextDispatchTime(Date referenceTime,String unit,int triggerDay) {

        if (week_unit.equals(unit)) {
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(referenceTime);
            //获得本周一的时间
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Date time = calendar.getTime();

            calendar.add(Calendar.DAY_OF_MONTH,triggerDay);
            time = calendar.getTime();

            //仅保留年月日
            Date referenceTimeTemp = DateUtil.formatStringToDateWithCheck(DateUtil.formatDate2(referenceTime));
            Date timeTemp = DateUtil.formatStringToDateWithCheck(DateUtil.formatDate2(time));

            

            if (timeTemp.before(referenceTimeTemp)){

            }else {
                return timeTemp;
            }
        }

        return null;
    }
}
