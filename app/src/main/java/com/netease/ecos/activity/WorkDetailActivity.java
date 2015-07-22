package com.netease.ecos.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/22.
 */
public class WorkDetailActivity extends BaseActivity {
    @InjectView(R.id.workDetailsLsVw)
    ListView commentListView;

    private WorkDetailListViewAdapter workDetailListViewAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.work_detail_layout);
        ButterKnife.inject(this);
        workDetailListViewAdapter = new WorkDetailListViewAdapter(this);
        commentListView.setAdapter(workDetailListViewAdapter);
//        commentListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction()){
//                    case MotionEvent.ACTION_MOVE:
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });
    }
}
