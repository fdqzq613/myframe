
package com.some.web.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * JDK版本：JDK1.6或更高
 * 
 */
public abstract class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	/**
	 * 转换long的时间为yyyyMMddHHmmss字符串
	 * @param value
	 * @return
	 * @author qzq
	 */
	  public static String changeLongDate2Str(String value){
			if (StringUtils.isEmpty(value)) {
				return "";
			}
			Date date = new Date(Long.valueOf(value));

			try {
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss");
				dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				return dateFm.format(date);
			} catch (Exception e) {
				throw new RuntimeException("时间转换错误:" + e.getMessage(), e);
			}
		}
	  /**
	   * 转换字符串为long格式
	   * @param value （yyyyMMddHHmmss）
	   * @return
	   * @author qzq
	   * @date 2016年10月13日 下午2:36:23
	   */
	  public static long changeStrDate2Log(String value){
			if (StringUtils.isEmpty(value)) {
				return 0;
			}

			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = null;
				try {
					date = format.parse(value);
				} catch (ParseException e) {
					e.printStackTrace();
					return -1;
				}
				return date.getTime();
			} catch (Exception e) {
				throw new RuntimeException("时间转换错误:" + e.getMessage(), e);
			}
		}
	/**
	 * 获取东八区的当前时间 格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 当前时间
	 */
	public static Date getCurrtDoneTime() {
		Date resultDate = new Date();
		try {
			Date date = new Date();
			SimpleDateFormat dateFm = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String dateStr = dateFm.format(date);
			resultDate = dateFm.parse(dateStr);
		} catch (Exception e) {
			throw new RuntimeException("时间转换错误:" + e.getMessage(), e);
		}
		return resultDate;
	}

	/**
	 * 获取东八区当前时间字符串 格式为 yyyyMMddHHmmss
	 * 
	 * @return 当前时间字符串
	 */
	public static String getCurrtDoneTimeString14() {
		return getCurrtDoneTimeString("yyyyMMddHHmmss");
	}
	/**
	 * 获取当前年 如2014
	 * @return
	 * @author qzq
	 */
	public static String getCurrtDoneYear() {
		Calendar cd = Calendar.getInstance();
		return String.valueOf(cd.get(Calendar.YEAR));
	}
	/**
	 * 获取当前月 如12
	 * @return
	 * @author qzq
	 */
	public static String getCurrtDoneMonth() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH)+1; 
		return FillUtils.zeroFill(String.valueOf(month), 2);
	}
	/**
	 * 获取当前日 如01
	 * @return
	 * @author qzq
	 */
	public static String getCurrtDoneDay() {
		Calendar cd = Calendar.getInstance();
		
		return FillUtils.zeroFill(String.valueOf(cd.get(Calendar.DATE)), 2);
	}

	/**
	 * 获取东八区当前时间字符串 格式为 yyyyMMdd
	 * 
	 * @return 当前时间字符串
	 */
	public static String getCurrtDoneTimeString8() {
		return getCurrtDoneTimeString("yyyyMMdd");
	}
	/**
	 * 获取东八区当前时间字符串 格式为 yyyy-MM-dd
	 * 
	 * @return 当前时间字符串
	 */
	public static String getCurrtDoneTimeBz8() {
		return getCurrtDoneTimeString("yyyy-MM-dd");
	}


	/**
	 * 获取回退时间字符串
	 * 
	 * @param backDay
	 * @return
	 * @author qzq
	 */
	public static String getBackDateString8(int backDay) {
		Date date = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, -backDay);
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd");
		dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFm.format(cd.getTime());
	}

	/**
	 * 获取指定日期回退时间字符串
	 * @param backDate
	 * @param backDay
	 * @return
	 * @author qzq
	 */
	public static String getBackDateString8(String backDate, int backDay) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = format.parse(backDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, -backDay);
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd");
		dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFm.format(cd.getTime());
	}
	/**
	 * 返回距离今天时间间隔
	 * @param startDateStr
	 * @return
	 * @author qzq
	 */
	public static int getDateSpanToday(String startDateStr){
		return getDateSpan(getCurrtDoneTimeString8(),startDateStr);
	}
	/**
	 * 返回日期间隔
	 * @param endDateStr
	 * @param startDateStr
	 * @return
	 * @author qzq
	 */
	public static int getDateSpan(String endDateStr,String startDateStr){
		Calendar endCd = Calendar.getInstance();
		Calendar startCd = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date endDate = null;
		Date startDate = null;
		try {
			endDate = format.parse(endDateStr);
			startDate = format.parse(startDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		endCd.setTime(endDate);
		long end = endCd.getTimeInMillis();
		startCd.setTime(startDate);
		long start = startCd.getTimeInMillis();
		long span=(end-start)/(1000*60*60*24);
		return (int) span;
	}
	/**
	 * 获取间隔月份
	 * @param endDateStr
	 * @param startDateStr
	 * @return
	 */
	public static int getMonthSpan(String endDateStr,String startDateStr){
		Calendar endCd = Calendar.getInstance();
		Calendar startCd = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date endDate = null;
		Date startDate = null;
		try {
			endDate = format.parse(endDateStr);
			startDate = format.parse(startDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		endCd.setTime(endDate);
		startCd.setTime(startDate);
		int span=(endCd.get(Calendar.YEAR) - startCd.get(Calendar.YEAR)) * 12 + endCd.get(Calendar.MONTH) - startCd.get(Calendar.MONTH); 
		return span + 1;
	}
	/**
	 * 获取回退时间字符串(月份)
	 * 
	 * @param backMonth
	 * @return
	 */
	public static String getBackMonthString8(String backDate, int backMonth) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = format.parse(backDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.MONTH, -backMonth);
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd");
		dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFm.format(cd.getTime());
	}
	/**
	 * 获取回退时间字符串(月份)
	 * 
	 * @param backMonth
	 * @return
	 */
	public static String getBackMonthString(int backMonth, String pattern) {
		Date date = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.MONTH, -backMonth);
		SimpleDateFormat dateFm = new SimpleDateFormat(pattern);
		dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFm.format(cd.getTime());
	}
	/**
	 * 获取前进时间字符串(月份)
	 * 
	 * @param forwardMonth
	 * @return
	 */
	public static String getForwardMonthString8(String forwardDate, int forwardMonth) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = format.parse(forwardDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.MONTH, forwardMonth);
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd");
		dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFm.format(cd.getTime());
	}
	/**
	 * 获取回退时间字符串
	 * 
	 * @param backDay
	 * @return
	 * @author qzq
	 */
	public static String getBackDateString14(int backDay) {
		Date date = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, -backDay);
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss");
		dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFm.format(cd.getTime());
	}

	/**
	 * 获取回退时间字符串
	 * 
	 * @param backDay
	 * @return
	 * @author qzq
	 */
	public static String getBackDateString14(int backDay, String pattern) {
		Date date = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, -backDay);
		SimpleDateFormat dateFm = new SimpleDateFormat(pattern);
		dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFm.format(cd.getTime());
	}
	
	/**
	 * 获取指定格式的东八区字符串 如果格式中有毫秒，那么毫秒有可能是2位或者3位
	 * 
	 * @param pattern
	 *            指定格式
	 * @return 指定格式的东八区字符串
	 */
	public static String getCurrtDoneTimeString(String pattern) {

		try {
			Date date = new Date();
			SimpleDateFormat dateFm = new SimpleDateFormat(pattern);
			dateFm.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			return dateFm.format(date);
		} catch (Exception e) {
			throw new RuntimeException("时间转换错误:" + e.getMessage(), e);
		}

	}

	/**
	 * 获取指定格式东八区时间 默认格式为 yyyy-MM-dd
	 * 
	 * @param string
	 *            时间字符串
	 * @param pattern
	 *            时间格式
	 * @return 时间
	 */
	public static Date formatStringToDateWithNull(String string, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		if (pattern != null) {
			dateFormat.applyPattern(pattern);
		} else {
			dateFormat.applyPattern("yyyy-MM-dd");
		}
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		try {
			Date date = dateFormat.parse(string);
			return date;
		} catch (ParseException e) {
			throw new RuntimeException("时间转换错误:" + e.getMessage(), e);
		}
	}

	/**
	 * 获取指定时间指定格式的时间字符串
	 * 
	 * @param date
	 *            指定时间
	 * @param pattern
	 *            指定格式
	 * @return 时间字符串
	 */
	public static String formatDateToStringWithNull(Date date, String pattern) {
		if (date == null)
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if (pattern != null) {
			dateFormat.applyPattern(pattern);
		} else {
			dateFormat.applyPattern("yyyy-MM-dd");
		}
		return dateFormat.format(date);
	}

	/**
	 * 获取当前时间指定格式的东八区字符串
	 * 
	 * @param pattern
	 *            指定格式
	 * @return 时间字符串
	 */
	public static String formatDateToStringWithNull(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if (pattern != null) {
			dateFormat.applyPattern(pattern);
		} else {
			dateFormat.applyPattern("yyyy-MM-dd");
		}
		return dateFormat.format(new Date());
	}

	public static Date date_add(Date date, int type, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(type, num);
		date = cal.getTime();
		Date newDate = date;
		return newDate;
	}
	/**
	 * 简单字符串日期格式化 yyyy-MM-dd
	 * @param dt
	 * @return
	 */
	public static String formatForSimple8(String dt){
		return dt.substring(0,4)+'-'+dt.substring(4,6)+'-'+dt.substring(6,8);
	}
	/**
	 * 简单字符串日期格式化 yyyy-MM-dd HH:mm
	 * @param ts
	 * @return
	 * @author qzq
	 */
	public static String formatForSimple(String ts){
		return ts.substring(0,4)+'-'+ts.substring(4,6)+'-'+ts.substring(6,8)+" "+ts.substring(8,10)+":"+ts.substring(10,12);
	}
	/**
	 * 字符串如yyyy-MM-dd HH:mm 格式化为数字字符串
	 * @param str
	 * @return
	 * @author qzq
	 */
    public static String formatStrForStr(String str){
    	if(str == null || "".equals(str)){
    		return null;
    	}
    	String regEx="[^0-9]";   
    	Pattern p = Pattern.compile(regEx);   
    	Matcher m = p.matcher(str);   
    	return m.replaceAll("").trim();
    }
    
    /**
     * 某一个月第一天
     * 
     * @param date
     * @return
     */
    public static String getFirstdayMonth(Date date,String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date theDate = calendar.getTime();
        
        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        day_first = str.toString();
        return day_first;
    }

    /**
     * 某一个月最后一天
     * 
     * @param date
     * @return
     */
    public static String getLastdayMonth(Date date,String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);    //加一个月
        
        //上个月最后一天
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();
        return day_last;
    }
    /**
     * LocalDateTime转换为long
     * @param time
     * @return
     * @author qzq
     */
    public static long getLocalDateTime2Long(LocalDateTime time){
    	if(time==null){
    		return 0;
    	}
    	return time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
    /**
     * 转换为yyyyMMddHHmmss
     * @param time
     * @return
     * @author qzq
     * @date 2020年3月11日 下午2:25:53
     */
    public static String getLocalDateTime2String14(LocalDateTime time){
    	if(time==null){
    		return null;
    	}
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String nowStr = now.format(format);
    	return nowStr;
    }
    public static String getLocalDateTime2String8(LocalDateTime time){
    	if(time==null){
    		return null;
    	}
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
    	return time.format(format);
    }
    /**
     * yyyyMMddHHmmss 转换为LocalDateTime
     * @param time
     * @return
     * @author qzq
     * @date 2020年3月11日 下午2:28:03
     */
    public static LocalDateTime changeString2LocalDateTime(String time){
    	if(time==null){
    		return null;
    	}
    	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    	return  LocalDateTime.parse(time, format);
    }
    
    public static LocalDateTime changeLong2LocalDateTime(long time){
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
    	return LocalDateTime.ofInstant(instant, zone);
    }

	public static void main(String[] args) {
	}
}
