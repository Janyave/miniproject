package com.netease.ecos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.netease.ecos.R;
import com.netease.ecos.activity.MyApplication;
import com.netease.ecos.model.Comment;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Think on 2015/7/22.
 */
public class WorkDetailListViewAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Comment> commentList;
    //for NetWorkImageView
    static ImageLoader.ImageCache imageCache;
    RequestQueue queue;
    ImageLoader imageLoader;

    public WorkDetailListViewAdapter(Context context) {
        this.mcontext = context;
        //init the data for NetWorkImageView
        commentList = new ArrayList<>();
        queue = MyApplication.getRequestQueue();
        imageCache = new SDImageCache();
        imageLoader = new ImageLoader(queue, imageCache);

    }

    public void updateCommentList(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CommentViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.comment_detail_layout, null);
            viewHolder.setImageView((RoundImageView) convertView.findViewById(R.id.commentPersonImgVw));
            viewHolder.setNameTxVw((TextView) convertView.findViewById(R.id.commentPersonNameTxVw));
            viewHolder.setCommentTxVw((TextView) convertView.findViewById(R.id.commentContentTxVw));
            convertView.setTag(viewHolder);
        } else
            viewHolder = (CommentViewHolder) convertView.getTag();
        setData(viewHolder, position);
        return convertView;
    }

    void setData(CommentViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageUrl(commentList.get(position).avatarUrl, imageLoader);
        viewHolder.nameTxVw.setText(commentList.get(position).fromNickName);
        viewHolder.commentTxVw.setText(commentList.get(position).content);
    }

    private static class CommentViewHolder {
        RoundImageView imageView;
        TextView nameTxVw, commentTxVw;

        public void setImageView(RoundImageView imageView) {
            this.imageView = imageView;
        }

        public void setNameTxVw(TextView nameTxVw) {
            this.nameTxVw = nameTxVw;
        }

        public void setCommentTxVw(TextView commentTxVw) {
            this.commentTxVw = commentTxVw;
        }
    }
}
