package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.CourseListViewAdapter;
import com.netease.ecos.adapter.RecruitmentListViewAdapter;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.course.CourseListRequest;
import com.netease.ecos.views.AnimationHelper;
import com.netease.ecos.views.FloadingButton;
import com.netease.ecos.views.XListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/27.
 */
public class RecruitmentCategoryActivity extends Activity implements View.OnClickListener,XListView.IXListViewListener{

    @InjectView(R.id.sp_sortType)
    Spinner sp_sortType;
    @InjectView(R.id.lv_list)
    XListView lv_list;

    @InjectView(R.id.lly_left_action)
    LinearLayout lly_left_action;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_location)
    TextView tv_location;
    @InjectView(R.id.ll_location)
    LinearLayout ll_location;

    private ArrayAdapter<String> spAdapter;
    private static final String[] sortType={"A型","B型","O型","AB型","其他"};

    private RecruitmentListViewAdapter recruitmentListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_type);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        sp_sortType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO 排序事件
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll_left.setOnClickListener(this);
        lly_left_action.setOnClickListener(this);
        ll_location.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                //TODO 左上角类型选择事件
                break;
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.ll_location:
                //TODO 位置选择界面
                break;
        }
    }

    private void initData() {
        //设置下拉菜单选项
        spAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sortType);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sortType.setAdapter(spAdapter);


        //设置列表Adapter
        lv_list.initRefleshTime(this.getClass().getSimpleName());
        lv_list.setPullLoadEnable(true);
        lv_list.setXListViewListener(this);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(RecruitmentCategoryActivity.this, CourseDetailActivity.class));
            }
        });

        //获取course信息
        recruitmentListViewAdapter=new RecruitmentListViewAdapter(this);
        lv_list.setAdapter(recruitmentListViewAdapter);
    }


    @Override
    public void onRefresh() {
        Toast.makeText(RecruitmentCategoryActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
        //1秒后关闭刷新
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_list.stopRefresh();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        Toast.makeText(RecruitmentCategoryActivity.this, "上拉加载", Toast.LENGTH_SHORT).show();

        //1秒后关闭加载
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_list.stopLoadMore();
            }
        }, 1000);
    }
}
