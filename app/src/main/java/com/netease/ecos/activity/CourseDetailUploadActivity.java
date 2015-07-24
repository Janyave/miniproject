package com.netease.ecos.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourseDetailUploadActivity extends ActionBarActivity {


    @InjectView(R.id.iv_myWork)
    ImageView iv_myWork;
    @InjectView(R.id.tv_evaluation)
    TextView tv_evaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_upload);
        ButterKnife.inject(this);


        getSupportActionBar().hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail_upload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
