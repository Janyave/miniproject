package com.netease.ecos.request.recruitment;

import com.netease.ecos.model.Recruitment;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/***
 * 
* @ClassName: CreateRecruitmentRequest 
* @Description: 创建招募请求
* @author enlizhang
* @date 2015年8月2日 下午3:35:46 
*
 */
public class CreateRecruitmentRequest extends BaseRequest{
	
	
	ICreateRecruitmentResponce mCreateAssignmentResponce;
	
	public void request(ICreateRecruitmentResponce createRecruitmentResponce, final Recruitment recruit)
	{
		super.initBaseRequest(createRecruitmentResponce);
		mCreateAssignmentResponce = createRecruitmentResponce;
		
		if(mCreateAssignmentResponce!=null)
		{
			mCreateAssignmentResponce.success(getTestRecruitment());
		}
		
		/*MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.CREATE_ASSIGNMENT_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put("recruitJson", getRequestRecruitJson(recruit));
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, CreateRecruitmentRequest.this.getUrl(RequestUrlConstants.CREATE_ASSIGNMENT_URL, map));
	            return map;  
	        }  
	        
	    }; 
	    
	    stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	    
	    getQueue().add(stringRequest);*/
	}
	
	@Override
	public void responceSuccess(String jstring) {
		traceNormal(TAG, jstring);
		
		try {
			JSONObject json = new JSONObject(jstring).getJSONObject(KEY_DATA);
			
			JSONObject asJO = json;
			
		/*	mAssignment.issueTimeStamp = asJO.getLong(KEY_AS_ISSUE_TIMES);
			
			
			if(mCreateAssignmentResponce!=null)
			{
				mCreateAssignmentResponce.success(mAssignment);
			}
			else
			{
				traceError(TAG,"回调接口为null");
			}*/
			
			
			Recruitment recruit = null; 
			recruit  = new Recruitment();
			recruit.recruitmentId = getString(asJO,"recruitmentId");
			recruit.averagePrice = getString(asJO,"recruitmentId");
			recruit.description = getString(asJO,"description");
			recruit.coverUrl = getString(asJO,"coverUrl");
			recruit.shareId = getString(asJO,"shareId");
			recruit.title = getString(asJO,"title");
			recruit.priceUnit = getString(asJO,"priceUnit");
			recruit.issueTimeStamp = 1;
			
			
			
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
	* @ClassName: ICreateRecruitmentResponce 
	* @Description: 创建招募回调函数
	*
	 */
	public interface ICreateRecruitmentResponce extends IBaseResponse{
		
		/** 创建招募成功，并返回添加的招募 */
		public void success(Recruitment recruitment);
	}
	
	
	public String getRequestRecruitJson(Recruitment recruitment){
		Map<Object,Object> jsonMap = new HashMap<Object,Object>();

		jsonMap.put("price", recruitment.averagePrice);
		jsonMap.put("description", recruitment.description);
		jsonMap.put("coverUrl", recruitment.coverUrl);
		jsonMap.put("shareId", recruitment.shareId);
		jsonMap.put("title", recruitment.title);
		jsonMap.put("priceUnit", recruitment.priceUnit);
		
		return new JSONObject(jsonMap).toString();
	}
	
	
	public Recruitment getTestRecruitment(){
		
		Recruitment recruit = null; 
		for(int i=0;i<1;i++){
			recruit  = new Recruitment();
			recruit.recruitmentId = "" + i;
			recruit.averagePrice = "" + i*100 + "/人";
			recruit.description = "这是一个测试描述" + i;
			recruit.coverUrl = "http://pic.jschina.com.cn/0/10/40/90/10409045_975387.jpg";
			recruit.shareId = "" + i;
			recruit.title = "招募测试标题" + i;
			recruit.priceUnit = "人" + i;
			recruit.issueTimeStamp = System.currentTimeMillis() - i*24*60*60*1000;
		}
		return recruit;
	}
	
	
}

