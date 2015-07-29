package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.NotificationContactAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/29.
 */
public class NotificationActivity extends Activity implements View.OnClickListener{

    @InjectView(R.id.tv_reply)
    TextView tv_reply; //回复
    @InjectView(R.id.tv_contact)
    TextView tv_contact; //私信
    @InjectView(R.id.tv_notice)
    TextView tv_notice; //通知
    @InjectView(R.id.lv_list)
    ListView lv_list; //显示列表

    private NotificationContactAdapter contactAdapter; //私信Adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        tv_contact.setOnClickListener(this);
        tv_notice.setOnClickListener(this);
        tv_reply.setOnClickListener(this);

        lv_list.setDividerHeight(0);
    }

    private void initData() {
        contactAdapter=new NotificationContactAdapter(this);
        lv_list.setAdapter(contactAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_reply:
                //回复
                break;
            case R.id.tv_contact:
                //私信
                break;
            case R.id.tv_notice:
                //通知
                break;
        }
    }
}
