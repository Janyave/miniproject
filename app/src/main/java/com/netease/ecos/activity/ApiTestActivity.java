package com.netease.ecos.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.model.ActivityModel;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Comment.CommentType;
import com.netease.ecos.model.Course;
import com.netease.ecos.model.Course.Assignment;
import com.netease.ecos.model.Image;
import com.netease.ecos.model.ModelUtils;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.activity.ActivityListRequest;
import com.netease.ecos.request.activity.CreateActivityRequest;
import com.netease.ecos.request.comment.CommentListRequest;
import com.netease.ecos.request.comment.CreateCommentRequest;
import com.netease.ecos.request.course.CourseListRequest;
import com.netease.ecos.request.course.CreateAssignmentRequest;
import com.netease.ecos.request.course.CreateCourseRequest;
import com.netease.ecos.request.course.GetAssignmentDetailRequest;
import com.netease.ecos.request.course.GetBannerRequest;
import com.netease.ecos.request.course.GetCourseDetailRequest;
import com.netease.ecos.request.recruitment.CreateRecruitmentRequest;
import com.netease.ecos.request.recruitment.GetRecruitmentDetailRequest;
import com.netease.ecos.request.recruitment.RecruitmentListRequest;
import com.netease.ecos.request.share.CreateShareRequest;
import com.netease.ecos.request.share.DeleteShareRequest;
import com.netease.ecos.request.share.GetShareDetailRequest;
import com.netease.ecos.request.share.ShareListRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author enlizhang
 * @Title: ApiTestActivity.java
 * @Description: 测试Api接口
 * @date 2015年7月27日 下午1:30:53
 */

public class ApiTestActivity extends BaseActivity {

    String items[] = {"上传作品", "查看作业详情", "查看教程评论", "获取banner列表", "获取推荐教程列表", "获取教程筛选列表", "获取教程详情"
            , "获取分享列表", "获取个人列表", "获取分享详情", "获取活动列表", "创建活动", "获取招募列表", "获取招募列表详情", "创建分享", "删除分享"
            , "创建教程", "创建招募"};


    public boolean isFirst = true;

