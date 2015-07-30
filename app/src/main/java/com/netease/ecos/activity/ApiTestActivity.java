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
import com.netease.ecos.model.Course;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.model.Image;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.comment.CommentListRequest;
import com.netease.ecos.request.course.CourseListRequest;
import com.netease.ecos.request.course.CreateAssignmentRequest;
import com.netease.ecos.request.course.GetAssignmentDetailRequest;
import com.netease.ecos.request.course.GetBannerRequest;
import com.netease.ecos.request.course.GetCourseDetailRequest;
import com.netease.ecos.request.share.GetShareDetailRequest;
import com.netease.ecos.request.share.ShareListRequest;

import java.util.List;

/**   
 * @Title: ApiTestActivity.java 
 * @Description: 测试Api接口
 * @author enlizhang   
 * @date 2015年7月27日 下午1:30:53 
 */

public class ApiTestActivity extends BaseActivity{

	String items[] = {"上传作品","查看作业详情","查看教程评论","获取banner列表","获取推荐教程列表","获取教程筛选列表","获取教程详情"
			,"获取分享列表","获取分享详情"};

	public boolean isFirst = true;

	TextView tv_display;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_api);

		MyApplication.setCurrentActivity(this);

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
					case 3:
						getBannerList();
						break;
					case 4:
						getCourseListByRecommend();
						break;
					case 5:
						getCourseListByFilter();
						break;
					case 6:
						getCourseDetail();
						break;
					case 7:
						getShareList();
						break;
					case 8:
						getShareDetail();
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
	class CreateAssignmentResponse extends BaseResponceImpl implements CreateAssignmentRequest.ICreateAssignmentResponce {

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
	class GetAssignmetnDetailResponse extends BaseResponceImpl implements GetAssignmentDetailRequest.IAssignmentDetailRespnce {

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
			tv_display.append("描述内容:" + assignment.content + "\n");
			tv_display.append("作业点赞数:" + assignment.praiseNum+"\n");
			tv_display.append("作业评论综述:" + assignment.commentNum+"\n");

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
	class GetCommentListResponse extends BaseResponceImpl implements CommentListRequest.ICommentListResponse {

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


	/***
	 * 获取评论列表
	 */
	public void getBannerList(){
		Log.e("获取banner列表", "getCommentList()");

		GetBannerRequest request = new GetBannerRequest();
		request.request(new GetBannerResponse());

	}



	/***
	 *
	 * @ClassName: GetBannerResponse
	 * @Description: 获取banner请求响应
	 * @author enlizhang
	 * @date 2015年7月27日 下午4:50:51
	 *
	 */
	class GetBannerResponse extends BaseResponceImpl implements GetBannerRequest.IGetBannerResponse {

		@Override
		public void doAfterFailedResponse(String message) {

		}

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void success(List<String> bannerList) {

			for(String string:bannerList){
				tv_display.append("banner url:" + string +"\n");
			}

		}

	}

	/***
	 * 获取教程列表
	 */
	public void getCourseListByRecommend(){
		Log.e("获取推荐教程列表", "getCommentList()");

		CourseListRequest request = new CourseListRequest();
		request.request(new CourseListResponse(),CourseListRequest.Type.推荐,null,null,null);
	}


	public void getCourseListByFilter(){
		Log.e("获取筛选教程列表", "getCourseListByFilter()");

		CourseListRequest request = new CourseListRequest();
		request.request(new CourseListResponse(),CourseListRequest.Type.筛选,
				Course.CourseType.妆娘,"鸣人",CourseListRequest.SortRule.时间);
	}


	/***
	 *
	 * @ClassName: ICourseListRespnce
	 * @Description: 教程列表响应回掉
	 * @author enlizhang
	 * @date 2015年7月27日 下午4:58:40
	 *
	 */
	class CourseListResponse extends BaseResponceImpl implements CourseListRequest.ICourseListResponse {

		@Override
		public void doAfterFailedResponse(String message) {

		}

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void success(List<Course> courseList) {
			tv_display.setText("");

				for(Course course:courseList){

					tv_display.append("网友昵称:" + course.author +"\n");
					tv_display.append("    教程标题:" + course.title +"\n");
					tv_display.append("    作者头像url:" + course.authorAvatarUrl +"\n");
					tv_display.append("    教程封面url:" + course.coverUrl +"\n");
					tv_display.append("    点赞数:" + course.praiseNum +"\n");

			}

		}

	}


	/***
	 * 获取教程详情
	 */
	public void getCourseDetail() {
		GetCourseDetailRequest request =  new GetCourseDetailRequest();

		request.request(new CourseDetailtResponse(), "1");
	}

	/***
	 *
	 * @ClassName: CourseDetailtResponse
	 * @Description: 获取教程详情
	 * @author enlizhang
	 * @date 2015年7月27日 下午5:21:16
	 *
	 */
	class CourseDetailtResponse extends BaseResponceImpl implements GetCourseDetailRequest.ICourseDetailResponse {

		@Override
		public void doAfterFailedResponse(String message) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void success(Course course) {
			tv_display.setText("");

			tv_display.append("教程封面url:" + course.coverUrl +"\n");
			tv_display.append("    教程标题:" + course.title +"\n");
			tv_display.append("    作者名字:" + course.author +"\n");
			tv_display.append("    作者头像url:" + course.authorAvatarUrl +"\n");
			tv_display.append("    教程封面url:" + course.coverUrl +"\n");
			tv_display.append("    点赞数:" + course.praiseNum +"\n");
			tv_display.append("    评论数:" + course.commentNum +"\n");
			tv_display.append("\n");

			for(Course.Step step:course.stepList){

				tv_display.append("教程步骤序号:" + step.stepIndex+"\n");
				tv_display.append("    教程图片url:" + step.photoUrl+"\n");
				tv_display.append("    评论内容:" + step.description+"\n");
			}
			tv_display.append("\n");

			tv_display.append("作业数:" + course.assignmentNum +"\n");

			for(Assignment assignment: course.assignmentList){
				tv_display.append("作业作者:" + assignment.author+"\n");
				tv_display.append("    作业图片url:" + assignment.imageUrl+"\n");
				tv_display.append("    作业描述:" + assignment.content+"\n");
				tv_display.append("    作者头像url:" + assignment.authorAvatarUrl+"\n");
				tv_display.append("    作业发布时间:" + assignment.getDateDescription()+"\n");
			}
			tv_display.append("\n");
			tv_display.append("评论数:" + course.commentNum +"\n");

		}
	}


	/***
	 * 获取分享列表
	 */
	public void getShareList() {
		ShareListRequest request =  new ShareListRequest();

		request.request(new ShareListResponse(), null,"keyWordk",0);
	}

	/***
	 *
	 * @ClassName: GetAssignmetnDetailResponse
	 * @Description:
	 * @author enlizhang
	 * @date 2015年7月28日 下午5:24:35
	 *
	 */
	class ShareListResponse extends BaseResponceImpl implements ShareListRequest.IShareListResponse{

		@Override
		public void doAfterFailedResponse(String message) {

		}

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void success(List<Share> shareList) {
			tv_display.setText("");

			for(int i=0;i<shareList.size();i++){

				Share share = shareList.get(i);

				tv_display.append("分享名称:" + share.title + "\n");
				tv_display.append("  分享者头像url:" + share.avatarUrl + "\n");
				tv_display.append("  分享者昵称:" + share.nickname + "\n");
				tv_display.append("  已关注分享着:" + share.hasAttention + "\n");
				tv_display.append("  已点赞:" + share.hasPraised + "\n");
				tv_display.append("  分享封面图url:" + share.coverUrl + "\n");
				tv_display.append("  分享时间:" + share.getDateDescription() + "\n");
				tv_display.append("  分享点赞数:" + share.praiseNum + "\n");
				tv_display.append("  分享评论数:" + share.commentNum + "\n");
				tv_display.append("  图片总数:" + share.totalPics + "\n");
				tv_display.append("\n");

				for(Comment comment:share.commentList){

					tv_display.append("网友名称:" + comment.fromNickName+"\n");
					tv_display.append("    网友头像:" + comment.avatarUrl+"\n");
					tv_display.append("    评论内容:" + comment.content+"\n");

				}
				tv_display.append("\n");
				tv_display.append("\n");

			}


		}
	}


	/***
	 * 获取分享详情
	 */
	public void getShareDetail() {
		GetShareDetailRequest request =  new GetShareDetailRequest();

		request.request(new GetShareDetealResponse(), "0");
	}

	/**
	 *
	 * @ClassName: GetShareResponse
	 * @Description: 获取分享详情回掉接口
	 * @author enlizhang
	 * @date 2015年7月28日 下午7:13:18
	 *
	 */
	class GetShareDetealResponse extends BaseResponceImpl implements GetShareDetailRequest.IGetShareResponse {

		@Override
		public void doAfterFailedResponse(String message) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void success(Share share) {
			tv_display.setText("");

			tv_display.append("分享名称:" + share.title + "\n");
			tv_display.append("  分享者头像url:" + share.avatarUrl + "\n");
			tv_display.append("  分享者昵称:" + share.nickname + "\n");
			tv_display.append("  已关注分享着:" + share.hasAttention + "\n");
			tv_display.append("  已点赞:" + share.hasPraised + "\n");
			tv_display.append("  分享封面图url:" + share.coverUrl + "\n");
			tv_display.append("  分享时间:" + share.getDateDescription() + "\n");
			tv_display.append("  分享点赞数:" + share.praiseNum + "\n");
			tv_display.append("  分享评论数:" + share.commentNum + "\n");
			tv_display.append("  图片总数:" + share.totalPics + "\n");

			List<Image> imageList = share.imageList;
			for(int i=0;i<imageList.size();i++){

				tv_display.append("  图片ur;:" + imageList.get(i).imageUrl + "\n");
			}

			tv_display.append("\n");

			for(Comment comment:share.commentList){

				tv_display.append("网友名称:" + comment.fromNickName+"\n");
				tv_display.append("    网友头像:" + comment.avatarUrl+"\n");
				tv_display.append("    评论内容:" + comment.content+"\n");

			}
			tv_display.append("\n");
			tv_display.append("\n");
		}

	}
}

