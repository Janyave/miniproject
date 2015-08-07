package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.ActivityDetailActivity;
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.activity.RecruitmentDetailActivity;
import com.netease.ecos.model.ActivityModel;
import com.netease.ecos.model.Recruitment;
import com.netease.ecos.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hzzhanyawei on 2015/8/5.
 * Email hzzhanyawei@corp.netease.com
 */
public class PersonRecruitAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mcontext;
    private List<Recruitment> recruitmentList;

    public PersonRecruitAdapter(Context context) {
        this.mcontext = context;
    }
    public PersonRecruitAdapter(Context context, List<Recruitment> recruitmentList) {
        this.mcontext = context;
        this.recruitmentList = recruitmentList;
    }


    public void setRecruitmentList(List<Recruitment> recruitmentList) {
        this.recruitmentList = recruitmentList;
    }

    public List<Recruitment> getRecruitmentList() {
        return this.recruitmentList;
    }

    public Context getMcontext() {
        return mcontext;
    }



    class ViewHolder {
        private ImageView iv_avatar;
        private ImageView iv_cover;
        private ImageView gender;
        private TextView tv_tag;
        private TextView tv_name;
        private TextView tv_price;
        private TextView tv_distance;



        public ViewHolder(View root) {
            iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);
            gender = (ImageView) root.findViewById(R.id.genderImVw);
            tv_tag = (TextView) root.findViewById(R.id.tv_talk);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_price = (TextView) root.findViewById(R.id.tv_price);
            tv_distance = (TextView) root.findViewById(R.id.tv_distance);
        }

        /**
         * 传入数据
         */
        public void setData(final int position) {
            Recruitment item = recruitmentList.get(position);
            if (item.avatarUrl != null && !item.avatarUrl.equals(""))
                Picasso.with(mcontext).load(item.avatarUrl).placeholder(R.drawable.img_default).into(iv_avatar);
            if (item.coverUrl != null && !item.coverUrl.equals(""))
                Picasso.with(mcontext).load(item.coverUrl).placeholder(R.drawable.img_default).into(iv_cover);

            if (item.gender == User.Gender.男){
                gender.setImageResource(R.mipmap.ic_gender_male);
            }else {
                gender.setImageResource(R.mipmap.ic_gender_female);
            }
            tv_name.setText(item.nickname);
            tv_tag.setText(item.recruitType + "");
            tv_price.setText(item.priceUnit + "");
            tv_distance.setText(item.distanceKM + "");

            iv_cover.setTag(position);
            tv_name.setTag(position);
            iv_avatar.setTag(position);
            iv_cover.setOnClickListener(PersonRecruitAdapter.this);
            tv_name.setOnClickListener(PersonRecruitAdapter.this);
            iv_avatar.setOnClickListener(PersonRecruitAdapter.this);
        }
    }

    @Override
    public int getCount() {
        if (recruitmentList != null){
            return recruitmentList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return recruitmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_personal_recruitment, null);
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
        int position = (int) v.getTag();
        switch (v.getId()){
            case R.id.iv_cover:
                Intent intent = new Intent(mcontext, RecruitmentDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(RecruitmentDetailActivity.RecruitID, recruitmentList.get(position).recruitmentId);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.iv_avatar:
                Intent intentB = new Intent(mcontext, PersonageDetailActivity.class);
                Bundle bundleB = new Bundle();
                bundleB.putString(PersonageDetailActivity.UserID, recruitmentList.get(position).userId);
                intentB.putExtras(bundleB);
                mcontext.startActivity(intentB);
            case R.id.tv_name:
                break;
        }
    }
}
