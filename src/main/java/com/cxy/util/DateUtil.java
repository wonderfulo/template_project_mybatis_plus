package com.cxy.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
	/**
	 * timestamp格式转换 util格式
	 * 
	 * @param date
	 * @return
	 */
	public static Date changeTimestamp(Timestamp date) {
		String time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null != date && !date.equals("")) {
			// 将Timestamp型时间转化成Date型
			Date d = new Date(date.getTime());
			return d;
		} else {
			return null;
		}

	}

	/**
	 * 字符串转化时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date formatStringToUtilDate(String str) {
		Date date = null;
		if (str == null || "".equals(str)) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage());
		}

		return date;
	}

	/**
	 * 字符串转化时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date formatStringToUtilDate_2(String str) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

		try {
			// date = sdf.parse(str);
			date = (Date) sdf.parseObject(str);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		return date;
	}

	/**
	 * 字符串转化时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date formatStringToDate(String str) {
		Date date = null;
		if (str == null || "".equals(str)) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		return date;
	}
	
	
	/**
	 * 字符串转化时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date formatStringToDateWithCheck(String str) {
		Date date = null;
		if (str == null || "".equals(str)) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(str.length()>10)
			sdf=  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		return date;
	}
	
	
	public static Date formatStringToDateYM(String str) {
		Date date = null;
		if (str == null || "".equals(str)) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		return date;
	}
	
	/**
	 * 字符串转化时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date formatStringToDateYm(String str) {
		Date date = null;
		if (str == null || "".equals(str)) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		return date;
	}

	/**
	 * 日期格式化 yyyy-MM-dd hh:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 日期格式化 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTimeTwo(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	
	/**
	 * 日期格式化 yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTimeToStringNoJoin(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
	
	
	/**
	 * 日期格式化 yyyy/MM/dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTimeThree(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 日期格式化 yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
	/**
	 * 日期格式化 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate2(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 日期格式化 yyyy.MM.dd
	 *
	 * @param date
	 * @return
	 */
	public static String formatDate3(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(date);
	}
	
	/**
	 * 字符串转化时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date formatStringToUtilDate2(String str) {
		Date date = null;
		if (str == null || "".equals(str)) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			date = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage());
		}

		return date;
	}
	
	/**
	 * 日期格式化 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}

	/**
	 * 得到当前的时间
	 * 
	 * @return utildate
	 */
	public static Date getNowUtilDate() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	/**
	 * String(yyyy-MM-dd HH:mm:ss)转10位时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static Integer StringToTimestamp(String time) {

		int times = 0;
		try {
			times = (int) ((Timestamp.valueOf(time).getTime()) / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (times == 0) {
			System.out.println("String转10位时间戳失败");
		}
		return times;

	}

	/**
	 * 10位int型的时间戳转换为String(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param time
	 * @return
	 */
	public static String timestampToString(Integer time) {
		// int转long时，先进行转型再进行计算，否则会是计算结束后在转型
		long temp = (long) time * 1000;
		Timestamp ts = new Timestamp(temp);
		String tsStr = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			// 方法一
			tsStr = dateFormat.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}

	/**
	 * 10位时间戳转Date
	 * 
	 * @param time
	 * @return
	 */
	public static Date TimestampToDate(Integer time) {
		long temp = (long) time * 1000;
		Timestamp ts = new Timestamp(temp);
		Date date = new Date();
		try {
			date = ts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	 public static StringBuffer timeDifference(Date date_1,Date date_2){
	    	StringBuffer date_str = new StringBuffer();
	    	try{
	    		long l = date_1.getTime() - date_2.getTime();
	    		long day = l/(24*60*60*1000);
	    		long hour = l/(60*60*1000)-day*24;
	    		long min = ((l/(60*1000))-day*24*60-hour*60);
	    		long sec = (l/1000-day*24*60*60-hour*60*60-min*60);
	    		if(day > 0){
	    			date_str.append(day);
	    			date_str.append("天");
	    		}
	    		if(hour > 0){
	    			date_str.append(hour);
	    			date_str.append("小时");
	    		}
	    		date_str.append(min);
				date_str.append("分");
				date_str.append(sec);
				date_str.append("秒");
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return date_str;
	    }
	/**
	 * 时间做比较，date_1 > date_2 返回 1，date_1 == date_2 返回 0 ，date_1 < date_2 返回 -1*/
	 public static int timeCompare(Date date_1,Date date_2){
		 	int comp_res = 0;
	    	try{
	    		long time_1 =  date_1.getTime();
	    		long time_2 =  date_2.getTime();
	    		if(time_1 > time_2){
	    			comp_res = 1;
	    		}else if(time_1 == time_2){
	    			comp_res = 0;
	    		}else{
	    			comp_res = -1;
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return comp_res;
	    }
	 
	 public static Date getPreDateByTerm(String term,Date curr_date){
		 	Calendar cal = Calendar.getInstance();
		 	cal.setTime(curr_date);
		 	if("d".equals(term)){
		 		cal.set(Calendar.MINUTE, 0);
		 		cal.set(Calendar.SECOND, 0);
		 		cal.set(Calendar.MILLISECOND, 0);
		 		cal.set(Calendar.HOUR_OF_DAY, 0);
		 	}else if("h".equals(term)){
		 		cal.add(Calendar.HOUR_OF_DAY, -1);
		 	}
	        return cal.getTime();
	 }
	 
	 /** 
	    * 获取过去第几天的日期 
	    * 
	    * @param past 
	    * @return 
	    */  
	public static Date getPastDate(int past) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
		Date today = calendar.getTime();  
		return today;  
	}  
	
	/**
	 * 获取今天的开始时间
	 * @return
	 */
	public static Date getBeginNowDate() {
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.HOUR_OF_DAY, 0);				// 设置小时为0
		calendar.set(Calendar.MINUTE, 0);					// 设置分钟为0
		calendar.set(Calendar.SECOND, 0);					// 设置秒为0
		calendar.set(Calendar.MILLISECOND, 0);				// 设置毫秒为0
		return calendar.getTime();
	}
	
	/**
	 * 获取今天的结束时间
	 * @return
	 */
	public static Date getEndNowDate() {
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.HOUR_OF_DAY, 23);				// 设置小时为23
		calendar.set(Calendar.MINUTE, 59);					// 设置分钟为59
		calendar.set(Calendar.SECOND, 59);					// 设置秒为59
		calendar.set(Calendar.MILLISECOND, 999);			// 设置毫秒为999
		return calendar.getTime();
	}
	
	public static Date getNext20Years() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+20);
		calendar.set(Calendar.DAY_OF_MONTH, 1);				// 设置日为1
		calendar.set(Calendar.HOUR_OF_DAY, 0);				// 设置小时为0
		calendar.set(Calendar.MINUTE, 0);					// 设置分钟为0
		calendar.set(Calendar.SECOND, 0);					// 设置秒为0
		calendar.set(Calendar.MILLISECOND, 0);				// 设置毫秒为0
		return calendar.getTime();
	}

	/**
	 * 获取当前小时的开始时间
	 *
	 * @return
	 */
	public static Date getBeginNowHourDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当前小时的结束时间
	 *
	 * @return
	 */
	public static Date getEndNowHourDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}


	//计算开始时间与结束时间之间相差多少小时
	public static Long getDatePoor(Date endDate, Date startDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - startDate.getTime();
		// 计算差多少小时
		long hour = diff % nd / nh;
		return hour;
	}

	/**
	 * 获取指定时间的几个小时
	 * @param date
	 * @param hours
	 * @return
	 * @throws ParseException
	 */
	public static Date getBeforeHourTime(String date ,int hours) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - hours);
		return cal.getTime();
	}

}
