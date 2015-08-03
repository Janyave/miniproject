package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.utils.RoundImageView;

public class PersonalInfoSettingActivity extends BaseActivity {

    private static final String[] gender = {"男", "女", "保密"};

    private LinearLayout mReturn;
    private RoundImageView mAvatarImg;
    private ImageView mSetAvatar;
    private TextView mSetName;
    private TextView mSetGender;
    private TextView mSetIntro;
    private ImageView mSetPwd;
    private Switch mSetMsgAlert;
    private Button mLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_setting);
        onBoundView();
        onBoundLinster();
    }

    private void onBoundView() {
        mReturn = (LinearLayout) findViewById(R.id.lly_left_action);
        mAvatarImg = (RoundImageView) findViewById(R.id.personal_info_set_avatar_pic);
        mSetAvatar = (ImageView) findViewById(R.id.personal_info_set_avatar);
        mSetName = (TextView) findViewById(R.id.personal_info_set_name);
        mSetGender = (TextView) findViewById(R.id.personal_info_set_gender);
        mSetIntro = (TextView) findViewById(R.id.personal_info_set_intro);
        mSetPwd = (ImageView) findViewById(R.id.personal_info_set_pwd);
        mSetMsgAlert = (Switch) findViewById(R.id.personal_info_set_Msg_alert);
        mLogOut = (Button) findViewById(R.id.personal_info_logout);
    }

    private void onBoundLinster() {
        //bound button linster
        ButtonListener listener = new ButtonListener();
        mReturn.setOnClickListener(listener);
        mSetAvatar.setOnClickListener(listener);
        mAvatarImg.setOnClickListener(listener);
        mSetPwd.setOnClickListener(listener);
        mLogOut.setOnClickListener(listener);

        //bound spinner linster
        bonudSpinner();
        //bound swith linster
        mSetMsgAlert.setOnCheckedChangeListener(new SwitchCheckedListener());

    }

    private void bonudSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gender);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lly_left_action:
                    finish();
                    break;

                case R.id.personal_info_set_avatar_pic:
                case R.id.personal_info_set_avatar:
                    //TODO set avatar
                    break;
                case R.id.personal_info_set_pwd:
                    //TODO set pwd
                    break;
                case R.id.personal_info_logout:
                    Intent intent=new Intent(PersonalInfoSettingActivity.this,SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //TODO 选中后要做的事情
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class SwitchCheckedListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                //TODO 接收消息
            } else {
                //TODO 关闭接收
            }
        }
    }

}
