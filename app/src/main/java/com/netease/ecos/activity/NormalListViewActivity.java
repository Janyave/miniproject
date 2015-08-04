package com.netease.ecos.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.EventWantGoAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/8/4.
 */
public class NormalListViewActivity extends BaseActivity{

    @InjectView(R.id.lv_list)
    ListView lv_list;

    public static String LISTVIEW_TYPE="type";

    public final static int TYPE_EVENT_WANTGO=0;



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
        eventWantGoAdapter=new EventWantGoAdapter(this);
        lv_list.setAdapter(eventWantGoAdapter);
    }
}
