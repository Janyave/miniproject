package com.netease.ecos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/***
 * 
* @ClassName: Activity 
* @Description: 活动详情
* @author enlizhang
* @date 2015年7月25日 下午11:28:08 
*
 */
public class Activity {
	
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
	
	/*** 活动介绍 */
	public String introductino;
	
	/*** 费用 */
	public String fee;
	
	/*** 分类 */
	public ActivityType actionType;
	
	/*** 想去的人数 */
	public String loveNums;
	
	/*** 活动地址 */
	public Location location;
	
	/*** 联系方式列表 */
	public List<ContactWay> contactWayList;
	
	/*** 想去的人的用户列表 */
	public List<User> signUpUseList;
	
	
	/***
	 * 根据{@link #issueTimeStamp}获取发布时间描述
	 * @return
	 */
	public String getDateDescription(){
		
		return ModelUtils.getDateDesByTimeStamp(issueTimeStamp);
	}
	
	/***
	 * 返回招募类别名称列表
	 * @return
	 */
	public static List<String> getActivityTypeNameList(){
		List<String> list = new ArrayList<String>();
		for(ActivityType at:ActivityType.values()){
			list.add(at.name());
		}
		
		return list;
	}
	
	
	/***
	 * 
	* @ClassName: Situation 
	* @Description: 活动现场实况
	* @author enlizhang
	* @date 2015年7月25日 下午11:27:37 
	*
	 */
	static class Situation{
		
		/** 发布者用户{@link User}id */
		public String userId;
		
		/** 发布时间,是一个时间戳 */
		public Long issueTime ;
		
		/** 图片 */
		List<Image> imageList;
		
		/** 内容 */
		public String content;
	}
	
	/***
	 * 
	* @ClassName: Time 
	* @Description: 活动时间
	* @author enlizhang
	* @date 2015年7月26日 上午8:45:01 
	*
	 */
	public class Time{
		
		/*** 开始时间,精确到分的时间戳 */
		public Long startTime;
		
		/*** 每天持续时间 */
		public String hoursEveryday;
		
		/*** 持续天数*/
		public String days;
	}
	
	/***
	 * 
	* @ClassName: ActivityType 
	* @Description: 活动类别,包括同人展、动漫节、官方活动、LIVE、舞台祭、赛事、主题ONLY、派对
	* @author enlizhang
	* @date 2015年7月25日 下午11:49:20 
	*
	 */
	public static enum ActivityType {
		
		同人展("1"),
		动漫节("2"),
		官方活动("3"),
		LIVE("4"),
		舞台祭("5"),
		赛事("6"),
		主题ONLY("7"),
		派对("8");
		
		private String value;

		private ActivityType(String _value ) {
			this.value = _value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/***
	 * 
	* @ClassName: Location 
	* @Description: 位置，包括省、市、区、详细地址
	* @author enlizhang
	* @date 2015年7月26日 上午8:50:25 
	*
	 */
	class Location{
		
		/** 省份 */
		public Province province;
		
		/** 城市 */
		public City city;
		
		/** 区域 */
		public String district;
		
		/** 详细地址 */
		public String address;
	}
	
	/***
	 * 
	* @ClassName: ContactWays 
	* @Description: 联系方式，包括QQ、QQ群、电话 
	* @author enlizhang
	* @date 2015年7月26日 上午8:58:22 
	*
	 */
	public static enum ContactWay {
		
		QQ("1"),
		QQ群("2"),
		电话("3");
		
		/*** 类型*/
		private String type;
		
		/*** 值 */
		private String value;

		private ContactWay(String _type ) {
			this.type = _type;
		}

		public void setValue(String _value){
			value = _value;
		}
		
		
		public String getValue(){
			return value;
		}
	}

	
	/****
	 *活动数据对象JSON
		{
		   title:标题
		   cover_url:封面图片
		   img_urls:图片列表（JSON Array）
		   description:活动介绍
		   time:{时间
		      start:开始时间(long)
		      keepup_times:持续时间
		      keepup_days:持续天数
		      
		   }
		   fee:费用
		   type：分类
		   address: {//活动地址
		      city_code:城市code
		      detail: 详细地址
		   }（省、市、区、详细地址）
		   contacts:{联系方式
		      qq: 
		      qq_group:
		      weibo:
		      phone: 
		   }
		   user_id:发布者
		}

	 * @return
	 */
	public String getRequestJson(){
		Map<String,String> jsonMap = new HashMap<String,String>();
		
		return new JSONObject(jsonMap).toString();
	}
}
