package com.netease.ecos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.netease.ecos.R;
import com.netease.ecos.adapter.ExhibitListViewAdapter;
import com.netease.ecos.adapter.WorkDetailListViewAdapter;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.share.GetShareDetailRequest;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.views.ExtensibleListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Think on 2015/7/23.
 */
public class ExhibitDetailActivity extends Activity implements View.OnTouchListener, AdapterView.OnItemClickListener, View.OnClickListener {

    //widget in the title bar
    @InjectView(R.id.tv_title)
    TextView titleTxVw;
    @InjectView(R.id.btn_right_action)
    Button rightButton;
    @InjectView(R.id.tv_left)
    TextView backTxVw;
    //widget in detail
    @InjectView(R.id.exhibitCoverImgVw)
    NetworkImageView exhibitCoverImgVw;
    @InjectView(R.id.exhibitPersonImgVw)
    NetworkImageView exhibitPersonImgVw;
    @InjectView(R.id.exhibitPersonNameTxVw)
    TextView exhibitPersonNameTxVw;
    @InjectView(R.id.exhibitFocusBtn)
    Button exhibitFocusBtn;
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
    Button favorBtn;


    private ExhibitListViewAdapter exhibitListViewAdapter;
    private WorkDetailListViewAdapter workDetailListViewAdapter;
    //for NetWorkImageView
    static ImageLoader.ImageCache imageCache;
    RequestQueue queue;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibit_detail_layout);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    void initView() {
        //implementation on the title bar
        titleTxVw.setVisibility(View.INVISIBLE);
        rightButton.setText("99+评论");
        //set adapter
        exhibitListView.setAdapter(exhibitListViewAdapter);
        exhibitCommentLsVwLsVw.setAdapter(workDetailListViewAdapter);
        //set listener
        commentEdTx.setOnTouchListener(this);
        favorBtn.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        backTxVw.setOnClickListener(this);
        exhibitCommentLsVwLsVw.setOnItemClickListener(this);
        //always hide the keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    void initData() {
        //init the adapter
        exhibitListViewAdapter = new ExhibitListViewAdapter(this);
        workDetailListViewAdapter = new WorkDetailListViewAdapter(this);
        //for NetWorkImageView
        queue = MyApplication.getRequestQueue();
        imageCache = new SDImageCache();
        imageLoader = new ImageLoader(queue, imageCache);
        //request the data
        GetShareDetailRequest request = new GetShareDetailRequest();
        request.request(new GetShareDetealResponse(), "0");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        width -= 80;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width * 4 / 3);
        exhibitCoverImgVw.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ExhibitDetailActivity.this, CommentDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(ExhibitDetailActivity.this, WriteContentActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right_action:
                Intent intent = new Intent(ExhibitDetailActivity.this, CommentDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_left:
                ExhibitDetailActivity.this.finish();
                break;
            case R.id.favorBtn:
                //TODO:send the favor information to the server.
                break;
        }
    }

    class GetShareDetealResponse extends BaseResponceImpl implements GetShareDetailRequest.IGetShareResponse {

        @Override
        public void doAfterFailedResponse(String message) {
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(Share share) {
            exhibitCoverImgVw.setImageUrl(share.coverUrl, imageLoader);
            exhibitPersonImgVw.setImageUrl(share.avatarUrl, imageLoader);
            exhibitPersonNameTxVw.setText(share.nickname);
            exhibitFocusBtn.setText(share.hasAttention ? ExhibitDetailActivity.this.getString(R.string.focus) : ExhibitDetailActivity.this.getString(R.string.notFocus));
            exhibitTitleTxVw.setText(share.title);
            exhibitTitleContentTxVw.setText(share.content);
            exhibitListViewAdapter.updateDataList(share.imageList);
            workDetailListViewAdapter.updateCommentList(share.commentList);
        }
    }
}

