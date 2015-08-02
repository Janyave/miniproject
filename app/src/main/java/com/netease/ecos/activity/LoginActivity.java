package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.NorResponce;
import com.netease.ecos.request.user.LoginRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/30.
 */
public class LoginActivity extends Activity implements TextWatcher,View.OnClickListener{
    @InjectView(R.id.et_phone)
    EditText et_phone;
    @InjectView(R.id.et_password)
    EditText et_password;
    @InjectView(R.id.tv_login)
    TextView tv_login;
    @InjectView(R.id.tv_forgetPassword)
    TextView tv_forgetPassword;
    @InjectView(R.id.iv_return)
    ImageView iv_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);


        initListener();
        initData();
    }

    private void initListener() {
        et_phone.addTextChangedListener(this);
        et_password.addTextChangedListener(this);

        tv_forgetPassword.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        iv_return.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_forgetPassword:
                startActivity(new Intent(LoginActivity.this, VerifyCodeActivity.class));
                break;
            case R.id.iv_return:
                finish();
                break;
        }
    }

    private void login() {
        LoginRequest request = new LoginRequest();
        request.request(new LoginResponse(), et_phone.getText().toString(),et_password.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (et_password.getText().toString().length()>7&&et_password.getText().toString().length()<17&&!TextUtils.isEmpty(et_phone.getText().toString())){
        if (!TextUtils.isEmpty(et_phone.getText().toString())){
            tv_login.setEnabled(true);
        }else{
            tv_login.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    class LoginResponse extends BaseResponceImpl implements NorResponce{

        @Override
        public void success() {
            Toast.makeText(LoginActivity.this,"LOGIN SUCCESS",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void doAfterFailedResponse(String message) {
            Toast.makeText(LoginActivity.this,"LOGIN FAIL",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(LoginActivity.this,"NETWORK FAIL",Toast.LENGTH_SHORT).show();
        }
    }
}