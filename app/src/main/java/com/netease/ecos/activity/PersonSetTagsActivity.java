package com.netease.ecos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/8/5.
 */
public class PersonSetTagsActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.lly_left_action)
    LinearLayout title_left;
    @InjectView(R.id.lly_right_action)
    LinearLayout title_right;
    @InjectView(R.id.tv_title)
    TextView title_text;

    @InjectView(R.id.checkbox1)
    CheckBox checkBox1;
    @InjectView(R.id.checkbox2)
    CheckBox checkBox2;
    @InjectView(R.id.checkbox3)
    CheckBox checkBox3;
    @InjectView(R.id.checkbox4)
    CheckBox checkBox4;
    @InjectView(R.id.checkbox5)
    CheckBox checkBox5;
    @InjectView(R.id.checkbox6)
    CheckBox checkBox6;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_person_set_tags);
        ButterKnife.inject(this);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        title_text.setText("设置");
    }

    private void initListener() {
        title_left.setOnClickListener(this);
        title_right.setOnClickListener(this);
    }


    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.lly_right_action:
                //checkbox state check
                //TODO
                break;
        }
    }
}
