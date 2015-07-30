package com.netease.ecos.request.activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.ActivityModel;
import com.netease.ecos.model.ActivityModel.ActivityType;
import com.netease.ecos.model.ActivityModel.ContactWay;
import com.netease.ecos.model.User;
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
 * @ClassName: GetActivityDetailRequest
 * @Description: 获取活动详情
 * @author enlizhang
 * @date 2015年7月26日 下午1:00:05
 *
 */
public class GetActivityDetailRequest extends BaseRequest{

	//请求参数键

	//响应参数键
	IActivityDetailResponse mActivityDetailResponse;


	public void request(IActivityDetailResponse activityDetailResponse, final String activityId)
	{
		super.initBaseRequest(activityDetailResponse);
		mActivityDetailResponse = activityDetailResponse;

		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_ACTIVITY_DETAIL_URL,  this, this) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = getRequestBasicMap();

				map.put("activityId", activityId);

				traceNormal(TAG, map.toString());
				traceNormal(TAG, GetActivityDetailRequest.this.getUrl(RequestUrlConstants.GET_ACTIVITY_DETAIL_URL, map));
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
			JSONObject activityJO = json;

			ActivityModel activity = new ActivityModel();
			activity.activityId = activityJO.getString("activityId");
			activity.title = activityJO.getString("title");
			activity.activityType = ActivityType.getActivityTypeByValue(activityJO.getString("activityType"));
			activity.coverUrl = activityJO.getString("logoUrl");


			activity.activityTime.startDateStamp = Long.valueOf(activityJO.getString("startTime")).longValue();
			activity.activityTime.endDateStamp = Long.valueOf(activityJO.getString("endTime")).longValue();
			activity.activityTime.dayStartTime = activityJO.getString("dayStartTime");
			activity.activityTime.dayEndTime = activityJO.getString("dayEndTime");

			activity.issueTimeStamp = Long.valueOf(activityJO.getString("issueTimeStamp")).longValue();

			activity.nickname = activityJO.getString("description");
			activity.nickname = activityJO.getString("fee");
			//发布者头像url
			activity.nickname = activityJO.getString("avatarUrl");
			//发布者姓名
			activity.nickname = activityJO.getString("nickName");


			activity.location.city.cityName = activityJO.getString("cityName");
			activity.location.city.cityCode = activityJO.getString("cityCode");
			activity.location.address = activityJO.getString("address");

			JSONArray contactWayJA = activityJO.getJSONArray("contacts");

			List<ContactWay> contactWayList = new ArrayList<ContactWay>();
			for(int i=0;i<contactWayJA.length();i++){
				JSONObject contactWayJO = contactWayJA.getJSONObject(i);
				ContactWay contactWay = ContactWay.getContactWayByValue(contactWayJO.getString("contactType"));
				contactWay.setValue(contactWayJO.getString("contactValue"));

				contactWayList.add(contactWay);
			}
			activity.contactWayList = contactWayList;

			JSONArray suAvatarUrls = activityJO.getJSONArray("suAvatarUrls");
			for(int i=0;i<suAvatarUrls.length();i++){
				User user = new User();
				user.avatarUrl = suAvatarUrls.getString(i);

				activity.signUpUseList.add(user);
			}

			activity.hasSignuped = activityJO.getBoolean("hasSignuped");
			activity.hasStarted = activityJO.getBoolean("hasStarted");

			if(mActivityDetailResponse!=null)
			{
				mActivityDetailResponse.success(activity);
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
	 * @ClassName: IActivityDetailResponse
	 * @Description: 活动详情请求回掉接口
	 * @author enlizhang
	 * @date 2015年7月30日 下午4:56:28
	 *
	 */
	public interface IActivityDetailResponse extends IBaseResponse{

		/***
		 * 请求成功回调函数，并返回活动详情
		 * @param activity
		 */
		public void success(ActivityModel activity);

	}

}

