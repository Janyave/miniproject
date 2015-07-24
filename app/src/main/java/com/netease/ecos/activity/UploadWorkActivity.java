package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.netease.ecos.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/23.
 */
public class UploadWorkActivity extends Activity {
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_work_layout);
        ButterKnife.inject(this);
        //implementation on the title bar
        titleTxVw.setVisibility(View.INVISIBLE);
        rightButton.setText("发布");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:send the content to the server.
                UploadWorkActivity.this.finish();
            }
        });
        backTxVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadWorkActivity.this.finish();
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
