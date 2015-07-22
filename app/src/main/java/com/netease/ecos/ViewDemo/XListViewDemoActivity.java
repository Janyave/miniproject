package com.netease.ecos.ViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.views.XListView;


/**
 * 类描述：{@see XListView使用演示}
 * Created by enlizhang on 2015/7/21.
 */
public class XListViewDemoActivity extends Activity implements XListView.IXListViewListener {

    XListView xListView;

    public String datas[]={"1", "2", "3", "2", "5", "6", "7", "8"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_xlistview);

        xListView = (XListView) findViewById(R.id.xlv_demo);
        xListView.setAdapter(new XListViewAdapter(XListViewDemoActivity.this, datas));
        xListView.initRefleshTime(this.getClass().getSimpleName());

        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
    }

    @Override
    public void onRefresh() {

        Toast.makeText(this,"下拉刷新",Toast.LENGTH_SHORT).show();
        //1秒后关闭刷新
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                xListView.stopRefresh();
            }
        }, 1000);


    }

    @Override
    public void onLoadMore()
    {
        Toast.makeText(this,"上拉加载",Toast.LENGTH_SHORT).show();

        //1秒后关闭加载
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                xListView.stopLoadMore();
            }
        }, 1000);
    }



}
