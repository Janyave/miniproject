package com.netease.ecos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.fragment.CourseFragment;
import com.netease.ecos.model.Course;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CourseListViewAdapter extends BaseAdapter{

    private Context mcontext;
    private List<Course> courseList = new ArrayList<Course>();

    public CourseListViewAdapter(Context context) {
        this.mcontext = context;
    }

    public CourseListViewAdapter(Context context, List<Course> courseList) {
        this.mcontext = context;
        this.courseList=courseList;
    }


    class ViewHolder {

        private ImageView networkImageView;
        private ImageView imageAuthorPic;
        private TextView textViewTitle;
        private TextView textViewAmz;
        private TextView textViewAuthor;


        public ViewHolder(View root) {
            networkImageView = (ImageView) root.findViewById(R.id.pic_dis);
            imageAuthorPic = (ImageView) root.findViewById(R.id.imageViewAuthor);

            textViewTitle = (TextView) root.findViewById(R.id.textViewTitle);
            textViewAmz = (TextView) root.findViewById(R.id.textViewAmz);
            textViewAuthor = (TextView) root.findViewById(R.id.textViewAuthor);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position){
            Course item=courseList.get(position);

            Picasso.with(mcontext).load(item.coverUrl).placeholder(R.drawable.img_default).into(networkImageView);
            Picasso.with(mcontext).load(item.authorAvatarUrl).placeholder(R.drawable.img_default).into(imageAuthorPic);
            textViewTitle.setText(item.title);
            textViewAmz.setText(item.praiseNum + "");
            textViewAuthor.setText(item.author);
        }
    }


    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return courseList.size();
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
        if (convertView==null){
            convertView=parent.inflate(mcontext,R.layout.item_course,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }

}
