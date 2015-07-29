package com.netease.ecos.model;

import android.content.Context;
import android.content.SharedPreferences;

/***
 * 保存用户信息，并在每次修改用户信息时同步
 * @author enlizhang
 *
 */
public class UserDataService {

	private final static String TAG = "UserDataService";

	private final String PREFERENCE_NAME = "UserData";
	private final int READ_MODE = Context.MODE_WORLD_READABLE;
	private final int WRITE_MODE = Context.MODE_WORLD_WRITEABLE;

	/****
	 * 存储{@link User#userId}
	 */
	private final static String USER_ID = "userId";


	/*** 存储昵称{@link User#nickname} */
	public static final  String NICKNAME = "nickname";

	/*** 存储用户头像{@link User#avatarUrl} */
	public static final  String AVATAR_URL = "avatarUrl";

	/*** 存储个性签名{@link User#characterSignature} */
	public static final  String CHARACTER_SIGANATURE = "characterSignature";

	/*** 存储城市code{@link User#cityCode} */
	public static final  String CITY_CODE = "cityCode";

	/*** 存储城市名称{@link User#cityName} */
	public static final  String CITY_NAME = "cityName";

	/*** 存储主页封面{@link User#coverUrl} */
	public static final  String COVER_URL = "coverUrl";

	/*** 存储性别{@link User#gender} */
	public static final  String GENDER = "gender";

	/*** 存储关注其他的数量{@link User#followOtherNum} */
	public static final  String FOLLOW_OTHER_NUM = "followOtherNum";

	/*** 存储{@link User#fansNum} */
	public static final  String FANS_NUM = "fansNum";


	private String DEFAULT_VALUE = "";

	private static UserDataService singleUserDataService = null;

	private  static Context mContext;


	private UserDataService(Context context)
	{
		mContext = context;
	}
	
	/***
	 * 返回AccountDataService类单例操作对象
	 * @param context 若mContext为null，则根据context进行创建，此时必须保证context!=null.<br>
	 * 					否则，context不进行使用
	 * @return
	 */
	public static UserDataService getSingleUserDataService(Context context) {
		if(singleUserDataService == null && mContext==null)
		{
			singleUserDataService = new UserDataService(context);
		}
		return singleUserDataService;
	}
	
	/***
	 * 保存userid
	 * @param user 用户
	 */
	public void saveUser(User user)
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,WRITE_MODE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(USER_ID, user.userId);
		editor.putString(NICKNAME, user.nickname);
		editor.putString(AVATAR_URL, user.avatarUrl);
		editor.putString(CHARACTER_SIGANATURE, user.characterSignature);
		editor.putString(CITY_CODE, user.cityCode);
		editor.putString(CITY_NAME, user.cityName);
		editor.putString(COVER_URL, user.coverUrl);
		editor.putString(GENDER, user.gender.getValue());
		editor.putString(FOLLOW_OTHER_NUM, user.followOtherNum);
		editor.putString(FANS_NUM, user.fansNum);

		editor.commit();
	}
	

	/****
	 * 获取当前用户userId
	 * @return 若无则返回null
	 */
	public User getUser()
	{
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME,READ_MODE);

		User user = new User();

		user.userId = sharedPreferences.getString(USER_ID, DEFAULT_VALUE);
		user.nickname = sharedPreferences.getString(NICKNAME, DEFAULT_VALUE);
		user.avatarUrl = sharedPreferences.getString(AVATAR_URL, DEFAULT_VALUE);
		user.characterSignature = sharedPreferences.getString(CHARACTER_SIGANATURE, DEFAULT_VALUE);
		user.cityCode = sharedPreferences.getString(CITY_CODE, DEFAULT_VALUE);
		user.cityName = sharedPreferences.getString(CITY_NAME, DEFAULT_VALUE);
		user.coverUrl = sharedPreferences.getString(COVER_URL, DEFAULT_VALUE);
		user.gender = User.Gender.getGender(sharedPreferences.getString(GENDER, DEFAULT_VALUE));

		user.followOtherNum = sharedPreferences.getString(FOLLOW_OTHER_NUM, DEFAULT_VALUE);
		user.fansNum = sharedPreferences.getString(FANS_NUM, DEFAULT_VALUE);

		return user;
	}
	

}

