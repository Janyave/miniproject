package com.netease.ecos.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/8/5.
 */
public class PersonSetInformationNormalActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.lly_left_action)
    LinearLayout title_left;
    @InjectView(R.id.tv_title)
    TextView title_text;
    @InjectView(R.id.lly_right_action)
    LinearLayout title_right;

    public static String ACTICITY_TYPE="type";

    public final static int TYPE_NAME=1;
    public final static int TYPE_SIGNATURE=2;
    public final static int TYPE_PASSWORD=3;


    @InjectView(R.id.et_input)
    EditText et_input;
    @InjectView(R.id.et_inputPassword)
    EditText et_inputPassword;
    @InjectView(R.id.et_inputPassword2)
    EditText et_inputPassword2;
    @InjectView(R.id.ll_inputPassword)
    LinearLayout ll_inputPassword;
    @InjectView(R.id.btn_right_action)
    Button btn_right_action;

    private int TYPE=0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_change_information_normal);
        ButterKnife.inject(this);

        initListener();

        try{
            TYPE=getIntent().getExtras().getInt(ACTICITY_TYPE);

            switch (TYPE){
                case TYPE_NAME:
                    initNameData();
                    break;
                case TYPE_SIGNATURE:
                    initSignatureData();
                    break;
                case TYPE_PASSWORD:
                    initPasswordData();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }

    }

    private void initListener() {
        title_left.setOnClickListener(this);
        title_right.setOnClickListener(this);
    }

    private void initNameData() {
        ll_inputPassword.setVisibility(View.GONE);
        et_input.setHint("请输入新昵称");
    }

    private void initSignatureData() {
        ll_inputPassword.setVisibility(View.GONE);
        et_input.setHint("请输入新简介");
    }

    private void initPasswordData() {
        ll_inputPassword.setVisibility(View.VISIBLE);
        et_input.setHint("请输入旧密码");
        et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_inputPassword.setHint("请输入新密码");
        et_inputPassword2.setHint("请再次输入新密码");
    }

    @Override
    public void onClick(View v) {
        Log.w("xuyun", TYPE + "");
        switch (v.getId()){
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.lly_right_action:

                //TODO change
                switch (TYPE){
                    case TYPE_NAME:
                        System.out.println("TYPE_NAME " + et_input.getText());
                        //TODO
                        break;
                    case TYPE_SIGNATURE:
                        System.out.println("TYPE_SIGNATURE " + et_input.getText());
                        //TODO
                        break;
                    case TYPE_PASSWORD:
                        System.out.println("TYPE_PASSWORD " + et_input.getText());
                        System.out.println("TYPE_PASSWORD " + et_inputPassword.getText());
                        System.out.println("TYPE_PASSWORD " + et_inputPassword.getText());
                        //TODO
                        break;
                }
                break;
        }
    }
}
