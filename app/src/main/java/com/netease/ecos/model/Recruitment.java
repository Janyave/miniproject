package com.netease.ecos.model;

import java.util.List;

/**
 * 
* @ClassName: Recruitment 
* @Description: 招募
* @author enlizhang
* @date 2015年7月25日 下午11:31:09 
*
 */
public class Recruitment {
	
	/*** 标题 */
	public String  title;
	
	/*** 介绍 */
	public String  introduction;
	
	/*** 发起者的用户id{@link User} */
	public String sponsorId;
	
	/*** 均价 */
	public String averagePrice;
	
	/*** 封面图本地路径 */
	public String coverLocalPath;
	
	/*** 封面图url */
	public String  coverUrl;
	
	/** 发布时间,是一个时间戳 */
	public Long issueTime ;
	
	/*** 作品{@link Share}}列表 */
	public List<Share> shareList;
	
	/***
	 * 根据{@link #issueTime}获取发布时间描述
	 * @return
	 */
	public String getDateDescription(){
		
		return "";
	}
}
