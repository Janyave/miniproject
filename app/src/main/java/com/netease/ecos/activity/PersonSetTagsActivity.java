package com.netease.ecos.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.request.NorResponce;
import com.netease.ecos.request.user.UpdateUserInfoRequest;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

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

    private User user;
    private UpdateUserInfoRequest request;

    private User.RoleType roleType;

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
        request = new UpdateUserInfoRequest();

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
                //TODO
                user = UserDataService.getSingleUserDataService(this).getUser();
                if (checkBox1.isChecked())
                {
                    roleType = User.RoleType.coser;
                    user.roleTypeSet.add(roleType);
                    Log.w("inTag","coser");
                }
                if (checkBox2.isChecked())
                {
                    roleType = User.RoleType.妆娘;
                    user.roleTypeSet.add(roleType);
                    Log.w("inTag", "妆娘");
                }
                if (checkBox3.isChecked())
                {
                    roleType = User.RoleType.摄影;
                    user.roleTypeSet.add(roleType);
                    Log.w("inTag", "摄影");
                }
                if (checkBox4.isChecked())
                {
                    roleType = User.RoleType.后期;
                    user.roleTypeSet.add(roleType);
                    Log.w("inTag", "后期");
                }
                if (checkBox5.isChecked())
                {
                    roleType = User.RoleType.裁缝;
                    user.roleTypeSet.add(roleType);
                    Log.w("inTag", "裁缝");
                }
                if (checkBox6.isChecked())
                {
                    roleType = User.RoleType.道具;
                    user.roleTypeSet.add(roleType);
                    Log.w("inTag", "道具");
                }
                Log.w("roleType---Size", user.roleTypeSet.size() + "");
                sendUser(user);
                break;
        }
    }

    void sendUser(User user) {
        request.request(new NorResponce() {
            @Override
            public void success() {
                Toast.makeText(PersonSetTagsActivity.this, "success", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void doAfterFailedResponse(String message) {

            }

            @Override
            public void responseNoGrant() {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, user);
    }
}
