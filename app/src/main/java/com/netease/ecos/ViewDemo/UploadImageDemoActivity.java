package com.netease.ecos.ViewDemo;

import android.os.Bundle;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.BaseActivity;

/**
 * 类描述：{@see UploadImageTools使用演示}
 * Created by enlizhang on 2015/7/23.
 */
public class UploadImageDemoActivity extends BaseActivity {

    TextView tv_display;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.demo_upload_file);

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
    }
}
