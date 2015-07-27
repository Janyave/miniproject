package com.netease.ecos.request.activity;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.Activity;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

/***
 * 
* @ClassName: CreateActivityRequest 
* @Description: 创建活动
* @author enlizhang
* @date 2015年7月26日 下午12:59:53 
*
 */
public class CreateActivityRequest extends BaseRequest{
	
	//请求参数键
	/*** Activity对象数据json串，空的属性用"null"字符串 */
	public static final String ACTIVITY_JSON = "act_json";
	
	
	//响应参数键
	Activity mActivity;
	
	CreateActivityResponce mCreateActivityResponce;
	
	public void request(CreateActivityResponce createActivityResponce, final Activity activity)
	{
		super.initBaseRequest(createActivityResponce);
		mCreateActivityResponce = createActivityResponce;
		mActivity = activity;
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.CREATE_ACTIVITY_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(ACTIVITY_JSON, activity.getRequestJson());
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, CreateActivityRequest.this.getUrl(RequestUrlConstants.CREATE_ACTIVITY_URL, map));
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
			
			
			if(mCreateActivityResponce!=null)
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
	* @ClassName: CreateActivityResponce 
	* @Description: 创建活动回调接口
	* @author enlizhang
	* @date 2015年7月26日 下午8:23:15 
	*
	 */
	interface CreateActivityResponce extends IBaseResponse
	{
		/***
		 * 请求成功回掉函数，并返回创建的活动
		 */
		public void success(Course course);
	}
	
}

