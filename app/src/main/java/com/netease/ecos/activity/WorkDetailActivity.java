package com.netease.ecos.activity;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;
import com.netease.ecos.views.ExtensibleListView;

import java.util.ArrayList;

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
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    @InjectView(R.id.gestureView)
    GestureOverlayView gestureOverlayView;

    private WorkDetailListViewAdapter workDetailListViewAdapter;
    private GestureLibrary library;
    private final String RIGHT = "right";
    private final String LEFT = "left";

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
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(WorkDetailActivity.this, WriteContentActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        //implementation on the title bar
        titleTxVw.setText("1/20");
        rightButton.setVisibility(View.INVISIBLE);
        backTxVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkDetailActivity.this.finish();
            }
        });
        //code for gesture
        library = GestureLibraries.fromRawResource(this, R.raw.gesture);
        library.load();
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                /* 查找手势库中与用户画的手势库有些相似性的手势集合
                 * 并按照相似性度高到低排序，与用户画的图形最相似的手势，放在集合第一个位置
                 * */
                ArrayList<Prediction> predictions = library.recognize(gesture);
                Prediction prediction;
                for (int i = 0; i < predictions.size(); i++) {
                    prediction = predictions.get(i);
                    // 匹配的手势
                    if (prediction.score > 1.0) {
                        if (RIGHT.equals(prediction.name)) {
                            Toast.makeText(WorkDetailActivity.this, "move to last work.", Toast.LENGTH_LONG).show();
                        } else if (LEFT.equals(prediction.name)) {
                            Toast.makeText(WorkDetailActivity.this, "move to next work.", Toast.LENGTH_LONG).show();
                        } else {
                        }
                    }
                }
            }
        });

    }
}
