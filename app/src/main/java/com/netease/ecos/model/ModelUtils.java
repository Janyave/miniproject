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
	 * 将时间戳转换成日期描述
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

}

