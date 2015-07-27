package com.netease.ecos.request;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.netease.ecos.activity.MyApplication;
import com.netease.ecos.model.AccountDataService;



public abstract class BaseRequest implements Listener<String>,ErrorListener{
	
	public final String TAG;
	
	//请求参数键
	protected final static String KEY_TOKEN = "token";
	protected final static String KEY_USER_ID = "userId";
	
	/*** 页面大小 */
	protected final static String KEY_PAGE_SIZE = "page_size";
	
	/*** 页编号 */
	protected final static String KEY_PAGE_INDEX = "pages";
	
	/*** 默认一页数据条数 */
	protected final static int DEFAULT_PAGE_SIZE = 15;
	
	//响应参数键
	protected String KEY_CODE = "code";
	protected String KEY_MSG = "msg";
	protected String KEY_DATA = "data";
	
	/***请求成功返回码 */
	public final static String RETURN_CODE_SUCCESS = "4200";
	
	/*** token失效返回码 */
	protected final static String RETURN_CODE_TOEKN_INVALID = "4004";
	

	public IBaseResponse mBaseResponse; 
	
	public BaseRequest()
	{
		TAG = getClass().getSimpleName();
	}
	
	protected void initBaseRequest(IBaseResponse baseResponse)
	{
		mBaseResponse = baseResponse;
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		
		Log.d(TAG, error.toString());
		
		if(mBaseResponse!=null)
		{
			mBaseResponse.onErrorResponse(error);
		}
		else
		{
			traceError(TAG,"回调接口为null");
		}
	}
	
	@Override
	public void onResponse(String jstring) {
		
		traceNormal(TAG, jstring);
		
		try {
			JSONObject json = new JSONObject(jstring);
			
			//请求失败
			if(!RETURN_CODE_SUCCESS.equals(json.get(KEY_CODE)))
			{
				if(!isTokenValid(json.get(KEY_CODE))){
					mBaseResponse.responseNoGrant();
					return ;
				}
				
				if(mBaseResponse!=null)
				{
					mBaseResponse.doAfterFailedResponse(json.getString(KEY_MSG));
				}
				else
				{
					traceError(TAG,"回调接口为null");
				}
				return ;
			}
			
			responceSuccess(jstring);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			if(mBaseResponse!=null)
			{
				mBaseResponse.doAfterFailedResponse("json异常");
			}
		}
	}
	
	
	/*** 与服务器成功通信后的响应  */
	protected abstract void responceSuccess(String jstring);
	
	/***
	 * 获取上下文
	 * @return
	 */
	public Context getContext()
	{
		return MyApplication.getContext();
	}
	
	/***
	 * 获取RequestQueue对象
	 * @return
	 */
	public RequestQueue getQueue() 
	{
		return MyApplication.getRequestQueue();
	}
	
	protected String getToken()
	{
		return AccountDataService.getSingleAccountDataService(getContext()).getToken();
	}
	
	
	/***
	 *  判断是否失效
	 * @param errCode 响应码
	 * @return true:未失效;false：失效
	 */
	public boolean isTokenValid(Object errCode){
		return !RETURN_CODE_TOEKN_INVALID.equals(errCode);
	}

	

	/*@Override
	public void onErrorResponse(VolleyError error) {
		mErrorResponseListener.onErrorResponse(error);
	}*/
		
	public final static boolean DEBUG_MODE = true;
	
	/****
	 * 封装了traceNormal()，打印一般信息。通过参数控制是否打印调试信息
	 * @param tag
	 * @param message
	 */
	protected void traceNormal(String tag,String message)
	{
		if(DEBUG_MODE)
			Log.i(tag, message);
	}
	
	/****
	 * 封装了traceError()，打印错误信息。通过参数控制是否打印调试信息
	 * @param tag
	 * @param message
	 */
	protected void traceError(String tag,String message)
	{
		if(DEBUG_MODE)
			Log.e(tag, message);
	}
	
	/***
	 * 获取当前用户id
	 * @return
	 */
	protected String getUserId()
	{
		return AccountDataService.getSingleAccountDataService(getContext()).getUserId();
	}
	
	/***
	 * 在jsonObject中查取与key的String
	 * @param key 不能为null
	 * @return 与key对应的String，如无该key则返回空字符串("");
	 * @throws JSONException 
	 */
	protected String getString(JSONObject jsonObject, String key) throws JSONException
	{
		return jsonObject.has(key)?jsonObject.getString(key):"";
	}
	
	
	/****
	 * 获取请求基本参数，token，identity，
	 * @return
	 */
	public Map<String,String> getRequestBasicMap(){
		Map<String,String> basicMap = new HashMap<String,String>();
		
		basicMap.put(KEY_TOKEN, getToken());
		basicMap.put(KEY_USER_ID, getUserId());
		
		return basicMap;
	}
	
	/***
	 * 
	* @ClassName: MyJSONObject 
	* @Description: TODO(能过滤无键的情况) 
	* @author enlizhang
	* @date 2015年3月1日 下午1:32:03 
	*
	 */
	class MyJSONObject extends JSONObject
	{
		public MyJSONObject(String jstring) throws JSONException {
			super(jstring);
		}

		/***
		 * 查取与key的String
		 * @param key 不能为null
		 * @return 与key对应的String，如无该key则返回空字符串("");
		 * @throws JSONException 
		 */
		@Override
		public String getString(String key) throws JSONException
		{
			return has(key)?super.getString(key):"";
		}

		@Override
		public JSONArray getJSONArray(String name) throws JSONException {
			// TODO Auto-generated method stub
			return super.getJSONArray(name);
			
		}

		@Override
		public JSONObject getJSONObject(String name) throws JSONException {
			// TODO Auto-generated method stub
			return super.getJSONObject(name);
		}
		
		
	}
	
	
	/*** 列表操作，刷新或加载 */
	public static enum ListAction {
		
		刷新("0"),
		加载("1");
		
		private String code;

		private ListAction(String code ) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

	}
	
	public String getUrl(String _url,Map<String,String> params){
		String url = _url;
		for(String key:params.keySet()){
			url += key + "=" + params.get(key) + "&";
		}
		return url;	
	}
}
