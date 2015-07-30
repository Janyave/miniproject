package com.netease.ecos.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.netease.ecos.R;
import com.netease.ecos.fragment.CommunityFragment;
import com.netease.ecos.fragment.CourseFragment;
import com.netease.ecos.fragment.DisplayFragment;
import com.netease.ecos.fragment.PersonageActivityFragment;
import com.netease.ecos.fragment.PersonageCourseFragment;
import com.netease.ecos.fragment.PersonageRecruitFragment;
import com.netease.ecos.fragment.PersonageShareFragment;
import com.netease.ecos.fragment.TransactionFragment;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonageDetailActivity extends BaseActivity {

    @InjectView(R.id.radio_group)
    RadioGroup mRadioGroup ;

    @InjectView(R.id.pager)
    ViewPager mViewPager ;

    @InjectView(R.id.lly_left_action)
    View bt_personage_return;

    @InjectView(R.id.bt_attention)
    Button bt_attention;

    private UserDataService mUserDataService;
    private User mUserData;


    public static final int TAB_COURSE_INDEX = 0;
    public static final int TAB_COMMUCITY_INDEX = 1;
    public static final int TAB_TRANSACTION_INDEX = 2;
    public static final int TAB_DISPLAY_INDEX = 3;

    private Fragment mFragments[] = new Fragment[4];

    private int mCurrentTab = 0;
    private TabFragmentPagerAdapter mPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personage_detail);

        ButterKnife.inject(this);
        initUserData();
        initViews();
    }

    /***
     * 初始化视图
     */
    private void initViews(){

        mPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mCurrentTab);
        mViewPager.setOffscreenPageLimit(2);

        mViewPager.setOnPageChangeListener(mOnPageChangeListener);

        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

        ((RadioButton)mRadioGroup.getChildAt(mCurrentTab)).setChecked(true);


        bt_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAttentionSuccess();
            }
        });
        bt_personage_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showAttentionSuccess(){
        Toast.makeText(this, "关注成功", Toast.LENGTH_LONG).show();
    }

    /***
     * {@link #mRadioGroup}监听Radio 按键
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.radio_1:
                    setCurrentTab(TAB_COURSE_INDEX);
                    break;
                case R.id.radio_2:
                    setCurrentTab(TAB_COMMUCITY_INDEX);
                    break;
                case R.id.radio_3:
                    setCurrentTab(TAB_TRANSACTION_INDEX);
                    break;
                case R.id.radio_4:
                    setCurrentTab(TAB_DISPLAY_INDEX);
                    break;
            }
        }
    };

    /***
     * {@link #mViewPager}设置当前tab id
     * @param index
     */
    private void setCurrentTab(int index){
        if(mViewPager != null && (index >= 0 && index <= 3)){
            mViewPager.setCurrentItem(index);
            mCurrentTab = index;
        }
    }

    /***
     * {@link #mViewPager}
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrollStateChanged(int status) {

        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageSelected(int index) {
            ((RadioButton)mRadioGroup.getChildAt(index)).setChecked(true);

        }
    };


    private void initUserData(){
        mUserDataService = UserDataService.getSingleUserDataService(this);
        mUserData = mUserDataService.getUser();

        RoundImageView user_avatar = (RoundImageView) findViewById(R.id.iv_personage_portrait);
        TextView user_name = (TextView) findViewById(R.id.bt_personage_name);
        RoundImageView user_gender = (RoundImageView) findViewById(R.id.riv_personage_gender);
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
        if(mUserData.gender == User.Gender.女) {
            user_gender.setBackgroundResource(R.drawable.img_gender_famale);
        }else {
            user_gender.setBackgroundResource(R.drawable.img_gender_male);
        }
        user_attention.setText("关注数：" + mUserData.followOtherNum);
        user_fans.setText("粉丝数：" + mUserData.fansNum);
        user_description.setText(mUserData.characterSignature);

    }

    class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int tabIndex) {
            switch (tabIndex) {
                case TAB_COURSE_INDEX:
                    if(mFragments[TAB_COURSE_INDEX]==null)
                        mFragments[TAB_COURSE_INDEX] = new PersonageCourseFragment();
                    return mFragments[TAB_COURSE_INDEX];

                case TAB_COMMUCITY_INDEX:
                    if(mFragments[TAB_COMMUCITY_INDEX]==null)
                        mFragments[TAB_COMMUCITY_INDEX] = new PersonageActivityFragment();
                    return mFragments[TAB_COMMUCITY_INDEX];

                case TAB_TRANSACTION_INDEX:
                    if(mFragments[TAB_TRANSACTION_INDEX]==null)
                        mFragments[TAB_TRANSACTION_INDEX] = new PersonageRecruitFragment();
                    return mFragments[TAB_TRANSACTION_INDEX];

                case TAB_DISPLAY_INDEX:
                    if(mFragments[TAB_DISPLAY_INDEX]==null)
                        mFragments[TAB_DISPLAY_INDEX] = new PersonageShareFragment();
                    return mFragments[TAB_DISPLAY_INDEX];
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_personage_detail, menu);
//        return true;
//    }


}
