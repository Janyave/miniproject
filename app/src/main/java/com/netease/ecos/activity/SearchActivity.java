package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.CourseListViewAdapter;
import com.netease.ecos.adapter.DisplayListViewAdapter;
import com.netease.ecos.adapter.SearchHistoryAdapter;
import com.netease.ecos.fragment.DisplayFragment;
import com.netease.ecos.model.Course;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.course.CourseListRequest;
import com.netease.ecos.request.share.ShareListRequest;
import com.netease.ecos.views.PopupHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/27.
 */
public class SearchActivity extends Activity {

    private static final String TAG = "Ecos---Search";
    public static final String SearchWord = "SearchWord";

    public static final String SEARCH_TYPE = "type";

    public static final int TYPE_COURSE = 2;
    public static final int TYPE_SHARE = 3;

    private int TYPE = TYPE_COURSE; //default course

    @InjectView(R.id.et_search)
    EditText et_search;
    @InjectView(R.id.tv_confirm)
    TextView tv_confirm;
    @InjectView(R.id.lv_searchHistory)
    ListView lv_searchHistory;  //历史记录
    @InjectView(R.id.lv_searchList)
    ListView lv_searchList;   //搜索结果

    @InjectView(R.id.ll_searchType)
    LinearLayout ll_searchType;
    @InjectView(R.id.tv_searchType)
    TextView tv_searchType;
    @InjectView(R.id.iv_searchType)
    ImageView iv_searchType;

    PopupWindow courseTypePopupWindow;
    PopupWindow shareSortTypePopupWindow;

    private SearchHistoryAdapter searchHistoryAdapter;

    //result adapter
    private CourseListViewAdapter courseListViewAdapter;
    private DisplayListViewAdapter displayListViewAdapter;
    //for request
    private ShareListRequest shareListRequest;
    private GetShareListResponse getShareListResponse;

    //for request
    private CourseListRequest courseListRequest;
    private CourseListResponse courseListResponse;

    //to record which item is selected
    private int selectPosition = 0;
    //to record the search key word
    private String searchWord;

    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        TYPE = bundle.getInt(SEARCH_TYPE);
        if (TYPE == TYPE_COURSE) {
            courseListRequest = new CourseListRequest();
            courseListResponse = new CourseListResponse();
        } else {
            shareListRequest = new ShareListRequest();
            getShareListResponse = new GetShareListResponse();
        }
        searchHistoryAdapter = new SearchHistoryAdapter(this);
        lv_searchHistory.setAdapter(searchHistoryAdapter);
    }

    private void initView() {
        if (TYPE == TYPE_COURSE)
            courseTypePopupWindow = PopupHelper.newSixTypePopupWindow(SearchActivity.this);
        else
            shareSortTypePopupWindow = PopupHelper.newShareSortTypePopupWindow(SearchActivity.this);
    }

    private void initListener() {
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWord = et_search.getText().toString();
                if (searchWord.equals("")) {
                    Toast.makeText(SearchActivity.this, getResources().getString(R.string.noContent), Toast.LENGTH_SHORT).show();
                    return;
                }
                lv_searchHistory.setVisibility(View.GONE);
                if (TYPE == TYPE_COURSE) {
                    courseListRequest.request(courseListResponse, CourseListRequest.Type.筛选, CourseCategoryActivity.courseTypes[selectPosition], searchWord, CourseListRequest.SortRule.时间, 0);
                } else {
                    shareListRequest.request(getShareListResponse, DisplayFragment.shareTypes[selectPosition], searchWord, 0);
                }
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //TODO 软键盘确定事件 搜索事件
                    //确定后隐藏lv_historyList 显示lv_searchList
                    return true;
                }
                return false;
            }
        });

        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_searchHistory.setVisibility(View.VISIBLE);
                lv_searchList.setVisibility(View.GONE);
            }
        });
        lv_searchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(SearchWord, ((SearchHistoryAdapter.SearchHistoryViewHolder) view.getTag()).tv_search.getText().toString());
                getIntent().putExtras(bundle);
                setResult(CourseCategoryActivity.ResultCodeForSearch, getIntent());
                finish();
            }
        });

        ll_searchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TYPE == TYPE_COURSE) {
                    if (courseTypePopupWindow.isShowing()) {
                        courseTypePopupWindow.dismiss();
                    } else {
                        PopupHelper.showSixTypePopupWindow(courseTypePopupWindow, SearchActivity.this, v, new PopupHelper.IPopupListner() {
                            @Override
                            public void clickListner(int type, View v, PopupWindow popupWindow) {
                                tv_searchType.setText(((RadioButton) v).getText().toString());
                                selectPosition = type;
                            }
                        });
                    }
                }
                if (TYPE == TYPE_SHARE) {
                    if (shareSortTypePopupWindow.isShowing()) {
                        shareSortTypePopupWindow.dismiss();
                    } else {
                        PopupHelper.showSixTypePopupWindow(shareSortTypePopupWindow, SearchActivity.this, v, new PopupHelper.IPopupListner() {
                            @Override
                            public void clickListner(int type, View v, PopupWindow popupWindow) {
                                tv_searchType.setText(((RadioButton) v).getText().toString());
                                selectPosition = type;
                            }
                        });
                    }
                }
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                inputMethodManager.showSoftInput(et_search, InputMethodManager.SHOW_FORCED);
            }
        });
    }

    class CourseListResponse extends BaseResponceImpl implements CourseListRequest.ICourseListResponse {

        @Override
        public void success(List<Course> courseList) {
            Log.d(TAG, "CourseListResponse.success()");
            if (courseList.size() == 0) {
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.noCourse), Toast.LENGTH_SHORT).show();
            }
            courseListViewAdapter = new CourseListViewAdapter(SearchActivity.this, courseList);
            lv_searchList.setVisibility(View.VISIBLE);
            lv_searchList.setAdapter(courseListViewAdapter);
            pageIndex = 0;
        }

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
        }
    }

    class GetShareListResponse extends BaseResponceImpl implements ShareListRequest.IShareListResponse {

        @Override
        public void success(List<Share> shareList) {
            if (shareList.size() == 0)
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.noShare), Toast.LENGTH_SHORT).show();
            displayListViewAdapter = new DisplayListViewAdapter(SearchActivity.this, shareList);
            lv_searchList.setVisibility(View.VISIBLE);
            lv_searchList.setAdapter(displayListViewAdapter);
            pageIndex = 0;
        }

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
        }
    }

}
