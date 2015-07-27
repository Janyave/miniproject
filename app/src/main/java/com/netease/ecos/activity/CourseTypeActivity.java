package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.netease.ecos.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/27.
 */
public class CourseTypeActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.makeuperBtn)
    Button makeuperBtn;
    @InjectView(R.id.propBtn)
    Button propBtn;
    @InjectView(R.id.hairBtn)
    Button hairBtn;
    @InjectView(R.id.costumeBtn)
    Button costumeBtn;
    @InjectView(R.id.photographyBtn)
    Button photographyBtn;
    @InjectView(R.id.backstageBtn)
    Button backstageBtn;
    @InjectView(R.id.experienceBtn)
    Button experienceBtn;
    @InjectView(R.id.othersBtn)
    Button othersBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_activity);
        ButterKnife.inject(this);
        makeuperBtn.setOnClickListener(this);
        propBtn.setOnClickListener(this);
        hairBtn.setOnClickListener(this);
        costumeBtn.setOnClickListener(this);
        photographyBtn.setOnClickListener(this);
        backstageBtn.setOnClickListener(this);
        experienceBtn.setOnClickListener(this);
        othersBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(CourseTypeActivity.this, BuildCourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", v.getId());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
