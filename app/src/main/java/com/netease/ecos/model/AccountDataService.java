package com.netease.ecos.model;

import android.content.Context;
import android.content.SharedPreferences;

/***
 * 
* @ClassName: AccountDataService 
* @Description: TODO(管理帐号信息，保罗) 
* @author enlizhang
* @date 2015年1月26日 下午2:20:42 
*
 */
public class AccountDataService {

	private final static String TAG = "AccountDataService";

	private final String PREFERENCE_NAME = "AccountData";
	private final int READ_MODE = Context.MODE_WORLD_READABLE;
	private final int WRITE_MODE = Context.MODE_WORLD_WRITEABLE;

	/****
	 * 存储{@link User#userId}
	 */
	private final static String USER_ID = "userId";

	/****
	 * 存储{@link User#accid}
	 */
	private final static String USER_ACCID = "accid";

	/****
	 * 存储token
	 */
	private final static String TOKEN = "token";

	/*** 注册验证码 */
	private final static String REGIST_VALIDATE_CODE = "registValidateCode";


	/*** 重置密码验证码 */
	private final static String RESET_PWD_VALIDATE_CODE = "resetPwdValidateCode";

	private String DEFAULT_VALUE = "";

	private static AccountDataService singleAccountDataService = null;

	private  static Context mContext;


	private AccountDataService(Context context)
	{
		mContext = context;
	}

	/***
	 * 返回AccountDataService类单例操作对象
	 * @param context 若mContext为null，则根据context进行创建，此时必须保证context!=null.<br>
	 * 					否则，context不进行使用
	 * @return
	 */
	public static AccountDataService getSingleAccountDataService(Context context) {
		if(singleAccountDataService == null && mContext==null)
		{
			singleAccountDataService = new AccountDataService(context);
		}
		return singleAccountDataService;
	}

	/***
	 * 保存userid
	 * @param userId 用户id
	 */
	public void saveUserId(String userId)
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,WRITE_MODE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(USER_ID, userId);
		editor.commit();
	}


	/***
	 * 保存云信accid
	 * @param userId 用户id
	 */
	public void saveUserAccId(String accid)
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,WRITE_MODE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(USER_ACCID, accid);
		editor.commit();
	}

	/***
	 * 保存token
	 * @param token 请求令牌
	 */
	public void savetToken(String token)
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,WRITE_MODE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(TOKEN, token);
		editor.commit();
	}




	/***
	 * 保存注册验证码
	 * @param registValidateCode 注册验证码
	 */
	public void saveRegistValidateCode(String registValidateCode)
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,WRITE_MODE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(REGIST_VALIDATE_CODE, registValidateCode);
		editor.commit();
	}

	/***
	 * 保存重置密码验证码
	 * @param resetPwdValidateCode 重置密码验证码
	 */
	public void saveResetPwdValidateCode(String resetPwdValidateCode)
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,WRITE_MODE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(RESET_PWD_VALIDATE_CODE, resetPwdValidateCode);
		editor.commit();
	}


	/****
	 * 获取当前用户userId
	 * @return 若无则返回null
	 */
	public String getUserId()
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,READ_MODE);

		return sharedPreferences.getString(USER_ID, null);
	}


	/****
	 * 获取当前用户云信accid
	 * @return 若无则返回null
	 */
	public String getUserAccId()
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,READ_MODE);

		return sharedPreferences.getString(USER_ACCID, null);
	}

	/****
	 * 获取token
	 * @return 若无则返回null
	 */
	public String getToken()
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,READ_MODE);

		return sharedPreferences.getString(TOKEN, "");
	}

	/****
	 * 获取注册验证码
	 * @return 若无则返回"-1"
	 */
	public String getRegistValidateCode()
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,READ_MODE);
		return sharedPreferences.getString(REGIST_VALIDATE_CODE, "-1");
	}

	/****
	 * 获取重置密码验证码
	 * @return 若无则返回"-1"
	 */
	public String getResetPwdValidateCode()
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,READ_MODE);
		return sharedPreferences.getString(RESET_PWD_VALIDATE_CODE, "-1");
	}

	/**
	 * 清除所有数据
	 */
	public void clearAllDataExceptUsername()
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,WRITE_MODE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.commit();
	}


}

