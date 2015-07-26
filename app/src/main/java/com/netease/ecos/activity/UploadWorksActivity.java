package com.netease.ecos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.ExhibitListViewAdapter;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/24.
 */
public class UploadWorksActivity extends BaseActivity {
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    @InjectView(R.id.uploadWorksLsVw)
    ExtensibleListView worksLsVw;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.upload_works_layout);
        ButterKnife.inject(this);
        //implementation on the title bar
        titleTxVw.setText("新建作品");
        rightButton.setText("发布");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:send the content to the server.
                UploadWorksActivity.this.finish();
            }
        });
        backTxVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadWorksActivity.this.finish();
            }
        });
        worksLsVw.setAdapter(new ExhibitListViewAdapter(UploadWorksActivity.this));
    }
}
