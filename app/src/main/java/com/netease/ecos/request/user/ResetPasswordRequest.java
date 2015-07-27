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
* @ClassName: ResetPasswordRequest 
* @Description: 重置密码
* @author enlizhang
* @date 2015年7月26日 上午10:37:23 
*
 */
public class ResetPasswordRequest extends BaseRequest{
	
	//请求参数键
	/*** 手机号 */
	public static final String PHONE = "phone";
	
	/*** 密码 */
	public static final String PASSWORD = "pwd";
	
	
	//响应参数键
	
	
	
	public void request(IBaseResponse baseresponce, final String mobile, final String password)
	{
		super.initBaseRequest(baseresponce);
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.RESET_PASSWORD_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = new HashMap<String, String>();
	            
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, ResetPasswordRequest.this.getUrl(RequestUrlConstants.RESET_PASSWORD_URL, map));
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

