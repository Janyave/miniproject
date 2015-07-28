package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.ExhibitDetailActivity;
import com.netease.ecos.views.ExtensibleListView;
import com.squareup.picasso.Picasso;

/**
 * Created by hzjixinyu on 2015/7/23.
 */
public class RecruitmentDetailWorkAdapter extends BaseAdapter {

    private Context mcontext;

    public RecruitmentDetailWorkAdapter(Context context) {
        this.mcontext = context;
    }

    class ViewHolder {

        private ImageView iv_cover;
        private TextView tv_coverNum;
        private TextView tv_coverTitle;
        private TextView tv_coverTime;

        private TextView tv_praise;
        private TextView tv_evaluate;
        private ImageView iv_praise;
        private ImageView iv_evaluate;
        private LinearLayout ll_praise;
        private LinearLayout ll_evaluate;


        public ViewHolder(View root) {

            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);
            tv_coverNum=(TextView)root.findViewById(R.id.tv_coverNum);
            tv_coverTitle = (TextView) root.findViewById(R.id.tv_coverTitle);
            tv_coverTime = (TextView) root.findViewById(R.id.tv_coverTime);

            tv_praise = (TextView) root.findViewById(R.id.tv_praise);
            tv_evaluate = (TextView) root.findViewById(R.id.tv_evaluation);
            iv_praise = (ImageView) root.findViewById(R.id.iv_praise);
            iv_evaluate = (ImageView) root.findViewById(R.id.iv_evaluation);
            ll_praise = (LinearLayout) root.findViewById(R.id.ll_praise);
            ll_evaluate = (LinearLayout) root.findViewById(R.id.ll_evaluation);


            iv_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, ExhibitDetailActivity.class);
                    mcontext.startActivity(intent);
                }
            });
            ll_praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 关注图标切换
                }
            });

            ll_evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 评论页面
                }
            });
        }

        /**
         * 传入数据未定
         */
        public void setData(int position) {
            //TODO 绑定数据
            Picasso.with(mcontext).load("http://i.imgur.com/DvpvklR.png").placeholder(R.drawable.img_default).into(iv_cover);
        }
    }

    //TODO 数据数量【现在模拟为5】
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_recruitment_detail_work, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }
}
