package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.ExhibitDetailActivity;
import com.netease.ecos.views.ExtensibleListView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by hzjixinyu on 2015/7/23.
 */
public class RecruitmentListViewAdapter extends BaseAdapter {

    private Context mcontext;

    public RecruitmentListViewAdapter(Context context) {
        this.mcontext = context;
    }

    class ViewHolder {

        private ImageView iv_avatar;
        private TextView tv_name;
        private TextView tv_distance;
        private TextView tv_price;
        private TextView tv_talk;
        private ImageView iv_cover;

        public ViewHolder(View root) {
            iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_distance=(TextView) root.findViewById(R.id.tv_distance);
            tv_price=(TextView) root.findViewById(R.id.tv_price);
            tv_talk=(TextView) root.findViewById(R.id.tv_talk);
            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);

            iv_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 招募详情页
                }
            });

            tv_talk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 私信
                }
            });
        }

        /**
         * 传入数据未定
         */
        public void setData(int position) {
            //TODO 绑定数据
            Picasso.with(mcontext).load("http://i.imgur.com/DvpvklR.png").placeholder(R.drawable.img_default).into(iv_cover);
        }
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_recruitment_type, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }
}
