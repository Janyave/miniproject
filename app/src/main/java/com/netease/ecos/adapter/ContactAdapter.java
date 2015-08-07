package com.netease.ecos.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.netease.ecos.R;
import com.netease.ecos.model.Course;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;
import com.netease.ecos.views.RoungImageView;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzjixinyu on 2015/7/29.
 */
public class ContactAdapter extends BaseAdapter{
    private Context mcontext;
    private List<IMMessage> messageList=new ArrayList<>();
    private String targetIMID;
    private String targetAvatarUrl;

    public ContactAdapter(Context context, List<IMMessage> messageList, String targetIMID,String targetAvatarUrl) {
        this.mcontext = context;
        this.messageList=messageList;
        this.targetIMID=targetIMID;
        this.targetAvatarUrl=targetAvatarUrl;
    }


    class ViewHolder {

        private RoundImageView iv_avatar;
        private TextView tv_text;
        private Boolean isMe;


        public ViewHolder(View root, Boolean me) {
            iv_avatar = (RoundImageView) root.findViewById(R.id.iv_avatar);
            tv_text = (TextView) root.findViewById(R.id.tv_text);
            isMe=me;

            iv_avatar.setDefaultImageResId(R.mipmap.bg_female_default);
            iv_avatar.setErrorImageResId(R.mipmap.bg_nogender_default);
            RequestQueue queue = Volley.newRequestQueue(mcontext);
            ImageLoader.ImageCache imageCache = new SDImageCache();
            ImageLoader imageLoader = new ImageLoader(queue, imageCache);
            if (isMe){
                iv_avatar.setImageUrl(UserDataService.getSingleUserDataService(mcontext).getUser().avatarUrl, imageLoader);
            }else {
                iv_avatar.setImageUrl(targetAvatarUrl, imageLoader);
            }
        }

        /**
         * ��������δ��
         */
        public void setData(int position){
            IMMessage item=messageList.get(position);
            tv_text.setText(item.getContent());
        }
    }

    /**
     * ����Ԫ��
     * @param
     */
    public void add(IMMessage message){
        messageList.add(message);
        notifyDataSetChanged();
    }

    //TODO ��������������ģ��Ϊ10��
    @Override
    public int getCount() {
        return messageList.size();
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

        //判断
        Boolean isMe= !TextUtils.equals(messageList.get(position).getFromAccount(),targetIMID);
        Log.v("contact", messageList.get(position).getFromAccount());
        Log.v("contact", targetIMID);
        if(convertView!=null&&((ViewHolder)convertView.getTag()).isMe==isMe){
            viewHolder=(ViewHolder)convertView.getTag();
        }else if(isMe){
            convertView=parent.inflate(mcontext, R.layout.item_contact_me, null);
            viewHolder=new ViewHolder(convertView,isMe);
            convertView.setTag(viewHolder);
        }else {
            convertView=parent.inflate(mcontext, R.layout.item_contact_other, null);
            viewHolder=new ViewHolder(convertView,isMe);
            convertView.setTag(viewHolder);
        }

        viewHolder.setData(position);

        return convertView;
    }

}
