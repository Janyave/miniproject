package com.netease.ecos.views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.ecos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hzjixinyu on 2015/7/24.
 */
public class Banner extends RelativeLayout{

    private Context mContext;

    private TextView tv_currentNum;
    private ViewPager vp_image;

    private List<String> URLList=new ArrayList<>();
    private List<View> ViewList=new ArrayList<>();
    private int count=0;
    private int delayTime=3000;

    private int pagerViewID=R.layout.item_bannerpager;
    private PagerAdapter pagerAdapter;

    private Timer timer;
    private TimerTask task;

    public Banner(Context context) {
        super(context);
        this.mContext=context;
        initView();
        initAuto();
    }



    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView();
        initAuto();
    }


    public void setURLList(List<String> data){
        this.URLList=data;
        this.count=data.size();

        for (int i=0; i<count; i++){
            View v=View.inflate(mContext, pagerViewID,null);
            Picasso.with(mContext).load(URLList.get(i)).into((ImageView)v.findViewById(R.id.iv_image));
            ViewList.add(v);
        }

        pagerAdapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view==o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView((View) ViewList.get(position % count));//Ìí¼ÓÒ³¿¨
                return ViewList.get(position % count);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)   {
                container.removeView((View) ViewList.get(position % count));
            }
        };

        vp_image.setAdapter(pagerAdapter);
        vp_image.setCurrentItem(count * 20);
        vp_image.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    tv_currentNum.setText(vp_image.getCurrentItem() % count + 1 + "");
                }
            }
        });
    }

    public void setDelayTime(int time){
        this.delayTime=time;
    }

    private void initView(){
        View.inflate(mContext, R.layout.item_bannerlayout,this);
        vp_image=(ViewPager)findViewById(R.id.vp_image);
        tv_currentNum=(TextView)findViewById(R.id.tv_currentNum);
        vp_image.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    if (timer!=null){
                        timer.cancel();
                        timer=null;
                    }
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (timer == null) {
                        timer=new Timer();
                        timer.schedule(getNewTask(),delayTime,delayTime);
                    }
                }
                return false;
            }
        });
    }

    private void initAuto() {
        timer=new Timer();
        timer.schedule(getNewTask(),delayTime,delayTime);
    }

    private TimerTask getNewTask(){
        task=null;
        task=new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        vp_image.setCurrentItem(vp_image.getCurrentItem()+1);
                    }
                });
            }
        };
        return task;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            if (timer!=null){
                timer.cancel();
                timer=null;
            }
        }
        if (event.getAction()==MotionEvent.ACTION_UP){
            if (timer == null) {
                timer=new Timer();
                timer.schedule(getNewTask(),delayTime,delayTime);
            }
        }
        return true;
    }
}
