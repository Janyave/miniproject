package com.netease.ecos.request.share;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

/***
 * 
* @ClassName: GetShareDetailRequest 
* @Description: 获取分享详情
* @author enlizhang
* @date 2015年7月26日 下午12:40:24 
*
 */
public class GetShareDetailRequest extends BaseRequest{
	
	//请求参数键
	/*** 要删除的分享id */
	public static final String SHARE_ID = "share_id";
	
	GetShareResponse mGetShareResponse;	
	
	
	public void request(GetShareResponse getShareResponse, final String shareId)
	{
		super.initBaseRequest(getShareResponse);
		mGetShareResponse =  getShareResponse;
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_SHARE_DETAIL_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(SHARE_ID, shareId);
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, GetShareDetailRequest.this.getUrl(RequestUrlConstants.GET_SHARE_DETAIL_URL, map));
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
			
			
			if(mGetShareResponse!=null)
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
	* @ClassName: GetShareResponse 
	* @Description: 获取分享详情成功后回调接口
	* @author enlizhang
	* @date 2015年7月26日 下午7:23:09 
	*
	 */
	interface GetShareResponse extends IBaseResponse{

		/*** 获取分享详情成功后回调，并返回分享详情  */
		public void success(Share share);
		
	}
	
}

