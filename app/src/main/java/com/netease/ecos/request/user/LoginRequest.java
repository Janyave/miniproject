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

/***
 * 
* @ClassName: LoginRequest 
* @Description: 登录
* @author enlizhang
* @date 2015年7月26日 上午10:30:53 
*
 */
public class LoginRequest extends BaseRequest{
	
	//请求参数键
	/*** 手机号 */
	public static final String PHONE = "phone";
	
	/*** 密码 */
	public static final String PASSWORD = "password";
	
	
	//响应参数键
	
	
	
	public void request(IBaseResponse baseresponce, final String phone, final String password)
	{
		super.initBaseRequest(baseresponce);
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.LOLGIN_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = new HashMap<String, String>();
	            
	        	map.put(PHONE, phone);
	        	map.put(PASSWORD, password);
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, LoginRequest.this.getUrl(RequestUrlConstants.LOLGIN_URL, map));
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

