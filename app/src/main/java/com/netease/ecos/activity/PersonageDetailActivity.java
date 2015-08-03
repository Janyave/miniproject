package com.netease.ecos.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.netease.ecos.R;
import com.netease.ecos.adapter.PersonCourseAdapter;
import com.netease.ecos.adapter.PersonDisplayAdapter;
import com.netease.ecos.adapter.RecruitmentDetailWorkAdapter;
import com.netease.ecos.fragment.PersonageActivityFragment;
import com.netease.ecos.fragment.PersonageCourseFragment;
import com.netease.ecos.fragment.PersonageRecruitFragment;
import com.netease.ecos.fragment.PersonageShareFragment;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonageDetailActivity extends BaseActivity implements View.OnClickListener{

    public static final String UserID = "UserID";
    @InjectView(R.id.radio_group)
    RadioGroup mRadioGroup;

    @InjectView(R.id.iv_return)
    ImageView iv_return;

    @InjectView(R.id.btn_attention)
    Button btn_attention;
    @InjectView(R.id.btn_contact)
    Button btn_contact;

    @InjectView(R.id.lv_list)
    ExtensibleListView lv_list;

    private UserDataService mUserDataService;
    private User mUserData;


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


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
//        String ID=bundle.getString("UserID");

        //TODO 比对ID
        initUserData();
    }

    /**
     * 初始化视图
     */
    private void initViews() {

        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

        ((RadioButton) mRadioGroup.getChildAt(mCurrentTab)).setChecked(true);

        iv_return.setOnClickListener(this);
        btn_attention.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
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
                    ((RadioButton)findViewById(R.id.radio_1)).setTextColor(getResources().getColor(R.color.text_red));
                    lv_list.setAdapter(personCourseAdapter);
                    break;
                case R.id.radio_2:
                    ((RadioButton)findViewById(R.id.radio_2)).setTextColor(getResources().getColor(R.color.text_red));
                    lv_list.setAdapter(personDisplayAdapter);
                    break;
                case R.id.radio_3:
                    ((RadioButton)findViewById(R.id.radio_3)).setTextColor(getResources().getColor(R.color.text_red));
                    Toast.makeText(PersonageDetailActivity.this, "CHANGE ADAPTER", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radio_4:
                    ((RadioButton)findViewById(R.id.radio_4)).setTextColor(getResources().getColor(R.color.text_red));
                    Toast.makeText(PersonageDetailActivity.this, "CHANGE ADAPTER", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void setUnChecked() {
        ((RadioButton)findViewById(R.id.radio_1)).setTextColor(getResources().getColor(R.color.text_gray));
        ((RadioButton)findViewById(R.id.radio_2)).setTextColor(getResources().getColor(R.color.text_gray));
        ((RadioButton)findViewById(R.id.radio_3)).setTextColor(getResources().getColor(R.color.text_gray));
        ((RadioButton)findViewById(R.id.radio_4)).setTextColor(getResources().getColor(R.color.text_gray));
    }


    private void initUserData() {
        mUserDataService = UserDataService.getSingleUserDataService(this);
        mUserData = mUserDataService.getUser();

        RoundImageView user_avatar = (RoundImageView) findViewById(R.id.iv_personage_portrait);
        TextView user_name = (TextView) findViewById(R.id.bt_personage_name);
        ImageView user_gender = (ImageView) findViewById(R.id.riv_personage_gender);
        TextView user_attention = (TextView) findViewById(R.id.tv_personage_attention);
        TextView user_fans = (TextView) findViewById(R.id.tv_personage_fans);
        TextView user_description = (TextView) findViewById(R.id.tv_personage_description);

        //设置默认图片
        user_avatar.setDefaultImageResId(R.drawable.img_default);
        //设置加载出错图片
        user_avatar.setErrorImageResId(R.drawable.img_default);
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageLoader.ImageCache imageCache = new SDImageCache();
        ImageLoader imageLoader = new ImageLoader(queue, imageCache);
        user_avatar.setImageUrl(mUserData.avatarUrl, imageLoader);

        user_name.setText(mUserData.nickname);
        if (mUserData.gender == User.Gender.女) {
            user_gender.setImageDrawable(getResources().getDrawable(R.drawable.img_gender_famale));
        } else {
            user_gender.setImageDrawable(getResources().getDrawable(R.drawable.img_gender_male));
        }
        user_attention.setText(""+ mUserData.followOtherNum);
        user_fans.setText(""+ mUserData.fansNum);
        user_description.setText(mUserData.characterSignature);


        //TODO 初始化adapter
        personCourseAdapter=new PersonCourseAdapter(this);
        personDisplayAdapter=new PersonDisplayAdapter(this);
        lv_list.setAdapter(personCourseAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_return:
                finish();
                break;
            case R.id.btn_attention:
                if (btn_attention.getText().equals("已关注")){
                    btn_attention.setText("+关注");
                }else {
                    btn_attention.setText("已关注");
                }
                break;
            case R.id.btn_contact:
                Intent intent=new Intent(PersonageDetailActivity.this, ContactActivity.class);
                startActivity(intent);
                break;
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_personage_detail, menu);
//        return true;
//    }


}
