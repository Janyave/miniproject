package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.model.Course;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzjixinyu on 2015/7/29.
 */
public class ContactAdapter extends BaseAdapter{
    private Context mcontext;

    public ContactAdapter(Context context) {
        this.mcontext = context;
    }

    class ViewHolder {

        private ImageView iv_avatar;
        private TextView tv_text;


        public ViewHolder(View root) {
            iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
            tv_text = (TextView) root.findViewById(R.id.tv_text);
        }

        /**
         * ��������δ��
         */
        public void setData(int position){
        }
    }

    /**
     * �Ƿ����Լ�����Ϣ
     * @param position
     * @return
     */
    private Boolean isMe(int position){
        //TODO
        return true;
    }

    /**
     * ����Ԫ��
     * @param i
     */
    private void add(int i){
        //TODO
        notifyDataSetChanged();
    }

    //TODO ��������������ģ��Ϊ10��
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

        if(true){
            convertView=parent.inflate(mcontext, R.layout.item_contact_me, null);
        }else{
            convertView=parent.inflate(mcontext, R.layout.item_contact_other, null);
        }
        viewHolder=new ViewHolder(convertView);

        viewHolder.setData(position);

        return convertView;
    }

}
