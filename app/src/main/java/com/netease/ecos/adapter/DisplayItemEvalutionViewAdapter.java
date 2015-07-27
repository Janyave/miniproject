package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.netease.ecos.R;


/**
 * Created by hzjixinyu on 2015/7/27.
 */
public class DisplayItemEvalutionViewAdapter extends BaseAdapter{
    private Context mcontext;
    public DisplayItemEvalutionViewAdapter(Context context) {
        this.mcontext = context;
    }

    class ViewHolder {

        private TextView tv_name;
        private TextView tv_evaluation;

        public ViewHolder(View root) {
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_evaluation=(TextView)root.findViewById(R.id.tv_evaluation);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position) {
            //TODO 绑定数据
        }
    }

    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return 4;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_display_item_evalution, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }
}
