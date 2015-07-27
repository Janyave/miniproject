package com.netease.ecos.request.comment;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constant.RequestUrlConstants;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Course;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.model.Share;
import com.netease.ecos.model.Comment.CommentType;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

/***
 * 
* @ClassName: CreateCommentRequest 
* @Description: 创建评论
* @author enlizhang
* @date 2015年7月26日 下午12:35:09 
*
 */
public class CreateCommentRequest extends BaseRequest{
	
	//请求参数键
	/** 评论类型{@link CommentType}、包括{@link Course}、作业{@link Assignment}、分享{@link Share}、招募{@link Recruitment}*/
	public static final String COMMENT_TYPE = "comment_type";
	
	/** 某种评论类型{@link CommentType}的id，不能为空 */
	public static final String COMMENT_TYPE_ID = "comment_type_id";
	
	//响应参数键
	Comment mComment;
	
	CreateCommentResponse mCreateCommentResponse;
	
	
	public void request(CreateCommentResponse createCommentResponse, final Comment comment)
	{
		super.initBaseRequest(createCommentResponse);
		mCreateCommentResponse = createCommentResponse;
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.CREATE_COMMENT_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, CreateCommentRequest.this.getUrl(RequestUrlConstants.CREATE_COMMENT_URL, map));
	            return map;  
	        }  
	        
	    }; 
	    
	    stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	    
	    getQueue().add(stringRequest);
	    
	}
	
	@Override
	public void responceSuccess(String jstring) {
		traceNormal(TAG, jstring);
		
		try {
			JSONObject json = new JSONObject(jstring);
			
			
			if(mCreateCommentResponse!=null)
			{
			}
			else
			{
				traceError(TAG,"回调接口为null");
			}
			
			
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
	* @ClassName: CreateCommentResponse 
	* @Description: 创建评论回调接口
	* @author enlizhang
	* @date 2015年7月26日 下午8:04:04 
	*
	 */
	interface CreateCommentResponse extends IBaseResponse{
		
		/** 请求成功回掉函数，返回创建的评论 */
		public void success(Comment comment);
		
	}
	
}

