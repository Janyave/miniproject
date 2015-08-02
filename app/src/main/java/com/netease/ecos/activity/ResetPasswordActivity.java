package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.NorResponce;
import com.netease.ecos.request.user.ResetPasswordRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/30.
 */
public class ResetPasswordActivity extends Activity implements View.OnClickListener,TextWatcher{
    @InjectView(R.id.et_password)
    EditText et_password;
    @InjectView(R.id.tv_reset_password)
    TextView tv_reset_password;
    @InjectView(R.id.iv_return)
    ImageView iv_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.inject(this);


        initListener();
        initData();
    }

    private void initListener() {
        et_password.addTextChangedListener(this);
        tv_reset_password.setOnClickListener(this);
        iv_return.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_reset_password:
                ResetPasswordRequest reqeust = new ResetPasswordRequest();
                //TODO phone
                reqeust.request(new ResetPwdResponse(), getIntent().getStringExtra("phone"), et_password.getText().toString());
                break;
            case R.id.iv_return:
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (et_password.getText().toString().length()>7&&et_password.getText().toString().length()<17){
            tv_reset_password.setEnabled(true);
        }else{
            tv_reset_password.setEnabled(false);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }

    class ResetPwdResponse extends BaseResponceImpl implements NorResponce{

        @Override
        public void success() {
            Toast.makeText(ResetPasswordActivity.this, "RESET SUCCESS", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void doAfterFailedResponse(String message) {
            Toast.makeText(ResetPasswordActivity.this, "RESET FAIL", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(ResetPasswordActivity.this, "NETWORK FAIL", Toast.LENGTH_SHORT).show();
        }
    }
}
