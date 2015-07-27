package com.netease.ecos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.UploadWorksListAdapter;
import com.netease.ecos.dialog.SetPhotoDialog;
import com.netease.ecos.utils.SetPhotoHelper;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/24.
 */
public class UploadWorksActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    @InjectView(R.id.uploadWorksLsVw)
    ExtensibleListView worksLsVw;
    @InjectView(R.id.uploadWorksCoverImgVw)
    ImageView coverImgView;

    private UploadWorksListAdapter uploadWorksListAdapter;
    /**
     * 当前正在设置第(couserStepPosition+1)步的教程图片
     */
    private int mCouserStepPosition = -1;
    public SetPhotoHelper mSetPhotoHelper;
    private String PhotoId;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.upload_works_layout);
        ButterKnife.inject(this);
        //choose the cover image
        mSetPhotoHelper = new SetPhotoHelper(this, null);
        //图片裁剪后输出宽度
        final int outPutWidth = 450;
        //图片裁剪后输出高度
        final int outPutHeight = 300;
        mSetPhotoHelper.setOutput(outPutWidth, outPutHeight);
        initView();
    }

    public void initView() {
        //implementation on the title bar
        titleTxVw.setText("新建作品");
        rightButton.setText("发布");
        rightButton.setOnClickListener(this);
        backTxVw.setOnClickListener(this);
        uploadWorksListAdapter = new UploadWorksListAdapter(this, getIntent().getExtras().getStringArrayList("paths"));
        worksLsVw.setAdapter(uploadWorksListAdapter);
        coverImgView.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SetPhotoHelper.REQUEST_BEFORE_CROP:
                    mSetPhotoHelper.setmSetPhotoCallBack(
                            new SetPhotoHelper.SetPhotoCallBack() {
                                @Override
                                public void success(String imagePath) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                                    coverImgView.setImageBitmap(bitmap);
                                }
                            });
                    mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_BEFORE_CROP, data);
                    return;
                case SetPhotoHelper.REQUEST_AFTER_CROP:
                    mSetPhotoHelper.handleActivityResult(SetPhotoHelper.REQUEST_AFTER_CROP, data);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right_action:
                UploadWorksActivity.this.finish();
                break;
            case R.id.tv_left:
                UploadWorksActivity.this.finish();
                break;
            case R.id.uploadWorksCoverImgVw:
                SetPhotoDialog dialog = new SetPhotoDialog(UploadWorksActivity.this, new SetPhotoDialog.ISetPhoto() {

                    @Override
                    public void choosePhotoFromLocal() {
                        mSetPhotoHelper.choosePhotoFromLocal();
                    }

                    @Override
                    public void takePhoto() {
                        mSetPhotoHelper.takePhoto(true);
                    }
                });
                dialog.showSetPhotoDialog();
                break;
        }
    }
}
