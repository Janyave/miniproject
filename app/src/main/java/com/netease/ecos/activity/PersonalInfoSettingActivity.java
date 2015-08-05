package com.netease.ecos.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
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
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.utils.RoundAngleImageView;
import com.netease.ecos.utils.RoundImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.PipedInputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PersonalInfoSettingActivity extends BaseActivity {

    private static final String[] gender = {"男", "女", "保密"};

    //show
    private LinearLayout mReturn;
    private RoundImageView mAvatarImg;
    private ImageView mSetAvatar;
    private TextView mSetName;
    private TextView mSetGender;
    private TextView mSetIntro;
    private Switch mSetMsgAlert;
    private Button mLogOut;

    //click
    @InjectView(R.id.ll_name)
    LinearLayout ll_name;
    @InjectView(R.id.ll_gender)
    LinearLayout ll_gender;
    @InjectView(R.id.ll_signature)
    LinearLayout ll_signature;
    @InjectView(R.id.ll_password)
    LinearLayout ll_password;

//    private RoundAngleImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_setting);
        ButterKnife.inject(this);
        onBoundView();
        onBoundLinster();
//        iv = (RoundAngleImageView) findViewById(R.id.picasso_test);
//        iv.setImageFromUrl("http://pic4.nipic.com/20090803/2618170_095921092_2.jpg");

    }


    private void onBoundView() {
        mReturn = (LinearLayout) findViewById(R.id.lly_left_action);
        mAvatarImg = (RoundImageView) findViewById(R.id.personal_info_set_avatar_pic);
        mSetAvatar = (ImageView) findViewById(R.id.personal_info_set_avatar);
        mSetName = (TextView) findViewById(R.id.personal_info_set_name);
        mSetGender = (TextView) findViewById(R.id.personal_info_set_gender);
        mSetIntro = (TextView) findViewById(R.id.personal_info_set_intro);
        mSetMsgAlert = (Switch) findViewById(R.id.personal_info_set_Msg_alert);
        mLogOut = (Button) findViewById(R.id.personal_info_logout);
    }

    private void onBoundLinster() {
        //bound button linster
        ButtonListener listener = new ButtonListener();
        mReturn.setOnClickListener(listener);
        mSetAvatar.setOnClickListener(listener);
        mAvatarImg.setOnClickListener(listener);
        mLogOut.setOnClickListener(listener);

        ll_name.setOnClickListener(listener);
        ll_gender.setOnClickListener(listener);
        ll_signature.setOnClickListener(listener);
        ll_password.setOnClickListener(listener);

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
                case R.id.ll_name:
                    Intent intent2=new Intent(PersonalInfoSettingActivity.this,PersonSetInformationNormal.class);
                    Bundle bundle2=new Bundle();
                    bundle2.putInt(PersonSetInformationNormal.ACTICITY_TYPE,PersonSetInformationNormal.TYPE_NAME);
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                    break;
                case R.id.ll_gender:
                    //TODO gender
                    Toast.makeText(PersonalInfoSettingActivity.this,"wait!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_signature:
                    Intent intent3=new Intent(PersonalInfoSettingActivity.this,PersonSetInformationNormal.class);
                    Bundle bundle3=new Bundle();
                    bundle3.putInt(PersonSetInformationNormal.ACTICITY_TYPE,PersonSetInformationNormal.TYPE_SIGNATURE);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                    break;
                case R.id.ll_password:
                    Intent intent1=new Intent(PersonalInfoSettingActivity.this,PersonSetInformationNormal.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt(PersonSetInformationNormal.ACTICITY_TYPE,PersonSetInformationNormal.TYPE_PASSWORD);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
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
