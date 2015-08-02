package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;

/**
 * Created by hzjixinyu on 2015/8/2.
 */
public class PersonCourseAdapter extends BaseAdapter{
    private Context mcontext;
//    private List<Course> courseList = new ArrayList<Course>();

    public PersonCourseAdapter(Context context) {
        this.mcontext = context;
    }

//    public PersonCourseAdapter(Context context, List<Course> courseList) {
//        this.mcontext = context;
//        this.courseList = courseList;
//    }


    class ViewHolder {

        private ImageView iv_cover;
        private TextView tv_praiseNum;
        private TextView tv_title;
        private TextView tv_time;


        public ViewHolder(View root) {
            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);

            tv_praiseNum = (TextView) root.findViewById(R.id.tv_praiseNum);
            tv_title = (TextView) root.findViewById(R.id.tv_title);
            tv_time = (TextView) root.findViewById(R.id.tv_time);
        }

        /**
         * 传入数据未定
         */
        public void setData(final int position) {
//            Course item = courseList.get(position);

//            Picasso.with(mcontext).load(item.coverUrl).placeholder(R.drawable.img_default).into(iv_cover);
//            tv_title.setText(item.title);
//            tv_praiseNum.setText(item.praiseNum + "");
//            tv_time.setText(item.time);

        }
    }


    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return 3;
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
            convertView = parent.inflate(mcontext, R.layout.item_personage_course, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }
}
