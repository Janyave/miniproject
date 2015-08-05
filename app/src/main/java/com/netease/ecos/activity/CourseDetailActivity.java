package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.CourseDetailOtherWorksHListViewAdapter;
import com.netease.ecos.adapter.CourseDetailStepAdapter;
import com.netease.ecos.dialog.SetPhotoDialog;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.course.GetCourseDetailRequest;
import com.netease.ecos.request.course.PraiseRequest;
import com.netease.ecos.request.user.FollowUserRequest;
import com.netease.ecos.utils.SetPhotoHelper;
import com.netease.ecos.views.ExtensibleListView;
import com.netease.ecos.views.HorizontalListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourseDetailActivity extends ActionBarActivity implements View.OnClickListener {
    private final String TAG = "Ecos---CourseDetail";

    public static final String CourseID = "CourseID";
    //the widget of the title bar
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    @InjectView(R.id.iv_cover)
    ImageView iv_cover;
    @InjectView(R.id.ll_praise)
    LinearLayout ll_praise;
    @InjectView(R.id.tv_praise)
    TextView tv_praise;
    @InjectView(R.id.iv_praise)
    ImageView iv_praise;
    @InjectView(R.id.tv_title1)
    TextView tv_title;
    @InjectView(R.id.tv_praiseNum)
    TextView tv_praiseNum;
    @InjectView(R.id.ll_author)
    LinearLayout ll_author;
    @InjectView(R.id.iv_avatar)
    ImageView iv_avatar;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_otherWorks)
    TextView tv_otherWorks;
    @InjectView(R.id.hlv_otherWorks)
    HorizontalListView hlv_otherWorks;
    @InjectView(R.id.ll_uploadMyWork)
    LinearLayout ll_updoadMyWork;
    @InjectView(R.id.btn_allEvaluation)
    Button btn_allEvaluation;
    @InjectView(R.id.course_detail_scrollveiw)
    ScrollView scrollView;
    @InjectView(R.id.lv_courseStep)
    ExtensibleListView lv_courseStep;

    //教程步骤
    private CourseDetailStepAdapter courseDetailStepAdapter;
    //作业列表
    private CourseDetailOtherWorksHListViewAdapter courseDetailOtherWorksHListViewAdapter;

    private SetPhotoHelper mSetPhotoHelper;
    //图片裁剪后输出宽度
    private final int outPutWidth = 450;
    //图片裁剪后输出高度
    private final int outPutHeight = 300;

    //record the list of assignment id.
    private ArrayList<String> workList;
    private String courseId = "";
    private Course course;
    //for request
    private GetCourseDetailRequest getCourseDetailRequest;
    private GetCourseDetailResponse getCourseDetailResponse;
    private PraiseRequest praiseRequest;
    private PraiseResponse praiseResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.inject(this);
        initData();
        initView();
        getSupportActionBar().hide();
    }

    private void initData() {
        courseId = getIntent().getExtras().getString(CourseID);
        getCourseDetailRequest = new GetCourseDetailRequest();
        getCourseDetailResponse = new GetCourseDetailResponse();
        getCourseDetailRequest.request(getCourseDetailResponse, courseId);
    }

    private void initView() {
        btn_allEvaluation.setOnClickListener(this);
        ll_updoadMyWork.setOnClickListener(this);
        ll_author.setOnClickListener(this);
        ll_praise.setOnClickListener(this);
        lv_courseStep.setDividerHeight(0);

        hlv_otherWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseDetailActivity.this, AssignmentDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(AssignmentDetailActivity.Work_List, workList);
                bundle.putInt(AssignmentDetailActivity.Work_Order, position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //拦截listview的touch事件，不会造成scrollview的滑动。
        hlv_otherWorks.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        mSetPhotoHelper = new SetPhotoHelper(this, null);
        mSetPhotoHelper.setOutput(outPutWidth, outPutHeight);
        //implementation on the title bar
        titleTxVw.setText("教程详情");
        rightButton.setVisibility(View.INVISIBLE);
        backTxVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDetailActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle;
        switch (v.getId()) {
            case R.id.ll_author:
                intent = new Intent(CourseDetailActivity.this, PersonageDetailActivity.class);
                bundle = new Bundle();
                bundle.putBoolean(PersonageDetailActivity.IsOwn, false);
                bundle.putString(PersonageDetailActivity.UserID, course.userId);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.ll_uploadMyWork:
                SetPhotoDialog dialog = new SetPhotoDialog(CourseDetailActivity.this, new SetPhotoDialog.ISetPhoto() {

                    @Override
                    public void choosePhotoFromLocal() {
                        mSetPhotoHelper.choosePhotoFromLocal();
                    }

                    @Override
                    public void takePhoto() {
                        mSetPhotoHelper.takePhoto(true);
                    }
                });
                dialog.showSetPhotoDialog();
                break;
            case R.id.btn_allEvaluation:
                intent = new Intent(CourseDetailActivity.this, CommentDetailActivity.class);
                bundle = new Bundle();
                bundle.putString(CommentDetailActivity.FromId, courseId);
                bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.教程.getBelongs());
                bundle.putBoolean(CommentDetailActivity.IsPraised, course.hasPraised);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_praise:
                if (praiseRequest == null)
                    praiseRequest = new PraiseRequest();
                if (praiseResponse == null)
                    praiseResponse = new PraiseResponse();
                praiseRequest.praiseCourse(praiseResponse, course.courseId, !course.hasPraised);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SetPhotoHelper.REQUEST_BEFORE_CROP:
                    mSetPhotoHelper.setmSetPhotoCallBack(
                            new SetPhotoHelper.SetPhotoCallBack() {
                                @Override
                                public void success(String imagePath) {
                                    Intent intent = new Intent(CourseDetailActivity.this, UploadAssignmentActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(UploadAssignmentActivity.CourseId, courseId);
                                    bundle.putString(UploadAssignmentActivity.ImagePath, imagePath);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, UploadAssignmentActivity.REQUEST_CODE_FOR_UPLOAD_ASSIGNMENT);
                                }
                            });
                    mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_BEFORE_CROP, data);
                    break;
                case SetPhotoHelper.REQUEST_AFTER_CROP:
                    mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_AFTER_CROP, data);
                    break;
                default:
                    Log.e("CLASS_TAG", "onActivityResult() 无对应");
            }
        } else if (requestCode == UploadAssignmentActivity.REQUEST_CODE_FOR_UPLOAD_ASSIGNMENT && resultCode == UploadAssignmentActivity.RESULT_CODE_FOR_UPLOAD_ASSIGNMENT) {
            getCourseDetailRequest.request(getCourseDetailResponse, courseId);
        }
    }

    /**
     * 网络返回事件处理类
     */
    class GetCourseDetailResponse extends BaseResponceImpl implements GetCourseDetailRequest.ICourseDetailResponse {

        @Override
        public void success(Course course) {
            bindData(course);
        }

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }

    /**
     * 绑定网络返回数据Course
     *
     * @param course
     */
    private void bindData(Course course) {
        Log.d(TAG, "bind the data for course detail");
        this.course = course;
        tv_title.setText(course.title);
        tv_name.setText(course.author);
        tv_praiseNum.setText(course.praiseNum + getResources().getString(R.string.manyFavor));
        setPraiseLayout();
        if (course.authorAvatarUrl != null && !course.authorAvatarUrl.equals(""))
            Picasso.with(CourseDetailActivity.this).load(course.authorAvatarUrl).placeholder(R.drawable.img_default).into(iv_avatar);
        if (course.coverUrl != null && !course.coverUrl.equals(""))
            Picasso.with(CourseDetailActivity.this).load(course.coverUrl).placeholder(R.drawable.img_default).into(iv_cover);
        tv_otherWorks.setText(course.assignmentNum + getResources().getString(R.string.manyAssignment));
        btn_allEvaluation.setText(course.commentNum + getResources().getString(R.string.manyComment));
        courseDetailStepAdapter = new CourseDetailStepAdapter(this, course.stepList);
        lv_courseStep.setAdapter(courseDetailStepAdapter);
        //if there is no assignment.
        if (course.assignmentList.size() == 0)
            hlv_otherWorks.setVisibility(View.GONE);
        courseDetailOtherWorksHListViewAdapter = new CourseDetailOtherWorksHListViewAdapter(this, course.assignmentList);
        hlv_otherWorks.setAdapter(courseDetailOtherWorksHListViewAdapter);

        //get the list of assignment id
        workList = new ArrayList<String>();
        for (int i = 0; i < course.assignmentList.size(); i++) {
            workList.add(course.assignmentList.get(i).assignmentId);
            Log.d(TAG, "worklist " + i + ":" + course.assignmentList.get(i).assignmentId);
        }
    }

    private void setPraiseLayout() {
        if (course.hasPraised) {
            tv_praise.setText(getResources().getString(R.string.cancelFavor));
            iv_praise.setImageResource(R.mipmap.ic_praise_fill);
        } else {
            tv_praise.setText(getResources().getString(R.string.favour));
            iv_praise.setImageResource(R.mipmap.ic_praise_block);
        }
    }

    class FollowResponce extends BaseResponceImpl implements FollowUserRequest.IFollowResponce {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(String userId, boolean follow) {
        }
    }

    class PraiseResponse extends BaseResponceImpl implements PraiseRequest.IPraiseResponce {

        @Override
        public void success(String userId, boolean praise) {
            course.hasPraised = !course.hasPraised;
            setPraiseLayout();
        }

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
}
