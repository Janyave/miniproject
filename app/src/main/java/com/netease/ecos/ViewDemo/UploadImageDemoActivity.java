package com.netease.ecos.ViewDemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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

    public SetPhotoHelper mSetPhotoHelper;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.demo_upload_image);

        /*tv_display = (TextView)findViewById(R.id.tv_display);

        File file = new File(Environment.getExternalStorageDirectory() + "/" + "a.png");
        if(file.exists())
        {
            tv_display.setText("a.png存在");
            Log.i("UploadImageDemoActivity", "a.png存在");
        }

        UploadImageTools.uploadImage(file, new UploadImageTools.UploadCallBack() {
            @Override
            public void success(String originUrl, String thumbUrl) {
                tv_display.setText(tv_display.getText().toString()+"\n"+originUrl);
            }

            @Override
            public void fail() {
                tv_display.setText("上传失败");
            }
        });*/

        iv_display = (ImageView)findViewById(R.id.iv_display);

        mSetPhotoHelper = new SetPhotoHelper(this, null);
        //图片裁剪后输出宽度
        final int outPutWidth = 450;
        //图片裁剪后输出高度
        final int outPutHeight = 300;
        mSetPhotoHelper.setOutput(outPutWidth, outPutHeight);

        SetPhotoDialog dialog = new SetPhotoDialog(UploadImageDemoActivity.this,
                new SetPhotoDialog.ISetPhoto() {

                    @Override
                    public void choosePhotoFromLocal() {
                        Toast.makeText(UploadImageDemoActivity.this, "选择本地图片", Toast.LENGTH_LONG).show();
                        mSetPhotoHelper.choosePhotoFromLocal();
                    }

                    @Override
                    public void takePhoto() {
                        Toast.makeText(UploadImageDemoActivity.this, "拍照", Toast.LENGTH_LONG).show();
                        mSetPhotoHelper.takePhoto(false);

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

                    File imageFile = mSetPhotoHelper.getFileBeforeCrop(data, 300, 200);
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    iv_display.setImageBitmap(bitmap);

                    UploadImageTools.uploadImage(imageFile, UploadImageDemoActivity.this,callback, false);

                    break;
                default:
                    Log.e("CLASS_TAG", "onActivityResult() 无对应");
            }


        } else {
            Log.e(CLASS_TAG, "操作取消");
        }
    }


    UploadImageTools.UploadCallBack callback = new UploadImageTools.UploadCallBack() {

        @Override
        public void onProcess(Object fileParam, long current, long total) {
            Toast.makeText(UploadImageDemoActivity.this, "onProcess " + current + "/" + total, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void success(String originUrl, String thumbUrl) {

            Log.e("上传图片", "原图" + originUrl);
            Log.e("上传图片", "缩略图" + thumbUrl);
        }

        @Override
        public void fail() {
            Log.e("上传图片", "上传失败");
        }
    };
}
