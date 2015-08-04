package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.adapter.CourseListViewAdapter;
import com.netease.ecos.adapter.DisplayListViewAdapter;
import com.netease.ecos.adapter.SearchHistoryAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hzjixinyu on 2015/7/27.
 */
public class SearchActivity extends Activity {

    private static final String TAG = "Ecos---Search";
    public static final String SearchWord = "SearchWord";

    @InjectView(R.id.et_search)
    EditText et_search;
    @InjectView(R.id.tv_confirm)
    TextView tv_confirm;
    @InjectView(R.id.lv_searchHistory)
    ListView lv_searchHistory;

    @InjectView(R.id.ll_searchType)
    LinearLayout ll_searchType;
    @InjectView(R.id.tv_searchType)
    TextView tv_searchType;
    @InjectView(R.id.iv_searchType)
    ImageView iv_searchType;

    private SearchHistoryAdapter searchHistoryAdapter;

    //result adapter
    private CourseListViewAdapter courseListViewAdapter;
    private DisplayListViewAdapter displayListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        initListener();
        initData();
    }

    private void initListener() {
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(SearchWord, et_search.getText().toString());
                getIntent().putExtras(bundle);
                setResult(CourseCategoryActivity.ResultCodeForSearch, getIntent());
                finish();
            }
        });

        lv_searchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(SearchWord, ((SearchHistoryAdapter.SearchHistoryViewHolder) view.getTag()).tv_search.getText().toString());
                getIntent().putExtras(bundle);
                setResult(CourseCategoryActivity.ResultCodeForSearch, getIntent());
                finish();
            }
        });

        ll_searchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Toast.makeText(SearchActivity.this, "choose Dialog", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        searchHistoryAdapter = new SearchHistoryAdapter(this);
        lv_searchHistory.setAdapter(searchHistoryAdapter);
    }
}
