package com.netease.ecos.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.netease.ecos.R;
import com.netease.ecos.adapter.PersonCourseAdapter;
import com.netease.ecos.adapter.PersonDisplayAdapter;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.user.FollowUserRequest;
import com.netease.ecos.request.user.GetUserInfoRequest;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonageDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String UserID = "UserID";
    public static final String IsOwn = "IsOwn";
    private boolean isOwn;
    private String userID = "";
    @InjectView(R.id.iv_personage_portrait)
    RoundImageView user_avatar;
    @InjectView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @InjectView(R.id.bt_personage_name)
    TextView user_name;
    @InjectView(R.id.riv_personage_gender)
    ImageView user_gender;
    @InjectView(R.id.tv_personage_attention)
    TextView user_attention;
    @InjectView(R.id.tv_personage_fans)
    TextView user_fans;
    @InjectView(R.id.tv_personage_description)
    TextView user_description;
    @InjectView(R.id.iv_return)
    ImageView iv_return;
    @InjectView(R.id.btn_attention)
    Button btn_attention;
    @InjectView(R.id.btn_contact)
    Button btn_contact;
    @InjectView(R.id.lv_list)
    ExtensibleListView lv_list;
    @InjectView(R.id.contactLayout)
    LinearLayout contactLayout;

    private UserDataService mUserDataService;
    private User mUserData;
    //for request
    private GetUserInfoRequest getUserInfoRequest;
    private GetuserInfoResponse getuserInfoResponse;
    private FollowUserRequest followUserRequest;
    private FollowResponce followResponce;


    public static final int TAB_COURSE_INDEX = 0;
    public static final int TAB_COMMUCITY_INDEX = 1;
    public static final int TAB_TRANSACTION_INDEX = 2;
    public static final int TAB_DISPLAY_INDEX = 3;


    private int mCurrentTab = 0;

    private PersonCourseAdapter personCourseAdapter;
    private PersonDisplayAdapter personDisplayAdapter;
    //TODO 其他Adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personage_detail);
        ButterKnife.inject(this);
        initViews();
        initUserData();
    }

    private void initUserData() {
        isOwn = getIntent().getExtras().getBoolean(IsOwn);
        if (isOwn) {
            mUserDataService = UserDataService.getSingleUserDataService(this);
            mUserData = mUserDataService.getUser();
            setData();
        } else {
            getUserInfoRequest = new GetUserInfoRequest();
            getuserInfoResponse = new GetuserInfoResponse();
            userID = getIntent().getExtras().getString(UserID);
            getUserInfoRequest.requestOtherUserInfo(getuserInfoResponse, userID);
        }
    }

    void setData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageLoader.ImageCache imageCache = new SDImageCache();
        ImageLoader imageLoader = new ImageLoader(queue, imageCache);
        user_avatar.setImageUrl(mUserData.avatarUrl, imageLoader);

        user_name.setText(mUserData.nickname);
        if (mUserData.gender == User.Gender.女) {
            user_gender.setImageDrawable(getResources().getDrawable(R.mipmap.ic_gender_female));
        } else {
            user_gender.setImageDrawable(getResources().getDrawable(R.mipmap.ic_gender_male));
        }
        user_attention.setText("" + mUserData.followOtherNum);
        user_fans.setText("" + mUserData.fansNum);
        user_description.setText(mUserData.characterSignature);
        contactLayout.setVisibility(isOwn ? View.GONE : View.VISIBLE);
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        //设置默认图片
        user_avatar.setDefaultImageResId(R.drawable.img_default);
        //设置加载出错图片
        user_avatar.setErrorImageResId(R.drawable.img_default);
        personCourseAdapter = new PersonCourseAdapter(this);
        personDisplayAdapter = new PersonDisplayAdapter(this);
        lv_list.setAdapter(personCourseAdapter);
        //set checked
        ((RadioButton) mRadioGroup.getChildAt(mCurrentTab)).setChecked(true);
        //set listener
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
        iv_return.setOnClickListener(this);
        btn_attention.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
    }

    private void setUnChecked() {
        ((RadioButton) findViewById(R.id.radio_1)).setTextColor(getResources().getColor(R.color.text_gray));
        ((RadioButton) findViewById(R.id.radio_2)).setTextColor(getResources().getColor(R.color.text_gray));
        ((RadioButton) findViewById(R.id.radio_3)).setTextColor(getResources().getColor(R.color.text_gray));
        ((RadioButton) findViewById(R.id.radio_4)).setTextColor(getResources().getColor(R.color.text_gray));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.btn_attention:
                if (followUserRequest == null)
                    followUserRequest = new FollowUserRequest();
                if (followResponce == null)
                    followResponce = new FollowResponce();
                if (btn_attention.getText().equals("已关注")) {
                    btn_attention.setText("+关注");
                    followUserRequest.request(followResponce, userID, false);
                } else {
                    btn_attention.setText("已关注");
                    followUserRequest.request(followResponce, userID, true);
                }
                break;
            case R.id.btn_contact:
                Intent intent = new Intent(PersonageDetailActivity.this, ContactActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * {@link #mRadioGroup}监听Radio 按键
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            setUnChecked();
            switch (checkedId) {
                case R.id.radio_1:
                    ((RadioButton) findViewById(R.id.radio_1)).setTextColor(getResources().getColor(R.color.text_red));
                    lv_list.setAdapter(personCourseAdapter);
                    break;
                case R.id.radio_2:
                    ((RadioButton) findViewById(R.id.radio_2)).setTextColor(getResources().getColor(R.color.text_red));
                    lv_list.setAdapter(personDisplayAdapter);
                    break;
                case R.id.radio_3:
                    ((RadioButton) findViewById(R.id.radio_3)).setTextColor(getResources().getColor(R.color.text_red));
                    Toast.makeText(PersonageDetailActivity.this, "CHANGE ADAPTER", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radio_4:
                    ((RadioButton) findViewById(R.id.radio_4)).setTextColor(getResources().getColor(R.color.text_red));
                    Toast.makeText(PersonageDetailActivity.this, "CHANGE ADAPTER", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    class GetuserInfoResponse extends BaseResponceImpl implements GetUserInfoRequest.IGetUserInfoResponse {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(User user) {
//            tv_display.append("用户id: " + user.userId + "\n");
//            tv_display.append("  云信id: " + user.imId + "\n");
//            tv_display.append("  头像url: " + user.avatarUrl + "\n");
//            tv_display.append("  昵称: " + user.nickname + "\n");
//            tv_display.append("  个性签名: " + user.characterSignature + "\n");
//            tv_display.append("  封面图: " + user.coverUrl + "\n");
//            tv_display.append("  粉丝数: " + user.fansNum + "\n");
//            tv_display.append("  性别: " + user.gender.name() + "\n");
//            tv_display.append("  角色: " + user.roleTypeSet + "\n");
            mUserData = user;
            setData();
        }
    }

    class FollowResponce extends BaseResponceImpl implements FollowUserRequest.IFollowResponce {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(String userId, boolean follow) {
//            tv_display.append("操作对象userId:" + userId + "\n");
//            tv_display.append("关注状态:" + follow + "\n");
        }
    }
}
