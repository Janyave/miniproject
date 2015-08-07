package com.netease.ecos.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.activity.ActivityDetailActivity;
import com.netease.ecos.activity.NewActivityActivity;
import com.netease.ecos.adapter.CampaignListViewAdapter;
import com.netease.ecos.adapter.CommunityLocationListViewAdapter;
import com.netease.ecos.database.ProvinceDBService;
import com.netease.ecos.interfaces.CommunityCallBack;
import com.netease.ecos.model.ActivityModel;
import com.netease.ecos.request.activity.ActivityListRequest;
import com.netease.ecos.utils.Util;
import com.netease.ecos.views.AnimationHelper;
import com.netease.ecos.views.CommunityListView;
import com.netease.ecos.views.FloadingButton;
import com.netease.ecos.views.ListViewListener;
import com.netease.ecos.views.XListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * *
 * 社区页面
 */
public class CommunityFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener, CommunityCallBack {

    private static final String TAG = "Ecos---CommunityF";

    private View mainView;
    private Button btn_location, btn_categary;
    private FloadingButton btn_floading;
    private XListView lv_campaign;
    private PopupWindow popupWindowLocation, popupWindowCategory;
    private Handler handler;
    private ArrayList<int[]> locationCommunityCountList;
    private ImageView iv_show_flag_location, iv_show_flag_category;
    private ActivityListRequest request;
    private CampaignListViewAdapter campaignListViewAdapter;

    private String recentLocation, recentCategory;
    private int pageIndex = 0;

