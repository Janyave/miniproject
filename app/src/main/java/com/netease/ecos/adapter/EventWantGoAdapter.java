package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.netease.ecos.R;
import com.netease.ecos.activity.ContactActivity;
import com.netease.ecos.activity.NormalListViewActivity;
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.model.Contact;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by hzjixinyu on 2015/8/4.
 */
public class EventWantGoAdapter extends BaseAdapter{
    private Context mcontext;
    private int TYPE=1;
    private List<User> userList;

    public EventWantGoAdapter(Context context) {
        this.mcontext = context;
    }

    public EventWantGoAdapter(Context context , int type, List<User> userList) {
        this.mcontext = context;
        this.TYPE=type;
        this.userList=userList;
    }




    class ViewHolder implements View.OnClickListener{

        private RoundImageView iv_avatar;
        private ImageView iv_relation;
        private LinearLayout ll_tagList;
        private TextView tv_name;
        private TextView tv_signature;
        private TextView tv_contact;


        public ViewHolder(View root) {
            iv_avatar = (RoundImageView) root.findViewById(R.id.iv_avatar);
            iv_relation = (ImageView) root.findViewById(R.id.iv_relation);
            ll_tagList = (LinearLayout)root.findViewById(R.id.ll_tagList);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_signature = (TextView) root.findViewById(R.id.tv_signature);
            tv_contact = (TextView) root.findViewById(R.id.tv_contact);


            iv_avatar.setOnClickListener(this);
            tv_contact.setOnClickListener(this);

        }

        public void setTag(int position){
            iv_avatar.setTag(position);
            tv_contact.setTag(position);
        }

        /**
         * 传入数据未定
         */
        public void setData(final int position, ViewGroup parent) {

            User item=userList.get(position);

            iv_avatar.setDefaultImageResId(R.mipmap.bg_female_default);
            iv_avatar.setErrorImageResId(R.mipmap.bg_female_default);
            RequestQueue queue = Volley.newRequestQueue(mcontext);
            ImageLoader.ImageCache imageCache = new SDImageCache();
            ImageLoader imageLoader = new ImageLoader(queue, imageCache);
            iv_avatar.setImageUrl(item.avatarUrl, imageLoader);

            tv_name.setText(item.nickname);
            tv_signature.setText(item.characterSignature);

            if (TYPE==NormalListViewActivity.TYPE_EVENT_ATTENTION){
                tv_contact.setText("私信");
            }
            if (TYPE==NormalListViewActivity.TYPE_EVENT_WANTGO){
                tv_contact.setText("戳一下");
            }
            if (TYPE==NormalListViewActivity.TYPE_EVENT_FANS){
                tv_contact.setText("私信");
            }

            ll_tagList.removeAllViews();
            Set<User.RoleType> roleTypeList=item.roleTypeSet;
            Iterator i=roleTypeList.iterator();
            while(i.hasNext()){
                User.RoleType type=(User.RoleType)i.next();
                View v=parent.inflate(mcontext, R.layout.item_tag,null);
                ((TextView)v.findViewById(R.id.tv_tag)).setText(type.name());
                ll_tagList.addView(v);
            }

        }

        @Override
        public void onClick(View v) {
            Intent intent;
            Bundle bundle=new Bundle();
            switch (v.getId()){
                case R.id.iv_avatar:
                    intent = new Intent(mcontext, PersonageDetailActivity.class);
                    bundle.putString(PersonageDetailActivity.UserID, userList.get((int)v.getTag()).userId);
                    bundle.putBoolean(PersonageDetailActivity.IsOwn, false);
                    intent.putExtras(bundle);
                    Toast.makeText(mcontext, "个人界面", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_contact:
                    intent = new Intent(mcontext, ContactActivity.class);
                    bundle.putString(ContactActivity.TargetUserID, userList.get((int)v.getTag()).userId);
                    bundle.putString(ContactActivity.TargetUserAvatar, userList.get((int)v.getTag()).avatarUrl);
                    bundle.putString(ContactActivity.TargetUserName, userList.get((int)v.getTag()).nickname);
                    bundle.putString(ContactActivity.TargetUserIMID, userList.get((int) v.getTag()).imId);
                    Log.v("contact", "targetIMID--------   " + userList.get((int) v.getTag()).imId);
                    Log.v("contact", "targetID--------   " + userList.get((int)v.getTag()).userId);
                    intent.putExtras(bundle);
                    break;
                default:
                    return;
            }
            mcontext.startActivity(intent);
        }
    }


    //TODO 数据数量【现在模拟为4】
    @Override
    public int getCount() {
        if (TYPE== NormalListViewActivity.TYPE_EVENT_WANTGO){
            return 4;
        }else {
            return userList.size();
        }

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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_activity_wantgo, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setTag(position);
        viewHolder.setData(position,parent);

        return convertView;
    }
}
