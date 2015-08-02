package com.netease.ecos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.adapter.UploadWorksListAdapter;
import com.netease.ecos.dialog.SetPhotoDialog;
import com.netease.ecos.model.Image;
import com.netease.ecos.utils.SetPhotoHelper;
import com.netease.ecos.utils.UploadImageTools;
import com.netease.ecos.views.ExtensibleListView;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/8/1.
 */
public class UploadDisplayActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "Ecos---UploadWorks";
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
    @InjectView(R.id.uploadWorksCoverEdTx)
    EditText uploadWorksCoverEdTx;
    @InjectView(R.id.uploadWorksDescrpEdTx)
    EditText uploadWorksDescrpEdTx;

    private UploadWorksListAdapter uploadWorksListAdapter;
    /*
    to record the images' path not including the cover image path
     */
    private ArrayList<String> imagePaths;
    /*
    to record the cover image path.
     */
    private String coverImagePath = "";
    /*
    to record the returned image urls responding to different types of image, such as cover or details.
     */
    private ArrayList<Image> imagesArraylist;
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
        imagePaths = getIntent().getExtras().getStringArrayList("paths");
        uploadWorksListAdapter = new UploadWorksListAdapter(this, imagePaths);
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
                                    coverImagePath = imagePath;
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
                if (coverImagePath.equals("")
                        || uploadWorksCoverEdTx.getText().toString().equals("")
                        || uploadWorksDescrpEdTx.getText().toString().equals("")) {
                    Toast.makeText(UploadDisplayActivity.this, "请填写完所有内容:)", Toast.LENGTH_SHORT).show();
                    return;
                }
                imagesArraylist = new ArrayList<>();
                //TODO: to check whether it has finished all the item. if not, it can not be uploaded.
                for (int i = 0; i < imagePaths.size(); i++) {
                    File file = new File(imagePaths.get(i));
                    UploadImageTools.uploadImageSys(file, new UploadWorksCallbacks(Image.ImageType.detailImage), UploadDisplayActivity.this, false);
                }
                File file = new File(coverImagePath);
                UploadImageTools.uploadImageSys(file, new UploadWorksCallbacks(Image.ImageType.coverImage), UploadDisplayActivity.this, false);
                break;
            case R.id.tv_left:
                UploadDisplayActivity.this.finish();
                break;
            case R.id.uploadWorksCoverImgVw:
                SetPhotoDialog dialog = new SetPhotoDialog(UploadDisplayActivity.this, new SetPhotoDialog.ISetPhoto() {

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

    /*
    to record how many images have been uploaded already.
    every time it return success, add one to count.
    upload all the urls until the count up to imagePaths.size()+1.
     */
    private Integer count = 0;

    class UploadWorksCallbacks implements UploadImageTools.UploadCallBack {
        private Image.ImageType imageType;

        public UploadWorksCallbacks(Image.ImageType imageType) {
            this.imageType = imageType;
        }

        @Override
        public void onProcess(Object fileParam, long current, long total) {
        }

        @Override
        public void fail() {
            Log.e(TAG, "uploadWorksFailed.");
        }

        @Override
        public void success(String originUrl, String thumbUrl) {
            Log.d(TAG, "uploadworksSuccess");
            synchronized (count) {
                count++;
                Log.d(TAG, "image " + count + " has been uploaded");
                if (count == (imagePaths.size() + 1)) {
                    Log.d(TAG, "all the images has been uploaded successfully.");
                }
            }
            Image image = new Image();
            image.type = imageType;
            image.originUrl = originUrl;
            image.thumbUrl = thumbUrl;
            imagesArraylist.add(image);

        }
    }
}

