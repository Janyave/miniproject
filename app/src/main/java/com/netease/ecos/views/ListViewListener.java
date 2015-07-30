package com.netease.ecos.views;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hzjixinyu on 2015/7/30.
 */
public class ListViewListener implements View.OnTouchListener{

    IOnMotionEvent onMotionEvent;
    String state="up";
    float downY=0;

    public ListViewListener(IOnMotionEvent onMotionEvent){
        this.onMotionEvent=onMotionEvent;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        String nowState=state;

        if (event.getAction()==MotionEvent.ACTION_DOWN){
            downY=event.getY();
        }
        if (event.getAction()==MotionEvent.ACTION_MOVE){
            float offsetY=event.getY()-downY;
            if (offsetY>30){
                nowState="up";
                downY=event.getY();
                //TODO 上滑事件
                Log.v("sroll", "up " + offsetY + "");
                onMotionEvent.doInUp();

            }
            if (offsetY<-30){
                nowState="down";
                downY=event.getY();
                //TODO 上滑事件
                Log.v("sroll", "down " + offsetY);
                onMotionEvent.doInDown();
            }

            if (!TextUtils.equals(nowState, state)){
                //TODO 方向装换事件
                if (TextUtils.equals(nowState,"up")){
                    //TODO 上滑事件
                    Log.v("sroll","change up "+v.getScrollY()+"");
                    onMotionEvent.doInChangeToUp();
                }
                if (TextUtils.equals(nowState,"down")){
                    //TODO 下滑事件
                    Log.v("sroll","change down "+v.getScrollY()+"");
                    onMotionEvent.doInChangeToDown();
                }
                state=nowState;
            }
        }

        if (event.getAction()==MotionEvent.ACTION_UP){
//                    Log.v("sroll","up " +v.getScrollY()+"");
        }


        return false;
    }

    public interface IOnMotionEvent{
        void doInDown();
        void doInUp();
        void doInChangeToDown();
        void doInChangeToUp();
    }
}
