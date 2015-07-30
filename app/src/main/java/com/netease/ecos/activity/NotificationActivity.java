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
public class NotificationActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.tv_reply)
    TextView tv_reply; //�ظ�
    @InjectView(R.id.tv_contact)
    TextView tv_contact; //˽��
    @InjectView(R.id.tv_notice)
    TextView tv_notice; //֪ͨ
    @InjectView(R.id.lv_list)
    ListView lv_list; //��ʾ�б�

    private NotificationContactAdapter contactAdapter; //˽��Adapter

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
        contactAdapter = new NotificationContactAdapter(this);
        lv_list.setAdapter(contactAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reply:
                //�ظ�
                break;
            case R.id.tv_contact:
                //˽��
                break;
            case R.id.tv_notice:
                //֪ͨ
                break;
        }
    }
}
