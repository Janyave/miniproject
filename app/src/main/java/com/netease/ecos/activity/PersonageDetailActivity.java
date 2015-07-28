package com.netease.ecos.activity;

import android.app.Activity;
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


import com.netease.ecos.R;
import com.netease.ecos.fragment.CommunityFragment;
import com.netease.ecos.fragment.CourseFragment;
import com.netease.ecos.fragment.DisplayFragment;
import com.netease.ecos.fragment.PersonageCourseFragment;
import com.netease.ecos.fragment.TransactionFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonageDetailActivity extends BaseActivity {

    @InjectView(R.id.radio_group)
    RadioGroup mRadioGroup ;

    @InjectView(R.id.pager)
    ViewPager mViewPager ;

    @InjectView(R.id.lly_left_action)
    View bt_personage_return;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.btn_right_action)
    Button bt_confirm;

    @InjectView(R.id.bt_attention)
    Button bt_attention;



    public static final int TAB_COURSE_INDEX = 0;
    public static final int TAB_COMMUCITY_INDEX = 1;
    public static final int TAB_TRANSACTION_INDEX = 2;
    public static final int TAB_DISPLAY_INDEX = 3;

    private Fragment mFragments[] = new Fragment[4];

    private int mCurrentTab = 0;
    private TabFragmentPagerAdapter mPagerAdapter;



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

        bt_confirm.setVisibility(View.INVISIBLE);
        tv_title.setText("个人主页");

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
                        mFragments[TAB_COMMUCITY_INDEX] = new CommunityFragment();
                    return mFragments[TAB_COMMUCITY_INDEX];

                case TAB_TRANSACTION_INDEX:
                    if(mFragments[TAB_TRANSACTION_INDEX]==null)
                        mFragments[TAB_TRANSACTION_INDEX] = new TransactionFragment();
                    return mFragments[TAB_TRANSACTION_INDEX];

                case TAB_DISPLAY_INDEX:
                    if(mFragments[TAB_DISPLAY_INDEX]==null)
                        mFragments[TAB_DISPLAY_INDEX] = new DisplayFragment();
                    return mFragments[TAB_DISPLAY_INDEX];
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personage_detail);

        ButterKnife.inject(this);

        initViews();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_personage_detail, menu);
//        return true;
//    }


}
