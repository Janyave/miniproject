package com.netease.ecos.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.request.NorResponce;
import com.netease.ecos.request.user.ModifyPasswordRequest;
import com.netease.ecos.request.user.UpdateUserInfoRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/8/5.
 */
public class PersonSetInformationNormalActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.lly_right_action)
    LinearLayout title_right;
    @InjectView(R.id.tv_right_text)
    TextView title_right_text;
    @InjectView(R.id.tv_title)
    TextView title_text;
    @InjectView(R.id.lly_left_action)
    LinearLayout title_left;

    public static String ACTICITY_TYPE = "type";

    public final static int TYPE_NAME = 1;
    public final static int TYPE_SIGNATURE = 2;
    public final static int TYPE_PASSWORD = 3;


    @InjectView(R.id.et_input)
    EditText et_input;
    @InjectView(R.id.et_inputPassword)
    EditText et_inputPassword;
    @InjectView(R.id.et_inputPassword2)
    EditText et_inputPassword2;
    @InjectView(R.id.ll_inputPassword)
    LinearLayout ll_inputPassword;

    private int TYPE = 0;
    private User user;
    private UpdateUserInfoRequest request;
    private ModifyPasswordRequest modifyPasswordRequest;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_change_information_normal);
        ButterKnife.inject(this);

        user = UserDataService.getSingleUserDataService(this).getUser();
        request = new UpdateUserInfoRequest();
        modifyPasswordRequest = new ModifyPasswordRequest();

        initTitle();
        initListener();

        try {
            TYPE = getIntent().getExtras().getInt(ACTICITY_TYPE);

            switch (TYPE) {
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
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

    }

    private void initTitle() {
        title_right_text.setText("确定");
        title_text.setText("");
    }

    private void initListener() {
        title_left.setOnClickListener(this);
        title_right.setOnClickListener(this);
    }

    private void initNameData() {
        ll_inputPassword.setVisibility(View.GONE);

        et_input.setHint(user.nickname);
    }

    private void initSignatureData() {
        ll_inputPassword.setVisibility(View.GONE);
        et_input.setHint(user.characterSignature);
    }

    private void initPasswordData() {
        ll_inputPassword.setVisibility(View.VISIBLE);
        et_input.setHint("请输入旧密码");
        et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_inputPassword.setHint("请输入新密码(8-16位)");
        et_inputPassword2.setHint("请再次输入新密码");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lly_left_action:
                finish();
                break;
            case R.id.lly_right_action:
                //TODO change
                System.out.println(TYPE);
                switch (TYPE) {
                    case TYPE_NAME:
                        user.nickname = et_input.getText().toString();
                        if (user.nickname.equals(""))
                            Toast.makeText(this, "请输入昵称", Toast.LENGTH_LONG).show();
                        else
                            sendUser(user);
                        Log.w("User", et_input.getText().toString());
                        //TODO
                        break;
                    case TYPE_SIGNATURE:
                        user.characterSignature = et_input.getText().toString();
                        sendUser(user);
                        Log.w("User", et_input.getText().toString());
                        //TODO
                        break;
                    case TYPE_PASSWORD:
                        if (et_inputPassword.getText().toString().equals(et_inputPassword2.getText().toString())) {
                            if (et_inputPassword.getText().toString().length()>7&&et_inputPassword.getText().toString().length()<17){
                                checkPassword(et_input.getText().toString(), et_inputPassword.getText().toString());
                            }else {
                                Toast.makeText(this, "请输入8~16位密码", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, "密码输入不一致", Toast.LENGTH_LONG).show();
                            et_input.setText("");
                            et_inputPassword.setText("");
                            et_inputPassword2.setText("");
                        }
                        Log.w("User", et_input.getText().toString());
                        //TODO
                        break;
                }
                break;
        }
    }

    void sendUser(User user) {
        request.request(new NorResponce() {
            @Override
            public void success() {
                Toast.makeText(PersonSetInformationNormalActivity.this, "success", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void doAfterFailedResponse(String message) {
                Toast.makeText(PersonSetInformationNormalActivity.this, getResources().getString(R.string.personalInformationLoadError) + message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void responseNoGrant() {
                Toast.makeText(PersonSetInformationNormalActivity.this, getResources().getString(R.string.personalInformationLoadError), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PersonSetInformationNormalActivity.this, getResources().getString(R.string.personalInformationLoadError), Toast.LENGTH_SHORT).show();
                finish();
            }
        }, user);
    }

    void checkPassword(String oldPwd, String newPwd) {
        modifyPasswordRequest.request(new NorResponce() {
            @Override
            public void success() {
                Toast.makeText(PersonSetInformationNormalActivity.this, "success", Toast.LENGTH_LONG).show();
                et_input.setText("");
                et_inputPassword.setText("");
                et_inputPassword2.setText("");
                finish();
            }

            @Override
            public void doAfterFailedResponse(String message) {
                Toast.makeText(PersonSetInformationNormalActivity.this, "密码输入错误", Toast.LENGTH_LONG).show();
                et_input.setText("");
                et_inputPassword.setText("");
                et_inputPassword2.setText("");
            }

            @Override
            public void responseNoGrant() {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, oldPwd, newPwd);
    }
}
