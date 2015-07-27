package com.netease.ecos.request.user;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constant.RequestUrlConstants;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;
import com.netease.ecos.request.NorResponce;

/***
 * 
* @ClassName: FollowUserRequest 
* @Description: 关注
* @author enlizhang
* @date 2015年7月26日 上午10:30:14 
*
 */
public class FollowUserRequest extends BaseRequest{
	
	//请求参数键
	/*** 操作类型(关注或取消关注){@link FollowType} */
	public static final String TYPE = "type";
	
	/** 关注或取消关注的对象的用户id*/
	public static final String TO_USRE_ID = "to_user_id";
	
	/** 请求结束回掉函数 */
	NorResponce mNorResponce;
	
	
	public void request(NorResponce baseresponce, final String toUserId, final FollowType type)
	{
		super.initBaseRequest(baseresponce);
		mNorResponce  = baseresponce;
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.FOLLOW_USER_INFO,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(TO_USRE_ID, toUserId);
	        	map.put(TYPE, type.getType());
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, FollowUserRequest.this.getUrl(RequestUrlConstants.FOLLOW_USER_INFO, map));
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
			
			
			if(mNorResponce!=null)
			{
			}
			else
			{
				traceError(TAG,"回调接口为null");
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			if(mNorResponce!=null)
			{
				mNorResponce.doAfterFailedResponse("json异常");
			}
		}
		
	}

	/***
	 * 
	* @ClassName: FollowType 
	* @Description: 关注类型, 关注或取消关注
	* @author enlizhang
	* @date 2015年7月26日 下午3:58:02 
	*
	 */
	static public enum FollowType{
		
		关注("follow"),
		取消关注("cancel");
		
		public String type;
		
		FollowType(String _type){
			type = _type;
		}
		
		public String getType(){
			return type;
		}
		
	}
}

