package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.NewDisplayListAdater;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/31.
 */
public class NewRecruitmentActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "Ecos---NewRecruitment";

    @InjectView(R.id.displayListView)
    ExtensibleListView displayLsVw;
    private NewDisplayListAdater newDisplayListAdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recruitment_layout);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    void initView() {
        //set adapter
        displayLsVw.setAdapter(newDisplayListAdater);
        //set listener
        displayLsVw.setOnItemClickListener(this);
    }

    void initData() {
        //init the adapter
        newDisplayListAdater = new NewDisplayListAdater(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO:set checkbox
    }
}
