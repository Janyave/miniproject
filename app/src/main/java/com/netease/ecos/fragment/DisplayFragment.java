package com.netease.ecos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.activity.CourseCategoryActivity;
import com.netease.ecos.activity.PhotoAlbumActivity;
import com.netease.ecos.activity.SearchActivity;
import com.netease.ecos.adapter.DisplayListViewAdapter;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.share.ShareListRequest;
import com.netease.ecos.views.AnimationHelper;
import com.netease.ecos.views.FloadingButton;
import com.netease.ecos.views.ListViewListener;
import com.netease.ecos.views.XListView;

import java.util.List;


public class DisplayFragment extends Fragment implements XListView.IXListViewListener, View.OnClickListener {
    private static final String TAG = "Ecos---DisplayF";

    private View mainView;
    private FloadingButton btn_floading;
    private XListView lv_course;

    private TextView tv_all;
    private TextView tv_recommend;
    private TextView tv_new;
    private TextView tv_attention;
    private TextView tv_search;

    private DisplayListViewAdapter displayListViewAdapter;

    //for request
    private ShareListRequest shareListRequest;
    private GetShareListResponse getShareListResponse;

    //record share type including recommend, latest, favor.
    private ShareListRequest.ShareType shareType;
    private String searchWord;


    public static DisplayFragment newInstance() {
        DisplayFragment fragment = new DisplayFragment();
        return fragment;
    }

    public DisplayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_display, container, false);
        bindView();
        initListener();
        initData();
        return mainView;
    }

    private void bindView() {
        tv_all = (TextView) mainView.findViewById(R.id.tv_all);
        tv_recommend = (TextView) mainView.findViewById(R.id.tv_recommend);
        tv_new = (TextView) mainView.findViewById(R.id.tv_new);
        tv_attention = (TextView) mainView.findViewById(R.id.tv_attention);
        tv_search = (TextView) mainView.findViewById(R.id.tv_search);

        lv_course = (XListView) mainView.findViewById(R.id.lv_course);
        lv_course.setDividerHeight(0);
        btn_floading = (FloadingButton) mainView.findViewById(R.id.btn_floading);
    }


    private void initData() {
        searchWord = "";
        shareListRequest = new ShareListRequest();
        getShareListResponse = new GetShareListResponse();
        shareType = ShareListRequest.ShareType.所有;
        shareListRequest.request(getShareListResponse, shareType, searchWord, 0);
    }

    private void initListener() {
        tv_all.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_attention.setOnClickListener(this);
        tv_recommend.setOnClickListener(this);
        tv_new.setOnClickListener(this);

        btn_floading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhotoAlbumActivity.class);
                startActivity(intent);
            }
        });

        lv_course.setDividerHeight(2);
        lv_course.initRefleshTime(this.getClass().getSimpleName());
        lv_course.setPullLoadEnable(true);
        lv_course.setPullLoadEnable(true);
        lv_course.setXListViewListener(this);
        lv_course.setOnTouchListener(new ListViewListener(new ListViewListener.IOnMotionEvent() {
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
        /*lv_course.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lvIndext = 0; //当前listView显示的首个Item的Index
            String state = "up"; //当前ListView动作状态 up or down
            Boolean isAnim = false; //是否正在动画

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                *//***当前滑动状态，与记录的lvIndex作比较，发生变化触发动画*//*
                String nowstate = state;
                *//***当前可见Item的首个Index*//*
                int nowIndext = firstVisibleItem;
                *//***nowIndex大于lvIndex，ListView下滑*//*
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
                *//***nowIndex小于lvIndex，ListView下滑*//*
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
        });*/
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_all:
                //the text color
                setUnChecked();
                tv_all.setTextColor(getResources().getColor(R.color.text_red));
                shareType = ShareListRequest.ShareType.所有;
                //send the request
                shareListRequest.request(getShareListResponse, shareType, searchWord, 0);
                break;
            case R.id.tv_recommend:
                //the text color
                setUnChecked();
                tv_recommend.setTextColor(getResources().getColor(R.color.text_red));
                shareType = ShareListRequest.ShareType.推荐;
                //send the request
                shareListRequest.request(getShareListResponse, shareType, searchWord, 0);
                break;
            case R.id.tv_attention:
                //the text color
                setUnChecked();
                tv_attention.setTextColor(getResources().getColor(R.color.text_red));
                shareType = ShareListRequest.ShareType.关注;
                //send the request
                shareListRequest.request(getShareListResponse, shareType, searchWord, 0);

                break;
            case R.id.tv_new:
                //the text color
                setUnChecked();
                tv_new.setTextColor(getResources().getColor(R.color.text_red));
                shareType = ShareListRequest.ShareType.新人;
                //send the request
                shareListRequest.request(getShareListResponse, shareType, searchWord, 0);
                break;
        }
    }

    private void setUnChecked() {
        int color = getResources().getColor(R.color.text_gray);
        tv_all.setTextColor(color);
        tv_new.setTextColor(color);
        tv_search.setTextColor(color);
        tv_recommend.setTextColor(color);
        tv_attention.setTextColor(color);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CourseCategoryActivity.RequestCodeForSearch && resultCode == CourseCategoryActivity.ResultCodeForSearch) {
            searchWord = data.getExtras().getString(SearchActivity.SearchWord);
            shareListRequest.request(getShareListResponse, shareType, searchWord, 0);
        }
    }

    class GetShareListResponse extends BaseResponceImpl implements ShareListRequest.IShareListResponse {

        @Override
        public void success(List<Share> shareList) {
            Log.d(TAG, "sharelist.size:" + shareList.size());
            displayListViewAdapter = new DisplayListViewAdapter(getActivity(), shareList);
            lv_course.setAdapter(displayListViewAdapter);
        }

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
}
