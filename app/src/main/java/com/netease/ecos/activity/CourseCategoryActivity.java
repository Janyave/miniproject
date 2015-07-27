package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.netease.ecos.R;
import com.netease.ecos.adapter.CourseListViewAdapter;
import com.netease.ecos.views.AnimationHelper;
import com.netease.ecos.views.FloadingButton;
import com.netease.ecos.views.XListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/27.
 */
public class CourseCategoryActivity extends Activity implements View.OnClickListener,XListView.IXListViewListener{

    @InjectView(R.id.sp_sortType)
    Spinner sp_sortType;
    @InjectView(R.id.iv_search)
    ImageView iv_search;
    @InjectView(R.id.lv_list)
    XListView lv_list;
    @InjectView(R.id.ll_sort)
    LinearLayout ll_sort;
    @InjectView(R.id.btn_floading)
    FloadingButton btn_floading;

    @InjectView(R.id.lly_left_action)
    LinearLayout lly_left_action;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    private ArrayAdapter<String> spAdapter;
    private static final String[] sortType={"A型","B型","O型","AB型","其他"};

    private CourseListViewAdapter courseTypeListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_type);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        sp_sortType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO 排序事件
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        iv_search.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        lly_left_action.setOnClickListener(this);
        btn_floading.setOnClickListener(this);

        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lvIndext = 0; //当前listView显示的首个Item的Index
            String state = "up"; //当前ListView动作状态 up or down
            Boolean isAnim = false; //是否正在动画

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /***当前滑动状态，与记录的lvIndex作比较，发生变化触发动画*/
                String nowstate = state;
                /***当前可见Item的首个Index*/
                int nowIndext = firstVisibleItem;
                /***nowIndex大于lvIndex，ListView下滑*/
                if (nowIndext > lvIndext && !isAnim) {
                    nowstate = "down";
                    if (!TextUtils.equals(nowstate, state)) {
                        btn_floading.disappear(new AnimationHelper.DoAfterAnimation() {
                            @Override
                            public void doAfterAnimation() {
                                isAnim = false;
                            }
                        });
                        isAnim = true;
                    }
                }
                /***nowIndex小于lvIndex，ListView下滑*/
                if (nowIndext < lvIndext && !isAnim) {
                    nowstate = "up";
                    if (!TextUtils.equals(nowstate, state)) {
                        btn_floading.appear(new AnimationHelper.DoAfterAnimation() {
                            @Override
                            public void doAfterAnimation() {
                                isAnim = false;
                            }
                        });
                        isAnim = true;
                    }
                }
                state = nowstate;
                lvIndext = nowIndext;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                //TODO 搜索按钮事件
                break;
            case R.id.ll_left:
                //TODO 左上角类型选择事件
                break;
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.btn_floading:
                        Intent intent = new Intent(CourseCategoryActivity.this, BuildCourseActivity.class);
                        startActivity(intent);
                break;
        }
    }

    private void initData() {
        //设置下拉菜单选项
        spAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sortType);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sortType.setAdapter(spAdapter);

        //设置列表Adapter
        courseTypeListViewAdapter=new CourseListViewAdapter(this);
        lv_list.setAdapter(courseTypeListViewAdapter);
        lv_list.initRefleshTime(this.getClass().getSimpleName());
        lv_list.setPullLoadEnable(true);
        lv_list.setXListViewListener(this);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(CourseCategoryActivity.this, CourseDetailActivity.class));
            }
        });
    }


    @Override
    public void onRefresh() {
        Toast.makeText(CourseCategoryActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
        //1秒后关闭刷新
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_list.stopRefresh();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        Toast.makeText(CourseCategoryActivity.this, "上拉加载", Toast.LENGTH_SHORT).show();

        //1秒后关闭加载
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_list.stopLoadMore();
            }
        }, 1000);
    }
}
