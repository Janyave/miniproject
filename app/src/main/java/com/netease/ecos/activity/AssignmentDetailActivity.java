package com.netease.ecos.activity;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by Think on 2015/8/1.
 */
public class AssignmentDetailActivity extends BaseActivity implements View.OnTouchListener, AdapterView.OnItemClickListener, View.OnClickListener, GestureOverlayView.OnGesturePerformedListener {

    private final String TAG = "Ecos---WorkDetail";
    public static final String Work_ID = "workId";
    public static final String Work_List = "workList";
    public static final String Work_Order = "workOrder";
    //to record the work id.
    private String workID = "";
    //to record the list of works
    private ArrayList<String> workList;
    //to record the order of the current work.
    private int workOrder;
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
    LinearLayout favorBtn;
    @InjectView(R.id.iv_favor)
    ImageView iv_favor;
    @InjectView(R.id.tv_favor)
    TextView tv_favor;

    private WorkDetailListViewAdapter workDetailListViewAdapter;
    private GestureLibrary library;
    private final String RIGHT = "right";
    private final String LEFT = "left";

    //for NetWorkImageView
    static ImageLoader.ImageCache imageCache;
    RequestQueue queue;
    ImageLoader imageLoader;

    //request
    private GetAssignmentDetailRequest request;
    private GetAssignmentDetailResponse assignmentDetailResponse;

    private Course.Assignment assignment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.work_detail_layout);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    void initData() {
        workID = getIntent().getExtras().getString(Work_ID);
        if (workID == null) {
            workList = getIntent().getExtras().getStringArrayList(Work_List);
            workOrder = getIntent().getExtras().getInt(Work_Order);
            for (int i = 0; i < workList.size(); i++)
                Log.d(TAG, workList.get(i));
            workID = workList.get(workOrder);
        }
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
        request = new GetAssignmentDetailRequest();
        assignmentDetailResponse = new GetAssignmentDetailResponse();
        Log.d(TAG, "work id:" + workID);
        request.request(assignmentDetailResponse, workID);

    }

    void initView() {
        //implementation on the title bar
        titleTxVw.setText((workOrder + 1) + "/" + workList.size());
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(AssignmentDetailActivity.this, WriteContentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.作业.getBelongs());
            bundle.putString(CommentDetailActivity.FromId, assignment.assignmentId);
            intent.putExtras(bundle);
            startActivityForResult(intent, CommentDetailActivity.RequestCodeForComment);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorBtn:
                //TODO:send the favor message to the server.

                if (TextUtils.equals(tv_favor.getText().toString(),"点赞")){
                    tv_favor.setText("已点赞");
                    iv_favor.setImageResource(R.mipmap.ic_praise_fill);
                }else {
                    tv_favor.setText("点赞");
                    iv_favor.setImageResource(R.mipmap.ic_praise_block);
                }

                break;
            case R.id.tv_left:
                AssignmentDetailActivity.this.finish();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(AssignmentDetailActivity.this, CommentDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.作业.getBelongs());
        bundle.putString(CommentDetailActivity.FromId, workList.get(workOrder));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommentDetailActivity.RequestCodeForComment && resultCode == CommentDetailActivity.ResultCodeForComment) {
            Comment comment = new Comment();
            comment.commentType = Comment.CommentType.作业;
            comment.commentTypeId = assignment.assignmentId;
//            commentListRequest.request(getCommentListResponse, comment, 1);
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
                    if (workOrder == 0) {
                        Toast.makeText(AssignmentDetailActivity.this, AssignmentDetailActivity.this.getString(R.string.alreadyFirstAssignment), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(AssignmentDetailActivity.this, AssignmentDetailActivity.this.getString(R.string.loadingLastAssignment), Toast.LENGTH_SHORT).show();
                    workOrder--;
                    titleTxVw.setText((workOrder + 1) + "/" + workList.size());
                } else if (LEFT.equals(prediction.name)) {
                    if (workOrder == workList.size() - 1) {
                        Toast.makeText(AssignmentDetailActivity.this, AssignmentDetailActivity.this.getString(R.string.alreadyLastAssignment), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(AssignmentDetailActivity.this, AssignmentDetailActivity.this.getString(R.string.loadingNextAssignment), Toast.LENGTH_SHORT).show();
                    workOrder++;
                    titleTxVw.setText((workOrder + 1) + "/" + workList.size());
                }
            }
        }
        workID = workList.get(workOrder);
        request.request(assignmentDetailResponse, workID);
    }

    /**
     * @ClassName: GetAssignmetnDetailResponse
     * @Description: 获取作业详情
     */
    class GetAssignmentDetailResponse extends BaseResponceImpl implements GetAssignmentDetailRequest.IAssignmentDetailRespnce {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(Course.Assignment assignment, List<Comment> commentList) {
            AssignmentDetailActivity.this.assignment = assignment;
            //set the work image.
            networkImageView.setImageUrl(assignment.imageUrl, imageLoader);
            personPicImgView.setImageUrl(assignment.authorAvatarUrl, imageLoader);
            personNameTxV.setText(assignment.author);
            workDetailDate.setText(assignment.getDateDescription());
            workDetailDescpTxVw.setText(assignment.content);
            workDetailFavorTxVw.setText(assignment.praiseNum + AssignmentDetailActivity.this.getString(R.string.favorCount));
            workDetailListViewAdapter.updateCommentList(commentList);
        }
    }
}