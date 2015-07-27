package com.netease.ecos.request.share;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constant.RequestUrlConstants;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

/***
 * 
* @ClassName: CreateShareRequest 
* @Description: 创建分享
* @author enlizhang
* @date 2015年7月26日 下午12:41:05 
*
 */
public class CreateShareRequest extends BaseRequest{
	
	//请求参数键
	/*** Course对象数据json串，空的属性用"null" */
	public static final String JSON_STRING = "share_json";
	
	CreateShareResponse mCreateShareResponse;
	
	
	public void request(CreateShareResponse createShareResponse, final Share share)
	{
		super.initBaseRequest(createShareResponse);
		mCreateShareResponse = createShareResponse;
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.CREATE_SHARE_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(JSON_STRING, share.getRequestJson());
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, CreateShareRequest.this.getUrl(RequestUrlConstants.CREATE_SHARE_URL, map));
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
			
			
			if(mCreateShareResponse!=null)
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
	* @ClassName: CreateShareResponse 
	* @Description: 创建教程响应回调函数
	* @author enlizhang
	* @date 2015年7月26日 下午7:31:21 
	*
	 */
	interface CreateShareResponse extends IBaseResponse{

		/*** 创建成功后回调，并返回创建的分享  */
		public void success(Share share);
		
	}
	
}

