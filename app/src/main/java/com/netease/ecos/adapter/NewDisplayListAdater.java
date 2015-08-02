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

import java.util.ArrayList;

/**
 * Created by Think on 2015/7/31.
 */
public class NewDisplayListAdater extends BaseAdapter {
    private Context mcontext;
    private LayoutInflater layoutInflater;
    /**
     * to record which item is chosen.
     * if it equals -1, it means no item is chosen.
     */
    private int chosenItem = -1;

    /**
     * to record the list of checkbox
     */
    private ArrayList<CheckBox> checkBoxes;

    public NewDisplayListAdater(Context context) {
        this.mcontext = context;
        this.layoutInflater = LayoutInflater.from(mcontext);
    }

    public int getChosenItem() {
        return chosenItem;
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
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (DisplayItemViewHolder) convertView.getTag();
        return convertView;
    }

    class DisplayItemViewHolder {
        ImageView coverImgVw;
        TextView titleTxVw, favorTxVw;
        CheckBox checkBox;
    }
}
