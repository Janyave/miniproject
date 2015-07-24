package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.CourseDetailOtherWorksHListViewAdapter;
import com.netease.ecos.views.HorizontalListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourseDetailActivity extends ActionBarActivity implements View.OnClickListener {

    @InjectView(R.id.iv_cover)
    ImageView iv_cover;
    @InjectView(R.id.ll_praise)
    LinearLayout ll_praise;
    @InjectView(R.id.tv_praise)
    TextView tv_praise;
    @InjectView(R.id.iv_praise)
    ImageView iv_praise;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_praiseNum)
    TextView tv_praiseNum;
    @InjectView(R.id.ll_author)
    LinearLayout ll_author;
    @InjectView(R.id.iv_avatar)
    ImageView iv_avatar;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.ll_courseDetail)
    LinearLayout ll_crouseDetail;
    @InjectView(R.id.tv_otherWorks)
    TextView tv_otherWorks;
    @InjectView(R.id.hlv_otherWorks)
    HorizontalListView hlv_otherWorks;
    @InjectView(R.id.btn_allWorks)
    Button btn_allWorks;
    @InjectView(R.id.btn_uploadMyWork)
    Button btn_updoadMyWork;
    @InjectView(R.id.btn_allEvaluation)
    Button btn_allEvaluation;

    private CourseDetailOtherWorksHListViewAdapter adapter;


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
        btn_updoadMyWork.setOnClickListener(this);
        ll_author.setOnClickListener(this);
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
                intent = new Intent(CourseDetailActivity.this, WorkDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_uploadMyWork:
                startActivity(new Intent(CourseDetailActivity.this, UploadWorkActivity.class));
                break;
            case R.id.btn_allEvaluation:
                //TODO 所有评论
                intent = new Intent(CourseDetailActivity.this, CommentDetailActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initData() {
        adapter = new CourseDetailOtherWorksHListViewAdapter(this);
        hlv_otherWorks.setAdapter(adapter);
        hlv_otherWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:进入网友作品
                Intent intent = new Intent(CourseDetailActivity.this, WorkDetailActivity.class);
                startActivity(intent);
            }
        });
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


}
