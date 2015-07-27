package com.netease.ecos.request.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.netease.ecos.constant.RequestUrlConstants;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Comment.CommentType;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.request.BaseRequest;
import com.netease.ecos.request.IBaseResponse;
import com.netease.ecos.request.MyStringRequest;

/***
 * 
* @ClassName: GetAssignmentDetailRequest 
* @Description: 获取教程作业详情
* @author enlizhang
* @date 2015年7月26日 上午11:04:21 
*
 */
public class GetAssignmentDetailRequest extends BaseRequest{
	
	//请求参数键
	/*** 教程作业id */
	public static final String ASSIGNMENT_ID = "assignment_id";
	
	//响应参数键
	IAssignmentDetailRespnce mAssignmentDetailRespnce;
	
	
	public void request(IAssignmentDetailRespnce assignmentDetailRespnce, final String assignmentId)
	{
		super.initBaseRequest(assignmentDetailRespnce);
		mAssignmentDetailRespnce = assignmentDetailRespnce;
		
		responceSuccess( new JSONObject().toString().toString());
		/*MyStringRequest stringRequest = new MyStringRequest(Method.POST, RequestUrlConstants.GET_ASSIGNMENT_DETAIL_URL,  this, this) {  
	        @Override  
	        protected Map<String, String> getParams() throws AuthFailureError {  
	        	Map<String, String> map = getRequestBasicMap();
	            
	        	map.put(ASSIGNMENT_ID, assignmentId);
	        	
	            traceNormal(TAG, map.toString());
	            traceNormal(TAG, GetAssignmentDetailRequest.this.getUrl(RequestUrlConstants.GET_ASSIGNMENT_DETAIL_URL, map));
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
			JSONObject json = new JSONObject(jstring);
			
			
			if(mAssignmentDetailRespnce!=null)
			{
				mAssignmentDetailRespnce.success(getTestAssignmentDetail(), getTestCommentList());
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
	 * 获取作业详情测试数据
	 * @return
	 */
	public Assignment getTestAssignmentDetail(){
		Assignment assignment = new Assignment();
		
		assignment.author = "张恩立";
		assignment.authorAvatarUrl = "http://p1.gexing.com/G1/M00/9E/A6/rBACE1J-AI7xPAUWAAAa1SSMm94668_200x200_3.jpg?recache=20131108";
		assignment.content = "这张图片是关于....？";
		assignment.praiseNum = 100;
		
		return assignment;
	}
	
	
	/***
	 * 获取评论列表测试数据(至多4条)
	 * @return
	 */
	public List<Comment> getTestCommentList(){
		List<Comment> commentList = new ArrayList<Comment>();

		int commentNum = 4;
		Comment comment;
		String commentContents[] = {"蓝天好老!","赞同1楼","I'm sky!","你们欺负我室友？"};
		String fromNickNames[] = {"胡泽","enli","蓝天","houzhe"};
		for(int i=0;i<commentNum;i++){
			comment = new Comment();
			comment.avatarUrl = "http://img4.imgtn.bdimg.com/it/u=4220977954,2571826636&fm=21&gp=0.jpg";
			comment.content = commentContents[i];
			comment.commentType = CommentType.作业;
			comment.commentTypeId = "" + i;
			comment.commentId = ""+i;
			comment.fromId = "" + i;
			comment.fromNickName = fromNickNames[i];
			comment.targetId = "" + (i<<2);
			
			commentList.add(comment);
		}
		
		return commentList;
	}
	
	
	
	/***
	 * 
	* @ClassName: AssignmentDetailRespnce 
	* @Description: 获取教程详情回调
	* @author enlizhang
	* @date 2015年7月26日 下午6:30:48 
	*
	 */
	public interface IAssignmentDetailRespnce extends IBaseResponse{
		
		/***
		 * 获取教程详情成功回调函数，并返回教程详情和简单评论列表
		 * @param assignment 教程详情
		 * @param commentList 简单评论列表
		 */
		public void success(Assignment assignment,List<Comment> commentList);
		
	}
	
}

