package com.netease.ecos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.activity.BuildCourseActivity;
import com.netease.ecos.adapter.CourseListViewAdapter;
import com.netease.ecos.views.AnimationHelper;
import com.netease.ecos.views.FloadingButton;
import com.netease.ecos.views.XListView;

/**
 * 教程页面
 */
public class CourseFragment extends Fragment implements XListView.IXListViewListener {

    private View mainView;
    private FloadingButton btn_floading;
    private XListView lv_course;

    public static CourseFragment newInstance() {
        CourseFragment fragment = new CourseFragment();
        return fragment;
    }

    public CourseFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mainView = inflater.inflate(R.layout.fragment_course, container, false);

        btn_floading = (FloadingButton) mainView.findViewById(R.id.btn_floading);

        lv_course = (XListView) mainView.findViewById(R.id.lv_course);
        lv_course.setAdapter(new CourseListViewAdapter(getActivity()));
        lv_course.setDividerHeight(2);
        lv_course.initRefleshTime(this.getClass().getSimpleName());

        lv_course.setPullLoadEnable(true);
        lv_course.setXListViewListener(this);


        lv_course.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        btn_floading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseFragment.this.getActivity(), BuildCourseActivity.class);
                startActivity(intent);
            }
        });

        return mainView;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
        //1秒后关闭刷新
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_course.stopRefresh();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        Toast.makeText(getActivity(), "上拉加载", Toast.LENGTH_SHORT).show();

        //1秒后关闭加载
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_course.stopLoadMore();
            }
        }, 1000);
    }
}
