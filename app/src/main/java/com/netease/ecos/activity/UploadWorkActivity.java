package com.netease.ecos.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.utils.UploadImageTools;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/23.
 */
public class UploadWorkActivity extends Activity implements View.OnClickListener {

    private final String TAG = "Ecos---UploadWork";
    public static final String CourseId = "courseId";
    public static final String ImagePath = "image_path";
    private String courseId = "";
    private String image_path = "";
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    @InjectView(R.id.uploadWorkImgVw)
    ImageView imageView;
    @InjectView(R.id.uploadWorkEdTx)
    EditText uploadWorkEdTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_work_layout);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    void initData() {
        image_path = getIntent().getExtras().getString(ImagePath);
        courseId = getIntent().getExtras().getString(CourseId);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    void initView() {
        Bitmap bitmap = BitmapFactory.decodeFile(image_path);
        if (bitmap == null)
            Log.d(TAG, "bitmap is null");
        imageView.setImageBitmap(bitmap);
        //implementation on the title bar
        titleTxVw.setVisibility(View.INVISIBLE);
        rightButton.setText("发布");
        //set listener
        rightButton.setOnClickListener(this);
        backTxVw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right_action:
                if (uploadWorkEdTx.getText().toString().equals("")) {
                    Toast.makeText(UploadWorkActivity.this, UploadWorkActivity.this.getString(R.string.notFinished), Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO:send the content to the server.
                /*first upload the image to the cloud storage.
                * if upload success, it will return two urls.
                */
                File file = new File(image_path);
                UploadImageTools.uploadImageSys(file, new UploadWorkCallBack(), UploadWorkActivity.this, false);
                break;
            case R.id.tv_left:
                UploadWorkActivity.this.finish();
                break;
        }

    }

    class UploadWorkCallBack implements UploadImageTools.UploadCallBack {

        @Override
        public void success(String originUrl, String thumbUrl) {
            Log.d(TAG, "have got the url already.");
            String assignmentContent = uploadWorkEdTx.getText().toString();
            //TODO:upload the information to the server to store it.

            UploadWorkActivity.this.finish();
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
