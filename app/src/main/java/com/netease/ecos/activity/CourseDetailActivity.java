package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
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
import com.netease.ecos.utils.SetPhotoHelper;
import com.netease.ecos.views.ExtensibleListView;
import com.netease.ecos.views.HorizontalListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourseDetailActivity extends ActionBarActivity implements View.OnClickListener {

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
    @InjectView(R.id.btn_allWorks)
    Button btn_allWorks;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.inject(this);

        initListener();
        initData();

        getSupportActionBar().hide();
    }


    private void initListener() {
        btn_allEvaluation.setOnClickListener(this);
        btn_allWorks.setOnClickListener(this);
        ll_updoadMyWork.setOnClickListener(this);
        ll_author.setOnClickListener(this);
        ll_praise.setOnClickListener(this);
        lv_courseStep.setDividerHeight(0);

        hlv_otherWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseDetailActivity.this, WorkDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(WorkDetailActivity.Work_List, workList);
                bundle.putInt(WorkDetailActivity.Work_Order, position);
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
        switch (v.getId()) {
            case R.id.ll_author:
                //TODO 个人页面
                break;
            case R.id.btn_allWorks:
                //TODO 所有作品
//                intent = new Intent(CourseDetailActivity.this, WorkDetailActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_uploadMyWork:
                //TODO:上传作品
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
                //TODO 所有评论
                intent = new Intent(CourseDetailActivity.this, CommentDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(CommentDetailActivity.FromId, courseId);
                bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.教程.getBelongs());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_praise:
                //点击关注
                if (TextUtils.equals(tv_praise.getText().toString(), "点赞")) {
                    tv_praise.setText("已赞");
                } else {
                    tv_praise.setText("点赞");
                }
                break;
        }
    }

    private void initData() {
        GetCourseDetailRequest request = new GetCourseDetailRequest();
        request.request(new GetCourseDetailResponse(), "1");
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
                                    Intent intent = new Intent(CourseDetailActivity.this, UploadWorkActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(UploadWorkActivity.CourseId, courseId);
                                    bundle.putString(UploadWorkActivity.ImagePath, imagePath);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
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
        courseId = course.courseId;
        tv_title.setText(course.title);
        tv_name.setText(course.author);
        tv_praiseNum.setText(course.praiseNum + "");
        Picasso.with(CourseDetailActivity.this).load(course.authorAvatarUrl).placeholder(R.drawable.img_default).into(iv_avatar);
        Picasso.with(CourseDetailActivity.this).load(course.coverUrl).placeholder(R.drawable.img_default).into(iv_cover);

        tv_otherWorks.setText(course.assignmentNum + " 个网友作品");
        btn_allEvaluation.setText("查看全部" + course.commentNum + "条评论");

        courseDetailStepAdapter = new CourseDetailStepAdapter(this, course.stepList);
        lv_courseStep.setAdapter(courseDetailStepAdapter);

        courseDetailOtherWorksHListViewAdapter = new CourseDetailOtherWorksHListViewAdapter(this, course.assignmentList);
        hlv_otherWorks.setAdapter(courseDetailOtherWorksHListViewAdapter);

        //get the list of assignment id
        workList = new ArrayList<String>();
        for (int i = 0; i < course.assignmentNum; i++) {
            workList.add(course.assignmentList.get(i).assignmentId);
        }
    }
}
