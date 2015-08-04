package com.netease.ecos.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.adapter.ActivityPhotoHListViewAdapter;
import com.netease.ecos.adapter.CampaignListViewAdapter;
import com.netease.ecos.views.HorizontalListView;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EventDetailActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.lly_left_action)
    View btn_return;
    @InjectView(R.id.btn_right_action)
    Button btn_confirm;

    @InjectView(R.id.sv)
    ScrollView sv;


    @InjectView(R.id.ll_photos)
    LinearLayout ll_photos; //gone when the activity not begin
    @InjectView(R.id.hlv_photos)
    HorizontalListView hlv_photos; //show the photos
    @InjectView(R.id.tv_publish_photo)
    TextView tv_publish_photo;

    ActivityPhotoHListViewAdapter activityPhotoHListViewAdapter;


    @InjectView(R.id.tv_wantgo)
    TextView tv_wantgo;
    @InjectView(R.id.tv_wangoNum)
    TextView tv_wantgoNum;
    @InjectView(R.id.ll_wantgo_icons)
    LinearLayout ll_wantgo_icons;


    //data
    @InjectView(R.id.tv_event_location_detail)
    TextView tv_location;
    @InjectView(R.id.tv_event_time_detail)
    TextView tv_time;
    @InjectView(R.id.tv_event_contant_detail)
    TextView tv_detail;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    @InjectView(R.id.tv_qq)
    TextView tv_qq;
    @InjectView(R.id.tv_weibo)
    TextView tv_weibo;

    //author
    @InjectView(R.id.tv_author_name)
    TextView tv_author_name;
    @InjectView(R.id.tv_author_time)
    TextView tv_author_time;
    @InjectView(R.id.iv_author_avator)
    ImageView iv_author_avator;


    //gone when the data not filled
    @InjectView(R.id.ll_phone)
    LinearLayout ll_phone;
    @InjectView(R.id.ll_qq)
    LinearLayout ll_qq;
    @InjectView(R.id.ll_weibo)
    LinearLayout ll_weibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.inject(this);

        initListener();
        initData();
        initView();
    }

    private void initView() {
        hlv_photos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                sv.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void initListener() {
        btn_confirm.setVisibility(View.INVISIBLE );
        btn_return.setOnClickListener(this);
        tv_wantgo.setOnClickListener(this);
        tv_publish_photo.setOnClickListener(this);
        iv_author_avator.setOnClickListener(this);
    }

    private void initData() {
        activityPhotoHListViewAdapter=new ActivityPhotoHListViewAdapter(this);
        hlv_photos.setAdapter(activityPhotoHListViewAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.tv_wantgo:
                tv_wantgo.setEnabled(false);
                tv_wantgo.setText("已想去");
                tv_wantgo.setTextColor(getResources().getColor(R.color.text_gray));
                break;
            case R.id.tv_publish_photo:
                //TODO
                Toast.makeText(EventDetailActivity.this,"publish photo",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_author_avator:
                //TODO
                Toast.makeText(EventDetailActivity.this,"person detail",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
