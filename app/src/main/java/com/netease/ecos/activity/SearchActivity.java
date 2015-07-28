package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.SearchHistoryAdapter;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/27.
 */
public class SearchActivity extends Activity{

    @InjectView(R.id.et_search)
    EditText et_search;
    @InjectView(R.id.tv_cancel)
    TextView tv_cancel;
    @InjectView(R.id.lv_searchHistory)
    ExtensibleListView lv_searchHistory;

    private SearchHistoryAdapter searchHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv_searchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO 搜索历史点击事件
            }
        });
    }

    private void initData() {
        searchHistoryAdapter=new SearchHistoryAdapter(this);
        lv_searchHistory.setAdapter(searchHistoryAdapter);
        lv_searchHistory.setDividerHeight(1);
    }
}
