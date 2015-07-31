package com.netease.ecos.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.utils.UploadImageTools;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/23.
 */
public class UploadWorkActivity extends Activity {

    private final String TAG = "Ecos---UploadWork";
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    @InjectView(R.id.uploadWorkImgVw)
    ImageView imageView;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_work_layout);
        ButterKnife.inject(this);
        init();
    }

    void init() {
        Bundle bundle = getIntent().getExtras();
        imagePath = bundle.getString("img_path");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (bitmap == null)
            Log.d(TAG, "bitmap is null");
        imageView.setImageBitmap(bitmap);
        //implementation on the title bar
        titleTxVw.setVisibility(View.INVISIBLE);
        rightButton.setText("发布");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:send the content to the server.
                /*first upload the image to the cloud storage.
                * if upload success, it will return two urls.
                 */
                File file = new File(imagePath);
                UploadImageTools.uploadImageSys(file, new UploadWorkCallBack(), UploadWorkActivity.this, false);
            }
        });
        backTxVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadWorkActivity.this.finish();
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    class UploadWorkCallBack implements UploadImageTools.UploadCallBack {

        @Override
        public void success(String originUrl, String thumbUrl) {
            //then upload the information to the server to store it.
            Log.d(TAG, "have got the url already.");
        }

        @Override
        public void fail() {
            Log.e("test", "failed to upload the image.");
        }

        @Override
        public void onProcess(Object fileParam, long current, long total) {
            Log.i(TAG, "总数" + total + "  ," + "已上传" + current);
        }

    }
}
