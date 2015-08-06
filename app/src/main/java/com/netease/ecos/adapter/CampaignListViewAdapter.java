package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.model.ActivityModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CampaignListViewAdapter extends BaseAdapter {

    private Context mcontext;
    private List<ActivityModel> activityList;

    private ViewHolder viewHolder = null;

    public CampaignListViewAdapter(Context context, List<ActivityModel> activityList) {
        this.mcontext = context;
        this.activityList = activityList;
    }

    public List<ActivityModel> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityModel> activityList) {
        this.activityList = activityList;
    }

    @Override
    public int getCount() {
        return activityList.size();
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
        private TextView activityTypeTxVw;

        public ViewHolder(View root) {
            imageTitlePic = (ImageView) root.findViewById(R.id.iv_campaign_dis);

            textViewTitle = (TextView) root.findViewById(R.id.tv_campaign_title);
            textViewTime = (TextView) root.findViewById(R.id.tv_campaign_time);
            textViewLocation = (TextView) root.findViewById(R.id.tv_campaign_location);
            activityTypeTxVw = (TextView) root.findViewById(R.id.activityTypeTxVw);
        }

        public void setData(int position) {
            // 设置封面
            if (activityList.get(position).coverUrl != null && !activityList.get(position).coverUrl.equals(""))
                Picasso.with(mcontext).load(activityList.get(position).coverUrl).placeholder(R.drawable.img_default).into(viewHolder.imageTitlePic);
            else
                viewHolder.imageTitlePic.setImageResource(R.drawable.img_default);

            viewHolder.textViewTitle.setText(activityList.get(position).title);

            viewHolder.textViewTime.setText(activityList.get(position).activityTime.toString());

            viewHolder.textViewLocation.setText(activityList.get(position).location.toString());

            viewHolder.activityTypeTxVw.setText(activityList.get(position).activityType.name());
        }
    }
}
