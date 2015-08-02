package com.netease.ecos.request.course;

import com.netease.ecos.model.Course;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.model.Course.Step;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/***
 * 
* @ClassName: GetCourseDetailRequest 
* @Description: 获取教程详情
* @author enlizhang
* @date 2015年7月26日 上午11:03:17 
*
 */
public class GetCourseDetailRequest extends BaseRequest{

	//请求参数键
	/** 教程id */
	public static final String COURSE_ID = "course_id";

	//响应参数键
	ICourseDetailResponse mCourseDetailRespnce;

	public void request(ICourseDetailResponse courseDetailRespnce, final String courseId)
	{
		super.initBaseRequest(courseDetailRespnce);
		mCourseDetailRespnce = courseDetailRespnce;

		responceSuccess(new JSONObject().toString());

		/*MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_COURSE_DETAIL_URL,  this, this) {
	        @Override
	        protected Map<String, String> getParams() throws AuthFailureError {
	        	Map<String, String> map = getRequestBasicMap();

	        	map.put(COURSE_ID, courseId);

	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, GetCourseDetailRequest.this.getUrl(RequestUrlConstants.GET_COURSE_DETAIL_URL, map));
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


			if(mCourseDetailRespnce!=null)
			{
				mCourseDetailRespnce.success(getTestCourse());
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
	 * @ClassName: CourseDetailRespnce
	 * @Description: 请求教程详情回调函数
	 * @author enlizhang
	 * @date 2015年7月26日 下午5:06:49
	 *
	 */
	public interface ICourseDetailResponse extends IBaseResponse{

		/** 请求成功，返回教程详情 */
		public void success(Course course);

	}


	public Course getTestCourse(){


		Course course;

		List<String> bannerList = new ArrayList<String>();
		bannerList.add("http://u4.tdimg.com/7/203/19/46138657748730920288026757971472766587.jpg");
		bannerList.add("http://www.cnnb.com.cn/pic/0/01/49/86/1498602_864010.jpg");
		bannerList.add("http://u3.tdimg.com/6/88/143/_56696781343356143444965292996172123406.jpg");
		bannerList.add("http://i3.cqnews.net/news/attachement/jpg/site82/2011-07-27/4386628352243053135.jpg");

		course = new Course();
		course.coverUrl = bannerList.get(0);
		course.title = "鸣人系列教程-"+1;
		course.author = "小鸣人 -" + 1;
		course.authorAvatarUrl = "http://p1.gexing.com/G1/M00/9E/A6/rBACE1J-AI7xPAUWAAAa1SSMm94668_200x200_3.jpg?recache=20131108";
		course.courseId = "" + 1;
		course.userId = "" + 1;
		course.praiseNum = 1*100;

		List<String> stepImageList = new ArrayList<String>();
		stepImageList.add("http://images.ali213.net/picfile/pic/2015/03/25/927_2015032554559561.jpg");
		stepImageList.add("http://p4.gexing.com/shaitu/20121006/1240/506fb646b32e5.jpg");
		stepImageList.add("http://img4.duitang.com/uploads/item/201406/07/20140607184856_jvXRz.thumb.700_0.jpeg");
		stepImageList.add("http://i8.topit.me/8/1f/65/1128323017f88651f8l.jpg");
		stepImageList.add("http://img2.duitang.com/uploads/item/201208/29/20120829120608_eymUK.thumb.600_0.jpeg");
		stepImageList.add("http://images.17173.com/2012/news/2012/01/25/y0125cos25s.jpg");

		Step step;
		for(int i=0;i<5;i++){
			step= new Step(i+1);
			step.imagePath = stepImageList.get(i);
			step.description = "这是测试" + i;
			course.addStep(step);
		}

		int assignmentNum = 5;
		course.assignmentNum = assignmentNum;

		List<String> imageList = new ArrayList<String>();

		imageList.add("http://img4.duitang.com/uploads/item/201401/05/20140105203829_rwYHC.thumb.700_0.jpeg");
		imageList.add("http://i2.topit.me/2/a1/4a/1187638510f924aa12o.jpg");
		imageList.add("http://dmimg.5054399.com/allimg/optuji/hzcos3/74.jpg");
		imageList.add("http://images.17173.com/2012/news/2012/06/13/y0613cos30s.jpg");
		imageList.add("http://hiphotos.baidu.com/694310353/pic/item/5fee232226a550804423e868.jpg");
		imageList.add("http://images.17173.com/2012/news/2012/03/01/y0301cos05s.jpg");
		imageList.add("http://img4.duitang.com/uploads/item/201411/29/20141129225352_Yt5iL.png");

		Assignment assignment;
		List<Assignment> assignmentList = new ArrayList<Assignment>();
		for(int i=0;i<assignmentNum;i++){
			assignment = new Assignment();

			assignment.author = "张恩立" + i;
			assignment.content = "张恩立的描述" + i;
			assignment.imageUrl = imageList.get(i);
			assignment.authorAvatarUrl = "http://p1.gexing.com/G1/M00/9E/A6/rBACE1J-AI7xPAUWAAAa1SSMm94668_200x200_3.jpg?recache=20131108";
			assignment.issueTimeStamp = System.currentTimeMillis();

			assignmentList.add(assignment);
		}
		course.assignmentList = assignmentList;
		course.commentNum = 200;
		return course;
	}
}

