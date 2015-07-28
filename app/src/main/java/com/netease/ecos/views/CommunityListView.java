package com.netease.ecos.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Administrator on 2015/7/28.
 */
public class CommunityListView extends ListView {
    public CommunityListView(Context context) {
        // TODO Auto-generated method stub
        super(context);
    }

    public CommunityListView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        super(context, attrs);
    }

    public CommunityListView(Context context, AttributeSet attrs, int defStyle) {
        // TODO Auto-generated method stub
        super(context, attrs, defStyle);
    }

    /**
     * 重构onInterceptTouchEvent方法并返回false会停止父ListView拦截子GridView的
     * 滑动操作，从而使子GridView能够滑动
     * @param ev
     * @return
     */
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return false;
//    }
}
