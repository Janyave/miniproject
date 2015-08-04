package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.DisplayDetailActivity;
import com.netease.ecos.model.Share;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hzjixinyu on 2015/7/23.
 */
public class RecruitmentDetailWorkAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mcontext;
    private List<Share> shareList;

    public RecruitmentDetailWorkAdapter(Context context, List<Share> shareList) {
        this.mcontext = context;
        this.shareList = shareList;
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
            tv_coverNum = (TextView) root.findViewById(R.id.tv_coverNum);
            tv_coverTitle = (TextView) root.findViewById(R.id.tv_coverTitle);
            tv_coverTime = (TextView) root.findViewById(R.id.tv_coverTime);
            tv_praise = (TextView) root.findViewById(R.id.tv_praise);
            tv_evaluate = (TextView) root.findViewById(R.id.tv_evaluation);
            iv_praise = (ImageView) root.findViewById(R.id.iv_praise);
            iv_evaluate = (ImageView) root.findViewById(R.id.iv_evaluation);
            ll_praise = (LinearLayout) root.findViewById(R.id.ll_praise);
            ll_evaluate = (LinearLayout) root.findViewById(R.id.ll_evaluation);
        }

        /**
         * 传入数据未定
         */
        public void setData(int position) {
            if (shareList.get(position).coverUrl != null && !shareList.get(position).coverUrl.equals(""))
                Picasso.with(mcontext).load(shareList.get(position).coverUrl).placeholder(R.drawable.img_default).into(iv_cover);
            tv_coverNum.setText(shareList.get(position).totalPageNumber + "");
            tv_coverTitle.setText(shareList.get(position).title);
            tv_coverTime.setText(shareList.get(position).getDateDescription() + "");
            tv_praise.setText(shareList.get(position).praiseNum + "");
            tv_evaluate.setText(shareList.get(position).commentNum + "");
        }
    }

    @Override
    public int getCount() {
        return shareList.size();
    }


    @Override
    public Object getItem(int position) {
        return shareList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mcontext, DisplayDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DisplayDetailActivity.ShareId, "1");
        intent.putExtras(bundle);
        mcontext.startActivity(intent);
    }
}
