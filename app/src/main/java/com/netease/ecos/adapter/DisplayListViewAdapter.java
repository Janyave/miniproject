package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.squareup.picasso.Picasso;

/**
 * Created by hzjixinyu on 2015/7/23.
 */
public class DisplayListViewAdapter extends BaseAdapter {

    private Context mcontext;

    public DisplayListViewAdapter(Context context){
        this.mcontext=context;
    }

    class ViewHolder {

        private ImageView iv_avatar;
        private TextView tv_name;
        private Button btn_focus;

        private ImageView iv_cover;
        private LinearLayout ll_coverInformation;
        private TextView tv_coverTitle;
        private TextView tv_coverTime;

        private Button btn_praise;
        private Button btn_evaluate;


        public ViewHolder(View root) {
            iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            btn_focus = (Button) root.findViewById(R.id.btn_focus);

            iv_cover=(ImageView)root.findViewById(R.id.iv_cover);
            ll_coverInformation=(LinearLayout)root.findViewById(R.id.ll_coverInformation);
            tv_coverTitle = (TextView) root.findViewById(R.id.tv_coverTitle);
            tv_coverTime = (TextView) root.findViewById(R.id.tv_coverTime);

            btn_praise = (Button) root.findViewById(R.id.btn_praise);
            btn_evaluate = (Button) root.findViewById(R.id.btn_evaluate);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position){
            //TODO 绑定数据
            Picasso.with(mcontext).load("http://i.imgur.com/DvpvklR.png").placeholder(R.drawable.img_default).into(iv_cover);
        }
    }

    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return 10;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=parent.inflate(mcontext,R.layout.item_display,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }
}
