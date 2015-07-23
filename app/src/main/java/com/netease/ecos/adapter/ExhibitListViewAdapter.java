package com.netease.ecos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.netease.ecos.R;

/**
 * Created by Think on 2015/7/23.
 */
public class ExhibitListViewAdapter extends BaseAdapter {
    private Context mcontext;

    public ExhibitListViewAdapter(Context context) {
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
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.exhibit_list_view_item, null);
        return convertView;
    }
}
