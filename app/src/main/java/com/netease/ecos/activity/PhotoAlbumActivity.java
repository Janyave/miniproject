package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.PhotoAibumAdapter;
import com.netease.ecos.model.PhotoAibum;
import com.netease.ecos.model.PhotoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Think on 2015/7/26.
 */
public class PhotoAlbumActivity extends Activity {
    private GridView aibumGV;
    private List<PhotoAibum> aibumList;

    // 设置获取图片的字段信息
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DISPLAY_NAME, // 显示的名称
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.LONGITUDE, // 经度
            MediaStore.Images.Media._ID, // id
            MediaStore.Images.Media.BUCKET_ID, // 目录
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME // 目录名字
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoalbum);

//		for(int i=0;i<STORE_IMAGES.length;i++)
//			System.out.println(STORE_IMAGES[i]);

        aibumGV = (GridView) findViewById(R.id.album_gridview);
        aibumList = getPhotoAlbum();
        aibumGV.setAdapter(new PhotoAibumAdapter(aibumList, PhotoAlbumActivity.this));
        aibumGV.setOnItemClickListener(aibumClickListener);
    }

    /**
     * 相册点击事件
     */
    AdapterView.OnItemClickListener aibumClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            Intent intent = new Intent(PhotoAlbumActivity.this, PhotoActivity.class);
            intent.putExtra("aibum", aibumList.get(position));  // 使用Intent传入被点击的相册
            startActivity(intent);
            finish();
        }
    };

    /**
     * 方法描述：获取各个相册信息
     */
    private List<PhotoAibum> getPhotoAlbum() {
        List<PhotoAibum> aibumList = new ArrayList<PhotoAibum>();
        Cursor cursor = MediaStore.Images.Media.query(getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
        Map<String, PhotoAibum> countMap = new HashMap<String, PhotoAibum>();
        PhotoAibum pa = null;
        /**
         * while函数遍历SD卡上的每张照片并归类到所属相册中
         */
        while (cursor.moveToNext()) {
            String path = cursor.getString(1);
            Log.w("Path", path);
            String id = cursor.getString(3);
            String dir_id = cursor.getString(4);
            String dir = cursor.getString(5);
            Log.e("info", "id===" + id + "==dir_id==" + dir_id + "==dir==" + dir + "==path=" + path);
            if (!countMap.containsKey(dir_id)) {
                pa = new PhotoAibum();
                pa.setName(dir);
                pa.setPathID(path);
                pa.setAibumID(Integer.parseInt(id));
                pa.setCount("1");
                pa.getBitList().add(new PhotoItem(Integer.valueOf(id), path));
                countMap.put(dir_id, pa);
            } else {
                pa = countMap.get(dir_id);
                pa.setCount(String.valueOf(Integer.parseInt(pa.getCount()) + 1));   // 相册中相片数量加一
                pa.getBitList().add(new PhotoItem(Integer.valueOf(id), path));      // 新增相片到已有相册中
            }
        }
        cursor.close();
        Iterable<String> it = countMap.keySet();
        for (String key : it) {
            aibumList.add(countMap.get(key));
        }
        return aibumList;   // 返回统计的相册列表
    }
}
