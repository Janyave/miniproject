package com.netease.ecos.request.course;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 *
 * @ClassName: AssignmentListRequest
 * @Description: 获取教程作业列表
 * @author enlizhang
 * @date 2015年7月26日 上午11:00:45
 *
 */
public class AssignmentListRequest extends BaseRequest{

	//请求参数键
	/** 教程id */
	public static final String COURSE_ID = "courseId";


	//响应参数键
	IAssignmentListResponse mAssignmentListRespnce;


	public void request(IAssignmentListResponse assignmentListRespnce, final String courseId,final int pages)
	{
		super.initBaseRequest(assignmentListRespnce);
		mAssignmentListRespnce  = assignmentListRespnce;

		//		responceSuccess("");
		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_ASSIGNMENT_LIST_URL,  this, this) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = getRequestBasicMap();

				map.put(COURSE_ID, courseId);

				map.put(KEY_PAGE_INDEX, String.valueOf(pages));
				map.put(KEY_PAGE_SIZE, String.valueOf(DEFAULT_PAGE_SIZE));

				traceNormal(TAG, map.toString());
				traceNormal(TAG, AssignmentListRequest.this.getUrl(RequestUrlConstants.GET_ASSIGNMENT_LIST_URL, map));
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
			JSONObject json = new JSONObject(jstring).getJSONObject(KEY_DATA);

			JSONArray assignmentJA = json.getJSONArray("assignments");
			int length = assignmentJA.length();

			List<Assignment> assignmentList = new ArrayList<Assignment>();
			for(int i=0;i<length;i++){
				JSONObject assignmentJO = assignmentJA.getJSONObject(i);

				Assignment assignment = new Assignment();
				assignment.assignmentId = getString(assignmentJO, "assignmentId");
				assignment.userId = getString(assignmentJO, "userId");
				assignment.author = getString(assignmentJO, "nickname");
				assignment.authorAvatarUrl = getString(assignmentJO, "authorAvatarUrl");

				assignment.imageUrl = getString(assignmentJO, "imgUrl");
				assignment.content = getString(assignmentJO, "description");
				assignment.issueTimeStamp = Long.valueOf(getString(assignmentJO, "issueTimeStamp")).longValue();
				assignmentList.add(assignment);

			}

			if(mAssignmentListRespnce!=null)
			{
				mAssignmentListRespnce.success(assignmentList);
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
	 * @ClassName: AssignmentListRespnce
	 * @Description: 获取教程作业列表
	 * @author enlizhang
	 * @date 2015年7月26日 下午5:23:17
	 *
	 */
	public interface IAssignmentListResponse extends IBaseResponse{

		/** 请求成功，返回教程列表 */
		public void success(List<Assignment> assignmentList);

	}

	/***
	 * 获取测试作业列表
	 * @return
	 */
	public List<Assignment> getTestAssignmentList(){

		List<String> imageList = new ArrayList<String>();

		imageList.add("http://img4.duitang.com/uploads/item/201401/05/20140105203829_rwYHC.thumb.700_0.jpeg");
		imageList.add("http://i2.topit.me/2/a1/4a/1187638510f924aa12o.jpg");
		imageList.add("http://dmimg.5054399.com/allimg/optuji/hzcos3/74.jpg");
		imageList.add("http://images.17173.com/2012/news/2012/06/13/y0613cos30s.jpg");
		imageList.add("http://hiphotos.baidu.com/694310353/pic/item/5fee232226a550804423e868.jpg");
		imageList.add("http://images.17173.com/2012/news/2012/03/01/y0301cos05s.jpg");
		imageList.add("http://img4.duitang.com/uploads/item/201411/29/20141129225352_Yt5iL.png");

		int assignmentNum = 5;
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

		return assignmentList;
	}
}

