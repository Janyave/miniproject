package com.netease.ecos.activity;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.netease.ecos.R;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Course;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.course.GetAssignmentDetailRequest;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.views.ExtensibleListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/22.
 */
public class WorkDetailActivity extends BaseActivity implements View.OnTouchListener, AdapterView.OnItemClickListener, View.OnClickListener, GestureOverlayView.OnGesturePerformedListener {

    private final String TAG = "Ecos---WorkDetail";
    //widget for gesture
    @InjectView(R.id.gestureView)
    GestureOverlayView gestureOverlayView;
    //widget in the title bar
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    //widget in details
    @InjectView(R.id.workDetailImgVw)
    NetworkImageView networkImageView;
    @InjectView(R.id.personPicImgView)
    NetworkImageView personPicImgView;
    @InjectView(R.id.personNameTxV)
    TextView personNameTxV;
    @InjectView(R.id.workDetailDate)
    TextView workDetailDate;
    @InjectView(R.id.workDetailDescpTxVw)
    TextView workDetailDescpTxVw;
    @InjectView(R.id.workDetailFavorTxVw)
    TextView workDetailFavorTxVw;
    @InjectView(R.id.workDetailsLsVw)
    ExtensibleListView commentListView;
    @InjectView(R.id.workDetailsCommentEdTx)
    EditText commentEdTx;
    @InjectView(R.id.favorBtn)
    Button favorBtn;


    private WorkDetailListViewAdapter workDetailListViewAdapter;
    private GestureLibrary library;
    private final String RIGHT = "right";
    private final String LEFT = "left";

    //for NetWorkImageView
    static ImageLoader.ImageCache imageCache;
    RequestQueue queue;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.work_detail_layout);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    void initData() {
        //code for gesture
        library = GestureLibraries.fromRawResource(this, R.raw.gesture);
        library.load();
        //always hide the keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //init the adapter
        workDetailListViewAdapter = new WorkDetailListViewAdapter(this);
        //init the data for NetWorkImageView
        queue = MyApplication.getRequestQueue();
        imageCache = new SDImageCache();
        imageLoader = new ImageLoader(queue, imageCache);
        //get the work detail from the server
        GetAssignmentDetailRequest request = new GetAssignmentDetailRequest();
        request.request(new GetAssignmetnDetailResponse(), "1");

    }

    void initView() {
        //implementation on the title bar
        titleTxVw.setText("1/20");
        rightButton.setVisibility(View.INVISIBLE);

        //set the default image for NetWorkImageView
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        //set the error image for NetWorkImageView
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);

        //setAdapter
        commentListView.setAdapter(workDetailListViewAdapter);

        //add listener
        commentListView.setOnItemClickListener(this);
        commentEdTx.setOnTouchListener(this);
        backTxVw.setOnClickListener(this);
        favorBtn.setOnClickListener(this);
        gestureOverlayView.addOnGesturePerformedListener(this);
    }

    /**
     * @ClassName: GetAssignmetnDetailResponse
     * @Description: 获取作业详情
     */
    class GetAssignmetnDetailResponse extends BaseResponceImpl implements GetAssignmentDetailRequest.IAssignmentDetailRespnce {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(Course.Assignment assignment, List<Comment> commentList) {
            //set the work image.
            Log.d(TAG, "assignment.imageUrl:" + assignment.imageUrl);
            networkImageView.setImageUrl(assignment.imageUrl, imageLoader);
            personPicImgView.setImageUrl(assignment.authorAvatarUrl, imageLoader);
            personNameTxV.setText(assignment.author);
            workDetailDate.setText(assignment.getDateDescription());
            workDetailDescpTxVw.setText(assignment.content);
            workDetailFavorTxVw.setText(assignment.praiseNum + WorkDetailActivity.this.getString(R.string.favorCount));
            workDetailListViewAdapter.updateCommentList(commentList);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(WorkDetailActivity.this, CommentDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(WorkDetailActivity.this, WriteContentActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorBtn:
                //TODO:send the favor message to the server.
                break;
            case R.id.tv_left:
                WorkDetailActivity.this.finish();
                break;
        }

    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = library.recognize(gesture);
        Prediction prediction;
        for (int i = 0; i < predictions.size(); i++) {
            prediction = predictions.get(i);
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
}
