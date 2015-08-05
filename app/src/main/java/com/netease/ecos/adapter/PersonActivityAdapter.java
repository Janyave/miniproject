package com.netease.ecos.adapter;

import
        android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.model.ActivityModel;
import com.netease.ecos.model.Share;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzzhanyawei on 2015/8/5.
 * Email hzzhanyawei@corp.netease.com
 */
public class PersonActivityAdapter extends BaseAdapter {
    private Context mcontext;
    private List<ActivityModel> activityList;

    public PersonActivityAdapter(Context context) {
        this.mcontext = context;
    }
    public PersonActivityAdapter(Context context, List<ActivityModel> activityList) {
        this.mcontext = context;
        this.activityList = activityList;
    }


    public void setActivityList(List<ActivityModel> activityList) {
        this.activityList = activityList;
    }

    public List<ActivityModel> getActivityList() {
        return this.activityList;
    }

    public Context getMcontext() {
        return mcontext;
    }

    class ViewHolder {

        private ImageView iv_cover;
        private TextView tv_tag;
        private TextView tv_title;
        private TextView tv_time;
        private TextView tv_location;


        public ViewHolder(View root) {
            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);
            tv_tag = (TextView) root.findViewById(R.id.tv_tag);
            tv_title = (TextView) root.findViewById(R.id.tv_title);
            tv_time = (TextView) root.findViewById(R.id.tv_time);
            tv_location = (TextView) root.findViewById(R.id.tv_location);
        }

        /**
         * 传入数据
         */
        public void setData(final int position) {
            ActivityModel item = activityList.get(position);

            Picasso.with(mcontext).load(item.coverUrl).placeholder(R.drawable.img_default).into(iv_cover);
            tv_title.setText(item.title);
            tv_tag.setText(item.activityType + "");
            tv_time.setText(item.issueTimeStamp+"");
            tv_location.setText(item.location+"");

        }
    }

    @Override
    public int getCount() {
        if (activityList != null){
            return activityList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_activity_personal, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }
}
