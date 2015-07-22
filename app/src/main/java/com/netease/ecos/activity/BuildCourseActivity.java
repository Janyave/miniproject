package com.netease.ecos.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.netease.ecos.R;
import com.netease.ecos.model.Course;
import com.netease.ecos.utils.SDImageCache;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 类描述：创建教程
 * Created by enlizhang on 2015/7/22.
 */
public class BuildCourseActivity extends BaseActivity{

    /*** 教程步骤列表 */
    @InjectView(R.id.lv_build_course)
    ListView lv_build_course;


    public String DESCRIPTIONS[] = {"卸妆", "补水", "上霜", "画眼线", "做头发"};

    public String URLS[] = {"http://g.hiphotos.baidu.com/image/pic/item/3801213fb80e7bec52f541e02d2eb9389b506b87.jpg"
    ,"http://g.hiphotos.baidu.com/image/pic/item/9825bc315c6034a8a0a41671c8134954082376f8.jpg"
    ,"http://g.hiphotos.baidu.com/image/pic/item/0b55b319ebc4b745e5fed681ccfc1e178a82153a.jpg"
    ,"http://h.hiphotos.baidu.com/image/pic/item/0823dd54564e9258067578999e82d158ccbf4e00.jpg"
    ,"http://b.hiphotos.baidu.com/image/w%3D230/sign=eed34f0f0846f21fc9345950c6256b31/a044ad345982b2b7e9bb383033adcbef76099b19.jpg"};

    @Override
    protected void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        setContentView(R.layout.activity_build_course);

        //注解工具初始化
        ButterKnife.inject(this);

        List<Course.Step> stepsList = new ArrayList<Course.Step>();

        for(int i=0;i<DESCRIPTIONS.length;i++){
            Course.Step step = new Course.Step();
            step.stepIndex = i+1 ;
            step.description = DESCRIPTIONS[i];
            step.photoUrl = URLS[i];
            stepsList.add(step);
        }

        lv_build_course.setAdapter(new CourseStepAdapter(this,stepsList));
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
    }


    /***
     * 教程步骤列表适配器
     */
    class CourseStepAdapter extends BaseAdapter{

        private Context mContext;

        private List<Course.Step> mStepsList;

        private LayoutInflater mInflater;

        public CourseStepAdapter(Context context, List<Course.Step> stepsList){
            mContext = context;
            mStepsList = stepsList;

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
            NetworkImageView niv_course_photo;
            /*** 步骤描述 */
            EditText etv_description;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e("创建教程","position: " + position);
            final ViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_build_course, null);
                holder = new ViewHolder();

                holder.tv_index = (TextView)convertView.findViewById(R.id.tv_index);
                holder.iv_last_step = (ImageView)convertView.findViewById(R.id.iv_last_step);
                holder.iv_next_step = (ImageView)convertView.findViewById(R.id.iv_next_step);
                holder.niv_course_photo = (NetworkImageView)convertView.findViewById(R.id.niv_course_photo);
                holder.etv_description = (EditText)convertView.findViewById(R.id.etv_description);

                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

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

            holder.niv_course_photo.setErrorImageResId(R.drawable.bg_niv_error);
            //设置兼职logo
            holder.niv_course_photo.setImageUrl(stepData.photoUrl, mImageLoader);

            //对步骤描述进行数字限制
            //.......
            holder.etv_description.setText(stepData.description);

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
                switch(view.getId()){
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
    }
}
