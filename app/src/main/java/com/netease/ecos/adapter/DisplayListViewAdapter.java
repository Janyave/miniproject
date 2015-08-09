package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.netease.ecos.R;
import com.netease.ecos.activity.CommentDetailActivity;
import com.netease.ecos.activity.DisplayDetailActivity;
import com.netease.ecos.activity.MyApplication;
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.activity.SearchActivity;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Share;
import com.netease.ecos.request.BaseResponceImpl;
import com.netease.ecos.request.user.FollowUserRequest;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.views.ExtensibleListView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hzjixinyu on 2015/7/23.
 */
public class DisplayListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mcontext;
    private List<Share> shareList;
    private FollowUserRequest request;
    private FollowResponce followResponce;

    public DisplayListViewAdapter(Context context, List<Share> shareList) {
        this.mcontext = context;
        this.shareList = shareList;
        request = new FollowUserRequest();
        followResponce = new FollowResponce();
    }

    class ViewHolder {

        private LinearLayout ll_author;
        private RoundImageView iv_avatar;
        private TextView tv_name;
        private TextView tv_focus;

        private ImageView iv_cover;
        private TextView tv_coverNum;
        private TextView tv_coverTitle;
        private TextView tv_coverTime;

        private TextView tv_praise;
        private TextView tv_evaluation;
        private LinearLayout ll_praise;
        private LinearLayout ll_evaluate;

        private ExtensibleListView lv_evaluation;
        private LinearLayout ll_evaluationList;
        private DisplayItemEvalutionViewAdapter adapter;

        public ViewHolder(View root) {
            ll_author = (LinearLayout) root.findViewById(R.id.ll_author);
            iv_avatar = (RoundImageView) root.findViewById(R.id.iv_avatar);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_focus = (TextView) root.findViewById(R.id.tv_focus);
            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);
            tv_coverNum = (TextView) root.findViewById(R.id.tv_coverNum);
            tv_coverTitle = (TextView) root.findViewById(R.id.tv_coverTitle);
            tv_coverTime = (TextView) root.findViewById(R.id.tv_coverTime);
            tv_praise = (TextView) root.findViewById(R.id.tv_praise);
            tv_evaluation = (TextView) root.findViewById(R.id.tv_evaluation);
            ll_praise = (LinearLayout) root.findViewById(R.id.ll_praise);
            ll_evaluate = (LinearLayout) root.findViewById(R.id.ll_evaluation);
            tv_praise = (TextView) root.findViewById(R.id.tv_praise);
            tv_evaluation = (TextView) root.findViewById(R.id.tv_evaluation);
            ll_evaluationList = (LinearLayout) root.findViewById(R.id.ll_evaluationList);
            lv_evaluation = (ExtensibleListView) root.findViewById(R.id.lv_evaluation);
        }

        /**
         * 传入数据未定
         */
        public void setData(final int position) {
            Share item = shareList.get(position);

            //bind the data
            if (item.avatarUrl != null && !item.avatarUrl.equals("")){
                iv_avatar.setDefaultImageResId(R.mipmap.bg_female_default);
                iv_avatar.setErrorImageResId(R.mipmap.bg_female_default);
                RequestQueue queue = MyApplication.getRequestQueue();
                ImageLoader.ImageCache imageCache = new SDImageCache();
                ImageLoader imageLoader = new ImageLoader(queue, imageCache);
                iv_avatar.setImageUrl(item.avatarUrl, imageLoader);
            }
            else
                iv_avatar.setImageResource(R.mipmap.bg_female_default);;
            tv_name.setText(item.nickname);
            tv_focus.setText(item.hasAttention ? "已关注" : "关注");
            tv_focus.setTextColor(mcontext.getResources().getColor(item.hasAttention ? R.color.text_gray : R.color.text_white));
            tv_focus.setBackgroundResource(item.hasAttention ? R.drawable.btn_focus_gray : R.drawable.btn_focus_pink);

            if (item.coverUrl != null && !TextUtils.isEmpty(item.coverUrl))
                Picasso.with(mcontext).load(item.coverUrl).placeholder(R.drawable.img_default).into(iv_cover);
            else
                iv_cover.setImageResource(R.drawable.img_default);
            tv_coverNum.setText(item.totalPageNumber + mcontext.getResources().getString(R.string.page));
            tv_coverTitle.setText(item.title);
            tv_coverTime.setText(item.getDateDescription());

            tv_praise.setText(item.praiseNum + mcontext.getResources().getString(R.string.manyFavor));
            tv_evaluation.setText(item.commentNum + mcontext.getResources().getString(R.string.manyComment));

            adapter = new DisplayItemEvalutionViewAdapter(mcontext, item.commentList);
            lv_evaluation.setAdapter(adapter);

            //set tag
            ll_author.setTag(position);
            tv_focus.setTag(position);
            iv_cover.setTag(position);
            tv_coverTitle.setTag(position);
            ll_praise.setTag(position);
            ll_evaluate.setTag(position);
            ll_evaluationList.setTag(position);

            //set listener
            ll_author.setOnClickListener(DisplayListViewAdapter.this);
            tv_focus.setOnClickListener(DisplayListViewAdapter.this);
            iv_cover.setOnClickListener(DisplayListViewAdapter.this);
            tv_coverTitle.setOnClickListener(DisplayListViewAdapter.this);
            ll_praise.setOnClickListener(DisplayListViewAdapter.this);
            ll_evaluate.setOnClickListener(DisplayListViewAdapter.this);
            ll_evaluationList.setOnClickListener(DisplayListViewAdapter.this);

            if (!item.hasPraised) {
                //TODO 未赞图片
            }
        }
    }

    @Override
    public int getCount() {
        return shareList.size() + 1;
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (position == 0) {
            convertView = parent.inflate(mcontext, R.layout.item_display_search, null);
            ((TextView) convertView.findViewById(R.id.tv_search)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(mcontext, SearchActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt(SearchActivity.SEARCH_TYPE, SearchActivity.TYPE_SHARE);
                    intent1.putExtras(bundle1);
                    mcontext.startActivity(intent1);
                }
            });
            convertView.setTag(false);
            return convertView;
        } else {
            if (convertView == null || convertView.getTag() instanceof Boolean) {
                convertView = parent.inflate(mcontext, R.layout.item_display, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.setData(position - 1);
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.ll_author:
                intent = new Intent(mcontext, PersonageDetailActivity.class);
                bundle.putString(PersonageDetailActivity.UserID, shareList.get(position).userId);
                bundle.putBoolean(PersonageDetailActivity.IsOwn, false);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.tv_focus:
                if (TextUtils.equals(((TextView) v).getText().toString(), "关注")) {
                    ((TextView) v).setText("已关注");
                    ((TextView) v).setTextColor(mcontext.getResources().getColor(R.color.text_gray));
                    ((TextView) v).setBackgroundResource(R.drawable.btn_focus_gray);
                    request.request(followResponce, shareList.get(position).userId, true);
                } else {
                    ((TextView) v).setText("关注");
                    ((TextView) v).setTextColor(mcontext.getResources().getColor(R.color.text_white));
                    ((TextView) v).setBackgroundResource(R.drawable.btn_focus_pink);
                    request.request(followResponce, shareList.get(position).userId, false);
                }
                break;
            case R.id.iv_cover:
            case R.id.tv_coverTitle:
                intent = new Intent(mcontext, DisplayDetailActivity.class);
                bundle.putString(DisplayDetailActivity.ShareId, shareList.get(position).shareId);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.ll_praise:
                //TODO:点赞 favor
                break;
            case R.id.ll_evaluation:
            case R.id.ll_evaluationList:
                intent = new Intent(mcontext, CommentDetailActivity.class);
                bundle.putString(CommentDetailActivity.FromId, shareList.get(position).shareId);
                bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.分享.getBelongs());
                bundle.putBoolean(CommentDetailActivity.IsPraised, shareList.get(position).hasPraised);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
        }
    }

    class FollowResponce extends BaseResponceImpl implements FollowUserRequest.IFollowResponce {

        @Override
        public void doAfterFailedResponse(String message) {
            Toast.makeText(mcontext, "error happens:" + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void success(String userId, boolean follow) {
        }
    }

    public List<Share> getShareList() {
        return shareList;
    }

    public void setShareList(List<Share> shareList) {
        this.shareList = shareList;
    }
}
