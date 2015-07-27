package com.netease.ecos.request.course;

import java.util.ArrayList;
import java.util.List;
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
* @ClassName: GetBannerRequest 
* @Description: 获取运营广告
* @author enlizhang
* @date 2015年7月26日 上午11:03:52 
*
 */
public class GetBannerRequest extends BaseRequest{
	

	GetBannerResponce mGetBannerResponce;
	
	public void request(GetBannerResponce getBannerResponce)
	{
		super.initBaseRequest(getBannerResponce);
		mGetBannerResponce = getBannerResponce;
		
		responceSuccess("");
		
		/*MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_BANNER,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, GetBannerRequest.this.getUrl(RequestUrlConstants.GET_BANNER, map));
	            return map;  
	        }  
	        
	    }; 
	    
	    stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	    
	    getQueue().add(stringRequest);*/
	    
	}
	
	@Override
	public void responceSuccess(String jstring) {
		traceNormal(TAG, jstring);
		
		try {
			JSONObject json = new JSONObject(jstring);
			
			
			List<String> bannerList = new ArrayList<String>();
			bannerList.add("http://u4.tdimg.com/7/203/19/46138657748730920288026757971472766587.jpg");
			bannerList.add("http://www.cnnb.com.cn/pic/0/01/49/86/1498602_864010.jpg");
			bannerList.add("http://u3.tdimg.com/6/88/143/_56696781343356143444965292996172123406.jpg");
			bannerList.add("http://i3.cqnews.net/news/attachement/jpg/site82/2011-07-27/4386628352243053135.jpg");
			
			if(mGetBannerResponce!=null)
			{
				mGetBannerResponce.success(bannerList);
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
	* @ClassName: GetBannerResponce 
	* @Description: 请求获取营销广告banner图片url
	* @author enlizhang
	* @date 2015年7月26日 下午4:24:12 
	*
	 */
	interface GetBannerResponce extends IBaseResponse
	{
		/***
		 * 请求成功返回banner图片url
		 */
		public void success(List<String> bannerList);
	}
	
	
	
}

