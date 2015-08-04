package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.CourseDetailActivity;
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.model.Course;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzjixinyu on 2015/8/4.
 */
public class EventContactWayAdapter extends BaseAdapter{
    private Context mcontext;

    public EventContactWayAdapter(Context context) {
        this.mcontext = context;
    }


    class ViewHolder {

        //R.mipmap.ic_phone_pink
        //R.mipmap.ic_qq_pink
        //R.mipmap.ic_weibo_pink
        private ImageView iv_type;
        private TextView tv_detail;


        public ViewHolder(View root) {
            iv_type = (ImageView) root.findViewById(R.id.iv_type);
            tv_detail = (TextView) root.findViewById(R.id.tv_detail);
        }

        /**
         * 传入数据未定
         */
        public void setData(final int position) {

        }
    }


    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return 4;
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
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_event_contactway, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }
}
