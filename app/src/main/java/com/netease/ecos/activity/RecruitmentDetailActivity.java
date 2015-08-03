package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.RecruitmentDetailWorkAdapter;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.recruitment.GetRecruitmentDetailRequest;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecruitmentDetailActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "Ecos---RecruitmentDet";
    public static final String RecruitID = "RecruitID";
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
    private String recruitID = "";
    private Recruitment recruitment;

    //for request
    private GetRecruitmentDetailRequest request;
    private GetRecruitmentLDetailResponse response;

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
        lv_list.setOnItemClickListener(this);
        lv_list.setDividerHeight(0);
    }

    private void initData() {
        recruitID = getIntent().getExtras().getString(RecruitID);
        request = new GetRecruitmentDetailRequest();
        response = new GetRecruitmentLDetailResponse();
        request.request(response, recruitID);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.ll_author:
                intent = new Intent(RecruitmentDetailActivity.this, PersonageDetailActivity.class);
                bundle.putString(PersonageDetailActivity.UserID, recruitment.userId);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_talk:
                intent = new Intent(RecruitmentDetailActivity.this, ContactActivity.class);
                bundle.putString(ContactActivity.TargetUserID, recruitment.userId);
                bundle.putString(ContactActivity.TargetUserName, recruitment.nickname);
                bundle.putString(ContactActivity.TargetUserAvatar, recruitment.avatarUrl);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(RecruitmentDetailActivity.this, DisplayDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DisplayDetailActivity.ShareId, recruitment.shareList.get(position).shareId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class GetRecruitmentLDetailResponse extends BaseResponceImpl implements GetRecruitmentDetailRequest.IGetRecruitmentLDetailResponse {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(Recruitment recruit) {
            recruitment = recruit;
            Picasso.with(RecruitmentDetailActivity.this).load(recruit.avatarUrl).placeholder(R.drawable.img_default).into(iv_avator);
            tv_name.setText(recruit.nickname);
            tv_distance.setText(recruit.distanceKM);
            tv_price.setText(recruit.averagePrice);
            tv_detail.setText(recruit.description);
            recruitmentDetailWorkAdapter = new RecruitmentDetailWorkAdapter(RecruitmentDetailActivity.this, recruitment.shareList);
            lv_list.setAdapter(recruitmentDetailWorkAdapter);
        }

    }
}
