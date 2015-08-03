package com.netease.ecos.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.views.HorizontalListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EventDetailActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.lly_left_action)
    View btn_return;
    @InjectView(R.id.btn_right_action)
    Button btn_confirm;


    @InjectView(R.id.ll_photos)
    LinearLayout ll_photos; //gone when the activity not begin
    @InjectView(R.id.hlv_photos)
    HorizontalListView hlv_photos; //show the photos
    @InjectView(R.id.tv_publish_photo)
    TextView tv_publish_photo;

    @InjectView(R.id.tv_wantgo)
    TextView tv_wantgo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.inject(this);

        btn_return.setOnClickListener(this);
        btn_confirm.setVisibility(View.INVISIBLE );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lly_left_action:
                finish();
                //onBackPressed();
                //onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            default:
                break;
        }
    }
}
