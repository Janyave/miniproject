package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.netease.ecos.R;
import com.netease.ecos.adapter.ExhibitListViewAdapter;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/23.
 */
public class ExhibitDetailActivity extends Activity {

    @InjectView(R.id.exhibitLsVw)
    ExtensibleListView exhibitListView;
    @InjectView(R.id.exhibitCommentLsVw)
    ExtensibleListView exhibitCommentLsVwLsVw;
    @InjectView(R.id.workDetailsCommentEdTx)
    EditText commentEdTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibit_detail_layout);
        ButterKnife.inject(this);
        exhibitListView.setAdapter(new ExhibitListViewAdapter(this));
        exhibitCommentLsVwLsVw.setAdapter(new WorkDetailListViewAdapter(this));
        exhibitCommentLsVwLsVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ExhibitDetailActivity.this, CommentDetailActivity.class);
                startActivity(intent);
            }
        });
        commentEdTx.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(ExhibitDetailActivity.this, WriteContentActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}

