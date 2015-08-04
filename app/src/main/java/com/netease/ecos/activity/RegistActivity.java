package com.netease.ecos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.netease.ecos.dialog.SetPhotoDialog;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.NorResponce;
import com.netease.ecos.request.user.RegistRequest;
import com.netease.ecos.utils.SetPhotoHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/30.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener,TextWatcher{
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


    public SetPhotoHelper mSetPhotoHelper;

    public boolean isSettingAvatart = false;

    public String mAvatarLocalPath = "";

    public String mAvatarUrl = "";

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

        mSetPhotoHelper = new SetPhotoHelper(this, null);
        //图片裁剪后输出宽度
        final int outPutWidth = 200;
        //图片裁剪后输出高度
        final int outPutHeight = 200;
        mSetPhotoHelper.setAspect(1, 1);
        mSetPhotoHelper.setOutput(outPutWidth, outPutHeight);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SetPhotoHelper.REQUEST_BEFORE_CROP:
                    //当前是设置封面
                    if (isSettingAvatart) {
                        mSetPhotoHelper.setmSetPhotoCallBack(new SetPhotoHelper.SetPhotoCallBack() {

                            @Override
                            public void success(String imagePath) {
                                Log.i("裁剪后图片路径", "-----------path:" + imagePath);
                                mAvatarLocalPath = imagePath;
                                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                                iv_avatar.setImageBitmap(bitmap);
                                isSettingAvatart = false;
                            }
                        });
                        mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_BEFORE_CROP, data);
                        return;
                    }


                    break;
                case SetPhotoHelper.REQUEST_AFTER_CROP:
                    mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_AFTER_CROP, data);
                    break;
                default:
                    isSettingAvatart = false;
                    Log.e("CLASS_TAG", "onActivityResult() 无对应");
            }


        } else {
            Log.e(CLASS_TAG, "操作取消");
        }
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

                SetPhotoDialog dialog = new SetPhotoDialog(RegistActivity.this,
                        new SetPhotoDialog.ISetPhoto() {

                            @Override
                            public void choosePhotoFromLocal() {
                                Toast.makeText(RegistActivity.this, "选择本地图片", Toast.LENGTH_LONG).show();
                                mSetPhotoHelper.choosePhotoFromLocal();
                            }

                            @Override
                            public void takePhoto() {
                                Toast.makeText(RegistActivity.this, "拍照", Toast.LENGTH_LONG).show();
                                mSetPhotoHelper.takePhoto(false);

                            }
                        });
                dialog.showSetPhotoDialog();

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
