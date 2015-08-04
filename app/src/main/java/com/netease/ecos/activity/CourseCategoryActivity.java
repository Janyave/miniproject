package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.CourseListViewAdapter;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.course.CourseListRequest;
import com.netease.ecos.views.AnimationHelper;
import com.netease.ecos.views.FloadingButton;
import com.netease.ecos.views.ListViewListener;
import com.netease.ecos.views.XListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/27.
 */
public class CourseCategoryActivity extends Activity implements View.OnClickListener, XListView.IXListViewListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "Ecos---CourseCategory";
    public static final String CourseCategory = "CourseCategory";
    private Course.CourseType courseType;
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
    @InjectView(R.id.tv_left)
    TextView tv_left;

    private ArrayAdapter<CourseListRequest.SortRule> spAdapter;
    private static final CourseListRequest.SortRule[] SORT_RULES = {CourseListRequest.SortRule.时间, CourseListRequest.SortRule.被关注数, CourseListRequest.SortRule.被点赞数};


    private CourseListViewAdapter courseTypeListViewAdapter;
    //record the search keyword.
    private String searchWords = "";

    public static final int RequestCodeForSearch = 1;
    public static final int ResultCodeForSearch = 2;

    //for request.
    private CourseListRequest request;
    private CourseListResponse courseListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_type);
        ButterKnife.inject(this);
        initListener();
        initData();
    }

    private void initData() {
        //get the extras from intent
        courseType = Course.CourseType.getCourseType(getIntent().getExtras().getString(CourseCategory));
        tv_left.setText(courseType.name());
        //设置下拉菜单选项
        spAdapter = new ArrayAdapter<CourseListRequest.SortRule>(this, R.layout.text_spinner_course_type, SORT_RULES);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sortType.setAdapter(spAdapter);
        sp_sortType.setOnItemSelectedListener(this);

        //设置列表Adapter
        lv_list.initRefleshTime(this.getClass().getSimpleName());
        lv_list.setPullLoadEnable(true);
        lv_list.setXListViewListener(this);

        //获取course信息
        request = new CourseListRequest();
        courseListResponse = new CourseListResponse();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_search:
                intent = new Intent(CourseCategoryActivity.this, SearchActivity.class);
                startActivityForResult(intent, RequestCodeForSearch);
                break;
            case R.id.ll_left:
                //TODO 左上角类型选择事件
                break;
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.btn_floading:
                intent = new Intent(CourseCategoryActivity.this, BuildCourseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(BuildCourseActivity.CourseType, courseType.getBelongs());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        request.request(courseListResponse, CourseListRequest.Type.筛选,
                courseType, searchWords, SORT_RULES[position], 0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeForSearch && resultCode == ResultCodeForSearch) {
            searchWords = data.getExtras().getString(SearchActivity.SearchWord);
            request.request(courseListResponse, CourseListRequest.Type.筛选,
                    courseType, searchWords, SORT_RULES[sp_sortType.getSelectedItemPosition()], 0);
        }
    }

    class CourseListResponse extends BaseResponceImpl implements CourseListRequest.ICourseListResponse {

        @Override
        public void success(List<Course> courseList) {
            Log.d(TAG, "CourseListResponse.success()");
            if (courseList.size() == 0) {
                Toast.makeText(CourseCategoryActivity.this, getResources().getString(R.string.noCourse), Toast.LENGTH_SHORT).show();
                return;
            }
            courseTypeListViewAdapter = new CourseListViewAdapter(CourseCategoryActivity.this, courseList);
            lv_list.setAdapter(courseTypeListViewAdapter);
        }

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
        }
    }

    private void initListener() {
        iv_search.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        lly_left_action.setOnClickListener(this);
        btn_floading.setOnClickListener(this);
        lv_list.setOnTouchListener(new ListViewListener(new ListViewListener.IOnMotionEvent() {
            @Override
            public void doInDown() {
                if (btn_floading.isAppear()) {
                    btn_floading.disappear(new AnimationHelper.DoAfterAnimation() {
                        @Override
                        public void doAfterAnimation() {
                            btn_floading.setIsDisappear();
                            btn_floading.setIsAnim(false);
                        }
                    });
                }
            }

            @Override
            public void doInUp() {
                if (btn_floading.isDisappear()) {
                    btn_floading.appear(new AnimationHelper.DoAfterAnimation() {
                        @Override
                        public void doAfterAnimation() {
                            btn_floading.setIsAppear();
                            btn_floading.setIsAnim(false);
                        }
                    });
                }
            }

            @Override
            public void doInChangeToDown() {
                btn_floading.disappear(new AnimationHelper.DoAfterAnimation() {
                    @Override
                    public void doAfterAnimation() {
                        btn_floading.setIsDisappear();
                        btn_floading.setIsAnim(false);
                    }
                });
            }

            @Override
            public void doInChangeToUp() {
                btn_floading.appear(new AnimationHelper.DoAfterAnimation() {
                    @Override
                    public void doAfterAnimation() {
                        btn_floading.setIsAppear();
                        btn_floading.setIsAnim(false);
                    }
                });
            }
        }));
//        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int lvIndext = 0; //当前listView显示的首个Item的Index
//            String state = "up"; //当前ListView动作状态 up or down
//            Boolean isAnim = false; //是否正在动画
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                /***当前滑动状态，与记录的lvIndex作比较，发生变化触发动画*/
//                String nowstate = state;
//                /***当前可见Item的首个Index*/
//                int nowIndext = firstVisibleItem;
//                /***nowIndex大于lvIndex，ListView下滑*/
//                if (nowIndext > lvIndext && !isAnim) {
//                    nowstate = "down";
//                    if (!TextUtils.equals(nowstate, state)) {
//                        btn_floading.disappear(new AnimationHelper.DoAfterAnimation() {
//                            @Override
//                            public void doAfterAnimation() {
//                                isAnim = false;
//                            }
//                        });
//                        isAnim = true;
//                    }
//                }
//                /***nowIndex小于lvIndex，ListView下滑*/
//                if (nowIndext < lvIndext && !isAnim) {
//                    nowstate = "up";
//                    if (!TextUtils.equals(nowstate, state)) {
//                        btn_floading.appear(new AnimationHelper.DoAfterAnimation() {
//                            @Override
//                            public void doAfterAnimation() {
//                                isAnim = false;
//                            }
//                        });
//                        isAnim = true;
//                    }
//                }
//                state = nowstate;
//                lvIndext = nowIndext;
//            }
//        });
    }
}
