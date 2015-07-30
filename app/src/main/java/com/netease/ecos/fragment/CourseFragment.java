package com.netease.ecos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.activity.CourseCategoryActivity;
import com.netease.ecos.activity.CourseDetailActivity;
import com.netease.ecos.activity.CourseTypeActivity;
import com.netease.ecos.adapter.CourseListViewAdapter;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.course.CourseListRequest;
import com.netease.ecos.request.course.GetBannerRequest;
import com.netease.ecos.views.AnimationHelper;
import com.netease.ecos.views.Banner;
import com.netease.ecos.views.ExtensibleListView;
import com.netease.ecos.views.FloadingButton;
import com.netease.ecos.views.ViewScrollListener;

import java.util.List;

/**
 * 教程页面
 */
public class CourseFragment extends Fragment implements View.OnClickListener {
    private View mainView;
    private Banner banner;
    private ImageView tv_type_1;
    private ImageView tv_type_2;
    private ImageView tv_type_3;
    private ImageView tv_type_4;
    private ImageView tv_type_5;
    private ImageView tv_type_6;
    private ImageView tv_type_7;
    private ImageView tv_type_8;
    private FloadingButton btn_floading;
    private ExtensibleListView lv_course;
    private ScrollView sv;

    private CourseListViewAdapter courseListViewAdapter;

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

        bindView();
        initListener();
        initData();

        return mainView;
    }

    private void bindView() {
        banner = (Banner) mainView.findViewById(R.id.banner);
        btn_floading = (FloadingButton) mainView.findViewById(R.id.btn_floading);
        lv_course = (ExtensibleListView) mainView.findViewById(R.id.lv_course);
        tv_type_1 = (ImageView) mainView.findViewById(R.id.tv_type_1);
        tv_type_2 = (ImageView) mainView.findViewById(R.id.tv_type_2);
        tv_type_3 = (ImageView) mainView.findViewById(R.id.tv_type_3);
        tv_type_4 = (ImageView) mainView.findViewById(R.id.tv_type_4);
        tv_type_5 = (ImageView) mainView.findViewById(R.id.tv_type_5);
        tv_type_6 = (ImageView) mainView.findViewById(R.id.tv_type_6);
        tv_type_7 = (ImageView) mainView.findViewById(R.id.tv_type_7);
        tv_type_8 = (ImageView) mainView.findViewById(R.id.tv_type_8);
        sv=(ScrollView)mainView.findViewById(R.id.sv);
    }


    private void initListener() {
        lv_course.setDividerHeight(0);
        lv_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), CourseDetailActivity.class));

            }
        });

        sv.setOnTouchListener(new ViewScrollListener(new ViewScrollListener.IOnMotionEvent() {
            Boolean isAnim = false; //是否正在动画
            @Override
            public void doInDown() {
            }

            @Override
            public void doInUp() {
            }

            @Override
            public void doInChangeToDown() {
                if (!isAnim){
                    btn_floading.disappear(new AnimationHelper.DoAfterAnimation() {
                        @Override
                        public void doAfterAnimation() {
                            isAnim = false;
                        }
                    });
                    isAnim = true;
                }

            }

            @Override
            public void doInChangeToUp() {
                if (!isAnim){
                    btn_floading.appear(new AnimationHelper.DoAfterAnimation() {
                        @Override
                        public void doAfterAnimation() {
                            isAnim = false;
                        }
                    });
                    isAnim = true;
                }
            }
        }));

        btn_floading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseFragment.this.getActivity(), CourseTypeActivity.class);
                startActivity(intent);
            }
        });

        tv_type_1.setOnClickListener(this);
        tv_type_2.setOnClickListener(this);
        tv_type_3.setOnClickListener(this);
        tv_type_4.setOnClickListener(this);
        tv_type_5.setOnClickListener(this);
        tv_type_6.setOnClickListener(this);
        tv_type_7.setOnClickListener(this);
        tv_type_8.setOnClickListener(this);

    }

    private void initData() {
//        lv_course.setAdapter(new CourseListViewAdapter(getActivity()));

//        List<String> URLList = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            URLList.add("http://img5.duitang.com/uploads/item/201403/07/20140307100224_trTBU.jpeg");
//        }
//        banner.setURLList(URLList);

        /**获取banner信息**/
        GetBannerRequest requestBanner = new GetBannerRequest();
        requestBanner.request(new GetBannerResponse());

        CourseListRequest requestCourse = new CourseListRequest();
        requestCourse.request(new GetCourseResponse(), CourseListRequest.Type.推荐, null, null, null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //TODO 增加点击信息
        }
        startActivity(new Intent(getActivity(), CourseCategoryActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
    }

    class GetBannerResponse extends BaseResponceImpl implements GetBannerRequest.IGetBannerResponse {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void success(List<String> bannerList) {
            banner.setURLList(bannerList);
        }

    }

    class GetCourseResponse extends BaseResponceImpl implements CourseListRequest.ICourseListResponse {

        @Override
        public void success(List<Course> courseList) {
            courseListViewAdapter = new CourseListViewAdapter(getActivity(), courseList);
            lv_course.setAdapter(courseListViewAdapter);
        }

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
}
