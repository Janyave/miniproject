package com.netease.ecos.request.user;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.AccountDataService;
import com.netease.ecos.model.Share;
import com.netease.ecos.model.Course.CourseType;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;
import com.netease.ecos.request.NorResponce;

/***
 *
 * @ClassName: SendAutocodeRequest
 * @Description: 发送验证码
 * @author enlizhang
 * @date 2015年7月26日 上午10:37:51
 *
 */
public class SendAutocodeRequest extends BaseRequest{

	//请求参数键
	/*** 请求类型,  get或者check */
	public static final String TYPE = "type";

	/*** 手机号 */
	public static final String PHONE = "phone";

	/*** 验证码 */
	public static final String AUTOCODE = "autocode";


	//响应参数键


	/** 请求结束回掉函数 */
	ISendAutocodeResponse mSendAutocodeResponse;

	public void requestSend(ISendAutocodeResponse sendAutocodeResponse, final String phone)
	{
		super.initBaseRequest(sendAutocodeResponse);
		mSendAutocodeResponse = sendAutocodeResponse;


		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.AUTO_CODE_URL,  this, this) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();

				map.put(TYPE, "get");
				map.put(PHONE, phone);

				traceNormal(TAG, map.toString());
				traceNormal(TAG, SendAutocodeRequest.this.getUrl(RequestUrlConstants.AUTO_CODE_URL, map));
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

			if(mSendAutocodeResponse!=null)
			{

				String autocode = getString(json, "code");
				//保存验证码到本地
				AccountDataService.getSingleAccountDataService(getContext()).saveAutocode(autocode);

				Log.i("保存的验证码", AccountDataService.getSingleAccountDataService(getContext()).getAutoCode());

				mSendAutocodeResponse.success();
			}
			else
			{
				traceError(TAG,"回调接口为null");
			}


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if(mSendAutocodeResponse!=null)
			{
				mSendAutocodeResponse.doAfterFailedResponse("json异常");
			}
		}

	}

	/***
	 *
	 * @ClassName: ActionType
	 * @Description: 验证码功能类别
	 * @author enlizhang
	 * @date 2015年7月31日 上午11:21:00
	 *
	 */
	public static enum ActionType {

		获取验证码("get"),
		核对验证码("check");

		private String value;

		private ActionType(String _value ) {
			this.value = _value;
		}

		public String getValue() {
			return value;
		}

		public static ActionType getActionType(String value){
			for(ActionType actionType:ActionType.values()){
				if(actionType.getValue().equals(value))
					return actionType;
			}

			return null;
		}

	}

	/**
	 *
	 * @ClassName: ISendAutocodeResponse
	 * @Description: 发送验证码回掉接口
	 * @author enlizhang
	 * @date 2015年7月31日 下午12:50:09
	 *
	 */
	public interface ISendAutocodeResponse extends IBaseResponse{

		/*** 发送成功回调函数  */
		public void success();

	}


}

