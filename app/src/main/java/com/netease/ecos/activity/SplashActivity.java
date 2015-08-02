package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.netease.ecos.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 * Created by hzjixinyu on 2015/7/30.
 */
public class SplashActivity extends Activity implements View.OnClickListener{

    @InjectView(R.id.tv_login)
    TextView tv_login;
    @InjectView(R.id.tv_regist)
    TextView tv_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplash);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        tv_login.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }

    private void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                break;
            case R.id.tv_regist:
                startActivityForResult(new Intent(SplashActivity.this, VerifyCodeActivity.class), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Intent intent=new Intent(SplashActivity.this, RegistActivity.class);
            intent.putExtra("phone",data.getStringExtra("phone"));
            startActivity(intent);
        }
    }
}
