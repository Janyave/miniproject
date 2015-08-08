package com.netease.ecos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.netease.ecos.adapter.ExhibitListViewAdapter;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.course.PraiseRequest;
import com.netease.ecos.request.share.GetShareDetailRequest;
import com.netease.ecos.request.user.FollowUserRequest;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/8/1.
 */
public class DisplayDetailActivity extends BaseActivity implements View.OnTouchListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private final String TAG = "Ecos---ExhibitDetail";
    public static final String ShareId = "shareId";
    //widget in the title bar
    @InjectView(R.id.lly_right_action)
    LinearLayout title_right;
    @InjectView(R.id.tv_right_text)
    TextView title_right_text;
    @InjectView(R.id.tv_title)
    TextView title_text;
    @InjectView(R.id.lly_left_action)
    LinearLayout title_left;
    //widget in detail
    @InjectView(R.id.exhibitCoverImgVw)
    NetworkImageView exhibitCoverImgVw;
    @InjectView(R.id.exhibitPersonImgVw)
    NetworkImageView exhibitPersonImgVw;
    @InjectView(R.id.exhibitPersonNameTxVw)
    TextView exhibitPersonNameTxVw;
    @InjectView(R.id.exhibitFocusBtn)
    TextView exhibitFocusBtn;
    @InjectView(R.id.exhibitTitleTxVw)
    TextView exhibitTitleTxVw;
    @InjectView(R.id.exhibitTitleContentTxVw)
    TextView exhibitTitleContentTxVw;
    @InjectView(R.id.exhibitLsVw)
    ExtensibleListView exhibitListView;
    @InjectView(R.id.exhibitCommentLsVw)
    ExtensibleListView exhibitCommentLsVwLsVw;
    @InjectView(R.id.workDetailsCommentEdTx)
    EditText commentEdTx;
    @InjectView(R.id.favorBtn)
    LinearLayout favorBtn;
    @InjectView(R.id.iv_favor)
    ImageView iv_favor;
    @InjectView(R.id.tv_favor)
    TextView tv_favor;

    private ExhibitListViewAdapter exhibitListViewAdapter;
    private WorkDetailListViewAdapter workDetailListViewAdapter;
    //for NetWorkImageView
    static ImageLoader.ImageCache imageCache;
    RequestQueue queue;
    ImageLoader imageLoader;
    //for request
    private FollowUserRequest followUserRequest;
    private FollowResponce followResponce;
    private GetShareDetailRequest getShareDetailRequest;
    private GetShareDetealResponse getShareDetealResponse;

    private String shareId = "";
    private Share share;
    private PraiseRequest praiseRequest;
    private PraiseResponse praiseResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibit_detail_layout);
        ButterKnife.inject(this);
        initTitle();
        initData();
        initView();
    }

    private void initTitle() {
        title_left.setOnClickListener(this);
        title_right.setOnClickListener(this);
        title_right_text.setText("评论");
        title_text.setText("");
    }

    void initView() {
        //set adapter
        exhibitListView.setAdapter(exhibitListViewAdapter);
        exhibitCommentLsVwLsVw.setAdapter(workDetailListViewAdapter);
        //set listener
        commentEdTx.setOnTouchListener(this);
        favorBtn.setOnClickListener(this);
        exhibitCommentLsVwLsVw.setOnItemClickListener(this);
        exhibitFocusBtn.setOnClickListener(this);
        exhibitPersonImgVw.setOnClickListener(this);
        //always hide the keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    void initData() {
        shareId = getIntent().getExtras().getString(ShareId);
        //init the adapter
        exhibitListViewAdapter = new ExhibitListViewAdapter(this);
        workDetailListViewAdapter = new WorkDetailListViewAdapter(this, false);
        //for NetWorkImageView
        queue = MyApplication.getRequestQueue();
        imageCache = new SDImageCache();
        imageLoader = new ImageLoader(queue, imageCache);
        //request the data
        getShareDetailRequest = new GetShareDetailRequest();
        getShareDetealResponse = new GetShareDetealResponse();
        showProcessBar(getResources().getString(R.string.loading));
        getShareDetailRequest.request(getShareDetealResponse, shareId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        width -= 80;
        ViewGroup.LayoutParams params = exhibitCoverImgVw.getLayoutParams();
        params.height=width*2/3;
        exhibitCoverImgVw.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(DisplayDetailActivity.this, CommentDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CommentDetailActivity.FromId, shareId);
        bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.分享.getBelongs());
        bundle.putBoolean(CommentDetailActivity.IsPraised, share.hasPraised);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(DisplayDetailActivity.this, WriteContentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(CommentDetailActivity.FromId, share.shareId);
            bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.分享.getBelongs());
            intent.putExtras(bundle);
            startActivityForResult(intent, CommentDetailActivity.RequestCodeForComment);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lly_right_action:
                Intent intent = new Intent(DisplayDetailActivity.this, CommentDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(CommentDetailActivity.FromId, shareId);
                bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.分享.getBelongs());
                bundle.putBoolean(CommentDetailActivity.IsPraised, share.hasPraised);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.lly_left_action:
                DisplayDetailActivity.this.finish();
                break;
            case R.id.favorBtn:
                if (praiseRequest == null)
                    praiseRequest = new PraiseRequest();
                if (praiseResponse == null)
                    praiseResponse = new PraiseResponse();
                praiseRequest.praiseShare(praiseResponse, share.shareId, !share.hasPraised);
                break;
            case R.id.exhibitFocusBtn:
                if (followUserRequest == null)
                    followUserRequest = new FollowUserRequest();
                if (followResponce == null)
                    followResponce = new FollowResponce();
                followUserRequest.request(followResponce, share.userId, !share.hasAttention);
                break;
            case R.id.exhibitPersonImgVw:
                Intent intent1 = new Intent(DisplayDetailActivity.this, PersonageDetailActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putBoolean(PersonageDetailActivity.IsOwn, false);
                bundle1.putString(PersonageDetailActivity.UserID, share.userId);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
        }
    }

    void setFavorBtn() {
        if (share.hasPraised) {
            tv_favor.setText("已点赞");
            iv_favor.setImageResource(R.mipmap.ic_praise_fill);
        } else {
            tv_favor.setText("点赞");
            iv_favor.setImageResource(R.mipmap.ic_praise_block);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommentDetailActivity.RequestCodeForComment && resultCode == CommentDetailActivity.ResultCodeForComment) {
            showProcessBar(getResources().getString(R.string.loading));
            getShareDetailRequest.request(getShareDetealResponse, shareId);
        }
    }

    class GetShareDetealResponse extends BaseResponceImpl implements GetShareDetailRequest.IGetShareResponse {

        @Override
        public void doAfterFailedResponse(String message) {
            dismissProcessBar();
            Toast.makeText(DisplayDetailActivity.this, "error happens:" + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            dismissProcessBar();
        }

        @Override
        public void success(Share share) {
            dismissProcessBar();
            DisplayDetailActivity.this.share = share;
            exhibitCoverImgVw.setImageUrl(share.coverUrl, imageLoader);
            exhibitPersonImgVw.setImageUrl(share.avatarUrl, imageLoader);
            exhibitPersonNameTxVw.setText(share.nickname);
            exhibitFocusBtn.setText(share.hasAttention ? DisplayDetailActivity.this.getString(R.string.focus) : DisplayDetailActivity.this.getString(R.string.notFocus));
            exhibitTitleTxVw.setText(share.title);
            exhibitTitleContentTxVw.setText(share.content);
            exhibitListViewAdapter.updateDataList(share.imageList);
            workDetailListViewAdapter.setCommentCount(share.commentNum);
            workDetailListViewAdapter.updateCommentList(share.commentList);
            setFavorBtn();
        }
    }


    class FollowResponce extends BaseResponceImpl implements FollowUserRequest.IFollowResponce {

        @Override
        public void doAfterFailedResponse(String message) {
            Toast.makeText(DisplayDetailActivity.this, "error happens:" + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(String userId, boolean follow) {
            share.hasAttention = follow;
            exhibitFocusBtn.setText(share.hasAttention ? DisplayDetailActivity.this.getString(R.string.focus) : DisplayDetailActivity.this.getString(R.string.notFocus));
        }
    }

    class PraiseResponse extends BaseResponceImpl implements PraiseRequest.IPraiseResponce {

        @Override
        public void success(String userId, boolean praise) {
            share.hasPraised = !share.hasPraised;
            setFavorBtn();
        }

        @Override
        public void doAfterFailedResponse(String message) {
            Toast.makeText(DisplayDetailActivity.this, "error happens:" + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
}
