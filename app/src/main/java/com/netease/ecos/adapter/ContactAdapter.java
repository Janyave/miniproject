package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.model.Course;
import com.netease.ecos.utils.RoundImageView;
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

    public ContactAdapter(Context context, List<IMMessage> messageList) {
        this.mcontext = context;
        this.messageList=messageList;
    }


    class ViewHolder {

        private RoundImageView iv_avatar;
        private TextView tv_text;


        public ViewHolder(View root) {
            iv_avatar = (RoundImageView) root.findViewById(R.id.iv_avatar);
            tv_text = (TextView) root.findViewById(R.id.tv_text);
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
     * @param i
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
        Boolean isMe=messageList.get(position).getFromAccount().equals("test1");
        if(isMe){
            convertView=parent.inflate(mcontext, R.layout.item_contact_me, null);
        }else{
            convertView=parent.inflate(mcontext, R.layout.item_contact_other, null);
        }
        viewHolder=new ViewHolder(convertView);

        viewHolder.setData(position);

        return convertView;
    }

}
