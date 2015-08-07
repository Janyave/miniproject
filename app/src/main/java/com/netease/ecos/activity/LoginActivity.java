package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.model.AccountDataService;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.NorResponce;
import com.netease.ecos.request.user.LoginRequest;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

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
                startActivityForResult(new Intent(LoginActivity.this, VerifyCodeActivity.class),0);
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

            String imId = AccountDataService.getSingleAccountDataService(LoginActivity.this).getUserAccId();
            String imtoken = AccountDataService.getSingleAccountDataService(LoginActivity.this).getImToken();

            Log.e("云信登录","imId:" + imId);
            Log.e("云信令牌","imtoken:" + imtoken);

            AbortableFuture<LoginInfo> loginRequest;
            loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(imId, imtoken));
            loginRequest.setCallback(new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {

                    Toast.makeText(LoginActivity.this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                }

                @Override
                public void onFailed(int code) {
                    Log.i("登录", "登录失败");
                    if (code == 302 || code == 404) {
                        Toast.makeText(LoginActivity.this, "帐号或密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "login error: " + code, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onException(Throwable exception) {
                    Log.i("登录", "登录异常");
                }

            });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Intent intent=new Intent(LoginActivity.this, ResetPasswordActivity.class);
            intent.putExtra("phone",data.getStringExtra("phone"));
            startActivity(intent);
        }
    }
}
