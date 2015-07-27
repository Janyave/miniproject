package com.netease.ecos.request;


import android.content.Context;

import com.netease.ecos.activity.MyApplication;

public abstract class BaseResponceImpl implements IBaseResponse{

	public String MESSAEE_LOGIN_AGAIN = "无操作权限，请重新登陆";
	public String MESSAEE_OTHER_ERROR = "请过段时间再进行操作";
	
	private final static String TAG = "BaseResponceImpl";
	
	private Context mContext;
	
	public BaseResponceImpl()
	{
		mContext = MyApplication.getContext();
	}
	
	public BaseResponceImpl(Context context)
	{
		mContext = context;
	}
	
	/****
	 * 当前token失效，账号已经在其他地方登录
	 */
	@Override
	public void responseNoGrant()
	{
		/*AccountDataService.getSingleAccountDataService(mContext).clearAllDataExceptUsername();
		
		LittleMessageDialog2 dialog = new LittleMessageDialog2(DemoApplication.currentActivity);
		dialog.setOnDismissListener(new MyOnDismissListener(){

			@Override
			public void dismissDo() {
				Intent intent = new Intent();
				intent.setClass(DemoApplication.currentActivity, LoginActivity.class);
				
				//清除所有activity后开启LoginActivity
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				DemoApplication.currentActivity.startActivity(intent);
				DemoApplication.currentActivity.finish();
				
			}
			
		});
		dialog.showProblemDialog("账号已经在其他地方登录，请重新登陆");*/
	}
	
}
