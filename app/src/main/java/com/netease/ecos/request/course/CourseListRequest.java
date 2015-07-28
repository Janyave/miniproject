package com.netease.ecos.request.course;

import com.netease.ecos.model.Course;
import com.netease.ecos.model.Course.CourseType;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/***
 * 
* @ClassName: CourseListRequest 
* @Description: 获取教程列表
* @author enlizhang
* @date 2015年7月26日 上午11:01:18 
*
 */
public class CourseListRequest extends BaseRequest{

	//请求参数
	/** 获取教程列表方式，包括推荐和筛选 */
	public static final String TYPE = "type";

	/** 教程类型{@link Course.CourseType} (仅在{@link #TYPE}为推荐时有效) */
	public static final String COURSE_TYPE = "filter_type";

	/** 关键字(仅在{@link #TYPE}为推荐时有效) */
	public static final String KEY_WORD = "key_word";

	/** 排序规则{@link SortRule}，包括时间、被关注数、被点赞数, (仅在{@link #TYPE}为推荐时有效) */
	public static final String SORT_RULE = "sort_rule";

	//响应参数键
	ICourseListResponse mCourseListRespnce;


	public void request(ICourseListResponse courseListRespnce, final Type type, final CourseType courseType
			,final String keyWord, final SortRule sortRule)
	{
		super.initBaseRequest(courseListRespnce);
		mCourseListRespnce = courseListRespnce;

		responceSuccess(new JSONObject().toString());
		
		/*MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_COURSE_LIST_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(TYPE, type.getValue());
	        	
	        	//如果当前获取方式是筛选
	        	if(type == Type.筛选){
	        		map.put(COURSE_TYPE, courseType.getBelongs());
		        	map.put(KEY_WORD, keyWord);
		        	map.put(SORT_RULE, sortRule.getValue());
	        	}
	        	
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, CourseListRequest.this.getUrl(RequestUrlConstants.GET_COURSE_LIST_URL, map));
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

			List<Course> courseList = getTestCourseList();

			if(mCourseListRespnce!=null)
			{
				mCourseListRespnce.success(courseList);
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
	 * @ClassName: Type
	 * @Description: 获取教程列表的方式，包括推荐和筛选
	 * @author enlizhang
	 * @date 2015年7月26日 下午4:45:32
	 *
	 */
	public static enum Type
	{
		推荐("recommended"),
		筛选("filter");

		public String type;

		Type(String _type){
			type = _type;
		}

		public String getValue(){
			return type;
		}
	}

	/***
	 *
	 * @ClassName: SortRule
	 * @Description: 排序规则，包括时间、被关注数、被点赞数
	 * @author enlizhang
	 * @date 2015年7月26日 下午4:53:39
	 *
	 */
	public static enum SortRule
	{
		时间("time"),
		被关注数("praised"),
		被点赞数("followed");

		public String rule;

		SortRule(String _rule){
			rule = _rule;
		}

		public String getValue(){
			return rule;
		}
	}

	/***
	 *
	 * @ClassName: CourseListRespnce
	 * @Description: 获取教程列表响应回调
	 * @author enlizhang
	 * @date 2015年7月26日 下午4:59:51
	 *
	 */
	public interface ICourseListResponse extends IBaseResponse{

		/** 请求成功，返回教程列表 */
		public void success(List<Course> courseList);

	}


	public List<Course> getTestCourseList(){

		List<Course> courseList = new ArrayList<Course>();

		Course course;

		List<String> bannerList = new ArrayList<String>();
		bannerList.add("http://u4.tdimg.com/7/203/19/46138657748730920288026757971472766587.jpg");
		bannerList.add("http://www.cnnb.com.cn/pic/0/01/49/86/1498602_864010.jpg");
		bannerList.add("http://u3.tdimg.com/6/88/143/_56696781343356143444965292996172123406.jpg");
		bannerList.add("http://i3.cqnews.net/news/attachement/jpg/site82/2011-07-27/4386628352243053135.jpg");

		for(int i=0;i<2;i++){
			course = new Course();
			course.coverUrl = bannerList.get(i);
			course.title = "鸣人系列教程-"+i;
			course.author = "小鸣人 -" + i;
			course.courseId = "" + i;
			course.userId = "" + i;
			course.praiseNum = i*100;

			courseList.add(course);
		}


		return courseList;
	}

}

