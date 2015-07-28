package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.squareup.picasso.Picasso;

public class CampaignListViewAdapter extends BaseAdapter {

    private Context mcontext;

    public CampaignListViewAdapter(Context context) {
        this.mcontext = context;
    }

    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_campaign_show, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }

    class ViewHolder {

        private ImageView imageTitlePic;
        private TextView textViewTitle;
        private TextView textViewTime;
        private TextView textViewLocation;


        public ViewHolder(View root) {
            imageTitlePic = (ImageView) root.findViewById(R.id.iv_campaign_dis);

            textViewTitle = (TextView) root.findViewById(R.id.tv_campaign_title);
            textViewTime = (TextView) root.findViewById(R.id.tv_campaign_time);
            textViewLocation = (TextView) root.findViewById(R.id.tv_campaign_location);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position) {
            //TODO 绑定数据
            Picasso.with(mcontext).load("http://i.imgur.com/DvpvklR.png").placeholder(R.drawable.img_default).into(imageTitlePic);
        }
    }
}
