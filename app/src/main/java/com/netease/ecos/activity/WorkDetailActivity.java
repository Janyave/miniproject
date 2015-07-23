package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.netease.ecos.R;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/22.
 */
public class WorkDetailActivity extends BaseActivity {
    @InjectView(R.id.workDetailsLsVw)
    ExtensibleListView commentListView;
    @InjectView(R.id.workDetailsCommentEdTx)
    EditText commentEdTx;

    private WorkDetailListViewAdapter workDetailListViewAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.work_detail_layout);
        ButterKnife.inject(this);
        workDetailListViewAdapter = new WorkDetailListViewAdapter(this);
        commentListView.setAdapter(workDetailListViewAdapter);
        commentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WorkDetailActivity.this, CommentDetailActivity.class);
                startActivity(intent);
            }
        });
        commentEdTx.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("test", "work details ontouch:" + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(WorkDetailActivity.this, WriteContentActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }
}
