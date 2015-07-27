package com.netease.ecos.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Comment.CommentType;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.comment.CommentListRequest;
import com.netease.ecos.request.comment.CommentListRequest.ICommentListResponse;
import com.netease.ecos.request.course.CreateAssignmentRequest;
import com.netease.ecos.request.course.CreateAssignmentRequest.ICreateAssignmentResponce;
import com.netease.ecos.request.course.GetAssignmentDetailRequest;
import com.netease.ecos.request.course.GetAssignmentDetailRequest.IAssignmentDetailRespnce;

import java.util.List;

/**   
 * @Title: ApiTestActivity.java 
 * @Description: 测试Api接口
 * @author enlizhang   
 * @date 2015年7月27日 下午1:30:53 
 */

public class ApiTestActivity extends BaseActivity{

	String items[] = {"上传作品","查看作业详情","查看教程评论"};
	
	public boolean isFirst = true;
	
	TextView tv_display;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_api);
		

		tv_display = (TextView) findViewById(R.id.tv_display);
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				switch(position){
				case 0:
					if(isFirst){
						isFirst = false;
						return ;
					}
					createAssignment();
					break;
				case 1:
					getAssignmentDetail();
					break;
				case 2:
					getCommentList();
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	/***
	 * 发布作业
	 */
	public void createAssignment(){
		Log.e("发布作业请求", "createAssignment()");
		CreateAssignmentRequest request = new CreateAssignmentRequest();
		
		Assignment assignment = new Assignment();
		
		assignment.author = "张恩立";
		assignment.authorAvatarUrl = "http://p1.gexing.com/G1/M00/9E/A6/rBACE1J-AI7xPAUWAAAa1SSMm94668_200x200_3.jpg?recache=20131108";
		assignment.imageUrl = "http://news.xinhuanet.com/games/2011-02/28/121123255_511n.jpg";
		assignment.content = "很有成就感";
		assignment.praiseNum = 100;
		
		request.request(new CreateAssignmentResponse(), "1", assignment);
	}
	
	/***
	 * 
	* @ClassName: CreateAssignmentResponse 
	* @Description: 发布作业回调接口
	* @author enlizhang
	* @date 2015年7月27日 下午2:12:55 
	*
	 */
	class CreateAssignmentResponse extends BaseResponceImpl implements ICreateAssignmentResponce{

		@Override
		public void doAfterFailedResponse(String message) {
			
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			
		}

		@Override
		public void success(Assignment assignment) {
			tv_display.setText("");
			
			tv_display.append("网友名称:" + assignment.author+"\n");
			tv_display.append("网友头像url:" + assignment.authorAvatarUrl+"\n");
			tv_display.append("作业图片url:" + assignment.imageUrl+"\n");
			tv_display.append("描述内容:" + assignment.content+"\n");
			
		}
		
	}

	
	/***
	 * 查看教程详情
	 */
	public void getAssignmentDetail(){
		Log.e("获取作业详情", "createAssignment()");
		GetAssignmentDetailRequest request = new GetAssignmentDetailRequest();
		
		Assignment assignment = new Assignment();
		
		request.request(new GetAssignmetnDetailResponse(), "1");
	}
	
	/***
	 * 
	* @ClassName: GetAssignmetnDetailResponse 
	* @Description: 获取作业详情
	* @author enlizhang
	* @date 2015年7月27日 下午2:20:59 
	*
	 */
	class GetAssignmetnDetailResponse extends BaseResponceImpl implements IAssignmentDetailRespnce{

		@Override
		public void doAfterFailedResponse(String message) {
			
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			
		}

		@Override
		public void success(Assignment assignment, List<Comment> commentList) {
			tv_display.setText("");
			
			tv_display.append("网友名称:" + assignment.author+"\n");
			tv_display.append("网友头像url:" + assignment.authorAvatarUrl+"\n");
			tv_display.append("作业图片url:" + assignment.imageUrl+"\n");
			tv_display.append("描述内容:" + assignment.content+"\n");
			tv_display.append("作业点赞数:" + assignment.praiseNum+"\n");
			
			tv_display.append("\n");
			tv_display.append("评论\n");
			
			for(Comment comment:commentList){
				
				tv_display.append("网友名称:" + comment.fromNickName+"\n");
				tv_display.append("    网友头像:" + comment.avatarUrl+"\n");
				tv_display.append("    评论内容:" + comment.content+"\n");
			}
			
		}
	}
	
	/***
	 * 获取评论列表
	 */
	public void getCommentList(){
		Log.e("获取评论列表", "createAssignment()");
		CommentListRequest request = new CommentListRequest();
		
		Assignment assignment = new Assignment();
		Comment comment = new Comment();
		
		//当前评论类别是作业
		comment.commentType = CommentType.作业;
		//评论对应的作业id是1
		comment.commentTypeId = "1";
		
		request.request(new GetCommentListResponse(), comment);
	}
	
	/***
	 * 
	* @ClassName: GetAssignmetnDetailResponse 
	* @Description: 获取作业详情
	* @author enlizhang
	* @date 2015年7月27日 下午2:20:59 
	*
	 */
	class GetCommentListResponse extends BaseResponceImpl implements ICommentListResponse{

		@Override
		public void doAfterFailedResponse(String message) {
			
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			
		}

		@Override
		public void success(List<Comment> commentList) {
			tv_display.setText("");
			
			for(Comment comment:commentList){
				
				tv_display.append("网友名称:" + comment.fromNickName+"\n");
				tv_display.append("    网友头像:" + comment.avatarUrl+"\n");
				tv_display.append("    评论内容:" + comment.content+"\n");
				tv_display.append("    评论时间:" + comment.getDateDescription()+"\n");
			}
			
		}
	}
}

