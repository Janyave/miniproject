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

    @InjectView(R.id.bt_personage_return)
    Button bt_personage_return;

    @InjectView(R.id.bt_attention)
    Button bt_attention;


    /*** �̳�tab�±� */
    public static final int TAB_COURSE_INDEX = 0;

    /*** ����tab�±� */
    public static final int TAB_COMMUCITY_INDEX = 1;

    /*** ����tab�±� */
    public static final int TAB_TRANSACTION_INDEX = 2;

    /*** չʾtab�±� */
    public static final int TAB_DISPLAY_INDEX = 3;


    /***
     * mFragments[0]��Ӧ{@link CourseFragment}
     * mFragments[1]��Ӧ{@link CommunityFragment}
     * mFragments[2]��Ӧ{@link TransactionFragment}
     */
    private Fragment mFragments[] = new Fragment[4];

    /*** ��ǰ������tab */
    private int mCurrentTab = 0;
    /*** {@link #mViewPager}������ */
    private TabFragmentPagerAdapter mPagerAdapter;



    /***
     * ��ʼ����ͼ
     */
    private void initViews(){

        mPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mCurrentTab);

        //����һ��ҳ������Ӧ�־û���fragmentΪ2
        mViewPager.setOffscreenPageLimit(2);

        //���û�ҳ����
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);

        //���ð�ť����
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

        //ѡ�е�mCurrentTab����ť
        ((RadioButton)mRadioGroup.getChildAt(mCurrentTab)).setChecked(true);

        bt_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "��ע�ɹ�", Toast.LENGTH_SHORT);
            }
        });
        bt_personage_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /***
     * {@link #mRadioGroup}�İ�ť������
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                //����̳�tab
                case R.id.radio_1:
                    setCurrentTab(TAB_COURSE_INDEX);
                    break;
                //�������tab
                case R.id.radio_2:
                    setCurrentTab(TAB_COMMUCITY_INDEX);
                    break;
                //�������tab
                case R.id.radio_3:
                    setCurrentTab(TAB_TRANSACTION_INDEX);
                    break;
                //���չʾtab
                case R.id.radio_4:
                    setCurrentTab(TAB_DISPLAY_INDEX);
                    break;
            }
        }
    };

    /***
     * {@link #mViewPager}ѡ�е�index��ҳ��
     * @param index
     */
    private void setCurrentTab(int index){
        if(mViewPager != null && (index >= 0 && index <= 3)){
            mViewPager.setCurrentItem(index);
            mCurrentTab = index;
        }
    }

    /***
     * {@link #mViewPager}��ҳ������
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
            //ѡ�е�index��tab��ť����������Ӧtabѡ��Ч��(��ť�·������ָʾ��)
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
                //����̳�tab��Ҫ��ʾ�̳�ҳ��
                case TAB_COURSE_INDEX:
                    if(mFragments[TAB_COURSE_INDEX]==null)
                        mFragments[TAB_COURSE_INDEX] = new PersonageCourseFragment();
                    return mFragments[TAB_COURSE_INDEX];

                //�������tab��Ҫ��ʾ����ҳ��
                case TAB_COMMUCITY_INDEX:
                    if(mFragments[TAB_COMMUCITY_INDEX]==null)
                        mFragments[TAB_COMMUCITY_INDEX] = new CommunityFragment();
                    return mFragments[TAB_COMMUCITY_INDEX];

                //�������tab��Ҫ��ʾ����ҳ��
                case TAB_TRANSACTION_INDEX:
                    if(mFragments[TAB_TRANSACTION_INDEX]==null)
                        mFragments[TAB_TRANSACTION_INDEX] = new TransactionFragment();
                    return mFragments[TAB_TRANSACTION_INDEX];

                //���չʾtab��Ҫ��ʾ����ҳ��
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
        //ע�⹤�߳�ʼ��
        ButterKnife.inject(this);
        //����ActionBar
        initViews();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_personage_detail, menu);
//        return true;
//    }


}
