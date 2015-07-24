package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;

/**
 * Created by hzjixinyu on 2015/7/21.
 */
public class CourseDetailOtherWorksHListViewAdapter extends BaseAdapter {
    private Context mContext;

    public CourseDetailOtherWorksHListViewAdapter(Context c){
        mContext = c;
    }


    //TODO 假定数量
    @Override
    public int getCount() {
        return 6;
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
		if(convertView==null){
            convertView=parent.inflate(mContext, R.layout.item_coursedetail_otherworks, null);
            viewHolder=new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}

        viewHolder.setData();

        return convertView;
    }
    class ViewHolder{
        private ImageView iv_image;
        private TextView tv_time;
        private TextView tv_name;
        private ImageView iv_avatar;
        private LinearLayout ll_author;

        ViewHolder(View v){
            iv_image=(ImageView)v.findViewById(R.id.iv_image);
            tv_name=(TextView)v.findViewById(R.id.tv_name);
            iv_avatar=(ImageView)v.findViewById(R.id.iv_avatar);
            tv_time=(TextView)v.findViewById(R.id.tv_time);
            ll_author=(LinearLayout)v.findViewById(R.id.ll_author);
        }

        //TODO 绑定数据
        void setData(){
        }
    }
}

