package com.netease.ecos.request.course;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static final String COURSE_JSON = "courseJson";

	//响应参数键
	ICreateCourseResponse mCreateCourseResponce;

	Course mCourse;

	public void request(ICreateCourseResponse createCourseResponce, final Course course)
	{
		super.initBaseRequest(createCourseResponce);
		mCreateCourseResponce = createCourseResponce;

		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.CREATE_COURSE_URL,  this, this) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = getRequestBasicMap();

				map.put(COURSE_JSON, getRequestCourseJson(course));

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
		//		traceNormal(TAG, jstring);

		try {
			JSONObject json = new JSONObject(jstring).getJSONObject(KEY_DATA);


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
	public interface ICreateCourseResponse extends IBaseResponse
	{
		/***
		 * 请求成功
		 */
		public void success(Course course);
	}


	/***
	 * 获取Course的JSON串
	 * @param course
	 * @return
	 */
	public String getRequestCourseJson(Course course){
		Map<Object,Object> jsonMap = new HashMap<Object,Object>();


		//		course.title = "sige nihao";
		//		course.courseType= CourseType.妆娘;
		//		course.coverUrl = "http://img3.cache.netease.com/house/2015/7/3/20150703163657b0237_550.jpg";

		jsonMap.put("title",course.title);
		jsonMap.put("courseType",course.courseType.getBelongs());
		jsonMap.put("coverUrl",course.coverUrl);

		List<String> imageUrls = new ArrayList<String>();
		List<String> descriptionUrls = new ArrayList<String>();

		int length = course.stepList.size();
		for(int i=0;i<length;i++){
			imageUrls.add(course.stepList.get(i).imageUrl);
			descriptionUrls.add(course.stepList.get(i).description);
		}

		jsonMap.put("imageUrls", new JSONArray(imageUrls));
		jsonMap.put("descriptions", new JSONArray(descriptionUrls));


		return new JSONObject(jsonMap).toString();
	}
}

