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
import com.netease.ecos.model.Contact;
import com.netease.ecos.model.Course;
import com.netease.ecos.model.ModelUtils;
import com.netease.ecos.utils.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzjixinyu on 2015/7/29.
 */
public class NotificationContactAdapter extends BaseAdapter{

    private Context mcontext;
    private List<Contact> contactList=new ArrayList<Contact>();

    public NotificationContactAdapter(Context context, List<Contact> contactList) {
        this.mcontext = context;
        this.contactList=contactList;
    }

    class ViewHolder implements View.OnClickListener{

        private RelativeLayout rly_main;
        private RoundImageView iv_avatar;
        private TextView tv_name;
        private TextView tv_recentContact;
        private TextView tv_recentTime;
        private TextView tv_uncheckNum;


        public ViewHolder(View root) {
            rly_main=(RelativeLayout)root.findViewById(R.id.rly_main);
            iv_avatar = (RoundImageView) root.findViewById(R.id.iv_avatar);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_recentContact = (TextView) root.findViewById(R.id.tv_recentContact);
            tv_recentTime = (TextView) root.findViewById(R.id.tv_recentTime);
            tv_uncheckNum=(TextView) root.findViewById(R.id.tv_uncheckNum);

            rly_main.setOnClickListener(this);
            iv_avatar.setOnClickListener(this);
        }

        /**
         * ��������δ��
         */
        public void setData(int position){
            Contact item=contactList.get(position);
//            Picasso.with(mcontext).load("").placeholder(R.drawable.img_default).into(iv_avatar);
            tv_name.setText("TEST");
            tv_recentContact.setText(item.messageContent);
            tv_recentTime.setText(ModelUtils.getDateDetailByTimeStamp(item.time)+"");
            tv_uncheckNum.setText(item.unreadedNum+"");
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


    //TODO ��������������ģ��Ϊ10��
    @Override
    public int getCount() {
        return contactList==null?0:contactList.size();
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
