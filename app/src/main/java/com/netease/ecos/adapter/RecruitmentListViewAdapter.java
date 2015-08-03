package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.ContactActivity;
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
        private LinearLayout ll_author;

        public ViewHolder(View root) {
            iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_distance = (TextView) root.findViewById(R.id.tv_distance);
            tv_price = (TextView) root.findViewById(R.id.tv_price);
            tv_talk = (TextView) root.findViewById(R.id.tv_talk);
            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);
            ll_author = (LinearLayout) root.findViewById(R.id.ll_author);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position) {
            //set the data for each widget
            Picasso.with(mcontext).load(recruitmentArrayList.get(position).avatarUrl).placeholder(R.drawable.img_default).into(iv_avatar);
            Picasso.with(mcontext).load(recruitmentArrayList.get(position).coverUrl).placeholder(R.drawable.img_default).into(iv_cover);
            tv_name.setText(recruitmentArrayList.get(position).nickname);
            tv_distance.setText(recruitmentArrayList.get(position).distanceKM);
            tv_price.setText(recruitmentArrayList.get(position).averagePrice);
            //set tag
            ll_author.setTag(position);
            tv_talk.setTag(position);
            iv_cover.setTag(position);
            //set listener
            ll_author.setOnClickListener(RecruitmentListViewAdapter.this);
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
            case R.id.ll_author:
                intent = new Intent(mcontext, PersonageDetailActivity.class);
                bundle.putString(PersonageDetailActivity.UserID, recruitmentArrayList.get(position).userId);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.iv_cover:
                intent = new Intent(mcontext, RecruitmentDetailActivity.class);
                intent.putExtras(bundle);
                bundle.putString(RecruitmentDetailActivity.RecruitID, recruitmentArrayList.get(position).recruitmentId);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.tv_talk:
                intent = new Intent(mcontext, ContactActivity.class);
                bundle.putString(ContactActivity.TargetUserID, recruitmentArrayList.get(position).userId);
                bundle.putString(ContactActivity.TargetUserName, recruitmentArrayList.get(position).nickname);
                bundle.putString(ContactActivity.TargetUserAvatar, recruitmentArrayList.get(position).avatarUrl);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
        }
    }
}
