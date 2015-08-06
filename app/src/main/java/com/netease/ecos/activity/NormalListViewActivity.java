package com.netease.ecos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.EventWantGoAdapter;

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
    public final static int TYPE_EVENT_STTENTION=2;



    private EventWantGoAdapter eventWantGoAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_listview_normal);
        ButterKnife.inject(this);

        int type=getIntent().getExtras().getInt(LISTVIEW_TYPE);

        switch (type){
            case TYPE_EVENT_WANTGO:
                initEventWantGo();
                break;
        }
    }

    private void initEventWantGo() {
        title_left.setOnClickListener(this);
        title_right.setVisibility(View.INVISIBLE);
        title_right_text.setText("评论");
        title_text.setText("想去的人");
        eventWantGoAdapter=new EventWantGoAdapter(this);
        lv_list.setAdapter(eventWantGoAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lly_left_action:
                finish();
                break;
        }
    }
}
