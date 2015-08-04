package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.netease.ecos.R;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;
import com.netease.ecos.model.Comment;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.comment.CommentListRequest;
import com.netease.ecos.utils.SDImageCache;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/23.
 */
public class CommentDetailActivity extends Activity implements View.OnTouchListener, View.OnClickListener {
    private static final String TAG = "Ecos---CommentDetail";
    public static final String FromId = "fromId";
    public static final String CommentType = "commentType";
    public static final String CommentContent = "CommentContent";
    private String fromId = "";
    private Comment.CommentType commentType;
    @InjectView(R.id.commentLsVw)
    ListView commentListView;
    @InjectView(R.id.workDetailsCommentEdTx)
    EditText commentEdTx;
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;

    //for NetWorkImageView
    static ImageLoader.ImageCache imageCache;
    RequestQueue queue;
    ImageLoader imageLoader;
    //for request
    private CommentListRequest commentListRequest;
    private GetCommentListResponse getCommentListResponse;

    private WorkDetailListViewAdapter workDetailListViewAdapter;


    public static final int RequestCodeForComment = 1;
    public static final int ResultCodeForComment = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_comment_detail);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    void initData() {
        fromId = getIntent().getExtras().getString(FromId);
        commentType = Comment.CommentType.getCommentTypeByValue(getIntent().getExtras().getString(CommentType));
        //init the data for NetWorkImageView
        queue = MyApplication.getRequestQueue();
        imageCache = new SDImageCache();
        imageLoader = new ImageLoader(queue, imageCache);
        //init the adapter
        workDetailListViewAdapter = new WorkDetailListViewAdapter(this);

        commentListRequest = new CommentListRequest();
        getCommentListResponse = new GetCommentListResponse();
        Comment comment = new Comment();
        comment.commentType = commentType;
        comment.commentTypeId = fromId;
        commentListRequest.request(getCommentListResponse, comment, 1);
    }

    void initView() {
        //implementation on the title bar
        titleTxVw.setText("评论");
        rightButton.setVisibility(View.INVISIBLE);
        //always hide the keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //set adapter
        commentListView.setAdapter(workDetailListViewAdapter);
        //set listener
        commentEdTx.setOnTouchListener(this);
        backTxVw.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeForComment && resultCode == ResultCodeForComment) {
            Comment comment = new Comment();
            comment.commentType = commentType;
            comment.commentTypeId = fromId;
            commentListRequest.request(getCommentListResponse, comment, 1);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(CommentDetailActivity.this, WriteContentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(CommentType, commentType.getBelongs());
            bundle.putString(FromId, fromId);
            intent.putExtras(bundle);
            startActivityForResult(intent, RequestCodeForComment);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        CommentDetailActivity.this.finish();
    }

    class GetCommentListResponse extends BaseResponceImpl implements CommentListRequest.ICommentListResponse {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(List<Comment> commentList) {
            workDetailListViewAdapter.updateCommentList(commentList);
        }
    }


}
