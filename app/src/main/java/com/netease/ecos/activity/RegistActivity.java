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
import com.netease.ecos.request.user.RegistRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/30.
 */
public class RegistActivity extends Activity implements View.OnClickListener,TextWatcher{
    @InjectView(R.id.et_name)
    EditText et_name;
    @InjectView(R.id.et_password)
    EditText et_password;
    @InjectView(R.id.iv_avatar)
    ImageView iv_avatar;
    @InjectView(R.id.tv_complete)
    TextView tv_complete;
    @InjectView(R.id.iv_return)
    ImageView iv_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        et_name.addTextChangedListener(this);
        et_password.addTextChangedListener(this);

        iv_avatar.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        iv_return.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_complete:
                //TODO 注册
                RegistRequest request = new RegistRequest();
                request.request(new RegistResponse(), getIntent().getStringExtra("phone"), et_password.getText().toString(), et_name.getText().toString(),
                        "http://img5.imgtn.bdimg.com/it/u=3692347433,431191650&fm=21&gp=0.jpg");
                break;
            case R.id.iv_avatar:
                //TODO 选择头像
                Toast.makeText(RegistActivity.this, "CHOOSE ICON", Toast.LENGTH_SHORT).show();
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
        if (et_password.getText().toString().length()>7&&et_password.getText().toString().length()<17&&!TextUtils.isEmpty(et_name.getText().toString())) {
            tv_complete.setEnabled(true);
        }else {
            tv_complete.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    class RegistResponse extends BaseResponceImpl implements NorResponce{

        @Override
        public void success() {
            Toast.makeText(RegistActivity.this, "REGIST SUCCESS", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void doAfterFailedResponse(String message) {
            Toast.makeText(RegistActivity.this, "REGIST FAIL", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(RegistActivity.this, "NETWORK FAIL", Toast.LENGTH_SHORT).show();
        }
    }
}
