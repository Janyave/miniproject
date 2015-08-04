package com.netease.ecos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.netease.ecos.R;
import com.netease.ecos.model.Comment;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.comment.CreateCommentRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/23.
 */
public class WriteContentActivity extends Activity {
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    @InjectView(R.id.commentEdTx)
    EditText commentEdTx;

    private CreateCommentRequest createCommentRequest;
    private UploadCommentResponse response;
    private Comment.CommentType commentType;
    private String fromId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_content_layout);
        ButterKnife.inject(this);
        //implementation on the title bar
        titleTxVw.setText("添加评论");
        rightButton.setText("发送");
        commentType = Comment.CommentType.getCommentTypeByValue(getIntent().getExtras().getString(CommentDetailActivity.CommentType));
        fromId = getIntent().getExtras().getString(CommentDetailActivity.FromId);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = commentEdTx.getText().toString();
                if (content.equals(""))
                    Toast.makeText(WriteContentActivity.this, getResources().getString(R.string.noComment), Toast.LENGTH_SHORT).show();
                Comment comment = new Comment();
                comment.content = content;
                comment.commentType = commentType;
                Log.d("test", commentType.getBelongs());
                comment.commentTypeId = fromId;

                if (createCommentRequest == null)
                    createCommentRequest = new CreateCommentRequest();
                if (response == null)
                    response = new UploadCommentResponse();
                createCommentRequest.request(response, comment);
            }
        });
        backTxVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    class UploadCommentResponse extends BaseResponceImpl implements CreateCommentRequest.ICreateCommentResponse {

        @Override
        public void doAfterFailedResponse(String message) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void success(Comment comment) {
            Toast.makeText(WriteContentActivity.this, getResources().getString(R.string.commentSuccess), Toast.LENGTH_SHORT).show();
            setResult(CommentDetailActivity.ResultCodeForComment);
            finish();
        }
    }
}
