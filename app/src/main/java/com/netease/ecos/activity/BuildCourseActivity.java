package com.netease.ecos.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.netease.ecos.R;
import com.netease.ecos.dialog.SetPhotoDialog;
import com.netease.ecos.model.Course;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.utils.SetPhotoHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 类描述：创建教程
 * Created by enlizhang on 2015/7/22.
 */
public class BuildCourseActivity extends BaseActivity{

    /*** 教程封面 */
    @InjectView(R.id.iv_course_cover)
    ImageView iv_course_cover;

    /*** 教程步骤列表 */
    @InjectView(R.id.lv_build_course)
    ListView lv_build_course;

    /*** 添加步骤 */
    @InjectView(R.id.btn_add_step)
    Button btn_add_step;


    /*** 发布教程 */
    @InjectView(R.id.btn_iss_course)
    Button btn_iss_course;


    public SetPhotoHelper mSetPhotoHelper;


    public String DESCRIPTIONS[] = {"卸妆", "补水", "上霜", "画眼线", "做头发"};

    public String URLS[] = {"http://g.hiphotos.baidu.com/image/pic/item/3801213fb80e7bec52f541e02d2eb9389b506b87.jpg"
    ,"http://g.hiphotos.baidu.com/image/pic/item/9825bc315c6034a8a0a41671c8134954082376f8.jpg"
    ,"http://g.hiphotos.baidu.com/image/pic/item/0b55b319ebc4b745e5fed681ccfc1e178a82153a.jpg"
    ,"http://h.hiphotos.baidu.com/image/pic/item/0823dd54564e9258067578999e82d158ccbf4e00.jpg"
    ,"http://b.hiphotos.baidu.com/image/w%3D230/sign=eed34f0f0846f21fc9345950c6256b31/a044ad345982b2b7e9bb383033adcbef76099b19.jpg"};

    /** 当前正在设置封面的图片 */
    public boolean isSettingCoverPhoto = false;

    /** 当前正在设置教程步骤的图片 */
    public boolean isSettingCoursePhoto = false;

    /*** 当前正在设置第(couserStepPosition+1)步的教程图片 */
    public int mCouserStepPosition = -1;


    CourseStepAdapter mCourseStepAdapter;

    @Override
    protected void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        Log.i(CLASS_TAG,"onCreate()");
        setContentView(R.layout.activity_build_course);

        //注解工具初始化
        ButterKnife.inject(this);



