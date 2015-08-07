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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.RecruitmentDetailWorkAdapter;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.recruitment.GetRecruitmentDetailRequest;
import com.netease.ecos.request.share.ShareListRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecruitmentDetailActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "Ecos---RecruitmentDet";
    public static final String RecruitID = "RecruitID";
    public static final String RecruitType = "RecruitType";
    public static final String UserId = "UserId";

    @InjectView(R.id.lly_right_action)
    LinearLayout title_right;
    @InjectView(R.id.tv_right_text)
    TextView title_right_text;
    @InjectView(R.id.tv_title)
    TextView title_text;
    @InjectView(R.id.lly_left_action)
    LinearLayout title_left;

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
    private Recruitment.RecruitType recruitType;
    private String userId = "";

    //for request
    private GetRecruitmentDetailRequest request;
    private GetRecruitmentLDetailResponse response;
    private ShareListRequest shareListRequest;
    private ShareListResponse shareListResponse;
    private List<Share> shareList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_detail);
        ButterKnife.inject(this);

        initTitle();
        initListener();
        initData();
        getSupportActionBar().hide();
    }

    private void initTitle() {
        title_left.setOnClickListener(this);
        title_right.setVisibility(View.INVISIBLE);
        title_right_text.setText("评论");
        title_text.setText("招募详情");
    }


    private void initListener() {
        ll_author.setOnClickListener(this);
        tv_talk.setOnClickListener(this);
        lv_list.setOnItemClickListener(this);
        lv_list.setDividerHeight(0);
    }

    private void initData() {
        recruitID = getIntent().getExtras().getString(RecruitID);
        recruitType = Recruitment.RecruitType.getRecruitTypeByValue(getIntent().getExtras().getString(RecruitType));
        userId = getIntent().getExtras().getString(UserId);
        request = new GetRecruitmentDetailRequest();
        response = new GetRecruitmentLDetailResponse();
        shareListRequest = new ShareListRequest();
        shareListResponse = new ShareListResponse();
        Share.Tag tag = new Share.Tag();
        if (recruitType == Recruitment.RecruitType.妆娘)
            tag.isMakeup = true;
        else if (recruitType == Recruitment.RecruitType.后期)
            tag.isLater = true;
        else if (recruitType == Recruitment.RecruitType.其他)
            tag.isLater = true;
        else if (recruitType == Recruitment.RecruitType.摄影)
            tag.isLater = true;
        else if (recruitType == Recruitment.RecruitType.服装)
            tag.isLater = true;
        else if (recruitType == Recruitment.RecruitType.道具)
            tag.isLater = true;
        shareListRequest.requestSomeOneShareWithTag(shareListResponse, userId, tag, 1);
        request.request(response, recruitID);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.ll_author:
                intent = new Intent(RecruitmentDetailActivity.this, PersonageDetailActivity.class);
                bundle.putString(PersonageDetailActivity.UserID, recruitment.userId);
                bundle.putBoolean(PersonageDetailActivity.IsOwn, false);
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
        bundle.putString(DisplayDetailActivity.ShareId, this.shareList.get(position).shareId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class GetRecruitmentLDetailResponse extends BaseResponceImpl implements GetRecruitmentDetailRequest.IGetRecruitmentLDetailResponse {

        @Override
        public void doAfterFailedResponse(String message) {
            Toast.makeText(RecruitmentDetailActivity.this, "error happens:" + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(Recruitment recruit) {
            recruitment = recruit;
            if (recruit.avatarUrl != null && !recruit.avatarUrl.equals(""))
                Picasso.with(RecruitmentDetailActivity.this).load(recruit.avatarUrl).placeholder(R.drawable.img_default).into(iv_avator);
            tv_name.setText(recruit.nickname);
            tv_distance.setText(recruit.distanceKM + getResources().getString(R.string.KM));
            tv_price.setText(recruit.averagePrice + recruit.recruitType.getPriceUnit());
            tv_detail.setText(recruit.description);
        }

    }

    class ShareListResponse extends BaseResponceImpl implements ShareListRequest.IShareListResponse {

        @Override
        public void success(List<Share> shareList) {
            RecruitmentDetailActivity.this.shareList = shareList;
            if (shareList.size() == 0) {
                Toast.makeText(RecruitmentDetailActivity.this, getResources().getString(R.string.noOtherShare), Toast.LENGTH_SHORT).show();
            }
            recruitmentDetailWorkAdapter = new RecruitmentDetailWorkAdapter(RecruitmentDetailActivity.this, shareList);
            lv_list.setAdapter(recruitmentDetailWorkAdapter);
        }

        @Override
        public void doAfterFailedResponse(String message) {
            Toast.makeText(RecruitmentDetailActivity.this, "error happens:" + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
}
