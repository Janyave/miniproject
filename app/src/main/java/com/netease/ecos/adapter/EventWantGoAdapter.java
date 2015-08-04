package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.ecos.R;

import org.w3c.dom.Text;

/**
 * Created by hzjixinyu on 2015/8/4.
 */
public class EventWantGoAdapter extends BaseAdapter{
    private Context mcontext;

    public EventWantGoAdapter(Context context) {
        this.mcontext = context;
    }




    class ViewHolder implements View.OnClickListener{

        private ImageView iv_avatar;
        private ImageView iv_relation;
        private LinearLayout ll_tagList;
        private TextView tv_name;
        private TextView tv_signature;
        private TextView tv_contact;


        public ViewHolder(View root) {
            iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
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

            ll_tagList.removeAllViews();

            View v=parent.inflate(mcontext, R.layout.item_tag,null);
            ((TextView)v.findViewById(R.id.tv_tag)).setText("单身狗");
            ll_tagList.addView(v);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_avatar:
                    //TODO 个人界面
                    Toast.makeText(mcontext, "个人界面", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_contact:
                    //TODO 私信界面
                    Toast.makeText(mcontext, "私信界面", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    //TODO 数据数量【现在模拟为4】
    @Override
    public int getCount() {
        return 4;
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
