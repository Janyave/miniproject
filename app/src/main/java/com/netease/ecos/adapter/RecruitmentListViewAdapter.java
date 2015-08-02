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
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.activity.RecruitmentDetailActivity;
import com.netease.ecos.model.Recruitment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hzjixinyu on 2015/7/23.
 */
public class RecruitmentListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mcontext;
    private List<Recruitment> recruitmentArrayList;

    public RecruitmentListViewAdapter(Context context, List<Recruitment> recruitmentArrayList) {
        this.mcontext = context;
        this.recruitmentArrayList = recruitmentArrayList;
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
            tv_distance = (TextView) root.findViewById(R.id.tv_distance);
            tv_price = (TextView) root.findViewById(R.id.tv_price);
            tv_talk = (TextView) root.findViewById(R.id.tv_talk);
            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position) {
            //TODO 绑定数据
            Picasso.with(mcontext).load("http://i.imgur.com/DvpvklR.png").placeholder(R.drawable.img_default).into(iv_cover);
            //set tag
            iv_avatar.setTag(position);
            tv_name.setTag(position);
            tv_talk.setTag(position);
            iv_cover.setTag(position);
            //set listener
            iv_avatar.setOnClickListener(RecruitmentListViewAdapter.this);
            tv_name.setOnClickListener(RecruitmentListViewAdapter.this);
            iv_cover.setOnClickListener(RecruitmentListViewAdapter.this);
            tv_talk.setOnClickListener(RecruitmentListViewAdapter.this);
        }
    }

    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return recruitmentArrayList.size();
    }


    @Override
    public Object getItem(int position) {
        return recruitmentArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.iv_avatar:
            case R.id.tv_name:
                intent = new Intent(mcontext, PersonageDetailActivity.class);
                bundle.putString(PersonageDetailActivity.UserID, recruitmentArrayList.get(position).userId);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.iv_cover:
                intent = new Intent(mcontext, RecruitmentDetailActivity.class);
                intent.putExtras(bundle);
                bundle.putString(RecruitmentDetailActivity.UserID, recruitmentArrayList.get(position).userId);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.tv_talk:
                //TODO:私信
                break;
        }
    }
}
