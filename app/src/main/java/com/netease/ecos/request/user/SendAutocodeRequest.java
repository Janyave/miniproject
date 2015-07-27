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
import com.netease.ecos.request.MyStringRequest;
import com.netease.ecos.request.NorResponce;

/***
 * 
* @ClassName: SendAutocodeRequest 
* @Description: 发送验证码
* @author enlizhang
* @date 2015年7月26日 上午10:37:51 
*
 */
public class SendAutocodeRequest extends BaseRequest{
	
	//请求参数键
	/*** 请求类型,  get或者check */
	public static final String TYPE = "type";
	
	/*** 手机号 */
	public static final String PHONE = "phone";
	
	
	//响应参数键
	
	
	/** 请求结束回掉函数 */
	NorResponce mNorResponce;
	
	public void request(NorResponce norResponce, final String phone, final String type)
	{
		super.initBaseRequest(norResponce);
		mNorResponce = norResponce;
		
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.SEND_AUTO_CODE_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = new HashMap<String, String>();

	        	map.put(PHONE, phone);
	        	map.put(TYPE, type);
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, SendAutocodeRequest.this.getUrl(RequestUrlConstants.SEND_AUTO_CODE_URL, map));
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
				mNorResponce.success();
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
	
}

