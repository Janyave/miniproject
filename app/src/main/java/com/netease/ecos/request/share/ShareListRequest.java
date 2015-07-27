package com.netease.ecos.request.share;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constant.RequestUrlConstants;
import com.netease.ecos.model.Course;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

/***
 * 
* @ClassName: ShareListRequest 
* @Description: 获取分享列表
* @author enlizhang
* @date 2015年7月26日 下午12:43:32 
*
 */
public class ShareListRequest extends BaseRequest{
	
	//请求参数键
	/** 获取教程列表方式，包括推荐和筛选 */
	public static final String TYPE = "type";
	
	/** 关键字(仅在{@link #TYPE}为推荐时有效) */
	public static final String KEY_WORD = "key_word";
	
	
	ShareListResponse mShareListResponse;
	
	public void request(ShareListResponse shareListResponse, final Type type, final String keyWord,
			final int pageIndex)
	{
		super.initBaseRequest(shareListResponse);
		mShareListResponse = shareListResponse;
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_SHARE_LIST_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(TYPE, type.getValue());
	        	map.put(KEY_WORD, keyWord);
	        	map.put(KEY_PAGE_SIZE, String.valueOf( DEFAULT_PAGE_SIZE ) );
	        	map.put(KEY_PAGE_INDEX, String.valueOf( pageIndex ) );
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, ShareListRequest.this.getUrl(RequestUrlConstants.GET_SHARE_LIST_URL, map));
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
	
	
	/**
	 * 
	* @ClassName: ShareListRespnce 
	* @Description: 获取教程请求回调接口
	* @author enlizhang
	* @date 2015年7月26日 下午7:42:45 
	*
	 */
	interface ShareListResponse extends IBaseResponse{
		
		/** 请求成功回调函数，返回分享列表 */
		public void success(List<Share> shareList);
		
	}
	
	/***
	 * 
	* @ClassName: Type 
	* @Description: 分享类型
	* @author enlizhang
	* @date 2015年7月26日 下午7:48:42 
	*
	 */
	static enum Type
	{
		所有("all"),
		推荐("recommended"),
		新人("transparent"),
		关注("follow");
		
		public String type;

		Type(String _type){
			type = _type;
		}
		
		public String getValue(){
			return type;
		}
	}
	
}

