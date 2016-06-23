package com.jgsu.imessage.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	/**
	 * ��ȡϵͳ��ǰ��Сʱ��
	 * @return
	 */
	public static int getCurrentHour() {

		Date date = new Date(System.currentTimeMillis());

		@SuppressWarnings("deprecation")
		int hours = date.getHours();

		return hours;

	}
	
	/**
	 * ��ȡϵͳ��ǰʱ���   ��-��-�� ʱ-�� ��ʽ
	 * @return
	 */
	public static String getCurrentTime(){
		
		Date date  = new Date(System.currentTimeMillis());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		
		return dateFormat.format(date);
		
	}
	
	/**
	 * ������ֵת�� ��-��-�յ�ʱ���ʽ
	 * @param millSeconds
	 * @return
	 */
	public static String millSecond2FomatedTime(long millSeconds){
		
		Date date  = new Date(millSeconds);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		
		return dateFormat.format(date);
		
	}
	
	/**
	 * �� ��-��-�յ�ʱ�任��ɺ���ֵ
	 * @param formatedTime
	 * @return
	 * @throws ParseException
	 */
	public static long formatedTime2MillSeconds(String formatedTime) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		
		return dateFormat.parse(formatedTime).getTime();
	}
	
	
}
