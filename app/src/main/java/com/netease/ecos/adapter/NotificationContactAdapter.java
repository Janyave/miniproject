package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.netease.ecos.R;
import com.netease.ecos.activity.ContactActivity;
import com.netease.ecos.activity.MyApplication;
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.model.AccountDataService;
import com.netease.ecos.model.Contact;
import com.netease.ecos.model.ModelUtils;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;

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

        public void setTag(int position){
            iv_avatar.setTag(position);
            rly_main.setTag(position);
        }

        /**
         * ��������δ��
         */
        public void setData(int position){
            Contact item=contactList.get(position);

            Log.i("最近联系人列表",item.toString());

            iv_avatar.setDefaultImageResId(R.mipmap.bg_female_default);
            iv_avatar.setErrorImageResId(R.mipmap.bg_female_default);

            ImageLoader.ImageCache imageCache = new SDImageCache();
            ImageLoader imageLoader = new ImageLoader(MyApplication.getRequestQueue(), imageCache);
            //TODO
            if( item.avatarUrl!=null && !"".equals(item.avatarUrl) )
                iv_avatar.setImageUrl(item.avatarUrl, imageLoader);
            tv_name.setText(item.contactNickName);
            tv_recentContact.setText(item.messageContent);
            tv_recentTime.setText(ModelUtils.getDateDetailByTimeStamp(item.time)+"");
            tv_uncheckNum.setText(item.unreadedNum+"");
            if (item.unreadedNum==0){
                tv_uncheckNum.setVisibility(View.GONE);
            }else {
                tv_uncheckNum.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            Bundle bundle=new Bundle();
            switch (v.getId()){
                case R.id.rly_main:
                    intent = new Intent(mcontext, ContactActivity.class);
                    bundle.putString(ContactActivity.TargetUserID, contactList.get((int)v.getTag()).contactUserId);
                    bundle.putString(ContactActivity.TargetUserAvatar, contactList.get((int)v.getTag()).avatarUrl);
                    bundle.putString(ContactActivity.TargetUserName, contactList.get((int) v.getTag()).contactNickName);

                    String myImId = AccountDataService.getSingleAccountDataService(mcontext).getUserAccId();
                    String fromAccount = contactList.get((int) v.getTag()).fromAccount;
                    if(myImId.equals(fromAccount)) {
                        bundle.putString(ContactActivity.TargetUserIMID, contactList.get((int) v.getTag()).contactAccid);
                    }
                    else{
                        bundle.putString(ContactActivity.TargetUserIMID, contactList.get((int) v.getTag()).fromAccount);
                    }

//                    bundle.putString(ContactActivity.TargetUserIMID, contactList.get((int) v.getTag()).fromAccount);
                    Log.v("contact", "targetIMID--------   " + contactList.get((int) v.getTag()).fromAccount);
                    Log.v("contact", "targetID--------   " + contactList.get((int)v.getTag()).contactUserId);
                    Log.v("contact", "targetAvatar--------   " + contactList.get((int)v.getTag()).avatarUrl);
                    intent.putExtras(bundle);
                    break;
                case R.id.iv_avatar:
                    intent = new Intent(mcontext, PersonageDetailActivity.class);
                    bundle.putString(PersonageDetailActivity.UserID, contactList.get((int)v.getTag()).contactUserId);
                    bundle.putBoolean(PersonageDetailActivity.IsOwn, false);
                    intent.putExtras(bundle);
                    Toast.makeText(mcontext, "个人界面", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return;
            }
            mcontext.startActivity(intent);
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

        viewHolder.setTag(position);
        viewHolder.setData(position);

        return convertView;
    }

}
