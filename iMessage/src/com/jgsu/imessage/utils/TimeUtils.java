package com.jgsu.imessage.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	/**
	 * 获取系统当前的小时数
	 * @return
	 */
	public static int getCurrentHour() {

		Date date = new Date(System.currentTimeMillis());

		@SuppressWarnings("deprecation")
		int hours = date.getHours();

		return hours;

	}
	
	/**
	 * 获取系统当前时间的   年-月-日 时-分 格式
	 * @return
	 */
	public static String getCurrentTime(){
		
		Date date  = new Date(System.currentTimeMillis());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		
		return dateFormat.format(date);
		
	}
	
	/**
	 * 将毫秒值转成 年-月-日的时间格式
	 * @param millSeconds
	 * @return
	 */
	public static String millSecond2FomatedTime(long millSeconds){
		
		Date date  = new Date(millSeconds);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		
		return dateFormat.format(date);
		
	}
	
	/**
	 * 将 年-月-日的时间换算成毫秒值
	 * @param formatedTime
	 * @return
	 * @throws ParseException
	 */
	public static long formatedTime2MillSeconds(String formatedTime) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		
		return dateFormat.parse(formatedTime).getTime();
	}
	
	
}
