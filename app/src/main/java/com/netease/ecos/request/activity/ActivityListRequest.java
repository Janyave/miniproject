package com.netease.ecos.request.activity;

import com.netease.ecos.model.ActivityModel;
import com.netease.ecos.model.ActivityModel.ActivityType;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/***
 * 
* @ClassName: ActivityListRequest 
* @Description: 活动列表
* @author enlizhang
* @date 2015年7月26日 下午12:58:47 
*
 */
public class ActivityListRequest extends BaseRequest{

	//请求参数键



	//响应参数键
	public IActivityListResponse mActivityListResponse;

	/***
	 *
	 * @param baseresponce
	 * @param provinceId 省id
	 * @param activityType {@link ActivityType}活动类型
	 * @param pageIndex 请求页数
	 */
	public void request(IActivityListResponse activityListResponse, final String provinceId, final ActivityType activityType,
						final int pageIndex)
	{
		super.initBaseRequest(activityListResponse);
		mActivityListResponse = activityListResponse;

		mActivityListResponse.success(getTestActivityList());
		//		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_ACTIVITY_LIST_URL,  this, this) {
		//	        @Override
		//	        protected Map<String, String> getParams() throws AuthFailureError {
		//	        	Map<String, String> map = getRequestBasicMap();
		//
		//	        	map.put("provinceId", provinceId);
		//	        	map.put("activityType", activityType.getValue());
		//	        	map.put(KEY_PAGE_SIZE, String.valueOf(DEFAULT_PAGE_SIZE));
		//	        	map.put(KEY_PAGE_INDEX, String.valueOf(pageIndex));
		//
		//	            traceNormal(TAG, map.toString());
		//	            traceNormal(TAG, ActivityListRequest.this.getUrl(RequestUrlConstants.GET_ACTIVITY_LIST_URL, map));
		//	            return map;
		//	        }
		//
		//	    };
		//
		//	    stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		//
		//	    getQueue().add(stringRequest);

	}

	@Override
	public void responceSuccess(String jstring) {
		traceNormal(TAG, jstring);

		try {
			JSONObject json = new JSONObject(jstring).getJSONObject(KEY_DATA);
			JSONObject activitJO = json;

			JSONArray activityJA = activitJO.getJSONArray("activitys");
			int length = activityJA.length();

			List<ActivityModel> activityList = new ArrayList<ActivityModel>();
			for(int i=0;i<length;i++){
				JSONObject activityJO = activityJA.getJSONObject(i);
				ActivityModel acitivty = new ActivityModel();
				acitivty.activityId = activityJO.getString("activityId");
				acitivty.coverUrl = activityJO.getString("activityLogoUrl");
				acitivty.title = activityJO.getString("title");

				acitivty.activityTime.startDateStamp = Long.valueOf(activityJO.getString("startTime")).longValue();
				acitivty.activityTime.endDateStamp = Long.valueOf(activityJO.getString("endTime")).longValue();
				acitivty.activityTime.dayStartTime = activityJO.getString("dayStartTime");
				acitivty.activityTime.dayEndTime = activityJO.getString("dayEndTime");



				acitivty.location.city.cityName = activityJO.getString("cityName");
				acitivty.location.city.cityCode = activityJO.getString("cityCode");
				acitivty.location.address = activityJO.getString("address");

				acitivty.issueTimeStamp = Long.valueOf(activityJO.getString("issueTimeStamp")).longValue();
				acitivty.activityType = ActivityType.getActivityTypeByValue(activityJO.getString("activityType"));

				activityList.add(acitivty);

			}

			if(mActivityListResponse!=null)
			{
				mActivityListResponse.success(activityList);
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
	 * @ClassName: IActivityListResponse
	 * @Description: 获取活动列表回调接口
	 * @author enlizhang
	 * @date 2015年7月30日 下午1:41:06
	 *
	 */
	public interface IActivityListResponse extends IBaseResponse{

		/** 请求成功，返回教程列表 */
		public void success(List<ActivityModel> activityList);

	}


	public List<ActivityModel> getTestActivityList(){

		List<ActivityModel> activityList = new ArrayList<ActivityModel>();
		for(int i=0;i<2;i++){

			ActivityModel acitivty = new ActivityModel();
			acitivty.activityId = "" + i;
			acitivty.coverUrl = "http://images.china.cn/attachement/jpg/site1000/20120711/001f1632fbfd116783094c.jpg";
			acitivty.title = "活动列表测试" + i;

			acitivty.activityTime.startDateStamp = System.currentTimeMillis() - 24*60*60*1000;
			acitivty.activityTime.endDateStamp = System.currentTimeMillis();

			acitivty.activityTime.dayStartTime = "10:00";
			acitivty.activityTime.dayEndTime = "17:00";



			acitivty.location.city.cityName = "杭州市";
			acitivty.location.city.cityCode = "12";
			acitivty.location.address = "滨江区网商路599号网易";

			acitivty.activityType = ActivityType.同人展;

			activityList.add(acitivty);

		}

		return activityList;
	}

}

