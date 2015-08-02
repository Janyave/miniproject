package com.netease.ecos.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.activity.CommentDetailActivity;
import com.netease.ecos.activity.DisplayDetailActivity;
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.model.Comment;
import com.netease.ecos.model.Share;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hzjixinyu on 2015/7/23.
 */
public class DisplayListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mcontext;
    private List<Share> shareList;

    public DisplayListViewAdapter(Context context, List<Share> shareList) {
        this.mcontext = context;
        this.shareList = shareList;
    }

    class ViewHolder {

        private LinearLayout ll_author;
        private ImageView iv_avatar;
        private TextView tv_name;
        private TextView tv_focus;

        private ImageView iv_cover;
        private TextView tv_coverNum;
        private LinearLayout ll_coverInformation;
        private TextView tv_coverTitle;
        private TextView tv_coverTime;

        private TextView tv_praise;
        private TextView tv_evaluate;
        private ImageView iv_praise;
        private ImageView iv_evaluate;
        private LinearLayout ll_praise;
        private LinearLayout ll_evaluate;

        private TextView tv_allEvaluation;

        //        private ExtensibleListView lv_evaluation;
        private LinearLayout ll_evaluationList;
        private DisplayItemEvalutionViewAdapter adapter;


        public ViewHolder(View root, int position) {
            ll_author = (LinearLayout) root.findViewById(R.id.ll_author);
            iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            tv_focus = (TextView) root.findViewById(R.id.tv_focus);

            iv_cover = (ImageView) root.findViewById(R.id.iv_cover);
            tv_coverNum = (TextView) root.findViewById(R.id.tv_coverNum);
            ll_coverInformation = (LinearLayout) root.findViewById(R.id.ll_coverInformation);
            tv_coverTitle = (TextView) root.findViewById(R.id.tv_coverTitle);
            tv_coverTime = (TextView) root.findViewById(R.id.tv_coverTime);

            tv_praise = (TextView) root.findViewById(R.id.tv_praise);
            tv_evaluate = (TextView) root.findViewById(R.id.tv_evaluation);
            iv_praise = (ImageView) root.findViewById(R.id.iv_praise);
            iv_evaluate = (ImageView) root.findViewById(R.id.iv_evaluation);
            ll_praise = (LinearLayout) root.findViewById(R.id.ll_praise);
            ll_evaluate = (LinearLayout) root.findViewById(R.id.ll_evaluation);
            tv_praise = (TextView) root.findViewById(R.id.tv_praise);
            tv_evaluate = (TextView) root.findViewById(R.id.tv_evaluation);

            ll_evaluationList = (LinearLayout) root.findViewById(R.id.ll_evaluationList);
            tv_allEvaluation = (TextView) root.findViewById(R.id.tv_allEvalution);


        }

        /**
         * 传入数据未定
         */
        public void setData(final int position) {
            Share item = shareList.get(position);

            Picasso.with(mcontext).load(item.avatarUrl).placeholder(R.drawable.img_default).into(iv_avatar);
            Picasso.with(mcontext).load(item.coverUrl).placeholder(R.drawable.img_default).into(iv_cover);
            tv_name.setText(item.nickname);
            tv_coverTitle.setText(item.title);
            tv_coverNum.setText(item.totalPageNumber + "");
            tv_coverTime.setText(item.getDateDescription());
            tv_praise.setText(item.praiseNum + "");
            tv_evaluate.setText(item.commentNum + "");
            //set tag
            ll_author.setTag(position);
            iv_cover.setTag(position);
            tv_coverTitle.setTag(position);
            tv_focus.setTag(position);
            ll_praise.setTag(position);
            ll_evaluate.setTag(position);
            ll_evaluationList.setTag(position);
            tv_allEvaluation.setTag(position);

            //set listener
            ll_author.setOnClickListener(DisplayListViewAdapter.this);
            iv_cover.setOnClickListener(DisplayListViewAdapter.this);
            tv_coverTitle.setOnClickListener(DisplayListViewAdapter.this);
            tv_focus.setOnClickListener(DisplayListViewAdapter.this);
            ll_praise.setOnClickListener(DisplayListViewAdapter.this);
            ll_evaluate.setOnClickListener(DisplayListViewAdapter.this);
            ll_evaluationList.setOnClickListener(DisplayListViewAdapter.this);
            tv_allEvaluation.setOnClickListener(DisplayListViewAdapter.this);

            if (item.hasAttention) {
                tv_focus.setText("已关注");
            } else {
                tv_focus.setText("关注");
            }
            if (item.hasPraised) {
                //TODO 已赞图片
            } else {
                //TODO 未赞图片
            }

            //评论Adapter
//            adapter=new DisplayItemEvalutionViewAdapter(mcontext, item.commentList);
//            lv_evaluation.setAdapter(adapter);
//            int num=item.commentList.size()>3?3:item.commentList.size();
//            for (int i=0; i<num; i++){
//                Comment comment=item.commentList.get(i);
//                View view=View.inflate(mcontext,R.layout.item_display_item_evalution,null);
//                ((TextView)view.findViewById(R.id.tv_name)).setText(comment.fromNickName);
//                ((TextView)view.findViewById(R.id.tv_evaluation)).setText(comment.content);
//                ll_evaluationList.addView(view);
//            }
        }
    }

    //TODO 数据数量【现在模拟为10】
    @Override
    public int getCount() {
        return shareList.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mcontext, R.layout.item_display, null);
            viewHolder = new ViewHolder(convertView, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();
        int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.ll_author:
                intent = new Intent(mcontext, PersonageDetailActivity.class);
//                bundle.putString(PersonageDetailActivity.UserID, shareList.get(position).userId);
                bundle.putString(PersonageDetailActivity.UserID, "");
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.iv_cover:
            case R.id.tv_coverTitle:
                intent = new Intent(mcontext, DisplayDetailActivity.class);
                bundle.putString(PersonageDetailActivity.UserID, shareList.get(position).shareId);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
            case R.id.tv_focus:
                if (TextUtils.equals(((TextView) v).getText().toString(), "关注")) {
                    ((TextView) v).setText("已关注");
                    //TODO 关注事件
                } else {
                    ((TextView) v).setText("关注");
                }
                break;
            case R.id.ll_praise:
                //TODO:点赞 favor
                break;
            case R.id.ll_evaluation:
            case R.id.tv_allEvalution:
            case R.id.ll_evaluationList:
                intent = new Intent(mcontext, CommentDetailActivity.class);
                bundle.putString(CommentDetailActivity.FromId, shareList.get(position).shareId);
                bundle.putString(CommentDetailActivity.CommentType, Comment.CommentType.分享.getBelongs());
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
                break;
        }
    }

}
