package com.netease.ecos.request.user;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;
/***
 * 
* @ClassName: GetUserInfoRequest 
* @Description: 获取用户信息
* @author enlizhang
* @date 2015年7月26日 上午10:30:34 
*
 */

public class GetUserInfoRequest extends BaseRequest{
	
	//响应参数键
	
	
	public void request(IBaseResponse baseresponce)
	{
		super.initBaseRequest(baseresponce);
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_USER_INFO,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, GetUserInfoRequest.this.getUrl(RequestUrlConstants.GET_USER_INFO, map));
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
			
			
			if(mBaseResponse!=null)
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
	
}

