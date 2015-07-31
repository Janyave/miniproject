package com.netease.ecos.ViewDemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.netease.ecos.R;
import com.netease.ecos.activity.BaseActivity;
import com.netease.ecos.dialog.SetPhotoDialog;
import com.netease.ecos.utils.SetPhotoHelper;
import com.netease.ecos.utils.UploadImageTools;

import java.io.File;

/**
 * 类描述：{@see UploadImageTools使用演示}
 * Created by enlizhang on 2015/7/23.
 */
public class UploadImageDemoActivity extends BaseActivity {

    ImageView iv_display;

    SetPhotoHelper mSetPhotoHelper;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.demo_upload_image);
        iv_display = (ImageView) findViewById(R.id.iv_display);


        mSetPhotoHelper = new SetPhotoHelper(this, null);
        //图片裁剪后输出宽度
        final int outPutWidth = 450;
        //图片裁剪后输出高度
        final int outPutHeight = 300;
        mSetPhotoHelper.setOutput(outPutWidth, outPutHeight);

        SetPhotoDialog dialog = new SetPhotoDialog(this, new SetPhotoDialog.ISetPhoto() {

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SetPhotoHelper.REQUEST_BEFORE_CROP:
                    //当前是设置封面
                    File imageFile = mSetPhotoHelper.getFileBeforeCrop(data, 300, 200);

                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

                    iv_display.setImageBitmap(bitmap);


                    UploadImageTools.uploadImageSys(imageFile, new UploadCallBack(), UploadImageDemoActivity.this, false);
                    break;
                default:
                    Log.e("CLASS_TAG", "onActivityResult() 无对应");
            }
        } else {
            Log.e(CLASS_TAG, "操作取消");
        }
    }


    class UploadCallBack implements UploadImageTools.UploadCallBack {

        @Override
        public void success(String originUrl, String thumbUrl) {

            Log.i("图片上传", "原图路径" + originUrl);
            Log.i("图片上传", "缩略图路径" + thumbUrl);
        }

        @Override
        public void fail() {

        }

        @Override
        public void onProcess(Object fileParam, long current, long total) {
            Log.i("图片上传", "上传数" + total + "  ," + "已上传" + current);
        }

    }

    ;
}
