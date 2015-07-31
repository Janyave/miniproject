package com.netease.ecos.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.RecruitmentDetailWorkAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecruitmentDetailActivity extends ActionBarActivity implements View.OnClickListener{

    @InjectView(R.id.ll_author)
    LinearLayout ll_author;
    @InjectView(R.id.iv_avatar)
    ImageView iv_avator;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_distance)
    TextView tv_distance;
    @InjectView(R.id.tv_price)
    TextView tv_price;
    @InjectView(R.id.tv_talk)
    TextView tv_talk;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_detail)
    TextView tv_detail;
    @InjectView(R.id.lv_list)
    ListView lv_list;

    private RecruitmentDetailWorkAdapter recruitmentDetailWorkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_detail);
        ButterKnife.inject(this);

        initListener();
        initData();

        getSupportActionBar().hide();
    }


    private void initListener() {
        ll_author.setOnClickListener(this);
        tv_talk.setOnClickListener(this);

        lv_list.setDividerHeight(0);
    }

    private void initData() {
        recruitmentDetailWorkAdapter=new RecruitmentDetailWorkAdapter(this);
        lv_list.setAdapter(recruitmentDetailWorkAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_author:
                //TODO  ���˽���
                break;
            case R.id.tv_talk:
                //TODO ˽��
                break;
        }
    }
}
