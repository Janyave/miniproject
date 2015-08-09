package com.netease.ecos.request.user;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constants.RequestUrlConstants;
import com.netease.ecos.model.User;
import com.netease.ecos.model.User.Gender;
import com.netease.ecos.model.User.RoleType;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
/***
 *
 * @ClassName: GetUserInfoRequest
 * @Description: 获取用户信息
 * @author enlizhang
 * @date 2015年7月26日 上午10:30:34
 *
 */

public class GetUserInfoRequest extends BaseRequest{


	public IGetUserInfoResponse mGetUserInfoResponse;

	/**
	 * 请求其他用户信息
	 * @param getUserInfoResponse
	 * @param userId 其他用户id
	 */
	public void requestOtherUserInfo(IGetUserInfoResponse getUserInfoResponse,final String userId)
	{
		super.initBaseRequest(getUserInfoResponse);
		mGetUserInfoResponse = getUserInfoResponse;

		//		mGetUserInfoResponse.success(getTestUser());

		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_USER_INFO,  this, this) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String,String>();
				map.put(KEY_TOKEN, getToken());
				map.put("userId", userId);

				traceNormal(TAG, map.toString());
				traceNormal(TAG, GetUserInfoRequest.this.getUrl(RequestUrlConstants.GET_USER_INFO, map));
				return map;
			}

		};

		stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		getQueue().add(stringRequest);

	}

	/**
	 * 请求个人用户信息
	 * @param getUserInfoResponse
	 */
	public void requestPersonalInfo(IGetUserInfoResponse getUserInfoResponse)
	{
		super.initBaseRequest(getUserInfoResponse);
		mGetUserInfoResponse = getUserInfoResponse;
		//		mGetUserInfoResponse.success(getTestUser());

		MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_USER_INFO,  this, this) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = getRequestBasicMap();

				traceNormal(TAG, map.toString());
				traceNormal(TAG, GetUserInfoRequest.this.getUrl(RequestUrlConstants.GET_USER_INFO, map));
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
			JSONObject usreJO = new JSONObject(jstring).getJSONObject(KEY_DATA);

			User user = new User();
			user.userId = getString(usreJO,"userId");
			user.imId = getString(usreJO,"imId");
			user.avatarUrl = getString(usreJO,"avatarUrl");
			user.nickname = getString(usreJO,"nickname");

			user.characterSignature = getString(usreJO,"characterSignature");
			user.coverUrl = getString(usreJO,"coverUrl");
			user.avatarUrl = getString(usreJO,"avatarUrl");
			user.fansNum = getString(usreJO,"fansNum");
			user.followOtherNum = getString(usreJO,"followOtherNum");

			if(usreJO.has("gender") && !usreJO.isNull("gender")){
				user.gender = Gender.getGender( usreJO.getString("gender") );
			}

			if(usreJO.has("roles") && !usreJO.isNull("roles")){
				JSONArray rolesJA = new JSONArray(getString(usreJO,"roles"));
				Set<RoleType> roleTypeSet = new LinkedHashSet<RoleType>();
				for(int i=0;i<rolesJA.length();i++){
					roleTypeSet.add( RoleType.getRoleTypeByValue(rolesJA.getString(i)) );
				}
			}

//			UserDataService.getSingleUserDataService(MyApplication.getContext()).saveUser(user);
//			AccountDataService.getSingleAccountDataService(MyApplication.getContext()).saveUserAccId(user.imId);
//			AccountDataService.getSingleAccountDataService(MyApplication.getContext()).saveUserId(user.userId);

			if(mGetUserInfoResponse!=null)
			{
				mGetUserInfoResponse.success(user);
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
	 * @ClassName: IGetUserInfoResponse
	 * @Description: 获取个人信息响应回调接口
	 * @author enlizhang
	 * @date 2015年7月31日 下午8:16:08
	 *
	 */
	public interface IGetUserInfoResponse extends IBaseResponse{

		/*** 获取用户请求成功回掉，并返回用户信息  */
		public void success(User user);

	}


	public User getTestUser(){

		User user = new User();

		user.userId = "123";
		user.imId = "321";
		user.avatarUrl = "http://img5.imgtn.bdimg.com/it/u=2797503391,4239472514&fm=21&gp=0.jpg";
		user.nickname = "小张";

		user.characterSignature = "网易遇见最美年华";
		user.coverUrl = "http://img4.imgtn.bdimg.com/it/u=2501638931,3725345607&fm=21&gp=0.jpg";
		user.fansNum = "100";
		user.followOtherNum = "5";
		user.gender = Gender.getGender("1");

		Set<RoleType> roleTypeSet = new LinkedHashSet<RoleType>();
		roleTypeSet.add(RoleType.妆娘);
		roleTypeSet.add(RoleType.后期);
		roleTypeSet.add(RoleType.妆娘);
		user.roleTypeSet = roleTypeSet;

		return user;
	}

}

