package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.netease.ecos.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/27.
 */
public class CourseTypeActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.tv_type_1)
    ImageView makeuperImgVw;
    @InjectView(R.id.tv_type_2)
    ImageView propImgVw;
    @InjectView(R.id.tv_type_3)
    ImageView hairImgVw;
    @InjectView(R.id.tv_type_4)
    ImageView costumeImgVw;
    @InjectView(R.id.tv_type_5)
    ImageView photographyImgVw;
    @InjectView(R.id.tv_type_6)
    ImageView backstageImgVw;
    @InjectView(R.id.tv_type_7)
    ImageView experienceImgVw;
    @InjectView(R.id.tv_type_8)
    ImageView othersImgVw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_activity);
        ButterKnife.inject(this);
        makeuperImgVw.setOnClickListener(this);
        propImgVw.setOnClickListener(this);
        hairImgVw.setOnClickListener(this);
        costumeImgVw.setOnClickListener(this);
        photographyImgVw.setOnClickListener(this);
        backstageImgVw.setOnClickListener(this);
        experienceImgVw.setOnClickListener(this);
        othersImgVw.setOnClickListener(this);
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
