package com.netease.ecos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;

/**
 * Created by Think on 2015/7/31.
 */
public class NewDisplayListAdater extends BaseAdapter {
    private Context mcontext;
    private LayoutInflater layoutInflater;

    public NewDisplayListAdater(Context context) {
        this.mcontext = context;
        this.layoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return 5;
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
        DisplayItemViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_new_display_list, null);
            viewHolder = new DisplayItemViewHolder();
            viewHolder.coverImgVw = (ImageView) convertView.findViewById(R.id.displayCoverImVw);
            viewHolder.titleTxVw = (TextView) convertView.findViewById(R.id.displayTitleTxVw);
            viewHolder.favorTxVw = (TextView) convertView.findViewById(R.id.displayFavorTxVw);
            viewHolder.checkBox = (ImageView) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DisplayItemViewHolder) convertView.getTag();
        }
        setData(viewHolder, position);
        return convertView;
    }

    void setData(DisplayItemViewHolder viewHolder, int position) {

//        viewHolder.checkBox.setImageResource(R.mipmap.ic_choose_false);
//        viewHolder.checkBox.setImageResource(R.mipmap.ic_choose_true);
    }

    class DisplayItemViewHolder {
        ImageView coverImgVw;
        TextView titleTxVw, favorTxVw;
        ImageView checkBox;
    }
}
