package com.netease.ecos.request.course;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constant.RequestUrlConstants;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;
import com.netease.ecos.request.NorResponce;

/***
 * 
* @ClassName: CreateCourseRequest 
* @Description: 创建教程
* @author enlizhang
* @date 2015年7月26日 上午11:05:39 
*
 */
public class CreateCourseRequest extends BaseRequest{
	
	//请求参数键
	/*** Course对象数据json串，空的属性用"null"字符串 */
	public static final String COURSE_JSON = "course_json";
	
	//响应参数键
	CreateCourseResponce mCreateCourseResponce;
	
	Course mCourse;
	
	public void request(CreateCourseResponce createCourseResponce, final Course course)
	{
		super.initBaseRequest(createCourseResponce);
		mCreateCourseResponce = createCourseResponce;
		
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.CREATE_COURSE_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(COURSE_JSON, course.getRequestJson());
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, CreateCourseRequest.this.getUrl(RequestUrlConstants.CREATE_COURSE_URL, map));
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
			
			
			if(mCreateCourseResponce!=null)
			{
				mCreateCourseResponce.success(mCourse);
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
	* @ClassName: CreateCourseResponce 
	* @Description: 创建教程请求响应
	* @author enlizhang
	* @date 2015年7月26日 下午4:34:42 
	*
	 */
	interface CreateCourseResponce extends IBaseResponse
	{
		/***
		 * 请求成功
		 */
		public void success(Course course);
	}
	
}

