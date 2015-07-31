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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/30.
 */
public class VerifyCodeActivity extends Activity implements TextWatcher{
    @InjectView(R.id.iv_return)
    ImageView iv_return;
    @InjectView(R.id.ll_container)
    LinearLayout ll_container;
    @InjectView(R.id.tv_next)
    TextView tv_next;
    @InjectView(R.id.tv_notice)
    TextView tv_notice;

    private int stepN=1;  //默认step

    private View view_step1;
    private View view_step2;

    //step1
    private EditText et_phone;

    //step2
    private TextView tv_phone;
    private EditText et_code;

    private String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifycode);
        ButterKnife.inject(this);

        view_step1=(View)View.inflate(VerifyCodeActivity.this, R.layout.view_verify_step1, null);
        view_step2=(View)View.inflate(VerifyCodeActivity.this, R.layout.view_verify_step2, null);

        ll_container.addView(view_step1);//默认设置step1

        bindView();
        initListener();
        initData();

    }

    private void bindView() {
        et_phone=(EditText)view_step1.findViewById(R.id.et_phone);
        tv_phone=(TextView)view_step2.findViewById(R.id.tv_phone);
        et_code=(EditText)view_step2.findViewById(R.id.et_code);
    }

    private void initListener() {
        tv_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 重新获取验证码
                phone=et_phone.getText().toString();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepN==1){
                    changePageTo(2);
                    //TODO 要求验证
                    phone=et_phone.getText().toString(); //已输入手机号

                }else{
                    //TODO 验证
                    String p=phone; //手机号
                    String code=et_code.getText().toString(); //已输入验证码


                    //TODO 修改
                    startActivity(new Intent(VerifyCodeActivity.this, ResetPasswordActivity.class));
                }
            }
        });

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepN==1){
                    finish();
                }else{
                    changePageTo(1);
                }
            }
        });

        et_phone.addTextChangedListener(this);
        et_code.addTextChangedListener(this);
    }

    private void initData() {
    }

    private void changePageTo(int i){
        if (i==1&&stepN!=1){
            stepN=1;
            ll_container.removeAllViews();
            ll_container.addView(view_step1);
            et_phone.setText(phone);
            tv_notice.setText("");
            tv_next.setText("获取验证码");
        }
        if (i==2&&stepN!=2){
            stepN=2;
            ll_container.removeAllViews();
            ll_container.addView(view_step2);
            tv_phone.setText(phone);
            tv_notice.setText("验证码有效时间计时器");
            tv_next.setText("下一步");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /**当有文字输入时才可以点击下一步按钮**/
        if (stepN==1&&!TextUtils.isEmpty(et_phone.getText().toString())) {
            phone=et_phone.getText().toString();
            tv_next.setEnabled(true);
        }else if (stepN==2&&!TextUtils.isEmpty(et_code.getText().toString())){
            tv_next.setEnabled(true);
        }else {
            tv_next.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
