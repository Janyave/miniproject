package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.ActivityPhotoHListViewAdapter;
import com.netease.ecos.adapter.EventContactWayAdapter;
import com.netease.ecos.model.ActivityModel;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.activity.GetActivityDetailRequest;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.views.ExtensibleListView;
import com.netease.ecos.views.HorizontalListView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/8/4.
 */
public class ActivityDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Ecos---EventDetail";
    public static final String ActivityID = "ActivityId";
    private String activityID = "";
    @InjectView(R.id.sv)
    ScrollView sv;
    @InjectView(R.id.btn_right_action)
    Button btn_right_action;
    @InjectView(R.id.lly_left_action)
    LinearLayout lly_left_action;
    @InjectView(R.id.iv_event_cover)
    ImageView iv_event_cover;
    @InjectView(R.id.tv_event_title)
    TextView tv_event_title;
    @InjectView(R.id.tv_event_location)
    TextView tv_event_location;
    @InjectView(R.id.tv_event_price)
    TextView tv_event_price;
    @InjectView(R.id.hlv_photos)
    HorizontalListView hlv_photos;
    @InjectView(R.id.tv_publish_photo)
    TextView tv_publish_photo;
    @InjectView(R.id.tv_event_location_detail)
    TextView tv_event_location_detail;
    @InjectView(R.id.tv_event_time_detail)
    TextView tv_event_time_detail;
    @InjectView(R.id.tv_event_content_detail)
    TextView tv_event_content_detail;
    @InjectView(R.id.tv_wantgo)
    TextView tv_wantgo;
    @InjectView(R.id.tv_wangoNum)
    TextView tv_wangoNum;
    @InjectView(R.id.lv_list)
    ExtensibleListView lv_list;
    @InjectView(R.id.iv_author_avator)
    RoundImageView iv_author_avator;
    @InjectView(R.id.tv_author_name)
    TextView tv_author_name;
    @InjectView(R.id.tv_author_time)
    TextView tv_author_time;

    private ActivityPhotoHListViewAdapter activityPhotoHListViewAdapter;
    private EventContactWayAdapter contactWayAdapter;
    //for request
    private GetActivityDetailRequest getActivityDetailRequest;
    private GetActivityDetailResponse getActivityDetailResponse;

    private ActivityModel activityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.inject(this);
        initListener();
        initData();
        initView();
    }

    private void initData() {
        activityID = getIntent().getExtras().getString(ActivityID);
        //for request
        getActivityDetailRequest = new GetActivityDetailRequest();
        getActivityDetailResponse = new GetActivityDetailResponse();
        getActivityDetailRequest.request(getActivityDetailResponse, activityID);
        //set adapter
        activityPhotoHListViewAdapter = new ActivityPhotoHListViewAdapter(this);
        hlv_photos.setAdapter(activityPhotoHListViewAdapter);
        contactWayAdapter = new EventContactWayAdapter(ActivityDetailActivity.this, activityModel.contactWayList);
        lv_list.setAdapter(contactWayAdapter);
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
        btn_right_action.setVisibility(View.INVISIBLE);
        lly_left_action.setOnClickListener(this);
        tv_publish_photo.setOnClickListener(this);
        tv_wantgo.setOnClickListener(this);
        iv_author_avator.setOnClickListener(this);
        tv_author_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.tv_wantgo:
                tv_wantgo.setEnabled(false);
                tv_wantgo.setText(getResources().getString(R.string.alreadyGo));
                tv_wantgo.setTextColor(getResources().getColor(R.color.text_gray));
                //TODO:send the request to go.
                break;
            case R.id.tv_publish_photo:
                //TODO
                Toast.makeText(ActivityDetailActivity.this, "publish photo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_author_avator:
                //TODO
                Toast.makeText(ActivityDetailActivity.this, "person detail", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityDetailActivity.this, PersonageDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(PersonageDetailActivity.IsOwn, false);
                bundle.putString(PersonageDetailActivity.UserID, activityModel.userId);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    class GetActivityDetailResponse extends BaseResponceImpl implements GetActivityDetailRequest.IActivityDetailResponse {

        @Override
        public void success(ActivityModel activity) {
            activityModel = activity;
            if (activity.coverUrl != null && !activity.coverUrl.equals(""))
                Picasso.with(ActivityDetailActivity.this).load(activity.coverUrl).placeholder(R.drawable.img_default).into(iv_event_cover);

            tv_event_title.setText(activity.title);
            tv_event_location.setText(activity.location.province.provinceName);
            tv_event_price.setText(activity.fee);
            //TODO: hlv_photos;
            tv_event_location_detail.setText(activity.location.toString());
            tv_event_time_detail.setText(activity.activityTime.toString());
            tv_event_content_detail.setText(activity.introduction);
            if (activity.hasSignuped) {
                tv_wantgo.setEnabled(false);
                tv_wantgo.setText(getResources().getString(R.string.alreadyGo));
                tv_wantgo.setTextColor(getResources().getColor(R.color.text_gray));
            }
            tv_wangoNum.setText(activity.loveNums);
            //TODO:set the contact way
            if (activity.avatarUrl != null && !activity.avatarUrl.equals(""))
                Picasso.with(ActivityDetailActivity.this).load(activity.avatarUrl).placeholder(R.drawable.img_default).into(iv_author_avator);
            tv_author_name.setText(activity.nickname);
            tv_author_time.setText(activity.getDateDescription());
        }

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
        }
    }
}