    private List<ActivityModel> activityList;

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
        recentLocation = "浙江";
        recentCategory = "全部分类";
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
        iv_show_flag_location = (ImageView) mainView.findViewById(R.id.iv_show_flag_location);
        iv_show_flag_category = (ImageView) mainView.findViewById(R.id.iv_show_flag_category);
    }

    private void initListener() {
        btn_categary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindowCategory == null) {
                    showCategoryPopupWindow(v);
                } else if (popupWindowCategory.isShowing()) {
                    popupWindowCategory.dismiss();
                    iv_show_flag_category.setImageResource(R.drawable.ic_unpress_next_step);
                } else {
                    showCategoryPopupWindow(v);
                }
                if (popupWindowLocation != null)
                    if (popupWindowLocation.isShowing()) {
                        popupWindowLocation.dismiss();
                        iv_show_flag_location.setImageResource(R.drawable.ic_unpress_next_step);
                    }
            }
        });
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindowLocation == null) {
                    showLocationPopupWindow(v);
                } else if (popupWindowLocation.isShowing()) {
                    popupWindowLocation.dismiss();
                    iv_show_flag_location.setImageResource(R.drawable.ic_unpress_next_step);
                } else {
                    showLocationPopupWindow(v);
                }
                if (popupWindowCategory != null)
                    if (popupWindowCategory.isShowing()) {
                        popupWindowCategory.dismiss();
                        iv_show_flag_category.setImageResource(R.drawable.ic_unpress_next_step);
                    }
            }
        });

        lv_campaign.setDividerHeight(2);
        lv_campaign.initRefleshTime(this.getClass().getSimpleName());
        lv_campaign.setPullLoadEnable(true);
        lv_campaign.setXListViewListener(this);
        lv_campaign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popupWindowLocation != null)
                    if (popupWindowLocation.isShowing()) {
                        popupWindowLocation.dismiss();
                    }
                if (popupWindowCategory != null)
                    if (popupWindowCategory.isShowing()) {
                        popupWindowCategory.dismiss();
                    }
                Intent intent = new Intent(getActivity(), ActivityDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ActivityDetailActivity.ActivityID, activityList.get(position - 1).activityId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lv_campaign.setOnTouchListener(new ListViewListener(new ListViewListener.IOnMotionEvent() {
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

        btn_floading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick");
                Intent intent = new Intent(CommunityFragment.this.getActivity(), NewActivityActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        request = new ActivityListRequest();
        final String strCategory = recentCategory;
        final String strLocation = recentLocation;

        String cityCode = ProvinceDBService.getProvinceDBServiceInstance(getActivity()).getProvinceId(strLocation);

        request.request(new ActivityListRequest.IActivityListResponse() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void doAfterFailedResponse(String message) {
                Toast.makeText(getActivity(), "error happens:" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void responseNoGrant() {

            }

            @Override
            public void success(List<ActivityModel> activityList) {
                CommunityFragment.this.activityList = activityList;
                Log.d("test", "activityList size:" + activityList.size());
                if (campaignListViewAdapter == null) {
                    campaignListViewAdapter = new CampaignListViewAdapter(getActivity(), activityList);
                    lv_campaign.setAdapter(campaignListViewAdapter);
                } else if (strCategory.equals(recentCategory) && strLocation.equals(recentLocation)) {  // 判断是否需要更新数据
                    campaignListViewAdapter.setActivityList(activityList);
                    campaignListViewAdapter.notifyDataSetChanged();
                }
            }
            // TODO 省份ID与省份名称未定
            // TODO 全部分类项需要添加
        }, cityCode, strCategory.equals("全部分类") ? null : Enum.valueOf(ActivityModel.ActivityType.class, strCategory), 0);
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

        iv_show_flag_category.setImageResource(R.drawable.ic_unpress_last_step);
        iv_show_flag_location.setImageResource(R.drawable.ic_unpress_next_step);
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

        popupWindowCategory = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindowCategory.setTouchable(true);
        setPopupWindowTouchModal(popupWindowCategory, false);// 使得popupWindow在显示的时候，popupWindow外部的控件也能够获得焦点
        popupWindowCategory.setTouchInterceptor(new View.OnTouchListener() {

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
        popupWindowCategory.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.white));

        // 设置好参数之后再show
        popupWindowCategory.showAsDropDown(view);
    }

    /**
     * showLocationPopupWindow函数用于弹出地区选择框
     *
     * @param view
     */
    private void showLocationPopupWindow(final View view) {

        iv_show_flag_location.setImageResource(R.drawable.ic_unpress_last_step);
        iv_show_flag_category.setImageResource(R.drawable.ic_unpress_next_step);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                final String strLocation = (String) msg.obj;
                final String strCategory = recentCategory;  // 记录当前状态下的地点和分类

                String cityCode = ProvinceDBService.getProvinceDBServiceInstance(getActivity()).getProvinceId(strLocation);
                recentLocation = strLocation;

                request.request(new ActivityListRequest.IActivityListResponse() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }

                    @Override
                    public void doAfterFailedResponse(String message) {
                        Toast.makeText(getActivity(), "error happens:" + message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void responseNoGrant() {

                    }

                    @Override
                    public void success(List<ActivityModel> activityList) {
                        if (campaignListViewAdapter == null) {
                            campaignListViewAdapter = new CampaignListViewAdapter(getActivity(), activityList);
                            lv_campaign.setAdapter(campaignListViewAdapter);
                        } else if (strCategory.equals(recentCategory) && strLocation.equals(recentLocation)) {  // 判断是否需要更新数据
                            campaignListViewAdapter.setActivityList(activityList);
                            campaignListViewAdapter.notifyDataSetChanged();
                            lv_campaign.smoothScrollToPosition(0);  // ListView回到顶部
                            pageIndex = 0;
                        }
                    }
//                }, strLocation, Enum.valueOf(ActivityModel.ActivityType.class, strCategory), 0);
                }, cityCode, strCategory.equals("全部分类") ? null : Enum.valueOf(ActivityModel.ActivityType.class, strCategory), 0);

                btn_location.setText((CharSequence) msg.obj);
                popupWindowLocation.dismiss();
                iv_show_flag_location.setImageResource(R.drawable.ic_unpress_next_step);
                int length = ((CharSequence) msg.obj).length();
                Util.setMargins(iv_show_flag_location, Util.dip2px(getActivity(), 120 + (length - 2) * 8), Util.dip2px(getActivity(), 15), 0, 0);
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

        popupWindowLocation = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindowLocation.setTouchable(true);
        setPopupWindowTouchModal(popupWindowLocation, false);   // 使得popupWindow在显示的时候，popupWindow外部的控件也能够获得焦点
        popupWindowLocation.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindowLocation.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.white));
        popupWindowLocation.showAsDropDown(view);
    }

    @Override
    public void onClick(View v) {
        final String strCategory = ((Button) v.findViewById(v.getId())).getText().toString();
        final String strLocation = recentLocation;
        recentCategory = strCategory;

        String cityCode = ProvinceDBService.getProvinceDBServiceInstance(getActivity()).getProvinceId(strLocation);
        request.request(new ActivityListRequest.IActivityListResponse() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void doAfterFailedResponse(String message) {
                Toast.makeText(getActivity(), "error happens:" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void responseNoGrant() {

            }

            @Override
            public void success(List<ActivityModel> activityList) {
                if (campaignListViewAdapter == null) {
                    campaignListViewAdapter = new CampaignListViewAdapter(getActivity(), activityList);
                    lv_campaign.setAdapter(campaignListViewAdapter);
                } else if (strCategory.equals(recentCategory) && strLocation.equals(recentLocation)) {
                    campaignListViewAdapter.setActivityList(activityList);
                    campaignListViewAdapter.notifyDataSetChanged();
                    lv_campaign.smoothScrollToPosition(0);  // ListView回到顶部
                    pageIndex = 0;
                }
            }
//        }, strLocation, Enum.valueOf(ActivityModel.ActivityType.class, strCategory), 0);
        }, cityCode, strCategory.equals("全部分类") ? null : Enum.valueOf(ActivityModel.ActivityType.class, strCategory), 0);

        btn_categary.setText(strCategory);
        int length = 0;
        if (strCategory.equals("LIVE"))
            length = 2;
        else if (strCategory.equals("主题ONLY"))
            length = 4;
        else
            length = strCategory.length();
        Util.setMargins(iv_show_flag_category, 0, Util.dip2px(getActivity(), 15), Util.dip2px(getActivity(), 60 - (length - 2) * 8), 0);
        popupWindowCategory.dismiss();
        iv_show_flag_category.setImageResource(R.drawable.ic_unpress_next_step);
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

        final String strCategory = recentCategory;
        final String strLocation = recentLocation;

        String cityCode = ProvinceDBService.getProvinceDBServiceInstance(getActivity()).getProvinceId(strLocation);

        request.request(new ActivityListRequest.IActivityListResponse() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void doAfterFailedResponse(String message) {
                Toast.makeText(getActivity(), "error happens:" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void responseNoGrant() {

            }

            @Override
            public void success(List<ActivityModel> activityList) {
                if (campaignListViewAdapter == null) {
                    campaignListViewAdapter = new CampaignListViewAdapter(getActivity(), activityList);
                    lv_campaign.setAdapter(campaignListViewAdapter);
                } else if (strCategory.equals(recentCategory) && strLocation.equals(recentLocation)) {
                    campaignListViewAdapter.setActivityList(activityList);
                    campaignListViewAdapter.notifyDataSetChanged();
                    lv_campaign.smoothScrollToPosition(0);  // ListView回到顶部
                    pageIndex = 0;
                }
            }
        }, cityCode, strCategory.equals("全部分类") ? null : Enum.valueOf(ActivityModel.ActivityType.class, strCategory), 0);
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

        final String strCategory = recentCategory;
        final String strLocation = recentLocation;

        String cityCode = ProvinceDBService.getProvinceDBServiceInstance(getActivity()).getProvinceId(strLocation);

        if (campaignListViewAdapter == null)
            pageIndex = 0;
        pageIndex++;
        request.request(new ActivityListRequest.IActivityListResponse() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void doAfterFailedResponse(String message) {
                Toast.makeText(getActivity(), "error happens:" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void responseNoGrant() {

            }

            @Override
            public void success(List<ActivityModel> activityList) {
                if (campaignListViewAdapter == null) {
                    campaignListViewAdapter = new CampaignListViewAdapter(getActivity(), activityList);
                    lv_campaign.setAdapter(campaignListViewAdapter);
                } else if (strCategory.equals(recentCategory) && strLocation.equals(recentLocation)) {
                    if (activityList.size() == 0) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.nothingLeft), Toast.LENGTH_SHORT).show();
                        pageIndex--;
                    } else {
                        campaignListViewAdapter.getActivityList().addAll(activityList); // 添加ListView的内容
                        campaignListViewAdapter.notifyDataSetChanged();
                    }
                }
            }
//        }, strLocation, Enum.valueOf(ActivityModel.ActivityType.class, strCategory), 0);
        }, cityCode, strCategory.equals("全部分类") ? null : Enum.valueOf(ActivityModel.ActivityType.class, strCategory), pageIndex);
        System.out.println("pageIndex " + pageIndex);
    }

    public static void setPopupWindowTouchModal(PopupWindow popupWindow, boolean touchModal) {
        if (null == popupWindow) {
            return;
        }
        Method method;
        try {
            method = PopupWindow.class.getDeclaredMethod("setTouchModal", boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, touchModal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] locationCommunityCount = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    @Override
    public void execute() {
        if (popupWindowLocation != null)
            if (popupWindowLocation.isShowing()) {
                popupWindowLocation.dismiss();
                iv_show_flag_location.setImageResource(R.drawable.ic_unpress_next_step);
            }
        if (popupWindowCategory != null)
            if (popupWindowCategory.isShowing()) {
                popupWindowCategory.dismiss();
                iv_show_flag_category.setImageResource(R.drawable.ic_unpress_next_step);
            }
    }
}
