package com.netease.ecos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.views.CommunityGridView;
import com.netease.ecos.views.CommunityListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommunityLocationListViewAdapter extends BaseAdapter {

    private Context mcontext;
    private Handler handler;
    ArrayList<int[]> locationCommunityCountList;

    public CommunityLocationListViewAdapter(Context context, Handler handler, ArrayList<int[]> locationCommunityCountList) {
        this.mcontext = context;
        this.handler = handler;
        this.locationCommunityCountList = locationCommunityCountList;
    }

    //TODO 省份块的数量是5
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        arrayListLocation.add(Location_H);
        arrayListLocation.add(Location_A);
        arrayListLocation.add(Location_I);
        arrayListLocation.add(Location_L);
        arrayListLocation.add(Location_T);

        ViewHolder viewHolder = null;
        if (convertView == null) {
            // item_location_show布局文件包含一个TextView（显示省份首字母范围）和一个GridView（显示省份）
            convertView = parent.inflate(mcontext, R.layout.item_location_show, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(position);
        return convertView;
    }

    class ViewHolder {

        private TextView textViewScope;
        private CommunityGridView gv_location;


        public ViewHolder(View root) {
            textViewScope = (TextView) root.findViewById(R.id.tv_location_scope);
            gv_location = (CommunityGridView) root.findViewById(R.id.gv_location);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position) {
            textViewScope.setText(strings[position]);
            gv_location.setAdapter(new CommunityLocationGridViewAdapter(mcontext, arrayListLocation.get(position), locationCommunityCountList.get(position)));
            gv_location.setTag("CommunityGridView" + position);
            gv_location.setSelector(new ColorDrawable(Color.TRANSPARENT));  // 设置GridView在被选中时背景色不改变

            gv_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Message message = handler.obtainMessage();
                    CharSequence charSequence = ((Button) parent.findViewWithTag("Button" + position)).getText();
                    String string = (String) charSequence;
                    String[] strings = string.split("\\(");
                    message.obj = strings[0];
                    handler.sendMessage(message);
                }
            });
        }
    }

    private String[] strings = new String[]{"热门", "A-G", "H-K", "L-S", "T-Z"};
    private String[] Location_H = new String[]{"不限", "北京", "上海", "广东", "浙江", "四川"};
    private String[] Location_A = new String[]{"安徽", "北京", "重庆", "福建", "甘肃", "广东", "广西", "贵州"};
    private String[] Location_I = new String[]{"海南", "河北", "黑龙江", "河南", "湖北", "湖南", "江苏", "江西", "吉林"};
    private String[] Location_L = new String[]{"辽宁", "内蒙", "宁夏", "青海", "山东", "上海", "陕西", "山西", "四川"};
    private String[] Location_T = new String[]{"天津", "新疆", "西藏", "云南", "浙江"};
    private ArrayList<String[]> arrayListLocation = new ArrayList<String[]>();
}
