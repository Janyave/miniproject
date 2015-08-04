package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.CourseDetailActivity;
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.model.Course;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CourseListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mcontext;
    private List<Course> courseList = new ArrayList<Course>();

    public CourseListViewAdapter(Context context) {
        this.mcontext = context;
    }

    public CourseListViewAdapter(Context context, List<Course> courseList) {
        this.mcontext = context;
        this.courseList = courseList;
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
        public void setData(final int position) {
            Course item = courseList.get(position);
            if (item.coverUrl != null)
                Picasso.with(mcontext).load(item.coverUrl).placeholder(R.drawable.img_default).into(networkImageView);
            if (item.authorAvatarUrl != null && !item.authorAvatarUrl.equals(""))
                Picasso.with(mcontext).load(item.authorAvatarUrl).placeholder(R.drawable.img_default).into(imageAuthorPic);
            textViewTitle.setText(item.title);
            textViewAmz.setText(item.praiseNum + "");
            textViewAuthor.setText(item.author);
            //set position tag
            imageAuthorPic.setTag(position);
            textViewTitle.setTag(position);
            networkImageView.setTag(position);
            //set listener
            imageAuthorPic.setOnClickListener(CourseListViewAdapter.this);
            textViewTitle.setOnClickListener(CourseListViewAdapter.this);
            networkImageView.setOnClickListener(CourseListViewAdapter.this);
        }
    }

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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_course, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Intent intent;
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.imageViewAuthor:
                intent = new Intent(mcontext, PersonageDetailActivity.class);
                bundle.putString(PersonageDetailActivity.UserID, courseList.get(position).userId);
                bundle.putBoolean(PersonageDetailActivity.IsOwn, false);
                break;
            default:
                intent = new Intent(mcontext, CourseDetailActivity.class);
                bundle.putString(CourseDetailActivity.CourseID, courseList.get(position).courseId);
                break;
        }
        intent.putExtras(bundle);
        mcontext.startActivity(intent);
    }
}
