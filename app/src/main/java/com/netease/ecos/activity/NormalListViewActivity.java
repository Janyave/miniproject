package com.netease.ecos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.adapter.EventWantGoAdapter;
import com.netease.ecos.model.User;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.user.FollowedUserListRequest;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/8/4.
 */
public class NormalListViewActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.lly_right_action)
    LinearLayout title_right;
    @InjectView(R.id.tv_right_text)
    TextView title_right_text;
    @InjectView(R.id.tv_title)
    TextView title_text;
    @InjectView(R.id.lly_left_action)
    LinearLayout title_left;
    @InjectView(R.id.lv_list)
    ListView lv_list;

    public static String LISTVIEW_TYPE="type";

    public final static int TYPE_EVENT_WANTGO=0;
    public final static int TYPE_EVENT_FANS=1;
    public final static int TYPE_EVENT_ATTENTION=2;

    private int TYPE=TYPE_EVENT_WANTGO;  //当前Activity类型

    private EventWantGoAdapter eventWantGoAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_listview_normal);
        ButterKnife.inject(this);

        TYPE=getIntent().getExtras().getInt(LISTVIEW_TYPE);

        switch (TYPE){
            case TYPE_EVENT_WANTGO:
                initEventWantGo();
                break;
            case TYPE_EVENT_ATTENTION:
                initFollows();
                break;
            case TYPE_EVENT_FANS:
                initFans();
                break;
        }
    }

    private void initEventWantGo() {
        title_left.setOnClickListener(this);
        title_right.setVisibility(View.INVISIBLE);
        title_text.setText("想去的人");
        eventWantGoAdapter=new EventWantGoAdapter(this, TYPE_EVENT_WANTGO, null);
        lv_list.setAdapter(eventWantGoAdapter);

        FollowedUserListRequest request  = new FollowedUserListRequest();
        request.requestMyFollows(new followedUserListRequest(), 1);

    }

    private void initFollows() {
        title_left.setOnClickListener(this);
        title_right.setVisibility(View.INVISIBLE);
        title_text.setText("我的关注");

        FollowedUserListRequest request  = new FollowedUserListRequest();
        request.requestMyFollows(new followedUserListRequest(), 1);
        showProcessBar("获取关注列表");
    }

    private void initFans() {
        title_left.setOnClickListener(this);
        title_right.setVisibility(View.INVISIBLE);
        title_text.setText("我的粉丝");

        FollowedUserListRequest request  = new FollowedUserListRequest();
        request.requestSomeOneFans(new followedUserListRequest(), null, 1);
        showProcessBar("获取粉丝列表");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lly_left_action:
                finish();
                break;
        }
    }

    class followedUserListRequest extends BaseResponceImpl implements FollowedUserListRequest.IFollowUserListResponce {

        @Override
        public void success(List<User> userList) {
            dismissProcessBar();
            if (TYPE==TYPE_EVENT_ATTENTION) {
                eventWantGoAdapter = new EventWantGoAdapter(NormalListViewActivity.this, TYPE_EVENT_ATTENTION, userList);
                lv_list.setAdapter(eventWantGoAdapter);
            }
            if (TYPE==TYPE_EVENT_FANS) {
                eventWantGoAdapter = new EventWantGoAdapter(NormalListViewActivity.this, TYPE_EVENT_FANS, userList);
                lv_list.setAdapter(eventWantGoAdapter);
            }
        }

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
}
