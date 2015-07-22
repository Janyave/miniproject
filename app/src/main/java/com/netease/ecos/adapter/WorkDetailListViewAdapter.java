package com.netease.ecos.adapter;

import android.content.Context;
import android.util.Log;
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
        return 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("test","getview");
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.comment_detail_layout, null);
        return convertView;
    }

    private static class CommentViewHolder {
        ImageView imageView;
        TextView nameTxVw, commentTxVw;
    }
}