    TextView tv_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_api);

        MyApplication.setCurrentActivity(this);

        tv_display = (TextView) findViewById(R.id.tv_display);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                switch (position) {
                    case 0:
                        if (isFirst) {
                            isFirst = false;
                            return;
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
                    case 7:
                        getShareList();
                        break;
                    case 8:
                        getPersonalShareList();
                        break;
                    case 9:
                        getShareDetail();
                        break;
                    case 10:
                        getActivityList();
                        break;
                    case 11:
                        createActivity();
                        break;
                    case 12:
                        getRecruitmentList();
                        break;
                    case 13:
                        getRecruitmentDetail();
                        break;
                    case 14:
                        createShare();
                        break;
                    case 15:
                        deleteShare();
                        break;
                    case 16:
                        createCourse();
                    case 17:
                        createRecruitment();
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    /**
     * 发布作业
     */
    public void createAssignment() {
        Log.e("发布作业请求", "createAssignment()");
        CreateAssignmentRequest request = new CreateAssignmentRequest();

        Assignment assignment = new Assignment();

        assignment.courseId = "1";
        //		assignment.author = "张恩立";
        //		assignment.authorAvatarUrl = "http://p1.gexing.com/G1/M00/9E/A6/rBACE1J-AI7xPAUWAAAa1SSMm94668_200x200_3.jpg?recache=20131108";
        assignment.imageUrl = "http://news.xinhuanet.com/games/2011-02/28/121123255_511n.jpg";
        assignment.content = "很有成就感";

        request.request(new CreateAssignmentResponse(), assignment);
    }

    /**
     * @author enlizhang
     * @ClassName: CreateAssignmentResponse
     * @Description: 发布作业回调接口
     * @date 2015年7月27日 下午2:12:55
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

            tv_display.append("网友名称:" + assignment.author + "\n");
            tv_display.append("网友头像url:" + assignment.authorAvatarUrl + "\n");
            tv_display.append("作业图片url:" + assignment.imageUrl + "\n");
            tv_display.append("描述内容:" + assignment.content + "\n");

        }

    }


    /**
     * 查看教程详情
     */
    public void getAssignmentDetail() {
        Log.e("获取作业详情", "createAssignment()");
        GetAssignmentDetailRequest request = new GetAssignmentDetailRequest();

        Assignment assignment = new Assignment();

        request.request(new GetAssignmetnDetailResponse(), "6");
    }

    /**
     * @author enlizhang
     * @ClassName: GetAssignmetnDetailResponse
     * @Description: 获取作业详情
     * @date 2015年7月27日 下午2:20:59
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

            tv_display.append("网友名称:" + assignment.author + "\n");
            tv_display.append("网友头像url:" + assignment.authorAvatarUrl + "\n");
            tv_display.append("作业图片url:" + assignment.imageUrl + "\n");
            tv_display.append("描述内容:" + assignment.content + "\n");
            tv_display.append("作业点赞数:" + assignment.praiseNum + "\n");
            tv_display.append("作业评论综述:" + assignment.commentNum + "\n");

            tv_display.append("\n");
            tv_display.append("评论\n");

            for (Comment comment : commentList) {

                tv_display.append("网友名称:" + comment.fromNickName + "\n");
                tv_display.append("    网友头像:" + comment.avatarUrl + "\n");
                tv_display.append("    评论内容:" + comment.content + "\n");
            }

        }
    }

    /**
     * 获取评论列表
     */
    public void getCommentList() {
        Log.e("获取评论列表", "createAssignment()");
        CommentListRequest request = new CommentListRequest();

        Assignment assignment = new Assignment();
        Comment comment = new Comment();

        //当前评论类别是作业
        comment.commentType = CommentType.作业;
        //评论对应的作业id是1
        comment.commentTypeId = "1";

        request.request(new GetCommentListResponse(), comment, 1);
    }

    /**
     * @author enlizhang
     * @ClassName: GetAssignmetnDetailResponse
     * @Description: 获取作业详情
     * @date 2015年7月27日 下午2:20:59
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

            for (Comment comment : commentList) {

                tv_display.append("网友名称:" + comment.fromNickName + "\n");
                tv_display.append("    网友头像:" + comment.avatarUrl + "\n");
                tv_display.append("    评论内容:" + comment.content + "\n");
                tv_display.append("    评论时间:" + comment.getDateDescription() + "\n");
            }

        }
    }


    /**
     * 获取评论列表
     */
    public void getBannerList() {
        Log.e("获取banner列表", "getCommentList()");

        GetBannerRequest request = new GetBannerRequest();
        request.request(new GetBannerResponse());

    }


    /**
     * @author enlizhang
     * @ClassName: GetBannerResponse
     * @Description: 获取banner请求响应
     * @date 2015年7月27日 下午4:50:51
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
            tv_display.setText("");

            for (String string : bannerList) {
                tv_display.append("banner url:" + string + "\n");
            }

        }

    }

    /**
     * 获取教程列表
     */
    public void getCourseListByRecommend() {
        Log.e("获取推荐教程列表", "getCommentList()");

        CourseListRequest request = new CourseListRequest();
        request.request(new CourseListResponse(), CourseListRequest.Type.推荐, null, null, null, 0);
    }


    public void getCourseListByFilter() {
        Log.e("获取筛选教程列表", "getCourseListByFilter()");

        CourseListRequest request = new CourseListRequest();
        request.request(new CourseListResponse(), CourseListRequest.Type.筛选, Course.CourseType.化妆, "鸣人", CourseListRequest.SortRule.时间, 0);
    }


    /**
     * @author enlizhang
     * @ClassName: ICourseListRespnce
     * @Description: 教程列表响应回掉
     * @date 2015年7月27日 下午4:58:40
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

            for (Course course : courseList) {

                tv_display.append("网友昵称:" + course.author + "\n");
                tv_display.append("    教程标题:" + course.title + "\n");
                tv_display.append("    作者头像url:" + course.authorAvatarUrl + "\n");
                tv_display.append("    教程封面url:" + course.coverUrl + "\n");
                tv_display.append("    点赞数:" + course.praiseNum + "\n");

            }

        }

    }


    /**
     * 获取教程详情
     */
    public void getCourseDetail() {
        GetCourseDetailRequest request = new GetCourseDetailRequest();

        request.request(new CourseDetailtResponse(), "1");
    }

    /**
     * @author enlizhang
     * @ClassName: CourseDetailtResponse
     * @Description: 获取教程详情
     * @date 2015年7月27日 下午5:21:16
     */
    class CourseDetailtResponse extends BaseResponceImpl implements GetCourseDetailRequest.ICourseDetailResponse {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(Course course) {
            tv_display.setText("");

            tv_display.append("教程封面url:" + course.coverUrl + "\n");
            tv_display.append("    教程标题:" + course.title + "\n");
            tv_display.append("    作者名字:" + course.author + "\n");
            tv_display.append("    作者头像url:" + course.authorAvatarUrl + "\n");
            tv_display.append("    教程封面url:" + course.coverUrl + "\n");
            tv_display.append("    点赞数:" + course.praiseNum + "\n");
            tv_display.append("    评论数:" + course.commentNum + "\n");
            tv_display.append("\n");

            for (Course.Step step : course.stepList) {

                tv_display.append("教程步骤序号:" + step.stepIndex + "\n");
                tv_display.append("    教程图片url:" + step.imageUrl + "\n");
                tv_display.append("    评论内容:" + step.description + "\n");
            }
            tv_display.append("\n");

            tv_display.append("作业数:" + course.assignmentNum + "\n");

            for (Assignment assignment : course.assignmentList) {
                tv_display.append("作业作者:" + assignment.author + "\n");
                tv_display.append("    作业图片url:" + assignment.imageUrl + "\n");
                tv_display.append("    作业描述:" + assignment.content + "\n");
                tv_display.append("    作者头像url:" + assignment.authorAvatarUrl + "\n");
                tv_display.append("    作业发布时间:" + assignment.getDateDescription() + "\n");
            }
            tv_display.append("\n");
            tv_display.append("评论数:" + course.commentNum + "\n");

        }
    }


    /**
     * 获取分享列表
     */
    public void getShareList() {
        ShareListRequest request = new ShareListRequest();

        request.request(new ShareListResponse(), null, "", 1);
    }

    /**
     * 获取个人分享列表
     */
    public void getPersonalShareList() {
        ShareListRequest request = new ShareListRequest();
        request.requestMyShareList(new ShareListResponse(), 1);
    }

    /**
     * @author enlizhang
     * @ClassName: GetAssignmetnDetailResponse
     * @Description:
     * @date 2015年7月28日 下午5:24:35
     */
    class ShareListResponse extends BaseResponceImpl implements ShareListRequest.IShareListResponse {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(List<Share> shareList) {
            tv_display.setText("");

            for (int i = 0; i < shareList.size(); i++) {

                Share share = shareList.get(i);

                tv_display.append("分享名称:" + share.title + "\n");
                tv_display.append("  分享id:" + share.shareId + "\n");
                tv_display.append("  分享者头像url:" + share.avatarUrl + "\n");
                tv_display.append("  分享者昵称:" + share.nickname + "\n");
                tv_display.append("  已关注分享着:" + share.hasAttention + "\n");
                tv_display.append("  已点赞:" + share.hasPraised + "\n");
                tv_display.append("  分享封面图url:" + share.coverUrl + "\n");
                tv_display.append("  分享时间:" + share.getDateDescription() + "\n");
                tv_display.append("  分享点赞数:" + share.praiseNum + "\n");
                tv_display.append("  分享评论数:" + share.commentNum + "\n");
                tv_display.append("  图片总数:" + share.totalPageNumber + "\n");
                tv_display.append("\n");

                for (int index = 0; index < share.commentList.size(); index++) {
                    Comment comment = share.commentList.get(index);

                    tv_display.append("网友名称:" + comment.fromNickName + "\n");
                    tv_display.append("    网友头像:" + comment.avatarUrl + "\n");
                    tv_display.append("    评论内容:" + comment.content + "\n");
                    tv_display.append("\n");

                }

                tv_display.append("\n");
                tv_display.append("\n");

            }


        }
    }


    /**
     * 获取分享详情
     */
    public void getShareDetail() {
        GetShareDetailRequest request = new GetShareDetailRequest();

        request.request(new GetShareResponse(), "3");
    }

    /**
     * @author enlizhang
     * @ClassName: GetShareResponse
     * @Description: 获取分享详情回掉接口
     * @date 2015年7月28日 下午7:13:18
     */
    class GetShareResponse extends BaseResponceImpl implements GetShareDetailRequest.IGetShareResponse {

        @Override
        public void doAfterFailedResponse(String message) {

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
            tv_display.append("  图片总数:" + share.totalPageNumber + "\n");

            List<Image> imageList = share.imageList;
            for (int i = 0; i < imageList.size(); i++) {

                tv_display.append("  图片ur;:" + imageList.get(i).originUrl + "\n");
            }

            tv_display.append("\n");

            for (Comment comment : share.commentList) {

                tv_display.append("网友名称:" + comment.fromNickName + "\n");
                tv_display.append("    网友头像:" + comment.avatarUrl + "\n");
                tv_display.append("    评论内容:" + comment.content + "\n");

            }
            tv_display.append("\n");
            tv_display.append("\n");
        }

    }


    public void testCreateComment() {
        Comment comment = new Comment();
        comment.commentType = CommentType.作业;
        comment.commentId = "1";
        comment.targetId = "2";
        comment.content = "你好，houzhe";

        CreateCommentRequest request = new CreateCommentRequest();
        request.request(null, comment);
    }

    /**
     * 获取活动列表
     */
    public void getActivityList() {
        ActivityListRequest request = new ActivityListRequest();

        request.request(new ActivityListResponse(), "12", ActivityModel.ActivityType.同人展, 0);
    }

    /**
     * @author enlizhang
     * @ClassName: ActivityListResponse
     * @Description: 活动列表响应回掉接口
     * @date 2015年7月30日 下午7:14:25
     */
    class ActivityListResponse extends BaseResponceImpl implements ActivityListRequest.IActivityListResponse {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(List<ActivityModel> activityList) {
            tv_display.setText("");

            for (int i = 0; i < activityList.size(); i++) {

                ActivityModel activity = activityList.get(i);

                tv_display.append("活动名称:" + activity.title + "\n");
                tv_display.append("  活动封面图:" + activity.coverUrl + "\n");
                tv_display.append("  活动开始日期:" + ModelUtils.getDateDesByTimeStamp(activity.activityTime.startDateStamp) + "\n");
                tv_display.append("  活动结束日期:" + ModelUtils.getDateDesByTimeStamp(activity.activityTime.endDateStamp) + "\n");
                tv_display.append("  每日开始时间:" + activity.activityTime.dayStartTime + "\n");
                tv_display.append("  每日结束时间:" + activity.activityTime.dayEndTime + "\n");
                tv_display.append("  城市名称:" + activity.location.city.cityName + "\n");
                tv_display.append("  详细地址:" + activity.location.address + "\n");
                tv_display.append("  活动类型:" + activity.activityType.name() + "\n");
                tv_display.append("\n");

            }


        }

    }

    /**
     * 创建活动
     */
    public void createActivity() {
        CreateActivityRequest request = new CreateActivityRequest();
        request.testData(new CreateActivityResponce(), new ActivityModel());
    }

    /**
     * @author enlizhang
     * @ClassName: CreateActivityResponce
     * @Description:
     * @date 2015年7月30日 下午8:26:40
     */
    class CreateActivityResponce extends BaseResponceImpl implements CreateActivityRequest.ICreateActivityResponce {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(ActivityModel activity) {
            Toast.makeText(ApiTestActivity.this, "创建成功", Toast.LENGTH_LONG).show();

        }

    }

    /**
     * 获取招募列表
     */
    public void getRecruitmentList() {
        RecruitmentListRequest request = new RecruitmentListRequest();

        //请求妆娘招募类别，城市id为12，排序方式为最受欢迎的招募列表的第0页数据
        request.request(new RecruitmentListResponse(), Recruitment.RecruitType.妆娘, "12", RecruitmentListRequest.SortRule.最受欢迎, 0);
    }


    /**
     * @author enlizhang
     * @ClassName: RecruitmentListResponse
     * @Description: 招募列表响应回掉接口
     * @date 2015年7月31日 上午8:32:33
     */
    class RecruitmentListResponse extends BaseResponceImpl implements RecruitmentListRequest.IRecruitmentListResponse {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(List<Recruitment> recruitList) {
            tv_display.setText("");

            for (int i = 0; i < recruitList.size(); i++) {

                Recruitment recruit = recruitList.get(i);

                //				tv_display.append("招募标题:" + recruit.title + "\n");
                tv_display.append("招募封面图url:" + recruit.coverUrl + "\n");
                tv_display.append("  招募id:" + recruit.recruitmentId + "\n");
                tv_display.append("  发起者用户id:" + recruit.userId + "\n");
                tv_display.append("  发起者云信id:" + recruit.imId + "\n");
                tv_display.append("  发起者头像:" + recruit.avatarUrl + "\n");
                tv_display.append("  发起者昵称:" + recruit.nickname + "\n");
                tv_display.append("  发起者性别:" + recruit.gender.name() + "\n");
                tv_display.append("  发起时间:" + ModelUtils.getDateDesByTimeStamp(recruit.issueTimeStamp) + "\n");
                tv_display.append("  与发起者距离:" + recruit.distanceKM + "km" + "\n");
                tv_display.append("  与发起者距离:" + "均价 " + recruit.averagePrice + "\n");
                tv_display.append("\n");

            }
        }
    }

    /**
     * 获取招募详情
     */
    public void getRecruitmentDetail() {
        GetRecruitmentDetailRequest request = new GetRecruitmentDetailRequest();

        //请求招募id为1的招募详情
        request.request(new GetRecruitmentLDetailResponse(), "1");

    }


    /**
     * @author enlizhang
     * @ClassName: RecruitmentListResponse
     * @Description: 招募列表响应回掉接口
     * @date 2015年7月31日 上午8:32:33
     */
    class GetRecruitmentLDetailResponse extends BaseResponceImpl implements GetRecruitmentDetailRequest.IGetRecruitmentLDetailResponse {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(Recruitment recruit) {
            tv_display.setText("");


            tv_display.append("招募标题:" + recruit.title + "\n");
            tv_display.append("  招募封面图url:" + recruit.coverUrl + "\n");
            tv_display.append("  招募描述:" + recruit.description + "\n");
            tv_display.append("  招募id:" + recruit.recruitmentId + "\n");
            tv_display.append("  发起者用户id:" + recruit.userId + "\n");
            tv_display.append("  发起者云信id:" + recruit.imId + "\n");
            tv_display.append("  发起者头像:" + recruit.avatarUrl + "\n");
            tv_display.append("  发起者昵称:" + recruit.nickname + "\n");
            tv_display.append("  发起者性别:" + recruit.gender.name() + "\n");
            tv_display.append("  发起时间:" + ModelUtils.getDateDesByTimeStamp(recruit.issueTimeStamp) + "\n");
            tv_display.append("  与发起者距离:" + recruit.distanceKM + "km" + "\n");
            tv_display.append("  与发起者距离:" + "均价 " + recruit.averagePrice + "\n");
            tv_display.append("\n");

            List<Share> shareList = recruit.shareList;
            int length = shareList.size();
            for (int i = 0; i < length; i++) {

                Share share = recruit.shareList.get(i);

                tv_display.append("分享名称:" + share.title + "\n");
                tv_display.append(" 分享id:" + share.shareId + "\n");
                tv_display.append("  已关注分享着:" + share.hasAttention + "\n");
                tv_display.append("  已点赞:" + share.hasPraised + "\n");
                tv_display.append("  分享封面图url:" + share.coverUrl + "\n");
                tv_display.append("  分享时间:" + share.getDateDescription() + "\n");
                tv_display.append("  分享点赞数:" + share.praiseNum + "\n");
                tv_display.append("  分享评论数:" + share.commentNum + "\n");
                tv_display.append("  图片总数:" + share.totalPageNumber + "\n");

                tv_display.append("\n");
            }
        }

    }

    /**
     * 创建分享
     */
    public void createShare() {
        CreateShareRequest request = new CreateShareRequest();

        Share share = new Share();
        share.title = "houzhe nihao";
        share.content = "houzhe jintian mai i6";
        share.coverUrl = "http://img3.cache.netease.com/house/2015/7/3/20150703163657b0237_550.jpg";

        int length = 2;
        share.totalPageNumber = length;
        for (int i = 0; i < length; i++) {
            Image image = new Image();
            image.originUrl = bannerList.get(i);
            share.imageList.add(image);
        }
        share.imageList.add(new Image());


        //请求招募id为1的招募详情
        request.request(new CreateShareResponse(), share);

    }

    static List<String> bannerList = new ArrayList<String>();

    {
        bannerList.add("http://u4.tdimg.com/7/203/19/46138657748730920288026757971472766587.jpg");
        bannerList.add("http://www.cnnb.com.cn/pic/0/01/49/86/1498602_864010.jpg");
    }


    /**
     * @author enlizhang
     * @ClassName: GetShareResponse
     * @Description: 获取分享详情回掉接口
     * @date 2015年7月28日 下午7:13:18
     */
    class CreateShareResponse extends BaseResponceImpl implements CreateShareRequest.ICreateShareResponse {

        @Override
        public void doAfterFailedResponse(String message) {
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
            tv_display.append("  图片总数:" + share.totalPageNumber + "\n");

            List<Image> imageList = share.imageList;
            for (int i = 0; i < imageList.size(); i++) {

                tv_display.append("  图片ur;:" + imageList.get(i).originUrl + "\n");
            }

            tv_display.append("\n");

            for (Comment comment : share.commentList) {

                tv_display.append("网友名称:" + comment.fromNickName + "\n");
                tv_display.append("    网友头像:" + comment.avatarUrl + "\n");
                tv_display.append("    评论内容:" + comment.content + "\n");

            }
            tv_display.append("\n");
            tv_display.append("\n");
        }

    }

    /**
     * 删除分享
     */
    public void deleteShare() {
        DeleteShareRequest request = new DeleteShareRequest();
        request.request(new DeleteShareResponse(), "2");
    }

    class DeleteShareResponse extends BaseResponceImpl implements DeleteShareRequest.IDeleteShareResponse {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(String shareId) {
            tv_display.setText("");

            tv_display.append("删除分享id:" + shareId);
        }

    }


    /**
     * 创建教程
     */
    public void createCourse() {
        CreateCourseRequest request = new CreateCourseRequest();

        Course course = new Course();

        course.title = "sige nihao";
        course.courseType = Course.CourseType.化妆;
        course.coverUrl = "http://img3.cache.netease.com/house/2015/7/3/20150703163657b0237_550.jpg";

        for (int i = 0; i < 2; i++) {
            Course.Step step = new Course.Step(i + 1);
            step.description = "great!" + i;
            step.imageUrl = bannerList.get(i);
            course.addStep(step);
        }

        request.request(new CreateCourseResponse(), course);

    }

    class CreateCourseResponse extends BaseResponceImpl implements CreateCourseRequest.ICreateCourseResponse {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(Course course) {

        }
    }

    /**
     * 创建招募
     */
    public void createRecruitment() {
        CreateRecruitmentRequest request = new CreateRecruitmentRequest();

        Recruitment recruitment = new Recruitment();
        recruitment.recruitmentId = "" + 1;
        recruitment.averagePrice = "" + 1 * 100 + "/人";
        recruitment.description = "这是一个测试描述" + 1;
        recruitment.coverUrl = "http://pic.jschina.com.cn/0/10/40/90/10409045_975387.jpg";
        recruitment.recruitmentId = "" + 1;
        recruitment.title = "招募测试标题" + 1;
        recruitment.priceUnit = "人" + 1;
        recruitment.issueTimeStamp = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000;

        request.request(new ICreateRecruitmentResponce(), recruitment);
    }


    /**
     * 创建招募响应回调接口
     */
    class ICreateRecruitmentResponce extends BaseResponceImpl implements CreateRecruitmentRequest.ICreateRecruitmentResponce {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void success(Recruitment recruit) {
            tv_display.setText("");

            tv_display.append("招募名称:" + recruit.recruitmentId + "\n");
            tv_display.append("  招募均价:" + recruit.averagePrice + "\n");
            tv_display.append("  招募描述:" + recruit.description + "\n");
            tv_display.append("  招募封面图url:" + recruit.coverUrl + "\n");
            tv_display.append("  分享id:" + recruit.recruitmentId + "\n");
            tv_display.append("  招募发表时间:" + ModelUtils.getDateDesByTimeStamp(recruit.issueTimeStamp) + "\n");


        }
    }
}

