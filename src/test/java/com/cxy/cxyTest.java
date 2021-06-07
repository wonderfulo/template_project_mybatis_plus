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

    /**
     * 数组中找到  3数相加等于0的不同组合
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                return lists;
            }

            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int L = i + 1;
            int R = nums.length - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == 0) {
                    ArrayList<Integer> tempList = new ArrayList<>();
                    tempList.add(nums[L]);
                    tempList.add(nums[i]);
                    tempList.add(nums[R]);
                    lists.add(tempList);

                    while (L < R && nums[L] == nums[L + 1]) {
                        L++;
                    }
                    while (L < R && nums[R] == nums[R - 1]) {
                        R--;
                    }

                    L++;
                    R--;
                } else if (sum < 0) {
                    L++;
                } else {
                    R--;
                }
            }
        }

        return lists;
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
