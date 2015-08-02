package com.netease.ecos.request.recruitment;

import com.netease.ecos.model.Recruitment;
import com.netease.ecos.model.Recruitment.RecruitType;
import com.netease.ecos.model.User.Gender;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/***
 * 
* @ClassName: RecruitmentListRequest 
* @Description: 招募列表
* @author enlizhang
* @date 2015年7月30日 下午8:48:26 
*
 */
public class RecruitmentListRequest extends BaseRequest{
	
	//请求参数键
	/** 招募类型 */
	public static final String KEY_RECRUITMENT_TYPE = "recruitType";
	
	/** 城市码 */
	public static final String KEY_CITY_CODE = "cityCode";
	
	/** 排列规则 */
	public static final String KEY_SORT_RULE = "sortRule";

	
	//响应参数列表
	/*** 招募列表JSONArray,内含JSONObject */
	public static final String JA_RECRUITMENTS= "recruitments";
	
		/** 招募id */
		public static final String KEY_RECRUITMENT_ID = "recruitId";
	
		/** 用户id */
		public static final String KEY_USER_ID = "userId";
	
		/** 云信id */
		public static final String KEY_IM_ID = "imId";
	
		/** 标题  */
		public static final String KEY_TITLE = "title";
		
		/** 头像url */
		public static final String KEY_AVATAR_URL = "avatarUrl";
	
		/** 昵称 */
		public static final String KEY_NICKNAME = "nickname";
		
		/** 性别 */
		public static final String KEY_GENDER = "gender";
		
		/** 封面图url */
		public static final String KEY_COVER_URL = "coverUrl";
		
		/*** 发布时间时间戳 */
		public static final String KEY_ISSUE_TIME_STAMP = "issueTimeStamp";
		
		
		
	
		IRecruitmentListResponse mRecruitmentListResponse;
	
	
	public void request(IRecruitmentListResponse recruitmentListResponse, final RecruitType recruitType, final String cityCode,
			final SortRule sortRule, final int pageIndex)
	{
		super.initBaseRequest(recruitmentListResponse);
		mRecruitmentListResponse = recruitmentListResponse;
		
		recruitmentListResponse.success(getTestRecruitmentList());
		
		/*MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_RECRUITMENT_LIST_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	        	
	        	map.put(KEY_RECRUITMENT_TYPE, recruitType.getValue());
	        	map.put(KEY_CITY_CODE, cityCode);
	        	map.put(KEY_SORT_RULE, sortRule.getValue());
	        	
	        	map.put(KEY_PAGE_SIZE, String.valueOf( DEFAULT_PAGE_SIZE ) );
	        	map.put(KEY_PAGE_INDEX, String.valueOf( pageIndex ) );
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, RecruitmentListRequest.this.getUrl(RequestUrlConstants.GET_RECRUITMENT_LIST_URL, map));
	            return map;  
	        }  
	        
	    }; 
	    
	    stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	    
	    getQueue().add(stringRequest);*/
	    
	}


	public static final String VALUE_MY_RECRUITS = "6";

	public void requestMyself(IRecruitmentListResponse recruitmentListResponse,  final int pageIndex)
	{
		super.initBaseRequest(recruitmentListResponse);
		mRecruitmentListResponse = recruitmentListResponse;

		recruitmentListResponse.success(getTestRecruitmentList());

		/*MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_RECRUITMENT_LIST_URL,  this, this) {
	        @Override
	        protected Map<String, String> getParams() throws AuthFailureError {
	        	Map<String, String> map = getRequestBasicMap();

	        	map.put(KEY_RECRUITMENT_TYPE, VALUE_MY_RECRUITS);

	        	map.put(KEY_PAGE_SIZE, String.valueOf( DEFAULT_PAGE_SIZE ) );
	        	map.put(KEY_PAGE_INDEX, String.valueOf( pageIndex ) );

	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, RecruitmentListRequest.this.getUrl(RequestUrlConstants.GET_RECRUITMENT_LIST_URL, map));
	            return map;
	        }

	    };

	    stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

	    getQueue().add(stringRequest);*/

	}

