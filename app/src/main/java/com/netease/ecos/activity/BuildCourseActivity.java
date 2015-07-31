package com.netease.ecos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.CourseStepAdapter;
import com.netease.ecos.dialog.SetPhotoDialog;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.course.CreateCourseRequest;
import com.netease.ecos.utils.SetPhotoHelper;
import com.netease.ecos.utils.UploadImageTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 类描述：创建教程
 * Created by enlizhang on 2015/7/22.
 */
public class BuildCourseActivity extends BaseActivity {

    private final String TAG = "Ecos---BuildCourse";
    public static final String CourseType = "courseType";

    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;

    /**
     * 教程名称
     */
    @InjectView(R.id.etv_course_title)
    TextView etv_course_title;

    /**
     * 教程封面
     */
    @InjectView(R.id.iv_course_cover)
    ImageView iv_course_cover;

    /**
     * 教程步骤列表
     */
    @InjectView(R.id.lv_build_course)
    ListView lv_build_course;

    /**
     * 添加步骤
     */
    @InjectView(R.id.btn_add_step)
    Button btn_add_step;


    /**
     * 发布教程
     */
    @InjectView(R.id.btn_iss_course)
    Button btn_iss_course;

    public SetPhotoHelper mSetPhotoHelper;

    public String DESCRIPTIONS[] = {"卸妆", "补水", "上霜", "画眼线", "做头发"};

    public String URLS[] = {"http://g.hiphotos.baidu.com/image/pic/item/3801213fb80e7bec52f541e02d2eb9389b506b87.jpg"
            , "http://g.hiphotos.baidu.com/image/pic/item/9825bc315c6034a8a0a41671c8134954082376f8.jpg"
            , "http://g.hiphotos.baidu.com/image/pic/item/0b55b319ebc4b745e5fed681ccfc1e178a82153a.jpg"
            , "http://h.hiphotos.baidu.com/image/pic/item/0823dd54564e9258067578999e82d158ccbf4e00.jpg"
            , "http://b.hiphotos.baidu.com/image/w%3D230/sign=eed34f0f0846f21fc9345950c6256b31/a044ad345982b2b7e9bb383033adcbef76099b19.jpg"};

    /**
     * 当前正在设置封面的图片
     */
    public boolean isSettingCoverPhoto = false;

    /**
     * 当前正在设置教程步骤的图片
     */
    public boolean isSettingCoursePhoto = false;

    /**
     * 当前正在设置第(couserStepPosition+1)步的教程图片
     */
    public int mCouserStepPosition = -1;


    CourseStepAdapter mCourseStepAdapter;

    /**
     * {@link com.netease.ecos.model.Course.CourseType}枚举值
     */
    public String mCourseTypeValue;

    /**
     * 教程封面本地路径
     */
    public String mCoverLocalPath;

    /**
     * 教程标题
     */
    public String mCourseTitle;


    @Override
    protected void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        Log.i(CLASS_TAG, "onCreate()");
        setContentView(R.layout.activity_build_course);

        if (getIntent() != null) {
            mCourseTypeValue = getIntent().getExtras().getString(CourseType);
        }

