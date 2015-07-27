package com.netease.ecos.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/***
 * 
* @ClassName: Share 
* @Description: 作品分享
* @author enlizhang
* @date 2015年7月25日 下午11:30:51 
*
 */
public class Share {

	
	/** 标题 */
	public String title ;
	
	/** 发布者 */
	public String author;
	
	/** 发布时间,是一个时间戳,可以通过{@link #getDateDescription()}获取日期描述 */
	public Long issueTimeStamp ;
	
	/** 封面图本地路径 */
	public String coverLocalPath;
	
	/** 封面图url */
	public String coverUrl;
	
	/** 图片列表 */
	public List<Image> imageList;
	
	/*** 内容 */
	public String content;
	
	/** 点赞数 */
	public int praiseNum ;

	/** 评论数 */
	public int commentNum;
	
	
	/***
	 * 根据{@link #issueTimeStamp}获取发布时间描述
	 * @return
	 */
	public String getDateDescription(){
		
		return ModelUtils.getDateDesByTimeStamp(issueTimeStamp);
	}
	
	/***
	 * 获取添加分享请求所需的Json串
	 * @return
	 */
	public String getRequestJson(){
		Map<String,String> jsonMap = new HashMap<String,String>();
		
		return new JSONObject(jsonMap).toString();
	}
	
}
