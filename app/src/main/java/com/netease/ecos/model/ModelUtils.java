package com.netease.ecos.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**   
 * @Title: ModelUtils.java 
 * @Description: 
 * @author enlizhang   
 * @date 2015年7月27日 上午11:01:20 
 */

public class ModelUtils {
	
	/***
	 * 将时间戳转换成日期描述:yyyy-MM-dd
	 * @param srcTimeStamp 时间戳,若该值为null则返回空串
	 * @return
	 */
	public static String getDateDesByTimeStamp(Long srcTimeStamp){
		  
		if(srcTimeStamp == null)
			return "";
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		  
		String sd = sdf.format(new Date(srcTimeStamp)); 
		
		return sd;
	}

	/***
	 * 将时间戳转换成日期描述:yyyy-MM-dd hh:mm:ss
	 * @param srcTimeStamp
	 * @return
	 */
	public static String getDateDetailByTimeStamp(Long srcTimeStamp){

		if(srcTimeStamp == null)
			return "";

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		String sd = sdf.format(new Date(srcTimeStamp));

		return sd;
	}

	/***
	 * 根据日期获取时间戳
	 * @param year 年纷
	 * @param month 月份，从1开始
	 * @param day 日，从1开始
	 * @return
	 */
	public static Date getTimeStampByDate(int year,int month, int day){
		Date date = new Date();
		date.setYear(year-1900);
		date.setMonth(month-1);
		date.setDate(day);

		return date;

	}

}