        //注解工具初始化
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        //implementation on the title bar
        titleTxVw.setText("新建教程");
        rightButton.setText("发布");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:send the content to the server.
                //TODO:check if it has finish the contents.
                BuildCourseActivity.this.finish();
            }
        });
        backTxVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildCourseActivity.this.finish();
            }
        });

        List<Course.Step> stepsList = new ArrayList<Course.Step>();

        /*for(int i=0;i<DESCRIPTIONS.length;i++){
            Course.Step step = new Course.Step();
            step.stepIndex = i+1 ;
            step.description = DESCRIPTIONS[i];
            step.photoUrl = URLS[i];
            stepsList.add(step);
        }*/
        int i = 0;
        Course.Step step = new Course.Step(i + 1);
        stepsList.add(step);

        mCourseStepAdapter = new CourseStepAdapter(this, stepsList, new CourseStepAdapter.AdapterAction() {
            @Override
            public void setPhotoAtPosition(int position) {

                isSettingCoursePhoto = true;
                mCouserStepPosition = position;

                SetPhotoDialog dialog = new SetPhotoDialog(BuildCourseActivity.this,
                        new SetPhotoDialog.ISetPhoto() {

                            @Override
                            public void choosePhotoFromLocal() {
                                Toast.makeText(BuildCourseActivity.this, "选择本地图片", Toast.LENGTH_LONG).show();
                                isSettingCoursePhoto = true;
                                mSetPhotoHelper.choosePhotoFromLocal();
                            }

                            @Override
                            public void takePhoto() {
                                Toast.makeText(BuildCourseActivity.this, "拍照", Toast.LENGTH_LONG).show();
                                isSettingCoursePhoto = true;
                                mSetPhotoHelper.takePhoto(false);

                            }
                        });
                dialog.showSetPhotoDialog();
            }
        });
        lv_build_course.setAdapter(mCourseStepAdapter);

        mSetPhotoHelper = new SetPhotoHelper(this, null);
        //图片裁剪后输出宽度
        final int outPutWidth = 450;
        //图片裁剪后输出高度
        final int outPutHeight = 300;
        mSetPhotoHelper.setOutput(outPutWidth, outPutHeight);

        iv_course_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPhotoDialog dialog = new SetPhotoDialog(BuildCourseActivity.this, new SetPhotoDialog.ISetPhoto() {

                    @Override
                    public void choosePhotoFromLocal() {
                        isSettingCoverPhoto = true;
                        mSetPhotoHelper.choosePhotoFromLocal();
                    }

                    @Override
                    public void takePhoto() {
                        isSettingCoverPhoto = true;
                        mSetPhotoHelper.takePhoto(true);
                    }
                });
                dialog.showSetPhotoDialog();
            }
        });


        btn_add_step.setOnClickListener(mOnClickListener);
        btn_iss_course.setOnClickListener(mOnClickListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseImageViewResouce(iv_course_cover);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("onSaveInstanceState", "<--------------------");
        savedInstanceState.putParcelableArrayList("stepData", (ArrayList<Course.Step>) mCourseStepAdapter.getStepDataList());
        savedInstanceState.putString("mConurseTypeValue", mCourseTypeValue);
        savedInstanceState.putString("mCoverLocalPath", mCoverLocalPath);
        savedInstanceState.putString("mCourseTitle", mCourseTitle);

        Log.i("onSaveInstanceState", getCourseByPage().toString());
        Log.i("onSaveInstanceState", "-------------------->");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        List<Course.Step> list = savedInstanceState.getParcelableArrayList("stepData");
        Log.i("onRestoreInstanceState", "--------------------");

        for (Course.Step step : list) {
            Log.i("onRestoreInstanceState", step.toString());
        }

        mCourseTypeValue = savedInstanceState.getString("mConurseTypeValue");
        mCoverLocalPath = savedInstanceState.getString("mCoverLocalPath");
        mCourseTitle = savedInstanceState.getString("mCourseTitle");

        releaseImageViewResouce(iv_course_cover);
        iv_course_cover.setImageBitmap(BitmapFactory.decodeFile(mCoverLocalPath));
        etv_course_title.setText(mCourseTitle);

        Log.i("onSaveInstanceState", getCourseByPage().toString());
        Log.i("onRestoreInstanceState", "--------------------");

    }

    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SetPhotoHelper.REQUEST_BEFORE_CROP:
                    //当前是设置封面
                    if (isSettingCoverPhoto) {
                        mSetPhotoHelper.setmSetPhotoCallBack(
                                new SetPhotoHelper.SetPhotoCallBack() {

                                    @Override
                                    public void success(String imagePath) {
                                        Log.i("裁剪后图片路径", "-----------path:" + imagePath);
                                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                                        iv_course_cover.setImageBitmap(bitmap);
                                    }
                                });
                        isSettingCoverPhoto = false;
                        mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_BEFORE_CROP, data);
                        return;
                    }

                    //当前是设置教程步骤图片
                    if (isSettingCoursePhoto) {
                        if (mCouserStepPosition == -1) {
                            Log.e("设置教程步骤图片", "缺少position");
                        }

                        File imageFile = mSetPhotoHelper.getFileBeforeCrop(data, 300, 200);
                        mCourseStepAdapter.refleshImageAtPosition(mCouserStepPosition, imageFile.getAbsolutePath());

                        isSettingCoursePhoto = false;
                        mCouserStepPosition = -1;
                        return;
                    }

                    break;
                case SetPhotoHelper.REQUEST_AFTER_CROP:
                    mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_AFTER_CROP, data);
                    break;
                default:
                    Log.e("CLASS_TAG", "onActivityResult() 无对应");
            }


        } else {
            Log.e(CLASS_TAG, "操作取消");
        }
    }


    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                //添加步骤
                case R.id.btn_add_step:
                    //若已有的步骤都已经填写完整
                    if (mCourseStepAdapter.isSomeEmpty()) {
                        Toast.makeText(BuildCourseActivity.this, "请将步骤补充完整再进行添加", Toast.LENGTH_LONG).show();
                        return;
                    }
                    mCourseStepAdapter.getStepDataList().add(new Course.Step(mCourseStepAdapter.getCount() + 1));
                    mCourseStepAdapter.notifyDataSetChanged();
                    break;
                //发布教程
                case R.id.btn_iss_course:
//                    startActivity(new Intent(BuildCourseActivity.this, MainActivity.class));
                    createCourse();

                    break;
            }
        }
    };

    public void createCourse() {
        Course course = getCourseByPage();
        CreateCourseRequest request = new CreateCourseRequest();
        request.request(new CreateCourseResponse(), course);
    }

    /**
     * 获取页面教程数据
     *
     * @return
     */
    public Course getCourseByPage() {
        final Course course = new Course();
        course.title = etv_course_title.getText().toString();
        course.courseType = Course.CourseType.getCourseType(mCourseTypeValue);

        //获取图片
        if (mCoverLocalPath != null && new File(mCoverLocalPath).exists()) {
            UploadImageTools.uploadImageSys(new File(mCoverLocalPath), new UploadImageTools.UploadCallBack() {

                @Override
                public void success(String originUrl, String thumbUrl) {

                    course.coverUrl = originUrl;
                    Log.i("图片上传", "原图路径" + originUrl);
                    Log.i("图片上传", "缩略图路径" + thumbUrl);
                }

                @Override
                public void fail() {
                    Toast.makeText(BuildCourseActivity.this, "上传失败", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onProcess(Object fileParam, long current, long total) {
                    Log.i("图片上传", "上传数" + total + "  ," + "已上传" + current);
                }

            }, BuildCourseActivity.this, false);
        }

        course.stepList = mCourseStepAdapter.getStepDataList();


        for (final Course.Step step : course.stepList) {
            if (step.imagePath != null && new File(step.imagePath).exists()) {
                UploadImageTools.uploadImageSys(new File(step.imagePath), new UploadImageTools.UploadCallBack() {

                    @Override
                    public void success(String originUrl, String thumbUrl) {

                        step.imageUrl = originUrl;
                    }

                    @Override
                    public void fail() {

                    }

                    @Override
                    public void onProcess(Object fileParam, long current, long total) {
                        Log.i("图片上传", "上传数" + total + "  ," + "已上传" + current);
                    }

                }, BuildCourseActivity.this, false);
            }
        }

        Log.v(TAG, "发布教程数据:------------" + course.toString());

        return course;
    }

    /**
     * 创建教程响应回调接口
     */
    class CreateCourseResponse extends BaseResponceImpl implements CreateCourseRequest.ICreateCourseResponce {

        @Override
        public void success(Course course) {
            Log.e(TAG, "上传成功");
            finish();
        }

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }


}