	@Override
	public void responceSuccess(String jstring) {
		traceNormal(TAG, jstring);
		
		try {
			JSONObject json = new JSONObject(jstring).getJSONObject(KEY_DATA);
//			JSONArray shareJA = json.getJSONArray(JA_SHARE);
//			int length = shareJA.length();
//			
//			List<Share> shareList = new ArrayList<Share>();
//			for(int i=0;i<length;i++){
//				JSONObject shareJO = shareJA.getJSONObject(i);
//				Share share = new Share();
//				share.avatarUrl = getString(shareJO, KEY_AVATAR_URL);
//				share.nickname = getString(shareJO, KEY_NICKNAME);
//				share.hasAttention = Boolean.valueOf(getString(shareJO, KEY_HAS_FOLLOWED));
//				share.hasPraised = Boolean.valueOf(getString(shareJO,KEY_HAS_Praised));
//				share.coverUrl = getString(shareJO, KEY_COVER_URL);
//				share.title = getString(shareJO, KEY_TITLE);
//				share.issueTimeStamp = Long.valueOf(shareJO.getString(KEY_ISSUE_TIME)).longValue();
//				
//				String totalPics = getString(shareJO, KEY_TOTAL_IMAGES);
//				share.totalPics = "".equals(totalPics)?0:Integer.valueOf(totalPics);
//						
//				String praiseNum = getString(shareJO, KEY_PRAISE_NUM);
//				share.praiseNum = "".equals(praiseNum)?0:Integer.valueOf(praiseNum);
//				
//				String commentNum = getString(shareJO, KEY_COMMENT_NUM);
//				share.commentNum = "".equals(commentNum)?0:Integer.valueOf(commentNum);
//				
//				//设置评论数据
//				List<Comment> commentList = new ArrayList<Comment>();
//				if(json.has(JA_COMMENTS)){
//					JSONArray commentJA = json.getJSONArray(JA_COMMENTS);
//					
//					int commentsLength = commentJA.length();
//					Comment comment;
//					for(int commentIndex=0;i<commentsLength;i++){
//						
//						JSONObject commentJO = commentJA.getJSONObject(commentIndex);
//						
//						comment = new Comment();
//						comment.commentId = getString(commentJO, KEY_COMMENT_ID);
//						comment.avatarUrl = getString(commentJO, KEY_COMMENT_AVATAR_URL);
//						comment.content = getString(commentJO, KEY_COMMENT_CONTENT);
//						comment.commentType = CommentType.valueOf(getString(commentJO, KEY_COMMENT_TYPE));
//						comment.commentTypeId = getString(commentJO, KEY_COMMENT_TYPE_ID);
//						comment.fromId = getString(commentJO, KEY_COMMENT_FROM_ID);
//						comment.fromNickName = getString(commentJO, KEY_COMMENT_USER_NICKNAME);
//						comment.targetId = getString(commentJO, KEY_COMMENT_PARENT_ID);
//						comment.targetNickname = getString(commentJO, KEY_COMMENT_PARENT_NICKNAME);
//						comment.commitTimeStamp = Long.valueOf(commentJO.getString(KEY_COMMENT_TIME_STAMP)).longValue();
//						commentList.add(comment);
//					}
//					
//				}
//				share.commentList = commentList;
//				shareList.add(share);
//			}
//			
//			
//			if(mShareListResponse!=null)
//			{
//				mShareListResponse.success(shareList);
//			}
//			else
//			{
//				traceError(TAG,"回调接口为null");
//			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			if(mBaseResponse!=null)
			{
				mBaseResponse.doAfterFailedResponse("json异常");
			}
		}
		
	}
	
	
	
	
	/***
	 * 
	* @ClassName: SortRule 
	* @Description: 排序规则，包括智能排序、价格最低、距离最近、最受欢迎
	* @author enlizhang
	* @date 2015年7月30日 下午8:49:12 
	*
	 */
	public static enum SortRule
	{
		智能排序("intelligent"),
		价格最低("price"),
		距离最近("distance"),
		最受欢迎("popular");
		
		public String value;

		SortRule(String _value){
			value = _value;
		}
		
		public String getValue(){
			return value;
		}
		
		/***
		 * 根据值获取对应枚举
		 * @param value
		 * @return
		 */
		public static SortRule getSortRuleByValue(String value){
			
			for(SortRule sortRule:SortRule.values()){
				if(sortRule.getValue().equals(value))
					return sortRule;
			}
			
			return 智能排序;
		}
	}
	
	/***
	 * 
	* @ClassName: IRecruitmentListResponse 
	* @Description: 获取招募列表响应回调接口
	* @author enlizhang
	* @date 2015年7月30日 下午9:46:41 
	*
	 */
	public interface IRecruitmentListResponse extends IBaseResponse{
		
		/** 请求成功回调函数，返回招募列表 */
		public void success(List<Recruitment> recruitmentList);
		
	}
	
	/***
	 * 获取测试分享数据
	 * @return
	 */
	public List<Recruitment> getTestRecruitmentList(){
		
		List<Recruitment> recruitList = new ArrayList<Recruitment>();
		
		int length = 4;
		
		List<String> bannerList = new ArrayList<String>();
		bannerList.add("http://u4.tdimg.com/7/203/19/46138657748730920288026757971472766587.jpg");
		bannerList.add("http://www.cnnb.com.cn/pic/0/01/49/86/1498602_864010.jpg");
		bannerList.add("http://u3.tdimg.com/6/88/143/_56696781343356143444965292996172123406.jpg");
		bannerList.add("http://i3.cqnews.net/news/attachement/jpg/site82/2011-07-27/4386628352243053135.jpg");
		
		
		for(int i=0;i<length;i++){
			Recruitment recruit = new Recruitment();
			recruit.recruitmentId = ""+i;
			recruit.userId = ""+i;
			recruit.imId = ""+i;
//			recruit.title = "招募测试"+i;
			recruit.avatarUrl = "http://img1.imgtn.bdimg.com/it/u=1413087,3985996900&fm=21&gp=0.jpg";
			recruit.nickname = "蓝天与白云的故事";
			recruit.gender = Gender.女;
			recruit.coverUrl = bannerList.get(i);
			recruit.issueTimeStamp = System.currentTimeMillis() - i*24*60*60*1000;
			recruit.distanceKM = "" + ( 1+i );
			recruit.averagePrice = "30元/人";
			
			recruitList.add(recruit);
		}
		
		return recruitList;
	}
	
	
	
}
