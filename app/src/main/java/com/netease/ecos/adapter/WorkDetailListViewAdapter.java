package com.netease.ecos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;

/**
 * Created by Think on 2015/7/22.
 */
public class WorkDetailListViewAdapter extends BaseAdapter {
    private Context mcontext;

    public WorkDetailListViewAdapter(Context context) {
        this.mcontext = context;
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
        return 16;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CommentViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.comment_detail_layout, null);
            viewHolder.setImageView((ImageView) convertView.findViewById(R.id.commentPersonImgVw));
            viewHolder.setNameTxVw((TextView) convertView.findViewById(R.id.commentPersonNameTxVw));
            viewHolder.setCommentTxVw((TextView) convertView.findViewById(R.id.commentContentTxVw));
            convertView.setTag(viewHolder);
        } else
            viewHolder = (CommentViewHolder) convertView.getTag();
        return convertView;
    }

    private static class CommentViewHolder {
        ImageView imageView;
        TextView nameTxVw, commentTxVw;

        public void setImageView(ImageView imageView) {
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
