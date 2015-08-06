package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.RecruitmentListViewAdapter;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.recruitment.RecruitmentListRequest;
import com.netease.ecos.views.XListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/27.
 */
public class RecruitmentCategoryActivity extends Activity implements View.OnClickListener, XListView.IXListViewListener {

    private static final String TAG = "Ecos---Recruitment";
    public static final String TRecruitmentType = "recruitmentType";
    private String recruitment_type = "";
    @InjectView(R.id.sp_sortType)
    Spinner sp_sortType;
    @InjectView(R.id.lv_list)
    XListView lv_list;

    @InjectView(R.id.lly_left_action)
    LinearLayout lly_left_action;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_location)
    TextView tv_location;
    @InjectView(R.id.ll_location)
    LinearLayout ll_location;
    @InjectView(R.id.tv_left)
    TextView recruitmentTypeTxVw;

    private ArrayAdapter<RecruitmentListRequest.SortRule> spAdapter;
    private RecruitmentListRequest.SortRule sortRules[] = {RecruitmentListRequest.SortRule.智能排序,
            RecruitmentListRequest.SortRule.价格最低, RecruitmentListRequest.SortRule.最受欢迎, RecruitmentListRequest.SortRule.距离最近};

    private RecruitmentListViewAdapter recruitmentListViewAdapter;
    //for request
    private RecruitmentListRequest request;
    private RecruitmentListResponse recruitmentListResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_type);
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

        ll_left.setOnClickListener(this);
        lly_left_action.setOnClickListener(this);
        ll_location.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                //TODO 左上角类型选择事件
                break;
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.ll_location:
                //TODO 位置选择界面
                break;
        }
    }

    private void initData() {
        recruitment_type = getIntent().getExtras().getString(TRecruitmentType);
        recruitmentTypeTxVw.setText(recruitment_type);
        //设置下拉菜单选项
        spAdapter = new ArrayAdapter<RecruitmentListRequest.SortRule>(this, android.R.layout.simple_spinner_item, sortRules);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sortType.setAdapter(spAdapter);

        //设置列表Adapter
        lv_list.initRefleshTime(this.getClass().getSimpleName());
        lv_list.setPullLoadEnable(true);
        lv_list.setXListViewListener(this);

        //for request
        request = new RecruitmentListRequest();
        recruitmentListResponse = new RecruitmentListResponse();
        request.request(recruitmentListResponse, Recruitment.RecruitType.妆娘, "12", RecruitmentListRequest.SortRule.智能排序, 1);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(RecruitmentCategoryActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(RecruitmentCategoryActivity.this, "上拉加载", Toast.LENGTH_SHORT).show();

        //1秒后关闭加载
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_list.stopLoadMore();
            }
        }, 1000);
    }

    class RecruitmentListResponse extends BaseResponceImpl implements RecruitmentListRequest.IRecruitmentListResponse {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(List<Recruitment> recruitList) {
            //获取recruit信息
            Log.d(TAG, "========================================================================");
            Log.d(TAG, "recruitment list size:" + recruitList.size());
            recruitmentListViewAdapter = new RecruitmentListViewAdapter(RecruitmentCategoryActivity.this, recruitList);
            lv_list.setAdapter(recruitmentListViewAdapter);
        }
    }
}
