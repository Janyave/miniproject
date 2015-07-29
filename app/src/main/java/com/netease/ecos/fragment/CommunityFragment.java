package com.netease.ecos.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.activity.EventDetailActivity;
import com.netease.ecos.activity.NewActivityActivity;
import com.netease.ecos.adapter.CampaignListViewAdapter;
import com.netease.ecos.adapter.CommunityLocationListViewAdapter;
import com.netease.ecos.views.AnimationHelper;
import com.netease.ecos.views.CommunityListView;
import com.netease.ecos.views.FloadingButton;
import com.netease.ecos.views.XListView;

import java.util.ArrayList;

/**
 * *
 * 社区页面
 */
public class CommunityFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener {

    private View mainView;
    private Button btn_location, btn_categary;
    private FloadingButton btn_floading;
    private XListView lv_campaign;
    private PopupWindow popupWindow;
    private Handler handler;
    private ArrayList<int[]> locationCommunityCountList;

    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        return fragment;
    }

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        locationCommunityCountList = new ArrayList<int[]>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_community, container, false);

        bindView();
        initListener();
        initData();

        return mainView;
    }

    private void bindView() {
        btn_categary = (Button) mainView.findViewById(R.id.btn_category);
        btn_location = (Button) mainView.findViewById(R.id.btn_location);
        lv_campaign = (XListView) mainView.findViewById(R.id.lv_campaign);
        btn_floading = (FloadingButton) mainView.findViewById(R.id.btn_floading_community);
    }

    private void initListener() {
        btn_categary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryPopupWindow(v);
            }
        });
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationPopupWindow(v);
            }
        });

        lv_campaign.setDividerHeight(2);
        lv_campaign.initRefleshTime(this.getClass().getSimpleName());
        lv_campaign.setPullLoadEnable(true);
        lv_campaign.setXListViewListener(this);
        lv_campaign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), EventDetailActivity.class));
            }
        });
        lv_campaign.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                Intent intent = new Intent(CommunityFragment.this.getActivity(), NewActivityActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        lv_campaign.setAdapter(new CampaignListViewAdapter(getActivity()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * showCategoryPopupWindow函数用于弹出分类选择框
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showCategoryPopupWindow(final View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window_category, null);
        // 设置按钮的点击事件
        Button btn_all_category = (Button) contentView.findViewById(R.id.btn_all_category);
        Button btn_same_people_show = (Button) contentView.findViewById(R.id.btn_same_people_show);
        Button btn_movie_festival = (Button) contentView.findViewById(R.id.btn_movie_festival);
        Button btn_official_activities = (Button) contentView.findViewById(R.id.btn_official_activities);
        Button btn_category_live = (Button) contentView.findViewById(R.id.btn_category_live);
        Button btn_category_stage = (Button) contentView.findViewById(R.id.btn_category_stage);
        Button btn_category_match = (Button) contentView.findViewById(R.id.btn_category_match);
        Button btn_theme_only = (Button) contentView.findViewById(R.id.btn_theme_only);
        final Button btn_category_party = (Button) contentView.findViewById(R.id.btn_category_party);

        btn_all_category.setOnClickListener(this);
        btn_same_people_show.setOnClickListener(this);
        btn_movie_festival.setOnClickListener(this);
        btn_official_activities.setOnClickListener(this);
        btn_category_live.setOnClickListener(this);
        btn_category_stage.setOnClickListener(this);
        btn_category_match.setOnClickListener(this);
        btn_theme_only.setOnClickListener(this);
        btn_category_party.setOnClickListener(this);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.white));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }

    /**
     * showLocationPopupWindow函数用于弹出地区选择框
     *
     * @param view
     */
    private void showLocationPopupWindow(final View view) {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                btn_location.setText((CharSequence) msg.obj);
                popupWindow.dismiss();
            }
        };

        locationCommunityCountList.add(locationCommunityCount);     // 五个地区块分别拥有一个int[]数据
        locationCommunityCountList.add(locationCommunityCount);
        locationCommunityCountList.add(locationCommunityCount);
        locationCommunityCountList.add(locationCommunityCount);
        locationCommunityCountList.add(locationCommunityCount);

        // popup_window_location布局文件包含一个自定义的ListView
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window_location, null);

        CommunityListView lv_Location = (CommunityListView) contentView.findViewById(R.id.lv_community_location);
        lv_Location.setAdapter(new CommunityLocationListViewAdapter(getActivity(), handler, locationCommunityCountList));

        lv_Location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("onItemClick" + "--" + position);
            }
        });

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.white));
        popupWindow.showAsDropDown(view);
    }

    @Override
    public void onClick(View v) {
        CharSequence string = ((Button) v.findViewById(v.getId())).getText();
        System.out.println(string);
        btn_categary.setText(string);
        popupWindow.dismiss();
        /**
         * 根据点击的按钮，刷新listView的数据
         */
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
        //1秒后关闭刷新
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_campaign.stopRefresh();
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
                lv_campaign.stopLoadMore();
            }
        }, 1000);
    }

    private int[] locationCommunityCount = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
}
