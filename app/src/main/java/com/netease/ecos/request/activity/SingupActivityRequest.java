package com.netease.ecos.request.activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/***
 * 
* @ClassName: SingupActivityRequest 
* @Description: 
* @author enlizhang
* @date 2015年7月26日 下午1:00:33 
*
 */
public class SingupActivityRequest extends BaseRequest{

	//请求参数键
	public static final String ACTIVITY_ID = "activityId";


	//响应参数键
	public ISignupResponse mSignupResponse;


	public String mActivityId;
	public SignupType mSignupType;


	public void request(ISignupResponse signupResponse, final String activityId, final SignupType signupType)
	{
		super.initBaseRequest(signupResponse);
		mSignupResponse = signupResponse;

		mActivityId = activityId;
		mSignupType = signupType;

		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.CREATE_COMMENT_URL,  this, this) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = getRequestBasicMap();

				map.put(ACTIVITY_ID, activityId);
				map.put("signupType", signupType.getValue());

				traceNormal(TAG, map.toString());
				traceNormal(TAG, SingupActivityRequest.this.getUrl(RequestUrlConstants.CREATE_COMMENT_URL, map));
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


			if(mSignupResponse!=null)
			{
				mSignupResponse.success(mActivityId, mSignupType);
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
	 * @ClassName: SignupType
	 * @Description: 报名类型:报名或取消报名
	 * @author enlizhang
	 * @date 2015年7月30日 下午2:39:45
	 *
	 */
	public static enum SignupType
	{
		报名("signup"),
		取消报名("cancel");

		public String value;

		SignupType(String _value){
			value = _value;
		}

		public String getValue(){
			return value;
		}
	}

	/**
	 *
	 * @ClassName: ISignupResponse
	 * @Description: 报名回调接口
	 * @author enlizhang
	 * @date 2015年7月30日 下午2:56:32
	 *
	 */
	public interface ISignupResponse extends IBaseResponse{

		/** 请求成功，返回活动id和当前报名类型 */
		public void success(String activityId, SignupType signupType);

	}

}