        initData();
    }

    private void initData(){
        List<Course.Step> stepsList = new ArrayList<Course.Step>();

        /*for(int i=0;i<DESCRIPTIONS.length;i++){
            Course.Step step = new Course.Step();
            step.stepIndex = i+1 ;
            step.description = DESCRIPTIONS[i];
            step.photoUrl = URLS[i];
            stepsList.add(step);
        }*/
        int i=0;
        Course.Step step = new Course.Step(i+1);
        stepsList.add(step);

        mCourseStepAdapter = new CourseStepAdapter(this, stepsList, new CourseStepAdapter.AdapterAction() {
            @Override
            public void setPhotoAtPosition(int position) {

                isSettingCoursePhoto = true;
                mCouserStepPosition = position;

                SetPhotoDialog dialog = new SetPhotoDialog(BuildCourseActivity.this,
                        new SetPhotoDialog.ISetPhoto(){

                            @Override
                            public void choosePhotoFromLocal() {
                                Toast.makeText(BuildCourseActivity.this,"选择本地图片",Toast.LENGTH_LONG).show();
                                isSettingCoursePhoto = true;
                                mSetPhotoHelper.choosePhotoFromLocal();
                            }



                            @Override
                            public void takePhoto() {
                                Toast.makeText(BuildCourseActivity.this,"拍照",Toast.LENGTH_LONG).show();
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
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i("onSaveInstanceState", "<--------------------");
        savedInstanceState.putParcelableArrayList("stepData", (ArrayList<Course.Step>) mCourseStepAdapter.getStepDataList());
        Log.i("onSaveInstanceState", "-------------------->");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        List<Course.Step> list = savedInstanceState.getParcelableArrayList("stepData");
        Log.i("onRestoreInstanceState", "--------------------" );

        for(Course.Step step:list){
            Log.i("onRestoreInstanceState", step.toString() );
        }
        Log.i("onRestoreInstanceState", "--------------------" );

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

        if (resultCode == RESULT_OK)
        {
            switch (requestCode) {
                case SetPhotoHelper.REQUEST_BEFORE_CROP:
                    //当前是设置封面
                    if(isSettingCoverPhoto){
                        mSetPhotoHelper.setmSetPhotoCallBack(
                                new SetPhotoHelper.SetPhotoCallBack(){

                            @Override
                            public void success(String imagePath) {
                                Log.i("裁剪后图片路径","-----------path:" + imagePath);
                                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                                iv_course_cover.setImageBitmap(bitmap);
                            }
                        });
                        isSettingCoverPhoto = false;
                        mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_BEFORE_CROP, data);
                        return;
                    }

                    //当前是设置教程步骤图片
                    if(isSettingCoursePhoto)
                    {
                        if( mCouserStepPosition == -1 ){
                            Log.e("设置教程步骤图片","缺少position");
                        }

                        File imageFile = mSetPhotoHelper.getFileBeforeCrop(data,300,200);
                        mCourseStepAdapter.refleshImageAtPosition(mCouserStepPosition, imageFile.getAbsolutePath());

                        isSettingCoursePhoto = false;
                        mCouserStepPosition=-1;
                        return;
                    }

                    break;
                case SetPhotoHelper.REQUEST_AFTER_CROP:
                    mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_AFTER_CROP, data);
                    break;
                default:
                    Log.e("CLASS_TAG" ,"onActivityResult() 无对应");
            }


        }
        else
        {
            Log.e(CLASS_TAG, "操作取消");
        }

    }



    View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                //添加步骤
                case R.id.btn_add_step:
                    //若已有的步骤都已经填写完整
                    if(mCourseStepAdapter.isSomeEmpty()){
                       Toast.makeText(BuildCourseActivity.this,"请将步骤补充完整再进行添加",Toast.LENGTH_LONG).show();
                        return ;
                    }
                    mCourseStepAdapter.getStepDataList().add(new Course.Step(mCourseStepAdapter.getCount()+1));
                    mCourseStepAdapter.notifyDataSetChanged();
                    break;
                //发布教程
                case R.id.btn_iss_course:
                    startActivity(new Intent(BuildCourseActivity.this, MainActivity.class));
                    break;
            }
        }
    };



    /***
     * 教程步骤列表适配器
     */
    static class CourseStepAdapter extends BaseAdapter{

        private Context mContext;

        private List<Course.Step> mStepsList;

        private LayoutInflater mInflater;

        private AdapterAction mAdapterAction;

        public CourseStepAdapter(Context context, List<Course.Step> stepsList,AdapterAction adapterAction){
            mContext = context;
            mStepsList = stepsList;
            mAdapterAction = adapterAction;


            mInflater= LayoutInflater.from(mContext);

            mImageLoader = new ImageLoader(MyApplication.getRequestQueue(), new SDImageCache());
        }

        @Override
        public int getCount() {
            return mStepsList==null?0:mStepsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mStepsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder{
            /*** 步骤序号 */
            TextView tv_index;
            /*** 置为上一步 */
            ImageView iv_last_step;
            /***  置为下一步 */
            ImageView iv_next_step;
            /*** 步骤图片 */
            ImageView niv_course_photo;
            /*** 步骤描述 */
            EditText etv_description;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_build_course, null);
                holder = new ViewHolder();

                holder.tv_index = (TextView)convertView.findViewById(R.id.tv_index);
                holder.iv_last_step = (ImageView)convertView.findViewById(R.id.iv_last_step);
                holder.iv_next_step = (ImageView)convertView.findViewById(R.id.iv_next_step);
                holder.niv_course_photo = (ImageView)convertView.findViewById(R.id.niv_course_photo);
                holder.etv_description = (EditText)convertView.findViewById(R.id.etv_description);

                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.niv_course_photo.setTag(position);
            holder.iv_last_step.setTag(position);
            holder.iv_next_step.setTag(position);

            setData(holder, position, mStepsList.get(position));

            return convertView;
        }

        ImageLoader mImageLoader;
        /***
         * 绑定视图数据
         * @param holder 视图holder
         * @param position 位置
         * @param stepData 步骤数据
         */
        private void setData(ViewHolder holder,int position,Course.Step stepData){
            holder.tv_index.setText(String.valueOf(stepData.stepIndex));

//            holder.niv_course_photo.setErrorImageResId(R.drawable.bg_niv_error);
//            //若照片Url有效，则进行加载
//            if( !(stepData.photoUrl ==null) && !("".equals(stepData.photoUrl.trim())) )
//                holder.niv_course_photo.setImageUrl(stepData.photoUrl, mImageLoader);


            //从SD卡中读取，可以优化为从内存读取，后续做
            if(!(stepData.imagePath ==null) && !("".equals(stepData.imagePath.trim()))){
                File file = new File( stepData.imagePath );
                if( file.exists() ){
                    Bitmap bitmap = BitmapFactory.decodeFile(stepData.imagePath);
                    holder.niv_course_photo.setImageBitmap(bitmap);
                }
                else{
                    Log.e("设置教程步骤图片", "无效路径: " + stepData.imagePath);
                }
            }

            //对步骤描述进行数字限制
            //.......
            holder.etv_description.setText(stepData.description);

            holder.niv_course_photo.setOnClickListener(viewClickListener);
            holder.iv_last_step.setOnClickListener(viewClickListener);
            holder.iv_next_step.setOnClickListener(viewClickListener);
        }


        /***
         * item点击事件，包括点击{@link ViewHolder#iv_next_step}和{@link ViewHolder#iv_last_step}
         */
        View.OnClickListener viewClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View view){
                int position = 0;
                Course.Step tempStep;
                Log.e("点击事件"," ");
                switch(view.getId()){
                    //点击设置图片
                    case R.id.niv_course_photo:
                        position = (int) view.getTag();
                        if(mAdapterAction!=null)
                            mAdapterAction.setPhotoAtPosition(position);
                        break;
                    //点击向上按钮
                    case R.id.iv_last_step:
                        position = (int) view.getTag();
                        if(position == 0 ){
                            Toast.makeText(mContext,"已经置顶，不能上移了",Toast.LENGTH_LONG).show();
                            return;
                        }
                        tempStep = mStepsList.get(position);
                        mStepsList.set(position,mStepsList.get(position-1));
                        mStepsList.set(position-1, tempStep);
                        notifyDataSetChanged();
                        break;
                    //点击向下按钮
                    case R.id.iv_next_step:
                        position = (int) view.getTag();
                        if(position == (getCount()-1) ){
                            Toast.makeText(mContext,"已经在底层，不能下移了",Toast.LENGTH_LONG).show();
                            return;
                        }
                        tempStep = mStepsList.get(position);
                        mStepsList.set(position,mStepsList.get(position+1));
                        mStepsList.set(position+1, tempStep);
                        notifyDataSetChanged();
                        break;
                }

            }
        };


        /***
         * 适配器中的动作接口，包括选择图片
         */
        public interface AdapterAction{
            /***
             * 设置第(position+1)个步骤的照片
             * @param position
             */
            public void setPhotoAtPosition(int position);
        }

        /***
         * 刷新教程第(positon+1)步的图片
         * @param position 图片位置
         * @param imagePath 图片路径
         */
        public void refleshImageAtPosition(int position, String imagePath){

            Log.i("刷新图片","position:" + position + ",------------    imagePath: " + imagePath);
            if(position>=0 && position<getCount()){
                mStepsList.get(position).imagePath = imagePath;
                notifyDataSetChanged();
            }
        }

        public List<Course.Step> getStepDataList(){
            return mStepsList;
        }

        /***
         * 是否有步骤数据不全
         * @return
         */
        public boolean isSomeEmpty(){

            for(Course.Step step:mStepsList){
                if(step.isSomeEmpty())
                    return true;
            }
            return false;
        }
    }
}
