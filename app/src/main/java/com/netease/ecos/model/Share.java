package com.netease.ecos.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 
* @ClassName: Share 
* @Description: 作品分享
* @author enlizhang
* @date 2015年7月25日 下午11:30:51 
*
 */
public class Share {

	/*** 分享id */
	public String shareId;

	/** 标题 */
	public String title ;

	/*** 用户id */
	public String userId;

	/** 发布者昵称 */
	public String nickname;

	/** 用户头像url */
	public String avatarUrl;

	/*** 图片总数 */
	public int totalPageNumber;

	/** 点赞数 */
	public int praiseNum ;

	/** 评论数 */
	public int commentNum;

	/** 是否已经关注,true:是 false:否 */
	public boolean hasAttention;

	/** 是否已经点赞,true:是 false:否 */
	public boolean hasPraised;

	/** 发布时间,是一个时间戳,可以通过{@link #getDateDescription()}获取日期描述 */
	public Long issueTimeStamp ;

	/** 封面图本地路径 */
	public String coverLocalPath;

	/** 封面图url */
	public String coverUrl;

	/** 图片列表 */
	public List<Image> imageList = new ArrayList<Image>();

	/** 评论列表 */
	public List<Comment> commentList = new ArrayList<Comment>();

	/*** 内容 */
	public String content;

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
