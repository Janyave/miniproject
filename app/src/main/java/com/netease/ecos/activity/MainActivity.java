package com.netease.ecos.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.fragment.CommunityFragment;
import com.netease.ecos.fragment.CourseFragment;
import com.netease.ecos.fragment.DisplayFragment;
import com.netease.ecos.fragment.NavigationDrawerFragment;
import com.netease.ecos.fragment.TransactionFragment;
import com.netease.ecos.utils.RoundImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @InjectView(R.id.btn_open)
    RoundImageView btn_open;

    @InjectView(R.id.radio_group)
    RadioGroup mRadioGroup;

    @InjectView(R.id.pager)
    ViewPager mViewPager;


    /**
     * 教程tab下标
     */
    public static final int TAB_COURSE_INDEX = 0;

    /**
     * 分享tab下标
     */
    public static final int TAB_DISPLAY_INDEX = 1;

    /**
     * 活动tab下标
     */
    public static final int TAB_COMMUCITY_INDEX = 2;

    /**
     * 招募tab下标
     */
    public static final int TAB_TRANSACTION_INDEX = 3;


    /**
     * mFragments[0]对应{@link CourseFragment}
     * mFragments[1]对应{@link CommunityFragment}
     * mFragments[2]对应{@link TransactionFragment}
     */
    private Fragment mFragments[] = new Fragment[4];

    /**
     * 当前所处的tab
     */
    private int mCurrentTab = 0;
    /**
     * {@link #mViewPager}适配器
     */
    private TabFragmentPagerAdapter mPagerAdapter;

    /**
     * /侧滑栏
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注解工具初始化
        ButterKnife.inject(this);

        //隐藏ActionBar
        getSupportActionBar().hide();

        initViews();
        initData();
    }


    /**
     * 初始化数据
     */
    public void initData() {
        //初始化侧滑栏
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        //初始化侧滑栏
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationDrawerFragment.openNavigationDrawer();
            }
        });

    }

    /**
     * 初始化视图
     */
    private void initViews() {
        mPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mCurrentTab);

        //任意一个页面两边应持久化的fragment为2
        mViewPager.setOffscreenPageLimit(2);

        //设置换页监听
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);

        //设置按钮监听
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

        //选中第mCurrentTab个按钮
        ((RadioButton) mRadioGroup.getChildAt(mCurrentTab)).setChecked(true);
    }

    /**
     * implements NavigationDrawerFragment.NavigationDrawerCallbacks
     * <p/>
     * 点击侧滑栏(NavigationDrawerFragment)item时的回掉函数
     *
     * @param position 点击的item序号，从0开始
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Log.e("点击侧滑栏", "点击第" + (position + 1) + "栏");
        Toast.makeText(this, "点击第" + (position + 1) + "栏", Toast.LENGTH_LONG).show();
    }

    /**
     * {@link #mRadioGroup}的按钮监听器
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                //点击教程tab
                case R.id.radio_1:
                    setCurrentTab(TAB_COURSE_INDEX);
                    break;
                //点击分享tab
                case R.id.radio_2:
                    setCurrentTab(TAB_DISPLAY_INDEX);
                    break;
                //点击活动tab
                case R.id.radio_3:
                    setCurrentTab(TAB_COMMUCITY_INDEX);
                    break;
                //点击招募tab
                case R.id.radio_4:
                    setCurrentTab(TAB_TRANSACTION_INDEX);
                    break;
            }
        }
    };

    /**
     * {@link #mViewPager}选中第index个页面
     *
     * @param index
     */
    private void setCurrentTab(int index) {
        if (mViewPager != null && (index >= 0 && index <= 3)) {
            mViewPager.setCurrentItem(index);
            mCurrentTab = index;
        }
    }

    /**
     * {@link #mViewPager}换页适配器
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int status) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            //选中第index个tab按钮，来触发对应tab选中效果(按钮下方会出现指示线)
            ((RadioButton) mRadioGroup.getChildAt(index)).setChecked(true);
        }
    };


    class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int tabIndex) {
            switch (tabIndex) {
                //点击教程tab，要显示教程页面
                case TAB_COURSE_INDEX:
                    if (mFragments[TAB_COURSE_INDEX] == null)
                        mFragments[TAB_COURSE_INDEX] = new CourseFragment();
                    return mFragments[TAB_COURSE_INDEX];
                //点击分享tab，要显示分享页面
                case TAB_DISPLAY_INDEX:
                    if (mFragments[TAB_DISPLAY_INDEX] == null)
                        mFragments[TAB_DISPLAY_INDEX] = new DisplayFragment();
                    return mFragments[TAB_DISPLAY_INDEX];
                //点击活动tab，要显示活动页面
                case TAB_COMMUCITY_INDEX:
                    if (mFragments[TAB_COMMUCITY_INDEX] == null)
                        mFragments[TAB_COMMUCITY_INDEX] = new CommunityFragment();
                    return mFragments[TAB_COMMUCITY_INDEX];
                //点击招募tab，要显示招募页面
                case TAB_TRANSACTION_INDEX:
                    if (mFragments[TAB_TRANSACTION_INDEX] == null)
                        mFragments[TAB_TRANSACTION_INDEX] = new TransactionFragment();
                    return mFragments[TAB_TRANSACTION_INDEX];
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
