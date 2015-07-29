package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.ContactActivity;
import com.netease.ecos.model.Course;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzjixinyu on 2015/7/29.
 */
public class NotificationContactAdapter extends BaseAdapter{

    private Context mcontext;

    public NotificationContactAdapter(Context context) {
        this.mcontext = context;
    }

    class ViewHolder implements View.OnClickListener{

        private RelativeLayout rly_main;
        private ImageView iv_avatar;
        private TextView tv_name;
        private TextView tv_recentContact;
        private TextView tv_recentTime;


        public ViewHolder(View root) {
            rly_main=(RelativeLayout)root.findViewById(R.id.rly_main);
            iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_recentContact = (TextView) root.findViewById(R.id.tv_recentContact);
            tv_recentTime = (TextView) root.findViewById(R.id.tv_recentTime);

            rly_main.setOnClickListener(this);
            iv_avatar.setOnClickListener(this);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position){
//            Picasso.with(mcontext).load(item.coverUrl).placeholder(R.drawable.img_default).into(networkImageView);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rly_main:
                    mcontext.startActivity(new Intent(mcontext, ContactActivity.class));
                    break;
                case R.id.iv_avatar:
                    //TODO
                    break;
            }
        }
    }


    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return 10;
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
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=parent.inflate(mcontext,R.layout.item_notification_contact,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }

}
