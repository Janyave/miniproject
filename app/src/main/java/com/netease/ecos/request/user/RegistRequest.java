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
* @ClassName: RegistRequest 
* @Description: 注册
* @author enlizhang
* @date 2015年7月26日 上午10:31:18 
*
 */
public class RegistRequest extends BaseRequest{
	
	//请求参数键
	/*** 手机号 */
	public static final String PHONE = "phone";
	
	/*** 昵称 */
	public static final String NICK_NAME = "nickname";
	
	/*** 密码 */
	public static final String PASSWORD = "password";
	
	
	//响应参数键
	
	
	
	public void request(IBaseResponse baseresponce, final String phone, final String password,
			final String nickName)
	{
		super.initBaseRequest(baseresponce);
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.REGIST_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = new HashMap<String, String>();
	        	
	        	map.put(PHONE, phone);
	        	map.put(NICK_NAME, password);
	        	map.put(PASSWORD, nickName);
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, RegistRequest.this.getUrl(RequestUrlConstants.REGIST_URL, map));
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

