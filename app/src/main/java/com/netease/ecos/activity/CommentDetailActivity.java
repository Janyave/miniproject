package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.netease.ecos.R;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/23.
 */
public class CommentDetailActivity extends Activity {
    @InjectView(R.id.commentLsVw)
    ListView commentListView;
    @InjectView(R.id.workDetailsCommentEdTx)
    EditText commentEdTx;

    private WorkDetailListViewAdapter workDetailListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_comment_detail);
        ButterKnife.inject(this);
        workDetailListViewAdapter = new WorkDetailListViewAdapter(this);
        commentListView.setAdapter(workDetailListViewAdapter);
        commentEdTx.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("test", "work details ontouch:" + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(CommentDetailActivity.this, WriteContentActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
