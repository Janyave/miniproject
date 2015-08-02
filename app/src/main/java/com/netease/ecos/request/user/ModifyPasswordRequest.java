package com.netease.ecos.request.user;

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
 * @ClassName: ModifyPasswordRequest
 * @Description: 修改密码
 * @author enlizhang
 * @date 2015年7月26日 上午10:31:04
 *
 */
public class ModifyPasswordRequest extends BaseRequest{

	//请求参数键
	/*** 精度 */
	public static final String OLD_PWD = "old_pwd";

	/*** 维度 */
	public static final String NEW_PWD = "new_pwd";


	//响应参数键


	public void request(IBaseResponse baseresponce, final String oldPwd, final String newPwd)
	{
		super.initBaseRequest(baseresponce);

		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.MODIFY_PASSWORD,  this, this) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = getRequestBasicMap();

				map.put(OLD_PWD, oldPwd);
				map.put(NEW_PWD, newPwd);

				traceNormal(TAG, map.toString());
				traceNormal(TAG, ModifyPasswordRequest.this.getUrl(RequestUrlConstants.MODIFY_PASSWORD, map));
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


			if(mBaseResponse!=null)
			{
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

}

