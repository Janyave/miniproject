package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.PhotoAdappter;
import com.netease.ecos.model.PhotoAibum;
import com.netease.ecos.model.PhotoItem;

import java.util.ArrayList;

/**
 * Created by Think on 2015/7/26.
 */
public class PhotoActivity extends Activity {
    private GridView gv;
    private PhotoAibum aibum;
    private PhotoAdappter adapter;
    private TextView tv;
    private int chooseNum = 0;
    private Button btn_sure;
    private LayoutInflater inflater;
    private ImageView imageViewPhotoSelect;

    private ArrayList<String> paths = new ArrayList<String>();  // �����ѡ����Ƭ��ȫ·��
    private ArrayList<String> ids = new ArrayList<String>();    // �����ѡ����Ƭ��ID
    private ArrayList<PhotoItem> gl_arr = new ArrayList<PhotoItem>();   // ��ű�ѡ�е���Ƭ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoalbum_gridview);

        btn_sure = (Button) findViewById(R.id.btn_sure);
        aibum = (PhotoAibum) getIntent().getExtras().get("aibum");  // ����ʾ�����
        /**
         * ͳ���Ѿ�ѡ���ͼƬ����
         * */
        for (int i = 0; i < aibum.getBitList().size(); i++) {
            if (aibum.getBitList().get(i).isSelect()) {
                chooseNum++;
            }
        }
        gv = (GridView) findViewById(R.id.photo_gridview);

        adapter = new PhotoAdappter(PhotoActivity.this, aibum, null);
        gv.setAdapter(adapter);

        // ��Ӧ���ͼƬ���¼�
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoItem gridItem = aibum.getBitList().get(position);
                if (aibum.getBitList().get(position).isSelect()) {
                    aibum.getBitList().get(position).setSelect(false);
                    paths.remove(aibum.getBitList().get(position).getPath());
                    ids.remove(aibum.getBitList().get(position).getPhotoID() + "");
                    gl_arr.remove(aibum.getBitList().get(position));
                    chooseNum--;
                    gv.findViewWithTag(position).setVisibility(View.INVISIBLE);
                } else {
                    aibum.getBitList().get(position).setSelect(true);
                    ids.add(aibum.getBitList().get(position).getPhotoID() + "");
                    paths.add(aibum.getBitList().get(position).getPath());
                    gl_arr.add(aibum.getBitList().get(position));
                    chooseNum++;
                    /**
                     * ��photoAdapter��������getView�����У�Ϊÿһ��item��mSelect��ImageView��
                     * ����TagΪposition����ʱposition�ͳ�Ϊ��mSelect��������ʹ��
                     * gv.findViewWithTag(position)�������ɻ��mSelect���Ӷ�����ָ����ͼ
                     */
                    gv.findViewWithTag(position).setVisibility(View.VISIBLE);
                }
            }
        });
        // ��Ӧ�����ť���¼�
        btn_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoActivity.this, UploadWorksActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("paths", paths);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
