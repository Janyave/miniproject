package com.netease.ecos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.request.NorResponce;
import com.netease.ecos.request.user.UpdateUserInfoRequest;

import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/8/5.
 */
public class PersonSetTagsActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.lly_right_action)
    LinearLayout title_right;
    @InjectView(R.id.tv_right_text)
    TextView title_right_text;
    @InjectView(R.id.tv_title)
    TextView title_text;
    @InjectView(R.id.lly_left_action)
    LinearLayout title_left;

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

    private Set<User.RoleType> roleTypes;
    private User.RoleType roleType;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_person_set_tags);
        ButterKnife.inject(this);

        initTitle();
        initView();
        initListener();
        initData();
    }

    private void initTitle() {
        title_right_text.setText("确定");
        title_text.setText("标签");
    }

    private void initView() {
        user = UserDataService.getSingleUserDataService(this).getUser();
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
                //checkbox state check
                //TODO
                if (checkBox1.isChecked())
                {
                    roleType = User.RoleType.getRoleTypeByValue("Coser");
                    roleTypes.add(roleType);
                }
                if (checkBox2.isChecked())
                {
                    roleType = User.RoleType.getRoleTypeByValue("妆娘");
                    roleTypes.add(roleType);
                }
                if (checkBox3.isChecked())
                {
                    roleType = User.RoleType.getRoleTypeByValue("摄影");
                    roleTypes.add(roleType);
                }
                if (checkBox4.isChecked())
                {
                    roleType = User.RoleType.getRoleTypeByValue("后期");
                    roleTypes.add(roleType);
                }
                if (checkBox5.isChecked())
                {
                    roleType = User.RoleType.getRoleTypeByValue("裁缝");
                    roleTypes.add(roleType);
                }
                if (checkBox6.isChecked())
                {
                    roleType = User.RoleType.getRoleTypeByValue("道具");
                    roleTypes.add(roleType);
                }
                user.roleTypeSet = roleTypes;
                sendUser(user);
                finish();
                break;
        }
    }

    void sendUser(User user) {
        request.request(new NorResponce() {
            @Override
            public void success() {

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
