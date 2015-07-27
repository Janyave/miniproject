package com.netease.ecos.request.course;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constant.RequestUrlConstants;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

/***
 * 
* @ClassName: CreateAssignmentRequest 
* @Description: 创建教程作业
* @author enlizhang
* @date 2015年7月26日 上午11:06:05 
*
 */
public class CreateAssignmentRequest extends BaseRequest{
	
	//请求参数键
	/** 教程id */
	public static final String COURSE_ID = "course_id";
	
	/** 图片url */
	public static final String IMAGE_URL = "img";
	
	/** 描述 */
	public static final String DESCRIPTION = "description";
	
	/** 作者 */
	public static final String AUTHOR = "author";
	
	
	
	Assignment mAssignment;
	
	ICreateAssignmentResponce mCreateAssignmentResponce;
	
	public void request(ICreateAssignmentResponce createAssignmentResponce, 
			final String courseId, final Assignment assignment)
	{
		super.initBaseRequest(createAssignmentResponce);
		mAssignment = assignment;
		mCreateAssignmentResponce = createAssignmentResponce;
		
		responceSuccess(new JSONObject().toString());
		
		/*MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.CREATE_ASSIGNMENT_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(COURSE_ID, courseId);
	        	map.put(IMAGE_URL, assignment.imageUrl);
	        	map.put(DESCRIPTION, assignment.content);
	        	map.put(AUTHOR, assignment.author);
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, CreateAssignmentRequest.this.getUrl(RequestUrlConstants.CREATE_ASSIGNMENT_URL, map));
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
			
			
			if(mCreateAssignmentResponce!=null)
			{
				mCreateAssignmentResponce.success(mAssignment);
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
	* @ClassName: CreateAssignmentResponce 
	* @Description: 创建教程作业回调函数
	*
	 */
	public interface ICreateAssignmentResponce extends IBaseResponse{
		
		/** 创建教程作业成功，并返回添加的教程作业 */
		public void success(Assignment assignment);
	}
}

