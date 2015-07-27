package com.netease.ecos.request.share;

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
* @ClassName: DeleteShareRequest 
* @Description: 删除分享
* @author enlizhang
* @date 2015年7月26日 下午12:41:48 
*
 */
public class DeleteShareRequest extends BaseRequest{
	
	//请求参数键
	/*** 要删除的分享id */
	public static final String SHARE_ID = "share_id";
	
	//响应参数键
	DeleteShareResponse mDeleteShareResponse;
	
	
	public void request(DeleteShareResponse deleteShareResponse, final String shareId)
	{
		super.initBaseRequest(deleteShareResponse);
		mDeleteShareResponse = deleteShareResponse;
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.DELETE_SHARE_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	        	
	        	map.put(SHARE_ID, shareId);
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, DeleteShareRequest.this.getUrl(RequestUrlConstants.DELETE_SHARE_URL, map));
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
			
			
			if(mDeleteShareResponse!=null)
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
	* @ClassName: DeleteShareResponse 
	* @Description: 删除分享回调函数
	* @author enlizhang
	* @date 2015年7月26日 下午7:24:04 
	*
	 */
	interface DeleteShareResponse extends IBaseResponse{

		/*** 创建成功后回调，并返回删除的分享id  */
		public void success(String shareId);
		
	}
}

